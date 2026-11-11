package com.google.android.gms.internal.ads;

import android.os.SystemClock;
import android.text.TextUtils;
import com.gameloft.glads.AndroidWebView;
import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.DataInputStream;
import java.io.EOFException;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

/* JADX INFO: loaded from: classes.dex */
public final class zzam implements zzb {
    private final Map<String, zzan> zzbw;
    private long zzbx;
    private final File zzby;
    private final int zzbz;

    public zzam(File file) {
        this(file, 5242880);
    }

    private zzam(File file, int i) {
        this.zzbw = new LinkedHashMap(16, 0.75f, true);
        this.zzbx = 0L;
        this.zzby = file;
        this.zzbz = 5242880;
    }

    private final synchronized void remove(String str) {
        boolean zDelete = zze(str).delete();
        removeEntry(str);
        if (!zDelete) {
            zzaf.d("Could not delete cache entry for key=%s, filename=%s", str, zzd(str));
        }
    }

    private final void removeEntry(String str) {
        zzan zzanVarRemove = this.zzbw.remove(str);
        if (zzanVarRemove != null) {
            this.zzbx -= zzanVarRemove.zzca;
        }
    }

    private static int zza(InputStream inputStream) throws IOException {
        int i = inputStream.read();
        if (i == -1) {
            throw new EOFException();
        }
        return i;
    }

    private static InputStream zza(File file) {
        return new FileInputStream(file);
    }

    static String zza(zzao zzaoVar) {
        return new String(zza(zzaoVar, zzc(zzaoVar)), AndroidWebView.UTF_8);
    }

    static void zza(OutputStream outputStream, int i) {
        outputStream.write(i & 255);
        outputStream.write((i >> 8) & 255);
        outputStream.write((i >> 16) & 255);
        outputStream.write(i >>> 24);
    }

    static void zza(OutputStream outputStream, long j) {
        outputStream.write((byte) j);
        outputStream.write((byte) (j >>> 8));
        outputStream.write((byte) (j >>> 16));
        outputStream.write((byte) (j >>> 24));
        outputStream.write((byte) (j >>> 32));
        outputStream.write((byte) (j >>> 40));
        outputStream.write((byte) (j >>> 48));
        outputStream.write((byte) (j >>> 56));
    }

    static void zza(OutputStream outputStream, String str) {
        byte[] bytes = str.getBytes(AndroidWebView.UTF_8);
        zza(outputStream, bytes.length);
        outputStream.write(bytes, 0, bytes.length);
    }

    private final void zza(String str, zzan zzanVar) {
        if (this.zzbw.containsKey(str)) {
            this.zzbx = (zzanVar.zzca - this.zzbw.get(str).zzca) + this.zzbx;
        } else {
            this.zzbx += zzanVar.zzca;
        }
        this.zzbw.put(str, zzanVar);
    }

    private static byte[] zza(zzao zzaoVar, long j) throws IOException {
        long jZzo = zzaoVar.zzo();
        if (j < 0 || j > jZzo || ((int) j) != j) {
            throw new IOException(new StringBuilder(73).append("streamToBytes length=").append(j).append(", maxLength=").append(jZzo).toString());
        }
        byte[] bArr = new byte[(int) j];
        new DataInputStream(zzaoVar).readFully(bArr);
        return bArr;
    }

    static int zzb(InputStream inputStream) {
        return zza(inputStream) | 0 | (zza(inputStream) << 8) | (zza(inputStream) << 16) | (zza(inputStream) << 24);
    }

    static List<zzl> zzb(zzao zzaoVar) {
        int iZzb = zzb((InputStream) zzaoVar);
        List<zzl> listEmptyList = iZzb == 0 ? Collections.emptyList() : new ArrayList<>(iZzb);
        for (int i = 0; i < iZzb; i++) {
            listEmptyList.add(new zzl(zza(zzaoVar).intern(), zza(zzaoVar).intern()));
        }
        return listEmptyList;
    }

    static long zzc(InputStream inputStream) {
        return 0 | (((long) zza(inputStream)) & 255) | ((((long) zza(inputStream)) & 255) << 8) | ((((long) zza(inputStream)) & 255) << 16) | ((((long) zza(inputStream)) & 255) << 24) | ((((long) zza(inputStream)) & 255) << 32) | ((((long) zza(inputStream)) & 255) << 40) | ((((long) zza(inputStream)) & 255) << 48) | ((((long) zza(inputStream)) & 255) << 56);
    }

    private static String zzd(String str) {
        int length = str.length() / 2;
        String strValueOf = String.valueOf(String.valueOf(str.substring(0, length).hashCode()));
        String strValueOf2 = String.valueOf(String.valueOf(str.substring(length).hashCode()));
        return strValueOf2.length() != 0 ? strValueOf.concat(strValueOf2) : new String(strValueOf);
    }

    private final File zze(String str) {
        return new File(this.zzby, zzd(str));
    }

    @Override // com.google.android.gms.internal.ads.zzb
    public final synchronized zzc zza(String str) {
        zzc zzcVar;
        zzan zzanVar = this.zzbw.get(str);
        if (zzanVar == null) {
            zzcVar = null;
        } else {
            File fileZze = zze(str);
            try {
                zzao zzaoVar = new zzao(new BufferedInputStream(zza(fileZze)), fileZze.length());
                try {
                    zzan zzanVarZzc = zzan.zzc(zzaoVar);
                    if (TextUtils.equals(str, zzanVarZzc.zzcb)) {
                        byte[] bArrZza = zza(zzaoVar, zzaoVar.zzo());
                        zzc zzcVar2 = new zzc();
                        zzcVar2.data = bArrZza;
                        zzcVar2.zza = zzanVar.zza;
                        zzcVar2.zzb = zzanVar.zzb;
                        zzcVar2.zzc = zzanVar.zzc;
                        zzcVar2.zzd = zzanVar.zzd;
                        zzcVar2.zze = zzanVar.zze;
                        List<zzl> list = zzanVar.zzg;
                        TreeMap treeMap = new TreeMap(String.CASE_INSENSITIVE_ORDER);
                        for (zzl zzlVar : list) {
                            treeMap.put(zzlVar.getName(), zzlVar.getValue());
                        }
                        zzcVar2.zzf = treeMap;
                        zzcVar2.zzg = Collections.unmodifiableList(zzanVar.zzg);
                        zzaoVar.close();
                        zzcVar = zzcVar2;
                    } else {
                        zzaf.d("%s: key=%s, found=%s", fileZze.getAbsolutePath(), str, zzanVarZzc.zzcb);
                        removeEntry(str);
                        zzaoVar.close();
                        zzcVar = null;
                    }
                } catch (Throwable th) {
                    zzaoVar.close();
                    throw th;
                }
            } catch (IOException e) {
                zzaf.d("%s: %s", fileZze.getAbsolutePath(), e.toString());
                remove(str);
                zzcVar = null;
            }
        }
        return zzcVar;
    }

    @Override // com.google.android.gms.internal.ads.zzb
    public final synchronized void zza() {
        if (this.zzby.exists()) {
            File[] fileArrListFiles = this.zzby.listFiles();
            if (fileArrListFiles != null) {
                for (File file : fileArrListFiles) {
                    try {
                        long length = file.length();
                        zzao zzaoVar = new zzao(new BufferedInputStream(zza(file)), length);
                        try {
                            zzan zzanVarZzc = zzan.zzc(zzaoVar);
                            zzanVarZzc.zzca = length;
                            zza(zzanVarZzc.zzcb, zzanVarZzc);
                            zzaoVar.close();
                        } catch (Throwable th) {
                            zzaoVar.close();
                            throw th;
                        }
                    } catch (IOException e) {
                        file.delete();
                    }
                }
            }
        } else if (!this.zzby.mkdirs()) {
            zzaf.e("Unable to create cache dir %s", this.zzby.getAbsolutePath());
        }
    }

    @Override // com.google.android.gms.internal.ads.zzb
    public final synchronized void zza(String str, zzc zzcVar) {
        int i;
        int i2 = 0;
        synchronized (this) {
            int length = zzcVar.data.length;
            if (this.zzbx + ((long) length) >= this.zzbz) {
                if (zzaf.DEBUG) {
                    zzaf.v("Pruning old cache entries.", new Object[0]);
                }
                long j = this.zzbx;
                long jElapsedRealtime = SystemClock.elapsedRealtime();
                Iterator<Map.Entry<String, zzan>> it = this.zzbw.entrySet().iterator();
                while (true) {
                    if (!it.hasNext()) {
                        i = i2;
                        break;
                    }
                    zzan value = it.next().getValue();
                    if (zze(value.zzcb).delete()) {
                        this.zzbx -= value.zzca;
                    } else {
                        zzaf.d("Could not delete cache entry for key=%s, filename=%s", value.zzcb, zzd(value.zzcb));
                    }
                    it.remove();
                    i = i2 + 1;
                    if (this.zzbx + ((long) length) < this.zzbz * 0.9f) {
                        break;
                    } else {
                        i2 = i;
                    }
                }
                if (zzaf.DEBUG) {
                    zzaf.v("pruned %d files, %d bytes, %d ms", Integer.valueOf(i), Long.valueOf(this.zzbx - j), Long.valueOf(SystemClock.elapsedRealtime() - jElapsedRealtime));
                }
            }
            File fileZze = zze(str);
            try {
                BufferedOutputStream bufferedOutputStream = new BufferedOutputStream(new FileOutputStream(fileZze));
                zzan zzanVar = new zzan(str, zzcVar);
                if (!zzanVar.zza(bufferedOutputStream)) {
                    bufferedOutputStream.close();
                    zzaf.d("Failed to write header for %s", fileZze.getAbsolutePath());
                    throw new IOException();
                }
                bufferedOutputStream.write(zzcVar.data);
                bufferedOutputStream.close();
                zza(str, zzanVar);
            } catch (IOException e) {
                if (!fileZze.delete()) {
                    zzaf.d("Could not clean up file %s", fileZze.getAbsolutePath());
                }
            }
        }
    }
}
