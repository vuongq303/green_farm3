package com.google.android.gms.internal.ads;

import android.content.Context;
import android.os.RemoteException;
import com.google.android.gms.ads.doubleclick.PublisherAdView;
import com.google.android.gms.ads.formats.OnPublisherAdViewLoadedListener;
import com.google.android.gms.c.a;
import com.google.android.gms.c.d;

/* JADX INFO: loaded from: classes.dex */
public final class zzsb extends zzrj {
    private final OnPublisherAdViewLoadedListener zzblf;

    public zzsb(OnPublisherAdViewLoadedListener onPublisherAdViewLoadedListener) {
        this.zzblf = onPublisherAdViewLoadedListener;
    }

    @Override // com.google.android.gms.internal.ads.zzri
    public final void zza(zzks zzksVar, a aVar) {
        if (zzksVar == null || aVar == null) {
            return;
        }
        PublisherAdView publisherAdView = new PublisherAdView((Context) d.b(aVar));
        try {
            if (zzksVar.zzbx() instanceof zzjf) {
                zzjf zzjfVar = (zzjf) zzksVar.zzbx();
                publisherAdView.setAdListener(zzjfVar != null ? zzjfVar.getAdListener() : null);
            }
        } catch (RemoteException e) {
            zzane.zzb("", e);
        }
        try {
            if (zzksVar.zzbw() instanceof zzjp) {
                zzjp zzjpVar = (zzjp) zzksVar.zzbw();
                publisherAdView.setAppEventListener(zzjpVar != null ? zzjpVar.getAppEventListener() : null);
            }
        } catch (RemoteException e2) {
            zzane.zzb("", e2);
        }
        zzamu.zzsy.post(new zzsc(this, publisherAdView, zzksVar));
    }
}
