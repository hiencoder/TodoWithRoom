package vn.app.base.customview.endlessrecycler;

import android.support.v7.widget.RecyclerView;

import vn.app.base.util.DebugLog;


/**
 * Custom Scroll listener for RecyclerView.
 * Based on implementation https://gist.github.com/ssinss/e06f12ef66c51252563e
 */
public abstract class EndlessRecyclerOnScrollListener extends RecyclerView.OnScrollListener {
    public static String TAG = "EndlessScrollListener";

    private int previousTotal = 0; // The total number of items in the dataset after the last load
    private boolean loading = true; // True if we are still waiting for the last set of data to load.
    private int visibleThreshold = 1; // The minimum amount of items to have below your current scroll position before loading more.
    int firstVisibleItem, visibleItemCount, totalItemCount;

    private boolean cancelLoading = false; // cancel loadmore on onLoadMore(currentPage);

    private int currentPage = 1;

    RecyclerViewPositionHelper mRecyclerViewHelper;

    @Override
    public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
        super.onScrolled(recyclerView, dx, dy);
        mRecyclerViewHelper = RecyclerViewPositionHelper.createHelper(recyclerView);
        visibleItemCount = recyclerView.getChildCount();
        totalItemCount = mRecyclerViewHelper.getItemCount();
        firstVisibleItem = mRecyclerViewHelper.findFirstVisibleItemPosition();

        DebugLog.i("totalItemCount: " + totalItemCount + " previousTotal: " + previousTotal);

        if (loading) {
            if (totalItemCount > previousTotal) {
                loading = false;
                previousTotal = totalItemCount;
            }
        }
        if (!loading && (totalItemCount - visibleItemCount)
                <= (firstVisibleItem + visibleThreshold)) {
            // End has been reached
            // Do something
            currentPage++;
            onLoadMore(currentPage);
            if (cancelLoading) {
                loading = false;
                cancelLoading = false;
            } else {
                loading = true;
            }
            DebugLog.i("currentPage: " + currentPage + "  loading: " + loading);
        }
    }

    public void resetLoadingStatus() {
        cancelLoading = true;
    }

    public void resetPreviousTotal() {
        previousTotal = 0;
    }

    public void setPreviousTotal(int previousTotal) {
        this.previousTotal = previousTotal;
    }

    //Start loading
    public abstract void onLoadMore(int currentPage);
}

