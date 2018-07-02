package vn.app.base.fragment;

import android.content.Context;
import android.os.Bundle;
import android.view.View;

import vn.app.base.activity.CommonActivity;
import vn.app.base.callback.CommonListener;
import vn.app.base.callback.FragmentListener;

public abstract class CommonFragment extends BaseFragment implements FragmentListener {

    protected CommonListener commonListener;

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof CommonListener) {
            commonListener = (CommonListener) context;
        } else {
            throw new RuntimeException(context.toString()
                    + " must implement CommonListener");
        }
        if (context instanceof CommonActivity) {
            ((CommonActivity) context).setFragmentListener(this);
        }
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        if (getActivity() instanceof CommonActivity) {
            ((CommonActivity) getActivity()).setFragmentListener(this);
        }
        super.onViewCreated(view, savedInstanceState);
    }

    @Override
    public void onDetach() {
        if (getActivity() instanceof CommonActivity) {
            ((CommonActivity) getActivity()).setFragmentListener(null);
        }
        super.onDetach();
        commonListener = null;
    }

    public String getScreenTitle() {
        return "";
    }

    @Override
    public void onFragmentDataHandle(Bundle bundle) {

    }

    @Override
    public void onFragmentUIHandle(Bundle bundle) {

    }
}
