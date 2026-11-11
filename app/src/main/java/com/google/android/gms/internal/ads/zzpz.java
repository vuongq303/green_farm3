package com.google.android.gms.internal.ads;

import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.RemoteException;
import com.google.android.gms.ads.formats.NativeAd;
import com.google.android.gms.c.a;
import com.google.android.gms.c.d;

/* JADX INFO: loaded from: classes.dex */
@zzadh
public final class zzpz extends NativeAd.Image {
    private final Drawable mDrawable;
    private final Uri mUri;
    private final double zzbhv;
    private final zzpw zzbkm;

    public zzpz(zzpw zzpwVar) {
        Drawable drawable;
        Uri uri = null;
        this.zzbkm = zzpwVar;
        try {
            a aVarZzjy = this.zzbkm.zzjy();
            drawable = aVarZzjy != null ? (Drawable) d.b(aVarZzjy) : null;
        } catch (RemoteException e) {
            zzane.zzb("", e);
        }
        this.mDrawable = drawable;
        try {
            uri = this.zzbkm.getUri();
        } catch (RemoteException e2) {
            zzane.zzb("", e2);
        }
        this.mUri = uri;
        double scale = 1.0d;
        try {
            scale = this.zzbkm.getScale();
        } catch (RemoteException e3) {
            zzane.zzb("", e3);
        }
        this.zzbhv = scale;
    }

    @Override // com.google.android.gms.ads.formats.NativeAd.Image
    public final Drawable getDrawable() {
        return this.mDrawable;
    }

    @Override // com.google.android.gms.ads.formats.NativeAd.Image
    public final double getScale() {
        return this.zzbhv;
    }

    @Override // com.google.android.gms.ads.formats.NativeAd.Image
    public final Uri getUri() {
        return this.mUri;
    }
}
