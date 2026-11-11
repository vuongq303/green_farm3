package com.google.android.gms.internal.ads;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/* JADX INFO: loaded from: classes.dex */
final class zzan {
    final String zza;
    final long zzb;
    final long zzc;
    long zzca;
    final String zzcb;
    final long zzd;
    final long zze;
    final List<zzl> zzg;

    /* JADX WARN: Illegal instructions before constructor call */
    zzan(String str, zzc zzcVar) {
        List arrayList;
        String str2 = zzcVar.zza;
        long j = zzcVar.zzb;
        long j2 = zzcVar.zzc;
        long j3 = zzcVar.zzd;
        long j4 = zzcVar.zze;
        if (zzcVar.zzg != null) {
            arrayList = zzcVar.zzg;
        } else {
            Map<String, String> map = zzcVar.zzf;
            arrayList = new ArrayList(map.size());
            for (Map.Entry<String, String> entry : map.entrySet()) {
                arrayList.add(new zzl(entry.getKey(), entry.getValue()));
            }
        }
        this(str, str2, j, j2, j3, j4, arrayList);
        this.zzca = zzcVar.data.length;
    }

    private zzan(String str, String str2, long j, long j2, long j3, long j4, List<zzl> list) {
        this.zzcb = str;
        this.zza = "".equals(str2) ? null : str2;
        this.zzb = j;
        this.zzc = j2;
        this.zzd = j3;
        this.zze = j4;
        this.zzg = list;
    }

    static zzan zzc(zzao zzaoVar) throws IOException {
        if (zzam.zzb((InputStream) zzaoVar) != 538247942) {
            throw new IOException();
        }
        return new zzan(zzam.zza(zzaoVar), zzam.zza(zzaoVar), zzam.zzc(zzaoVar), zzam.zzc(zzaoVar), zzam.zzc(zzaoVar), zzam.zzc(zzaoVar), zzam.zzb(zzaoVar));
    }

    final boolean zza(OutputStream outputStream) {
        try {
            zzam.zza(outputStream, 538247942);
            zzam.zza(outputStream, this.zzcb);
            zzam.zza(outputStream, this.zza == null ? "" : this.zza);
            zzam.zza(outputStream, this.zzb);
            zzam.zza(outputStream, this.zzc);
            zzam.zza(outputStream, this.zzd);
            zzam.zza(outputStream, this.zze);
            List<zzl> list = this.zzg;
            if (list != null) {
                zzam.zza(outputStream, list.size());
                for (zzl zzlVar : list) {
                    zzam.zza(outputStream, zzlVar.getName());
                    zzam.zza(outputStream, zzlVar.getValue());
                }
            } else {
                zzam.zza(outputStream, 0);
            }
            outputStream.flush();
            return true;
        } catch (IOException e) {
            zzaf.d("%s", e.toString());
            return false;
        }
    }
}
