package com.google.android.gms.internal.ads;

import android.content.Context;
import android.content.pm.ApplicationInfo;
import com.google.android.gms.common.d.s;

/* JADX INFO: loaded from: classes.dex */
@zzadh
public final class zzapo extends zzaph {
    @Override // com.google.android.gms.internal.ads.zzaph
    public final zzapg zza(Context context, zzapw zzapwVar, int i, boolean z, zznx zznxVar, zzapv zzapvVar) {
        ApplicationInfo applicationInfo = context.getApplicationInfo();
        if (s.abv() && (applicationInfo == null || applicationInfo.targetSdkVersion >= 11)) {
            return new zzaov(context, z, zzapwVar.zzud().zzvs(), zzapvVar, new zzapx(context, zzapwVar.zztq(), zzapwVar.zzol(), zznxVar, zzapwVar.zztn()));
        }
        return null;
    }
}
