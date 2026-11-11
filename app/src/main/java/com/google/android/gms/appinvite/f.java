package com.google.android.gms.appinvite;

import android.view.View;

/* JADX INFO: loaded from: classes.dex */
final class f implements View.OnClickListener {
    private final /* synthetic */ PreviewActivity bEO;

    f(PreviewActivity previewActivity) {
        this.bEO = previewActivity;
    }

    @Override // android.view.View.OnClickListener
    public final void onClick(View view) {
        this.bEO.finish();
    }
}
