package com.google.android.gms.internal.ads;

import android.os.RemoteException;
import com.facebook.share.internal.ShareConstants;
import java.util.Map;

/* JADX INFO: loaded from: classes.dex */
final class zzol implements com.google.android.gms.ads.internal.gmsg.zzv<Object> {
    private final /* synthetic */ zzok zzbhr;

    zzol(zzok zzokVar) {
        this.zzbhr = zzokVar;
    }

    @Override // com.google.android.gms.ads.internal.gmsg.zzv
    public final void zza(Object obj, Map<String, String> map) {
        try {
            this.zzbhr.zzbhp = Long.valueOf(Long.parseLong(map.get("timestamp")));
        } catch (NumberFormatException e) {
            zzakb.e("Failed to call parse unconfirmedClickTimestamp.");
        }
        this.zzbhr.zzbho = map.get(ShareConstants.WEB_DIALOG_PARAM_ID);
        String str = map.get("asset_id");
        if (this.zzbhr.zzbhm == null) {
            zzakb.zzck("Received unconfirmed click but UnconfirmedClickListener is null.");
            return;
        }
        try {
            this.zzbhr.zzbhm.onUnconfirmedClickReceived(str);
        } catch (RemoteException e2) {
            zzane.zzd("#007 Could not call remote method.", e2);
        }
    }
}
