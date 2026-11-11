package com.google.android.gms.auth;

import android.content.Intent;

/* JADX INFO: loaded from: classes.dex */
public class d extends a {
    private final Intent v;

    public d(String str, Intent intent) {
        super(str);
        this.v = intent;
    }

    public Intent getIntent() {
        if (this.v == null) {
            return null;
        }
        return new Intent(this.v);
    }
}
