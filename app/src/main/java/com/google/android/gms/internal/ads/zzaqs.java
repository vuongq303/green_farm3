package com.google.android.gms.internal.ads;

import android.content.DialogInterface;
import android.webkit.JsPromptResult;

/* JADX INFO: loaded from: classes.dex */
final class zzaqs implements DialogInterface.OnCancelListener {
    private final /* synthetic */ JsPromptResult zzdbl;

    zzaqs(JsPromptResult jsPromptResult) {
        this.zzdbl = jsPromptResult;
    }

    @Override // android.content.DialogInterface.OnCancelListener
    public final void onCancel(DialogInterface dialogInterface) {
        this.zzdbl.cancel();
    }
}
