package com.google.android.gms.ads.internal;

import android.content.Context;
import android.text.TextUtils;
import com.google.android.gms.ads.formats.PublisherAdViewOptions;
import com.google.android.gms.internal.ads.zzadh;
import com.google.android.gms.internal.ads.zzang;
import com.google.android.gms.internal.ads.zzjn;
import com.google.android.gms.internal.ads.zzkh;
import com.google.android.gms.internal.ads.zzkk;
import com.google.android.gms.internal.ads.zzko;
import com.google.android.gms.internal.ads.zzlg;
import com.google.android.gms.internal.ads.zzpl;
import com.google.android.gms.internal.ads.zzqw;
import com.google.android.gms.internal.ads.zzqz;
import com.google.android.gms.internal.ads.zzrc;
import com.google.android.gms.internal.ads.zzrf;
import com.google.android.gms.internal.ads.zzri;
import com.google.android.gms.internal.ads.zzrl;
import com.google.android.gms.internal.ads.zzxn;

/* JADX INFO: loaded from: classes.dex */
@zzadh
public final class zzak extends zzko {
    private zzjn bBa;
    private PublisherAdViewOptions bBb;
    private zzlg bBe;
    private zzqw bBh;
    private zzrl bBi;
    private zzqz bBj;
    private zzri bBm;
    private final Context mContext;
    private final zzw zzwc;
    private final zzxn zzwh;
    private zzkh zzxs;
    private zzpl zzyb;
    private final String zzye;
    private final zzang zzyf;
    private android.support.v4.e.r<String, zzrf> bBl = new android.support.v4.e.r<>();
    private android.support.v4.e.r<String, zzrc> bBk = new android.support.v4.e.r<>();

    public zzak(Context context, String str, zzxn zzxnVar, zzang zzangVar, zzw zzwVar) {
        this.mContext = context;
        this.zzye = str;
        this.zzwh = zzxnVar;
        this.zzyf = zzangVar;
        this.zzwc = zzwVar;
    }

    @Override // com.google.android.gms.internal.ads.zzkn
    public final void zza(PublisherAdViewOptions publisherAdViewOptions) {
        this.bBb = publisherAdViewOptions;
    }

    @Override // com.google.android.gms.internal.ads.zzkn
    public final void zza(zzpl zzplVar) {
        this.zzyb = zzplVar;
    }

    @Override // com.google.android.gms.internal.ads.zzkn
    public final void zza(zzqw zzqwVar) {
        this.bBh = zzqwVar;
    }

    @Override // com.google.android.gms.internal.ads.zzkn
    public final void zza(zzqz zzqzVar) {
        this.bBj = zzqzVar;
    }

    @Override // com.google.android.gms.internal.ads.zzkn
    public final void zza(zzri zzriVar, zzjn zzjnVar) {
        this.bBm = zzriVar;
        this.bBa = zzjnVar;
    }

    @Override // com.google.android.gms.internal.ads.zzkn
    public final void zza(zzrl zzrlVar) {
        this.bBi = zzrlVar;
    }

    @Override // com.google.android.gms.internal.ads.zzkn
    public final void zza(String str, zzrf zzrfVar, zzrc zzrcVar) {
        if (TextUtils.isEmpty(str)) {
            throw new IllegalArgumentException("Custom template ID for native custom template ad is empty. Please provide a valid template id.");
        }
        this.bBl.put(str, zzrfVar);
        this.bBk.put(str, zzrcVar);
    }

    @Override // com.google.android.gms.internal.ads.zzkn
    public final void zzb(zzkh zzkhVar) {
        this.zzxs = zzkhVar;
    }

    @Override // com.google.android.gms.internal.ads.zzkn
    public final void zzb(zzlg zzlgVar) {
        this.bBe = zzlgVar;
    }

    @Override // com.google.android.gms.internal.ads.zzkn
    public final zzkk zzdh() {
        return new zzah(this.mContext, this.zzye, this.zzwh, this.zzyf, this.zzxs, this.bBh, this.bBi, this.bBj, this.bBl, this.bBk, this.zzyb, this.bBe, this.zzwc, this.bBm, this.bBa, this.bBb);
    }
}
