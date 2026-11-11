package com.unity3d.services.monetization.placementcontent.purchasing;

import com.unity3d.services.monetization.placementcontent.core.CustomEvent;
import java.util.HashMap;

/* JADX INFO: loaded from: classes.dex */
public class NativePromoAdapter {
    private static final String EVENT_SHOWN_SHOW_TYPE = "showType";
    private static final String EVENT_TYPE_CLICKED = "clicked";
    private static final String EVENT_TYPE_CLOSED = "closed";
    private static final String EVENT_TYPE_SHOWN = "shown";
    private PromoMetadata promoMetadata;
    private PromoAdPlacementContent promoPlacementContent;

    public NativePromoAdapter(PromoAdPlacementContent promoAdPlacementContent) {
        this.promoPlacementContent = promoAdPlacementContent;
        this.promoMetadata = promoAdPlacementContent.getMetadata();
    }

    public PromoMetadata getPromoMetadata() {
        return this.promoMetadata;
    }

    public void onShown() {
        onShown(NativePromoShowType.FULL);
    }

    public void onShown(NativePromoShowType nativePromoShowType) {
        HashMap map = new HashMap();
        map.put(EVENT_SHOWN_SHOW_TYPE, nativePromoShowType.toString());
        this.promoPlacementContent.sendCustomEvent(new CustomEvent(EVENT_TYPE_SHOWN, map));
    }

    public void onClosed() {
        this.promoPlacementContent.sendCustomEvent(new CustomEvent(EVENT_TYPE_CLOSED));
    }

    public void onClicked() {
        this.promoPlacementContent.sendCustomEvent(new CustomEvent(EVENT_TYPE_CLICKED));
    }
}
