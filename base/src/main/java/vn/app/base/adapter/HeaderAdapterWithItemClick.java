package vn.app.base.adapter;

import vn.app.base.adapter.viewholder.OnClickViewHolder;
import vn.app.base.callback.OnRecyclerViewItemClick;


/**
 * Created on 9/21/2015.
 */
public abstract class HeaderAdapterWithItemClick<VH extends OnClickViewHolder, H, T, F> extends HeaderRecyclerViewAdapter<VH, H, T, F> {

    OnRecyclerViewItemClick onRecyclerViewItemClick;

    public OnRecyclerViewItemClick getOnRecyclerViewItemClick() {
        return onRecyclerViewItemClick;
    }

    public void setOnRecyclerViewItemClick(OnRecyclerViewItemClick onRecyclerViewItemClick) {
        this.onRecyclerViewItemClick = onRecyclerViewItemClick;
    }

    @Override
    protected void onBindItemViewHolder(VH holder, int position) {
        holder.setOnRecyclerViewItemClick(onRecyclerViewItemClick);
    }
}
