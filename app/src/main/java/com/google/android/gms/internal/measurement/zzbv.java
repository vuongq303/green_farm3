package com.google.android.gms.internal.measurement;

import android.util.Log;
import com.google.android.gms.a.c;

/* JADX INFO: loaded from: classes.dex */
final class zzbv implements c {
    private boolean zzrb;
    private int zzxx = 2;

    zzbv() {
    }

    public final void error(Exception exc) {
    }

    @Override // com.google.android.gms.a.c
    public final void error(String str) {
    }

    @Override // com.google.android.gms.a.c
    public final int getLogLevel() {
        return this.zzxx;
    }

    public final void info(String str) {
    }

    public final void setLogLevel(int i) {
        this.zzxx = i;
        if (this.zzrb) {
            return;
        }
        String str = zzcc.zzyh.get();
        String str2 = zzcc.zzyh.get();
        Log.i(str, new StringBuilder(String.valueOf(str2).length() + 91).append("Logger is deprecated. To enable debug logging, please run:\nadb shell setprop log.tag.").append(str2).append(" DEBUG").toString());
        this.zzrb = true;
    }

    @Override // com.google.android.gms.a.c
    public final void verbose(String str) {
    }

    @Override // com.google.android.gms.a.c
    public final void warn(String str) {
    }
}
