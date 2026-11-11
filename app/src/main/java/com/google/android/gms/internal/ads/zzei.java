package com.google.android.gms.internal.ads;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.concurrent.Callable;

/* JADX INFO: loaded from: classes.dex */
public abstract class zzei implements Callable {
    private final String TAG = getClass().getSimpleName();
    private final String className;
    protected final zzcz zzps;
    protected final zzba zztq;
    private final String zztx;
    protected Method zztz;
    private final int zzud;
    private final int zzue;

    public zzei(zzcz zzczVar, String str, String str2, zzba zzbaVar, int i, int i2) {
        this.zzps = zzczVar;
        this.className = str;
        this.zztx = str2;
        this.zztq = zzbaVar;
        this.zzud = i;
        this.zzue = i2;
    }

    protected abstract void zzar();

    @Override // java.util.concurrent.Callable
    /* JADX INFO: renamed from: zzat, reason: merged with bridge method [inline-methods] */
    public Void call() {
        try {
            long jNanoTime = System.nanoTime();
            this.zztz = this.zzps.zza(this.className, this.zztx);
            if (this.zztz != null) {
                zzar();
                zzcc zzccVarZzag = this.zzps.zzag();
                if (zzccVarZzag != null && this.zzud != Integer.MIN_VALUE) {
                    zzccVarZzag.zza(this.zzue, this.zzud, (System.nanoTime() - jNanoTime) / 1000);
                }
            }
        } catch (IllegalAccessException e) {
        } catch (InvocationTargetException e2) {
        }
        return null;
    }
}
