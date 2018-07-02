package vn.app.base.activity;

import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;

import butterknife.ButterKnife;
import vn.app.base.api.volley.event.ApiEvent;
import vn.app.base.customview.LoadingDialog;
import vn.app.base.util.DebugLog;
import vn.app.base.util.DialogUtil;
import vn.app.base.util.EventBusHelper;
import vn.app.base.util.KeyboardUtil;

/**
 * Created by Envy 15T on 6/4/2015.
 */
public abstract class BaseActivity extends AppCompatActivity {

    AlertDialog dialogErrorAPI;
    AlertDialog dialogTimeOutAPI;
    AlertDialog dialogNoConnection;

    LoadingDialog loadingDialog;

    protected boolean isUnregistEventBus = false;

    protected ViewGroup rootView;

    DialogInterface.OnClickListener apiErrorDialogOnclick = new DialogInterface.OnClickListener() {
        @Override
        public void onClick(DialogInterface dialogInterface, int i) {
            dialogInterface.dismiss();
            handleApiErrorDialogOnClick();
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        onPreSetContentView(savedInstanceState);
        super.onCreate(savedInstanceState);
        Intent intent = getIntent();
        String action = intent.getAction();
        if (Intent.ACTION_VIEW.equals(action)) {
            handleDeepLinkData(intent.getData());
        }
        setContentView(setContentViewId());
        onPostSetContentView(savedInstanceState);
        ButterKnife.bind(this);
        EventBusHelper.register(this);
        isUnregistEventBus = false;
        initDialogApi();
        initView();
        initData();
    }

    private void initDialogApi() {
        dialogNoConnection = DialogUtil.createApiErrorDialog(this, getNoConnectionMessage(), apiErrorDialogOnclick);
        dialogErrorAPI = DialogUtil.createApiErrorDialog(this, getErrorAPIMessage(), apiErrorDialogOnclick);
        dialogTimeOutAPI = DialogUtil.createApiErrorDialog(this, getTimeOutMessage(), apiErrorDialogOnclick);
        loadingDialog = new LoadingDialog(this);
    }

    protected abstract String getNoConnectionMessage();

    protected abstract String getErrorAPIMessage();

    protected abstract String getTimeOutMessage();

    @Override
    protected void onDestroy() {
        hideLoading();
        if (!isUnregistEventBus) {
            EventBus.getDefault().unregister(this);
        }
        super.onDestroy();
    }

    @SuppressWarnings("unused")
    @Subscribe
    public void onEventMainThread(ApiEvent event) {
        switch (event.apiEventType) {
            case SHOW_API_ERROR_DIALOG:
                showApiDialog(dialogErrorAPI, event.getTitle(), event.getMessage());
                break;
            case SHOW_API_TIMEOUT_DIALOG:
                showApiDialog(dialogTimeOutAPI, null, null);
                break;
            case SHOW_API_NO_CONNECTION_DIALOG:
                showApiDialog(dialogNoConnection, null, null);
                break;
        }
    }

    private synchronized void showApiDialog(AlertDialog alertDialog, String title, String message) {
        if (alertDialog != null && !checkApiDialogIsShow() && !isFinishing()) {
            if (message != null) {
                alertDialog.setTitle(title);
                alertDialog.setMessage(message);
            }
            try {
                DebugLog.i("showApiDialog");
                alertDialog.show();
            } catch (Exception ignored) {

            }
        }
    }

    protected boolean checkApiDialogIsShow() {
        return dialogErrorAPI.isShowing() || dialogTimeOutAPI.isShowing() || dialogNoConnection.isShowing();
    }

    protected void handleApiErrorDialogOnClick() {

    }

    /**
     * Handle data before setContentView call
     *
     * @param savedInstanceState
     */
    protected void onPreSetContentView(Bundle savedInstanceState) {

    }

    /**
     * Handle data after setContentView call
     *
     * @param savedInstanceState
     */
    protected void onPostSetContentView(Bundle savedInstanceState) {
        rootView = (ViewGroup) ((ViewGroup) this
                .findViewById(android.R.id.content)).getChildAt(0);
    }


    /**
     * Handle deep link data
     *
     * @param uri
     */
    protected void handleDeepLinkData(Uri uri) {
        DebugLog.i("uri: " + uri.toString());
    }

    /**
     * @return layout of activity
     */
    public abstract int setContentViewId();

    /**
     * Define your view
     */
    public abstract void initView();


    /**
     * Setup your data
     */
    public abstract void initData();


    @Override
    public void startActivity(Intent intent) {
        EventBusHelper.unregister(this);
        isUnregistEventBus = true;
        super.startActivity(intent);
    }

    @Override
    protected void onResume() {
        super.onResume();
        if (isUnregistEventBus) {
            EventBusHelper.register(this);
            isUnregistEventBus = false;
        }
    }

    public void showLoading() {
        if (loadingDialog != null && !loadingDialog.isShowing()) {
            loadingDialog.showDialogChecked();
        }
    }

    public void hideLoading() {
        if (loadingDialog != null && loadingDialog.isShowing()) {
            loadingDialog.dismissDialogChecked();
        }
    }

    public void hideKeyBoardWhenTouchOutside(ViewGroup viewGroup) {
        if (viewGroup != null) {
            viewGroup.setOnTouchListener(new View.OnTouchListener() {
                @Override
                public boolean onTouch(View view, MotionEvent motionEvent) {
                    KeyboardUtil.hideKeyboard(BaseActivity.this);
                    return false;
                }
            });
        }
    }

}
