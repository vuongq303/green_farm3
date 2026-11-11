package com.google.android.gms.common.data;

import com.google.android.gms.common.internal.bf;
import java.util.ArrayList;
import java.util.HashMap;

/* JADX INFO: loaded from: classes.dex */
public class g {
    private final String[] bNe;
    private final ArrayList<HashMap<String, Object>> bNl;
    private final String bNm;
    private final HashMap<Object, Integer> bNn;
    private boolean bNo;
    private String bNp;

    private g(String[] strArr, String str) {
        this.bNe = (String[]) bf.checkNotNull(strArr);
        this.bNl = new ArrayList<>();
        this.bNm = str;
        this.bNn = new HashMap<>();
        this.bNo = false;
        this.bNp = null;
    }

    /* synthetic */ g(String[] strArr, String str, l lVar) {
        this(strArr, str);
    }
}
