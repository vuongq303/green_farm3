package com.facebook.appevents.internal;

import a.f;
import android.app.Activity;
import android.content.ComponentName;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import com.facebook.FacebookSdk;

/* JADX INFO: loaded from: classes.dex */
class SourceApplicationInfo {
    private static final String CALL_APPLICATION_PACKAGE_KEY = "com.facebook.appevents.SourceApplicationInfo.callingApplicationPackage";
    private static final String OPENED_BY_APP_LINK_KEY = "com.facebook.appevents.SourceApplicationInfo.openedByApplink";
    private static final String SOURCE_APPLICATION_HAS_BEEN_SET_BY_THIS_INTENT = "_fbSourceApplicationHasBeenSet";
    private String callingApplicationPackage;
    private boolean openedByAppLink;

    private SourceApplicationInfo(String str, boolean z) {
        this.callingApplicationPackage = str;
        this.openedByAppLink = z;
    }

    public static SourceApplicationInfo getStoredSourceApplicatioInfo() {
        SharedPreferences defaultSharedPreferences = PreferenceManager.getDefaultSharedPreferences(FacebookSdk.getApplicationContext());
        if (defaultSharedPreferences.contains(CALL_APPLICATION_PACKAGE_KEY)) {
            return new SourceApplicationInfo(defaultSharedPreferences.getString(CALL_APPLICATION_PACKAGE_KEY, null), defaultSharedPreferences.getBoolean(OPENED_BY_APP_LINK_KEY, false));
        }
        return null;
    }

    public static void clearSavedSourceApplicationInfoFromDisk() {
        SharedPreferences.Editor editorEdit = PreferenceManager.getDefaultSharedPreferences(FacebookSdk.getApplicationContext()).edit();
        editorEdit.remove(CALL_APPLICATION_PACKAGE_KEY);
        editorEdit.remove(OPENED_BY_APP_LINK_KEY);
        editorEdit.apply();
    }

    public String getCallingApplicationPackage() {
        return this.callingApplicationPackage;
    }

    public boolean isOpenedByAppLink() {
        return this.openedByAppLink;
    }

    public String toString() {
        String str = "Unclassified";
        if (this.openedByAppLink) {
            str = "Applink";
        }
        if (this.callingApplicationPackage != null) {
            return str + "(" + this.callingApplicationPackage + ")";
        }
        return str;
    }

    public void writeSourceApplicationInfoToDisk() {
        SharedPreferences.Editor editorEdit = PreferenceManager.getDefaultSharedPreferences(FacebookSdk.getApplicationContext()).edit();
        editorEdit.putString(CALL_APPLICATION_PACKAGE_KEY, this.callingApplicationPackage);
        editorEdit.putBoolean(OPENED_BY_APP_LINK_KEY, this.openedByAppLink);
        editorEdit.apply();
    }

    public class Factory {
        /* JADX WARN: Code duplicated, block: B:21:0x005a  */
        public static SourceApplicationInfo create(Activity activity) {
            String string;
            boolean z = false;
            String packageName = "";
            ComponentName callingActivity = activity.getCallingActivity();
            if (callingActivity != null) {
                packageName = callingActivity.getPackageName();
                if (packageName.equals(activity.getPackageName())) {
                    return null;
                }
            }
            Intent intent = activity.getIntent();
            if (intent == null || intent.getBooleanExtra(SourceApplicationInfo.SOURCE_APPLICATION_HAS_BEEN_SET_BY_THIS_INTENT, false)) {
                string = packageName;
            } else {
                intent.putExtra(SourceApplicationInfo.SOURCE_APPLICATION_HAS_BEEN_SET_BY_THIS_INTENT, true);
                Bundle bundleE = f.e(intent);
                if (bundleE != null) {
                    Bundle bundle = bundleE.getBundle("referer_app_link");
                    if (bundle != null) {
                        string = bundle.getString("package");
                        z = true;
                    } else {
                        string = packageName;
                        z = true;
                    }
                } else {
                    string = packageName;
                }
            }
            if (intent != null) {
                intent.putExtra(SourceApplicationInfo.SOURCE_APPLICATION_HAS_BEEN_SET_BY_THIS_INTENT, true);
            }
            return new SourceApplicationInfo(string, z);
        }
    }
}
