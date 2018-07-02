package vn.app.base.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import butterknife.ButterKnife;
import butterknife.Unbinder;
import vn.app.base.BaseApplication;
import vn.app.base.R;
import vn.app.base.activity.CommonActivity;
import vn.app.base.util.DebugLog;
import vn.app.base.util.NetworkUtils;
import vn.app.base.util.UiUtil;


public abstract class BaseFragment extends Fragment {

    protected View rootView;

    protected ViewGroup fragmentViewParent;

    View initialProgressBar;

    View initialNetworkError;

    View initialEmptyList;

    View coverNetworkLoading;

    LinearLayout linearLayoutEmpty;

    TextView tvEmpty;

    protected boolean isLoading = false;

    private Unbinder unbinder;

    public View getInitialEmptyList() {
        return initialEmptyList;
    }

    public View getInitialNetworkError() {
        return initialNetworkError;
    }

    public View getInitialProgressBar() {
        return initialProgressBar;
    }

    public View getCoverNetworkLoading() {
        return coverNetworkLoading;
    }

    public TextView getTvEmpty() {
        return tvEmpty;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        super.onCreateView(inflater, container, savedInstanceState);
        DebugLog.i("Lifecycle " + this.getClass().getSimpleName());
        return createRootView(inflater, container);
    }

    private View createRootView(LayoutInflater inflater, ViewGroup container) {
        if (isSkipGenerateBaseLayout()) {
            rootView = inflater.inflate(getLayoutId(), container, false);
            unbinder = ButterKnife.bind(this, rootView);
            initCommonViews(rootView);
        } else {
            rootView = inflater.inflate(R.layout.layout_base_fragment, container, false);
            fragmentViewParent = (ViewGroup) rootView.findViewById(R.id.fragmentViewParent);
            fragmentViewParent.addView(inflater.inflate(getLayoutId(), container, false));
            unbinder = ButterKnife.bind(this, rootView);
            initCommonViews(rootView);
            bypassCommonNetworkLoadingIfNecessary();
        }
        return rootView;
    }

    protected void initCommonViews(View rootView) {
        initialProgressBar = rootView.findViewById(R.id.initialProgressBar);

        initialNetworkError = rootView.findViewById(R.id.initialNetworkError);

        initialEmptyList = rootView.findViewById(R.id.initialEmptyList);

        coverNetworkLoading = rootView.findViewById(R.id.coverNetworkLoading);

        linearLayoutEmpty = (LinearLayout) rootView.findViewById(R.id.common_layout);

        tvEmpty = (TextView) rootView.findViewById(R.id.common_txt_empty);
    }

    protected boolean isSkipGenerateBaseLayout() {
        return false;
    }

    private void bypassCommonNetworkLoadingIfNecessary() {
        if (!isStartWithLoading()) {
            initialResponseHandled();
        } else {
            initialLoadingProgress();
            isLoading = true;
        }
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        if (getArguments() != null) {
            getArgument(getArguments());
        }
        initView(rootView);
        initData();
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
        if (isCancelRequestOnDestroyView()) {
            NetworkUtils.getInstance(BaseApplication.getInstance()).cancelNormalRequest();
            isLoading = isLoadingMore();
        }
    }

    abstract protected int getLayoutId();

    abstract protected void initView(View root);

    abstract protected void getArgument(Bundle bundle);

    abstract protected void initData();

    protected boolean isStartWithLoading() {
        return false;
    }

    protected String setEmptyDataMessage() {
        return "";
    }

    protected boolean isCancelRequestOnDestroyView() {
        return true;
    }

    protected boolean isLoadingMore() {
        return false;
    }

    private void showAndHideOthers(View target) {
        showOrHide(initialProgressBar, target);
        showOrHide(initialNetworkError, target);
        showOrHide(initialEmptyList, target);
        showOrHide(fragmentViewParent, target);
    }

    protected void showOrHide(View subject, View target) {
        subject.setVisibility(subject == target ? View.VISIBLE : View.GONE);
    }

    protected void showCoverNetworkLoading() {
        UiUtil.showView(coverNetworkLoading);
        isLoading = true;
    }

    protected void hideCoverNetworkLoading() {
        UiUtil.hideView(coverNetworkLoading, true);
        isLoading = false;
    }

    protected void initialLoadingProgress() {
        showAndHideOthers(initialProgressBar);
    }

    protected void initialNetworkError() {
        hideCoverNetworkLoading();
        showAndHideOthers(initialNetworkError);
    }

    protected void initialEmptyList() {
        hideCoverNetworkLoading();
        showAndHideOthers(initialEmptyList);
        linearLayoutEmpty.setGravity(Gravity.CENTER);
        tvEmpty.setText(setEmptyDataMessage());
    }

    protected void initialResponseHandled() {
        hideCoverNetworkLoading();
        showAndHideOthers(fragmentViewParent);
    }

    protected boolean checkFragmentVisible() {
        if (isVisible() && getActivity() != null) {
            return true;
        } else {
            return false;
        }
    }

    protected void clearBackStack() {
        ((CommonActivity) getActivity()).popEntireFragmentBackStack();
    }
}

