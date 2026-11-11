package com.google.android.gms.internal.measurement;

/* JADX INFO: loaded from: classes.dex */
final class zzdb extends zzaq implements zzbt<zzdc> {
    private final zzdc zzabq;

    public zzdb(zzat zzatVar) {
        super(zzatVar);
        this.zzabq = new zzdc();
    }

    @Override // com.google.android.gms.internal.measurement.zzbt
    public final void zza(String str, boolean z) {
        if ("ga_autoActivityTracking".equals(str)) {
            this.zzabq.zzabu = z ? 1 : 0;
            return;
        }
        if ("ga_anonymizeIp".equals(str)) {
            this.zzabq.zzabv = z ? 1 : 0;
        } else if (!"ga_reportUncaughtExceptions".equals(str)) {
            zzd("bool configuration name not recognized", str);
        } else {
            this.zzabq.zzabw = z ? 1 : 0;
        }
    }

    @Override // com.google.android.gms.internal.measurement.zzbt
    public final void zzb(String str, int i) {
        if ("ga_sessionTimeout".equals(str)) {
            this.zzabq.zzabt = i;
        } else {
            zzd("int configuration name not recognized", str);
        }
    }

    @Override // com.google.android.gms.internal.measurement.zzbt
    public final void zzb(String str, String str2) {
        this.zzabq.zzabx.put(str, str2);
    }

    @Override // com.google.android.gms.internal.measurement.zzbt
    public final void zzc(String str, String str2) {
        if ("ga_trackingId".equals(str)) {
            this.zzabq.zzabr = str2;
            return;
        }
        if (!"ga_sampleFrequency".equals(str)) {
            zzd("string configuration name not recognized", str);
            return;
        }
        try {
            this.zzabq.zzabs = Double.parseDouble(str2);
        } catch (NumberFormatException e) {
            zzc("Error parsing ga_sampleFrequency value", str2, e);
        }
    }

    @Override // com.google.android.gms.internal.measurement.zzbt
    public final /* synthetic */ zzbr zzdr() {
        return this.zzabq;
    }
}
