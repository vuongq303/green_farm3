package com.google.android.gms.internal.measurement;

import android.content.SharedPreferences;
import android.util.Pair;
import com.google.android.gms.common.internal.bf;

/* JADX INFO: loaded from: classes.dex */
public final class zzfv {
    private final long zzabe;
    private final /* synthetic */ zzfr zzakp;
    private final String zzakr;
    private final String zzaks;
    private final String zzakt;

    private zzfv(zzfr zzfrVar, String str, long j) {
        this.zzakp = zzfrVar;
        bf.id(str);
        bf.ca(j > 0);
        this.zzakr = String.valueOf(str).concat(":start");
        this.zzaks = String.valueOf(str).concat(":count");
        this.zzakt = String.valueOf(str).concat(":value");
        this.zzabe = j;
    }

    private final void zzfg() {
        this.zzakp.zzab();
        long jCurrentTimeMillis = this.zzakp.zzbt().currentTimeMillis();
        SharedPreferences.Editor editorEdit = this.zzakp.zziu().edit();
        editorEdit.remove(this.zzaks);
        editorEdit.remove(this.zzakt);
        editorEdit.putLong(this.zzakr, jCurrentTimeMillis);
        editorEdit.apply();
    }

    private final long zzfi() {
        return this.zzakp.zziu().getLong(this.zzakr, 0L);
    }

    public final void zzc(String str, long j) {
        this.zzakp.zzab();
        if (zzfi() == 0) {
            zzfg();
        }
        if (str == null) {
            str = "";
        }
        long j2 = this.zzakp.zziu().getLong(this.zzaks, 0L);
        if (j2 <= 0) {
            SharedPreferences.Editor editorEdit = this.zzakp.zziu().edit();
            editorEdit.putString(this.zzakt, str);
            editorEdit.putLong(this.zzaks, 1L);
            editorEdit.apply();
            return;
        }
        boolean z = (this.zzakp.zzgc().zzku().nextLong() & Long.MAX_VALUE) < Long.MAX_VALUE / (j2 + 1);
        SharedPreferences.Editor editorEdit2 = this.zzakp.zziu().edit();
        if (z) {
            editorEdit2.putString(this.zzakt, str);
        }
        editorEdit2.putLong(this.zzaks, j2 + 1);
        editorEdit2.apply();
    }

    public final Pair<String, Long> zzfh() {
        long jAbs;
        this.zzakp.zzab();
        this.zzakp.zzab();
        long jZzfi = zzfi();
        if (jZzfi == 0) {
            zzfg();
            jAbs = 0;
        } else {
            jAbs = Math.abs(jZzfi - this.zzakp.zzbt().currentTimeMillis());
        }
        if (jAbs < this.zzabe) {
            return null;
        }
        if (jAbs > (this.zzabe << 1)) {
            zzfg();
            return null;
        }
        String string = this.zzakp.zziu().getString(this.zzakt, null);
        long j = this.zzakp.zziu().getLong(this.zzaks, 0L);
        zzfg();
        return (string == null || j <= 0) ? zzfr.zzajr : new Pair<>(string, Long.valueOf(j));
    }
}
