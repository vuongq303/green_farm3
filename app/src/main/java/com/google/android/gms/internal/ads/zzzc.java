package com.google.android.gms.internal.ads;

import com.google.ads.b;
import com.google.ads.c;
import com.google.ads.mediation.h;
import java.util.Date;
import java.util.HashSet;

/* JADX INFO: loaded from: classes.dex */
@zzadh
public final class zzzc {
    public static int zza(b bVar) {
        switch (bVar) {
            case INVALID_REQUEST:
                return 1;
            case NETWORK_ERROR:
                return 2;
            case NO_FILL:
                return 3;
            default:
                return 0;
        }
    }

    public static h zza(zzjj zzjjVar, boolean z) {
        c cVar;
        HashSet hashSet = zzjjVar.zzapy != null ? new HashSet(zzjjVar.zzapy) : null;
        Date date = new Date(zzjjVar.zzapw);
        switch (zzjjVar.zzapx) {
            case 1:
                cVar = c.MALE;
                break;
            case 2:
                cVar = c.FEMALE;
                break;
            default:
                cVar = c.UNKNOWN;
                break;
        }
        return new h(date, cVar, hashSet, z, zzjjVar.zzaqe);
    }
}
