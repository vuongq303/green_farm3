package com.unity3d.services.banners;

import android.os.ConditionVariable;
import com.unity3d.services.ads.properties.AdsProperties;
import com.unity3d.services.core.webview.WebViewApp;
import com.unity3d.services.core.webview.bridge.CallbackStatus;
import java.lang.reflect.Method;

/* JADX INFO: loaded from: classes.dex */
public class BannerHide {
    private static ConditionVariable _waitHideStatus;

    public static synchronized boolean hide() {
        boolean zBlock;
        Method method = BannerHide.class.getMethod("showCallback", CallbackStatus.class);
        _waitHideStatus = new ConditionVariable();
        WebViewApp.getCurrentApp().invokeMethod("webview", "hideBanner", method, new Object[0]);
        zBlock = _waitHideStatus.block(AdsProperties.getShowTimeout());
        _waitHideStatus = null;
        return zBlock;
    }

    public static void showCallback(CallbackStatus callbackStatus) {
        if (_waitHideStatus != null && callbackStatus.equals(CallbackStatus.OK)) {
            _waitHideStatus.open();
        }
    }
}
