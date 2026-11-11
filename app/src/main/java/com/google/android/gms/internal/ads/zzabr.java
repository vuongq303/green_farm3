package com.google.android.gms.internal.ads;

import android.content.Context;
import android.os.Bundle;
import android.text.TextUtils;
import com.google.ads.mediation.admob.AdMobAdapter;
import java.util.ArrayList;
import java.util.List;
import java.util.ListIterator;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.TimeUnit;
import org.json.JSONObject;

/* JADX INFO: loaded from: classes.dex */
@zzadh
public final class zzabr extends zzabh {
    private final zzaqw zzbnd;
    private zzwy zzbtj;
    private zzww zzbzq;
    protected zzxe zzbzr;
    private boolean zzbzs;
    private final zznx zzvr;
    private zzxn zzwh;

    zzabr(Context context, zzaji zzajiVar, zzxn zzxnVar, zzabm zzabmVar, zznx zznxVar, zzaqw zzaqwVar) {
        super(context, zzajiVar, zzabmVar);
        this.zzwh = zzxnVar;
        this.zzbtj = zzajiVar.zzcod;
        this.zzvr = zznxVar;
        this.zzbnd = zzaqwVar;
    }

    @Override // com.google.android.gms.internal.ads.zzabh, com.google.android.gms.internal.ads.zzajx
    public final void onStop() {
        synchronized (this.zzbzh) {
            super.onStop();
            if (this.zzbzq != null) {
                this.zzbzq.cancel();
            }
        }
    }

    @Override // com.google.android.gms.internal.ads.zzabh
    protected final zzajh zzaa(int i) {
        String strSubstring;
        int i2;
        zzaef zzaefVar = this.zzbze.zzcgs;
        zzjj zzjjVar = zzaefVar.zzccv;
        zzaqw zzaqwVar = this.zzbnd;
        List<String> list = this.zzbzf.zzbsn;
        List<String> list2 = this.zzbzf.zzbso;
        List<String> list3 = this.zzbzf.zzces;
        int i3 = this.zzbzf.orientation;
        long j = this.zzbzf.zzbsu;
        String str = zzaefVar.zzccy;
        boolean z = this.zzbzf.zzceq;
        zzwx zzwxVar = this.zzbzr != null ? this.zzbzr.zzbtw : null;
        zzxq zzxqVar = this.zzbzr != null ? this.zzbzr.zzbtx : null;
        String name = this.zzbzr != null ? this.zzbzr.zzbty : AdMobAdapter.class.getName();
        zzwy zzwyVar = this.zzbtj;
        zzxa zzxaVar = this.zzbzr != null ? this.zzbzr.zzbtz : null;
        long j2 = this.zzbzf.zzcer;
        zzjn zzjnVar = this.zzbze.zzacv;
        long j3 = this.zzbzf.zzcep;
        long j4 = this.zzbze.zzcoh;
        long j5 = this.zzbzf.zzceu;
        String str2 = this.zzbzf.zzcev;
        JSONObject jSONObject = this.zzbze.zzcob;
        zzaig zzaigVar = this.zzbzf.zzcfe;
        List<String> list4 = this.zzbzf.zzcff;
        List<String> list5 = this.zzbzf.zzcfg;
        boolean z2 = this.zzbtj != null ? this.zzbtj.zzbsz : false;
        zzael zzaelVar = this.zzbzf.zzcfi;
        if (this.zzbzq != null) {
            List<zzxe> listZzme = this.zzbzq.zzme();
            if (listZzme == null) {
                strSubstring = "".toString();
            } else {
                String string = "";
                for (zzxe zzxeVar : listZzme) {
                    if (zzxeVar != null && zzxeVar.zzbtw != null && !TextUtils.isEmpty(zzxeVar.zzbtw.zzbru)) {
                        String strValueOf = String.valueOf(string);
                        String str3 = zzxeVar.zzbtw.zzbru;
                        switch (zzxeVar.zzbtv) {
                            case -1:
                                i2 = 4;
                                break;
                            case 0:
                                i2 = 0;
                                break;
                            case 1:
                                i2 = 1;
                                break;
                            case 2:
                            default:
                                i2 = 6;
                                break;
                            case 3:
                                i2 = 2;
                                break;
                            case 4:
                                i2 = 3;
                                break;
                            case 5:
                                i2 = 5;
                                break;
                        }
                        String string2 = new StringBuilder(String.valueOf(str3).length() + 33).append(str3).append(".").append(i2).append(".").append(zzxeVar.zzbub).toString();
                        string = new StringBuilder(String.valueOf(strValueOf).length() + 1 + String.valueOf(string2).length()).append(strValueOf).append(string2).append("_").toString();
                    }
                }
                strSubstring = string.substring(0, Math.max(0, string.length() - 1));
            }
        } else {
            strSubstring = null;
        }
        return new zzajh(zzjjVar, zzaqwVar, list, i, list2, list3, i3, j, str, z, zzwxVar, zzxqVar, name, zzwyVar, zzxaVar, j2, zzjnVar, j3, j4, j5, str2, jSONObject, null, zzaigVar, list4, list5, z2, zzaelVar, strSubstring, this.zzbzf.zzbsr, this.zzbzf.zzcfl, this.zzbze.zzcoq, this.zzbzf.zzzl, this.zzbze.zzcor, this.zzbzf.zzcfp, this.zzbzf.zzbsp, this.zzbzf.zzzm, this.zzbzf.zzcfq);
    }

    @Override // com.google.android.gms.internal.ads.zzabh
    protected final void zze(long j) throws zzabk {
        Bundle bundle;
        synchronized (this.zzbzh) {
            this.zzbzq = this.zzbtj.zzbsx != -1 ? new zzxh(this.mContext, this.zzbze.zzcgs, this.zzwh, this.zzbtj, this.zzbzf.zzare, this.zzbzf.zzarg, this.zzbzf.zzcfj, j, ((Long) zzkb.zzik().zzd(zznk.zzbao)).longValue(), 2, this.zzbze.zzcor) : new zzxk(this.mContext, this.zzbze.zzcgs, this.zzwh, this.zzbtj, this.zzbzf.zzare, this.zzbzf.zzarg, this.zzbzf.zzcfj, j, ((Long) zzkb.zzik().zzd(zznk.zzbao)).longValue(), this.zzvr, this.zzbze.zzcor);
        }
        ArrayList arrayList = new ArrayList(this.zzbtj.zzbsm);
        boolean z = false;
        Bundle bundle2 = this.zzbze.zzcgs.zzccv.zzaqg;
        if (bundle2 != null && (bundle = bundle2.getBundle("com.google.ads.mediation.admob.AdMobAdapter")) != null) {
            z = bundle.getBoolean("_skipMediation");
        }
        if (z) {
            ListIterator listIterator = arrayList.listIterator();
            while (listIterator.hasNext()) {
                if (!((zzwx) listIterator.next()).zzbrt.contains("com.google.ads.mediation.admob.AdMobAdapter")) {
                    listIterator.remove();
                }
            }
        }
        this.zzbzr = this.zzbzq.zzh(arrayList);
        switch (this.zzbzr.zzbtv) {
            case 0:
                if (this.zzbzr.zzbtw == null || this.zzbzr.zzbtw.zzbsf == null) {
                    return;
                }
                CountDownLatch countDownLatch = new CountDownLatch(1);
                zzakk.zzcrm.post(new zzabs(this, countDownLatch));
                try {
                    countDownLatch.await(10L, TimeUnit.SECONDS);
                    synchronized (this.zzbzh) {
                        if (!this.zzbzs) {
                            throw new zzabk("View could not be prepared", 0);
                        }
                        if (this.zzbnd.isDestroyed()) {
                            throw new zzabk("Assets not loaded, web view is destroyed", 0);
                        }
                    }
                    return;
                } catch (InterruptedException e) {
                    String strValueOf = String.valueOf(e);
                    throw new zzabk(new StringBuilder(String.valueOf(strValueOf).length() + 38).append("Interrupted while waiting for latch : ").append(strValueOf).toString(), 0);
                }
            case 1:
                throw new zzabk("No fill from any mediation ad networks.", 3);
            default:
                throw new zzabk(new StringBuilder(40).append("Unexpected mediation result: ").append(this.zzbzr.zzbtv).toString(), 0);
        }
    }
}
