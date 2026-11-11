package com.gameloft.glads;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Build;
import android.os.Environment;
import android.provider.MediaStore;
import android.webkit.ValueCallback;
import android.webkit.WebChromeClient;
import com.facebook.share.internal.ShareConstants;
import com.gameloft.android.wrapper.au;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileOutputStream;

/* JADX INFO: loaded from: classes.dex */
class WebViewFileChooser {
    public static final int CHOOSE_FILE_ACTION = 143592;
    public static final int TAKE_PICTURE_ACTION = 143591;
    private static String IMAGE_TYPE = "image/*";
    private static ValueCallback<Uri[]> s_filePathCallback = null;

    WebViewFileChooser() {
    }

    static boolean ShowFileChooser(ValueCallback<Uri[]> valueCallback, WebChromeClient.FileChooserParams fileChooserParams) {
        if (s_filePathCallback != null) {
            s_filePathCallback.onReceiveValue(null);
        }
        s_filePathCallback = valueCallback;
        ShowSelectionMenu();
        return true;
    }

    private static void ShowSelectionMenu() {
        AlertDialog.Builder builder = new AlertDialog.Builder(AndroidUtils.GetActivity());
        builder.setTitle("Add Photo!");
        builder.setItems(new CharSequence[]{"Take Photo", "Choose from Library"}, new DialogInterface.OnClickListener() { // from class: com.gameloft.glads.WebViewFileChooser.1
            @Override // android.content.DialogInterface.OnClickListener
            public void onClick(DialogInterface dialogInterface, int i) {
                switch (i) {
                    case 0:
                        WebViewFileChooser.TakePicture();
                        break;
                    case 1:
                        WebViewFileChooser.FilePicker();
                        break;
                }
            }
        });
        builder.setOnCancelListener(new DialogInterface.OnCancelListener() { // from class: com.gameloft.glads.WebViewFileChooser.2
            @Override // android.content.DialogInterface.OnCancelListener
            public void onCancel(DialogInterface dialogInterface) {
                WebViewFileChooser.Close(null);
            }
        });
        builder.show();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static void TakePicture() {
        try {
            Activity activity = au.getActivity();
            Intent intent = new Intent("android.media.action.IMAGE_CAPTURE");
            if (intent.resolveActivity(activity.getPackageManager()) != null) {
                activity.startActivityForResult(intent, TAKE_PICTURE_ACTION);
            } else {
                Close(null);
            }
        } catch (Exception e) {
            Close(null);
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static void FilePicker() {
        try {
            Intent intent = new Intent();
            intent.setType(IMAGE_TYPE);
            intent.setAction("android.intent.action.GET_CONTENT");
            AndroidUtils.GetActivity().startActivityForResult(Intent.createChooser(intent, "Select File"), CHOOSE_FILE_ACTION);
        } catch (Exception e) {
            Close(null);
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static void Close(Uri[] uriArr) {
        if (s_filePathCallback != null) {
            s_filePathCallback.onReceiveValue(uriArr);
        }
        s_filePathCallback = null;
    }

    static boolean onActivityResult(int i, int i2, Intent intent) {
        switch (i) {
            case TAKE_PICTURE_ACTION /* 143591 */:
                HandleTakePictureResponse(i, i2, intent);
                return true;
            case CHOOSE_FILE_ACTION /* 143592 */:
                HandleFilePickerResponse(i, i2, intent);
                return true;
            default:
                return false;
        }
    }

    private static void HandleTakePictureResponse(int i, int i2, Intent intent) {
        Uri[] uriArr;
        if (i2 != -1) {
            Close(null);
            return;
        }
        try {
            if (CanTakePhotoFromBuffer()) {
                Bitmap bitmap = (Bitmap) intent.getExtras().get(ShareConstants.WEB_DIALOG_PARAM_DATA);
                ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
                bitmap.compress(Bitmap.CompressFormat.PNG, 0, byteArrayOutputStream);
                byte[] byteArray = byteArrayOutputStream.toByteArray();
                String str = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DCIM) + "GL" + System.currentTimeMillis() + ".png";
                File file = new File(str);
                file.createNewFile();
                FileOutputStream fileOutputStream = new FileOutputStream(file);
                fileOutputStream.write(byteArray);
                fileOutputStream.flush();
                uriArr = new Uri[]{Uri.fromFile(new File(str))};
            } else {
                Cursor cursorManagedQuery = AndroidUtils.GetActivity().managedQuery(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, new String[]{"_id", "_data"}, null, null, "_id DESC");
                cursorManagedQuery.moveToFirst();
                while (true) {
                    String string = cursorManagedQuery.getString(cursorManagedQuery.getColumnIndex("_data"));
                    if (string != null) {
                        uriArr = new Uri[]{Uri.fromFile(new File(string))};
                        break;
                    } else if (!cursorManagedQuery.moveToNext()) {
                        uriArr = null;
                        break;
                    }
                }
            }
        } catch (Exception e) {
            uriArr = null;
        }
        Close(uriArr);
    }

    private static void HandleFilePickerResponse(int i, int i2, Intent intent) {
        Uri[] uriArr;
        if (i2 != -1) {
            Close(null);
            return;
        }
        try {
            uriArr = new Uri[]{Uri.parse(intent.getDataString())};
        } catch (Exception e) {
            uriArr = null;
        }
        Close(uriArr);
    }

    private static boolean CanTakePhotoFromBuffer() {
        String lowerCase = Build.MODEL.toLowerCase();
        return lowerCase.contains("nexus") || lowerCase.contains("asus") || lowerCase.contains("sm-g928f");
    }
}
