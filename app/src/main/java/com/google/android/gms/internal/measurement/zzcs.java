package com.google.android.gms.internal.measurement;

import android.content.SharedPreferences;
import android.util.Pair;
import com.google.android.gms.common.internal.bf;
import java.util.UUID;

/* JADX INFO: loaded from: classes.dex */
public final class zzcs {
    private final String name;
    private final long zzabe;
    private final /* synthetic */ zzcq zzabf;

    private zzcs(zzcq zzcqVar, String str, long j) {
        this.zzabf = zzcqVar;
        bf.id(str);
        bf.ca(j > 0);
        this.name = str;
        this.zzabe = j;
    }

    private final void zzfg() {
        long jCurrentTimeMillis = this.zzabf.zzbt().currentTimeMillis();
        SharedPreferences.Editor editorEdit = this.zzabf.zzaba.edit();
        editorEdit.remove(zzfk());
        editorEdit.remove(zzfl());
        editorEdit.putLong(zzfj(), jCurrentTimeMillis);
        editorEdit.commit();
    }

    private final long zzfi() {
        return this.zzabf.zzaba.getLong(zzfj(), 0L);
    }

    private final String zzfj() {
        return String.valueOf(this.name).concat(":start");
    }

    private final String zzfk() {
        return String.valueOf(this.name).concat(":count");
    }

    private final String zzfl() {
        return String.valueOf(this.name).concat(":value");
    }

    public final void zzad(String str) {
        if (zzfi() == 0) {
            zzfg();
        }
        if (str == null) {
            str = "";
        }
        synchronized (this) {
            long j = this.zzabf.zzaba.getLong(zzfk(), 0L);
            if (j <= 0) {
                SharedPreferences.Editor editorEdit = this.zzabf.zzaba.edit();
                editorEdit.putString(zzfl(), str);
                editorEdit.putLong(zzfk(), 1L);
                editorEdit.apply();
                return;
            }
            boolean z = (UUID.randomUUID().getLeastSignificantBits() & Long.MAX_VALUE) < Long.MAX_VALUE / (j + 1);
            SharedPreferences.Editor editorEdit2 = this.zzabf.zzaba.edit();
            if (z) {
                editorEdit2.putString(zzfl(), str);
            }
            editorEdit2.putLong(zzfk(), j + 1);
            editorEdit2.apply();
        }
    }

    public final Pair<String, Long> zzfh() {
        long jZzfi = zzfi();
        long jAbs = jZzfi == 0 ? 0L : Math.abs(jZzfi - this.zzabf.zzbt().currentTimeMillis());
        if (jAbs < this.zzabe) {
            return null;
        }
        if (jAbs > (this.zzabe << 1)) {
            zzfg();
            return null;
        }
        String string = this.zzabf.zzaba.getString(zzfl(), null);
        long j = this.zzabf.zzaba.getLong(zzfk(), 0L);
        zzfg();
        if (string == null || j <= 0) {
            return null;
        }
        return new Pair<>(string, Long.valueOf(j));
    }
}
