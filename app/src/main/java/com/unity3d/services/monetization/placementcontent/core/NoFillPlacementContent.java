package com.unity3d.services.monetization.placementcontent.core;

import com.unity3d.services.monetization.UnityMonetization;
import java.util.Map;

/* JADX INFO: loaded from: classes.dex */
public class NoFillPlacementContent extends PlacementContent {
    public NoFillPlacementContent(String str, Map<String, Object> map) {
        super(str, map);
    }

    @Override // com.unity3d.services.monetization.placementcontent.core.PlacementContent
    public boolean isReady() {
        return false;
    }

    @Override // com.unity3d.services.monetization.placementcontent.core.PlacementContent
    public UnityMonetization.PlacementContentState getState() {
        return UnityMonetization.PlacementContentState.NO_FILL;
    }

    @Override // com.unity3d.services.monetization.placementcontent.core.PlacementContent
    public void sendCustomEvent(CustomEvent customEvent) {
    }

    @Override // com.unity3d.services.monetization.placementcontent.core.PlacementContent
    protected String getDefaultEventCategory() {
        return "NO_FILL";
    }
}
