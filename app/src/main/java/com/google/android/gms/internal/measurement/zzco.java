package com.google.android.gms.internal.measurement;

import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Build;
import com.gameloft.glads.AndroidWebView;
import com.google.android.gms.a.n;
import com.google.android.gms.common.internal.bf;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.zip.GZIPOutputStream;

/* JADX INFO: loaded from: classes.dex */
final class zzco extends zzar {
    private static final byte[] zzaaw = "\n".getBytes();
    private final String zzaau;
    private final zzcz zzaav;

    zzco(zzat zzatVar) {
        super(zzatVar);
        this.zzaau = String.format("%s/%s (Linux; U; Android %s; %s; %s Build/%s)", "GoogleAnalytics", zzas.VERSION, Build.VERSION.RELEASE, zzdd.zza(Locale.getDefault()), Build.MODEL, Build.ID);
        this.zzaav = new zzcz(zzatVar.zzbt());
    }

    private final int zza(URL url) {
        bf.checkNotNull(url);
        zzb("GET request", url);
        HttpURLConnection httpURLConnectionZzb = null;
        try {
            httpURLConnectionZzb = zzb(url);
            httpURLConnectionZzb.connect();
            zza(httpURLConnectionZzb);
            int responseCode = httpURLConnectionZzb.getResponseCode();
            if (responseCode == 200) {
                zzby().zzbr();
            }
            zzb("GET status", Integer.valueOf(responseCode));
        } catch (IOException e) {
            zzd("Network GET connection error", e);
            return 0;
        } finally {
            if (httpURLConnectionZzb != null) {
                httpURLConnectionZzb.disconnect();
            }
        }
    }

    /* JADX WARN: Code duplicated, block: B:34:0x0096  */
    /* JADX WARN: Code duplicated, block: B:46:0x0091 A[EXC_TOP_SPLITTER, SYNTHETIC] */
    private final int zza(URL url, byte[] bArr) throws Throwable {
        OutputStream outputStream;
        HttpURLConnection httpURLConnectionZzb;
        bf.checkNotNull(url);
        bf.checkNotNull(bArr);
        zzb("POST bytes, url", Integer.valueOf(bArr.length), url);
        if (zzcg()) {
            zza("Post payload\n", new String(bArr));
        }
        try {
            getContext().getPackageName();
            httpURLConnectionZzb = zzb(url);
            try {
                httpURLConnectionZzb.setDoOutput(true);
                httpURLConnectionZzb.setFixedLengthStreamingMode(bArr.length);
                httpURLConnectionZzb.connect();
                outputStream = httpURLConnectionZzb.getOutputStream();
                try {
                    try {
                        outputStream.write(bArr);
                        zza(httpURLConnectionZzb);
                        int responseCode = httpURLConnectionZzb.getResponseCode();
                        if (responseCode == 200) {
                            zzby().zzbr();
                        }
                        zzb("POST status", Integer.valueOf(responseCode));
                        if (outputStream != null) {
                            try {
                                outputStream.close();
                            } catch (IOException e) {
                                zze("Error closing http post connection output stream", e);
                            }
                        }
                        if (httpURLConnectionZzb == null) {
                            return responseCode;
                        }
                        httpURLConnectionZzb.disconnect();
                        return responseCode;
                    } catch (Throwable th) {
                        th = th;
                        if (outputStream != null) {
                            try {
                                outputStream.close();
                            } catch (IOException e2) {
                                zze("Error closing http post connection output stream", e2);
                            }
                        }
                        if (httpURLConnectionZzb != null) {
                            httpURLConnectionZzb.disconnect();
                        }
                        throw th;
                    }
                } catch (IOException e3) {
                    e = e3;
                    zzd("Network POST connection error", e);
                    if (outputStream != null) {
                        try {
                            outputStream.close();
                        } catch (IOException e4) {
                            zze("Error closing http post connection output stream", e4);
                        }
                    }
                    if (httpURLConnectionZzb != null) {
                        httpURLConnectionZzb.disconnect();
                    }
                    return 0;
                }
            } catch (IOException e5) {
                e = e5;
                outputStream = null;
            } catch (Throwable th2) {
                th = th2;
                outputStream = null;
                if (outputStream != null) {
                    outputStream.close();
                }
                if (httpURLConnectionZzb != null) {
                    httpURLConnectionZzb.disconnect();
                }
                throw th;
            }
        } catch (IOException e6) {
            e = e6;
            outputStream = null;
            httpURLConnectionZzb = null;
        } catch (Throwable th3) {
            th = th3;
            outputStream = null;
            httpURLConnectionZzb = null;
        }
    }

    private static void zza(StringBuilder sb, String str, String str2) {
        if (sb.length() != 0) {
            sb.append('&');
        }
        sb.append(URLEncoder.encode(str, AndroidWebView.UTF_8));
        sb.append('=');
        sb.append(URLEncoder.encode(str2, AndroidWebView.UTF_8));
    }

    private final void zza(HttpURLConnection httpURLConnection) {
        InputStream inputStream = null;
        try {
            inputStream = httpURLConnection.getInputStream();
            do {
            } while (inputStream.read(new byte[1024]) > 0);
        } finally {
            if (inputStream != null) {
                try {
                    inputStream.close();
                } catch (IOException e) {
                    zze("Error closing http connection input stream", e);
                }
            }
        }
    }

    /* JADX WARN: Code duplicated, block: B:40:0x00e3  */
    /* JADX WARN: Code duplicated, block: B:52:0x00de A[EXC_TOP_SPLITTER, SYNTHETIC] */
    private final int zzb(URL url, byte[] bArr) throws Throwable {
        OutputStream outputStream;
        HttpURLConnection httpURLConnectionZzb;
        bf.checkNotNull(url);
        bf.checkNotNull(bArr);
        try {
            getContext().getPackageName();
            ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
            GZIPOutputStream gZIPOutputStream = new GZIPOutputStream(byteArrayOutputStream);
            gZIPOutputStream.write(bArr);
            gZIPOutputStream.close();
            byteArrayOutputStream.close();
            byte[] byteArray = byteArrayOutputStream.toByteArray();
            zza("POST compressed size, ratio %, url", Integer.valueOf(byteArray.length), Long.valueOf((100 * ((long) byteArray.length)) / ((long) bArr.length)), url);
            if (byteArray.length > bArr.length) {
                zzc("Compressed payload is larger then uncompressed. compressed, uncompressed", Integer.valueOf(byteArray.length), Integer.valueOf(bArr.length));
            }
            if (zzcg()) {
                String strValueOf = String.valueOf(new String(bArr));
                zza("Post payload", strValueOf.length() != 0 ? "\n".concat(strValueOf) : new String("\n"));
            }
            httpURLConnectionZzb = zzb(url);
            try {
                httpURLConnectionZzb.setDoOutput(true);
                httpURLConnectionZzb.addRequestProperty("Content-Encoding", "gzip");
                httpURLConnectionZzb.setFixedLengthStreamingMode(byteArray.length);
                httpURLConnectionZzb.connect();
                outputStream = httpURLConnectionZzb.getOutputStream();
                try {
                    try {
                        outputStream.write(byteArray);
                        outputStream.close();
                        zza(httpURLConnectionZzb);
                        int responseCode = httpURLConnectionZzb.getResponseCode();
                        if (responseCode == 200) {
                            zzby().zzbr();
                        }
                        zzb("POST status", Integer.valueOf(responseCode));
                        if (httpURLConnectionZzb == null) {
                            return responseCode;
                        }
                        httpURLConnectionZzb.disconnect();
                        return responseCode;
                    } catch (Throwable th) {
                        th = th;
                        if (outputStream != null) {
                            try {
                                outputStream.close();
                            } catch (IOException e) {
                                zze("Error closing http compressed post connection output stream", e);
                            }
                        }
                        if (httpURLConnectionZzb != null) {
                            httpURLConnectionZzb.disconnect();
                        }
                        throw th;
                    }
                } catch (IOException e2) {
                    e = e2;
                    zzd("Network compressed POST connection error", e);
                    if (outputStream != null) {
                        try {
                            outputStream.close();
                        } catch (IOException e3) {
                            zze("Error closing http compressed post connection output stream", e3);
                        }
                    }
                    if (httpURLConnectionZzb != null) {
                        httpURLConnectionZzb.disconnect();
                    }
                    return 0;
                }
            } catch (IOException e4) {
                e = e4;
                outputStream = null;
            } catch (Throwable th2) {
                th = th2;
                outputStream = null;
                if (outputStream != null) {
                    outputStream.close();
                }
                if (httpURLConnectionZzb != null) {
                    httpURLConnectionZzb.disconnect();
                }
                throw th;
            }
        } catch (IOException e5) {
            e = e5;
            outputStream = null;
            httpURLConnectionZzb = null;
        } catch (Throwable th3) {
            th = th3;
            outputStream = null;
            httpURLConnectionZzb = null;
        }
    }

    private final HttpURLConnection zzb(URL url) throws IOException {
        URLConnection uRLConnectionOpenConnection = url.openConnection();
        if (!(uRLConnectionOpenConnection instanceof HttpURLConnection)) {
            throw new IOException("Failed to obtain http connection");
        }
        HttpURLConnection httpURLConnection = (HttpURLConnection) uRLConnectionOpenConnection;
        httpURLConnection.setDefaultUseCaches(false);
        httpURLConnection.setConnectTimeout(zzcc.zzzj.get().intValue());
        httpURLConnection.setReadTimeout(zzcc.zzzk.get().intValue());
        httpURLConnection.setInstanceFollowRedirects(false);
        httpURLConnection.setRequestProperty("User-Agent", this.zzaau);
        httpURLConnection.setDoInput(true);
        return httpURLConnection;
    }

    private final URL zzb(zzch zzchVar, String str) {
        String string;
        if (zzchVar.zzeo()) {
            String strZzdz = zzbu.zzdz();
            String strZzeb = zzbu.zzeb();
            string = new StringBuilder(String.valueOf(strZzdz).length() + 1 + String.valueOf(strZzeb).length() + String.valueOf(str).length()).append(strZzdz).append(strZzeb).append("?").append(str).toString();
        } else {
            String strZzea = zzbu.zzea();
            String strZzeb2 = zzbu.zzeb();
            string = new StringBuilder(String.valueOf(strZzea).length() + 1 + String.valueOf(strZzeb2).length() + String.valueOf(str).length()).append(strZzea).append(strZzeb2).append("?").append(str).toString();
        }
        try {
            return new URL(string);
        } catch (MalformedURLException e) {
            zze("Error trying to parse the hardcoded host url", e);
            return null;
        }
    }

    private final URL zzd(zzch zzchVar) {
        String strConcat;
        if (zzchVar.zzeo()) {
            String strValueOf = String.valueOf(zzbu.zzdz());
            String strValueOf2 = String.valueOf(zzbu.zzeb());
            strConcat = strValueOf2.length() != 0 ? strValueOf.concat(strValueOf2) : new String(strValueOf);
        } else {
            String strValueOf3 = String.valueOf(zzbu.zzea());
            String strValueOf4 = String.valueOf(zzbu.zzeb());
            strConcat = strValueOf4.length() != 0 ? strValueOf3.concat(strValueOf4) : new String(strValueOf3);
        }
        try {
            return new URL(strConcat);
        } catch (MalformedURLException e) {
            zze("Error trying to parse the hardcoded host url", e);
            return null;
        }
    }

    private final URL zzex() {
        String strValueOf = String.valueOf(zzbu.zzdz());
        String strValueOf2 = String.valueOf(zzcc.zzyy.get());
        try {
            return new URL(strValueOf2.length() != 0 ? strValueOf.concat(strValueOf2) : new String(strValueOf));
        } catch (MalformedURLException e) {
            zze("Error trying to parse the hardcoded host url", e);
            return null;
        }
    }

    final String zza(zzch zzchVar, boolean z) {
        bf.checkNotNull(zzchVar);
        StringBuilder sb = new StringBuilder();
        try {
            for (Map.Entry<String, String> entry : zzchVar.zzcs().entrySet()) {
                String key = entry.getKey();
                if (!"ht".equals(key) && !"qt".equals(key) && !"AppUID".equals(key) && !"z".equals(key) && !"_gmsv".equals(key)) {
                    zza(sb, key, entry.getValue());
                }
            }
            zza(sb, "ht", String.valueOf(zzchVar.zzen()));
            zza(sb, "qt", String.valueOf(zzbt().currentTimeMillis() - zzchVar.zzen()));
            if (z) {
                long jZzep = zzchVar.zzep();
                zza(sb, "z", jZzep != 0 ? String.valueOf(jZzep) : String.valueOf(zzchVar.zzem()));
            }
            return sb.toString();
        } catch (UnsupportedEncodingException e) {
            zze("Failed to encode name or value", e);
            return null;
        }
    }

    @Override // com.google.android.gms.internal.measurement.zzar
    protected final void zzac() {
        zza("Network initialized. User agent", this.zzaau);
    }

    /* JADX WARN: Code duplicated, block: B:14:0x0066  */
    /* JADX WARN: Code duplicated, block: B:16:0x0072 A[LOOP:0: B:12:0x0060->B:16:0x0072, LOOP_END] */
    /* JADX WARN: Code duplicated, block: B:24:0x00a3  */
    /* JADX WARN: Code duplicated, block: B:27:0x00ab  */
    /* JADX WARN: Code duplicated, block: B:29:0x00ad  */
    /* JADX WARN: Code duplicated, block: B:31:0x00b3  */
    /* JADX WARN: Code duplicated, block: B:33:0x00be A[DONT_INVERT] */
    /* JADX WARN: Code duplicated, block: B:34:0x00c0  */
    /* JADX WARN: Code duplicated, block: B:36:0x00ca  */
    /* JADX WARN: Code duplicated, block: B:37:0x00da  */
    /* JADX WARN: Code duplicated, block: B:38:0x00e3  */
    /* JADX WARN: Code duplicated, block: B:40:0x00ff  */
    /* JADX WARN: Code duplicated, block: B:41:0x010b  */
    /* JADX WARN: Code duplicated, block: B:44:0x011e  */
    /* JADX WARN: Code duplicated, block: B:46:0x012d  */
    /* JADX WARN: Code duplicated, block: B:49:0x0134  */
    /* JADX WARN: Code duplicated, block: B:51:0x0141  */
    /* JADX WARN: Code duplicated, block: B:54:0x0159  */
    /* JADX WARN: Code duplicated, block: B:55:0x015b  */
    /* JADX WARN: Code duplicated, block: B:57:0x016d  */
    /* JADX WARN: Code duplicated, block: B:59:0x0173  */
    /* JADX WARN: Code duplicated, block: B:61:0x017b  */
    /* JADX WARN: Code duplicated, block: B:63:0x0181  */
    /* JADX WARN: Code duplicated, block: B:64:0x0183  */
    /* JADX WARN: Code duplicated, block: B:65:0x0185  */
    /* JADX WARN: Code duplicated, block: B:67:0x018b  */
    /* JADX WARN: Code duplicated, block: B:68:0x0197  */
    /* JADX WARN: Code duplicated, block: B:70:0x01aa  */
    /* JADX WARN: Code duplicated, block: B:71:0x01b6  */
    /* JADX WARN: Code duplicated, block: B:73:0x01bc  */
    /* JADX WARN: Code duplicated, block: B:74:0x01c3  */
    /* JADX WARN: Code duplicated, block: B:76:0x01c9  */
    /* JADX WARN: Code duplicated, block: B:78:0x00a5 A[EDGE_INSN: B:78:0x00a5->B:25:0x00a5 BREAK  A[LOOP:0: B:12:0x0060->B:16:0x0072], SYNTHETIC] */
    /* JADX WARN: Code duplicated, block: B:80:0x0156 A[EDGE_INSN: B:80:0x0156->B:53:0x0156 BREAK  A[LOOP:1: B:42:0x0118->B:82:?], SYNTHETIC] */
    /* JADX WARN: Code duplicated, block: B:81:0x0156 A[EDGE_INSN: B:81:0x0156->B:53:0x0156 BREAK  A[LOOP:1: B:42:0x0118->B:82:?], SYNTHETIC] */
    /* JADX WARN: Code duplicated, block: B:82:? A[LOOP:1: B:42:0x0118->B:82:?, LOOP_END, SYNTHETIC] */
    /* JADX WARN: Code duplicated, block: B:9:0x0037  */
    public final List<Long> zzb(List<zzch> list) throws Throwable {
        boolean z;
        boolean z2;
        ArrayList arrayList;
        boolean z3;
        String strZza;
        String strZza2;
        byte[] bytes;
        URL urlZzd;
        boolean z4;
        URL urlZzb;
        zzcp zzcpVar;
        ArrayList arrayList2;
        URL urlZzex;
        int iZza;
        n.zzab();
        zzch();
        bf.checkNotNull(list);
        if (!zzbv().zzec().isEmpty() && this.zzaav.zzj(((long) zzcc.zzzh.get().intValue()) * 1000)) {
            z = zzbk.zzz(zzcc.zzza.get()) != zzbk.NONE;
            z2 = zzbq.zzaa(zzcc.zzzb.get()) == zzbq.GZIP;
            if (z) {
                bf.ca(list.isEmpty() ? false : true);
                zza("Uploading batched hits. compression, count", Boolean.valueOf(z2), Integer.valueOf(list.size()));
                zzcpVar = new zzcp(this);
                arrayList2 = new ArrayList();
                for (zzch zzchVar : list) {
                    if (zzcpVar.zze(zzchVar)) {
                        break;
                    }
                    arrayList2.add(Long.valueOf(zzchVar.zzem()));
                }
                if (zzcpVar.zzez() == 0) {
                    return arrayList2;
                }
                urlZzex = zzex();
                if (urlZzex == null) {
                    zzu("Failed to build batching endpoint url");
                } else {
                    if (z2) {
                        iZza = zzb(urlZzex, zzcpVar.getPayload());
                    } else {
                        iZza = zza(urlZzex, zzcpVar.getPayload());
                    }
                    if (200 == iZza) {
                        zza("Batched upload completed. Hits batched", Integer.valueOf(zzcpVar.zzez()));
                        return arrayList2;
                    }
                    zza("Network error uploading hits. status code", Integer.valueOf(iZza));
                    if (zzbv().zzec().contains(Integer.valueOf(iZza))) {
                        zzt("Server instructed the client to stop batching");
                        this.zzaav.start();
                    }
                }
                return Collections.emptyList();
            }
            arrayList = new ArrayList(list.size());
            for (zzch zzchVar2 : list) {
                bf.checkNotNull(zzchVar2);
                if (zzchVar2.zzeo()) {
                    z3 = false;
                } else {
                    z3 = true;
                }
                strZza = zza(zzchVar2, z3);
                if (strZza == null) {
                    zzbu().zza(zzchVar2, "Error formatting hit for upload");
                    z4 = true;
                } else if (strZza.length() <= zzcc.zzyz.get().intValue()) {
                    urlZzb = zzb(zzchVar2, strZza);
                    if (urlZzb == null) {
                        zzu("Failed to build collect GET endpoint url");
                        z4 = false;
                    } else if (zza(urlZzb) == 200) {
                        z4 = true;
                    } else {
                        z4 = false;
                    }
                } else {
                    strZza2 = zza(zzchVar2, false);
                    if (strZza2 == null) {
                        zzbu().zza(zzchVar2, "Error formatting hit for POST upload");
                        z4 = true;
                    } else {
                        bytes = strZza2.getBytes();
                        if (bytes.length > zzcc.zzze.get().intValue()) {
                            zzbu().zza(zzchVar2, "Hit payload exceeds size limit");
                            z4 = true;
                        } else {
                            urlZzd = zzd(zzchVar2);
                            if (urlZzd == null) {
                                zzu("Failed to build collect POST endpoint url");
                            } else if (zza(urlZzd, bytes) == 200) {
                                z4 = true;
                            }
                            z4 = false;
                        }
                    }
                }
                if (z4) {
                    break;
                }
                arrayList.add(Long.valueOf(zzchVar2.zzem()));
                if (arrayList.size() >= zzbu.zzdx()) {
                    break;
                }
            }
            return arrayList;
        }
        z = false;
        if (z) {
            bf.ca(list.isEmpty() ? false : true);
            zza("Uploading batched hits. compression, count", Boolean.valueOf(z2), Integer.valueOf(list.size()));
            zzcpVar = new zzcp(this);
            arrayList2 = new ArrayList();
            while (r3.hasNext()) {
                if (zzcpVar.zze(zzchVar)) {
                    break;
                    break;
                }
                arrayList2.add(Long.valueOf(zzchVar.zzem()));
            }
            if (zzcpVar.zzez() == 0) {
                return arrayList2;
            }
            urlZzex = zzex();
            if (urlZzex == null) {
                zzu("Failed to build batching endpoint url");
            } else {
                if (z2) {
                    iZza = zzb(urlZzex, zzcpVar.getPayload());
                } else {
                    iZza = zza(urlZzex, zzcpVar.getPayload());
                }
                if (200 == iZza) {
                    zza("Batched upload completed. Hits batched", Integer.valueOf(zzcpVar.zzez()));
                    return arrayList2;
                }
                zza("Network error uploading hits. status code", Integer.valueOf(iZza));
                if (zzbv().zzec().contains(Integer.valueOf(iZza))) {
                    zzt("Server instructed the client to stop batching");
                    this.zzaav.start();
                }
            }
            return Collections.emptyList();
        }
        arrayList = new ArrayList(list.size());
        while (r5.hasNext()) {
            bf.checkNotNull(zzchVar2);
            if (zzchVar2.zzeo()) {
                z3 = true;
            } else {
                z3 = false;
            }
            strZza = zza(zzchVar2, z3);
            if (strZza == null) {
                zzbu().zza(zzchVar2, "Error formatting hit for upload");
                z4 = true;
            } else if (strZza.length() <= zzcc.zzyz.get().intValue()) {
                urlZzb = zzb(zzchVar2, strZza);
                if (urlZzb == null) {
                    zzu("Failed to build collect GET endpoint url");
                    z4 = false;
                } else if (zza(urlZzb) == 200) {
                    z4 = true;
                } else {
                    z4 = false;
                }
            } else {
                strZza2 = zza(zzchVar2, false);
                if (strZza2 == null) {
                    zzbu().zza(zzchVar2, "Error formatting hit for POST upload");
                    z4 = true;
                } else {
                    bytes = strZza2.getBytes();
                    if (bytes.length > zzcc.zzze.get().intValue()) {
                        zzbu().zza(zzchVar2, "Hit payload exceeds size limit");
                        z4 = true;
                    } else {
                        urlZzd = zzd(zzchVar2);
                        if (urlZzd == null) {
                            zzu("Failed to build collect POST endpoint url");
                        } else if (zza(urlZzd, bytes) == 200) {
                            z4 = true;
                        }
                        z4 = false;
                    }
                }
            }
            if (z4) {
                break;
                break;
            }
            arrayList.add(Long.valueOf(zzchVar2.zzem()));
            if (arrayList.size() >= zzbu.zzdx()) {
                break;
                break;
            }
        }
        return arrayList;
    }

    public final boolean zzew() {
        NetworkInfo activeNetworkInfo;
        n.zzab();
        zzch();
        try {
            activeNetworkInfo = ((ConnectivityManager) getContext().getSystemService("connectivity")).getActiveNetworkInfo();
        } catch (SecurityException e) {
            activeNetworkInfo = null;
        }
        if (activeNetworkInfo != null && activeNetworkInfo.isConnected()) {
            return true;
        }
        zzq("No network connectivity");
        return false;
    }
}
