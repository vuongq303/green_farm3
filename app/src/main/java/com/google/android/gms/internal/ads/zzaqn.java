package com.google.android.gms.internal.ads;

import com.google.android.gms.common.d.e;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLConnection;
import java.nio.ByteBuffer;
import java.nio.channels.Channels;
import java.nio.channels.FileChannel;
import java.nio.channels.ReadableByteChannel;
import java.text.DecimalFormat;
import java.util.Collections;
import java.util.HashSet;
import java.util.Set;

/* JADX INFO: loaded from: classes.dex */
@zzadh
public final class zzaqn extends zzaqh {
    private static final Set<String> zzdbg = Collections.synchronizedSet(new HashSet());
    private static final DecimalFormat zzdbh = new DecimalFormat("#,###");
    private File zzdbi;
    private boolean zzdbj;

    public zzaqn(zzapw zzapwVar) {
        super(zzapwVar);
        File cacheDir = this.mContext.getCacheDir();
        if (cacheDir == null) {
            zzakb.zzdk("Context.getCacheDir() returned null");
            return;
        }
        this.zzdbi = new File(cacheDir, "admobVideoStreams");
        if (!this.zzdbi.isDirectory() && !this.zzdbi.mkdirs()) {
            String strValueOf = String.valueOf(this.zzdbi.getAbsolutePath());
            zzakb.zzdk(strValueOf.length() != 0 ? "Could not create preload cache directory at ".concat(strValueOf) : new String("Could not create preload cache directory at "));
            this.zzdbi = null;
        } else {
            if (this.zzdbi.setReadable(true, false) && this.zzdbi.setExecutable(true, false)) {
                return;
            }
            String strValueOf2 = String.valueOf(this.zzdbi.getAbsolutePath());
            zzakb.zzdk(strValueOf2.length() != 0 ? "Could not set cache file permissions at ".concat(strValueOf2) : new String("Could not set cache file permissions at "));
            this.zzdbi = null;
        }
    }

    private final File zzc(File file) {
        return new File(this.zzdbi, String.valueOf(file.getName()).concat(".done"));
    }

    @Override // com.google.android.gms.internal.ads.zzaqh
    public final void abort() {
        this.zzdbj = true;
    }

    /* JADX WARN: Code duplicated, block: B:214:0x05de  */
    /* JADX WARN: Multi-variable type inference failed */
    /* JADX WARN: Type inference failed for: r3v36 */
    /* JADX WARN: Type inference failed for: r3v45 */
    /* JADX WARN: Type inference failed for: r3v46 */
    /* JADX WARN: Type inference failed for: r3v59 */
    /* JADX WARN: Type inference failed for: r3v60, types: [com.google.android.gms.internal.ads.zzaqh] */
    /* JADX WARN: Type inference failed for: r3v71 */
    /* JADX WARN: Type inference failed for: r3v72 */
    /* JADX WARN: Type inference failed for: r3v76 */
    /* JADX WARN: Type inference failed for: r3v93 */
    /* JADX WARN: Type inference failed for: r3v94 */
    /* JADX WARN: Type inference failed for: r4v18, types: [long] */
    /* JADX WARN: Type inference failed for: r4v22, types: [java.lang.String] */
    @Override // com.google.android.gms.internal.ads.zzaqh
    public final boolean zzdp(String str) {
        int i;
        zzni zzniVarZzik;
        String str2;
        int responseCode;
        boolean zDelete;
        long jLastModified;
        if (this.zzdbi == null) {
            zza(str, null, "noCacheDir", null);
            return false;
        }
        do {
            if (this.zzdbi == null) {
                i = 0;
            } else {
                int i2 = 0;
                for (File file : this.zzdbi.listFiles()) {
                    if (!file.getName().endsWith(".done")) {
                        i2++;
                    }
                }
                i = i2;
            }
            if (i <= ((Integer) zzkb.zzik().zzd(zznk.zzaux)).intValue()) {
                zzkb.zzif();
                File file2 = new File(this.zzdbi, zzamu.zzde(str));
                File fileZzc = zzc(file2);
                if (file2.isFile() && fileZzc.isFile()) {
                    int length = (int) file2.length();
                    String strValueOf = String.valueOf(str);
                    zzakb.zzck(strValueOf.length() != 0 ? "Stream cache hit at ".concat(strValueOf) : new String("Stream cache hit at "));
                    zza(str, file2.getAbsolutePath(), length);
                    return true;
                }
                String strValueOf2 = String.valueOf(this.zzdbi.getAbsolutePath());
                String strValueOf3 = String.valueOf(str);
                String strConcat = strValueOf3.length() != 0 ? strValueOf2.concat(strValueOf3) : new String(strValueOf2);
                synchronized (zzdbg) {
                    if (zzdbg.contains(strConcat)) {
                        String strValueOf4 = String.valueOf(str);
                        zzakb.zzdk(strValueOf4.length() != 0 ? "Stream cache already in progress at ".concat(strValueOf4) : new String("Stream cache already in progress at "));
                        zza(str, file2.getAbsolutePath(), "inProgress", null);
                        return false;
                    }
                    zzdbg.add(strConcat);
                    FileOutputStream fileOutputStream = null;
                    try {
                        com.google.android.gms.ads.internal.zzbv.zzew();
                        int iIntValue = ((Integer) zzkb.zzik().zzd(zznk.zzavc)).intValue();
                        URL url = new URL(str);
                        int i3 = 0;
                        while (true) {
                            int i4 = i3 + 1;
                            if (i4 > 20) {
                                throw new IOException("Too many redirects (20)");
                            }
                            URLConnection uRLConnectionOpenConnection = url.openConnection();
                            uRLConnectionOpenConnection.setConnectTimeout(iIntValue);
                            uRLConnectionOpenConnection.setReadTimeout(iIntValue);
                            if (!(uRLConnectionOpenConnection instanceof HttpURLConnection)) {
                                throw new IOException("Invalid protocol.");
                            }
                            HttpURLConnection httpURLConnection = (HttpURLConnection) uRLConnectionOpenConnection;
                            zzamy zzamyVar = new zzamy();
                            zzamyVar.zza(httpURLConnection, (byte[]) null);
                            httpURLConnection.setInstanceFollowRedirects(false);
                            int responseCode2 = httpURLConnection.getResponseCode();
                            zzamyVar.zza(httpURLConnection, responseCode2);
                            if (responseCode2 / 100 != 3) {
                                if (!(httpURLConnection instanceof HttpURLConnection) || (responseCode = httpURLConnection.getResponseCode()) < 400) {
                                    int contentLength = httpURLConnection.getContentLength();
                                    if (contentLength < 0) {
                                        String strValueOf5 = String.valueOf(str);
                                        zzakb.zzdk(strValueOf5.length() != 0 ? "Stream cache aborted, missing content-length header at ".concat(strValueOf5) : new String("Stream cache aborted, missing content-length header at "));
                                        zza(str, file2.getAbsolutePath(), "contentLengthMissing", null);
                                        zzdbg.remove(strConcat);
                                        return false;
                                    }
                                    String str3 = zzdbh.format(contentLength);
                                    int iIntValue2 = ((Integer) zzkb.zzik().zzd(zznk.zzauy)).intValue();
                                    if (contentLength > iIntValue2) {
                                        zzakb.zzdk(new StringBuilder(String.valueOf(str3).length() + 33 + String.valueOf(str).length()).append("Content length ").append(str3).append(" exceeds limit at ").append(str).toString());
                                        String strValueOf6 = String.valueOf(str3);
                                        zza(str, file2.getAbsolutePath(), "sizeExceeded", strValueOf6.length() != 0 ? "File too big for full file cache. Size: ".concat(strValueOf6) : new String("File too big for full file cache. Size: "));
                                        zzdbg.remove(strConcat);
                                        return false;
                                    }
                                    StringBuilder sbAppend = new StringBuilder(String.valueOf(str3).length() + 20 + String.valueOf(str).length()).append("Caching ").append(str3);
                                    str2 = " bytes from ";
                                    zzakb.zzck(sbAppend.append(" bytes from ").append(str).toString());
                                    ReadableByteChannel readableByteChannelNewChannel = Channels.newChannel(httpURLConnection.getInputStream());
                                    FileOutputStream fileOutputStream2 = new FileOutputStream(file2);
                                    try {
                                        try {
                                            FileChannel channel = fileOutputStream2.getChannel();
                                            ByteBuffer byteBufferAllocate = ByteBuffer.allocate(1048576);
                                            e eVarZzer = com.google.android.gms.ads.internal.zzbv.zzer();
                                            int i5 = 0;
                                            long jCurrentTimeMillis = eVarZzer.currentTimeMillis();
                                            zzamj zzamjVar = new zzamj(((Long) zzkb.zzik().zzd(zznk.zzavb)).longValue());
                                            zzna<Long> zznaVar = zznk.zzava;
                                            zzniVarZzik = zzkb.zzik();
                                            long jLongValue = ((Long) zzniVarZzik.zzd(zznaVar)).longValue();
                                            while (true) {
                                                int i6 = readableByteChannelNewChannel.read(byteBufferAllocate);
                                                if (i6 < 0) {
                                                    fileOutputStream2.close();
                                                    if (zzakb.isLoggable(3)) {
                                                        String str4 = zzdbh.format(i5);
                                                        zzakb.zzck(new StringBuilder(String.valueOf(str4).length() + 22 + String.valueOf(str).length()).append("Preloaded ").append(str4).append(" bytes from ").append(str).toString());
                                                    }
                                                    file2.setReadable(true, false);
                                                    if (fileZzc.isFile()) {
                                                        fileZzc.setLastModified(System.currentTimeMillis());
                                                    } else {
                                                        try {
                                                            fileZzc.createNewFile();
                                                        } catch (IOException e) {
                                                        }
                                                    }
                                                    zza(str, file2.getAbsolutePath(), i5);
                                                    zzdbg.remove(strConcat);
                                                    return true;
                                                }
                                                i5 += i6;
                                                try {
                                                    try {
                                                        if (i5 > iIntValue2) {
                                                            String strValueOf7 = String.valueOf(Integer.toString(i5));
                                                            if (strValueOf7.length() != 0) {
                                                                "File too big for full file cache. Size: ".concat(strValueOf7);
                                                            } else {
                                                                new String("File too big for full file cache. Size: ");
                                                            }
                                                            throw new IOException("stream cache file size limit exceeded");
                                                        }
                                                        byteBufferAllocate.flip();
                                                        while (channel.write(byteBufferAllocate) > 0) {
                                                        }
                                                        byteBufferAllocate.clear();
                                                        str2 = 1000 * jLongValue;
                                                        if (eVarZzer.currentTimeMillis() - jCurrentTimeMillis > str2) {
                                                            String string = Long.toString(jLongValue);
                                                            new StringBuilder(String.valueOf(string).length() + 29).append("Timeout exceeded. Limit: ").append(string).append(" sec").toString();
                                                            throw new IOException("stream cache time limit exceeded");
                                                        }
                                                        if (this.zzdbj) {
                                                            throw new IOException("abort requested");
                                                        }
                                                        if (zzamjVar.tryAcquire()) {
                                                            zzniVarZzik = this;
                                                            str2 = str;
                                                            zzamu.zzsy.post(new zzaqi(zzniVarZzik, str2, file2.getAbsolutePath(), i5, contentLength, false));
                                                        }
                                                    } catch (IOException e2) {
                                                        e = e2;
                                                        fileOutputStream = fileOutputStream2;
                                                    } catch (RuntimeException e3) {
                                                        e = e3;
                                                        fileOutputStream = fileOutputStream2;
                                                    }
                                                } catch (IOException e4) {
                                                    e = e4;
                                                    zzniVarZzik = 0;
                                                    fileOutputStream = fileOutputStream2;
                                                } catch (RuntimeException e5) {
                                                    e = e5;
                                                    zzniVarZzik = 0;
                                                    fileOutputStream = fileOutputStream2;
                                                }
                                            }
                                        } catch (IOException e6) {
                                            e = e6;
                                            zzniVarZzik = 0;
                                            str2 = "error";
                                            fileOutputStream = fileOutputStream2;
                                        }
                                    } catch (RuntimeException e7) {
                                        e = e7;
                                        zzniVarZzik = 0;
                                        str2 = "error";
                                        fileOutputStream = fileOutputStream2;
                                    }
                                } else {
                                    str2 = "badUrl";
                                    try {
                                        String strValueOf8 = String.valueOf(Integer.toString(responseCode));
                                        zzniVarZzik = strValueOf8.length() != 0 ? "HTTP request failed. Code: ".concat(strValueOf8) : new String("HTTP request failed. Code: ");
                                        try {
                                            throw new IOException(new StringBuilder(String.valueOf(str).length() + 32).append("HTTP status code ").append(responseCode).append(" at ").append(str).toString());
                                        } catch (IOException e8) {
                                            e = e8;
                                        } catch (RuntimeException e9) {
                                            e = e9;
                                        }
                                    } catch (IOException e10) {
                                        e = e10;
                                        zzniVarZzik = 0;
                                    } catch (RuntimeException e11) {
                                        e = e11;
                                        zzniVarZzik = 0;
                                    }
                                }
                                if (e instanceof RuntimeException) {
                                    com.google.android.gms.ads.internal.zzbv.zzeo().zza(e, "VideoStreamFullFileCache.preload");
                                }
                                try {
                                    fileOutputStream.close();
                                } catch (IOException e12) {
                                } catch (NullPointerException e13) {
                                }
                                if (this.zzdbj) {
                                    zzakb.zzdj(new StringBuilder(String.valueOf(str).length() + 26).append("Preload aborted for URL \"").append(str).append("\"").toString());
                                } else {
                                    zzakb.zzc(new StringBuilder(String.valueOf(str).length() + 25).append("Preload failed for URL \"").append(str).append("\"").toString(), e);
                                }
                                if (file2.exists() && !file2.delete()) {
                                    String strValueOf9 = String.valueOf(file2.getAbsolutePath());
                                    zzakb.zzdk(strValueOf9.length() != 0 ? "Could not delete partial cache file at ".concat(strValueOf9) : new String("Could not delete partial cache file at "));
                                }
                                zza(str, file2.getAbsolutePath(), str2, zzniVarZzik);
                                zzdbg.remove(strConcat);
                                return false;
                            }
                            String headerField = httpURLConnection.getHeaderField("Location");
                            if (headerField == null) {
                                throw new IOException("Missing Location header in redirect");
                            }
                            URL url2 = new URL(url, headerField);
                            String protocol = url2.getProtocol();
                            if (protocol == null) {
                                throw new IOException("Protocol is null");
                            }
                            if (!protocol.equals("http") && !protocol.equals("https")) {
                                String strValueOf10 = String.valueOf(protocol);
                                throw new IOException(strValueOf10.length() != 0 ? "Unsupported scheme: ".concat(strValueOf10) : new String("Unsupported scheme: "));
                            }
                            String strValueOf11 = String.valueOf(headerField);
                            zzakb.zzck(strValueOf11.length() != 0 ? "Redirecting to ".concat(strValueOf11) : new String("Redirecting to "));
                            httpURLConnection.disconnect();
                            i3 = i4;
                            url = url2;
                        }
                    } catch (IOException e14) {
                        e = e14;
                        zzniVarZzik = 0;
                        str2 = "error";
                    } catch (RuntimeException e15) {
                        e = e15;
                        zzniVarZzik = 0;
                        str2 = "error";
                    }
                }
            } else if (this.zzdbi == null) {
                zDelete = false;
            } else {
                File file3 = null;
                long j = Long.MAX_VALUE;
                File[] fileArrListFiles = this.zzdbi.listFiles();
                int length2 = fileArrListFiles.length;
                int i7 = 0;
                while (i7 < length2) {
                    File file4 = fileArrListFiles[i7];
                    if (file4.getName().endsWith(".done")) {
                        jLastModified = j;
                        file4 = file3;
                    } else {
                        jLastModified = file4.lastModified();
                        if (jLastModified >= j) {
                            jLastModified = j;
                            file4 = file3;
                        }
                    }
                    i7++;
                    j = jLastModified;
                    file3 = file4;
                }
                zDelete = false;
                if (file3 != null) {
                    zDelete = file3.delete();
                    File fileZzc2 = zzc(file3);
                    if (fileZzc2.isFile()) {
                        zDelete &= fileZzc2.delete();
                    }
                }
            }
        } while (zDelete);
        zzakb.zzdk("Unable to expire stream cache");
        zza(str, null, "expireFailed", null);
        return false;
    }
}
