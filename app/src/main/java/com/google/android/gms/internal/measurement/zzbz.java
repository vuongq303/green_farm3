package com.google.android.gms.internal.measurement;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.app.job.JobInfo;
import android.app.job.JobScheduler;
import android.content.ComponentName;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.PersistableBundle;
import com.facebook.internal.NativeProtocol;
import com.google.android.gms.common.internal.bf;

/* JADX INFO: loaded from: classes.dex */
public final class zzbz extends zzar {
    private boolean zzyb;
    private boolean zzyc;
    private final AlarmManager zzyd;
    private Integer zzye;

    protected zzbz(zzat zzatVar) {
        super(zzatVar);
        this.zzyd = (AlarmManager) getContext().getSystemService("alarm");
    }

    private final int getJobId() {
        if (this.zzye == null) {
            String strValueOf = String.valueOf(getContext().getPackageName());
            this.zzye = Integer.valueOf((strValueOf.length() != 0 ? "analytics".concat(strValueOf) : new String("analytics")).hashCode());
        }
        return this.zzye.intValue();
    }

    private final PendingIntent zzek() {
        Intent intent = new Intent("com.google.android.gms.analytics.ANALYTICS_DISPATCH");
        intent.setComponent(new ComponentName(getContext(), "com.google.android.gms.analytics.AnalyticsReceiver"));
        return PendingIntent.getBroadcast(getContext(), 0, intent, 0);
    }

    public final void cancel() {
        this.zzyc = false;
        this.zzyd.cancel(zzek());
        if (Build.VERSION.SDK_INT >= 24) {
            JobScheduler jobScheduler = (JobScheduler) getContext().getSystemService("jobscheduler");
            zza("Cancelling job. JobID", Integer.valueOf(getJobId()));
            jobScheduler.cancel(getJobId());
        }
    }

    @Override // com.google.android.gms.internal.measurement.zzar
    protected final void zzac() {
        ActivityInfo receiverInfo;
        try {
            cancel();
            if (zzbu.zzdw() <= 0 || (receiverInfo = getContext().getPackageManager().getReceiverInfo(new ComponentName(getContext(), "com.google.android.gms.analytics.AnalyticsReceiver"), 2)) == null || !receiverInfo.enabled) {
                return;
            }
            zzq("Receiver registered for local dispatch.");
            this.zzyb = true;
        } catch (PackageManager.NameNotFoundException e) {
        }
    }

    public final boolean zzef() {
        return this.zzyc;
    }

    public final boolean zzei() {
        return this.zzyb;
    }

    public final void zzej() {
        zzch();
        bf.a(this.zzyb, "Receiver not registered");
        long jZzdw = zzbu.zzdw();
        if (jZzdw > 0) {
            cancel();
            long jElapsedRealtime = zzbt().elapsedRealtime() + jZzdw;
            this.zzyc = true;
            if (Build.VERSION.SDK_INT < 24) {
                zzq("Scheduling upload with AlarmManager");
                this.zzyd.setInexactRepeating(2, jElapsedRealtime, jZzdw, zzek());
                return;
            }
            zzq("Scheduling upload with JobScheduler");
            ComponentName componentName = new ComponentName(getContext(), "com.google.android.gms.analytics.AnalyticsJobService");
            JobScheduler jobScheduler = (JobScheduler) getContext().getSystemService("jobscheduler");
            JobInfo.Builder builder = new JobInfo.Builder(getJobId(), componentName);
            builder.setMinimumLatency(jZzdw);
            builder.setOverrideDeadline(jZzdw << 1);
            PersistableBundle persistableBundle = new PersistableBundle();
            persistableBundle.putString(NativeProtocol.WEB_DIALOG_ACTION, "com.google.android.gms.analytics.ANALYTICS_DISPATCH");
            builder.setExtras(persistableBundle);
            JobInfo jobInfoBuild = builder.build();
            zza("Scheduling job. JobID", Integer.valueOf(getJobId()));
            jobScheduler.schedule(jobInfoBuild);
        }
    }
}
