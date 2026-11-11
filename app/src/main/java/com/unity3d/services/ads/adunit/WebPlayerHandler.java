package com.unity3d.services.ads.adunit;

import android.os.Bundle;
import android.view.View;
import com.unity3d.services.ads.webplayer.WebPlayer;
import com.unity3d.services.core.misc.ViewUtilities;

/* JADX INFO: loaded from: classes.dex */
public class WebPlayerHandler implements IAdUnitViewHandler {
    private WebPlayer _webPlayer;

    @Override // com.unity3d.services.ads.adunit.IAdUnitViewHandler
    public boolean create(AdUnitActivity adUnitActivity) {
        if (this._webPlayer == null) {
            this._webPlayer = new WebPlayer(adUnitActivity, "webplayer", com.unity3d.services.ads.api.WebPlayer.getWebSettings(), com.unity3d.services.ads.api.WebPlayer.getWebPlayerSettings());
            this._webPlayer.setEventSettings(com.unity3d.services.ads.api.WebPlayer.getWebPlayerEventSettings());
            return true;
        }
        return true;
    }

    @Override // com.unity3d.services.ads.adunit.IAdUnitViewHandler
    public boolean destroy() {
        if (this._webPlayer != null) {
            ViewUtilities.removeViewFromParent(this._webPlayer);
        }
        this._webPlayer = null;
        return true;
    }

    @Override // com.unity3d.services.ads.adunit.IAdUnitViewHandler
    public View getView() {
        return this._webPlayer;
    }

    @Override // com.unity3d.services.ads.adunit.IAdUnitViewHandler
    public void onCreate(AdUnitActivity adUnitActivity, Bundle bundle) {
        create(adUnitActivity);
    }

    @Override // com.unity3d.services.ads.adunit.IAdUnitViewHandler
    public void onStart(AdUnitActivity adUnitActivity) {
    }

    @Override // com.unity3d.services.ads.adunit.IAdUnitViewHandler
    public void onStop(AdUnitActivity adUnitActivity) {
    }

    @Override // com.unity3d.services.ads.adunit.IAdUnitViewHandler
    public void onResume(AdUnitActivity adUnitActivity) {
    }

    @Override // com.unity3d.services.ads.adunit.IAdUnitViewHandler
    public void onPause(AdUnitActivity adUnitActivity) {
    }

    @Override // com.unity3d.services.ads.adunit.IAdUnitViewHandler
    public void onDestroy(AdUnitActivity adUnitActivity) {
        if (adUnitActivity.isFinishing()) {
            destroy();
        }
    }
}
