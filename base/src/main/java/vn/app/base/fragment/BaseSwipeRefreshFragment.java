package vn.app.base.fragment;

import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import vn.app.base.R;
import vn.app.base.customview.endlessrecycler.EndlessRecyclerOnScrollListener;

/**
 * Created on 10/14/2015.
 */
public abstract class BaseSwipeRefreshFragment extends CommonFragment implements SwipeRefreshLayout.OnRefreshListener {

    protected SwipeRefreshLayout mSwipeRefreshLayout;

    protected RecyclerView rvList;

    @Override
    protected int getLayoutId() {
        return R.layout.common_swipe_to_refresh_list;
    }

    @Override
    protected void initCommonViews(View rootView) {
        super.initCommonViews(rootView);
        mSwipeRefreshLayout = (SwipeRefreshLayout) rootView.findViewById(R.id.swipe_refresh_layout);
        rvList = (RecyclerView) rootView.findViewById(R.id.recycerList);
        mSwipeRefreshLayout.setOnRefreshListener(this);
    }

    public void setCanRefresh(boolean canRefresh) {
        if (mSwipeRefreshLayout != null) {
            mSwipeRefreshLayout.setEnabled(canRefresh);
        }
    }

    abstract protected void onRefreshData();

    protected void setLoadMore() {
        if (rvList != null) {
            rvList.addOnScrollListener(new EndlessRecyclerOnScrollListener() {
                @Override
                public void onLoadMore(int currentPage) {
                    onLoadingMore(currentPage);
                }
            });
        }
    }

    @Override
    protected void initialResponseHandled() {
        super.initialResponseHandled();
        mSwipeRefreshLayout.setRefreshing(false);
    }

    protected void onLoadingMore(int currentPage) {

    }

    @Override
    public void onRefresh() {
        onRefreshData();
    }
}
