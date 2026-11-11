package com.unity3d.services.monetization;

import com.unity3d.services.IUnityServicesListener;
import com.unity3d.services.monetization.placementcontent.core.PlacementContent;

/* JADX INFO: loaded from: classes.dex */
public interface IUnityMonetizationListener extends IUnityServicesListener {
    void onPlacementContentReady(String str, PlacementContent placementContent);

    void onPlacementContentStateChange(String str, PlacementContent placementContent, UnityMonetization.PlacementContentState placementContentState, UnityMonetization.PlacementContentState placementContentState2);
}
