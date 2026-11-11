package com.unity3d.services.monetization.placementcontent.purchasing;

import com.unity3d.services.monetization.placementcontent.ads.ShowAdPlacementContent;
import java.util.Map;

/* JADX INFO: loaded from: classes.dex */
public class PromoAdPlacementContent extends ShowAdPlacementContent {
    private final PromoMetadata promoMetadata;

    public PromoAdPlacementContent(String str, Map<String, Object> map) {
        super(str, map);
        this.promoMetadata = PromoMetadataUtilities.createPromoMetadataFromParamsMap(map);
    }

    public PromoMetadata getMetadata() {
        return this.promoMetadata;
    }

    @Override // com.unity3d.services.monetization.placementcontent.core.PlacementContent
    protected String getDefaultEventCategory() {
        return "PROMO";
    }
}
