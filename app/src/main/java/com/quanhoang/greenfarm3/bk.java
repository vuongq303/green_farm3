package com.quanhoang.greenfarm3;

import android.webkit.ConsoleMessage;
import android.webkit.WebChromeClient;

/* JADX INFO: compiled from: SplashScreenActivity.java */
/* JADX INFO: loaded from: classes.dex */
class bk extends WebChromeClient {
    final /* synthetic */ SplashScreenActivity bcJ;

    bk(SplashScreenActivity splashScreenActivity) {
        this.bcJ = splashScreenActivity;
    }

    @Override // android.webkit.WebChromeClient
    public boolean onConsoleMessage(ConsoleMessage consoleMessage) {
        return true;
    }
}
