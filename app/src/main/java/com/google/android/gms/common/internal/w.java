package com.google.android.gms.common.internal;

import android.app.Activity;
import android.content.ActivityNotFoundException;
import android.content.DialogInterface;
import android.content.Intent;
import android.support.v4.app.Fragment;
import android.util.Log;

/* JADX INFO: loaded from: classes.dex */
public abstract class w implements DialogInterface.OnClickListener {
    public static w a(Activity activity, Intent intent, int i) {
        return new bm(intent, activity, i);
    }

    public static w a(Fragment fragment, Intent intent, int i) {
        return new bn(intent, fragment, i);
    }

    public static w a(com.google.android.gms.common.api.a.u uVar, Intent intent, int i) {
        return new bo(intent, uVar, i);
    }

    protected abstract void aap();

    @Override // android.content.DialogInterface.OnClickListener
    public void onClick(DialogInterface dialogInterface, int i) {
        try {
            aap();
        } catch (ActivityNotFoundException e) {
            Log.e("DialogRedirect", "Failed to start resolution intent", e);
        } finally {
            dialogInterface.dismiss();
        }
    }
}
