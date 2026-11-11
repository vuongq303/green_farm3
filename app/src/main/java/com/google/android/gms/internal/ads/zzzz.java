package com.google.android.gms.internal.ads;

import android.content.DialogInterface;
import android.content.Intent;

/* JADX INFO: loaded from: classes.dex */
final class zzzz implements DialogInterface.OnClickListener {
    private final /* synthetic */ zzzy zzbvx;

    zzzz(zzzy zzzyVar) {
        this.zzbvx = zzzyVar;
    }

    @Override // android.content.DialogInterface.OnClickListener
    public final void onClick(DialogInterface dialogInterface, int i) {
        Intent intentCreateIntent = this.zzbvx.createIntent();
        com.google.android.gms.ads.internal.zzbv.zzek();
        zzakk.zza(this.zzbvx.mContext, intentCreateIntent);
    }
}
