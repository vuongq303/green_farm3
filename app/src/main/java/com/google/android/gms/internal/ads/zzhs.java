package com.google.android.gms.internal.ads;

import android.os.Environment;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import javax.annotation.concurrent.GuardedBy;

/* JADX INFO: loaded from: classes.dex */
@zzadh
public final class zzhs {
    private final zzhx zzakc;

    @GuardedBy("this")
    private final zzii zzakd;
    private final boolean zzake;

    private zzhs() {
        this.zzake = false;
        this.zzakc = new zzhx();
        this.zzakd = new zzii();
        zzhn();
    }

    public zzhs(zzhx zzhxVar) {
        this.zzakc = zzhxVar;
        this.zzake = ((Boolean) zzkb.zzik().zzd(zznk.zzbeo)).booleanValue();
        this.zzakd = new zzii();
        zzhn();
    }

    private final synchronized void zzb(zzhu.zza.zzb zzbVar) {
        this.zzakd.zzanl = zzho();
        this.zzakc.zzd(zzbfi.zzb(this.zzakd)).zzs(zzbVar.zzhq()).zzbd();
        String strValueOf = String.valueOf(Integer.toString(zzbVar.zzhq(), 10));
        zzakb.v(strValueOf.length() != 0 ? "Logging Event with event code : ".concat(strValueOf) : new String("Logging Event with event code : "));
    }

    private final synchronized void zzc(zzhu.zza.zzb zzbVar) {
        File externalStorageDirectory = Environment.getExternalStorageDirectory();
        if (externalStorageDirectory != null) {
            try {
                FileOutputStream fileOutputStream = new FileOutputStream(new File(externalStorageDirectory, "clearcut_events.txt"), true);
                try {
                    try {
                        fileOutputStream.write(zzd(zzbVar).getBytes());
                        fileOutputStream.write(10);
                        try {
                            fileOutputStream.close();
                        } catch (IOException e) {
                            zzakb.v("Could not close Clearcut output stream.");
                        }
                    } catch (IOException e2) {
                        zzakb.v("Could not write Clearcut to file.");
                        try {
                            fileOutputStream.close();
                        } catch (IOException e3) {
                            zzakb.v("Could not close Clearcut output stream.");
                        }
                    }
                } catch (Throwable th) {
                    try {
                        fileOutputStream.close();
                    } catch (IOException e4) {
                        zzakb.v("Could not close Clearcut output stream.");
                    }
                    throw th;
                }
            } catch (FileNotFoundException e5) {
                zzakb.v("Could not find file for Clearcut");
            }
        }
    }

    private final synchronized String zzd(zzhu.zza.zzb zzbVar) {
        return String.format("id=%s,timestamp=%s,event=%s", this.zzakd.zzanh, Long.valueOf(com.google.android.gms.ads.internal.zzbv.zzer().elapsedRealtime()), Integer.valueOf(zzbVar.zzhq()));
    }

    public static zzhs zzhm() {
        return new zzhs();
    }

    private final synchronized void zzhn() {
        this.zzakd.zzanp = new zzib();
        this.zzakd.zzanp.zzalw = new zzie();
        this.zzakd.zzanm = new zzig();
    }

    private static long[] zzho() {
        List<String> listZzjc = zznk.zzjc();
        ArrayList arrayList = new ArrayList();
        Iterator<String> it = listZzjc.iterator();
        while (it.hasNext()) {
            for (String str : it.next().split(",")) {
                try {
                    arrayList.add(Long.valueOf(str));
                } catch (NumberFormatException e) {
                    zzakb.v("Experiment ID is not a number");
                }
            }
        }
        long[] jArr = new long[arrayList.size()];
        ArrayList arrayList2 = arrayList;
        int size = arrayList2.size();
        int i = 0;
        int i2 = 0;
        while (i < size) {
            Object obj = arrayList2.get(i);
            i++;
            jArr[i2] = ((Long) obj).longValue();
            i2++;
        }
        return jArr;
    }

    public final synchronized void zza(zzht zzhtVar) {
        if (this.zzake) {
            try {
                zzhtVar.zza(this.zzakd);
            } catch (NullPointerException e) {
                com.google.android.gms.ads.internal.zzbv.zzeo().zza(e, "AdMobClearcutLogger.modify");
            }
        }
    }

    public final synchronized void zza(zzhu.zza.zzb zzbVar) {
        if (this.zzake) {
            if (((Boolean) zzkb.zzik().zzd(zznk.zzbep)).booleanValue()) {
                zzc(zzbVar);
            } else {
                zzb(zzbVar);
            }
        }
    }
}
