package com.unity3d.services.ads.placement;

import com.unity3d.ads.UnityAds;
import java.util.HashMap;

/* JADX INFO: loaded from: classes.dex */
public class Placement {
    private static String _defaultBannerPlacement;
    private static String _defaultPlacement;
    private static HashMap<String, UnityAds.PlacementState> _placementReadyMap;

    public static void setPlacementState(String str, String str2) {
        if (_placementReadyMap == null) {
            _placementReadyMap = new HashMap<>();
        }
        _placementReadyMap.put(str, UnityAds.PlacementState.valueOf(str2));
    }

    public static boolean isReady(String str) {
        return getPlacementState(str) == UnityAds.PlacementState.READY;
    }

    public static boolean isReady() {
        return getPlacementState() == UnityAds.PlacementState.READY;
    }

    public static UnityAds.PlacementState getPlacementState(String str) {
        return currentState(str);
    }

    public static UnityAds.PlacementState getPlacementState() {
        return _defaultPlacement == null ? UnityAds.PlacementState.NOT_AVAILABLE : getPlacementState(_defaultPlacement);
    }

    public static void reset() {
        _placementReadyMap = null;
        _defaultPlacement = null;
    }

    public static void setDefaultPlacement(String str) {
        _defaultPlacement = str;
    }

    public static String getDefaultPlacement() {
        return _defaultPlacement;
    }

    public static void setDefaultBannerPlacement(String str) {
        _defaultBannerPlacement = str;
    }

    public static String getDefaultBannerPlacement() {
        return _defaultBannerPlacement;
    }

    private static UnityAds.PlacementState currentState(String str) {
        return (_placementReadyMap == null || !_placementReadyMap.containsKey(str)) ? UnityAds.PlacementState.NOT_AVAILABLE : _placementReadyMap.get(str);
    }
}
