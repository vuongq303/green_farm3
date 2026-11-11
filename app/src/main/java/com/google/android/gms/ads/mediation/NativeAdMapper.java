package com.google.android.gms.ads.mediation;

import android.os.Bundle;
import android.view.View;
import com.google.android.gms.ads.VideoController;
import com.google.android.gms.internal.ads.zzadh;
import java.util.Map;

/* JADX INFO: loaded from: classes.dex */
@zzadh
public class NativeAdMapper {
    protected boolean bDG;
    protected boolean bDH;
    protected View bDI;
    private View bDJ;
    private boolean bDK;
    protected Bundle mExtras = new Bundle();
    private VideoController zzasv;

    public View getAdChoicesContent() {
        return this.bDI;
    }

    public final Bundle getExtras() {
        return this.mExtras;
    }

    public final boolean getOverrideClickHandling() {
        return this.bDH;
    }

    public final boolean getOverrideImpressionRecording() {
        return this.bDG;
    }

    public final VideoController getVideoController() {
        return this.zzasv;
    }

    public void handleClick(View view) {
    }

    public boolean hasVideoContent() {
        return this.bDK;
    }

    public void recordImpression() {
    }

    public void setAdChoicesContent(View view) {
        this.bDI = view;
    }

    public final void setExtras(Bundle bundle) {
        this.mExtras = bundle;
    }

    public void setHasVideoContent(boolean z) {
        this.bDK = z;
    }

    public void setMediaView(View view) {
        this.bDJ = view;
    }

    public final void setOverrideClickHandling(boolean z) {
        this.bDH = z;
    }

    public final void setOverrideImpressionRecording(boolean z) {
        this.bDG = z;
    }

    @Deprecated
    public void trackView(View view) {
    }

    public void trackViews(View view, Map<String, View> map, Map<String, View> map2) {
    }

    public void untrackView(View view) {
    }

    public final void zza(VideoController videoController) {
        this.zzasv = videoController;
    }

    public final View zzvy() {
        return this.bDJ;
    }
}
