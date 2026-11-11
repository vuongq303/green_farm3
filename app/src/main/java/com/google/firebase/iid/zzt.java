package com.google.firebase.iid;

import android.os.Bundle;
import android.util.Log;
import com.google.android.gms.f.g;

/* JADX INFO: loaded from: classes.dex */
abstract class zzt<T> {
    final int what;
    final int zzbrh;
    final g<T> zzbri = new g<>();
    final Bundle zzbrj;

    zzt(int i, int i2, Bundle bundle) {
        this.zzbrh = i;
        this.what = i2;
        this.zzbrj = bundle;
    }

    final void finish(T t) {
        if (Log.isLoggable("MessengerIpcClient", 3)) {
            String strValueOf = String.valueOf(this);
            String strValueOf2 = String.valueOf(t);
            Log.d("MessengerIpcClient", new StringBuilder(String.valueOf(strValueOf).length() + 16 + String.valueOf(strValueOf2).length()).append("Finishing ").append(strValueOf).append(" with ").append(strValueOf2).toString());
        }
        this.zzbri.setResult(t);
    }

    public String toString() {
        int i = this.what;
        int i2 = this.zzbrh;
        return new StringBuilder(55).append("Request { what=").append(i).append(" id=").append(i2).append(" oneWay=").append(zzst()).append("}").toString();
    }

    final void zza(zzu zzuVar) {
        if (Log.isLoggable("MessengerIpcClient", 3)) {
            String strValueOf = String.valueOf(this);
            String strValueOf2 = String.valueOf(zzuVar);
            Log.d("MessengerIpcClient", new StringBuilder(String.valueOf(strValueOf).length() + 14 + String.valueOf(strValueOf2).length()).append("Failing ").append(strValueOf).append(" with ").append(strValueOf2).toString());
        }
        this.zzbri.g(zzuVar);
    }

    abstract void zzh(Bundle bundle);

    abstract boolean zzst();
}
