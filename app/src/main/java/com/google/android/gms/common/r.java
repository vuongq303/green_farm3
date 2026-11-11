package com.google.android.gms.common;

import android.content.Intent;

/* JADX INFO: loaded from: classes.dex */
public class r extends Exception {
    private final Intent v;

    public r(String str, Intent intent) {
        super(str);
        this.v = intent;
    }

    public Intent getIntent() {
        return new Intent(this.v);
    }
}
