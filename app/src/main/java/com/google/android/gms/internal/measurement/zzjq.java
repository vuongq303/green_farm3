package com.google.android.gms.internal.measurement;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.app.job.JobInfo;
import android.app.job.JobScheduler;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.os.PersistableBundle;
import com.facebook.internal.NativeProtocol;
import com.google.android.gms.common.d.e;

/* JADX INFO: loaded from: classes.dex */
public final class zzjq extends zzhk {
    private final zzem zzaqs;
    private final AlarmManager zzyd;
    private Integer zzye;

    protected zzjq(zzgl zzglVar) {
        super(zzglVar);
        this.zzyd = (AlarmManager) getContext().getSystemService("alarm");
        this.zzaqs = new zzjr(this, zzglVar, zzglVar);
    }

    private final int getJobId() {
        if (this.zzye == null) {
            String strValueOf = String.valueOf(getContext().getPackageName());
            this.zzye = Integer.valueOf((strValueOf.length() != 0 ? "measurement".concat(strValueOf) : new String("measurement")).hashCode());
        }
        return this.zzye.intValue();
    }

    private final PendingIntent zzek() {
        Intent className = new Intent().setClassName(getContext(), "com.google.android.gms.measurement.AppMeasurementReceiver");
        className.setAction("com.google.android.gms.measurement.UPLOAD");
        return PendingIntent.getBroadcast(getContext(), 0, className, 0);
    }

    private final void zzks() {
        JobScheduler jobScheduler = (JobScheduler) getContext().getSystemService("jobscheduler");
        zzgg().zzir().zzg("Cancelling job. JobID", Integer.valueOf(getJobId()));
        jobScheduler.cancel(getJobId());
    }

    public final void cancel() {
        zzch();
        this.zzyd.cancel(zzek());
        this.zzaqs.cancel();
        if (Build.VERSION.SDK_INT >= 24) {
            zzks();
        }
    }

    @Override // com.google.android.gms.internal.measurement.zzhj
    public final /* bridge */ /* synthetic */ Context getContext() {
        return super.getContext();
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

    public final void zzh(long j) {
        zzch();
        if (!zzgb.zza(getContext())) {
            zzgg().zziq().log("Receiver not registered/enabled");
        }
        if (!zzjf.zza(getContext(), false)) {
            zzgg().zziq().log("Service not registered/enabled");
        }
        cancel();
        long jElapsedRealtime = zzbt().elapsedRealtime() + j;
        if (j < Math.max(0L, zzew.zzahi.get().longValue()) && !this.zzaqs.zzef()) {
            zzgg().zzir().log("Scheduling upload with DelayedRunnable");
            this.zzaqs.zzh(j);
        }
        if (Build.VERSION.SDK_INT < 24) {
            zzgg().zzir().log("Scheduling upload with AlarmManager");
            this.zzyd.setInexactRepeating(2, jElapsedRealtime, Math.max(zzew.zzahd.get().longValue(), j), zzek());
            return;
        }
        zzgg().zzir().log("Scheduling upload with JobScheduler");
        ComponentName componentName = new ComponentName(getContext(), "com.google.android.gms.measurement.AppMeasurementJobService");
        JobScheduler jobScheduler = (JobScheduler) getContext().getSystemService("jobscheduler");
        JobInfo.Builder builder = new JobInfo.Builder(getJobId(), componentName);
        builder.setMinimumLatency(j);
        builder.setOverrideDeadline(j << 1);
        PersistableBundle persistableBundle = new PersistableBundle();
        persistableBundle.putString(NativeProtocol.WEB_DIALOG_ACTION, "com.google.android.gms.measurement.UPLOAD");
        builder.setExtras(persistableBundle);
        JobInfo jobInfoBuild = builder.build();
        zzgg().zzir().zzg("Scheduling job. JobID", Integer.valueOf(getJobId()));
        jobScheduler.schedule(jobInfoBuild);
    }

    @Override // com.google.android.gms.internal.measurement.zzhk
    protected final boolean zzhh() {
        this.zzyd.cancel(zzek());
        if (Build.VERSION.SDK_INT < 24) {
            return false;
        }
        zzks();
        return false;
    }
}
