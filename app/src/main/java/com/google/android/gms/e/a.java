package com.google.android.gms.e;

import android.content.Context;
import android.os.Build;
import android.os.PowerManager;
import android.os.WorkSource;
import android.text.TextUtils;
import android.util.Log;
import com.google.android.gms.common.d.ab;
import com.google.android.gms.common.d.s;
import com.google.android.gms.common.d.x;
import com.google.android.gms.common.internal.bf;
import com.google.android.gms.common.stats.e;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicInteger;
import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import javax.annotation.concurrent.ThreadSafe;

/* JADX INFO: loaded from: classes.dex */
@ThreadSafe
public class a {
    private static ScheduledExecutorService bYL;
    private static b bYM = new c();
    private final Context bJi;
    private final PowerManager.WakeLock bYD;
    private WorkSource bYE;
    private String bYF;
    private final String bYG;
    private final String bYH;
    private final Map<String, Integer[]> bYI;
    private int bYJ;
    private AtomicInteger bYK;
    private final int zzady;
    private final String zzaeb;
    private boolean zzaec;

    public a(Context context, int i, @Nonnull String str) {
        this(context, i, str, null, context == null ? null : context.getPackageName());
    }

    public a(Context context, int i, @Nonnull String str, @Nullable String str2, @Nonnull String str3) {
        this(context, i, str, str2, str3, null);
    }

    public a(Context context, int i, @Nonnull String str, @Nullable String str2, @Nonnull String str3, @Nullable String str4) {
        this.zzaec = true;
        this.bYI = new HashMap();
        this.bYK = new AtomicInteger(0);
        bf.d(str, (Object) "Wake lock name can NOT be empty");
        this.zzady = i;
        this.bYH = str2;
        this.zzaeb = str4;
        this.bJi = context.getApplicationContext();
        if ("com.google.android.gms".equals(context.getPackageName())) {
            this.bYG = str;
        } else {
            String strValueOf = String.valueOf("*gcore*:");
            String strValueOf2 = String.valueOf(str);
            this.bYG = strValueOf2.length() != 0 ? strValueOf.concat(strValueOf2) : new String(strValueOf);
        }
        this.bYD = ((PowerManager) context.getSystemService("power")).newWakeLock(i, str);
        if (ab.aS(context)) {
            str3 = x.im(str3) ? context.getPackageName() : str3;
            if (!bYM.ago() || str3 == null || str4 == null) {
                this.bYE = ab.B(context, str3);
            } else {
                Log.d("WakeLock", new StringBuilder(String.valueOf(str3).length() + 42 + String.valueOf(str4).length()).append("Using experimental Pi WorkSource chains: ").append(str3).append(",").append(str4).toString());
                this.bYF = str3;
                this.bYE = ab.g(context, str3, str4);
            }
            c(this.bYE);
        }
        if (bYL == null) {
            bYL = com.google.android.gms.common.c.a.aaG().newSingleThreadScheduledExecutor();
        }
    }

    /* JADX WARN: Code duplicated, block: B:10:0x0018 A[Catch: all -> 0x0068, TryCatch #0 {, blocks: (B:4:0x0007, B:6:0x000b, B:14:0x0020, B:15:0x0041, B:19:0x0047, B:21:0x004f, B:22:0x0056, B:10:0x0018, B:12:0x001c), top: B:26:0x0007 }] */
    /* JADX WARN: Code duplicated, block: B:14:0x0020 A[Catch: all -> 0x0068, TryCatch #0 {, blocks: (B:4:0x0007, B:6:0x000b, B:14:0x0020, B:15:0x0041, B:19:0x0047, B:21:0x004f, B:22:0x0056, B:10:0x0018, B:12:0x001c), top: B:26:0x0007 }] */
    private final void Q(String str, int i) {
        boolean z;
        String strZzn = zzn(str);
        synchronized (this) {
            if (this.zzaec) {
                Integer[] numArr = this.bYI.get(strZzn);
                if (numArr == null) {
                    z = false;
                } else if (numArr[0].intValue() == 1) {
                    this.bYI.remove(strZzn);
                    z = true;
                } else {
                    numArr[0] = Integer.valueOf(numArr[0].intValue() - 1);
                    z = false;
                }
                if (z) {
                    e.abl().a(this.bJi, com.google.android.gms.common.stats.c.a(this.bYD, strZzn), 8, this.bYG, strZzn, this.zzaeb, this.zzady, agn());
                    this.bYJ--;
                } else if (!this.zzaec) {
                    e.abl().a(this.bJi, com.google.android.gms.common.stats.c.a(this.bYD, strZzn), 8, this.bYG, strZzn, this.zzaeb, this.zzady, agn());
                    this.bYJ--;
                }
            } else if (!this.zzaec && this.bYJ == 1) {
                e.abl().a(this.bJi, com.google.android.gms.common.stats.c.a(this.bYD, strZzn), 8, this.bYG, strZzn, this.zzaeb, this.zzady, agn());
                this.bYJ--;
            }
        }
        ox(i);
    }

    private final List<String> agn() {
        List<String> listB = ab.b(this.bYE);
        if (this.bYF == null) {
            return listB;
        }
        ArrayList arrayList = new ArrayList(listB);
        arrayList.add(this.bYF);
        return arrayList;
    }

    private final void d(WorkSource workSource) {
        try {
            this.bYD.setWorkSource(workSource);
        } catch (ArrayIndexOutOfBoundsException | IllegalArgumentException e) {
            Log.wtf("WakeLock", e.toString());
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public final void ox(int i) {
        if (this.bYD.isHeld()) {
            try {
                if (Build.VERSION.SDK_INT < 21 || i <= 0) {
                    this.bYD.release();
                } else {
                    this.bYD.release(i);
                }
            } catch (RuntimeException e) {
                if (!e.getClass().equals(RuntimeException.class)) {
                    throw e;
                }
                Log.e("WakeLock", String.valueOf(this.bYG).concat(" was already released!"), e);
            }
        }
    }

    /* JADX WARN: Code duplicated, block: B:17:0x0044 A[Catch: all -> 0x00bb, TryCatch #0 {, blocks: (B:4:0x0007, B:6:0x000f, B:11:0x0023, B:13:0x0027, B:15:0x0031, B:21:0x004c, B:22:0x006d, B:35:0x00ab, B:17:0x0044, B:19:0x0048, B:8:0x0013, B:10:0x001b), top: B:40:0x0007 }] */
    /* JADX WARN: Code duplicated, block: B:21:0x004c A[Catch: all -> 0x00bb, TryCatch #0 {, blocks: (B:4:0x0007, B:6:0x000f, B:11:0x0023, B:13:0x0027, B:15:0x0031, B:21:0x004c, B:22:0x006d, B:35:0x00ab, B:17:0x0044, B:19:0x0048, B:8:0x0013, B:10:0x001b), top: B:40:0x0007 }] */
    private final void zza(String str, long j) {
        boolean z;
        String strZzn = zzn(str);
        synchronized (this) {
            if ((!this.bYI.isEmpty() || this.bYJ > 0) && !this.bYD.isHeld()) {
                this.bYI.clear();
                this.bYJ = 0;
            }
            if (this.zzaec) {
                Integer[] numArr = this.bYI.get(strZzn);
                if (numArr == null) {
                    this.bYI.put(strZzn, new Integer[]{1});
                    z = true;
                } else {
                    numArr[0] = Integer.valueOf(numArr[0].intValue() + 1);
                    z = false;
                }
                if (z) {
                    e.abl().a(this.bJi, com.google.android.gms.common.stats.c.a(this.bYD, strZzn), 7, this.bYG, strZzn, this.zzaeb, this.zzady, agn(), j);
                    this.bYJ++;
                } else if (!this.zzaec) {
                    e.abl().a(this.bJi, com.google.android.gms.common.stats.c.a(this.bYD, strZzn), 7, this.bYG, strZzn, this.zzaeb, this.zzady, agn(), j);
                    this.bYJ++;
                }
            } else if (!this.zzaec && this.bYJ == 0) {
                e.abl().a(this.bJi, com.google.android.gms.common.stats.c.a(this.bYD, strZzn), 7, this.bYG, strZzn, this.zzaeb, this.zzady, agn(), j);
                this.bYJ++;
            }
        }
        this.bYD.acquire();
        if (j > 0) {
            bYL.schedule(new d(this), j, TimeUnit.MILLISECONDS);
            if (s.abv() || !this.zzaec) {
                return;
            }
            String strValueOf = String.valueOf(this.bYG);
            Log.wtf("WakeLock", strValueOf.length() != 0 ? "Do not acquire with timeout on reference counted wakeLocks before ICS. wakelock: ".concat(strValueOf) : new String("Do not acquire with timeout on reference counted wakeLocks before ICS. wakelock: "));
        }
    }

    private final void zzb(String str, int i) {
        if (this.bYK.decrementAndGet() < 0) {
            Log.e("WakeLock", "release without a matched acquire!");
        }
        Q(str, i);
    }

    private final String zzn(String str) {
        return (!this.zzaec || TextUtils.isEmpty(str)) ? this.bYH : str;
    }

    public void acquire(long j) {
        this.bYK.incrementAndGet();
        zza(null, j);
    }

    public void c(WorkSource workSource) {
        if (workSource == null || !ab.aS(this.bJi)) {
            return;
        }
        if (this.bYE != null) {
            this.bYE.add(workSource);
        } else {
            this.bYE = workSource;
        }
        d(this.bYE);
    }

    public boolean isHeld() {
        return this.bYD.isHeld();
    }

    public void release() {
        zzb(null, 0);
    }

    public void setReferenceCounted(boolean z) {
        this.bYD.setReferenceCounted(z);
        this.zzaec = z;
    }
}
