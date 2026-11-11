package com.google.android.gms.internal.ads;

import android.os.Bundle;
import android.view.View;
import com.google.android.gms.ads.formats.NativeAd;
import com.google.android.gms.ads.mediation.NativeContentAdMapper;
import com.google.android.gms.c.a;
import com.google.android.gms.c.d;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/* JADX INFO: loaded from: classes.dex */
@zzadh
public final class zzyn extends zzyd {
    private final NativeContentAdMapper zzbuz;

    public zzyn(NativeContentAdMapper nativeContentAdMapper) {
        this.zzbuz = nativeContentAdMapper;
    }

    @Override // com.google.android.gms.internal.ads.zzyc
    public final String getAdvertiser() {
        return this.zzbuz.getAdvertiser();
    }

    @Override // com.google.android.gms.internal.ads.zzyc
    public final String getBody() {
        return this.zzbuz.getBody();
    }

    @Override // com.google.android.gms.internal.ads.zzyc
    public final String getCallToAction() {
        return this.zzbuz.getCallToAction();
    }

    @Override // com.google.android.gms.internal.ads.zzyc
    public final Bundle getExtras() {
        return this.zzbuz.getExtras();
    }

    @Override // com.google.android.gms.internal.ads.zzyc
    public final String getHeadline() {
        return this.zzbuz.getHeadline();
    }

    @Override // com.google.android.gms.internal.ads.zzyc
    public final List getImages() {
        List<NativeAd.Image> images = this.zzbuz.getImages();
        if (images == null) {
            return null;
        }
        ArrayList arrayList = new ArrayList();
        for (NativeAd.Image image : images) {
            arrayList.add(new zzon(image.getDrawable(), image.getUri(), image.getScale()));
        }
        return arrayList;
    }

    @Override // com.google.android.gms.internal.ads.zzyc
    public final boolean getOverrideClickHandling() {
        return this.zzbuz.getOverrideClickHandling();
    }

    @Override // com.google.android.gms.internal.ads.zzyc
    public final boolean getOverrideImpressionRecording() {
        return this.zzbuz.getOverrideImpressionRecording();
    }

    @Override // com.google.android.gms.internal.ads.zzyc
    public final zzlo getVideoController() {
        if (this.zzbuz.getVideoController() != null) {
            return this.zzbuz.getVideoController().zzbc();
        }
        return null;
    }

    @Override // com.google.android.gms.internal.ads.zzyc
    public final void recordImpression() {
        this.zzbuz.recordImpression();
    }

    @Override // com.google.android.gms.internal.ads.zzyc
    public final void zzb(a aVar, a aVar2, a aVar3) {
        this.zzbuz.trackViews((View) d.b(aVar), (HashMap) d.b(aVar2), (HashMap) d.b(aVar3));
    }

    @Override // com.google.android.gms.internal.ads.zzyc
    public final void zzj(a aVar) {
        this.zzbuz.handleClick((View) d.b(aVar));
    }

    @Override // com.google.android.gms.internal.ads.zzyc
    public final void zzk(a aVar) {
        this.zzbuz.trackView((View) d.b(aVar));
    }

    @Override // com.google.android.gms.internal.ads.zzyc
    public final a zzke() {
        return null;
    }

    @Override // com.google.android.gms.internal.ads.zzyc
    public final zzps zzkf() {
        return null;
    }

    @Override // com.google.android.gms.internal.ads.zzyc
    public final zzpw zzkg() {
        NativeAd.Image logo = this.zzbuz.getLogo();
        if (logo != null) {
            return new zzon(logo.getDrawable(), logo.getUri(), logo.getScale());
        }
        return null;
    }

    @Override // com.google.android.gms.internal.ads.zzyc
    public final void zzl(a aVar) {
        this.zzbuz.untrackView((View) d.b(aVar));
    }

    @Override // com.google.android.gms.internal.ads.zzyc
    public final a zzmv() {
        View adChoicesContent = this.zzbuz.getAdChoicesContent();
        if (adChoicesContent == null) {
            return null;
        }
        return d.F(adChoicesContent);
    }

    @Override // com.google.android.gms.internal.ads.zzyc
    public final a zzmw() {
        View viewZzvy = this.zzbuz.zzvy();
        if (viewZzvy == null) {
            return null;
        }
        return d.F(viewZzvy);
    }
}
