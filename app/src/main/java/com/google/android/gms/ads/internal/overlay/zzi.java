package com.google.android.gms.ads.internal.overlay;

import android.content.Context;
import android.view.ViewGroup;
import android.view.ViewParent;
import com.google.android.gms.internal.ads.zzadh;
import com.google.android.gms.internal.ads.zzaqw;

/* JADX INFO: loaded from: classes.dex */
@zzadh
public final class zzi {
    public final int index;
    public final ViewGroup parent;
    public final ViewGroup.LayoutParams zzbyi;
    public final Context zzrt;

    public zzi(zzaqw zzaqwVar) throws c {
        this.zzbyi = zzaqwVar.getLayoutParams();
        ViewParent parent = zzaqwVar.getParent();
        this.zzrt = zzaqwVar.zzua();
        if (parent == null || !(parent instanceof ViewGroup)) {
            throw new c("Could not get the parent of the WebView for an overlay.");
        }
        this.parent = (ViewGroup) parent;
        this.index = this.parent.indexOfChild(zzaqwVar.getView());
        this.parent.removeView(zzaqwVar.getView());
        zzaqwVar.zzai(true);
    }
}
