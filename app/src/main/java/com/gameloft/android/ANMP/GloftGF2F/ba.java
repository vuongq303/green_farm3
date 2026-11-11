package com.gameloft.android.ANMP.GloftGF2F;

import android.content.Context;
import android.view.KeyEvent;
import android.webkit.WebView;
import com.facebook.internal.FacebookRequestErrorClassification;
import com.gameloft.adsmanager.JavaUtils;

/* JADX INFO: compiled from: InGameBrowserActivity.java */
/* JADX INFO: loaded from: classes.dex */
class ba extends WebView {
    final /* synthetic */ InGameBrowserActivity Ex;

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    public ba(InGameBrowserActivity inGameBrowserActivity, Context context) {
        super(context);
        this.Ex = inGameBrowserActivity;
    }

    @Override // android.view.ViewGroup, android.view.View
    public boolean dispatchKeyEventPreIme(KeyEvent keyEvent) {
        switch (keyEvent.getKeyCode()) {
            case 19:
            case 20:
            case 21:
            case 22:
            case 96:
            case 97:
            case 99:
            case JavaUtils.Constants.NATIVE_HEIGHT_DP_100 /* 100 */:
            case FacebookRequestErrorClassification.EC_INVALID_SESSION /* 102 */:
            case 103:
            case 108:
            case 109:
                if (keyEvent.getAction() == 0) {
                    this.Ex.onKeyDown(keyEvent.getKeyCode(), keyEvent);
                } else if (keyEvent.getAction() == 1) {
                    this.Ex.onKeyUp(keyEvent.getKeyCode(), keyEvent);
                }
                break;
        }
        return super.dispatchKeyEventPreIme(keyEvent);
    }
}
