package com.google.android.gms.internal.ads;

import android.net.Uri;
import android.os.Handler;
import android.os.Looper;
import android.text.TextUtils;
import java.util.Collections;
import java.util.Map;

/* JADX INFO: loaded from: classes.dex */
public abstract class zzr<T> implements Comparable<zzr<T>> {
    private final Object mLock;
    private final zzaf.zza zzae;
    private final int zzaf;
    private final String zzag;
    private final int zzah;
    private zzy zzai;
    private Integer zzaj;
    private zzv zzak;
    private boolean zzal;
    private boolean zzam;
    private boolean zzan;
    private boolean zzao;
    private zzab zzap;
    private zzc zzaq;
    private zzt zzar;

    public zzr(int i, String str, zzy zzyVar) {
        Uri uri;
        String host;
        this.zzae = zzaf.zza.zzbk ? new zzaf.zza() : null;
        this.mLock = new Object();
        this.zzal = true;
        this.zzam = false;
        this.zzan = false;
        this.zzao = false;
        this.zzaq = null;
        this.zzaf = i;
        this.zzag = str;
        this.zzai = zzyVar;
        this.zzap = new zzh();
        this.zzah = (TextUtils.isEmpty(str) || (uri = Uri.parse(str)) == null || (host = uri.getHost()) == null) ? 0 : host.hashCode();
    }

    @Override // java.lang.Comparable
    public /* synthetic */ int compareTo(Object obj) {
        zzr zzrVar = (zzr) obj;
        zzu zzuVar = zzu.NORMAL;
        zzu zzuVar2 = zzu.NORMAL;
        return zzuVar == zzuVar2 ? this.zzaj.intValue() - zzrVar.zzaj.intValue() : zzuVar2.ordinal() - zzuVar.ordinal();
    }

    public Map<String, String> getHeaders() {
        return Collections.emptyMap();
    }

    public final int getMethod() {
        return this.zzaf;
    }

    public final String getUrl() {
        return this.zzag;
    }

    public final boolean isCanceled() {
        synchronized (this.mLock) {
        }
        return false;
    }

    public String toString() {
        String strValueOf = String.valueOf(Integer.toHexString(this.zzah));
        String strConcat = strValueOf.length() != 0 ? "0x".concat(strValueOf) : new String("0x");
        String str = this.zzag;
        String strValueOf2 = String.valueOf(zzu.NORMAL);
        String strValueOf3 = String.valueOf(this.zzaj);
        return new StringBuilder(String.valueOf("[ ] ").length() + 3 + String.valueOf(str).length() + String.valueOf(strConcat).length() + String.valueOf(strValueOf2).length() + String.valueOf(strValueOf3).length()).append("[ ] ").append(str).append(" ").append(strConcat).append(" ").append(strValueOf2).append(" ").append(strValueOf3).toString();
    }

    /* JADX WARN: Multi-variable type inference failed */
    public final zzr<?> zza(int i) {
        this.zzaj = Integer.valueOf(i);
        return this;
    }

    /* JADX WARN: Multi-variable type inference failed */
    public final zzr<?> zza(zzc zzcVar) {
        this.zzaq = zzcVar;
        return this;
    }

    /* JADX WARN: Multi-variable type inference failed */
    public final zzr<?> zza(zzv zzvVar) {
        this.zzak = zzvVar;
        return this;
    }

    protected abstract zzx<T> zza(zzp zzpVar);

    final void zza(zzt zztVar) {
        synchronized (this.mLock) {
            this.zzar = zztVar;
        }
    }

    final void zza(zzx<?> zzxVar) {
        zzt zztVar;
        synchronized (this.mLock) {
            zztVar = this.zzar;
        }
        if (zztVar != null) {
            zztVar.zza(this, zzxVar);
        }
    }

    protected abstract void zza(T t);

    public final void zzb(zzae zzaeVar) {
        zzy zzyVar;
        synchronized (this.mLock) {
            zzyVar = this.zzai;
        }
        if (zzyVar != null) {
            zzyVar.zzd(zzaeVar);
        }
    }

    public final void zzb(String str) {
        if (zzaf.zza.zzbk) {
            this.zzae.zza(str, Thread.currentThread().getId());
        }
    }

    final void zzc(String str) {
        if (this.zzak != null) {
            this.zzak.zzf(this);
        }
        if (zzaf.zza.zzbk) {
            long id = Thread.currentThread().getId();
            if (Looper.myLooper() != Looper.getMainLooper()) {
                new Handler(Looper.getMainLooper()).post(new zzs(this, str, id));
            } else {
                this.zzae.zza(str, id);
                this.zzae.zzc(toString());
            }
        }
    }

    public final int zze() {
        return this.zzah;
    }

    public final zzc zzf() {
        return this.zzaq;
    }

    public byte[] zzg() {
        return null;
    }

    public final boolean zzh() {
        return this.zzal;
    }

    public final int zzi() {
        return this.zzap.zzc();
    }

    public final zzab zzj() {
        return this.zzap;
    }

    public final void zzk() {
        synchronized (this.mLock) {
            this.zzan = true;
        }
    }

    public final boolean zzl() {
        boolean z;
        synchronized (this.mLock) {
            z = this.zzan;
        }
        return z;
    }

    final void zzm() {
        zzt zztVar;
        synchronized (this.mLock) {
            zztVar = this.zzar;
        }
        if (zztVar != null) {
            zztVar.zza(this);
        }
    }
}
