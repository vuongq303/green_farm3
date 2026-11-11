package com.google.android.gms.internal.measurement;

import android.text.TextUtils;
import android.util.Log;
import com.facebook.internal.AnalyticsEvents;
import com.google.android.gms.a.l;
import com.google.android.gms.common.internal.bf;
import java.util.HashMap;
import java.util.UUID;

/* JADX INFO: loaded from: classes.dex */
public final class zzae extends l<zzae> {
    private String zzug;
    private int zzuh;
    private int zzui;
    private String zzuj;
    private String zzuk;
    private boolean zzul;
    private boolean zzum;

    public zzae() {
        this(false);
    }

    /* JADX WARN: Illegal instructions before constructor call */
    private zzae(boolean z) {
        UUID uuidRandomUUID = UUID.randomUUID();
        int leastSignificantBits = (int) (uuidRandomUUID.getLeastSignificantBits() & 2147483647L);
        if (leastSignificantBits == 0 && (leastSignificantBits = (int) (uuidRandomUUID.getMostSignificantBits() & 2147483647L)) == 0) {
            Log.e("GAv4", "UUID.randomUUID() returned 0.");
            leastSignificantBits = Integer.MAX_VALUE;
        }
        this(false, leastSignificantBits);
    }

    private zzae(boolean z, int i) {
        bf.nP(i);
        this.zzuh = i;
        this.zzum = false;
    }

    public final String toString() {
        HashMap map = new HashMap();
        map.put("screenName", this.zzug);
        map.put("interstitial", Boolean.valueOf(this.zzul));
        map.put(AnalyticsEvents.PARAMETER_SHARE_DIALOG_SHOW_AUTOMATIC, Boolean.valueOf(this.zzum));
        map.put("screenId", Integer.valueOf(this.zzuh));
        map.put("referrerScreenId", Integer.valueOf(this.zzui));
        map.put("referrerScreenName", this.zzuj);
        map.put("referrerUri", this.zzuk);
        return zza((Object) map);
    }

    @Override // com.google.android.gms.a.l
    public final /* synthetic */ void zzb(l lVar) {
        zzae zzaeVar = (zzae) lVar;
        if (!TextUtils.isEmpty(this.zzug)) {
            zzaeVar.zzug = this.zzug;
        }
        if (this.zzuh != 0) {
            zzaeVar.zzuh = this.zzuh;
        }
        if (this.zzui != 0) {
            zzaeVar.zzui = this.zzui;
        }
        if (!TextUtils.isEmpty(this.zzuj)) {
            zzaeVar.zzuj = this.zzuj;
        }
        if (!TextUtils.isEmpty(this.zzuk)) {
            String str = this.zzuk;
            if (TextUtils.isEmpty(str)) {
                zzaeVar.zzuk = null;
            } else {
                zzaeVar.zzuk = str;
            }
        }
        if (this.zzul) {
            zzaeVar.zzul = this.zzul;
        }
        if (this.zzum) {
            zzaeVar.zzum = this.zzum;
        }
    }

    public final String zzbg() {
        return this.zzug;
    }

    public final int zzbh() {
        return this.zzuh;
    }

    public final String zzbi() {
        return this.zzuk;
    }
}
