package com.unity3d.ads.purchasing;

/* JADX INFO: loaded from: classes.dex */
public interface IPurchasing {
    void onGetProductCatalog();

    void onGetPurchasingVersion();

    void onInitializePurchasing();

    void onPurchasingCommand(String str);
}
