package com.google.android.gms.internal.ads;

import android.graphics.drawable.Drawable;
import android.net.Uri;
import com.google.android.gms.c.a;
import com.google.android.gms.c.d;

/* JADX INFO: loaded from: classes.dex */
@zzadh
public final class zzon extends zzpx {
    private final Uri mUri;
    private final Drawable zzbhu;
    private final double zzbhv;

    public zzon(Drawable drawable, Uri uri, double d) {
        this.zzbhu = drawable;
        this.mUri = uri;
        this.zzbhv = d;
    }

    @Override // com.google.android.gms.internal.ads.zzpw
    public final double getScale() {
        return this.zzbhv;
    }

    @Override // com.google.android.gms.internal.ads.zzpw
    public final Uri getUri() {
        return this.mUri;
    }

    @Override // com.google.android.gms.internal.ads.zzpw
    public final a zzjy() {
        return d.F(this.zzbhu);
    }
}
