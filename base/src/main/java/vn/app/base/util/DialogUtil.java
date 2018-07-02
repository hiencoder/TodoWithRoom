package vn.app.base.util;

import android.content.Context;
import android.content.DialogInterface;
import android.support.v7.app.AlertDialog;

import vn.app.base.R;


public class DialogUtil {

    public static AlertDialog createCloseBtnDialog(Context context, String title, String message) {
        AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(
                context, R.style.MyAlertDialogStyle);

        if (title != null && !title.isEmpty()) {
            alertDialogBuilder.setTitle(title);
        }

        alertDialogBuilder.setMessage(message);

        alertDialogBuilder.setPositiveButton(R.string.dialog_btn_close,
                new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                    }
                });

        alertDialogBuilder.setCancelable(false);
        return alertDialogBuilder.create();
    }


    public static AlertDialog showOkBtnDialog(final Context context, String title, String message) {
        AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(
                context, R.style.MyAlertDialogStyle);

        if (title != null && !title.isEmpty()) {
            alertDialogBuilder.setTitle(title);
        }

        alertDialogBuilder.setMessage(message);

        alertDialogBuilder.setPositiveButton(R.string.dialog_btn_ok,
                new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                    }
                });

        alertDialogBuilder.setCancelable(false);
        AlertDialog alertDialog = alertDialogBuilder.create();
        alertDialog.show();
        return alertDialog;
    }


    public static AlertDialog createOneBtnWithHandleDialog(final Context context, String title, String message, String okBtnText, DialogInterface.OnClickListener onClickListener) {
        AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(
                context, R.style.MyAlertDialogStyle);

        if (title != null && !title.isEmpty()) {
            alertDialogBuilder.setTitle(title);
        }

        alertDialogBuilder.setMessage(message);

        alertDialogBuilder.setPositiveButton(okBtnText,
                onClickListener);

        alertDialogBuilder.setCancelable(false);
        return alertDialogBuilder.create();
    }


    public static AlertDialog showTwoBtnCancelableDialog(final Context context, String title, String message, DialogInterface.OnClickListener onClickListener) {
        AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(
                context, R.style.MyAlertDialogStyle);

        if (title != null && !title.isEmpty()) {
            alertDialogBuilder.setTitle(title);
        }

        alertDialogBuilder.setMessage(message);

        alertDialogBuilder.setPositiveButton(R.string.dialog_btn_ok,
                onClickListener);

        alertDialogBuilder.setNegativeButton(R.string.dialog_btn_cancel, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                dialogInterface.dismiss();
            }
        });

        alertDialogBuilder.setCancelable(false);
        AlertDialog alertDialog = alertDialogBuilder.create();
        alertDialog.show();
        return alertDialog;
    }

    public static AlertDialog showTwoBtnWithHandleDialog(Context context, String title, String message, String okText, String cancelText, DialogInterface.OnClickListener okOnClickListener, DialogInterface.OnClickListener cancelOnClickListener) {
        AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(
                context, R.style.MyAlertDialogStyle);

        if (title != null && !title.isEmpty()) {
            alertDialogBuilder.setTitle(title);
        }

        alertDialogBuilder.setMessage(message);

        alertDialogBuilder.setPositiveButton(okText, okOnClickListener);

        alertDialogBuilder.setNegativeButton(cancelText, cancelOnClickListener);

        alertDialogBuilder.setCancelable(false);
        AlertDialog alertDialog = alertDialogBuilder.create();
        alertDialog.show();
        return alertDialog;
    }

    public static AlertDialog showApiErrorDialogDialog(final Context context) {
        return showOkBtnDialog(context, null, context.getString(R.string.dialog_error_api));
    }

    public static AlertDialog createApiErrorDialog(final Context context, String message, DialogInterface.OnClickListener onClickListener) {
        return createOneBtnWithHandleDialog(context, null, message, context.getString(R.string.dialog_btn_ok), onClickListener);
    }

}
