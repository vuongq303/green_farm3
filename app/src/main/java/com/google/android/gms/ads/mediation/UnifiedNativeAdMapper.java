package com.google.android.gms.ads.mediation;

import android.os.Bundle;
import android.view.View;
import com.google.android.gms.ads.VideoController;
import com.google.android.gms.ads.formats.NativeAd;
import com.google.android.gms.internal.ads.zzadh;
import java.util.List;
import java.util.Map;

/* JADX INFO: loaded from: classes.dex */
@zzadh
public class UnifiedNativeAdMapper {
    private String bDN;
    private List<NativeAd.Image> bDO;
    private NativeAd.Image bDP;
    private String bDQ;
    private String bDR;
    private Double bDS;
    private String bDT;
    private String bDU;
    private boolean bDV;
    private View bDW;
    private View bDX;
    private Object bDY;
    private boolean bDZ;
    private boolean bEa;
    private Bundle extras = new Bundle();
    private VideoController zzbkz;
    private String zzceo;

    public View getAdChoicesContent() {
        return this.bDW;
    }

    public final String getAdvertiser() {
        return this.bDR;
    }

    public final String getBody() {
        return this.zzceo;
    }

    public final String getCallToAction() {
        return this.bDQ;
    }

    public final Bundle getExtras() {
        return this.extras;
    }

    public final String getHeadline() {
        return this.bDN;
    }

    public final NativeAd.Image getIcon() {
        return this.bDP;
    }

    public final List<NativeAd.Image> getImages() {
        return this.bDO;
    }

    public final boolean getOverrideClickHandling() {
        return this.bEa;
    }

    public final boolean getOverrideImpressionRecording() {
        return this.bDZ;
    }

    public final String getPrice() {
        return this.bDU;
    }

    public final Double getStarRating() {
        return this.bDS;
    }

    public final String getStore() {
        return this.bDT;
    }

    public final VideoController getVideoController() {
        return this.zzbkz;
    }

    public void handleClick(View view) {
    }

    public boolean hasVideoContent() {
        return this.bDV;
    }

    public void recordImpression() {
    }

    public void setAdChoicesContent(View view) {
        this.bDW = view;
    }

    public final void setAdvertiser(String str) {
        this.bDR = str;
    }

    public final void setBody(String str) {
        this.zzceo = str;
    }

    public final void setCallToAction(String str) {
        this.bDQ = str;
    }

    public final void setExtras(Bundle bundle) {
        this.extras = bundle;
    }

    public void setHasVideoContent(boolean z) {
        this.bDV = z;
    }

    public final void setHeadline(String str) {
        this.bDN = str;
    }

    public final void setIcon(NativeAd.Image image) {
        this.bDP = image;
    }

    public final void setImages(List<NativeAd.Image> list) {
        this.bDO = list;
    }

    public void setMediaView(View view) {
        this.bDX = view;
    }

    public final void setOverrideClickHandling(boolean z) {
        this.bEa = z;
    }

    public final void setOverrideImpressionRecording(boolean z) {
        this.bDZ = z;
    }

    public final void setPrice(String str) {
        this.bDU = str;
    }

    public final void setStarRating(Double d) {
        this.bDS = d;
    }

    public final void setStore(String str) {
        this.bDT = str;
    }

    public void trackViews(View view, Map<String, View> map, Map<String, View> map2) {
    }

    public void untrackView(View view) {
    }

    public final void zza(VideoController videoController) {
        this.zzbkz = videoController;
    }

    public final Object zzbh() {
        return this.bDY;
    }

    public final void zzl(Object obj) {
        this.bDY = obj;
    }

    public final View zzvy() {
        return this.bDX;
    }
}
