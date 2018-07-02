package vn.app.base.util;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;

import vn.app.base.R;


public class FragmentUtil {

    public static void popBackStack(FragmentActivity activity) {
        if (activity == null) {
            return;
        }
        activity.getSupportFragmentManager().popBackStack();
    }

    public static void popAllBackStack(FragmentActivity activity) {
        if (activity == null) {
            return;
        }
        final FragmentManager fm = activity.getSupportFragmentManager();
        final int backStackCount = fm.getBackStackEntryCount();
        for (int i = 0; i < backStackCount; i++) {
            fm.popBackStack();
        }
    }

    public static void pushFragment(FragmentManager fragmentManager, @NonNull Fragment fragment, @Nullable Bundle data) {
        DebugLog.e("bundle data:" + data);
        showFragment(fragmentManager, fragment, true, data, null, false);
    }

    public static void replaceFragment(FragmentManager fragmentManager, @NonNull Fragment fragment, @Nullable Bundle data) {
        showFragment(fragmentManager, fragment, false, data, null, false);
    }

    public static void pushFragment(FragmentActivity activity, @NonNull Fragment fragment, @Nullable Bundle data) {
        DebugLog.e("bundle data:" + data);
        showFragment(activity, fragment, true, data, null, false);
    }

    public static void pushFragment(FragmentActivity activity, @NonNull Fragment fragment, @Nullable Bundle data, @Nullable String tag) {
        showFragment(activity, fragment, true, data, tag, false);
    }

    public static void replaceFragment(FragmentActivity activity, @NonNull Fragment fragment, @Nullable Bundle data) {
        showFragment(activity, fragment, false, data, null, false);
    }

    public static void replaceFragment(FragmentActivity activity, @NonNull Fragment fragment, @Nullable Bundle data, @Nullable String tag) {
        showFragment(activity, fragment, false, data, tag, false);
    }

    public static void pushFragmentWithAnimation(FragmentActivity activity, @NonNull Fragment fragment, @Nullable Bundle data) {
        DebugLog.e("bundle data:" + data);
        showFragment(activity, fragment, true, data, null, true);
    }

    public static void replaceFragmentAnimation(FragmentActivity activity, @NonNull Fragment fragment, @Nullable Bundle data) {
        showFragment(activity, fragment, false, data, null, true);
    }

    public static void showFragment(FragmentActivity activity, @NonNull Fragment fragment, boolean isPushInsteadOfReplace, @Nullable Bundle data, @Nullable String tag, boolean isShowAnimation) {
        if (activity == null) {
            return;
        }

        if (data != null) {
            fragment.setArguments(data);
        }

        FragmentTransaction fragmentTransaction = activity.getSupportFragmentManager().beginTransaction();

        if (isShowAnimation) {
            fragmentTransaction.setCustomAnimations(R.anim.slide_in_up,
                    R.anim.slide_out_up, R.anim.slide_in_up, R.anim.slide_out_up);
        }

        fragmentTransaction.replace(R.id.container, fragment, tag);
        if (isPushInsteadOfReplace) {
            fragmentTransaction.addToBackStack(null);
        }

        fragmentTransaction.setTransition(FragmentTransaction.TRANSIT_NONE);
        fragmentTransaction.commitAllowingStateLoss();
    }

    public static void showFragment(FragmentManager fragmentManager, @NonNull Fragment fragment, boolean isPushInsteadOfReplace, @Nullable Bundle data, @Nullable String tag, boolean isShowAnimation) {
        if (fragmentManager == null) {
            return;
        }

        if (data != null) {
            fragment.setArguments(data);
        }

        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();

        if (isShowAnimation) {
            fragmentTransaction.setCustomAnimations(R.anim.slide_in_up,
                    R.anim.slide_out_up, R.anim.slide_in_up, R.anim.slide_out_up);
        }

        fragmentTransaction.replace(R.id.container, fragment, tag);
        if (isPushInsteadOfReplace) {
            fragmentTransaction.addToBackStack(null);
        }

        fragmentTransaction.setTransition(FragmentTransaction.TRANSIT_NONE);
        fragmentTransaction.commit();
    }

    public static void addFragment(FragmentActivity activity, @NonNull Fragment fragment, boolean isAddToBackStack, @Nullable String tag) {
        if (activity == null) {
            return;
        }


        FragmentTransaction fragmentTransaction = activity.getSupportFragmentManager().beginTransaction();

        fragmentTransaction.add(R.id.container, fragment, tag);

        if (isAddToBackStack) {
            fragmentTransaction.addToBackStack(null);
        }

        fragmentTransaction.setTransition(FragmentTransaction.TRANSIT_NONE);
        fragmentTransaction.commit();
    }

    public static void showFragmentWithAnimation(FragmentActivity activity, @NonNull Fragment fragment, boolean isPushInsteadOfReplace, @Nullable Bundle data, @Nullable String tag, int enterAnim, int exitAnim) {
        if (activity == null) {
            return;
        }

        if (data != null) {
            fragment.setArguments(data);
        }

        FragmentTransaction fragmentTransaction = activity.getSupportFragmentManager().beginTransaction();

        fragmentTransaction.setCustomAnimations(enterAnim,
                exitAnim, enterAnim, exitAnim);

        fragmentTransaction.replace(R.id.container, fragment, tag);
        if (isPushInsteadOfReplace) {
            fragmentTransaction.addToBackStack(null);
        }

        fragmentTransaction.setTransition(FragmentTransaction.TRANSIT_NONE);
        fragmentTransaction.commit();
    }

    public static Fragment getCurrentFragmentByTag(FragmentActivity activity, String tag) {
        return getCurrentFragmentByTag(activity.getSupportFragmentManager(), tag);
    }

    public static Fragment getCurrentFragmentByTag(FragmentManager fragmentManager, String tag) {
        return fragmentManager.findFragmentByTag(tag);
    }

}
