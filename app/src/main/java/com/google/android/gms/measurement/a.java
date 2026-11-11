package com.google.android.gms.measurement;

import android.content.Context;
import android.os.Bundle;
import com.google.android.gms.common.internal.bf;
import com.google.android.gms.internal.measurement.zzgl;
import com.google.android.gms.internal.measurement.zzjv;
import java.util.List;

/* JADX INFO: loaded from: classes.dex */
@Deprecated
public class a {
    private final zzgl zzacr;

    public a(zzgl zzglVar) {
        bf.checkNotNull(zzglVar);
        this.zzacr = zzglVar;
    }

    @Deprecated
    public static a aX(Context context) {
        return zzgl.zzg(context).zzjo();
    }

    public void a(String str, String str2, Object obj) {
        this.zzacr.zzfu().zza(str, str2, obj);
    }

    public void b(String str, String str2, Bundle bundle) {
        if (bundle == null) {
            bundle = new Bundle();
        }
        this.zzacr.zzfu().zza(str, str2, bundle);
    }

    public List<b> getConditionalUserProperties(String str, String str2) {
        return this.zzacr.zzfu().getConditionalUserProperties(str, str2);
    }

    public int iy(String str) {
        this.zzacr.zzfu();
        bf.id(str);
        return 25;
    }

    public final void logEvent(String str, Bundle bundle) {
        if (bundle == null) {
            bundle = new Bundle();
        }
        this.zzacr.zzfu().zza("app", str, bundle, true);
    }

    @Deprecated
    public void setMeasurementEnabled(boolean z) {
        this.zzacr.zzfu().setMeasurementEnabled(z);
    }

    public final void setMinimumSessionDuration(long j) {
        this.zzacr.zzfu().setMinimumSessionDuration(j);
    }

    public final void setSessionTimeoutDuration(long j) {
        this.zzacr.zzfu().setSessionTimeoutDuration(j);
    }

    public final void setUserProperty(String str, String str2) {
        int iZzbx = this.zzacr.zzgc().zzbx(str);
        if (iZzbx == 0) {
            a("app", str, str2);
        } else {
            this.zzacr.zzgc();
            this.zzacr.zzgc().zza(iZzbx, "_ev", zzjv.zza(str, 24, true), str != null ? str.length() : 0);
        }
    }
}
