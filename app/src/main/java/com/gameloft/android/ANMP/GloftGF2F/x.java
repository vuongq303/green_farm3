package com.gameloft.android.ANMP.GloftGF2F;

import android.webkit.ConsoleMessage;
import android.webkit.WebChromeClient;

/* JADX INFO: compiled from: IGPFreemiumActivity.java */
/* JADX INFO: loaded from: classes.dex */
class x extends WebChromeClient {
    final /* synthetic */ IGPFreemiumActivity Dj;

    x(IGPFreemiumActivity iGPFreemiumActivity) {
        this.Dj = iGPFreemiumActivity;
    }

    @Override // android.webkit.WebChromeClient
    public boolean onConsoleMessage(ConsoleMessage consoleMessage) {
        return true;
    }
}
