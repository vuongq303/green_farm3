package com.google.android.gms.internal.measurement;

import android.content.SharedPreferences;
import android.text.TextUtils;
import com.google.android.gms.a.n;

/* JADX INFO: loaded from: classes.dex */
public final class zzcq extends zzar {
    private SharedPreferences zzaba;
    private long zzabb;
    private long zzabc;
    private final zzcs zzabd;

    protected zzcq(zzat zzatVar) {
        super(zzatVar);
        this.zzabc = -1L;
        this.zzabd = new zzcs(this, "monitoring", zzcc.zzzu.get().longValue());
    }

    @Override // com.google.android.gms.internal.measurement.zzar
    protected final void zzac() {
        this.zzaba = getContext().getSharedPreferences("com.google.android.gms.analytics.prefs", 0);
    }

    public final void zzac(String str) {
        n.zzab();
        zzch();
        SharedPreferences.Editor editorEdit = this.zzaba.edit();
        if (TextUtils.isEmpty(str)) {
            editorEdit.remove("installation_campaign");
        } else {
            editorEdit.putString("installation_campaign", str);
        }
        if (editorEdit.commit()) {
            return;
        }
        zzt("Failed to commit campaign data");
    }

    public final long zzfa() {
        n.zzab();
        zzch();
        if (this.zzabb == 0) {
            long j = this.zzaba.getLong("first_run", 0L);
            if (j != 0) {
                this.zzabb = j;
            } else {
                long jCurrentTimeMillis = zzbt().currentTimeMillis();
                SharedPreferences.Editor editorEdit = this.zzaba.edit();
                editorEdit.putLong("first_run", jCurrentTimeMillis);
                if (!editorEdit.commit()) {
                    zzt("Failed to commit first run time");
                }
                this.zzabb = jCurrentTimeMillis;
            }
        }
        return this.zzabb;
    }

    public final zzcz zzfb() {
        return new zzcz(zzbt(), zzfa());
    }

    public final long zzfc() {
        n.zzab();
        zzch();
        if (this.zzabc == -1) {
            this.zzabc = this.zzaba.getLong("last_dispatch", 0L);
        }
        return this.zzabc;
    }

    public final void zzfd() {
        n.zzab();
        zzch();
        long jCurrentTimeMillis = zzbt().currentTimeMillis();
        SharedPreferences.Editor editorEdit = this.zzaba.edit();
        editorEdit.putLong("last_dispatch", jCurrentTimeMillis);
        editorEdit.apply();
        this.zzabc = jCurrentTimeMillis;
    }

    public final String zzfe() {
        n.zzab();
        zzch();
        String string = this.zzaba.getString("installation_campaign", null);
        if (TextUtils.isEmpty(string)) {
            return null;
        }
        return string;
    }

    public final zzcs zzff() {
        return this.zzabd;
    }
}
