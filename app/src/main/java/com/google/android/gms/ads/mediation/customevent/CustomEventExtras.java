package com.google.android.gms.ads.mediation.customevent;

import com.google.ads.mediation.q;
import java.util.HashMap;

/* JADX INFO: loaded from: classes.dex */
@Deprecated
public final class CustomEventExtras implements q {
    private final HashMap<String, Object> bEg = new HashMap<>();

    public final Object getExtra(String str) {
        return this.bEg.get(str);
    }

    public final void setExtra(String str, Object obj) {
        this.bEg.put(str, obj);
    }
}
