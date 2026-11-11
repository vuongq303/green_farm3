package com.google.android.gms.internal.ads;

import android.content.Context;
import android.os.IBinder;
import android.os.RemoteException;
import com.google.android.gms.ads.VideoController;
import com.google.android.gms.ads.formats.MediaView;
import com.google.android.gms.ads.formats.NativeAd;
import com.google.android.gms.ads.formats.NativeCustomTemplateAd;
import com.google.android.gms.c.d;
import java.util.List;
import java.util.WeakHashMap;

/* JADX INFO: loaded from: classes.dex */
@zzadh
public final class zzqv implements NativeCustomTemplateAd {
    private static WeakHashMap<IBinder, zzqv> zzbkt = new WeakHashMap<>();
    private final VideoController zzasv = new VideoController();
    private final zzqs zzbku;
    private final MediaView zzbkv;

    private zzqv(zzqs zzqsVar) {
        Context context;
        MediaView mediaView = null;
        this.zzbku = zzqsVar;
        try {
            context = (Context) d.b(zzqsVar.zzkh());
        } catch (RemoteException | NullPointerException e) {
            zzane.zzb("", e);
            context = null;
        }
        if (context != null) {
            MediaView mediaView2 = new MediaView(context);
            try {
                mediaView = this.zzbku.zzh(d.F(mediaView2)) ? mediaView2 : null;
            } catch (RemoteException e2) {
                zzane.zzb("", e2);
            }
        }
        this.zzbkv = mediaView;
    }

    public static zzqv zza(zzqs zzqsVar) {
        zzqv zzqvVar;
        synchronized (zzbkt) {
            zzqvVar = zzbkt.get(zzqsVar.asBinder());
            if (zzqvVar == null) {
                zzqvVar = new zzqv(zzqsVar);
                zzbkt.put(zzqsVar.asBinder(), zzqvVar);
            }
        }
        return zzqvVar;
    }

    @Override // com.google.android.gms.ads.formats.NativeCustomTemplateAd
    public final void destroy() {
        try {
            this.zzbku.destroy();
        } catch (RemoteException e) {
            zzane.zzb("", e);
        }
    }

    @Override // com.google.android.gms.ads.formats.NativeCustomTemplateAd
    public final List<String> getAvailableAssetNames() {
        try {
            return this.zzbku.getAvailableAssetNames();
        } catch (RemoteException e) {
            zzane.zzb("", e);
            return null;
        }
    }

    @Override // com.google.android.gms.ads.formats.NativeCustomTemplateAd
    public final String getCustomTemplateId() {
        try {
            return this.zzbku.getCustomTemplateId();
        } catch (RemoteException e) {
            zzane.zzb("", e);
            return null;
        }
    }

    @Override // com.google.android.gms.ads.formats.NativeCustomTemplateAd
    public final NativeAd.Image getImage(String str) {
        try {
            zzpw zzpwVarZzap = this.zzbku.zzap(str);
            if (zzpwVarZzap != null) {
                return new zzpz(zzpwVarZzap);
            }
        } catch (RemoteException e) {
            zzane.zzb("", e);
        }
        return null;
    }

    @Override // com.google.android.gms.ads.formats.NativeCustomTemplateAd
    public final CharSequence getText(String str) {
        try {
            return this.zzbku.zzao(str);
        } catch (RemoteException e) {
            zzane.zzb("", e);
            return null;
        }
    }

    @Override // com.google.android.gms.ads.formats.NativeCustomTemplateAd
    public final VideoController getVideoController() {
        try {
            zzlo videoController = this.zzbku.getVideoController();
            if (videoController != null) {
                this.zzasv.zza(videoController);
            }
        } catch (RemoteException e) {
            zzane.zzb("Exception occurred while getting video controller", e);
        }
        return this.zzasv;
    }

    @Override // com.google.android.gms.ads.formats.NativeCustomTemplateAd
    public final MediaView getVideoMediaView() {
        return this.zzbkv;
    }

    @Override // com.google.android.gms.ads.formats.NativeCustomTemplateAd
    public final void performClick(String str) {
        try {
            this.zzbku.performClick(str);
        } catch (RemoteException e) {
            zzane.zzb("", e);
        }
    }

    @Override // com.google.android.gms.ads.formats.NativeCustomTemplateAd
    public final void recordImpression() {
        try {
            this.zzbku.recordImpression();
        } catch (RemoteException e) {
            zzane.zzb("", e);
        }
    }

    public final zzqs zzku() {
        return this.zzbku;
    }
}
