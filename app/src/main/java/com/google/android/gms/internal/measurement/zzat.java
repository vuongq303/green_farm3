package com.google.android.gms.internal.measurement;

import android.content.Context;
import com.google.android.gms.a.b;
import com.google.android.gms.a.n;
import com.google.android.gms.common.d.e;
import com.google.android.gms.common.d.h;
import com.google.android.gms.common.internal.bf;

/* JADX INFO: loaded from: classes.dex */
public class zzat {
    private static volatile zzat zzvl;
    private final Context zzqs;
    private final e zzrj;
    private final Context zzvm;
    private final zzbu zzvn;
    private final zzcm zzvo;
    private final n zzvp;
    private final zzai zzvq;
    private final zzbz zzvr;
    private final zzde zzvs;
    private final zzcq zzvt;
    private final b zzvu;
    private final zzbl zzvv;
    private final zzah zzvw;
    private final zzbe zzvx;
    private final zzby zzvy;

    private zzat(zzav zzavVar) {
        Context applicationContext = zzavVar.getApplicationContext();
        bf.d(applicationContext, "Application context can't be null");
        Context contextZzci = zzavVar.zzci();
        bf.checkNotNull(contextZzci);
        this.zzqs = applicationContext;
        this.zzvm = contextZzci;
        this.zzrj = h.abr();
        this.zzvn = new zzbu(this);
        zzcm zzcmVar = new zzcm(this);
        zzcmVar.zzm();
        this.zzvo = zzcmVar;
        zzcm zzcmVarZzbu = zzbu();
        String str = zzas.VERSION;
        zzcmVarZzbu.zzs(new StringBuilder(String.valueOf(str).length() + 134).append("Google Analytics ").append(str).append(" is starting up. To enable debug logging on a device run:\n  adb shell setprop log.tag.GAv4 DEBUG\n  adb logcat -s GAv4").toString());
        zzcq zzcqVar = new zzcq(this);
        zzcqVar.zzm();
        this.zzvt = zzcqVar;
        zzde zzdeVar = new zzde(this);
        zzdeVar.zzm();
        this.zzvs = zzdeVar;
        zzai zzaiVar = new zzai(this, zzavVar);
        zzbl zzblVar = new zzbl(this);
        zzah zzahVar = new zzah(this);
        zzbe zzbeVar = new zzbe(this);
        zzby zzbyVar = new zzby(this);
        n nVarAx = n.ax(applicationContext);
        nVarAx.a(new zzau(this));
        this.zzvp = nVarAx;
        b bVar = new b(this);
        zzblVar.zzm();
        this.zzvv = zzblVar;
        zzahVar.zzm();
        this.zzvw = zzahVar;
        zzbeVar.zzm();
        this.zzvx = zzbeVar;
        zzbyVar.zzm();
        this.zzvy = zzbyVar;
        zzbz zzbzVar = new zzbz(this);
        zzbzVar.zzm();
        this.zzvr = zzbzVar;
        zzaiVar.zzm();
        this.zzvq = zzaiVar;
        bVar.zzm();
        this.zzvu = bVar;
        zzaiVar.start();
    }

    private static void zza(zzar zzarVar) {
        bf.d(zzarVar, "Analytics service not created/initialized");
        bf.b(zzarVar.isInitialized(), "Analytics service not initialized");
    }

    public static zzat zzc(Context context) {
        bf.checkNotNull(context);
        if (zzvl == null) {
            synchronized (zzat.class) {
                if (zzvl == null) {
                    e eVarAbr = h.abr();
                    long jElapsedRealtime = eVarAbr.elapsedRealtime();
                    zzat zzatVar = new zzat(new zzav(context));
                    zzvl = zzatVar;
                    b.zzn();
                    long jElapsedRealtime2 = eVarAbr.elapsedRealtime() - jElapsedRealtime;
                    long jLongValue = zzcc.zzzv.get().longValue();
                    if (jElapsedRealtime2 > jLongValue) {
                        zzatVar.zzbu().zzc("Slow initialization (ms)", Long.valueOf(jElapsedRealtime2), Long.valueOf(jLongValue));
                    }
                }
            }
        }
        return zzvl;
    }

    public final Context getContext() {
        return this.zzqs;
    }

    public final e zzbt() {
        return this.zzrj;
    }

    public final zzcm zzbu() {
        zza(this.zzvo);
        return this.zzvo;
    }

    public final zzbu zzbv() {
        return this.zzvn;
    }

    public final n zzbw() {
        bf.checkNotNull(this.zzvp);
        return this.zzvp;
    }

    public final zzai zzby() {
        zza(this.zzvq);
        return this.zzvq;
    }

    public final zzbz zzbz() {
        zza(this.zzvr);
        return this.zzvr;
    }

    public final zzde zzca() {
        zza(this.zzvs);
        return this.zzvs;
    }

    public final zzcq zzcb() {
        zza(this.zzvt);
        return this.zzvt;
    }

    public final zzbe zzce() {
        zza(this.zzvx);
        return this.zzvx;
    }

    public final zzby zzcf() {
        return this.zzvy;
    }

    public final Context zzci() {
        return this.zzvm;
    }

    public final zzcm zzcj() {
        return this.zzvo;
    }

    public final b zzck() {
        bf.checkNotNull(this.zzvu);
        bf.b(this.zzvu.isInitialized(), "Analytics instance not initialized");
        return this.zzvu;
    }

    public final zzcq zzcl() {
        if (this.zzvt == null || !this.zzvt.isInitialized()) {
            return null;
        }
        return this.zzvt;
    }

    public final zzah zzcm() {
        zza(this.zzvw);
        return this.zzvw;
    }

    public final zzbl zzcn() {
        zza(this.zzvv);
        return this.zzvv;
    }
}
