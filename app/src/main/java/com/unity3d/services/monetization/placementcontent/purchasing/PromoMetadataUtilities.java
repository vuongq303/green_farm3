package com.unity3d.services.monetization.placementcontent.purchasing;

import com.google.firebase.analytics.FirebaseAnalytics;
import com.unity3d.services.purchasing.core.Product;
import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

/* JADX INFO: loaded from: classes.dex */
public class PromoMetadataUtilities {
    public static PromoMetadata createPromoMetadataFromParamsMap(Map<String, Object> map) {
        PromoMetadata.Builder builderNewBuilder = PromoMetadata.newBuilder();
        if (map.containsKey("impressionDate")) {
            builderNewBuilder.withImpressionDate(new Date(((Long) map.get("impressionDate")).longValue()));
        }
        if (map.containsKey("offerDuration")) {
            builderNewBuilder.withOfferDuration(((Long) map.get("offerDuration")).longValue());
        }
        if (map.containsKey("costs")) {
            builderNewBuilder.withCosts(getItemListFromList((List) map.get("costs")));
        }
        if (map.containsKey("payouts")) {
            builderNewBuilder.withPayouts(getItemListFromList((List) map.get("payouts")));
        }
        if (map.containsKey("product")) {
            builderNewBuilder.withPremiumProduct(createProductFromMap((Map) map.get("product")));
        }
        if (map.containsKey("userInfo")) {
            builderNewBuilder.withCustomInfo((Map) map.get("userInfo"));
        }
        return builderNewBuilder.build();
    }

    private static List<Item> getItemListFromList(List<Map<String, Object>> list) {
        ArrayList arrayList = new ArrayList(list.size());
        Iterator<Map<String, Object>> it = list.iterator();
        while (it.hasNext()) {
            arrayList.add(createItemFromMap(it.next()));
        }
        return arrayList;
    }

    private static Item createItemFromMap(Map<String, Object> map) {
        Item.Builder builderNewBuilder = Item.newBuilder();
        if (map.containsKey("itemId")) {
            builderNewBuilder.withItemId((String) map.get("itemId"));
        }
        if (map.containsKey(FirebaseAnalytics.Param.QUANTITY)) {
            builderNewBuilder.withQuantity(((Number) map.get(FirebaseAnalytics.Param.QUANTITY)).longValue());
        }
        if (map.containsKey("type")) {
            builderNewBuilder.withType((String) map.get("type"));
        }
        return builderNewBuilder.build();
    }

    private static Product createProductFromMap(Map<String, Object> map) {
        Product.Builder builderNewBuilder = Product.newBuilder();
        if (map.containsKey("productId")) {
            builderNewBuilder.withProductId((String) map.get("productId"));
        }
        if (map.containsKey("isoCurrencyCode")) {
            builderNewBuilder.withIsoCurrencyCode((String) map.get("isoCurrencyCode"));
        }
        if (map.containsKey("localizedPriceString")) {
            builderNewBuilder.withLocalizedPriceString((String) map.get("localizedPriceString"));
        }
        if (map.containsKey("localizedDescription")) {
            builderNewBuilder.withLocalizedDescription((String) map.get("localizedDescription"));
        }
        if (map.containsKey("localizedTitle")) {
            builderNewBuilder.withLocalizedTitle((String) map.get("localizedTitle"));
        }
        if (map.containsKey("localizedPrice")) {
            builderNewBuilder.withLocalizedPrice(new Double(map.get("localizedPrice").toString()).doubleValue());
        }
        if (map.containsKey("productType")) {
            builderNewBuilder.withProductType((String) map.get("productType"));
        }
        return builderNewBuilder.build();
    }
}
