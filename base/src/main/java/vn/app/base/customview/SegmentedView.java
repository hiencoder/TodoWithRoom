package vn.app.base.customview;

import android.content.Context;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.LinearLayout;

public class SegmentedView extends LinearLayout implements OnClickListener {

    /**
     * Number of tab in segment, min is 2
     */
    int numberTab;

    /**
     * current select tab
     */
    int selectTab = 0;

    View selectedView;

    View[] tabs;

    SegmentedViewOnSelect segmentedViewOnSelect;

    public SegmentedView(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        // TODO Auto-generated constructor stub
        initView(context);
        Log.e("SegmentedView", "segmentedViewOnSelect" + segmentedViewOnSelect);
    }

    public SegmentedView(Context context, AttributeSet attrs) {
        super(context, attrs);
        // TODO Auto-generated constructor stub
        initView(context);
    }

    public SegmentedView(Context context) {
        super(context);
        // TODO Auto-generated constructor stub
        initView(context);
    }

    private void initView(Context context) {
        // TODO init data
    }

    @Override
    protected void onFinishInflate() {
        // TODO Auto-generated method stub
        numberTab = getChildCount();

        if (numberTab > 0) {

            // Collect children declared in XML.
            tabs = new View[numberTab];
            int index = numberTab;

            for (int i = index - 1; i >= 0; i--) {
                View view = getChildAt(i);
                tabs[i] = view;
                view.setTag(i);
                view.setOnClickListener(this);

            }

            tabs[selectTab].setSelected(true);

            selectedView = tabs[selectTab];

        }

        super.onFinishInflate();
    }

    @Override
    public void onClick(View v) {
        // TODO Auto-generated method stub
        // DebugLog.e(v.getId() + "");
        if (selectedView != null) {
            selectedView.setSelected(false);
        }
        selectedView = v;
        selectedView.setSelected(true);

        if (segmentedViewOnSelect != null) {
            int tag = Integer.parseInt(v.getTag().toString());
            // segmentedViewOnSelect.onSegmentedViewSelect((int)v.getTag());
            segmentedViewOnSelect.onSegmentedViewSelect(tag);
            selectTab = tag;
        }
    }

    public void setSelectedTab(int position) {
        if (tabs.length > position) {
            if (selectedView != null) {
                selectedView.setSelected(false);
            }
            tabs[position].setSelected(true);
            selectedView = tabs[position];
            selectTab = position;
        } else {
            Log.e("SegmentedView", "setSelectedTab position out of range range:" + tabs.length);
        }
    }

    public void setSegmentedViewOnSelect(SegmentedViewOnSelect segmentedViewOnSelect) {
        this.segmentedViewOnSelect = segmentedViewOnSelect;
    }

    public interface SegmentedViewOnSelect {
        void onSegmentedViewSelect(int position);
    }

    public int getSelectTab() {
        return selectTab;
    }

    public void setSelectTab(int selectTab) {
        this.selectTab = selectTab;
    }
}
