package com.google.android.gms.internal.ads;

import android.content.DialogInterface;

/* JADX INFO: loaded from: classes.dex */
final class zzaag implements DialogInterface.OnClickListener {
    private final /* synthetic */ zzaae zzbwq;

    zzaag(zzaae zzaaeVar) {
        this.zzbwq = zzaaeVar;
    }

    @Override // android.content.DialogInterface.OnClickListener
    public final void onClick(DialogInterface dialogInterface, int i) {
        this.zzbwq.zzbw("User canceled the download.");
    }
}
