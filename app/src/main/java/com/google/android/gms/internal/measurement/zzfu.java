package com.google.android.gms.internal.measurement;

import android.content.SharedPreferences;
import com.google.android.gms.common.internal.bf;

/* JADX INFO: loaded from: classes.dex */
public final class zzfu {
    private long value;
    private boolean zzako;
    private final /* synthetic */ zzfr zzakp;
    private final long zzakq;
    private final String zznt;

    public zzfu(zzfr zzfrVar, String str, long j) {
        this.zzakp = zzfrVar;
        bf.id(str);
        this.zznt = str;
        this.zzakq = j;
    }

    public final long get() {
        if (!this.zzako) {
            this.zzako = true;
            this.value = this.zzakp.zziu().getLong(this.zznt, this.zzakq);
        }
        return this.value;
    }

    public final void set(long j) {
        SharedPreferences.Editor editorEdit = this.zzakp.zziu().edit();
        editorEdit.putLong(this.zznt, j);
        editorEdit.apply();
        this.value = j;
    }
}
