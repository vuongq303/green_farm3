package com.google.android.gms.internal.ads;

import android.os.Bundle;
import android.view.View;
import com.google.android.gms.ads.formats.NativeAd;
import com.google.android.gms.ads.mediation.UnifiedNativeAdMapper;
import com.google.android.gms.c.a;
import com.google.android.gms.c.d;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/* JADX INFO: loaded from: classes.dex */
@zzadh
public final class zzze extends zzyg {
    private final UnifiedNativeAdMapper zzbvh;

    public zzze(UnifiedNativeAdMapper unifiedNativeAdMapper) {
        this.zzbvh = unifiedNativeAdMapper;
    }

    @Override // com.google.android.gms.internal.ads.zzyf
    public final String getAdvertiser() {
        return this.zzbvh.getAdvertiser();
    }

    @Override // com.google.android.gms.internal.ads.zzyf
    public final String getBody() {
        return this.zzbvh.getBody();
    }

    @Override // com.google.android.gms.internal.ads.zzyf
    public final String getCallToAction() {
        return this.zzbvh.getCallToAction();
    }

    @Override // com.google.android.gms.internal.ads.zzyf
    public final Bundle getExtras() {
        return this.zzbvh.getExtras();
    }

    @Override // com.google.android.gms.internal.ads.zzyf
    public final String getHeadline() {
        return this.zzbvh.getHeadline();
    }

    @Override // com.google.android.gms.internal.ads.zzyf
    public final List getImages() {
        List<NativeAd.Image> images = this.zzbvh.getImages();
        ArrayList arrayList = new ArrayList();
        if (images != null) {
            for (NativeAd.Image image : images) {
                arrayList.add(new zzon(image.getDrawable(), image.getUri(), image.getScale()));
            }
        }
        return arrayList;
    }

    @Override // com.google.android.gms.internal.ads.zzyf
    public final boolean getOverrideClickHandling() {
        return this.zzbvh.getOverrideClickHandling();
    }

    @Override // com.google.android.gms.internal.ads.zzyf
    public final boolean getOverrideImpressionRecording() {
        return this.zzbvh.getOverrideImpressionRecording();
    }

    @Override // com.google.android.gms.internal.ads.zzyf
    public final String getPrice() {
        return this.zzbvh.getPrice();
    }

    @Override // com.google.android.gms.internal.ads.zzyf
    public final double getStarRating() {
        if (this.zzbvh.getStarRating() != null) {
            return this.zzbvh.getStarRating().doubleValue();
        }
        return -1.0d;
    }

    @Override // com.google.android.gms.internal.ads.zzyf
    public final String getStore() {
        return this.zzbvh.getStore();
    }

    @Override // com.google.android.gms.internal.ads.zzyf
    public final zzlo getVideoController() {
        if (this.zzbvh.getVideoController() != null) {
            return this.zzbvh.getVideoController().zzbc();
        }
        return null;
    }

    @Override // com.google.android.gms.internal.ads.zzyf
    public final void recordImpression() {
        this.zzbvh.recordImpression();
    }

    @Override // com.google.android.gms.internal.ads.zzyf
    public final void zzb(a aVar, a aVar2, a aVar3) {
        this.zzbvh.trackViews((View) d.b(aVar), (HashMap) d.b(aVar2), (HashMap) d.b(aVar3));
    }

    @Override // com.google.android.gms.internal.ads.zzyf
    public final void zzj(a aVar) {
        this.zzbvh.handleClick((View) d.b(aVar));
    }

    @Override // com.google.android.gms.internal.ads.zzyf
    public final zzpw zzjz() {
        NativeAd.Image icon = this.zzbvh.getIcon();
        if (icon != null) {
            return new zzon(icon.getDrawable(), icon.getUri(), icon.getScale());
        }
        return null;
    }

    @Override // com.google.android.gms.internal.ads.zzyf
    public final a zzke() {
        Object objZzbh = this.zzbvh.zzbh();
        if (objZzbh == null) {
            return null;
        }
        return d.F(objZzbh);
    }

    @Override // com.google.android.gms.internal.ads.zzyf
    public final zzps zzkf() {
        return null;
    }

    @Override // com.google.android.gms.internal.ads.zzyf
    public final void zzl(a aVar) {
        this.zzbvh.untrackView((View) d.b(aVar));
    }

    @Override // com.google.android.gms.internal.ads.zzyf
    public final a zzmv() {
        View adChoicesContent = this.zzbvh.getAdChoicesContent();
        if (adChoicesContent == null) {
            return null;
        }
        return d.F(adChoicesContent);
    }

    @Override // com.google.android.gms.internal.ads.zzyf
    public final a zzmw() {
        View viewZzvy = this.zzbvh.zzvy();
        if (viewZzvy == null) {
            return null;
        }
        return d.F(viewZzvy);
    }
}
