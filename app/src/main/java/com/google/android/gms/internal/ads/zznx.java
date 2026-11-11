package com.google.android.gms.internal.ads;

import android.text.TextUtils;
import com.facebook.internal.NativeProtocol;
import java.util.LinkedHashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import javax.annotation.ParametersAreNonnullByDefault;

/* JADX INFO: loaded from: classes.dex */
@ParametersAreNonnullByDefault
@zzadh
public final class zznx {
    private boolean zzbgn;
    private String zzbgq;
    private zznx zzbgr;
    private final List<zznv> zzbgo = new LinkedList();
    private final Map<String, String> zzbgp = new LinkedHashMap();
    private final Object mLock = new Object();

    public zznx(boolean z, String str, String str2) {
        this.zzbgn = z;
        this.zzbgp.put(NativeProtocol.WEB_DIALOG_ACTION, str);
        this.zzbgp.put("ad_format", str2);
    }

    public final boolean zza(zznv zznvVar, long j, String... strArr) {
        synchronized (this.mLock) {
            for (String str : strArr) {
                this.zzbgo.add(new zznv(j, str, zznvVar));
            }
        }
        return true;
    }

    public final boolean zza(zznv zznvVar, String... strArr) {
        if (!this.zzbgn || zznvVar == null) {
            return false;
        }
        return zza(zznvVar, com.google.android.gms.ads.internal.zzbv.zzer().elapsedRealtime(), strArr);
    }

    public final void zzan(String str) {
        if (this.zzbgn) {
            synchronized (this.mLock) {
                this.zzbgq = str;
            }
        }
    }

    public final void zzc(zznx zznxVar) {
        synchronized (this.mLock) {
            this.zzbgr = zznxVar;
        }
    }

    public final zznv zzd(long j) {
        if (this.zzbgn) {
            return new zznv(j, null, null);
        }
        return null;
    }

    public final void zze(String str, String str2) {
        zznn zznnVarZzpy;
        if (!this.zzbgn || TextUtils.isEmpty(str2) || (zznnVarZzpy = com.google.android.gms.ads.internal.zzbv.zzeo().zzpy()) == null) {
            return;
        }
        synchronized (this.mLock) {
            zznr zznrVarZzal = zznnVarZzpy.zzal(str);
            Map<String, String> map = this.zzbgp;
            map.put(str, zznrVarZzal.zzd(map.get(str), str2));
        }
    }

    public final zznv zzjj() {
        return zzd(com.google.android.gms.ads.internal.zzbv.zzer().elapsedRealtime());
    }

    public final String zzjk() {
        String string;
        StringBuilder sb = new StringBuilder();
        synchronized (this.mLock) {
            for (zznv zznvVar : this.zzbgo) {
                long time = zznvVar.getTime();
                String strZzjg = zznvVar.zzjg();
                zznv zznvVarZzjh = zznvVar.zzjh();
                if (zznvVarZzjh != null && time > 0) {
                    sb.append(strZzjg).append('.').append(time - zznvVarZzjh.getTime()).append(',');
                }
            }
            this.zzbgo.clear();
            if (!TextUtils.isEmpty(this.zzbgq)) {
                sb.append(this.zzbgq);
            } else if (sb.length() > 0) {
                sb.setLength(sb.length() - 1);
            }
            string = sb.toString();
        }
        return string;
    }

    final Map<String, String> zzjl() {
        Map<String, String> mapZza;
        synchronized (this.mLock) {
            zznn zznnVarZzpy = com.google.android.gms.ads.internal.zzbv.zzeo().zzpy();
            mapZza = (zznnVarZzpy == null || this.zzbgr == null) ? this.zzbgp : zznnVarZzpy.zza(this.zzbgp, this.zzbgr.zzjl());
        }
        return mapZza;
    }

    public final zznv zzjm() {
        synchronized (this.mLock) {
        }
        return null;
    }
}
