package com.google.android.gms.common;

import android.R;
import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.res.Resources;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.bu;
import android.support.v4.app.bv;
import android.util.Log;
import android.util.TypedValue;
import android.widget.ProgressBar;
import com.google.android.gms.common.api.GoogleApiActivity;
import com.google.android.gms.common.internal.bf;

/* JADX INFO: loaded from: classes.dex */
public class e extends g {
    private String zzat;
    private static final Object mLock = new Object();
    private static final e bId = new e();
    public static final int GOOGLE_PLAY_SERVICES_VERSION_CODE = g.GOOGLE_PLAY_SERVICES_VERSION_CODE;

    e() {
    }

    public static e YB() {
        return bId;
    }

    private final String YC() {
        String str;
        synchronized (mLock) {
            str = this.zzat;
        }
        return str;
    }

    static Dialog a(Context context, int i, com.google.android.gms.common.internal.w wVar, DialogInterface.OnCancelListener onCancelListener) {
        if (i == 0) {
            return null;
        }
        TypedValue typedValue = new TypedValue();
        context.getTheme().resolveAttribute(R.attr.alertDialogTheme, typedValue, true);
        AlertDialog.Builder builder = "Theme.Dialog.Alert".equals(context.getResources().getResourceEntryName(typedValue.resourceId)) ? new AlertDialog.Builder(context, 5) : null;
        if (builder == null) {
            builder = new AlertDialog.Builder(context);
        }
        builder.setMessage(com.google.android.gms.common.internal.u.B(context, i));
        if (onCancelListener != null) {
            builder.setOnCancelListener(onCancelListener);
        }
        String strD = com.google.android.gms.common.internal.u.D(context, i);
        if (strD != null) {
            builder.setPositiveButton(strD, wVar);
        }
        String strZ = com.google.android.gms.common.internal.u.z(context, i);
        if (strZ != null) {
            builder.setTitle(strZ);
        }
        return builder.create();
    }

    private final String a(Context context, NotificationManager notificationManager) {
        bf.bZ(com.google.android.gms.common.d.s.ax());
        String strYC = YC();
        if (strYC == null) {
            strYC = "com.google.android.gms.availability";
            NotificationChannel notificationChannel = notificationManager.getNotificationChannel("com.google.android.gms.availability");
            String strAG = com.google.android.gms.common.internal.u.aG(context);
            if (notificationChannel == null) {
                notificationManager.createNotificationChannel(new NotificationChannel("com.google.android.gms.availability", strAG, 4));
            } else if (!strAG.equals(notificationChannel.getName())) {
                notificationChannel.setName(strAG);
                notificationManager.createNotificationChannel(notificationChannel);
            }
        }
        return strYC;
    }

    static void a(Activity activity, Dialog dialog, String str, DialogInterface.OnCancelListener onCancelListener) {
        if (activity instanceof FragmentActivity) {
            q.b(dialog, onCancelListener).show(((FragmentActivity) activity).getSupportFragmentManager(), str);
        } else {
            c.a(dialog, onCancelListener).show(activity.getFragmentManager(), str);
        }
    }

    private final void a(Context context, int i, String str, PendingIntent pendingIntent) {
        Notification notificationBuild;
        int i2;
        if (i == 18) {
            zza(context);
            return;
        }
        if (pendingIntent == null) {
            if (i == 6) {
                Log.w("GoogleApiAvailability", "Missing resolution for ConnectionResult.RESOLUTION_REQUIRED. Call GoogleApiAvailability#showErrorNotification(Context, ConnectionResult) instead.");
                return;
            }
            return;
        }
        String strA = com.google.android.gms.common.internal.u.A(context, i);
        String strC = com.google.android.gms.common.internal.u.C(context, i);
        Resources resources = context.getResources();
        NotificationManager notificationManager = (NotificationManager) context.getSystemService("notification");
        if (com.google.android.gms.common.d.i.aM(context)) {
            bf.bZ(com.google.android.gms.common.d.s.abB());
            Notification.Builder style = new Notification.Builder(context).setSmallIcon(context.getApplicationInfo().icon).setPriority(2).setAutoCancel(true).setContentTitle(strA).setStyle(new Notification.BigTextStyle().bigText(strC));
            if (com.google.android.gms.common.d.i.aN(context)) {
                style.addAction(com.google.android.gms.b.b.bHA, resources.getString(com.google.android.gms.b.c.bHP), pendingIntent);
            } else {
                style.setContentIntent(pendingIntent);
            }
            if (com.google.android.gms.common.d.s.ax() && com.google.android.gms.common.d.s.ax()) {
                style.setChannelId(a(context, notificationManager));
            }
            notificationBuild = style.build();
        } else {
            bv bvVarA = new bv(context).l(R.drawable.stat_sys_warning).d(resources.getString(com.google.android.gms.b.c.bHI)).c(System.currentTimeMillis()).d(true).a(pendingIntent).b(strA).c(strC).e(true).a(new bu().a(strC));
            if (com.google.android.gms.common.d.s.ax() && com.google.android.gms.common.d.s.ax()) {
                bvVarA.i(a(context, notificationManager));
            }
            notificationBuild = bvVarA.build();
        }
        switch (i) {
            case 1:
            case 2:
            case 3:
                i2 = 10436;
                m.zzbt.set(false);
                break;
            default:
                i2 = 39789;
                break;
        }
        if (str == null) {
            notificationManager.notify(i2, notificationBuild);
        } else {
            notificationManager.notify(str, i2, notificationBuild);
        }
    }

    public Dialog a(Activity activity, int i, int i2, DialogInterface.OnCancelListener onCancelListener) {
        return a(activity, i, com.google.android.gms.common.internal.w.a(activity, b(activity, i, "d"), i2), onCancelListener);
    }

    public Dialog a(Activity activity, DialogInterface.OnCancelListener onCancelListener) {
        ProgressBar progressBar = new ProgressBar(activity, null, R.attr.progressBarStyleLarge);
        progressBar.setIndeterminate(true);
        progressBar.setVisibility(0);
        AlertDialog.Builder builder = new AlertDialog.Builder(activity);
        builder.setView(progressBar);
        builder.setMessage(com.google.android.gms.common.internal.u.B(activity, 18));
        builder.setPositiveButton("", (DialogInterface.OnClickListener) null);
        AlertDialog alertDialogCreate = builder.create();
        a(activity, alertDialogCreate, "GooglePlayServicesUpdatingDialog", onCancelListener);
        return alertDialogCreate;
    }

    @Override // com.google.android.gms.common.g
    public PendingIntent a(Context context, int i, int i2) {
        return super.a(context, i, i2);
    }

    @Override // com.google.android.gms.common.g
    public PendingIntent a(Context context, int i, int i2, String str) {
        return super.a(context, i, i2, str);
    }

    public PendingIntent a(Context context, ConnectionResult connectionResult) {
        return connectionResult.Yy() ? connectionResult.Yz() : a(context, connectionResult.getErrorCode(), 0);
    }

    public com.google.android.gms.common.api.a.p a(Context context, com.google.android.gms.common.api.a.q qVar) {
        IntentFilter intentFilter = new IntentFilter("android.intent.action.PACKAGE_ADDED");
        intentFilter.addDataScheme("package");
        com.google.android.gms.common.api.a.p pVar = new com.google.android.gms.common.api.a.p(qVar);
        context.registerReceiver(pVar, intentFilter);
        pVar.aD(context);
        if (isUninstalledAppPossiblyUpdating(context, "com.google.android.gms")) {
            return pVar;
        }
        qVar.zzv();
        pVar.unregister();
        return null;
    }

    public void a(Context context, int i, String str) {
        a(context, i, str, a(context, i, 0, "n"));
    }

    public boolean a(Activity activity, com.google.android.gms.common.api.a.u uVar, int i, int i2, DialogInterface.OnCancelListener onCancelListener) {
        Dialog dialogA = a(activity, i, com.google.android.gms.common.internal.w.a(uVar, b(activity, i, "d"), i2), onCancelListener);
        if (dialogA == null) {
            return false;
        }
        a(activity, dialogA, GooglePlayServicesUtil.GMS_ERROR_DIALOG, onCancelListener);
        return true;
    }

    public boolean a(Context context, ConnectionResult connectionResult, int i) {
        PendingIntent pendingIntentA = a(context, connectionResult);
        if (pendingIntentA == null) {
            return false;
        }
        a(context, connectionResult.getErrorCode(), (String) null, GoogleApiActivity.a(context, pendingIntentA, i));
        return true;
    }

    @Override // com.google.android.gms.common.g
    public Intent b(Context context, int i, String str) {
        return super.b(context, i, str);
    }

    public boolean b(Activity activity, int i, int i2, DialogInterface.OnCancelListener onCancelListener) {
        Dialog dialogA = a(activity, i, i2, onCancelListener);
        if (dialogA == null) {
            return false;
        }
        a(activity, dialogA, GooglePlayServicesUtil.GMS_ERROR_DIALOG, onCancelListener);
        return true;
    }

    @Override // com.google.android.gms.common.g
    public int getApkVersion(Context context) {
        return super.getApkVersion(context);
    }

    @Override // com.google.android.gms.common.g
    public final String getErrorString(int i) {
        return super.getErrorString(i);
    }

    @Override // com.google.android.gms.common.g
    public int isGooglePlayServicesAvailable(Context context) {
        return super.isGooglePlayServicesAvailable(context);
    }

    @Override // com.google.android.gms.common.g
    public int isGooglePlayServicesAvailable(Context context, int i) {
        return super.isGooglePlayServicesAvailable(context, i);
    }

    @Override // com.google.android.gms.common.g
    public boolean isPlayServicesPossiblyUpdating(Context context, int i) {
        return super.isPlayServicesPossiblyUpdating(context, i);
    }

    @Override // com.google.android.gms.common.g
    public final boolean ns(int i) {
        return super.ns(i);
    }

    @Override // com.google.android.gms.common.g
    @Deprecated
    public Intent nt(int i) {
        return super.nt(i);
    }

    public void y(Context context, int i) {
        a(context, i, (String) null);
    }

    final void zza(Context context) {
        new f(this, context).sendEmptyMessageDelayed(1, 120000L);
    }
}
