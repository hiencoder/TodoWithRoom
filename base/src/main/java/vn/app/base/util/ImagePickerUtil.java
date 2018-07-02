package vn.app.base.util;

import android.app.Activity;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.content.pm.ResolveInfo;
import android.net.Uri;
import android.os.Parcelable;
import android.provider.MediaStore;
import android.support.v4.content.ContextCompat;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

/**
 * Created on 7/28/2016.
 */
public class ImagePickerUtil {

    public static final int PICTURE_PICKER_REQUEST_CODE = 6969;

    public static final int PICTURE_CROP_REQUEST_CODE = 9696;

    public Uri outputFileUri;

    public void createImageFile(Context context) {

        outputFileUri = Uri.fromFile(createFileUri(context));

        DebugLog.i("outputFileUri: " + outputFileUri.toString());

    }

    public void pickImage(Activity activity, boolean isPickFromCamera) {

        List<Intent> cameraIntents = null;

        if (isPickFromCamera) {

            createImageFile(activity);

            // Camera.
            cameraIntents = new ArrayList<>();
            final Intent captureIntent = new Intent(android.provider.MediaStore.ACTION_IMAGE_CAPTURE);
            final PackageManager packageManager = activity.getPackageManager();
            final List<ResolveInfo> listCam = packageManager.queryIntentActivities(captureIntent, 0);
            for (ResolveInfo res : listCam) {
                final String packageName = res.activityInfo.packageName;
                final Intent intent = new Intent(captureIntent);
                intent.setComponent(new ComponentName(res.activityInfo.packageName, res.activityInfo.name));
                intent.setPackage(packageName);
                intent.putExtra(MediaStore.EXTRA_OUTPUT, outputFileUri);
                cameraIntents.add(intent);
            }
        }

        // Filesystem.
        final Intent galleryIntent = new Intent();
        galleryIntent.setType("image/*");
        galleryIntent.putExtra(Intent.EXTRA_LOCAL_ONLY, true);
        galleryIntent.setAction(Intent.ACTION_GET_CONTENT);


        // Chooser of filesystem options.
        final Intent chooserIntent = Intent.createChooser(galleryIntent, "Select Source");

        if (isPickFromCamera) {
            // Add the camera options.
            chooserIntent.putExtra(Intent.EXTRA_INITIAL_INTENTS, cameraIntents.toArray(new Parcelable[cameraIntents.size()]));
        }

        activity.startActivityForResult(chooserIntent, PICTURE_PICKER_REQUEST_CODE);
    }

    public File createFileUri(Context context) {
        // Determine Uri of camera image to save.

        File[] externalFile = ContextCompat.getExternalFilesDirs(context, null);

        if (externalFile == null) {
            externalFile = new File[]{context.getExternalFilesDir(null)};
        }

        final File root = new File(externalFile[0] + File.separator + "LeClub" + File.separator);

        root.mkdirs();
        final String fname = "LeClub_Photo.jpg";
        final File sdImageMainDirectory = new File(root, fname);
        if (sdImageMainDirectory.exists()) {
            sdImageMainDirectory.delete();
        }
        return sdImageMainDirectory;
    }

    public void handleResult(int requestCode, int resultCode, Intent data) {
        DebugLog.i("requestCode:" + requestCode + "  |  resultCode: " + resultCode);

        if (resultCode == Activity.RESULT_OK) {
            if (requestCode == PICTURE_PICKER_REQUEST_CODE) {
                final boolean isCamera;
                if (data == null) {
                    isCamera = true;
                } else {
                    final String action = data.getAction();
                    isCamera = action != null && action.equals(MediaStore.ACTION_IMAGE_CAPTURE);
                }

                DebugLog.i("isCamera:" + isCamera);

                if (!isCamera) {
                    outputFileUri = data.getData();
                }

                if (outputFileUri != null) {
                    DebugLog.i("outputFileUri: " + outputFileUri.toString());
                }
            }
        }
    }
}
