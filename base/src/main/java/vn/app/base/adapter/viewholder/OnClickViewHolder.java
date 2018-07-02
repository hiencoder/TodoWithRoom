package vn.app.base.adapter.viewholder;

import android.support.v7.widget.RecyclerView;
import android.view.View;

import butterknife.ButterKnife;
import vn.app.base.callback.OnRecyclerViewItemClick;

/**
 * Created on 8/14/2015.
 */
public class OnClickViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

    OnRecyclerViewItemClick onRecyclerViewItemClick;

    public void setOnRecyclerViewItemClick(OnRecyclerViewItemClick onRecyclerViewItemClick) {
        this.onRecyclerViewItemClick = onRecyclerViewItemClick;
    }

    public OnClickViewHolder(View itemView) {
        super(itemView);
        ButterKnife.bind(this, itemView);
        itemView.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        if (onRecyclerViewItemClick != null) {
            onRecyclerViewItemClick.onItemClick(view, getAdapterPosition());
        }
    }
}
