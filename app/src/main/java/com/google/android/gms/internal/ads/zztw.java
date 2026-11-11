package com.google.android.gms.internal.ads;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Parcel;
import android.util.Base64;
import com.gameloft.glads.AndroidWebView;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.Map;
import java.util.Set;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import javax.annotation.ParametersAreNonnullByDefault;

/* JADX INFO: loaded from: classes.dex */
@ParametersAreNonnullByDefault
@zzadh
public final class zztw {
    private final Map<zztx, zzty> zzbok = new HashMap();
    private final LinkedList<zztx> zzbol = new LinkedList<>();
    private zzss zzbom;

    private static void zza(String str, zztx zztxVar) {
        if (zzakb.isLoggable(2)) {
            zzakb.v(String.format(str, zztxVar));
        }
    }

    private static String[] zzax(String str) {
        try {
            String[] strArrSplit = str.split("\u0000");
            for (int i = 0; i < strArrSplit.length; i++) {
                strArrSplit[i] = new String(Base64.decode(strArrSplit[i], 0), AndroidWebView.UTF_8);
            }
            return strArrSplit;
        } catch (UnsupportedEncodingException e) {
            return new String[0];
        }
    }

    private static boolean zzay(String str) {
        try {
            return Pattern.matches((String) zzkb.zzik().zzd(zznk.zzazf), str);
        } catch (RuntimeException e) {
            com.google.android.gms.ads.internal.zzbv.zzeo().zza(e, "InterstitialAdPool.isExcludedAdUnit");
            return false;
        }
    }

    private static String zzaz(String str) {
        try {
            Matcher matcher = Pattern.compile("([^/]+/[0-9]+).*").matcher(str);
            return matcher.matches() ? matcher.group(1) : str;
        } catch (RuntimeException e) {
            return str;
        }
    }

    private static void zzb(Bundle bundle, String str) {
        while (true) {
            String[] strArrSplit = str.split("/", 2);
            if (strArrSplit.length == 0) {
                return;
            }
            String str2 = strArrSplit[0];
            if (strArrSplit.length == 1) {
                bundle.remove(str2);
                return;
            }
            bundle = bundle.getBundle(str2);
            if (bundle == null) {
                return;
            } else {
                str = strArrSplit[1];
            }
        }
    }

    static Set<String> zzh(zzjj zzjjVar) {
        HashSet hashSet = new HashSet();
        hashSet.addAll(zzjjVar.extras.keySet());
        Bundle bundle = zzjjVar.zzaqg.getBundle("com.google.ads.mediation.admob.AdMobAdapter");
        if (bundle != null) {
            hashSet.addAll(bundle.keySet());
        }
        return hashSet;
    }

    static zzjj zzi(zzjj zzjjVar) {
        zzjj zzjjVarZzk = zzk(zzjjVar);
        Bundle bundle = zzjjVarZzk.zzaqg.getBundle("com.google.ads.mediation.admob.AdMobAdapter");
        if (bundle != null) {
            bundle.putBoolean("_skipMediation", true);
        }
        zzjjVarZzk.extras.putBoolean("_skipMediation", true);
        return zzjjVarZzk;
    }

    private static zzjj zzj(zzjj zzjjVar) {
        zzjj zzjjVarZzk = zzk(zzjjVar);
        for (String str : ((String) zzkb.zzik().zzd(zznk.zzazb)).split(",")) {
            zzb(zzjjVarZzk.zzaqg, str);
            if (str.startsWith("com.google.ads.mediation.admob.AdMobAdapter/")) {
                zzb(zzjjVarZzk.extras, str.replaceFirst("com.google.ads.mediation.admob.AdMobAdapter/", ""));
            }
        }
        return zzjjVarZzk;
    }

    private static zzjj zzk(zzjj zzjjVar) {
        Parcel parcelObtain = Parcel.obtain();
        zzjjVar.writeToParcel(parcelObtain, 0);
        parcelObtain.setDataPosition(0);
        zzjj zzjjVarCreateFromParcel = zzjj.CREATOR.createFromParcel(parcelObtain);
        parcelObtain.recycle();
        return ((Boolean) zzkb.zzik().zzd(zznk.zzayo)).booleanValue() ? zzjjVarCreateFromParcel.zzhv() : zzjjVarCreateFromParcel;
    }

    private final String zzle() {
        try {
            StringBuilder sb = new StringBuilder();
            Iterator<zztx> it = this.zzbol.iterator();
            while (it.hasNext()) {
                sb.append(Base64.encodeToString(it.next().toString().getBytes(AndroidWebView.UTF_8), 0));
                if (it.hasNext()) {
                    sb.append("\u0000");
                }
            }
            return sb.toString();
        } catch (UnsupportedEncodingException e) {
            return "";
        }
    }

    final zztz zza(zzjj zzjjVar, String str) {
        zzty zztyVar;
        if (zzay(str)) {
            return null;
        }
        int i = new zzagb(this.zzbom.getApplicationContext()).zzoo().zzcjx;
        zzjj zzjjVarZzj = zzj(zzjjVar);
        String strZzaz = zzaz(str);
        zztx zztxVar = new zztx(zzjjVarZzj, strZzaz, i);
        zzty zztyVar2 = this.zzbok.get(zztxVar);
        if (zztyVar2 == null) {
            zza("Interstitial pool created at %s.", zztxVar);
            zzty zztyVar3 = new zzty(zzjjVarZzj, strZzaz, i);
            this.zzbok.put(zztxVar, zztyVar3);
            zztyVar = zztyVar3;
        } else {
            zztyVar = zztyVar2;
        }
        this.zzbol.remove(zztxVar);
        this.zzbol.add(zztxVar);
        zztyVar.zzli();
        while (this.zzbol.size() > ((Integer) zzkb.zzik().zzd(zznk.zzazc)).intValue()) {
            zztx zztxVarRemove = this.zzbol.remove();
            zzty zztyVar4 = this.zzbok.get(zztxVarRemove);
            zza("Evicting interstitial queue for %s.", zztxVarRemove);
            while (zztyVar4.size() > 0) {
                zztz zztzVarZzl = zztyVar4.zzl(null);
                if (zztzVarZzl.zzwa) {
                    zzua.zzlk().zzlm();
                }
                zztzVarZzl.zzbor.zzdj();
            }
            this.zzbok.remove(zztxVarRemove);
        }
        while (zztyVar.size() > 0) {
            zztz zztzVarZzl2 = zztyVar.zzl(zzjjVarZzj);
            if (zztzVarZzl2.zzwa) {
                if (com.google.android.gms.ads.internal.zzbv.zzer().currentTimeMillis() - zztzVarZzl2.zzbou > 1000 * ((long) ((Integer) zzkb.zzik().zzd(zznk.zzaze)).intValue())) {
                    zza("Expired interstitial at %s.", zztxVar);
                    zzua.zzlk().zzll();
                }
            }
            String str2 = zztzVarZzl2.zzbos != null ? " (inline) " : " ";
            zza(new StringBuilder(String.valueOf(str2).length() + 34).append("Pooled interstitial").append(str2).append("returned at %s.").toString(), zztxVar);
            return zztzVarZzl2;
        }
        return null;
    }

    final void zza(zzss zzssVar) {
        if (this.zzbom == null) {
            this.zzbom = zzssVar.zzlc();
            if (this.zzbom != null) {
                SharedPreferences sharedPreferences = this.zzbom.getApplicationContext().getSharedPreferences("com.google.android.gms.ads.internal.interstitial.InterstitialAdPool", 0);
                while (this.zzbol.size() > 0) {
                    zztx zztxVarRemove = this.zzbol.remove();
                    zzty zztyVar = this.zzbok.get(zztxVarRemove);
                    zza("Flushing interstitial queue for %s.", zztxVarRemove);
                    while (zztyVar.size() > 0) {
                        zztyVar.zzl(null).zzbor.zzdj();
                    }
                    this.zzbok.remove(zztxVarRemove);
                }
                try {
                    HashMap map = new HashMap();
                    for (Map.Entry<String, ?> entry : sharedPreferences.getAll().entrySet()) {
                        if (!entry.getKey().equals("PoolKeys")) {
                            zzuc zzucVarZzba = zzuc.zzba((String) entry.getValue());
                            zztx zztxVar = new zztx(zzucVarZzba.zzaao, zzucVarZzba.zzye, zzucVarZzba.zzbop);
                            if (!this.zzbok.containsKey(zztxVar)) {
                                this.zzbok.put(zztxVar, new zzty(zzucVarZzba.zzaao, zzucVarZzba.zzye, zzucVarZzba.zzbop));
                                map.put(zztxVar.toString(), zztxVar);
                                zza("Restored interstitial queue for %s.", zztxVar);
                            }
                        }
                    }
                    for (String str : zzax(sharedPreferences.getString("PoolKeys", ""))) {
                        zztx zztxVar2 = (zztx) map.get(str);
                        if (this.zzbok.containsKey(zztxVar2)) {
                            this.zzbol.add(zztxVar2);
                        }
                    }
                } catch (IOException | RuntimeException e) {
                    com.google.android.gms.ads.internal.zzbv.zzeo().zza(e, "InterstitialAdPool.restore");
                    zzakb.zzc("Malformed preferences value for InterstitialAdPool.", e);
                    this.zzbok.clear();
                    this.zzbol.clear();
                }
            }
        }
    }

    final void zzb(zzjj zzjjVar, String str) {
        if (this.zzbom == null) {
            return;
        }
        int i = new zzagb(this.zzbom.getApplicationContext()).zzoo().zzcjx;
        zzjj zzjjVarZzj = zzj(zzjjVar);
        String strZzaz = zzaz(str);
        zztx zztxVar = new zztx(zzjjVarZzj, strZzaz, i);
        zzty zztyVar = this.zzbok.get(zztxVar);
        if (zztyVar == null) {
            zza("Interstitial pool created at %s.", zztxVar);
            zztyVar = new zzty(zzjjVarZzj, strZzaz, i);
            this.zzbok.put(zztxVar, zztyVar);
        }
        zztyVar.zza(this.zzbom, zzjjVar);
        zztyVar.zzli();
        zza("Inline entry added to the queue at %s.", zztxVar);
    }

    final void zzld() {
        int size;
        int iZzlg;
        if (this.zzbom == null) {
            return;
        }
        for (Map.Entry<zztx, zzty> entry : this.zzbok.entrySet()) {
            zztx key = entry.getKey();
            zzty value = entry.getValue();
            if (zzakb.isLoggable(2) && (iZzlg = value.zzlg()) < (size = value.size())) {
                zzakb.v(String.format("Loading %s/%s pooled interstitials for %s.", Integer.valueOf(size - iZzlg), Integer.valueOf(size), key));
            }
            int iZzlh = value.zzlh() + 0;
            while (value.size() < ((Integer) zzkb.zzik().zzd(zznk.zzazd)).intValue()) {
                zza("Pooling and loading one new interstitial for %s.", key);
                if (value.zzb(this.zzbom)) {
                    iZzlh++;
                }
            }
            zzua.zzlk().zzw(iZzlh);
        }
        if (this.zzbom != null) {
            SharedPreferences.Editor editorEdit = this.zzbom.getApplicationContext().getSharedPreferences("com.google.android.gms.ads.internal.interstitial.InterstitialAdPool", 0).edit();
            editorEdit.clear();
            for (Map.Entry<zztx, zzty> entry2 : this.zzbok.entrySet()) {
                zztx key2 = entry2.getKey();
                zzty value2 = entry2.getValue();
                if (value2.zzlj()) {
                    editorEdit.putString(key2.toString(), new zzuc(value2).zzlu());
                    zza("Saved interstitial queue for %s.", key2);
                }
            }
            editorEdit.putString("PoolKeys", zzle());
            editorEdit.apply();
        }
    }
}
