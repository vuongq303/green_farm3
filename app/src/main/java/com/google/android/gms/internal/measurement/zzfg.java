package com.google.android.gms.internal.measurement;

import android.content.Context;
import android.text.TextUtils;
import android.util.Log;
import android.util.Pair;
import com.google.android.gms.common.d.e;
import com.google.android.gms.common.internal.bf;
import com.google.android.gms.measurement.a;

/* JADX INFO: loaded from: classes.dex */
public final class zzfg extends zzhk {
    private long zzadp;
    private char zzaim;
    private String zzain;
    private final zzfi zzaio;
    private final zzfi zzaip;
    private final zzfi zzaiq;
    private final zzfi zzair;
    private final zzfi zzais;
    private final zzfi zzait;
    private final zzfi zzaiu;
    private final zzfi zzaiv;
    private final zzfi zzaiw;

    zzfg(zzgl zzglVar) {
        super(zzglVar);
        this.zzaim = (char) 0;
        this.zzadp = -1L;
        this.zzaio = new zzfi(this, 6, false, false);
        this.zzaip = new zzfi(this, 6, true, false);
        this.zzaiq = new zzfi(this, 6, false, true);
        this.zzair = new zzfi(this, 5, false, false);
        this.zzais = new zzfi(this, 5, true, false);
        this.zzait = new zzfi(this, 5, false, true);
        this.zzaiu = new zzfi(this, 4, false, false);
        this.zzaiv = new zzfi(this, 3, false, false);
        this.zzaiw = new zzfi(this, 2, false, false);
    }

    static /* synthetic */ long zza(zzfg zzfgVar, long j) {
        zzfgVar.zzadp = 12451L;
        return 12451L;
    }

    private static String zza(boolean z, Object obj) {
        String className;
        if (obj == null) {
            return "";
        }
        Object objValueOf = obj instanceof Integer ? Long.valueOf(((Integer) obj).intValue()) : obj;
        if (objValueOf instanceof Long) {
            if (z && Math.abs(((Long) objValueOf).longValue()) >= 100) {
                String str = String.valueOf(objValueOf).charAt(0) == '-' ? "-" : "";
                String strValueOf = String.valueOf(Math.abs(((Long) objValueOf).longValue()));
                return new StringBuilder(String.valueOf(str).length() + 43 + String.valueOf(str).length()).append(str).append(Math.round(Math.pow(10.0d, strValueOf.length() - 1))).append("...").append(str).append(Math.round(Math.pow(10.0d, strValueOf.length()) - 1.0d)).toString();
            }
            return String.valueOf(objValueOf);
        }
        if (objValueOf instanceof Boolean) {
            return String.valueOf(objValueOf);
        }
        if (!(objValueOf instanceof Throwable)) {
            if (objValueOf instanceof zzfj) {
                return ((zzfj) objValueOf).zzajf;
            }
            return z ? "-" : String.valueOf(objValueOf);
        }
        Throwable th = (Throwable) objValueOf;
        StringBuilder sb = new StringBuilder(z ? th.getClass().getName() : th.toString());
        String strZzbi = zzbi(a.class.getCanonicalName());
        String strZzbi2 = zzbi(zzgl.class.getCanonicalName());
        for (StackTraceElement stackTraceElement : th.getStackTrace()) {
            if (!stackTraceElement.isNativeMethod() && (className = stackTraceElement.getClassName()) != null) {
                String strZzbi3 = zzbi(className);
                if (strZzbi3.equals(strZzbi) || strZzbi3.equals(strZzbi2)) {
                    sb.append(": ");
                    sb.append(stackTraceElement);
                    break;
                }
            }
        }
        return sb.toString();
    }

    static String zza(boolean z, String str, Object obj, Object obj2, Object obj3) {
        if (str == null) {
            str = "";
        }
        String strZza = zza(z, obj);
        String strZza2 = zza(z, obj2);
        String strZza3 = zza(z, obj3);
        StringBuilder sb = new StringBuilder();
        String str2 = "";
        if (!TextUtils.isEmpty(str)) {
            sb.append(str);
            str2 = ": ";
        }
        if (!TextUtils.isEmpty(strZza)) {
            sb.append(str2);
            sb.append(strZza);
            str2 = ", ";
        }
        if (!TextUtils.isEmpty(strZza2)) {
            sb.append(str2);
            sb.append(strZza2);
            str2 = ", ";
        }
        if (!TextUtils.isEmpty(strZza3)) {
            sb.append(str2);
            sb.append(strZza3);
        }
        return sb.toString();
    }

    protected static Object zzbh(String str) {
        if (str == null) {
            return null;
        }
        return new zzfj(str);
    }

    private static String zzbi(String str) {
        if (TextUtils.isEmpty(str)) {
            return "";
        }
        int iLastIndexOf = str.lastIndexOf(46);
        return iLastIndexOf != -1 ? str.substring(0, iLastIndexOf) : str;
    }

    private final String zzis() {
        String str;
        synchronized (this) {
            if (this.zzain == null) {
                this.zzain = zzew.zzagl.get();
            }
            str = this.zzain;
        }
        return str;
    }

    @Override // com.google.android.gms.internal.measurement.zzhj
    public final /* bridge */ /* synthetic */ Context getContext() {
        return super.getContext();
    }

    protected final boolean isLoggable(int i) {
        return Log.isLoggable(zzis(), i);
    }

    protected final void zza(int i, String str) {
        Log.println(i, zzis(), str);
    }

    protected final void zza(int i, boolean z, boolean z2, String str, Object obj, Object obj2, Object obj3) {
        if (!z && isLoggable(i)) {
            zza(i, zza(false, str, obj, obj2, obj3));
        }
        if (z2 || i < 5) {
            return;
        }
        bf.checkNotNull(str);
        zzgg zzggVarZzjn = this.zzacr.zzjn();
        if (zzggVarZzjn == null) {
            zza(6, "Scheduler not set. Not logging error/warn");
            return;
        }
        if (!zzggVarZzjn.isInitialized()) {
            zza(6, "Scheduler not initialized. Not logging error/warn");
            return;
        }
        int i2 = i >= 0 ? i : 0;
        if (i2 >= 9) {
            i2 = 8;
        }
        zzggVarZzjn.zzc(new zzfh(this, i2, str, obj, obj2, obj3));
    }

    @Override // com.google.android.gms.internal.measurement.zzhj
    public final /* bridge */ /* synthetic */ void zzab() {
        super.zzab();
    }

    @Override // com.google.android.gms.internal.measurement.zzhj
    public final /* bridge */ /* synthetic */ e zzbt() {
        return super.zzbt();
    }

    @Override // com.google.android.gms.internal.measurement.zzhj
    public final /* bridge */ /* synthetic */ void zzfq() {
        super.zzfq();
    }

    @Override // com.google.android.gms.internal.measurement.zzhj
    public final /* bridge */ /* synthetic */ void zzfr() {
        super.zzfr();
    }

    @Override // com.google.android.gms.internal.measurement.zzhj
    public final /* bridge */ /* synthetic */ zzdx zzfs() {
        return super.zzfs();
    }

    @Override // com.google.android.gms.internal.measurement.zzhj
    public final /* bridge */ /* synthetic */ zzee zzft() {
        return super.zzft();
    }

    @Override // com.google.android.gms.internal.measurement.zzhj
    public final /* bridge */ /* synthetic */ zzhm zzfu() {
        return super.zzfu();
    }

    @Override // com.google.android.gms.internal.measurement.zzhj
    public final /* bridge */ /* synthetic */ zzfb zzfv() {
        return super.zzfv();
    }

    @Override // com.google.android.gms.internal.measurement.zzhj
    public final /* bridge */ /* synthetic */ zzeo zzfw() {
        return super.zzfw();
    }

    @Override // com.google.android.gms.internal.measurement.zzhj
    public final /* bridge */ /* synthetic */ zzil zzfx() {
        return super.zzfx();
    }

    @Override // com.google.android.gms.internal.measurement.zzhj
    public final /* bridge */ /* synthetic */ zzih zzfy() {
        return super.zzfy();
    }

    @Override // com.google.android.gms.internal.measurement.zzhj
    public final /* bridge */ /* synthetic */ zzfc zzfz() {
        return super.zzfz();
    }

    @Override // com.google.android.gms.internal.measurement.zzhj
    public final /* bridge */ /* synthetic */ zzei zzga() {
        return super.zzga();
    }

    @Override // com.google.android.gms.internal.measurement.zzhj
    public final /* bridge */ /* synthetic */ zzfe zzgb() {
        return super.zzgb();
    }

    @Override // com.google.android.gms.internal.measurement.zzhj
    public final /* bridge */ /* synthetic */ zzjv zzgc() {
        return super.zzgc();
    }

    @Override // com.google.android.gms.internal.measurement.zzhj
    public final /* bridge */ /* synthetic */ zzgf zzgd() {
        return super.zzgd();
    }

    @Override // com.google.android.gms.internal.measurement.zzhj
    public final /* bridge */ /* synthetic */ zzjk zzge() {
        return super.zzge();
    }

    @Override // com.google.android.gms.internal.measurement.zzhj
    public final /* bridge */ /* synthetic */ zzgg zzgf() {
        return super.zzgf();
    }

    @Override // com.google.android.gms.internal.measurement.zzhj
    public final /* bridge */ /* synthetic */ zzfg zzgg() {
        return super.zzgg();
    }

    @Override // com.google.android.gms.internal.measurement.zzhj
    public final /* bridge */ /* synthetic */ zzfr zzgh() {
        return super.zzgh();
    }

    @Override // com.google.android.gms.internal.measurement.zzhj
    public final /* bridge */ /* synthetic */ zzeh zzgi() {
        return super.zzgi();
    }

    @Override // com.google.android.gms.internal.measurement.zzhk
    protected final boolean zzhh() {
        return false;
    }

    public final zzfi zzil() {
        return this.zzaio;
    }

    public final zzfi zzim() {
        return this.zzaip;
    }

    public final zzfi zzin() {
        return this.zzair;
    }

    public final zzfi zzio() {
        return this.zzait;
    }

    public final zzfi zzip() {
        return this.zzaiu;
    }

    public final zzfi zziq() {
        return this.zzaiv;
    }

    public final zzfi zzir() {
        return this.zzaiw;
    }

    public final String zzit() {
        Pair<String, Long> pairZzfh = zzgh().zzajs.zzfh();
        if (pairZzfh == null || pairZzfh == zzfr.zzajr) {
            return null;
        }
        String strValueOf = String.valueOf(pairZzfh.second);
        String str = (String) pairZzfh.first;
        return new StringBuilder(String.valueOf(strValueOf).length() + 1 + String.valueOf(str).length()).append(strValueOf).append(":").append(str).toString();
    }
}
