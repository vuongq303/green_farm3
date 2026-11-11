package com.google.android.gms.common.internal;

import android.content.Context;
import android.content.res.Resources;
import javax.annotation.Nullable;

/* JADX INFO: loaded from: classes.dex */
public class bj {
    private final Resources bOA;
    private final String bOB;

    public bj(Context context) {
        bf.checkNotNull(context);
        this.bOA = context.getResources();
        this.bOB = this.bOA.getResourcePackageName(com.google.android.gms.common.p.common_google_play_services_unknown_issue);
    }

    @Nullable
    public String getString(String str) {
        int identifier = this.bOA.getIdentifier(str, "string", this.bOB);
        if (identifier == 0) {
            return null;
        }
        return this.bOA.getString(identifier);
    }
}
