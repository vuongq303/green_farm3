package com.unity3d.services.monetization.core.placementcontent;

import com.unity3d.services.monetization.placementcontent.ads.ShowAdPlacementContent;
import com.unity3d.services.monetization.placementcontent.core.NoFillPlacementContent;
import com.unity3d.services.monetization.placementcontent.core.PlacementContent;
import com.unity3d.services.monetization.placementcontent.purchasing.PromoAdPlacementContent;
import java.util.Map;

/* JADX INFO: loaded from: classes.dex */
public class PlacementContentResultFactory {

    public enum PlacementContentResultType {
        SHOW_AD,
        PROMO_AD,
        NO_FILL,
        CUSTOM;

        static PlacementContentResultType parse(String str) {
            if (str == null) {
                return CUSTOM;
            }
            try {
                return valueOf(str);
            } catch (IllegalArgumentException e) {
                return CUSTOM;
            }
        }
    }

    public static PlacementContent create(String str, Map<String, Object> map) {
        switch (PlacementContentResultType.parse((String) map.get("type"))) {
            case SHOW_AD:
                return new ShowAdPlacementContent(str, map);
            case PROMO_AD:
                return new PromoAdPlacementContent(str, map);
            case NO_FILL:
                return new NoFillPlacementContent(str, map);
            default:
                return new PlacementContent(str, map);
        }
    }
}
