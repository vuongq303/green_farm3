package com.google.android.gms.ads.internal;

import android.app.Activity;
import android.content.Context;
import android.view.MotionEvent;
import android.view.View;
import com.google.android.gms.internal.ads.zzadh;
import com.google.android.gms.internal.ads.zzakb;
import com.google.android.gms.internal.ads.zzaki;
import com.google.android.gms.internal.ads.zzamu;
import com.google.android.gms.internal.ads.zzang;
import com.google.android.gms.internal.ads.zzce;
import com.google.android.gms.internal.ads.zzch;
import com.google.android.gms.internal.ads.zzkb;
import com.google.android.gms.internal.ads.zznk;
import java.util.List;
import java.util.Vector;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.atomic.AtomicReference;

/* JADX INFO: loaded from: classes.dex */
@zzadh
public final class zzag implements zzce, Runnable {
    private final AtomicReference<zzce> bAT;
    private zzang bAU;
    private CountDownLatch bAV;
    private Context zzrt;
    private final List<Object[]> zzxo;

    private zzag(Context context, zzang zzangVar) {
        this.zzxo = new Vector();
        this.bAT = new AtomicReference<>();
        this.bAV = new CountDownLatch(1);
        this.zzrt = context;
        this.bAU = zzangVar;
        zzkb.zzif();
        if (zzamu.zzsh()) {
            zzaki.zzb(this);
        } else {
            run();
        }
    }

    public zzag(zzbw zzbwVar) {
        this(zzbwVar.zzrt, zzbwVar.zzacr);
    }

    private final boolean WU() {
        try {
            this.bAV.await();
            return true;
        } catch (InterruptedException e) {
            zzakb.zzc("Interrupted during GADSignals creation.", e);
            return false;
        }
    }

    private static Context aw(Context context) {
        Context applicationContext = context.getApplicationContext();
        return applicationContext == null ? context : applicationContext;
    }

    private final void zzdd() {
        if (this.zzxo.isEmpty()) {
            return;
        }
        for (Object[] objArr : this.zzxo) {
            if (objArr.length == 1) {
                this.bAT.get().zza((MotionEvent) objArr[0]);
            } else if (objArr.length == 3) {
                this.bAT.get().zza(((Integer) objArr[0]).intValue(), ((Integer) objArr[1]).intValue(), ((Integer) objArr[2]).intValue());
            }
        }
        this.zzxo.clear();
    }

    /* JADX WARN: Multi-variable type inference failed */
    @Override // java.lang.Runnable
    public final void run() {
        try {
            this.bAT.set(zzch.zza(this.bAU.zzcw, aw(this.zzrt), !((Boolean) zzkb.zzik().zzd(zznk.zzayl)).booleanValue() && (this.bAU.zzcvg) == true));
        } finally {
            this.bAV.countDown();
            this.zzrt = null;
            this.bAU = null;
        }
    }

    @Override // com.google.android.gms.internal.ads.zzce
    public final String zza(Context context) {
        zzce zzceVar;
        if (!WU() || (zzceVar = this.bAT.get()) == null) {
            return "";
        }
        zzdd();
        return zzceVar.zza(aw(context));
    }

    @Override // com.google.android.gms.internal.ads.zzce
    public final String zza(Context context, String str, View view) {
        return zza(context, str, view, null);
    }

    @Override // com.google.android.gms.internal.ads.zzce
    public final String zza(Context context, String str, View view, Activity activity) {
        zzce zzceVar;
        if (!WU() || (zzceVar = this.bAT.get()) == null) {
            return "";
        }
        zzdd();
        return zzceVar.zza(aw(context), str, view, activity);
    }

    @Override // com.google.android.gms.internal.ads.zzce
    public final void zza(int i, int i2, int i3) {
        zzce zzceVar = this.bAT.get();
        if (zzceVar == null) {
            this.zzxo.add(new Object[]{Integer.valueOf(i), Integer.valueOf(i2), Integer.valueOf(i3)});
        } else {
            zzdd();
            zzceVar.zza(i, i2, i3);
        }
    }

    @Override // com.google.android.gms.internal.ads.zzce
    public final void zza(MotionEvent motionEvent) {
        zzce zzceVar = this.bAT.get();
        if (zzceVar == null) {
            this.zzxo.add(new Object[]{motionEvent});
        } else {
            zzdd();
            zzceVar.zza(motionEvent);
        }
    }

    @Override // com.google.android.gms.internal.ads.zzce
    public final void zzb(View view) {
        zzce zzceVar = this.bAT.get();
        if (zzceVar != null) {
            zzceVar.zzb(view);
        }
    }
}
