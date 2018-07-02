package vn.app.base.adapter;

import android.support.v7.widget.RecyclerView;

import vn.app.base.adapter.viewholder.OnClickViewHolder;
import vn.app.base.callback.OnRecyclerViewItemClick;


/**
 * Created on 9/21/2015.
 */
public abstract class AdapterWithItemClick<VH extends OnClickViewHolder> extends RecyclerView.Adapter<VH> {

    OnRecyclerViewItemClick onRecyclerViewItemClick;

    public OnRecyclerViewItemClick getOnRecyclerViewItemClick() {
        return onRecyclerViewItemClick;
    }

    public void setOnRecyclerViewItemClick(OnRecyclerViewItemClick onRecyclerViewItemClick) {
        this.onRecyclerViewItemClick = onRecyclerViewItemClick;
    }

    @Override
    public void onBindViewHolder(VH holder, int position) {
        holder.setOnRecyclerViewItemClick(onRecyclerViewItemClick);
    }
}
