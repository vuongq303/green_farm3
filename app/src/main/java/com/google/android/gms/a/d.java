package com.google.android.gms.a;

import android.net.Uri;
import android.text.TextUtils;
import com.google.android.gms.common.internal.bf;
import com.google.android.gms.internal.measurement.zzad;
import com.google.android.gms.internal.measurement.zzah;
import com.google.android.gms.internal.measurement.zzat;
import java.util.ListIterator;

/* JADX INFO: loaded from: classes.dex */
public class d extends m<d> {
    private boolean bEp;
    private final zzat zzqh;

    public d(zzat zzatVar) {
        super(zzatVar.zzbw(), zzatVar.zzbt());
        this.zzqh = zzatVar;
    }

    final zzat Xm() {
        return this.zzqh;
    }

    @Override // com.google.android.gms.a.m
    public final j Xn() {
        j jVarXp = this.bEC.Xp();
        jVarXp.a(this.zzqh.zzce().zzdb());
        jVarXp.a(this.zzqh.zzcf().zzeg());
        d(jVarXp);
        return jVarXp;
    }

    @Override // com.google.android.gms.a.m
    protected final void a(j jVar) {
        zzad zzadVar = (zzad) jVar.b(zzad.class);
        if (TextUtils.isEmpty(zzadVar.zzaz())) {
            zzadVar.setClientId(this.zzqh.zzcn().zzdn());
        }
        if (this.bEp && TextUtils.isEmpty(zzadVar.zzbb())) {
            zzah zzahVarZzcm = this.zzqh.zzcm();
            zzadVar.zzm(zzahVarZzcm.zzbj());
            zzadVar.zza(zzahVarZzcm.zzbc());
        }
    }

    public final void bW(boolean z) {
        this.bEp = z;
    }

    public final void hJ(String str) {
        bf.id(str);
        Uri uriHK = e.hK(str);
        ListIterator<u> listIterator = this.bEC.zzq().listIterator();
        while (listIterator.hasNext()) {
            if (uriHK.equals(listIterator.next().Xo())) {
                listIterator.remove();
            }
        }
        this.bEC.zzq().add(new e(this.zzqh, str));
    }
}
