package com.google.android.gms.internal.ads;

import android.content.DialogInterface;
import android.webkit.JsPromptResult;

/* JADX INFO: loaded from: classes.dex */
final class zzaqt implements DialogInterface.OnClickListener {
    private final /* synthetic */ JsPromptResult zzdbl;

    zzaqt(JsPromptResult jsPromptResult) {
        this.zzdbl = jsPromptResult;
    }

    @Override // android.content.DialogInterface.OnClickListener
    public final void onClick(DialogInterface dialogInterface, int i) {
        this.zzdbl.cancel();
    }
}
