package com.unity3d.services.purchasing.core;

import java.util.Map;

/* JADX INFO: loaded from: classes.dex */
public interface IPurchasingAdapter {
    void onPurchase(String str, ITransactionListener iTransactionListener, Map<String, Object> map);

    void retrieveProducts(IRetrieveProductsListener iRetrieveProductsListener);
}
