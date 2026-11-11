package com.google.android.gms.internal.ads;

import android.os.SystemClock;
import com.gameloft.glads.GLURLConnection;
import java.io.IOException;
import java.io.InputStream;
import java.net.MalformedURLException;
import java.net.SocketTimeoutException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.TreeSet;

/* JADX INFO: loaded from: classes.dex */
public class zzaj implements zzm {
    private static final boolean DEBUG = zzaf.DEBUG;

    @Deprecated
    private final zzar zzbo;
    private final zzai zzbp;
    private final zzak zzbq;

    public zzaj(zzai zzaiVar) {
        this(zzaiVar, new zzak(4096));
    }

    private zzaj(zzai zzaiVar, zzak zzakVar) {
        this.zzbp = zzaiVar;
        this.zzbo = zzaiVar;
        this.zzbq = zzakVar;
    }

    @Deprecated
    public zzaj(zzar zzarVar) {
        this(zzarVar, new zzak(4096));
    }

    @Deprecated
    private zzaj(zzar zzarVar, zzak zzakVar) {
        this.zzbo = zzarVar;
        this.zzbp = new zzah(zzarVar);
        this.zzbq = zzakVar;
    }

    private static void zza(String str, zzr<?> zzrVar, zzae zzaeVar) throws zzae {
        zzab zzabVarZzj = zzrVar.zzj();
        int iZzi = zzrVar.zzi();
        try {
            zzabVarZzj.zza(zzaeVar);
            zzrVar.zzb(String.format("%s-retry [timeout=%s]", str, Integer.valueOf(iZzi)));
        } catch (zzae e) {
            zzrVar.zzb(String.format("%s-timeout-giveup [timeout=%s]", str, Integer.valueOf(iZzi)));
            throw e;
        }
    }

    private final byte[] zza(InputStream inputStream, int i) throws IOException {
        zzau zzauVar = new zzau(this.zzbq, i);
        try {
            if (inputStream == null) {
                throw new zzac();
            }
            byte[] bArrZzb = this.zzbq.zzb(1024);
            while (true) {
                int i2 = inputStream.read(bArrZzb);
                if (i2 == -1) {
                    break;
                }
                zzauVar.write(bArrZzb, 0, i2);
            }
            byte[] byteArray = zzauVar.toByteArray();
            if (inputStream != null) {
                try {
                    inputStream.close();
                } catch (IOException e) {
                    zzaf.v("Error occurred when closing InputStream", new Object[0]);
                }
            }
            this.zzbq.zza(bArrZzb);
            zzauVar.close();
            return byteArray;
        } catch (Throwable th) {
            if (inputStream != null) {
                try {
                    inputStream.close();
                } catch (IOException e2) {
                    zzaf.v("Error occurred when closing InputStream", new Object[0]);
                }
            }
            this.zzbq.zza(null);
            zzauVar.close();
            throw th;
        }
    }

    @Override // com.google.android.gms.internal.ads.zzm
    public zzp zzc(zzr<?> zzrVar) throws zzae {
        byte[] bArrZza;
        Map<String, String> map;
        long jElapsedRealtime = SystemClock.elapsedRealtime();
        while (true) {
            zzaq zzaqVar = null;
            List<zzl> listEmptyList = Collections.emptyList();
            try {
                try {
                    zzc zzcVarZzf = zzrVar.zzf();
                    if (zzcVarZzf == null) {
                        map = Collections.emptyMap();
                    } else {
                        map = new HashMap<>();
                        if (zzcVarZzf.zza != null) {
                            map.put("If-None-Match", zzcVarZzf.zza);
                        }
                        if (zzcVarZzf.zzc > 0) {
                            map.put("If-Modified-Since", zzap.zzb(zzcVarZzf.zzc));
                        }
                    }
                    zzaq zzaqVarZza = this.zzbp.zza(zzrVar, map);
                    try {
                        int statusCode = zzaqVarZza.getStatusCode();
                        listEmptyList = zzaqVarZza.zzq();
                        if (statusCode == 304) {
                            zzc zzcVarZzf2 = zzrVar.zzf();
                            if (zzcVarZzf2 == null) {
                                return new zzp(GLURLConnection.HTTP_NOT_MODIFIED, (byte[]) null, true, SystemClock.elapsedRealtime() - jElapsedRealtime, listEmptyList);
                            }
                            TreeSet treeSet = new TreeSet(String.CASE_INSENSITIVE_ORDER);
                            if (!listEmptyList.isEmpty()) {
                                Iterator<zzl> it = listEmptyList.iterator();
                                while (it.hasNext()) {
                                    treeSet.add(it.next().getName());
                                }
                            }
                            ArrayList arrayList = new ArrayList(listEmptyList);
                            if (zzcVarZzf2.zzg != null) {
                                if (!zzcVarZzf2.zzg.isEmpty()) {
                                    for (zzl zzlVar : zzcVarZzf2.zzg) {
                                        if (!treeSet.contains(zzlVar.getName())) {
                                            arrayList.add(zzlVar);
                                        }
                                    }
                                }
                            } else if (!zzcVarZzf2.zzf.isEmpty()) {
                                for (Map.Entry<String, String> entry : zzcVarZzf2.zzf.entrySet()) {
                                    if (!treeSet.contains(entry.getKey())) {
                                        arrayList.add(new zzl(entry.getKey(), entry.getValue()));
                                    }
                                }
                            }
                            return new zzp(GLURLConnection.HTTP_NOT_MODIFIED, zzcVarZzf2.data, true, SystemClock.elapsedRealtime() - jElapsedRealtime, (List<zzl>) arrayList);
                        }
                        InputStream content = zzaqVarZza.getContent();
                        bArrZza = content != null ? zza(content, zzaqVarZza.getContentLength()) : new byte[0];
                        try {
                            long jElapsedRealtime2 = SystemClock.elapsedRealtime() - jElapsedRealtime;
                            if (DEBUG || jElapsedRealtime2 > 3000) {
                                Object[] objArr = new Object[5];
                                objArr[0] = zzrVar;
                                objArr[1] = Long.valueOf(jElapsedRealtime2);
                                objArr[2] = bArrZza != null ? Integer.valueOf(bArrZza.length) : "null";
                                objArr[3] = Integer.valueOf(statusCode);
                                objArr[4] = Integer.valueOf(zzrVar.zzj().zzd());
                                zzaf.d("HTTP response for request=<%s> [lifetime=%d], [size=%s], [rc=%d], [retryCount=%s]", objArr);
                            }
                            if (statusCode < 200 || statusCode > 299) {
                                throw new IOException();
                            }
                            return new zzp(statusCode, bArrZza, false, SystemClock.elapsedRealtime() - jElapsedRealtime, listEmptyList);
                        } catch (IOException e) {
                            e = e;
                            zzaqVar = zzaqVarZza;
                            if (zzaqVar == null) {
                                throw new zzq(e);
                            }
                            int statusCode2 = zzaqVar.getStatusCode();
                            zzaf.e("Unexpected response code %d for %s", Integer.valueOf(statusCode2), zzrVar.getUrl());
                            if (bArrZza != null) {
                                zzp zzpVar = new zzp(statusCode2, bArrZza, false, SystemClock.elapsedRealtime() - jElapsedRealtime, listEmptyList);
                                if (statusCode2 != 401 && statusCode2 != 403) {
                                    if (statusCode2 >= 400 && statusCode2 <= 499) {
                                        throw new zzg(zzpVar);
                                    }
                                    if (statusCode2 < 500 || statusCode2 > 599) {
                                        throw new zzac(zzpVar);
                                    }
                                    throw new zzac(zzpVar);
                                }
                                zza("auth", zzrVar, new zza(zzpVar));
                            } else {
                                zza("network", zzrVar, new zzo());
                            }
                        }
                    } catch (IOException e2) {
                        e = e2;
                        bArrZza = null;
                        zzaqVar = zzaqVarZza;
                    }
                } catch (MalformedURLException e3) {
                    String strValueOf = String.valueOf(zzrVar.getUrl());
                    throw new RuntimeException(strValueOf.length() != 0 ? "Bad URL ".concat(strValueOf) : new String("Bad URL "), e3);
                } catch (SocketTimeoutException e4) {
                    zza("socket", zzrVar, new zzad());
                }
            } catch (IOException e5) {
                e = e5;
                bArrZza = null;
            }
        }
    }
}
