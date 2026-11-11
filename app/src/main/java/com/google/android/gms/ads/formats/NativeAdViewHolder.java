package com.google.android.gms.ads.formats;

import android.os.RemoteException;
import android.view.View;
import com.google.android.gms.c.d;
import com.google.android.gms.common.internal.bf;
import com.google.android.gms.internal.ads.zzane;
import com.google.android.gms.internal.ads.zzkb;
import com.google.android.gms.internal.ads.zzqf;
import java.lang.ref.WeakReference;
import java.util.HashMap;
import java.util.Map;
import java.util.WeakHashMap;

/* JADX INFO: loaded from: classes.dex */
public final class NativeAdViewHolder {
    public static WeakHashMap<View, NativeAdViewHolder> zzvk = new WeakHashMap<>();
    private zzqf bzE;
    private WeakReference<View> bzF;

    public NativeAdViewHolder(View view, Map<String, View> map, Map<String, View> map2) {
        bf.d(view, "ContainerView must not be null");
        if ((view instanceof NativeAdView) || (view instanceof UnifiedNativeAdView)) {
            zzane.e("The provided containerView is of type of NativeAdView, which cannot be usedwith NativeAdViewHolder.");
            return;
        }
        if (zzvk.get(view) != null) {
            zzane.e("The provided containerView is already in use with another NativeAdViewHolder.");
            return;
        }
        zzvk.put(view, this);
        this.bzF = new WeakReference<>(view);
        this.bzE = zzkb.zzig().zza(view, zzb(map), zzb(map2));
    }

    private final void zza(com.google.android.gms.c.a aVar) {
        View view = this.bzF != null ? this.bzF.get() : null;
        if (view == null) {
            zzane.zzdk("NativeAdViewHolder.setNativeAd containerView doesn't exist, returning");
            return;
        }
        if (!zzvk.containsKey(view)) {
            zzvk.put(view, this);
        }
        if (this.bzE != null) {
            try {
                this.bzE.zza(aVar);
            } catch (RemoteException e) {
                zzane.zzb("Unable to call setNativeAd on delegate", e);
            }
        }
    }

    private static HashMap<String, View> zzb(Map<String, View> map) {
        return map == null ? new HashMap<>() : new HashMap<>(map);
    }

    public final void setClickConfirmingView(View view) {
        try {
            this.bzE.zzc(d.F(view));
        } catch (RemoteException e) {
            zzane.zzb("Unable to call setClickConfirmingView on delegate", e);
        }
    }

    public final void setNativeAd(NativeAd nativeAd) {
        zza((com.google.android.gms.c.a) nativeAd.zzbe());
    }

    public final void setNativeAd(UnifiedNativeAd unifiedNativeAd) {
        zza((com.google.android.gms.c.a) unifiedNativeAd.zzbe());
    }

    public final void unregisterNativeAd() {
        if (this.bzE != null) {
            try {
                this.bzE.unregisterNativeAd();
            } catch (RemoteException e) {
                zzane.zzb("Unable to call unregisterNativeAd on delegate", e);
            }
        }
        View view = this.bzF != null ? this.bzF.get() : null;
        if (view != null) {
            zzvk.remove(view);
        }
    }
}
