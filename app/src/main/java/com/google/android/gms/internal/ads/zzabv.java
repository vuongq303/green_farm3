package com.google.android.gms.internal.ads;

import android.content.Context;
import android.graphics.Color;
import android.net.Uri;
import android.os.RemoteException;
import android.text.TextUtils;
import com.facebook.appevents.AppEventsConstants;
import com.facebook.appevents.codeless.internal.Constants;
import com.facebook.share.internal.MessengerShareContentUtility;
import com.gameloft.glads.AndroidWebView;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;
import java.util.List;
import java.util.concurrent.Callable;
import java.util.concurrent.CancellationException;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Future;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;
import java.util.concurrent.atomic.AtomicInteger;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

/* JADX INFO: loaded from: classes.dex */
@zzadh
public final class zzabv implements Callable<zzajh> {
    private static long zzbzx = 10;
    private final Context mContext;
    private final zzacm zzaad;
    private final zzci zzbjc;
    private final zzaji zzbze;
    private final zzalt zzbzy;
    private final com.google.android.gms.ads.internal.zzbc zzbzz;
    private JSONObject zzcac;
    private final zznx zzvr;
    private final Object mLock = new Object();
    private boolean zzcaa = false;
    private int mErrorCode = -2;
    private List<String> zzcab = null;
    private String zzcad = null;
    private String zzcae = null;

    public zzabv(Context context, com.google.android.gms.ads.internal.zzbc zzbcVar, zzalt zzaltVar, zzci zzciVar, zzaji zzajiVar, zznx zznxVar) {
        this.mContext = context;
        this.zzbzz = zzbcVar;
        this.zzbzy = zzaltVar;
        this.zzbze = zzajiVar;
        this.zzbjc = zzciVar;
        this.zzvr = zznxVar;
        this.zzaad = zzbcVar.zzdr();
    }

    private final zzajh zza(zzpb zzpbVar, boolean z) {
        int i;
        synchronized (this.mLock) {
            i = this.mErrorCode;
            if (zzpbVar == null && this.mErrorCode == -2) {
                i = 0;
            }
        }
        return new zzajh(this.zzbze.zzcgs.zzccv, null, this.zzbze.zzcos.zzbsn, i, this.zzbze.zzcos.zzbso, this.zzcab, this.zzbze.zzcos.orientation, this.zzbze.zzcos.zzbsu, this.zzbze.zzcgs.zzccy, false, null, null, null, null, null, 0L, this.zzbze.zzacv, this.zzbze.zzcos.zzcep, this.zzbze.zzcoh, this.zzbze.zzcoi, this.zzbze.zzcos.zzcev, this.zzcac, i != -2 ? null : zzpbVar, null, null, null, this.zzbze.zzcos.zzcfh, this.zzbze.zzcos.zzcfi, null, this.zzbze.zzcos.zzbsr, this.zzcad, this.zzbze.zzcoq, this.zzbze.zzcos.zzzl, this.zzbze.zzcor, z, this.zzbze.zzcos.zzbsp, this.zzbze.zzcos.zzzm, this.zzcae);
    }

    private final zzanz<zzon> zza(JSONObject jSONObject, boolean z, boolean z2) {
        String string = z ? jSONObject.getString("url") : jSONObject.optString("url");
        double dOptDouble = jSONObject.optDouble("scale", 1.0d);
        boolean zOptBoolean = jSONObject.optBoolean("is_transparent", true);
        if (!TextUtils.isEmpty(string)) {
            return z2 ? zzano.zzi(new zzon(null, Uri.parse(string), dOptDouble)) : this.zzbzy.zza(string, new zzacb(this, z, dOptDouble, zOptBoolean, string));
        }
        zzd(0, z);
        return zzano.zzi(null);
    }

    private final void zzab(int i) {
        synchronized (this.mLock) {
            this.zzcaa = true;
            this.mErrorCode = i;
        }
    }

    private static zzaqw zzb(zzanz<zzaqw> zzanzVar) {
        try {
            return zzanzVar.get(((Integer) zzkb.zzik().zzd(zznk.zzbby)).intValue(), TimeUnit.MILLISECONDS);
        } catch (InterruptedException e) {
            zzane.zzc("", e);
            Thread.currentThread().interrupt();
            return null;
        } catch (CancellationException e2) {
            e = e2;
            zzane.zzc("", e);
            return null;
        } catch (ExecutionException e3) {
            e = e3;
            zzane.zzc("", e);
            return null;
        } catch (TimeoutException e4) {
            e = e4;
            zzane.zzc("", e);
            return null;
        }
    }

    private static Integer zzb(JSONObject jSONObject, String str) {
        try {
            JSONObject jSONObject2 = jSONObject.getJSONObject(str);
            return Integer.valueOf(Color.rgb(jSONObject2.getInt("r"), jSONObject2.getInt("g"), jSONObject2.getInt("b")));
        } catch (JSONException e) {
            return null;
        }
    }

    static zzaqw zzc(zzanz<zzaqw> zzanzVar) {
        try {
            return zzanzVar.get(((Integer) zzkb.zzik().zzd(zznk.zzbbx)).intValue(), TimeUnit.SECONDS);
        } catch (InterruptedException e) {
            zzakb.zzc("InterruptedException occurred while waiting for video to load", e);
            Thread.currentThread().interrupt();
            return null;
        } catch (CancellationException e2) {
            e = e2;
            zzakb.zzc("Exception occurred while waiting for video to load", e);
            return null;
        } catch (ExecutionException e3) {
            e = e3;
            zzakb.zzc("Exception occurred while waiting for video to load", e);
            return null;
        } catch (TimeoutException e4) {
            e = e4;
            zzakb.zzc("Exception occurred while waiting for video to load", e);
            return null;
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public final void zzc(zzqs zzqsVar, String str) {
        try {
            zzrc zzrcVarZzr = this.zzbzz.zzr(zzqsVar.getCustomTemplateId());
            if (zzrcVarZzr != null) {
                zzrcVarZzr.zzb(zzqsVar, str);
            }
        } catch (RemoteException e) {
            zzakb.zzc(new StringBuilder(String.valueOf(str).length() + 40).append("Failed to call onCustomClick for asset ").append(str).append(".").toString(), e);
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static <V> List<V> zzk(List<zzanz<V>> list) {
        ArrayList arrayList = new ArrayList();
        Iterator<zzanz<V>> it = list.iterator();
        while (it.hasNext()) {
            V v = it.next().get();
            if (v != null) {
                arrayList.add(v);
            }
        }
        return arrayList;
    }

    /* JADX INFO: Access modifiers changed from: private */
    /* JADX WARN: Code duplicated, block: B:13:0x0040 A[Catch: CancellationException -> 0x019c, ExecutionException -> 0x01b5, TimeoutException -> 0x0220, Exception -> 0x0229, InterruptedException -> 0x0232, JSONException -> 0x0235, TryCatch #2 {InterruptedException -> 0x0232, CancellationException -> 0x019c, ExecutionException -> 0x01b5, TimeoutException -> 0x0220, JSONException -> 0x0235, Exception -> 0x0229, blocks: (B:3:0x0002, B:5:0x000e, B:7:0x002a, B:9:0x0033, B:11:0x003a, B:14:0x0044, B:16:0x005e, B:17:0x0071, B:19:0x007a, B:20:0x0080, B:43:0x00f0, B:45:0x00ff, B:46:0x0108, B:48:0x0110, B:49:0x0119, B:51:0x0122, B:24:0x0089, B:79:0x01b7, B:84:0x01cb, B:29:0x0094, B:31:0x0098, B:32:0x00a9, B:89:0x021b, B:85:0x0205, B:86:0x020c, B:88:0x0212, B:54:0x0132, B:56:0x013b, B:57:0x0147, B:59:0x0150, B:61:0x0170, B:62:0x0178, B:64:0x018c, B:65:0x0190, B:67:0x0196, B:76:0x01b1, B:36:0x00bb, B:38:0x00c4, B:39:0x00cb, B:41:0x00d8, B:42:0x00df, B:13:0x0040), top: B:99:0x0002 }] */
    /* JADX WARN: Code duplicated, block: B:35:0x00b9  */
    /* JADX WARN: Code duplicated, block: B:73:0x01a7  */
    @Override // java.util.concurrent.Callable
    /* JADX INFO: renamed from: zznw, reason: merged with bridge method [inline-methods] */
    public final zzajh call() {
        JSONObject jSONObject;
        JSONObject jSONObjectOptJSONObject;
        zzanz zzanzVarZzi;
        zzacd zzacpVar;
        zzpb zzpbVar;
        String[] strArr;
        try {
            String uuid = this.zzbzz.getUuid();
            if (zznx()) {
                jSONObject = null;
            } else {
                JSONObject jSONObject2 = new JSONObject(this.zzbze.zzcos.zzceo);
                JSONObject jSONObject3 = new JSONObject(this.zzbze.zzcos.zzceo);
                if (jSONObject3.length() == 0) {
                    zzab(3);
                } else {
                    JSONArray jSONArrayOptJSONArray = jSONObject3.optJSONArray("ads");
                    JSONObject jSONObjectOptJSONObject2 = jSONArrayOptJSONArray != null ? jSONArrayOptJSONArray.optJSONObject(0) : null;
                    if (jSONObjectOptJSONObject2 == null || jSONObjectOptJSONObject2.length() == 0) {
                        zzab(3);
                    }
                }
                JSONObject jSONObject4 = this.zzaad.zzh(jSONObject2).get(zzbzx, TimeUnit.SECONDS);
                if (jSONObject4.optBoolean("success", false)) {
                    jSONObject = jSONObject4.getJSONObject("json").optJSONArray("ads").getJSONObject(0);
                } else {
                    jSONObject = null;
                }
            }
            boolean zOptBoolean = jSONObject.optBoolean("enable_omid");
            if (zOptBoolean && (jSONObjectOptJSONObject = jSONObject.optJSONObject("omid_settings")) != null) {
                final String strOptString = jSONObjectOptJSONObject.optString("omid_html");
                if (TextUtils.isEmpty(strOptString)) {
                    zzanzVarZzi = zzano.zzi(null);
                } else {
                    final zzaoj zzaojVar = new zzaoj();
                    zzaoe.zzcvy.execute(new Runnable(this, zzaojVar, strOptString) { // from class: com.google.android.gms.internal.ads.zzabw
                        private final zzabv zzcaf;
                        private final zzaoj zzcag;
                        private final String zzcah;

                        {
                            this.zzcaf = this;
                            this.zzcag = zzaojVar;
                            this.zzcah = strOptString;
                        }

                        @Override // java.lang.Runnable
                        public final void run() {
                            this.zzcaf.zza(this.zzcag, this.zzcah);
                        }
                    });
                    zzanzVarZzi = zzaojVar;
                }
            } else {
                zzanzVarZzi = zzano.zzi(null);
            }
            if (zznx() || jSONObject == null) {
                zzacpVar = null;
            } else {
                String string = jSONObject.getString("template_id");
                boolean z = this.zzbze.zzcgs.zzadj != null ? this.zzbze.zzcgs.zzadj.zzbjn : false;
                boolean z2 = this.zzbze.zzcgs.zzadj != null ? this.zzbze.zzcgs.zzadj.zzbjp : false;
                if ("2".equals(string)) {
                    zzacpVar = new zzacn(z, z2, this.zzbze.zzcor);
                } else if (AppEventsConstants.EVENT_PARAM_VALUE_YES.equals(string)) {
                    zzacpVar = new zzaco(z, z2, this.zzbze.zzcor);
                } else {
                    if ("3".equals(string)) {
                        String string2 = jSONObject.getString("custom_template_id");
                        zzaoj zzaojVar2 = new zzaoj();
                        zzakk.zzcrm.post(new zzaby(this, zzaojVar2, string2));
                        if (zzaojVar2.get(zzbzx, TimeUnit.SECONDS) != null) {
                            zzacpVar = new zzacp(z);
                        } else {
                            String strValueOf = String.valueOf(jSONObject.getString("custom_template_id"));
                            zzakb.e(strValueOf.length() != 0 ? "No handler for custom template: ".concat(strValueOf) : new String("No handler for custom template: "));
                        }
                    } else {
                        zzab(0);
                    }
                    zzacpVar = null;
                }
            }
            if (zznx() || zzacpVar == null || jSONObject == null) {
                zzpbVar = null;
            } else {
                JSONObject jSONObject5 = jSONObject.getJSONObject("tracking_urls_and_actions");
                JSONArray jSONArrayOptJSONArray2 = jSONObject5.optJSONArray("impression_tracking_urls");
                if (jSONArrayOptJSONArray2 == null) {
                    strArr = null;
                } else {
                    strArr = new String[jSONArrayOptJSONArray2.length()];
                    for (int i = 0; i < jSONArrayOptJSONArray2.length(); i++) {
                        strArr[i] = jSONArrayOptJSONArray2.getString(i);
                    }
                }
                this.zzcab = strArr == null ? null : Arrays.asList(strArr);
                this.zzcac = jSONObject5.optJSONObject("active_view");
                this.zzcad = jSONObject.optString("debug_signals");
                this.zzcae = jSONObject.optString("omid_settings");
                zzpb zzpbVarZza = zzacpVar.zza(this, jSONObject);
                zzpbVarZza.zzb(new zzpd(this.mContext, this.zzbzz, this.zzaad, this.zzbjc, jSONObject, zzpbVarZza, this.zzbze.zzcgs.zzacr, uuid));
                zzpbVar = zzpbVarZza;
            }
            if (zzpbVar instanceof zzos) {
                this.zzaad.zza("/nativeAdCustomClick", new zzabz(this, (zzos) zzpbVar));
            }
            zzajh zzajhVarZza = zza(zzpbVar, zOptBoolean);
            this.zzbzz.zzg(zzb(zzanzVarZzi));
            return zzajhVarZza;
        } catch (InterruptedException e) {
            e = e;
            zzakb.zzc("Malformed native JSON response.", e);
            if (!this.zzcaa) {
                zzab(0);
            }
            return zza((zzpb) null, false);
        } catch (CancellationException e2) {
            e = e2;
            zzakb.zzc("Malformed native JSON response.", e);
            if (!this.zzcaa) {
                zzab(0);
            }
            return zza((zzpb) null, false);
        } catch (ExecutionException e3) {
            e = e3;
            zzakb.zzc("Malformed native JSON response.", e);
            if (!this.zzcaa) {
                zzab(0);
            }
            return zza((zzpb) null, false);
        } catch (TimeoutException e4) {
            zzakb.zzc("Timeout when loading native ad.", e4);
            if (!this.zzcaa) {
                zzab(0);
            }
            return zza((zzpb) null, false);
        } catch (JSONException e5) {
            e = e5;
            zzakb.zzc("Malformed native JSON response.", e);
            if (!this.zzcaa) {
                zzab(0);
            }
            return zza((zzpb) null, false);
        } catch (Exception e6) {
            zzakb.zzc("Error occured while doing native ads initialization.", e6);
            if (!this.zzcaa) {
                zzab(0);
            }
            return zza((zzpb) null, false);
        }
    }

    private final boolean zznx() {
        boolean z;
        synchronized (this.mLock) {
            z = this.zzcaa;
        }
        return z;
    }

    public final zzanz<zzon> zza(JSONObject jSONObject, String str, boolean z, boolean z2) {
        JSONObject jSONObject2 = z ? jSONObject.getJSONObject(str) : jSONObject.optJSONObject(str);
        if (jSONObject2 == null) {
            jSONObject2 = new JSONObject();
        }
        return zza(jSONObject2, z, z2);
    }

    public final List<zzanz<zzon>> zza(JSONObject jSONObject, String str, boolean z, boolean z2, boolean z3) throws JSONException {
        JSONArray jSONArrayOptJSONArray = jSONObject.optJSONArray(str);
        ArrayList arrayList = new ArrayList();
        if (jSONArrayOptJSONArray == null || jSONArrayOptJSONArray.length() == 0) {
            zzd(0, false);
            return arrayList;
        }
        int length = z3 ? jSONArrayOptJSONArray.length() : 1;
        for (int i = 0; i < length; i++) {
            JSONObject jSONObject2 = jSONArrayOptJSONArray.getJSONObject(i);
            if (jSONObject2 == null) {
                jSONObject2 = new JSONObject();
            }
            arrayList.add(zza(jSONObject2, false, z2));
        }
        return arrayList;
    }

    public final Future<zzon> zza(JSONObject jSONObject, String str, boolean z) throws JSONException {
        JSONObject jSONObject2 = jSONObject.getJSONObject(str);
        boolean zOptBoolean = jSONObject2.optBoolean("require", true);
        if (jSONObject2 == null) {
            jSONObject2 = new JSONObject();
        }
        return zza(jSONObject2, zOptBoolean, z);
    }

    final /* synthetic */ void zza(final zzaoj zzaojVar, String str) {
        try {
            com.google.android.gms.ads.internal.zzbv.zzel();
            final zzaqw zzaqwVarZza = zzarc.zza(this.mContext, zzasi.zzvq(), "native-omid", false, false, this.zzbjc, this.zzbze.zzcgs.zzacr, this.zzvr, null, this.zzbzz.zzbi(), this.zzbze.zzcoq);
            zzaqwVarZza.zzuf().zza(new zzasd(zzaojVar, zzaqwVarZza) { // from class: com.google.android.gms.internal.ads.zzabx
                private final zzaoj zzbnu;
                private final zzaqw zzcai;

                {
                    this.zzbnu = zzaojVar;
                    this.zzcai = zzaqwVarZza;
                }

                @Override // com.google.android.gms.internal.ads.zzasd
                public final void zze(boolean z) {
                    this.zzbnu.set(this.zzcai);
                }
            });
            zzaqwVarZza.loadData(str, "text/html", AndroidWebView.UTF_8);
        } catch (Exception e) {
            zzaojVar.set(null);
            zzane.zzc("", e);
        }
    }

    public final zzanz<zzaqw> zzc(JSONObject jSONObject, String str) {
        final JSONObject jSONObjectOptJSONObject = jSONObject.optJSONObject(str);
        if (jSONObjectOptJSONObject == null) {
            return zzano.zzi(null);
        }
        if (TextUtils.isEmpty(jSONObjectOptJSONObject.optString("vast_xml"))) {
            zzakb.zzdk("Required field 'vast_xml' is missing");
            return zzano.zzi(null);
        }
        final zzace zzaceVar = new zzace(this.mContext, this.zzbjc, this.zzbze, this.zzvr, this.zzbzz);
        final zzaoj zzaojVar = new zzaoj();
        zzaoe.zzcvy.execute(new Runnable(zzaceVar, jSONObjectOptJSONObject, zzaojVar) { // from class: com.google.android.gms.internal.ads.zzacf
            private final zzaoj zzbns;
            private final zzace zzcbf;
            private final JSONObject zzcbg;

            {
                this.zzcbf = zzaceVar;
                this.zzcbg = jSONObjectOptJSONObject;
                this.zzbns = zzaojVar;
            }

            @Override // java.lang.Runnable
            public final void run() {
                this.zzcbf.zza(this.zzcbg, this.zzbns);
            }
        });
        return zzaojVar;
    }

    public final void zzd(int i, boolean z) {
        if (z) {
            zzab(i);
        }
    }

    public final zzanz<zzoj> zzg(JSONObject jSONObject) throws JSONException {
        List<zzanz<zzon>> listZza;
        JSONObject jSONObjectOptJSONObject = jSONObject.optJSONObject("attribution");
        if (jSONObjectOptJSONObject == null) {
            return zzano.zzi(null);
        }
        String strOptString = jSONObjectOptJSONObject.optString("text");
        int iOptInt = jSONObjectOptJSONObject.optInt("text_size", -1);
        Integer numZzb = zzb(jSONObjectOptJSONObject, "text_color");
        Integer numZzb2 = zzb(jSONObjectOptJSONObject, "bg_color");
        int iOptInt2 = jSONObjectOptJSONObject.optInt("animation_ms", Constants.APP_INDEXING_SCHEDULE_INTERVAL_MS);
        int iOptInt3 = jSONObjectOptJSONObject.optInt("presentation_ms", 4000);
        int i = (this.zzbze.zzcgs.zzadj == null || this.zzbze.zzcgs.zzadj.versionCode < 2) ? 1 : this.zzbze.zzcgs.zzadj.zzbjq;
        boolean zOptBoolean = jSONObjectOptJSONObject.optBoolean("allow_pub_rendering");
        ArrayList arrayList = new ArrayList();
        if (jSONObjectOptJSONObject.optJSONArray("images") != null) {
            listZza = zza(jSONObjectOptJSONObject, "images", false, false, true);
        } else {
            arrayList.add(zza(jSONObjectOptJSONObject, MessengerShareContentUtility.MEDIA_IMAGE, false, false));
            listZza = arrayList;
        }
        zzaoj zzaojVar = new zzaoj();
        int size = listZza.size();
        AtomicInteger atomicInteger = new AtomicInteger(0);
        Iterator<zzanz<zzon>> it = listZza.iterator();
        while (it.hasNext()) {
            it.next().zza(new zzacc(atomicInteger, size, zzaojVar, listZza), zzaki.zzcrj);
        }
        return zzano.zza(zzaojVar, new zzaca(this, strOptString, numZzb2, numZzb, iOptInt, iOptInt3, iOptInt2, i, zOptBoolean), zzaki.zzcrj);
    }
}
