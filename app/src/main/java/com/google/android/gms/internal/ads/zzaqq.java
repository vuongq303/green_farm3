package com.google.android.gms.internal.ads;

import android.content.DialogInterface;
import android.webkit.JsResult;

/* JADX INFO: loaded from: classes.dex */
final class zzaqq implements DialogInterface.OnClickListener {
    private final /* synthetic */ JsResult zzdbk;

    zzaqq(JsResult jsResult) {
        this.zzdbk = jsResult;
    }

    @Override // android.content.DialogInterface.OnClickListener
    public final void onClick(DialogInterface dialogInterface, int i) {
        this.zzdbk.cancel();
    }
}
