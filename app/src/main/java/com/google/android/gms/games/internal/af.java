package com.google.android.gms.games.internal;

import android.os.Bundle;
import com.google.android.gms.common.api.Status;
import com.google.android.gms.common.data.DataHolder;
import java.util.Iterator;

/* JADX INFO: loaded from: classes.dex */
final class af implements com.google.android.gms.games.request.d {
    private final Status bUl;
    private final Bundle bUr;

    af(Status status, Bundle bundle) {
        this.bUl = status;
        this.bUr = bundle;
    }

    @Override // com.google.android.gms.common.api.ae
    public final Status getStatus() {
        return this.bUl;
    }

    @Override // com.google.android.gms.common.api.ac
    public final void release() {
        Iterator<String> it = this.bUr.keySet().iterator();
        while (it.hasNext()) {
            DataHolder dataHolder = (DataHolder) this.bUr.getParcelable(it.next());
            if (dataHolder != null) {
                dataHolder.close();
            }
        }
    }
}
