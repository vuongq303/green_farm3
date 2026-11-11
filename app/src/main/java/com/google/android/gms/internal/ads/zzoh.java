package com.google.android.gms.internal.ads;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.content.pm.ResolveInfo;
import android.net.Uri;
import android.os.Bundle;
import android.support.a.b;
import android.support.a.g;
import android.support.a.i;
import com.facebook.internal.NativeProtocol;
import java.util.List;
import javax.annotation.ParametersAreNonnullByDefault;

/* JADX INFO: loaded from: classes.dex */
@ParametersAreNonnullByDefault
@zzadh
public final class zzoh implements zzbfy {
    private i zzbgw;
    private b zzbgx;
    private g zzbgy;
    private zzoi zzbgz;

    public static boolean zzh(Context context) {
        PackageManager packageManager = context.getPackageManager();
        if (packageManager == null) {
            return false;
        }
        Intent intent = new Intent("android.intent.action.VIEW", Uri.parse("http://www.example.com"));
        ResolveInfo resolveInfoResolveActivity = packageManager.resolveActivity(intent, 0);
        List<ResolveInfo> listQueryIntentActivities = packageManager.queryIntentActivities(intent, NativeProtocol.MESSAGE_GET_ACCESS_TOKEN_REQUEST);
        if (listQueryIntentActivities == null || resolveInfoResolveActivity == null) {
            return false;
        }
        for (int i = 0; i < listQueryIntentActivities.size(); i++) {
            if (resolveInfoResolveActivity.activityInfo.name.equals(listQueryIntentActivities.get(i).activityInfo.name)) {
                return resolveInfoResolveActivity.activityInfo.packageName.equals(zzbfw.zzbn(context));
            }
        }
        return false;
    }

    public final boolean mayLaunchUrl(Uri uri, Bundle bundle, List<Bundle> list) {
        if (this.zzbgx == null) {
            return false;
        }
        if (this.zzbgx == null) {
            this.zzbgw = null;
        } else if (this.zzbgw == null) {
            this.zzbgw = this.zzbgx.a(null);
        }
        i iVar = this.zzbgw;
        if (iVar != null) {
            return iVar.mayLaunchUrl(uri, null, null);
        }
        return false;
    }

    @Override // com.google.android.gms.internal.ads.zzbfy
    public final void zza(b bVar) {
        this.zzbgx = bVar;
        this.zzbgx.b(0L);
        if (this.zzbgz != null) {
            this.zzbgz.zzjp();
        }
    }

    public final void zza(zzoi zzoiVar) {
        this.zzbgz = zzoiVar;
    }

    public final void zzc(Activity activity) {
        if (this.zzbgy == null) {
            return;
        }
        activity.unbindService(this.zzbgy);
        this.zzbgx = null;
        this.zzbgw = null;
        this.zzbgy = null;
    }

    public final void zzd(Activity activity) {
        String strZzbn;
        if (this.zzbgx == null && (strZzbn = zzbfw.zzbn(activity)) != null) {
            this.zzbgy = new zzbfx(this);
            b.a(activity, strZzbn, this.zzbgy);
        }
    }

    @Override // com.google.android.gms.internal.ads.zzbfy
    public final void zzjo() {
        this.zzbgx = null;
        this.zzbgw = null;
        if (this.zzbgz != null) {
            this.zzbgz.zzjq();
        }
    }
}
