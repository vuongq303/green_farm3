package com.google.android.gms.common.api.a;

import android.app.Activity;
import android.support.v4.app.FragmentActivity;

/* JADX INFO: loaded from: classes.dex */
public class s {
    private final Object bJN;

    public s(Activity activity) {
        com.google.android.gms.common.internal.bf.d(activity, "Activity must not be null");
        this.bJN = activity;
    }

    public final boolean Zj() {
        return this.bJN instanceof FragmentActivity;
    }

    public final boolean Zk() {
        return this.bJN instanceof Activity;
    }

    public final Activity Zl() {
        return (Activity) this.bJN;
    }

    public final FragmentActivity Zm() {
        return (FragmentActivity) this.bJN;
    }
}
