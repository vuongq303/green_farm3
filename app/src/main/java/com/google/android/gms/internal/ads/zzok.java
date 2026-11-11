package com.google.android.gms.internal.ads;

import android.os.RemoteException;
import android.view.View;
import com.facebook.share.internal.ShareConstants;
import java.lang.ref.WeakReference;
import javax.annotation.ParametersAreNonnullByDefault;
import org.json.JSONException;
import org.json.JSONObject;

/* JADX INFO: loaded from: classes.dex */
@ParametersAreNonnullByDefault
@zzadh
public final class zzok implements View.OnClickListener {
    private final zzacm zzaad;
    private zzro zzbhm;
    private com.google.android.gms.ads.internal.gmsg.zzv zzbhn;
    String zzbho;
    Long zzbhp;
    WeakReference<View> zzbhq;

    public zzok(zzacm zzacmVar) {
        this.zzaad = zzacmVar;
    }

    private final void zzjx() {
        this.zzbho = null;
        this.zzbhp = null;
        if (this.zzbhq == null) {
            return;
        }
        View view = this.zzbhq.get();
        this.zzbhq = null;
        if (view != null) {
            view.setClickable(false);
            view.setOnClickListener(null);
        }
    }

    public final void cancelUnconfirmedClick() {
        if (this.zzbhm == null || this.zzbhp == null) {
            return;
        }
        zzjx();
        try {
            this.zzbhm.onUnconfirmedClickCancelled();
        } catch (RemoteException e) {
            zzane.zzd("#007 Could not call remote method.", e);
        }
    }

    @Override // android.view.View.OnClickListener
    public final void onClick(View view) {
        if (this.zzbhq == null || this.zzbhq.get() != view) {
            return;
        }
        if (this.zzbho != null && this.zzbhp != null) {
            JSONObject jSONObject = new JSONObject();
            try {
                jSONObject.put(ShareConstants.WEB_DIALOG_PARAM_ID, this.zzbho);
                jSONObject.put("time_interval", com.google.android.gms.ads.internal.zzbv.zzer().currentTimeMillis() - this.zzbhp.longValue());
                jSONObject.put("messageType", "onePointFiveClick");
                this.zzaad.zza("sendMessageToNativeJs", jSONObject);
            } catch (JSONException e) {
                zzakb.zzb("Unable to dispatch sendMessageToNativeJs event", e);
            }
        }
        zzjx();
    }

    public final void zza(zzro zzroVar) {
        this.zzbhm = zzroVar;
        if (this.zzbhn != null) {
            this.zzaad.zzb("/unconfirmedClick", this.zzbhn);
        }
        this.zzbhn = new zzol(this);
        this.zzaad.zza("/unconfirmedClick", this.zzbhn);
    }

    public final zzro zzjw() {
        return this.zzbhm;
    }
}
