package com.google.android.gms.internal.measurement;

import android.content.SharedPreferences;
import com.google.android.gms.common.internal.bf;

/* JADX INFO: loaded from: classes.dex */
public final class zzfw {
    private String value;
    private boolean zzako;
    private final /* synthetic */ zzfr zzakp;
    private final String zzaku;
    private final String zznt;

    public zzfw(zzfr zzfrVar, String str, String str2) {
        this.zzakp = zzfrVar;
        bf.id(str);
        this.zznt = str;
        this.zzaku = null;
    }

    public final void zzbn(String str) {
        if (zzjv.zzs(str, this.value)) {
            return;
        }
        SharedPreferences.Editor editorEdit = this.zzakp.zziu().edit();
        editorEdit.putString(this.zznt, str);
        editorEdit.apply();
        this.value = str;
    }

    public final String zzjc() {
        if (!this.zzako) {
            this.zzako = true;
            this.value = this.zzakp.zziu().getString(this.zznt, null);
        }
        return this.value;
    }
}
