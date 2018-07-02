package vn.app.base.activity;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;

import vn.app.base.callback.CommonListener;
import vn.app.base.callback.FragmentListener;
import vn.app.base.fragment.CommonFragment;
import vn.app.base.util.FragmentUtil;


public abstract class CommonActivity extends BaseActivity implements CommonListener {

    protected FragmentListener fragmentListener;

    public void setFragmentListener(FragmentListener fragmentListener) {
        this.fragmentListener = fragmentListener;
    }

    /**
     * Clear all fragment backstack
     */
    public void popEntireFragmentBackStack() {
        final FragmentManager fm = getSupportFragmentManager();
        final int backStackCount = fm.getBackStackEntryCount();
        for (int i = 0; i < backStackCount; i++) {
            fm.popBackStack();
        }
    }

    protected void setUpInitScreen(CommonFragment fragment, String tag) {
        FragmentUtil.addFragment(this, fragment, false, tag);
    }

    @Override
    public void onAttachFragment(Fragment fragment) {
        super.onAttachFragment(fragment);
    }

    @Override
    protected String getNoConnectionMessage() {
        return "";
    }

    @Override
    protected String getErrorAPIMessage() {
        return "";
    }

    @Override
    protected String getTimeOutMessage() {
        return "";
    }

}
