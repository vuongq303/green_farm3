package com.google.android.gms.internal.games;

import android.content.Intent;
import com.google.android.gms.common.api.aa;
import com.google.android.gms.common.api.w;
import com.google.android.gms.games.achievement.b;
import com.google.android.gms.games.achievement.d;
import com.google.android.gms.games.c;

/* JADX INFO: loaded from: classes.dex */
public final class zzf implements b {
    public final Intent getAchievementsIntent(w wVar) {
        return c.a(wVar).ado();
    }

    public final void increment(w wVar, String str, int i) {
        wVar.b(new zzl(this, str, wVar, str, i));
    }

    public final aa<d> incrementImmediate(w wVar, String str, int i) {
        return wVar.b(new zzm(this, str, wVar, str, i));
    }

    public final aa<com.google.android.gms.games.achievement.c> load(w wVar, boolean z) {
        return wVar.a(new zzg(this, wVar, z));
    }

    public final void reveal(w wVar, String str) {
        wVar.b(new zzh(this, str, wVar, str));
    }

    public final aa<d> revealImmediate(w wVar, String str) {
        return wVar.b(new zzi(this, str, wVar, str));
    }

    public final void setSteps(w wVar, String str, int i) {
        wVar.b(new zzn(this, str, wVar, str, i));
    }

    public final aa<d> setStepsImmediate(w wVar, String str, int i) {
        return wVar.b(new zzo(this, str, wVar, str, i));
    }

    public final void unlock(w wVar, String str) {
        wVar.b(new zzj(this, str, wVar, str));
    }

    public final aa<d> unlockImmediate(w wVar, String str) {
        return wVar.b(new zzk(this, str, wVar, str));
    }
}
