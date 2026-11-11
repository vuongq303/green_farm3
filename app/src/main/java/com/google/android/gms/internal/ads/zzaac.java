package com.google.android.gms.internal.ads;

import android.view.View;

/* JADX INFO: loaded from: classes.dex */
final class zzaac implements View.OnClickListener {
    private final /* synthetic */ zzaab zzbwl;

    zzaac(zzaab zzaabVar) {
        this.zzbwl = zzaabVar;
    }

    @Override // android.view.View.OnClickListener
    public final void onClick(View view) {
        this.zzbwl.zzm(true);
    }
}
