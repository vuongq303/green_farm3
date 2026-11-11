package com.google.android.gms.internal.ads;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import com.google.android.gms.common.e.c;
import com.google.android.gms.common.internal.bf;

/* JADX INFO: loaded from: classes.dex */
@zzadh
public final class zzmw {
    private final Context mContext;

    public zzmw(Context context) {
        bf.d(context, "Context can not be null");
        this.mContext = context;
    }

    private final boolean zza(Intent intent) {
        bf.d(intent, "Intent can not be null");
        return !this.mContext.getPackageManager().queryIntentActivities(intent, 0).isEmpty();
    }

    public final boolean zziw() {
        Intent intent = new Intent("android.intent.action.DIAL");
        intent.setData(Uri.parse("tel:"));
        return zza(intent);
    }

    public final boolean zzix() {
        Intent intent = new Intent("android.intent.action.VIEW");
        intent.setData(Uri.parse("sms:"));
        return zza(intent);
    }

    public final boolean zziy() {
        return ((Boolean) zzaml.zza(this.mContext, new zzmx())).booleanValue() && c.aV(this.mContext).checkCallingOrSelfPermission("android.permission.WRITE_EXTERNAL_STORAGE") == 0;
    }

    public final boolean zziz() {
        return zza(new Intent("android.intent.action.INSERT").setType("vnd.android.cursor.dir/event"));
    }
}
