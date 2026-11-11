package com.google.android.gms.internal.measurement;

import android.app.Activity;
import android.app.Application;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.text.TextUtils;
import com.facebook.share.internal.ShareConstants;

/* JADX INFO: loaded from: classes.dex */
final class zzif implements Application.ActivityLifecycleCallbacks {
    private final /* synthetic */ zzhm zzaop;

    private zzif(zzhm zzhmVar) {
        this.zzaop = zzhmVar;
    }

    /* synthetic */ zzif(zzhm zzhmVar, zzhn zzhnVar) {
        this(zzhmVar);
    }

    @Override // android.app.Application.ActivityLifecycleCallbacks
    public final void onActivityCreated(Activity activity, Bundle bundle) {
        Bundle bundle2;
        Uri data;
        try {
            this.zzaop.zzgg().zzir().log("onActivityCreated");
            Intent intent = activity.getIntent();
            if (intent != null && (data = intent.getData()) != null && data.isHierarchical()) {
                if (bundle == null) {
                    Bundle bundleZza = this.zzaop.zzgc().zza(data);
                    this.zzaop.zzgc();
                    String str = zzjv.zzd(intent) ? "gs" : "auto";
                    if (bundleZza != null) {
                        this.zzaop.zza(str, "_cmp", bundleZza);
                    }
                }
                String queryParameter = data.getQueryParameter("referrer");
                if (TextUtils.isEmpty(queryParameter)) {
                    return;
                }
                if (!(queryParameter.contains("gclid") && (queryParameter.contains("utm_campaign") || queryParameter.contains("utm_source") || queryParameter.contains("utm_medium") || queryParameter.contains("utm_term") || queryParameter.contains("utm_content")))) {
                    this.zzaop.zzgg().zziq().log("Activity created with data 'referrer' param without gclid and at least one utm field");
                    return;
                } else {
                    this.zzaop.zzgg().zziq().zzg("Activity created with referrer", queryParameter);
                    if (!TextUtils.isEmpty(queryParameter)) {
                        this.zzaop.zza("auto", "_ldl", queryParameter);
                    }
                }
            }
        } catch (Throwable th) {
            this.zzaop.zzgg().zzil().zzg("Throwable caught in onActivityCreated", th);
        }
        zzih zzihVarZzfy = this.zzaop.zzfy();
        if (bundle == null || (bundle2 = bundle.getBundle("com.google.firebase.analytics.screen_service")) == null) {
            return;
        }
        zzik zzikVarZze = zzihVarZzfy.zze(activity);
        zzikVarZze.zzapb = bundle2.getLong(ShareConstants.WEB_DIALOG_PARAM_ID);
        zzikVarZze.zzug = bundle2.getString("name");
        zzikVarZze.zzapa = bundle2.getString("referrer_name");
    }

    @Override // android.app.Application.ActivityLifecycleCallbacks
    public final void onActivityDestroyed(Activity activity) {
        this.zzaop.zzfy().onActivityDestroyed(activity);
    }

    @Override // android.app.Application.ActivityLifecycleCallbacks
    public final void onActivityPaused(Activity activity) {
        this.zzaop.zzfy().onActivityPaused(activity);
        zzjk zzjkVarZzge = this.zzaop.zzge();
        zzjkVarZzge.zzgf().zzc(new zzjo(zzjkVarZzge, zzjkVarZzge.zzbt().elapsedRealtime()));
    }

    @Override // android.app.Application.ActivityLifecycleCallbacks
    public final void onActivityResumed(Activity activity) {
        this.zzaop.zzfy().onActivityResumed(activity);
        zzjk zzjkVarZzge = this.zzaop.zzge();
        zzjkVarZzge.zzgf().zzc(new zzjn(zzjkVarZzge, zzjkVarZzge.zzbt().elapsedRealtime()));
    }

    @Override // android.app.Application.ActivityLifecycleCallbacks
    public final void onActivitySaveInstanceState(Activity activity, Bundle bundle) {
        this.zzaop.zzfy().onActivitySaveInstanceState(activity, bundle);
    }

    @Override // android.app.Application.ActivityLifecycleCallbacks
    public final void onActivityStarted(Activity activity) {
    }

    @Override // android.app.Application.ActivityLifecycleCallbacks
    public final void onActivityStopped(Activity activity) {
    }
}
