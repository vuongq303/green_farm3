package com.google.android.gms.internal.ads;

import java.io.UnsupportedEncodingException;

/* JADX INFO: loaded from: classes.dex */
public class zzav extends zzr<String> {
    private final Object mLock;
    private zzz<String> zzck;

    public zzav(int i, String str, zzz<String> zzzVar, zzy zzyVar) {
        super(i, str, zzyVar);
        this.mLock = new Object();
        this.zzck = zzzVar;
    }

    @Override // com.google.android.gms.internal.ads.zzr
    protected final zzx<String> zza(zzp zzpVar) {
        String str;
        String str2;
        try {
            byte[] bArr = zzpVar.data;
            String str3 = zzpVar.zzab.get("Content-Type");
            if (str3 == null) {
                str2 = "ISO-8859-1";
                break;
            }
            String[] strArrSplit = str3.split(";");
            int i = 1;
            while (true) {
                if (i >= strArrSplit.length) {
                    str2 = "ISO-8859-1";
                    break;
                }
                String[] strArrSplit2 = strArrSplit[i].trim().split("=");
                if (strArrSplit2.length == 2 && strArrSplit2[0].equals("charset")) {
                    str2 = strArrSplit2[1];
                    break;
                }
                i++;
            }
            str = new String(bArr, str2);
        } catch (UnsupportedEncodingException e) {
            str = new String(zzpVar.data);
        }
        return zzx.zza(str, zzap.zzb(zzpVar));
    }

    /* JADX INFO: Access modifiers changed from: protected */
    @Override // com.google.android.gms.internal.ads.zzr
    /* JADX INFO: renamed from: zzh, reason: merged with bridge method [inline-methods] */
    public void zza(String str) {
        zzz<String> zzzVar;
        synchronized (this.mLock) {
            zzzVar = this.zzck;
        }
        if (zzzVar != null) {
            zzzVar.zzb(str);
        }
    }
}
