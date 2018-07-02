package vn.app.base;

import android.app.Application;

import vn.app.base.util.NetworkUtils;
import vn.app.base.util.SharedPrefUtils;


public class BaseApplication extends Application {

    private static BaseApplication instance;
    private static SharedPrefUtils sharedPreferences;

    public BaseApplication() {
        // TODO Auto-generated constructor stub
        instance = this;
    }

    public static BaseApplication getInstance() {
        return instance;
    }

    @Override
    public void onCreate() {
        // TODO Auto-generated method stub
        super.onCreate();
        sharedPreferences = new SharedPrefUtils(getApplicationContext());
        NetworkUtils.getInstance(this);
    }

    public static SharedPrefUtils getSharedPreferences() {
        return sharedPreferences;
    }
}
