package com.c.a.a.a;

import android.content.Context;

/* JADX INFO: compiled from: AvidContext.java */
/* JADX INFO: loaded from: classes.dex */
public class b {
    private static final b ciL = new b();
    private String ciM;

    public static b aiv() {
        return ciL;
    }

    public void init(Context context) {
        if (this.ciM == null) {
            this.ciM = context.getApplicationContext().getPackageName();
        }
    }

    public String aiw() {
        return this.ciM;
    }

    public String aix() {
        return "3.6.4";
    }

    public String aiy() {
        return "gameloft";
    }
}
