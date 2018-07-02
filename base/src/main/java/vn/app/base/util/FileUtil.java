package vn.app.base.util;

import android.content.Context;
import android.support.v4.content.ContextCompat;

import java.io.File;

/**
 * Created by QuangBoo on 12/19/2015.
 */
public class FileUtil {

    public File createFile(String folderName, String fileName, Context context) {
        if (fileName == null) {
            return null;
        }
        File[] externalFile = ContextCompat.getExternalFilesDirs(context, null);

        if (externalFile == null) {
            externalFile = new File[]{context.getExternalFilesDir(null)};
        }

        final File root = new File(externalFile[0] + File.separator + folderName + File.separator);

        root.mkdirs();
        DebugLog.i("fileName: " + fileName);
        final File sdImageMainDirectory = new File(root, fileName);
        if (sdImageMainDirectory.exists()) {
            sdImageMainDirectory.delete();
        }
        return sdImageMainDirectory;

    }
}
