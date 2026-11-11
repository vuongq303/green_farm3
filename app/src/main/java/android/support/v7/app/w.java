package android.support.v7.app;

import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.content.res.Configuration;
import android.os.Build;
import android.os.Bundle;
import android.view.MenuInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;

/* JADX INFO: compiled from: AppCompatDelegate.java */
/* JADX INFO: loaded from: classes.dex */
public abstract class w {
    private static int kM = -1;
    private static boolean kN = false;

    public abstract void addContentView(View view, ViewGroup.LayoutParams layoutParams);

    public abstract boolean bA();

    public abstract a bv();

    public abstract void bz();

    public abstract <T extends View> T findViewById(int i);

    public abstract MenuInflater getMenuInflater();

    public abstract void invalidateOptionsMenu();

    public abstract void onConfigurationChanged(Configuration configuration);

    public abstract void onCreate(Bundle bundle);

    public abstract void onDestroy();

    public abstract void onPostCreate(Bundle bundle);

    public abstract void onPostResume();

    public abstract void onSaveInstanceState(Bundle bundle);

    public abstract void onStart();

    public abstract void onStop();

    public abstract boolean requestWindowFeature(int i);

    public abstract void setContentView(int i);

    public abstract void setContentView(View view);

    public abstract void setContentView(View view, ViewGroup.LayoutParams layoutParams);

    public abstract void setTitle(CharSequence charSequence);

    public static w a(Activity activity, v vVar) {
        return a(activity, activity.getWindow(), vVar);
    }

    public static w a(Dialog dialog, v vVar) {
        return a(dialog.getContext(), dialog.getWindow(), vVar);
    }

    private static w a(Context context, Window window, v vVar) {
        if (Build.VERSION.SDK_INT >= 24) {
            return new aa(context, window, vVar);
        }
        if (Build.VERSION.SDK_INT >= 23) {
            return new ah(context, window, vVar);
        }
        if (Build.VERSION.SDK_INT >= 14) {
            return new ad(context, window, vVar);
        }
        if (Build.VERSION.SDK_INT >= 11) {
            return new ac(context, window, vVar);
        }
        return new aj(context, window, vVar);
    }

    w() {
    }

    public static int bB() {
        return kM;
    }

    public static boolean bC() {
        return kN;
    }
}
