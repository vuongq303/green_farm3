package com.google.android.gms.internal.ads;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Build;
import android.util.Log;
import android.util.Pair;
import com.facebook.appevents.codeless.internal.Constants;
import com.google.android.gms.ads.identifier.AdvertisingIdClient;
import com.google.android.gms.common.g;
import com.google.android.gms.common.k;
import com.google.android.gms.common.l;
import dalvik.system.DexClassLoader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.lang.reflect.Method;
import java.security.NoSuchAlgorithmException;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;

/* JADX INFO: loaded from: classes.dex */
public class zzcz {
    private static final String TAG = zzcz.class.getSimpleName();
    protected Context zzrt;
    private ExecutorService zzru;
    private DexClassLoader zzrv;
    private zzck zzrw;
    private byte[] zzrx;
    private boolean zzsa;
    private zzcc zzsd;
    private Map<Pair<String, String>, zzeg> zzsg;
    private volatile AdvertisingIdClient zzry = null;
    private volatile boolean zzqt = false;
    private Future zzrz = null;
    private volatile zzba zzsb = null;
    private Future zzsc = null;
    private boolean zzse = false;
    private boolean zzsf = false;
    private boolean zzsh = false;
    private boolean zzsi = true;
    private boolean zzsj = false;

    final class zza extends BroadcastReceiver {
        private zza() {
        }

        /* synthetic */ zza(zzcz zzczVar, zzda zzdaVar) {
            this();
        }

        @Override // android.content.BroadcastReceiver
        public final void onReceive(Context context, Intent intent) {
            if ("android.intent.action.USER_PRESENT".equals(intent.getAction())) {
                zzcz.this.zzsi = true;
            } else if ("android.intent.action.SCREEN_OFF".equals(intent.getAction())) {
                zzcz.this.zzsi = false;
            }
        }
    }

    private zzcz(Context context) {
        Context applicationContext = context.getApplicationContext();
        this.zzsa = applicationContext != null;
        this.zzrt = this.zzsa ? applicationContext : context;
        this.zzsg = new HashMap();
    }

    public static zzcz zza(Context context, String str, String str2, boolean z) throws Throwable {
        zzcz zzczVar = new zzcz(context);
        try {
            zzczVar.zzru = Executors.newCachedThreadPool(new zzda());
            zzczVar.zzqt = z;
            if (z) {
                zzczVar.zzrz = zzczVar.zzru.submit(new zzdb(zzczVar));
            }
            zzczVar.zzru.execute(new zzdd(zzczVar));
            try {
                g gVarYD = g.YD();
                zzczVar.zzse = gVarYD.getApkVersion(zzczVar.zzrt) > 0;
                zzczVar.zzsf = gVarYD.isGooglePlayServicesAvailable(zzczVar.zzrt) == 0;
            } catch (Throwable th) {
            }
            zzczVar.zza(0, true);
            if (zzdg.isMainThread() && ((Boolean) zzkb.zzik().zzd(zznk.zzbaz)).booleanValue()) {
                throw new IllegalStateException("Task Context initialization must not be called from the UI thread.");
            }
            zzczVar.zzrw = new zzck(null);
            try {
                zzczVar.zzrx = zzczVar.zzrw.zzl(str);
                try {
                    try {
                        try {
                            File cacheDir = zzczVar.zzrt.getCacheDir();
                            if (cacheDir == null && (cacheDir = zzczVar.zzrt.getDir("dex", 0)) == null) {
                                throw new zzcw();
                            }
                            File file = cacheDir;
                            File file2 = new File(String.format("%s/%s.jar", file, "1521499837408"));
                            if (!file2.exists()) {
                                byte[] bArrZza = zzczVar.zzrw.zza(zzczVar.zzrx, str2);
                                file2.createNewFile();
                                FileOutputStream fileOutputStream = new FileOutputStream(file2);
                                fileOutputStream.write(bArrZza, 0, bArrZza.length);
                                fileOutputStream.close();
                            }
                            zzczVar.zzb(file, "1521499837408");
                            try {
                                zzczVar.zzrv = new DexClassLoader(file2.getAbsolutePath(), file.getAbsolutePath(), null, zzczVar.zzrt.getClassLoader());
                                zzb(file2);
                                zzczVar.zza(file, "1521499837408");
                                zzm(String.format("%s/%s.dex", file, "1521499837408"));
                                if (!zzczVar.zzsj) {
                                    IntentFilter intentFilter = new IntentFilter();
                                    intentFilter.addAction("android.intent.action.USER_PRESENT");
                                    intentFilter.addAction("android.intent.action.SCREEN_OFF");
                                    zzczVar.zzrt.registerReceiver(new zza(zzczVar, null), intentFilter);
                                    zzczVar.zzsj = true;
                                }
                                zzczVar.zzsd = new zzcc(zzczVar);
                                zzczVar.zzsh = true;
                            } catch (Throwable th2) {
                                zzb(file2);
                                zzczVar.zza(file, "1521499837408");
                                zzm(String.format("%s/%s.dex", file, "1521499837408"));
                                throw th2;
                            }
                        } catch (IOException e) {
                            throw new zzcw(e);
                        } catch (NullPointerException e2) {
                            throw new zzcw(e2);
                        }
                    } catch (zzcl e3) {
                        throw new zzcw(e3);
                    }
                } catch (FileNotFoundException e4) {
                    throw new zzcw(e4);
                }
            } catch (zzcl e5) {
                throw new zzcw(e5);
            }
        } catch (zzcw e6) {
        }
        return zzczVar;
    }

    private final void zza(File file, String str) throws Throwable {
        Throwable th;
        FileOutputStream fileOutputStream;
        FileInputStream fileInputStream;
        FileOutputStream fileOutputStream2;
        File file2 = new File(String.format("%s/%s.tmp", file, str));
        if (file2.exists()) {
            return;
        }
        File file3 = new File(String.format("%s/%s.dex", file, str));
        if (file3.exists()) {
            long length = file3.length();
            if (length > 0) {
                byte[] bArr = new byte[(int) length];
                try {
                    fileInputStream = new FileInputStream(file3);
                    try {
                        if (fileInputStream.read(bArr) <= 0) {
                            try {
                                fileInputStream.close();
                            } catch (IOException e) {
                            }
                            zzb(file3);
                            return;
                        }
                        zzbe zzbeVar = new zzbe();
                        zzbeVar.zzgs = Build.VERSION.SDK.getBytes();
                        zzbeVar.zzgr = str.getBytes();
                        byte[] bytes = this.zzrw.zzb(this.zzrx, bArr).getBytes();
                        zzbeVar.data = bytes;
                        zzbeVar.zzgq = zzbk.zzb(bytes);
                        file2.createNewFile();
                        fileOutputStream2 = new FileOutputStream(file2);
                        try {
                            byte[] bArrZzb = zzbfi.zzb(zzbeVar);
                            fileOutputStream2.write(bArrZzb, 0, bArrZzb.length);
                            fileOutputStream2.close();
                            try {
                                fileInputStream.close();
                            } catch (IOException e2) {
                            }
                            try {
                                fileOutputStream2.close();
                            } catch (IOException e3) {
                            }
                            zzb(file3);
                            return;
                        } catch (zzcl e4) {
                        } catch (IOException e5) {
                        } catch (NoSuchAlgorithmException e6) {
                        } catch (Throwable th2) {
                            th = th2;
                            fileOutputStream = fileOutputStream2;
                            if (fileInputStream != null) {
                                try {
                                    fileInputStream.close();
                                } catch (IOException e7) {
                                }
                            }
                            if (fileOutputStream != null) {
                                try {
                                    fileOutputStream.close();
                                } catch (IOException e8) {
                                }
                            }
                            zzb(file3);
                            throw th;
                        }
                    } catch (zzcl e9) {
                        fileOutputStream2 = null;
                    } catch (IOException e10) {
                        fileOutputStream2 = null;
                    } catch (NoSuchAlgorithmException e11) {
                        fileOutputStream2 = null;
                    } catch (Throwable th3) {
                        th = th3;
                        fileOutputStream = null;
                    }
                } catch (zzcl e12) {
                    fileOutputStream2 = null;
                    fileInputStream = null;
                } catch (IOException e13) {
                    fileOutputStream2 = null;
                    fileInputStream = null;
                } catch (NoSuchAlgorithmException e14) {
                    fileOutputStream2 = null;
                    fileInputStream = null;
                } catch (Throwable th4) {
                    th = th4;
                    fileOutputStream = null;
                    fileInputStream = null;
                }
                if (fileInputStream != null) {
                    try {
                        fileInputStream.close();
                    } catch (IOException e15) {
                    }
                }
                if (fileOutputStream2 != null) {
                    try {
                        fileOutputStream2.close();
                    } catch (IOException e16) {
                    }
                }
                zzb(file3);
            }
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static boolean zza(int i, zzba zzbaVar) {
        if (i < 4) {
            if (zzbaVar == null) {
                return true;
            }
            if (((Boolean) zzkb.zzik().zzd(zznk.zzbbc)).booleanValue() && (zzbaVar.zzcx == null || zzbaVar.zzcx.equals("0000000000000000000000000000000000000000000000000000000000000000"))) {
                return true;
            }
            if (((Boolean) zzkb.zzik().zzd(zznk.zzbbd)).booleanValue() && (zzbaVar.zzfn == null || zzbaVar.zzfn.zzgl == null || zzbaVar.zzfn.zzgl.longValue() == -2)) {
                return true;
            }
        }
        return false;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public final void zzal() {
        try {
            if (this.zzry == null && this.zzsa) {
                AdvertisingIdClient advertisingIdClient = new AdvertisingIdClient(this.zzrt);
                advertisingIdClient.start();
                this.zzry = advertisingIdClient;
            }
        } catch (k | l | IOException e) {
            this.zzry = null;
        }
    }

    private final zzba zzam() {
        try {
            return zzatq.zzl(this.zzrt, this.zzrt.getPackageName(), Integer.toString(this.zzrt.getPackageManager().getPackageInfo(this.zzrt.getPackageName(), 0).versionCode));
        } catch (Throwable th) {
            return null;
        }
    }

    private static void zzb(File file) {
        if (file.exists()) {
            file.delete();
        } else {
            Log.d(TAG, String.format("File %s not found. No need for deletion", file.getAbsolutePath()));
        }
    }

    /* JADX WARN: Code duplicated, block: B:87:0x00d1 A[EXC_TOP_SPLITTER, SYNTHETIC] */
    /* JADX WARN: Code duplicated, block: B:89:0x00cc A[EXC_TOP_SPLITTER, SYNTHETIC] */
    /* JADX WARN: Multi-variable type inference failed */
    /* JADX WARN: Type inference failed for: r0v17, types: [boolean] */
    /* JADX WARN: Type inference failed for: r1v10 */
    /* JADX WARN: Type inference failed for: r1v12 */
    /* JADX WARN: Type inference failed for: r1v14 */
    /* JADX WARN: Type inference failed for: r1v17 */
    /* JADX WARN: Type inference failed for: r1v18 */
    /* JADX WARN: Type inference failed for: r1v19 */
    /* JADX WARN: Type inference failed for: r1v2 */
    /* JADX WARN: Type inference failed for: r1v3 */
    /* JADX WARN: Type inference failed for: r1v4 */
    /* JADX WARN: Type inference failed for: r1v6, types: [java.io.FileInputStream] */
    /* JADX WARN: Type inference failed for: r4v10 */
    /* JADX WARN: Type inference failed for: r4v11 */
    /* JADX WARN: Type inference failed for: r4v12 */
    /* JADX WARN: Type inference failed for: r4v13 */
    /* JADX WARN: Type inference failed for: r4v14 */
    /* JADX WARN: Type inference failed for: r4v15 */
    /* JADX WARN: Type inference failed for: r4v2 */
    /* JADX WARN: Type inference failed for: r4v3, types: [java.io.FileInputStream] */
    /* JADX WARN: Type inference failed for: r4v4, types: [java.io.FileInputStream] */
    /* JADX WARN: Type inference failed for: r4v5 */
    /* JADX WARN: Type inference failed for: r4v6 */
    /* JADX WARN: Type inference failed for: r4v7 */
    /* JADX WARN: Type inference failed for: r4v8 */
    /* JADX WARN: Type inference failed for: r4v9 */
    private final boolean zzb(File file, String str) throws Throwable {
        Throwable th;
        FileOutputStream fileOutputStream;
        ?? r4;
        FileOutputStream fileOutputStream2;
        ?? r1;
        ?? fileInputStream;
        boolean z;
        FileOutputStream fileOutputStream3 = null;
        IOException e = null;
        File file2 = new File(String.format("%s/%s.tmp", file, str));
        if (!file2.exists()) {
            return false;
        }
        Object[] objArr = {file, str};
        File file3 = new File(String.format("%s/%s.dex", objArr));
        if (file3.exists()) {
            return false;
        }
        try {
            long length = file2.length();
            if (length <= 0) {
                zzb(file2);
                z = false;
                fileInputStream = objArr;
            } else {
                byte[] bArr = new byte[(int) length];
                fileInputStream = new FileInputStream(file2);
                try {
                    if (fileInputStream.read(bArr) <= 0) {
                        Log.d(TAG, "Cannot read the cache data.");
                        zzb(file2);
                        try {
                            fileInputStream.close();
                        } catch (IOException e2) {
                        }
                        z = false;
                        fileInputStream = fileInputStream;
                    } else {
                        zzbe zzbeVar = (zzbe) zzbfi.zza(new zzbe(), bArr);
                        if (str.equals(new String(zzbeVar.zzgr)) && Arrays.equals(zzbeVar.zzgq, zzbk.zzb(zzbeVar.data)) && Arrays.equals(zzbeVar.zzgs, Build.VERSION.SDK.getBytes())) {
                            byte[] bArrZza = this.zzrw.zza(this.zzrx, new String(zzbeVar.data));
                            file3.createNewFile();
                            fileOutputStream2 = new FileOutputStream(file3);
                            fileOutputStream3 = null;
                            try {
                                fileOutputStream2.write(bArrZza, 0, bArrZza.length);
                                try {
                                    fileInputStream.close();
                                } catch (IOException e3) {
                                    e = e3;
                                }
                                try {
                                    fileOutputStream2.close();
                                } catch (IOException e4) {
                                }
                                z = true;
                                fileInputStream = fileInputStream;
                            } catch (zzcl e5) {
                                r1 = fileInputStream;
                                if (r1 != 0) {
                                    try {
                                        r1.close();
                                    } catch (IOException e6) {
                                    }
                                }
                                if (fileOutputStream2 != null) {
                                    try {
                                        fileOutputStream2.close();
                                    } catch (IOException e7) {
                                    }
                                }
                                return e;
                            } catch (IOException e8) {
                                r1 = fileInputStream;
                                if (r1 != 0) {
                                    r1.close();
                                }
                                if (fileOutputStream2 != null) {
                                    fileOutputStream2.close();
                                }
                                return e;
                            } catch (NoSuchAlgorithmException e9) {
                                r1 = fileInputStream;
                                if (r1 != 0) {
                                    r1.close();
                                }
                                if (fileOutputStream2 != null) {
                                    fileOutputStream2.close();
                                }
                                return e;
                            } catch (Throwable th2) {
                                th = th2;
                                fileOutputStream = fileOutputStream2;
                                r4 = fileInputStream;
                                if (r4 != 0) {
                                    try {
                                        r4.close();
                                    } catch (IOException e10) {
                                    }
                                }
                                if (fileOutputStream == null) {
                                    throw th;
                                }
                                try {
                                    fileOutputStream.close();
                                    throw th;
                                } catch (IOException e11) {
                                    throw th;
                                }
                            }
                        } else {
                            zzb(file2);
                            try {
                                fileInputStream.close();
                            } catch (IOException e12) {
                            }
                            z = false;
                            fileInputStream = fileInputStream;
                        }
                    }
                } catch (zzcl e13) {
                    fileOutputStream2 = fileOutputStream3;
                    r1 = fileInputStream;
                } catch (IOException e14) {
                    fileOutputStream2 = fileOutputStream3;
                    r1 = fileInputStream;
                } catch (NoSuchAlgorithmException e15) {
                    fileOutputStream2 = fileOutputStream3;
                    r1 = fileInputStream;
                } catch (Throwable th3) {
                    th = th3;
                    fileOutputStream = fileOutputStream3;
                    r4 = fileInputStream;
                }
            }
            return z;
        } catch (zzcl e16) {
            fileOutputStream2 = fileOutputStream3;
            r1 = fileOutputStream3;
        } catch (IOException e17) {
            fileOutputStream2 = fileOutputStream3;
            r1 = fileOutputStream3;
        } catch (NoSuchAlgorithmException e18) {
            fileOutputStream2 = fileOutputStream3;
            r1 = fileOutputStream3;
        } catch (Throwable th4) {
            th = th4;
            fileOutputStream = fileOutputStream3;
            r4 = fileOutputStream3;
        }
    }

    private static void zzm(String str) {
        zzb(new File(str));
    }

    public final Context getContext() {
        return this.zzrt;
    }

    public final boolean isInitialized() {
        return this.zzsh;
    }

    public final Method zza(String str, String str2) {
        zzeg zzegVar = this.zzsg.get(new Pair(str, str2));
        if (zzegVar == null) {
            return null;
        }
        return zzegVar.zzaw();
    }

    final void zza(int i, boolean z) {
        if (this.zzsf) {
            Future<?> futureSubmit = this.zzru.submit(new zzdc(this, i, z));
            if (i == 0) {
                this.zzsc = futureSubmit;
            }
        }
    }

    public final boolean zza(String str, String str2, Class<?>... clsArr) {
        if (this.zzsg.containsKey(new Pair(str, str2))) {
            return false;
        }
        this.zzsg.put(new Pair<>(str, str2), new zzeg(this, str, str2, clsArr));
        return true;
    }

    public final ExecutorService zzab() {
        return this.zzru;
    }

    public final DexClassLoader zzac() {
        return this.zzrv;
    }

    public final zzck zzad() {
        return this.zzrw;
    }

    public final byte[] zzae() {
        return this.zzrx;
    }

    public final boolean zzaf() {
        return this.zzse;
    }

    public final zzcc zzag() {
        return this.zzsd;
    }

    public final boolean zzah() {
        return this.zzsf;
    }

    public final boolean zzai() {
        return this.zzsi;
    }

    public final zzba zzaj() {
        return this.zzsb;
    }

    public final Future zzak() {
        return this.zzsc;
    }

    public final AdvertisingIdClient zzan() {
        if (!this.zzqt) {
            return null;
        }
        if (this.zzry != null) {
            return this.zzry;
        }
        if (this.zzrz != null) {
            try {
                this.zzrz.get(2000L, TimeUnit.MILLISECONDS);
                this.zzrz = null;
            } catch (InterruptedException e) {
            } catch (ExecutionException e2) {
            } catch (TimeoutException e3) {
                this.zzrz.cancel(true);
            }
        }
        return this.zzry;
    }

    final zzba zzb(int i, boolean z) {
        if (i > 0 && z) {
            try {
                Thread.sleep(i * Constants.APP_INDEXING_SCHEDULE_INTERVAL_MS);
            } catch (InterruptedException e) {
            }
        }
        return zzam();
    }

    public final int zzx() {
        if (this.zzsd != null) {
            return zzcc.zzx();
        }
        return Integer.MIN_VALUE;
    }
}
