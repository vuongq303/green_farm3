package com.google.android.gms.internal.measurement;

import android.content.SharedPreferences;
import com.google.android.gms.common.internal.bf;

/* JADX INFO: loaded from: classes.dex */
public final class zzft {
    private boolean value;
    private final boolean zzakn;
    private boolean zzako;
    private final /* synthetic */ zzfr zzakp;
    private final String zznt;

    public zzft(zzfr zzfrVar, String str, boolean z) {
        this.zzakp = zzfrVar;
        bf.id(str);
        this.zznt = str;
        this.zzakn = true;
    }

    public final boolean get() {
        if (!this.zzako) {
            this.zzako = true;
            this.value = this.zzakp.zziu().getBoolean(this.zznt, this.zzakn);
        }
        return this.value;
    }

    public final void set(boolean z) {
        SharedPreferences.Editor editorEdit = this.zzakp.zziu().edit();
        editorEdit.putBoolean(this.zznt, z);
        editorEdit.apply();
        this.value = z;
    }
}
