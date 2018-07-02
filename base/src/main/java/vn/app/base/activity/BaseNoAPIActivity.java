package vn.app.base.activity;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;

import butterknife.ButterKnife;
import vn.app.base.customview.LoadingDialog;
import vn.app.base.util.DebugLog;
import vn.app.base.util.KeyboardUtil;

/**
 * Created by Envy 15T on 6/4/2015.
 */
public abstract class BaseNoAPIActivity extends AppCompatActivity {

    LoadingDialog loadingDialog;

    protected ViewGroup rootView;

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
        initView();
        initData();
    }


    @Override
    protected void onDestroy() {
        hideLoading();
        super.onDestroy();
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
                    KeyboardUtil.hideKeyboard(BaseNoAPIActivity.this);
                    return false;
                }
            });
        }
    }

}
