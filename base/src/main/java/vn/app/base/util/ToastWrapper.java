package vn.app.base.util;

import android.app.Activity;
import android.widget.Toast;

/**
 * Created by QuangBoo on 12/27/2015.
 */
public class ToastWrapper {

    public static void makeText(Activity activity, String content, int lenght) {
        Toast.makeText(activity, content, lenght).show();
    }
}
