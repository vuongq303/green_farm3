package com.google.android.gms.internal.ads;

import android.app.Activity;
import android.os.Bundle;
import android.os.RemoteException;
import com.google.ads.mediation.i;
import com.google.ads.mediation.j;
import com.google.ads.mediation.l;
import com.google.ads.mediation.n;
import com.google.ads.mediation.q;
import com.google.android.gms.c.a;
import com.google.android.gms.c.d;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import org.json.JSONObject;

/* JADX INFO: loaded from: classes.dex */
@zzadh
public final class zzyp<NETWORK_EXTRAS extends q, SERVER_PARAMETERS extends n> extends zzxr {
    private final i<NETWORK_EXTRAS, SERVER_PARAMETERS> zzbvb;
    private final NETWORK_EXTRAS zzbvc;

    public zzyp(i<NETWORK_EXTRAS, SERVER_PARAMETERS> iVar, NETWORK_EXTRAS network_extras) {
        this.zzbvb = iVar;
        this.zzbvc = network_extras;
    }

    private final SERVER_PARAMETERS zza(String str, int i, String str2) throws RemoteException {
        HashMap map;
        try {
            if (str != null) {
                JSONObject jSONObject = new JSONObject(str);
                map = new HashMap(jSONObject.length());
                Iterator<String> itKeys = jSONObject.keys();
                while (itKeys.hasNext()) {
                    String next = itKeys.next();
                    map.put(next, jSONObject.getString(next));
                }
            } else {
                map = new HashMap(0);
            }
            Class<SERVER_PARAMETERS> clsWH = this.zzbvb.WH();
            if (clsWH == null) {
                return null;
            }
            SERVER_PARAMETERS server_parametersNewInstance = clsWH.newInstance();
            server_parametersNewInstance.b(map);
            return server_parametersNewInstance;
        } catch (Throwable th) {
            zzane.zzb("", th);
            throw new RemoteException();
        }
    }

    private static boolean zzm(zzjj zzjjVar) {
        if (!zzjjVar.zzapz) {
            zzkb.zzif();
            if (!zzamu.zzsg()) {
                return false;
            }
        }
        return true;
    }

    @Override // com.google.android.gms.internal.ads.zzxq
    public final void destroy() throws RemoteException {
        try {
            this.zzbvb.destroy();
        } catch (Throwable th) {
            zzane.zzb("", th);
            throw new RemoteException();
        }
    }

    @Override // com.google.android.gms.internal.ads.zzxq
    public final Bundle getInterstitialAdapterInfo() {
        return new Bundle();
    }

    @Override // com.google.android.gms.internal.ads.zzxq
    public final zzlo getVideoController() {
        return null;
    }

    @Override // com.google.android.gms.internal.ads.zzxq
    public final a getView() throws RemoteException {
        if (!(this.zzbvb instanceof j)) {
            String strValueOf = String.valueOf(this.zzbvb.getClass().getCanonicalName());
            zzane.zzdk(strValueOf.length() != 0 ? "Not a MediationBannerAdapter: ".concat(strValueOf) : new String("Not a MediationBannerAdapter: "));
            throw new RemoteException();
        }
        try {
            return d.F(((j) this.zzbvb).getBannerView());
        } catch (Throwable th) {
            zzane.zzb("", th);
            throw new RemoteException();
        }
    }

    @Override // com.google.android.gms.internal.ads.zzxq
    public final boolean isInitialized() {
        return true;
    }

    @Override // com.google.android.gms.internal.ads.zzxq
    public final void pause() throws RemoteException {
        throw new RemoteException();
    }

    @Override // com.google.android.gms.internal.ads.zzxq
    public final void resume() throws RemoteException {
        throw new RemoteException();
    }

    @Override // com.google.android.gms.internal.ads.zzxq
    public final void setImmersiveMode(boolean z) {
    }

    @Override // com.google.android.gms.internal.ads.zzxq
    public final void showInterstitial() throws RemoteException {
        if (!(this.zzbvb instanceof l)) {
            String strValueOf = String.valueOf(this.zzbvb.getClass().getCanonicalName());
            zzane.zzdk(strValueOf.length() != 0 ? "Not a MediationInterstitialAdapter: ".concat(strValueOf) : new String("Not a MediationInterstitialAdapter: "));
            throw new RemoteException();
        }
        zzane.zzck("Showing interstitial from adapter.");
        try {
            ((l) this.zzbvb).showInterstitial();
        } catch (Throwable th) {
            zzane.zzb("", th);
            throw new RemoteException();
        }
    }

    @Override // com.google.android.gms.internal.ads.zzxq
    public final void showVideo() {
    }

    @Override // com.google.android.gms.internal.ads.zzxq
    public final void zza(a aVar, zzaic zzaicVar, List<String> list) {
    }

    @Override // com.google.android.gms.internal.ads.zzxq
    public final void zza(a aVar, zzjj zzjjVar, String str, zzaic zzaicVar, String str2) {
    }

    @Override // com.google.android.gms.internal.ads.zzxq
    public final void zza(a aVar, zzjj zzjjVar, String str, zzxt zzxtVar) throws RemoteException {
        zza(aVar, zzjjVar, str, (String) null, zzxtVar);
    }

    /* JADX WARN: Multi-variable type inference failed */
    @Override // com.google.android.gms.internal.ads.zzxq
    public final void zza(a aVar, zzjj zzjjVar, String str, String str2, zzxt zzxtVar) throws RemoteException {
        if (!(this.zzbvb instanceof l)) {
            String strValueOf = String.valueOf(this.zzbvb.getClass().getCanonicalName());
            zzane.zzdk(strValueOf.length() != 0 ? "Not a MediationInterstitialAdapter: ".concat(strValueOf) : new String("Not a MediationInterstitialAdapter: "));
            throw new RemoteException();
        }
        zzane.zzck("Requesting interstitial ad from adapter.");
        try {
            ((l) this.zzbvb).a(new zzyq(zzxtVar), (Activity) d.b(aVar), zza(str, zzjjVar.zzaqa, str2), zzzc.zza(zzjjVar, zzm(zzjjVar)), this.zzbvc);
        } catch (Throwable th) {
            zzane.zzb("", th);
            throw new RemoteException();
        }
    }

    @Override // com.google.android.gms.internal.ads.zzxq
    public final void zza(a aVar, zzjj zzjjVar, String str, String str2, zzxt zzxtVar, zzpl zzplVar, List<String> list) {
    }

    @Override // com.google.android.gms.internal.ads.zzxq
    public final void zza(a aVar, zzjn zzjnVar, zzjj zzjjVar, String str, zzxt zzxtVar) throws RemoteException {
        zza(aVar, zzjnVar, zzjjVar, str, null, zzxtVar);
    }

    /* JADX WARN: Multi-variable type inference failed */
    @Override // com.google.android.gms.internal.ads.zzxq
    public final void zza(a aVar, zzjn zzjnVar, zzjj zzjjVar, String str, String str2, zzxt zzxtVar) throws RemoteException {
        com.google.ads.d dVar;
        if (!(this.zzbvb instanceof j)) {
            String strValueOf = String.valueOf(this.zzbvb.getClass().getCanonicalName());
            zzane.zzdk(strValueOf.length() != 0 ? "Not a MediationBannerAdapter: ".concat(strValueOf) : new String("Not a MediationBannerAdapter: "));
            throw new RemoteException();
        }
        zzane.zzck("Requesting banner ad from adapter.");
        try {
            j jVar = (j) this.zzbvb;
            zzyq zzyqVar = new zzyq(zzxtVar);
            Activity activity = (Activity) d.b(aVar);
            n nVarZza = zza(str, zzjjVar.zzaqa, str2);
            com.google.ads.d[] dVarArr = {com.google.ads.d.byP, com.google.ads.d.byQ, com.google.ads.d.byR, com.google.ads.d.byS, com.google.ads.d.byT, com.google.ads.d.byU};
            for (int i = 0; i < 6; i++) {
                if (dVarArr[i].getWidth() == zzjnVar.width && dVarArr[i].getHeight() == zzjnVar.height) {
                    dVar = dVarArr[i];
                    jVar.a(zzyqVar, activity, nVarZza, dVar, zzzc.zza(zzjjVar, zzm(zzjjVar)), this.zzbvc);
                }
            }
            dVar = new com.google.ads.d(com.google.android.gms.ads.zzb.zza(zzjnVar.width, zzjnVar.height, zzjnVar.zzarb));
            jVar.a(zzyqVar, activity, nVarZza, dVar, zzzc.zza(zzjjVar, zzm(zzjjVar)), this.zzbvc);
        } catch (Throwable th) {
            zzane.zzb("", th);
            throw new RemoteException();
        }
    }

    @Override // com.google.android.gms.internal.ads.zzxq
    public final void zza(zzjj zzjjVar, String str, String str2) {
    }

    @Override // com.google.android.gms.internal.ads.zzxq
    public final void zzc(zzjj zzjjVar, String str) {
    }

    @Override // com.google.android.gms.internal.ads.zzxq
    public final void zzi(a aVar) {
    }

    @Override // com.google.android.gms.internal.ads.zzxq
    public final zzxz zzmo() {
        return null;
    }

    @Override // com.google.android.gms.internal.ads.zzxq
    public final zzyc zzmp() {
        return null;
    }

    @Override // com.google.android.gms.internal.ads.zzxq
    public final Bundle zzmq() {
        return new Bundle();
    }

    @Override // com.google.android.gms.internal.ads.zzxq
    public final Bundle zzmr() {
        return new Bundle();
    }

    @Override // com.google.android.gms.internal.ads.zzxq
    public final boolean zzms() {
        return false;
    }

    @Override // com.google.android.gms.internal.ads.zzxq
    public final zzqs zzmt() {
        return null;
    }

    @Override // com.google.android.gms.internal.ads.zzxq
    public final zzyf zzmu() {
        return null;
    }
}
