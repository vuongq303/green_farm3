package com.google.android.gms.ads.internal;

import android.app.Activity;
import android.content.Context;
import android.view.View;
import android.widget.FrameLayout;
import com.google.android.gms.ads.internal.overlay.AdOverlayInfoParcel;
import com.google.android.gms.ads.internal.overlay.zzr;
import com.google.android.gms.ads.internal.overlay.zzs;
import com.google.android.gms.internal.ads.zzaap;
import com.google.android.gms.internal.ads.zzaaz;
import com.google.android.gms.internal.ads.zzadh;
import com.google.android.gms.internal.ads.zzagq;
import com.google.android.gms.internal.ads.zzagz;
import com.google.android.gms.internal.ads.zzakk;
import com.google.android.gms.internal.ads.zzang;
import com.google.android.gms.internal.ads.zzjn;
import com.google.android.gms.internal.ads.zzkb;
import com.google.android.gms.internal.ads.zzkn;
import com.google.android.gms.internal.ads.zzks;
import com.google.android.gms.internal.ads.zzle;
import com.google.android.gms.internal.ads.zzlj;
import com.google.android.gms.internal.ads.zznk;
import com.google.android.gms.internal.ads.zzpn;
import com.google.android.gms.internal.ads.zzpp;
import com.google.android.gms.internal.ads.zzqa;
import com.google.android.gms.internal.ads.zzqf;
import com.google.android.gms.internal.ads.zzub;
import com.google.android.gms.internal.ads.zzxn;
import java.util.HashMap;
import javax.annotation.ParametersAreNonnullByDefault;

/* JADX INFO: loaded from: classes.dex */
@ParametersAreNonnullByDefault
@zzadh
public class ClientApi extends zzle {
    @Override // com.google.android.gms.internal.ads.zzld
    public zzkn createAdLoaderBuilder(com.google.android.gms.c.a aVar, String str, zzxn zzxnVar, int i) {
        Context context = (Context) com.google.android.gms.c.d.b(aVar);
        zzbv.zzek();
        return new zzak(context, str, zzxnVar, new zzang(12451000, i, true, zzakk.zzav(context)), zzw.zzc(context));
    }

    @Override // com.google.android.gms.internal.ads.zzld
    public zzaap createAdOverlay(com.google.android.gms.c.a aVar) {
        Activity activity = (Activity) com.google.android.gms.c.d.b(aVar);
        AdOverlayInfoParcel adOverlayInfoParcelZzc = AdOverlayInfoParcel.zzc(activity.getIntent());
        if (adOverlayInfoParcelZzc == null) {
            return new zzr(activity);
        }
        switch (adOverlayInfoParcelZzc.zzbyu) {
            case 1:
                return new com.google.android.gms.ads.internal.overlay.zzq(activity);
            case 2:
                return new com.google.android.gms.ads.internal.overlay.zzx(activity);
            case 3:
                return new com.google.android.gms.ads.internal.overlay.zzy(activity);
            case 4:
                return new zzs(activity, adOverlayInfoParcelZzc);
            default:
                return new zzr(activity);
        }
    }

    @Override // com.google.android.gms.internal.ads.zzld
    public zzks createBannerAdManager(com.google.android.gms.c.a aVar, zzjn zzjnVar, String str, zzxn zzxnVar, int i) {
        Context context = (Context) com.google.android.gms.c.d.b(aVar);
        zzbv.zzek();
        return new zzy(context, zzjnVar, str, zzxnVar, new zzang(12451000, i, true, zzakk.zzav(context)), zzw.zzc(context));
    }

    @Override // com.google.android.gms.internal.ads.zzld
    public zzaaz createInAppPurchaseManager(com.google.android.gms.c.a aVar) {
        return null;
    }

    /* JADX WARN: Code duplicated, block: B:6:0x0038 A[DONT_INVERT] */
    /* JADX WARN: Code duplicated, block: B:7:0x003a  */
    /* JADX WARN: Code duplicated, block: B:9:0x004c  */
    @Override // com.google.android.gms.internal.ads.zzld
    public zzks createInterstitialAdManager(com.google.android.gms.c.a aVar, zzjn zzjnVar, String str, zzxn zzxnVar, int i) {
        boolean z;
        Context context = (Context) com.google.android.gms.c.d.b(aVar);
        zznk.initialize(context);
        zzbv.zzek();
        zzang zzangVar = new zzang(12451000, i, true, zzakk.zzav(context));
        boolean zEquals = "reward_mb".equals(zzjnVar.zzarb);
        if (zEquals) {
            if (zEquals) {
                if (((Boolean) zzkb.zzik().zzd(zznk.zzayz)).booleanValue()) {
                }
            }
        } else {
            if (!((Boolean) zzkb.zzik().zzd(zznk.zzayy)).booleanValue()) {
                if (zEquals) {
                    z = ((Boolean) zzkb.zzik().zzd(zznk.zzayz)).booleanValue();
                }
            }
        }
        return z ? new zzub(context, str, zzxnVar, zzangVar, zzw.zzc(context)) : new zzal(context, zzjnVar, str, zzxnVar, zzangVar, zzw.zzc(context));
    }

    @Override // com.google.android.gms.internal.ads.zzld
    public zzqa createNativeAdViewDelegate(com.google.android.gms.c.a aVar, com.google.android.gms.c.a aVar2) {
        return new zzpn((FrameLayout) com.google.android.gms.c.d.b(aVar), (FrameLayout) com.google.android.gms.c.d.b(aVar2));
    }

    @Override // com.google.android.gms.internal.ads.zzld
    public zzqf createNativeAdViewHolderDelegate(com.google.android.gms.c.a aVar, com.google.android.gms.c.a aVar2, com.google.android.gms.c.a aVar3) {
        return new zzpp((View) com.google.android.gms.c.d.b(aVar), (HashMap) com.google.android.gms.c.d.b(aVar2), (HashMap) com.google.android.gms.c.d.b(aVar3));
    }

    @Override // com.google.android.gms.internal.ads.zzld
    public zzagz createRewardedVideoAd(com.google.android.gms.c.a aVar, zzxn zzxnVar, int i) {
        Context context = (Context) com.google.android.gms.c.d.b(aVar);
        zzbv.zzek();
        return new zzagq(context, zzw.zzc(context), zzxnVar, new zzang(12451000, i, true, zzakk.zzav(context)));
    }

    @Override // com.google.android.gms.internal.ads.zzld
    public zzks createSearchAdManager(com.google.android.gms.c.a aVar, zzjn zzjnVar, String str, int i) {
        Context context = (Context) com.google.android.gms.c.d.b(aVar);
        zzbv.zzek();
        return new zzbp(context, zzjnVar, str, new zzang(12451000, i, true, zzakk.zzav(context)));
    }

    @Override // com.google.android.gms.internal.ads.zzld
    public zzlj getMobileAdsSettingsManager(com.google.android.gms.c.a aVar) {
        return null;
    }

    @Override // com.google.android.gms.internal.ads.zzld
    public zzlj getMobileAdsSettingsManagerWithClientJarVersion(com.google.android.gms.c.a aVar, int i) {
        Context context = (Context) com.google.android.gms.c.d.b(aVar);
        zzbv.zzek();
        return zzay.zza(context, new zzang(12451000, i, true, zzakk.zzav(context)));
    }
}
