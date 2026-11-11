package com.a.a.a.a.c;

import android.content.Context;

/* JADX INFO: loaded from: classes.dex */
public class e {
    private static e cer = new e();
    private Context ceo;

    private e() {
    }

    public static e ahY() {
        return cer;
    }

    public void a(Context context) {
        this.ceo = context != null ? context.getApplicationContext() : null;
    }

    public Context ahZ() {
        return this.ceo;
    }
}
