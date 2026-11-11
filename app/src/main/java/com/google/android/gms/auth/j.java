package com.google.android.gms.auth;

import android.os.Bundle;
import android.os.IBinder;
import com.google.android.gms.internal.auth.zzi;

/* JADX INFO: loaded from: classes.dex */
final class j implements k<Void> {
    private final /* synthetic */ Bundle val$extras;
    private final /* synthetic */ String zzt;

    j(String str, Bundle bundle) {
        this.zzt = str;
        this.val$extras = bundle;
    }

    @Override // com.google.android.gms.auth.k
    public final /* synthetic */ Void f(IBinder iBinder) throws a {
        Bundle bundle = (Bundle) h.w(zzi.zzd(iBinder).zzd(this.zzt, this.val$extras));
        String string = bundle.getString("Error");
        if (bundle.getBoolean("booleanResult")) {
            return null;
        }
        throw new a(string);
    }
}
