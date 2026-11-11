package com.google.android.gms.internal.p001authapiphone;

import android.app.Activity;
import android.content.Context;
import com.google.android.gms.auth.api.a.a;
import com.google.android.gms.f.f;

/* JADX INFO: loaded from: classes.dex */
public final class zzj extends a {
    public zzj(Activity activity) {
        super(activity);
    }

    public zzj(Context context) {
        super(context);
    }

    @Override // com.google.android.gms.auth.api.a.a
    public final f<Void> startSmsRetriever() {
        return doWrite(new zzk(this));
    }
}
