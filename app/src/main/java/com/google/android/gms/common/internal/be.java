package com.google.android.gms.common.internal;

import java.util.ArrayList;
import java.util.List;

/* JADX INFO: loaded from: classes.dex */
public final class be {
    private final List<String> bOu;
    private final Object bOv;

    private be(Object obj) {
        this.bOv = bf.checkNotNull(obj);
        this.bOu = new ArrayList();
    }

    public final be c(String str, Object obj) {
        List<String> list = this.bOu;
        String str2 = (String) bf.checkNotNull(str);
        String strValueOf = String.valueOf(obj);
        list.add(new StringBuilder(String.valueOf(str2).length() + 1 + String.valueOf(strValueOf).length()).append(str2).append("=").append(strValueOf).toString());
        return this;
    }

    public final String toString() {
        StringBuilder sbAppend = new StringBuilder(100).append(this.bOv.getClass().getSimpleName()).append('{');
        int size = this.bOu.size();
        for (int i = 0; i < size; i++) {
            sbAppend.append(this.bOu.get(i));
            if (i < size - 1) {
                sbAppend.append(", ");
            }
        }
        return sbAppend.append('}').toString();
    }
}
