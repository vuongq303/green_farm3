package com.google.android.gms.internal.ads;

import java.util.List;

/* JADX INFO: loaded from: classes.dex */
final class zzaca implements zzank<List<zzon>, zzoj> {
    private final /* synthetic */ String zzcan;
    private final /* synthetic */ Integer zzcao;
    private final /* synthetic */ Integer zzcap;
    private final /* synthetic */ int zzcaq;
    private final /* synthetic */ int zzcar;
    private final /* synthetic */ int zzcas;
    private final /* synthetic */ int zzcat;
    private final /* synthetic */ boolean zzcau;

    zzaca(zzabv zzabvVar, String str, Integer num, Integer num2, int i, int i2, int i3, int i4, boolean z) {
        this.zzcan = str;
        this.zzcao = num;
        this.zzcap = num2;
        this.zzcaq = i;
        this.zzcar = i2;
        this.zzcas = i3;
        this.zzcat = i4;
        this.zzcau = z;
    }

    @Override // com.google.android.gms.internal.ads.zzank
    public final /* synthetic */ zzoj apply(List<zzon> list) {
        List<zzon> list2 = list;
        if (list2 == null || list2.isEmpty()) {
            return null;
        }
        return new zzoj(this.zzcan, list2, this.zzcao, this.zzcap, this.zzcaq > 0 ? Integer.valueOf(this.zzcaq) : null, this.zzcar + this.zzcas, this.zzcat, this.zzcau);
    }
}
