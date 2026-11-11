package com.google.android.gms.internal.ads;

import android.content.Context;
import android.text.TextUtils;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import org.json.JSONException;
import org.json.JSONObject;

/* JADX INFO: loaded from: classes.dex */
@zzadh
public final class zzahx extends zzajx implements zzahw {
    private final Context mContext;
    private final Object mLock;
    private final zzaji zzbze;
    private final long zzclp;
    private final ArrayList<zzahn> zzcmd;
    private final List<zzahq> zzcme;
    private final HashSet<String> zzcmf;
    private final zzago zzcmg;

    public zzahx(Context context, zzaji zzajiVar, zzago zzagoVar) {
        this(context, zzajiVar, zzagoVar, ((Long) zzkb.zzik().zzd(zznk.zzaye)).longValue());
    }

    private zzahx(Context context, zzaji zzajiVar, zzago zzagoVar, long j) {
        this.zzcmd = new ArrayList<>();
        this.zzcme = new ArrayList();
        this.zzcmf = new HashSet<>();
        this.mLock = new Object();
        this.mContext = context;
        this.zzbze = zzajiVar;
        this.zzcmg = zzagoVar;
        this.zzclp = j;
    }

    private final zzajh zza(int i, String str, zzwx zzwxVar) {
        String strSubstring;
        int i2;
        zzjj zzjjVar = this.zzbze.zzcgs.zzccv;
        List<String> list = this.zzbze.zzcos.zzbsn;
        List<String> list2 = this.zzbze.zzcos.zzbso;
        List<String> list3 = this.zzbze.zzcos.zzces;
        int i3 = this.zzbze.zzcos.orientation;
        long j = this.zzbze.zzcos.zzbsu;
        String str2 = this.zzbze.zzcgs.zzccy;
        boolean z = this.zzbze.zzcos.zzceq;
        zzwy zzwyVar = this.zzbze.zzcod;
        long j2 = this.zzbze.zzcos.zzcer;
        zzjn zzjnVar = this.zzbze.zzacv;
        long j3 = this.zzbze.zzcos.zzcep;
        long j4 = this.zzbze.zzcoh;
        long j5 = this.zzbze.zzcos.zzceu;
        String str3 = this.zzbze.zzcos.zzcev;
        JSONObject jSONObject = this.zzbze.zzcob;
        zzaig zzaigVar = this.zzbze.zzcos.zzcfe;
        List<String> list4 = this.zzbze.zzcos.zzcff;
        List<String> list5 = this.zzbze.zzcos.zzcfg;
        boolean z2 = this.zzbze.zzcos.zzcfh;
        zzael zzaelVar = this.zzbze.zzcos.zzcfi;
        StringBuilder sb = new StringBuilder("");
        if (this.zzcme == null) {
            strSubstring = sb.toString();
        } else {
            for (zzahq zzahqVar : this.zzcme) {
                if (zzahqVar != null && !TextUtils.isEmpty(zzahqVar.zzbru)) {
                    String str4 = zzahqVar.zzbru;
                    switch (zzahqVar.errorCode) {
                        case 3:
                            i2 = 1;
                            break;
                        case 4:
                            i2 = 2;
                            break;
                        case 5:
                            i2 = 4;
                            break;
                        case 6:
                            i2 = 0;
                            break;
                        case 7:
                            i2 = 3;
                            break;
                        default:
                            i2 = 6;
                            break;
                    }
                    sb.append(String.valueOf(new StringBuilder(String.valueOf(str4).length() + 33).append(str4).append(".").append(i2).append(".").append(zzahqVar.zzbub).toString()).concat("_"));
                }
            }
            strSubstring = sb.substring(0, Math.max(0, sb.length() - 1));
        }
        return new zzajh(zzjjVar, null, list, i, list2, list3, i3, j, str2, z, zzwxVar, null, str, zzwyVar, null, j2, zzjnVar, j3, j4, j5, str3, jSONObject, null, zzaigVar, list4, list5, z2, zzaelVar, strSubstring, this.zzbze.zzcos.zzbsr, this.zzbze.zzcos.zzcfl, this.zzbze.zzcoq, this.zzbze.zzcos.zzzl, this.zzbze.zzcor, this.zzbze.zzcos.zzcfp, this.zzbze.zzcos.zzbsp, this.zzbze.zzcos.zzzm, this.zzbze.zzcos.zzcfq);
    }

    @Override // com.google.android.gms.internal.ads.zzajx
    public final void onStop() {
    }

    @Override // com.google.android.gms.internal.ads.zzahw
    public final void zza(String str, int i) {
    }

    @Override // com.google.android.gms.internal.ads.zzahw
    public final void zzcb(String str) {
        synchronized (this.mLock) {
            this.zzcmf.add(str);
        }
    }

    @Override // com.google.android.gms.internal.ads.zzajx
    public final void zzdn() {
        String string;
        for (zzwx zzwxVar : this.zzbze.zzcod.zzbsm) {
            String str = zzwxVar.zzbsb;
            for (String str2 : zzwxVar.zzbrt) {
                if ("com.google.android.gms.ads.mediation.customevent.CustomEventAdapter".equals(str2) || "com.google.ads.mediation.customevent.CustomEventAdapter".equals(str2)) {
                    try {
                        string = new JSONObject(str).getString("class_name");
                    } catch (JSONException e) {
                        zzakb.zzb("Unable to determine custom event class name, skipping...", e);
                    }
                } else {
                    string = str2;
                }
                synchronized (this.mLock) {
                    zzaib zzaibVarZzca = this.zzcmg.zzca(string);
                    if (zzaibVarZzca == null || zzaibVarZzca.zzpf() == null || zzaibVarZzca.zzpe() == null) {
                        this.zzcme.add(new zzahs().zzcd(zzwxVar.zzbru).zzcc(string).zzg(0L).zzad(7).zzpd());
                    } else {
                        zzahn zzahnVar = new zzahn(this.mContext, string, str, zzwxVar, this.zzbze, zzaibVarZzca, this, this.zzclp);
                        zzahnVar.zza(this.zzcmg.zzos());
                        this.zzcmd.add(zzahnVar);
                    }
                }
            }
        }
        HashSet hashSet = new HashSet();
        ArrayList<zzahn> arrayList = this.zzcmd;
        int size = arrayList.size();
        int i = 0;
        while (i < size) {
            zzahn zzahnVar2 = arrayList.get(i);
            i++;
            zzahn zzahnVar3 = zzahnVar2;
            if (hashSet.add(zzahnVar3.zzbth)) {
                zzahnVar3.zzoz();
            }
        }
        ArrayList<zzahn> arrayList2 = this.zzcmd;
        int size2 = arrayList2.size();
        int i2 = 0;
        while (i2 < size2) {
            i2++;
            zzahn zzahnVar4 = arrayList2.get(i2);
            try {
                try {
                    zzahnVar4.zzoz().get();
                    synchronized (this.mLock) {
                        if (!TextUtils.isEmpty(zzahnVar4.zzbth)) {
                            this.zzcme.add(zzahnVar4.zzpa());
                        }
                    }
                    synchronized (this.mLock) {
                        if (this.zzcmf.contains(zzahnVar4.zzbth)) {
                            final zzajh zzajhVarZza = zza(-2, zzahnVar4.zzbth, zzahnVar4.zzpb());
                            zzamu.zzsy.post(new Runnable(this, zzajhVarZza) { // from class: com.google.android.gms.internal.ads.zzahy
                                private final zzahx zzcmh;
                                private final zzajh zzxk;

                                {
                                    this.zzcmh = this;
                                    this.zzxk = zzajhVarZza;
                                }

                                @Override // java.lang.Runnable
                                public final void run() {
                                    this.zzcmh.zzm(this.zzxk);
                                }
                            });
                            return;
                        }
                    }
                } catch (InterruptedException e2) {
                    Thread.currentThread().interrupt();
                    synchronized (this.mLock) {
                        if (!TextUtils.isEmpty(zzahnVar4.zzbth)) {
                            this.zzcme.add(zzahnVar4.zzpa());
                        }
                        final zzajh zzajhVarZza2 = zza(3, null, null);
                        zzamu.zzsy.post(new Runnable(this, zzajhVarZza2) { // from class: com.google.android.gms.internal.ads.zzahz
                            private final zzahx zzcmh;
                            private final zzajh zzxk;

                            {
                                this.zzcmh = this;
                                this.zzxk = zzajhVarZza2;
                            }

                            @Override // java.lang.Runnable
                            public final void run() {
                                this.zzcmh.zzl(this.zzxk);
                            }
                        });
                    }
                } catch (Exception e3) {
                    zzakb.zzc("Unable to resolve rewarded adapter.", e3);
                    synchronized (this.mLock) {
                        if (!TextUtils.isEmpty(zzahnVar4.zzbth)) {
                            this.zzcme.add(zzahnVar4.zzpa());
                        }
                    }
                }
            } catch (Throwable th) {
                synchronized (this.mLock) {
                    if (!TextUtils.isEmpty(zzahnVar4.zzbth)) {
                        this.zzcme.add(zzahnVar4.zzpa());
                    }
                    throw th;
                }
            }
        }
        final zzajh zzajhVarZza3 = zza(3, null, null);
        zzamu.zzsy.post(new Runnable(this, zzajhVarZza3) { // from class: com.google.android.gms.internal.ads.zzahz
            private final zzahx zzcmh;
            private final zzajh zzxk;

            {
                this.zzcmh = this;
                this.zzxk = zzajhVarZza3;
            }

            @Override // java.lang.Runnable
            public final void run() {
                this.zzcmh.zzl(this.zzxk);
            }
        });
    }

    final /* synthetic */ void zzl(zzajh zzajhVar) {
        this.zzcmg.zzot().zzb(zzajhVar);
    }

    final /* synthetic */ void zzm(zzajh zzajhVar) {
        this.zzcmg.zzot().zzb(zzajhVar);
    }
}
