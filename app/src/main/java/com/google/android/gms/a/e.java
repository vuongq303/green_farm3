package com.google.android.gms.a;

import android.net.Uri;
import android.text.TextUtils;
import com.facebook.appevents.AppEventsConstants;
import com.facebook.appevents.UserDataStore;
import com.facebook.share.internal.ShareConstants;
import com.google.android.gms.common.internal.bf;
import com.google.android.gms.internal.measurement.zzaa;
import com.google.android.gms.internal.measurement.zzab;
import com.google.android.gms.internal.measurement.zzac;
import com.google.android.gms.internal.measurement.zzad;
import com.google.android.gms.internal.measurement.zzae;
import com.google.android.gms.internal.measurement.zzaf;
import com.google.android.gms.internal.measurement.zzag;
import com.google.android.gms.internal.measurement.zzaq;
import com.google.android.gms.internal.measurement.zzas;
import com.google.android.gms.internal.measurement.zzat;
import com.google.android.gms.internal.measurement.zzaw;
import com.google.android.gms.internal.measurement.zzch;
import com.google.android.gms.internal.measurement.zzdd;
import com.google.android.gms.internal.measurement.zzu;
import com.google.android.gms.internal.measurement.zzv;
import com.google.android.gms.internal.measurement.zzw;
import com.google.android.gms.internal.measurement.zzx;
import com.google.android.gms.internal.measurement.zzy;
import com.google.android.gms.internal.measurement.zzz;
import com.google.firebase.analytics.FirebaseAnalytics;
import java.text.DecimalFormat;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

/* JADX INFO: loaded from: classes.dex */
public final class e extends zzaq implements u {
    private static DecimalFormat bEq;
    private final String bEr;
    private final zzat zzqh;
    private final Uri zzqn;

    public e(zzat zzatVar, String str) {
        this(zzatVar, str, true, false);
    }

    private e(zzat zzatVar, String str, boolean z, boolean z2) {
        super(zzatVar);
        bf.id(str);
        this.zzqh = zzatVar;
        this.bEr = str;
        this.zzqn = hK(this.bEr);
    }

    private static void a(Map<String, String> map, String str, double d) {
        if (d != 0.0d) {
            map.put(str, b(d));
        }
    }

    private static void a(Map<String, String> map, String str, int i, int i2) {
        if (i <= 0 || i2 <= 0) {
            return;
        }
        map.put(str, new StringBuilder(23).append(i).append("x").append(i2).toString());
    }

    private static void a(Map<String, String> map, String str, String str2) {
        if (TextUtils.isEmpty(str2)) {
            return;
        }
        map.put(str, str2);
    }

    private static void a(Map<String, String> map, String str, boolean z) {
        if (z) {
            map.put(str, AppEventsConstants.EVENT_PARAM_VALUE_YES);
        }
    }

    private static String b(double d) {
        if (bEq == null) {
            bEq = new DecimalFormat("0.######");
        }
        return bEq.format(d);
    }

    private static Map<String, String> c(j jVar) {
        String strValueOf;
        HashMap map = new HashMap();
        zzy zzyVar = (zzy) jVar.a(zzy.class);
        if (zzyVar != null) {
            for (Map.Entry<String, Object> entry : zzyVar.zzas().entrySet()) {
                Object value = entry.getValue();
                if (value == null) {
                    strValueOf = null;
                } else if (value instanceof String) {
                    strValueOf = (String) value;
                    if (TextUtils.isEmpty(strValueOf)) {
                        strValueOf = null;
                    }
                } else if (value instanceof Double) {
                    Double d = (Double) value;
                    strValueOf = d.doubleValue() != 0.0d ? b(d.doubleValue()) : null;
                } else if (value instanceof Boolean) {
                    strValueOf = value != Boolean.FALSE ? AppEventsConstants.EVENT_PARAM_VALUE_YES : null;
                } else {
                    strValueOf = String.valueOf(value);
                }
                if (strValueOf != null) {
                    map.put(entry.getKey(), strValueOf);
                }
            }
        }
        zzad zzadVar = (zzad) jVar.a(zzad.class);
        if (zzadVar != null) {
            a(map, "t", zzadVar.zzay());
            a(map, "cid", zzadVar.zzaz());
            a(map, "uid", zzadVar.zzba());
            a(map, "sc", zzadVar.zzbd());
            a(map, "sf", zzadVar.zzbf());
            a(map, "ni", zzadVar.zzbe());
            a(map, "adid", zzadVar.zzbb());
            a(map, "ate", zzadVar.zzbc());
        }
        zzae zzaeVar = (zzae) jVar.a(zzae.class);
        if (zzaeVar != null) {
            a(map, "cd", zzaeVar.zzbg());
            a(map, "a", zzaeVar.zzbh());
            a(map, "dr", zzaeVar.zzbi());
        }
        zzab zzabVar = (zzab) jVar.a(zzab.class);
        if (zzabVar != null) {
            a(map, "ec", zzabVar.zzax());
            a(map, "ea", zzabVar.getAction());
            a(map, "el", zzabVar.getLabel());
            a(map, "ev", zzabVar.getValue());
        }
        zzv zzvVar = (zzv) jVar.a(zzv.class);
        if (zzvVar != null) {
            a(map, "cn", zzvVar.getName());
            a(map, "cs", zzvVar.getSource());
            a(map, "cm", zzvVar.zzaj());
            a(map, "ck", zzvVar.zzak());
            a(map, "cc", zzvVar.zzal());
            a(map, "ci", zzvVar.getId());
            a(map, "anid", zzvVar.zzam());
            a(map, "gclid", zzvVar.zzan());
            a(map, "dclid", zzvVar.zzao());
            a(map, FirebaseAnalytics.Param.ACLID, zzvVar.zzap());
        }
        zzac zzacVar = (zzac) jVar.a(zzac.class);
        if (zzacVar != null) {
            a(map, "exd", zzacVar.zztw);
            a(map, "exf", zzacVar.zztx);
        }
        zzaf zzafVar = (zzaf) jVar.a(zzaf.class);
        if (zzafVar != null) {
            a(map, "sn", zzafVar.zzun);
            a(map, "sa", zzafVar.zzuo);
            a(map, UserDataStore.STATE, zzafVar.zzup);
        }
        zzag zzagVar = (zzag) jVar.a(zzag.class);
        if (zzagVar != null) {
            a(map, "utv", zzagVar.zzuq);
            a(map, "utt", zzagVar.zzur);
            a(map, "utc", zzagVar.mCategory);
            a(map, "utl", zzagVar.zzus);
        }
        zzw zzwVar = (zzw) jVar.a(zzw.class);
        if (zzwVar != null) {
            for (Map.Entry<Integer, String> entry2 : zzwVar.zzaq().entrySet()) {
                String strNf = g.nf(entry2.getKey().intValue());
                if (!TextUtils.isEmpty(strNf)) {
                    map.put(strNf, entry2.getValue());
                }
            }
        }
        zzx zzxVar = (zzx) jVar.a(zzx.class);
        if (zzxVar != null) {
            for (Map.Entry<Integer, Double> entry3 : zzxVar.zzar().entrySet()) {
                String strNg = g.ng(entry3.getKey().intValue());
                if (!TextUtils.isEmpty(strNg)) {
                    map.put(strNg, b(entry3.getValue().doubleValue()));
                }
            }
        }
        zzaa zzaaVar = (zzaa) jVar.a(zzaa.class);
        if (zzaaVar != null) {
            com.google.android.gms.a.a.b bVarZzat = zzaaVar.zzat();
            if (bVarZzat != null) {
                for (Map.Entry<String, String> entry4 : bVarZzat.Xl().entrySet()) {
                    if (entry4.getKey().startsWith("&")) {
                        map.put(entry4.getKey().substring(1), entry4.getValue());
                    } else {
                        map.put(entry4.getKey(), entry4.getValue());
                    }
                }
            }
            Iterator<com.google.android.gms.a.a.c> it = zzaaVar.zzaw().iterator();
            int i = 1;
            while (it.hasNext()) {
                map.putAll(it.next().hI(g.ni(i)));
                i++;
            }
            Iterator<com.google.android.gms.a.a.a> it2 = zzaaVar.zzau().iterator();
            int i2 = 1;
            while (it2.hasNext()) {
                map.putAll(it2.next().hI(g.nh(i2)));
                i2++;
            }
            int i3 = 1;
            for (Map.Entry<String, List<com.google.android.gms.a.a.a>> entry5 : zzaaVar.zzav().entrySet()) {
                List<com.google.android.gms.a.a.a> value2 = entry5.getValue();
                String strNk = g.nk(i3);
                int i4 = 1;
                for (com.google.android.gms.a.a.a aVar : value2) {
                    String strValueOf2 = String.valueOf(strNk);
                    String strValueOf3 = String.valueOf(g.nj(i4));
                    map.putAll(aVar.hI(strValueOf3.length() != 0 ? strValueOf2.concat(strValueOf3) : new String(strValueOf2)));
                    i4++;
                }
                if (!TextUtils.isEmpty(entry5.getKey())) {
                    String strValueOf4 = String.valueOf(strNk);
                    String strValueOf5 = String.valueOf("nm");
                    map.put(strValueOf5.length() != 0 ? strValueOf4.concat(strValueOf5) : new String(strValueOf4), entry5.getKey());
                }
                i3++;
            }
        }
        zzz zzzVar = (zzz) jVar.a(zzz.class);
        if (zzzVar != null) {
            a(map, "ul", zzzVar.getLanguage());
            a(map, "sd", zzzVar.zztq);
            a(map, "sr", zzzVar.zztr, zzzVar.zzts);
            a(map, "vp", zzzVar.zztt, zzzVar.zztu);
        }
        zzu zzuVar = (zzu) jVar.a(zzu.class);
        if (zzuVar != null) {
            a(map, "an", zzuVar.zzaf());
            a(map, "aid", zzuVar.zzah());
            a(map, "aiid", zzuVar.zzai());
            a(map, "av", zzuVar.zzag());
        }
        return map;
    }

    static Uri hK(String str) {
        bf.id(str);
        Uri.Builder builder = new Uri.Builder();
        builder.scheme(ShareConstants.MEDIA_URI);
        builder.authority("google-analytics.com");
        builder.path(str);
        return builder.build();
    }

    @Override // com.google.android.gms.a.u
    public final Uri Xo() {
        return this.zzqn;
    }

    @Override // com.google.android.gms.a.u
    public final void b(j jVar) {
        bf.checkNotNull(jVar);
        bf.b(jVar.Xt(), "Can't deliver not submitted measurement");
        bf.hW("deliver should be called on worker thread");
        j jVarXp = jVar.Xp();
        zzad zzadVar = (zzad) jVarXp.b(zzad.class);
        if (TextUtils.isEmpty(zzadVar.zzay())) {
            zzbu().zza(c(jVarXp), "Ignoring measurement without type");
            return;
        }
        if (TextUtils.isEmpty(zzadVar.zzaz())) {
            zzbu().zza(c(jVarXp), "Ignoring measurement without client id");
            return;
        }
        if (this.zzqh.zzck().Xk()) {
            return;
        }
        double dZzbf = zzadVar.zzbf();
        if (zzdd.zza(dZzbf, zzadVar.zzaz())) {
            zzb("Sampling enabled. Hit sampled out. sampling rate", Double.valueOf(dZzbf));
            return;
        }
        Map<String, String> mapC = c(jVarXp);
        mapC.put("v", AppEventsConstants.EVENT_PARAM_VALUE_YES);
        mapC.put("_v", zzas.zzvk);
        mapC.put("tid", this.bEr);
        if (this.zzqh.zzck().Xj()) {
            StringBuilder sb = new StringBuilder();
            for (Map.Entry<String, String> entry : mapC.entrySet()) {
                if (sb.length() != 0) {
                    sb.append(", ");
                }
                sb.append(entry.getKey());
                sb.append("=");
                sb.append(entry.getValue());
            }
            zzc("Dry run is enabled. GoogleAnalytics would have sent", sb.toString());
            return;
        }
        HashMap map = new HashMap();
        zzdd.zzb(map, "uid", zzadVar.zzba());
        zzu zzuVar = (zzu) jVar.a(zzu.class);
        if (zzuVar != null) {
            zzdd.zzb(map, "an", zzuVar.zzaf());
            zzdd.zzb(map, "aid", zzuVar.zzah());
            zzdd.zzb(map, "av", zzuVar.zzag());
            zzdd.zzb(map, "aiid", zzuVar.zzai());
        }
        mapC.put("_s", String.valueOf(zzby().zza(new zzaw(0L, zzadVar.zzaz(), this.bEr, !TextUtils.isEmpty(zzadVar.zzbb()), 0L, map))));
        zzby().zza(new zzch(zzbu(), mapC, jVar.Xr(), true));
    }
}
