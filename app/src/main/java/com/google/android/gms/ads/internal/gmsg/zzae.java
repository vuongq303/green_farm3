package com.google.android.gms.ads.internal.gmsg;

import android.app.ActivityManager;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.content.pm.ResolveInfo;
import android.net.Uri;
import android.text.TextUtils;
import android.view.View;
import com.facebook.internal.NativeProtocol;
import com.google.android.gms.ads.internal.zzbv;
import com.google.android.gms.internal.ads.zzakk;
import com.google.android.gms.internal.ads.zzci;
import com.google.android.gms.internal.ads.zzkb;
import com.google.android.gms.internal.ads.zznk;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

/* JADX INFO: loaded from: classes.dex */
public final class zzae {
    private final zzci bAa;
    private final Context mContext;
    private final View mView;

    public zzae(Context context, zzci zzciVar, View view) {
        this.mContext = context;
        this.bAa = zzciVar;
        this.mView = view;
    }

    private static Intent a(Intent intent, ResolveInfo resolveInfo) {
        Intent intent2 = new Intent(intent);
        intent2.setClassName(resolveInfo.activityInfo.packageName, resolveInfo.activityInfo.name);
        return intent2;
    }

    private final ResolveInfo a(Intent intent, ArrayList<ResolveInfo> arrayList) {
        ResolveInfo resolveInfo;
        try {
            PackageManager packageManager = this.mContext.getPackageManager();
            if (packageManager == null) {
                return null;
            }
            List<ResolveInfo> listQueryIntentActivities = packageManager.queryIntentActivities(intent, NativeProtocol.MESSAGE_GET_ACCESS_TOKEN_REQUEST);
            ResolveInfo resolveInfoResolveActivity = packageManager.resolveActivity(intent, NativeProtocol.MESSAGE_GET_ACCESS_TOKEN_REQUEST);
            if (listQueryIntentActivities != null && resolveInfoResolveActivity != null) {
                int i = 0;
                while (true) {
                    int i2 = i;
                    if (i2 >= listQueryIntentActivities.size()) {
                        resolveInfo = null;
                        break;
                    }
                    ResolveInfo resolveInfo2 = listQueryIntentActivities.get(i2);
                    if (resolveInfoResolveActivity != null && resolveInfoResolveActivity.activityInfo.name.equals(resolveInfo2.activityInfo.name)) {
                        resolveInfo = resolveInfoResolveActivity;
                        break;
                    }
                    i = i2 + 1;
                }
            } else {
                resolveInfo = null;
                break;
            }
            try {
                arrayList.addAll(listQueryIntentActivities);
                return resolveInfo;
            } catch (Throwable th) {
                th = th;
            }
        } catch (Throwable th2) {
            th = th2;
            resolveInfo = null;
        }
        zzbv.zzeo().zza(th, "OpenSystemBrowserHandler.getDefaultBrowserResolverForIntent");
        return resolveInfo;
    }

    private static Intent c(Uri uri) {
        if (uri == null) {
            return null;
        }
        Intent intent = new Intent("android.intent.action.VIEW");
        intent.addFlags(268435456);
        intent.setData(uri);
        intent.setAction("android.intent.action.VIEW");
        return intent;
    }

    private final ResolveInfo i(Intent intent) {
        return a(intent, new ArrayList<>());
    }

    /* JADX WARN: Code duplicated, block: B:9:0x0066  */
    public final Intent zzi(Map<String, String> map) {
        boolean z;
        List<ActivityManager.RunningAppProcessInfo> runningAppProcesses;
        ResolveInfo resolveInfoI;
        Uri uriBuild = null;
        ActivityManager activityManager = (ActivityManager) this.mContext.getSystemService("activity");
        String str = map.get("u");
        if (TextUtils.isEmpty(str)) {
            return null;
        }
        Uri uri = Uri.parse(zzad.a(this.mContext, this.bAa, str, this.mView, null));
        boolean z2 = Boolean.parseBoolean(map.get("use_first_package"));
        boolean z3 = Boolean.parseBoolean(map.get("use_running_process"));
        if (Boolean.parseBoolean(map.get("use_custom_tabs"))) {
            z = true;
        } else if (((Boolean) zzkb.zzik().zzd(zznk.zzbdz)).booleanValue()) {
            z = true;
        } else {
            z = false;
        }
        if ("http".equalsIgnoreCase(uri.getScheme())) {
            uriBuild = uri.buildUpon().scheme("https").build();
        } else if ("https".equalsIgnoreCase(uri.getScheme())) {
            uriBuild = uri.buildUpon().scheme("http").build();
        }
        ArrayList<ResolveInfo> arrayList = new ArrayList<>();
        Intent intentC = c(uri);
        Intent intentC2 = c(uriBuild);
        if (z) {
            zzbv.zzek();
            zzakk.zzb(this.mContext, intentC);
            zzbv.zzek();
            zzakk.zzb(this.mContext, intentC2);
        }
        ResolveInfo resolveInfoA = a(intentC, arrayList);
        if (resolveInfoA != null) {
            return a(intentC, resolveInfoA);
        }
        if (intentC2 != null && (resolveInfoI = i(intentC2)) != null) {
            Intent intentA = a(intentC, resolveInfoI);
            if (i(intentA) != null) {
                return intentA;
            }
        }
        if (arrayList.size() == 0) {
            return intentC;
        }
        if (z3 && activityManager != null && (runningAppProcesses = activityManager.getRunningAppProcesses()) != null) {
            ArrayList<ResolveInfo> arrayList2 = arrayList;
            int size = arrayList2.size();
            int i = 0;
            while (i < size) {
                int i2 = i + 1;
                ResolveInfo resolveInfo = arrayList2.get(i);
                Iterator<ActivityManager.RunningAppProcessInfo> it = runningAppProcesses.iterator();
                while (it.hasNext()) {
                    if (it.next().processName.equals(resolveInfo.activityInfo.packageName)) {
                        return a(intentC, resolveInfo);
                    }
                }
                i = i2;
            }
        }
        return z2 ? a(intentC, arrayList.get(0)) : intentC;
    }
}
