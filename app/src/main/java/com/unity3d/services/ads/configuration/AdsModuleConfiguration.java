package com.unity3d.services.ads.configuration;

import android.os.ConditionVariable;
import com.unity3d.ads.IUnityAdsListener;
import com.unity3d.ads.UnityAds;
import com.unity3d.services.ads.adunit.VideoPlayerHandler;
import com.unity3d.services.ads.adunit.WebPlayerHandler;
import com.unity3d.services.ads.adunit.WebViewHandler;
import com.unity3d.services.ads.api.AdUnit;
import com.unity3d.services.ads.api.AdsProperties;
import com.unity3d.services.ads.api.Listener;
import com.unity3d.services.ads.api.Placement;
import com.unity3d.services.ads.api.Purchasing;
import com.unity3d.services.ads.api.VideoPlayer;
import com.unity3d.services.ads.api.WebPlayer;
import com.unity3d.services.core.configuration.Configuration;
import com.unity3d.services.core.log.DeviceLog;
import com.unity3d.services.core.misc.Utilities;
import java.net.InetAddress;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.HashMap;
import java.util.Map;

/* JADX INFO: loaded from: classes.dex */
public class AdsModuleConfiguration implements IAdsModuleConfiguration {
    private InetAddress _address;

    @Override // com.unity3d.services.core.configuration.IModuleConfiguration
    public Class[] getWebAppApiClassList() {
        return new Class[]{AdUnit.class, Listener.class, VideoPlayer.class, Placement.class, WebPlayer.class, Purchasing.class, AdsProperties.class};
    }

    @Override // com.unity3d.services.core.configuration.IModuleConfiguration
    public boolean resetState(Configuration configuration) {
        com.unity3d.services.ads.placement.Placement.reset();
        return true;
    }

    /* JADX WARN: Type inference failed for: r3v0, types: [com.unity3d.services.ads.configuration.AdsModuleConfiguration$1] */
    @Override // com.unity3d.services.core.configuration.IModuleConfiguration
    public boolean initModuleState(Configuration configuration) {
        DeviceLog.debug("Unity Ads init: checking for ad blockers");
        try {
            final String host = new URL(configuration.getConfigUrl()).getHost();
            final ConditionVariable conditionVariable = new ConditionVariable();
            new Thread() { // from class: com.unity3d.services.ads.configuration.AdsModuleConfiguration.1
                @Override // java.lang.Thread, java.lang.Runnable
                public void run() {
                    try {
                        AdsModuleConfiguration.this._address = InetAddress.getByName(host);
                        conditionVariable.open();
                    } catch (Exception e) {
                        DeviceLog.exception("Couldn't get address. Host: " + host, e);
                        conditionVariable.open();
                    }
                }
            }.start();
            if (!conditionVariable.block(2000L) || this._address == null || !this._address.isLoopbackAddress()) {
                return true;
            }
            DeviceLog.error("Unity Ads init: halting init because Unity Ads config resolves to loopback address (due to ad blocker?)");
            final IUnityAdsListener listener = com.unity3d.services.ads.properties.AdsProperties.getListener();
            if (listener != null) {
                Utilities.runOnUiThread(new Runnable() { // from class: com.unity3d.services.ads.configuration.AdsModuleConfiguration.2
                    @Override // java.lang.Runnable
                    public void run() {
                        listener.onUnityAdsError(UnityAds.UnityAdsError.AD_BLOCKER_DETECTED, "Unity Ads config server resolves to loopback address (due to ad blocker?)");
                    }
                });
            }
            return false;
        } catch (MalformedURLException e) {
            return true;
        }
    }

    @Override // com.unity3d.services.core.configuration.IModuleConfiguration
    public boolean initErrorState(Configuration configuration, String str, String str2) {
        final IUnityAdsListener listener = UnityAds.getListener();
        final String str3 = "Init failed in " + str;
        if (com.unity3d.services.ads.properties.AdsProperties.getListener() != null) {
            Utilities.runOnUiThread(new Runnable() { // from class: com.unity3d.services.ads.configuration.AdsModuleConfiguration.3
                @Override // java.lang.Runnable
                public void run() {
                    listener.onUnityAdsError(UnityAds.UnityAdsError.INITIALIZE_FAILED, str3);
                }
            });
            return true;
        }
        return true;
    }

    @Override // com.unity3d.services.core.configuration.IModuleConfiguration
    public boolean initCompleteState(Configuration configuration) {
        return true;
    }

    @Override // com.unity3d.services.ads.configuration.IAdsModuleConfiguration
    public Map<String, Class> getAdUnitViewHandlers() {
        HashMap map = new HashMap();
        map.put("videoplayer", VideoPlayerHandler.class);
        map.put("webplayer", WebPlayerHandler.class);
        map.put("webview", WebViewHandler.class);
        return map;
    }
}
