package vn.app.base.imageloader;

import android.content.Context;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.widget.ImageView;

import com.bumptech.glide.Glide;

/**
 * Created on 7/19/2016.
 */
public class ImageLoader {

    public static void loadImage(FragmentActivity activity, String url, ImageView imageView) {
        Glide.with(activity).load(url).into(imageView);
    }

    public static void loadImageCropCenter(FragmentActivity activity, String url, ImageView imageView) {
        Glide.with(activity).load(url).dontAnimate().centerCrop().into(imageView);
    }

    public static void loadImage(Fragment fragment, String url, ImageView imageView) {
        Glide.with(fragment).load(url).dontAnimate().into(imageView);
    }

    public static void loadImage(Fragment fragment, int placeHolder, String url, ImageView imageView) {
        Glide.with(fragment).load(url).placeholder(placeHolder).dontAnimate().into(imageView);
    }

    public static void loadImageResize(Fragment fragment, String url, ImageView imageView, int w, int h) {
        Glide.with(fragment).load(url).override(w, h).into(imageView);
    }

    public static void loadImageCropCenter(Fragment fragment, String url, ImageView imageView) {
        Glide.with(fragment).load(url).centerCrop().into(imageView);
    }

    public static void loadImage(Context context, String url, ImageView imageView) {
        Glide.with(context).load(url).dontAnimate().into(imageView);
    }

    public static void loadImage(Context context, int placeHolder, String url, ImageView imageView) {
        Glide.with(context).load(url).placeholder(placeHolder).dontAnimate().into(imageView);
    }

    public static void loadImageCropCenter(Context context, String url, ImageView imageView) {
        Glide.with(context).load(url).centerCrop().into(imageView);
    }
}
