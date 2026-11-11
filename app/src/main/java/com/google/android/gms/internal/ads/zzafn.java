package com.google.android.gms.internal.ads;

import android.content.Context;
import android.location.Location;
import android.net.ConnectivityManager;
import android.os.Bundle;
import android.text.TextUtils;
import com.facebook.appevents.codeless.internal.Constants;
import com.google.android.gms.ads.identifier.AdvertisingIdClient;
import com.google.android.gms.common.d.o;
import java.io.BufferedOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.UUID;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;
import javax.annotation.concurrent.GuardedBy;
import org.json.JSONException;
import org.json.JSONObject;

/* JADX INFO: loaded from: classes.dex */
@zzadh
public final class zzafn extends zzaeo {
    private static final Object sLock = new Object();

    @GuardedBy("sLock")
    private static zzafn zzchh;
    private final Context mContext;
    private final zzafm zzchi;
    private final ScheduledExecutorService zzchj = Executors.newSingleThreadScheduledExecutor();

    private zzafn(Context context, zzafm zzafmVar) {
        this.mContext = context;
        this.zzchi = zzafmVar;
    }

    private static zzaej zza(Context context, zzafm zzafmVar, zzaef zzaefVar, ScheduledExecutorService scheduledExecutorService) {
        String string;
        zzakb.zzck("Starting ad request from service using: google.afma.request.getAdDictionary");
        zznx zznxVar = new zznx(((Boolean) zzkb.zzik().zzd(zznk.zzawh)).booleanValue(), "load_ad", zzaefVar.zzacv.zzarb);
        if (zzaefVar.versionCode > 10 && zzaefVar.zzcdl != -1) {
            zznxVar.zza(zznxVar.zzd(zzaefVar.zzcdl), "cts");
        }
        zznv zznvVarZzjj = zznxVar.zzjj();
        zzanz zzanzVarZza = zzano.zza(zzafmVar.zzche.zzk(context), ((Long) zzkb.zzik().zzd(zznk.zzbdf)).longValue(), TimeUnit.MILLISECONDS, scheduledExecutorService);
        zzanz zzanzVarZza2 = zzano.zza(zzafmVar.zzchd.zzr(context), ((Long) zzkb.zzik().zzd(zznk.zzbah)).longValue(), TimeUnit.MILLISECONDS, scheduledExecutorService);
        zzanz<String> zzanzVarZzcl = zzafmVar.zzcgy.zzcl(zzaefVar.zzccw.packageName);
        zzanz<String> zzanzVarZzcm = zzafmVar.zzcgy.zzcm(zzaefVar.zzccw.packageName);
        zzanz<String> zzanzVarZza3 = zzafmVar.zzchf.zza(zzaefVar.zzccx, zzaefVar.zzccw);
        Future<zzaga> futureZzq = com.google.android.gms.ads.internal.zzbv.zzev().zzq(context);
        zzanz<Location> zzanzVarZzi = zzano.zzi(null);
        Bundle bundle = zzaefVar.zzccv.extras;
        boolean z = (bundle == null || bundle.getString("_ad") == null) ? false : true;
        if (zzaefVar.zzcdr && !z) {
            zzanzVarZzi = zzafmVar.zzchb.zza(zzaefVar.applicationInfo);
        }
        zzanz zzanzVarZza4 = zzano.zza(zzanzVarZzi, ((Long) zzkb.zzik().zzd(zznk.zzbco)).longValue(), TimeUnit.MILLISECONDS, scheduledExecutorService);
        zzanz zzanzVarZza5 = ((Boolean) zzkb.zzik().zzd(zznk.zzayj)).booleanValue() ? zzano.zza(zzafmVar.zzchf.zzae(context), ((Long) zzkb.zzik().zzd(zznk.zzayk)).longValue(), TimeUnit.MILLISECONDS, scheduledExecutorService) : zzano.zzi(null);
        Bundle bundle2 = (zzaefVar.versionCode < 4 || zzaefVar.zzcdc == null) ? null : zzaefVar.zzcdc;
        ((Boolean) zzkb.zzik().zzd(zznk.zzawx)).booleanValue();
        com.google.android.gms.ads.internal.zzbv.zzek();
        if (zzakk.zzl(context, "android.permission.ACCESS_NETWORK_STATE") && ((ConnectivityManager) context.getSystemService("connectivity")).getActiveNetworkInfo() == null) {
            zzakb.zzck("Device is offline.");
        }
        String string2 = zzaefVar.versionCode >= 7 ? zzaefVar.zzcdi : UUID.randomUUID().toString();
        new zzaft(context, string2, zzaefVar.applicationInfo.packageName);
        if (zzaefVar.zzccv.extras != null && (string = zzaefVar.zzccv.extras.getString("_ad")) != null) {
            return zzafs.zza(context, zzaefVar, string);
        }
        List<String> listZzf = zzafmVar.zzcgz.zzf(zzaefVar.zzcdj);
        Bundle bundle3 = (Bundle) zzano.zza(zzanzVarZza, (Object) null, ((Long) zzkb.zzik().zzd(zznk.zzbdf)).longValue(), TimeUnit.MILLISECONDS);
        zzagk zzagkVar = (zzagk) zzano.zza(zzanzVarZza2, (Object) null);
        Location location = (Location) zzano.zza(zzanzVarZza4, (Object) null);
        AdvertisingIdClient.Info info = (AdvertisingIdClient.Info) zzano.zza(zzanzVarZza5, (Object) null);
        String str = (String) zzano.zza(zzanzVarZza3, (Object) null);
        String str2 = (String) zzano.zza(zzanzVarZzcl, (Object) null);
        String str3 = (String) zzano.zza(zzanzVarZzcm, (Object) null);
        zzaga zzagaVar = (zzaga) zzano.zza(futureZzq, (Object) null);
        if (zzagaVar == null) {
            zzakb.zzdk("Error fetching device info. This is not recoverable.");
            return new zzaej(0);
        }
        zzafl zzaflVar = new zzafl();
        zzaflVar.zzcgs = zzaefVar;
        zzaflVar.zzcgt = zzagaVar;
        zzaflVar.zzcgo = zzagkVar;
        zzaflVar.zzaqe = location;
        zzaflVar.zzcgn = bundle3;
        zzaflVar.zzccx = str;
        zzaflVar.zzcgr = info;
        if (listZzf == null) {
            zzaflVar.zzcdj.clear();
        }
        zzaflVar.zzcdj = listZzf;
        zzaflVar.zzcdc = bundle2;
        zzaflVar.zzcgp = str2;
        zzaflVar.zzcgq = str3;
        zzaflVar.zzcgu = zzafmVar.zzcgx.zze(context);
        zzaflVar.zzcgv = zzafmVar.zzcgv;
        JSONObject jSONObjectZza = zzafs.zza(context, zzaflVar);
        if (jSONObjectZza == null) {
            return new zzaej(0);
        }
        if (zzaefVar.versionCode < 7) {
            try {
                jSONObjectZza.put("request_id", string2);
            } catch (JSONException e) {
            }
        }
        zznxVar.zza(zznvVarZzjj, "arc");
        zznxVar.zzjj();
        zzanz zzanzVarZza6 = zzano.zza(zzano.zza(zzafmVar.zzchg.zzof().zzf(jSONObjectZza), zzafo.zzxn, scheduledExecutorService), 10L, TimeUnit.SECONDS, scheduledExecutorService);
        zzanz<Void> zzanzVarZzop = zzafmVar.zzcha.zzop();
        if (zzanzVarZzop != null) {
            zzanm.zza(zzanzVarZzop, "AdRequestServiceImpl.loadAd.flags");
        }
        zzafz zzafzVar = (zzafz) zzano.zza(zzanzVarZza6, (Object) null);
        if (zzafzVar == null) {
            return new zzaej(0);
        }
        if (zzafzVar.getErrorCode() != -2) {
            return new zzaej(zzafzVar.getErrorCode());
        }
        zznxVar.zzjm();
        zzaej zzaejVarZza = TextUtils.isEmpty(zzafzVar.zzom()) ? null : zzafs.zza(context, zzaefVar, zzafzVar.zzom());
        if (zzaejVarZza == null && !TextUtils.isEmpty(zzafzVar.getUrl())) {
            zzaejVarZza = zza(zzaefVar, context, zzaefVar.zzacr.zzcw, zzafzVar.getUrl(), str2, str3, zzafzVar, zznxVar, zzafmVar);
        }
        if (zzaejVarZza == null) {
            zzaejVarZza = new zzaej(0);
        }
        zznxVar.zza(zznvVarZzjj, "tts");
        zzaejVarZza.zzcfd = zznxVar.zzjk();
        return zzaejVarZza;
    }

    public static zzaej zza(zzaef zzaefVar, Context context, String str, String str2, String str3, String str4, zzafz zzafzVar, zznx zznxVar, zzafm zzafmVar) {
        InputStreamReader inputStreamReader;
        BufferedOutputStream bufferedOutputStream;
        zznv zznvVarZzjj = zznxVar != null ? zznxVar.zzjj() : null;
        try {
            zzafx zzafxVar = new zzafx(zzaefVar, zzafzVar.zzoi());
            String strValueOf = String.valueOf(str2);
            zzakb.zzck(strValueOf.length() != 0 ? "AdRequestServiceImpl: Sending request: ".concat(strValueOf) : new String("AdRequestServiceImpl: Sending request: "));
            URL url = new URL(str2);
            long jElapsedRealtime = com.google.android.gms.ads.internal.zzbv.zzer().elapsedRealtime();
            int i = 0;
            URL url2 = url;
            while (true) {
                if (zzafmVar != null) {
                    zzafmVar.zzchc.zzoq();
                }
                HttpURLConnection httpURLConnection = (HttpURLConnection) url2.openConnection();
                try {
                    com.google.android.gms.ads.internal.zzbv.zzek().zza(context, str, false, httpURLConnection);
                    if (zzafzVar.zzok()) {
                        if (!TextUtils.isEmpty(str3)) {
                            httpURLConnection.addRequestProperty("x-afma-drt-cookie", str3);
                        }
                        if (!TextUtils.isEmpty(str4)) {
                            httpURLConnection.addRequestProperty("x-afma-drt-v2-cookie", str4);
                        }
                    }
                    String str5 = zzaefVar.zzcds;
                    if (!TextUtils.isEmpty(str5)) {
                        zzakb.zzck("Sending webview cookie in ad request header.");
                        httpURLConnection.addRequestProperty("Cookie", str5);
                    }
                    byte[] bytes = null;
                    if (zzafzVar != null && !TextUtils.isEmpty(zzafzVar.zzoj())) {
                        httpURLConnection.setDoOutput(true);
                        bytes = zzafzVar.zzoj().getBytes();
                        httpURLConnection.setFixedLengthStreamingMode(bytes.length);
                        try {
                            BufferedOutputStream bufferedOutputStream2 = new BufferedOutputStream(httpURLConnection.getOutputStream());
                            try {
                                bufferedOutputStream2.write(bytes);
                                o.closeQuietly(bufferedOutputStream2);
                            } catch (Throwable th) {
                                th = th;
                                bufferedOutputStream = bufferedOutputStream2;
                                o.closeQuietly(bufferedOutputStream);
                                throw th;
                            }
                        } catch (Throwable th2) {
                            th = th2;
                            bufferedOutputStream = null;
                        }
                    }
                    zzamy zzamyVar = new zzamy(zzaefVar.zzcdi);
                    zzamyVar.zza(httpURLConnection, bytes);
                    int responseCode = httpURLConnection.getResponseCode();
                    Map<String, List<String>> headerFields = httpURLConnection.getHeaderFields();
                    zzamyVar.zza(httpURLConnection, responseCode);
                    if (responseCode < 200 || responseCode >= 300) {
                        zza(url2.toString(), headerFields, (String) null, responseCode);
                        if (responseCode < 300 || responseCode >= 400) {
                            zzakb.zzdk(new StringBuilder(46).append("Received error HTTP response code: ").append(responseCode).toString());
                            zzaej zzaejVar = new zzaej(0);
                            httpURLConnection.disconnect();
                            if (zzafmVar != null) {
                                zzafmVar.zzchc.zzor();
                            }
                            return zzaejVar;
                        }
                        String headerField = httpURLConnection.getHeaderField("Location");
                        if (TextUtils.isEmpty(headerField)) {
                            zzakb.zzdk("No location header to follow redirect.");
                            zzaej zzaejVar2 = new zzaej(0);
                            httpURLConnection.disconnect();
                            if (zzafmVar != null) {
                                zzafmVar.zzchc.zzor();
                            }
                            return zzaejVar2;
                        }
                        url2 = new URL(headerField);
                        i++;
                        if (i > ((Integer) zzkb.zzik().zzd(zznk.zzbes)).intValue()) {
                            zzakb.zzdk("Too many redirects.");
                            zzaej zzaejVar3 = new zzaej(0);
                            httpURLConnection.disconnect();
                            if (zzafmVar != null) {
                                zzafmVar.zzchc.zzor();
                            }
                            return zzaejVar3;
                        }
                        zzafxVar.zzl(headerFields);
                        httpURLConnection.disconnect();
                        if (zzafmVar != null) {
                            zzafmVar.zzchc.zzor();
                        }
                    } else {
                        String string = url2.toString();
                        try {
                            InputStreamReader inputStreamReader2 = new InputStreamReader(httpURLConnection.getInputStream());
                            try {
                                com.google.android.gms.ads.internal.zzbv.zzek();
                                String strZza = zzakk.zza(inputStreamReader2);
                                o.closeQuietly(inputStreamReader2);
                                zzamyVar.zzdg(strZza);
                                zza(string, headerFields, strZza, responseCode);
                                zzafxVar.zza(string, headerFields, strZza);
                                if (zznxVar != null) {
                                    zznxVar.zza(zznvVarZzjj, "ufe");
                                }
                                zzaej zzaejVarZza = zzafxVar.zza(jElapsedRealtime, zzafzVar.zzon());
                                httpURLConnection.disconnect();
                                if (zzafmVar != null) {
                                    zzafmVar.zzchc.zzor();
                                }
                                return zzaejVarZza;
                            } catch (Throwable th3) {
                                th = th3;
                                inputStreamReader = inputStreamReader2;
                                o.closeQuietly(inputStreamReader);
                                throw th;
                            }
                        } catch (Throwable th4) {
                            th = th4;
                            inputStreamReader = null;
                        }
                    }
                } catch (Throwable th5) {
                    httpURLConnection.disconnect();
                    if (zzafmVar != null) {
                        zzafmVar.zzchc.zzor();
                    }
                    throw th5;
                }
            }
        } catch (IOException e) {
            String strValueOf2 = String.valueOf(e.getMessage());
            zzakb.zzdk(strValueOf2.length() != 0 ? "Error while connecting to ad server: ".concat(strValueOf2) : new String("Error while connecting to ad server: "));
            return new zzaej(2);
        }
    }

    public static zzafn zza(Context context, zzafm zzafmVar) {
        zzafn zzafnVar;
        synchronized (sLock) {
            if (zzchh == null) {
                if (context.getApplicationContext() != null) {
                    context = context.getApplicationContext();
                }
                zznk.initialize(context);
                zzchh = new zzafn(context, zzafmVar);
                if (context.getApplicationContext() != null) {
                    com.google.android.gms.ads.internal.zzbv.zzek().zzal(context);
                }
                zzajz.zzai(context);
            }
            zzafnVar = zzchh;
        }
        return zzafnVar;
    }

    private static void zza(String str, Map<String, List<String>> map, String str2, int i) {
        if (zzakb.isLoggable(2)) {
            zzakb.v(new StringBuilder(String.valueOf(str).length() + 39).append("Http Response: {\n  URL:\n    ").append(str).append("\n  Headers:").toString());
            if (map != null) {
                for (String str3 : map.keySet()) {
                    zzakb.v(new StringBuilder(String.valueOf(str3).length() + 5).append("    ").append(str3).append(":").toString());
                    Iterator<String> it = map.get(str3).iterator();
                    while (it.hasNext()) {
                        String strValueOf = String.valueOf(it.next());
                        zzakb.v(strValueOf.length() != 0 ? "      ".concat(strValueOf) : new String("      "));
                    }
                }
            }
            zzakb.v("  Body:");
            if (str2 != null) {
                for (int i2 = 0; i2 < Math.min(str2.length(), 100000); i2 += Constants.APP_INDEXING_SCHEDULE_INTERVAL_MS) {
                    zzakb.v(str2.substring(i2, Math.min(str2.length(), i2 + Constants.APP_INDEXING_SCHEDULE_INTERVAL_MS)));
                }
            } else {
                zzakb.v("    null");
            }
            zzakb.v(new StringBuilder(34).append("  Response Code:\n    ").append(i).append("\n}").toString());
        }
    }

    @Override // com.google.android.gms.internal.ads.zzaen
    public final void zza(zzaef zzaefVar, zzaeq zzaeqVar) {
        com.google.android.gms.ads.internal.zzbv.zzeo().zzd(this.mContext, zzaefVar.zzacr);
        zzanz<?> zzanzVarZzb = zzaki.zzb(new zzafp(this, zzaefVar, zzaeqVar));
        com.google.android.gms.ads.internal.zzbv.zzez().zzsa();
        com.google.android.gms.ads.internal.zzbv.zzez().getHandler().postDelayed(new zzafq(this, zzanzVarZzb), 60000L);
    }

    @Override // com.google.android.gms.internal.ads.zzaen
    public final void zza(zzaey zzaeyVar, zzaet zzaetVar) {
        zzakb.v("Nonagon code path entered in octagon");
        throw new IllegalArgumentException();
    }

    @Override // com.google.android.gms.internal.ads.zzaen
    public final zzaej zzb(zzaef zzaefVar) {
        return zza(this.mContext, this.zzchi, zzaefVar, this.zzchj);
    }

    @Override // com.google.android.gms.internal.ads.zzaen
    public final void zzb(zzaey zzaeyVar, zzaet zzaetVar) {
        zzakb.v("Nonagon code path entered in octagon");
        throw new IllegalArgumentException();
    }
}
