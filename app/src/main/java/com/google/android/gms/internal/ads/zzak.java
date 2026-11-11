package com.google.android.gms.internal.ads;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.LinkedList;
import java.util.List;

/* JADX INFO: loaded from: classes.dex */
public final class zzak {
    private static final Comparator<byte[]> zzbv = new zzal();
    private final List<byte[]> zzbr = new LinkedList();
    private final List<byte[]> zzbs = new ArrayList(64);
    private int zzbt = 0;
    private final int zzbu = 4096;

    public zzak(int i) {
    }

    private final synchronized void zzn() {
        while (this.zzbt > this.zzbu) {
            byte[] bArrRemove = this.zzbr.remove(0);
            this.zzbs.remove(bArrRemove);
            this.zzbt -= bArrRemove.length;
        }
    }

    public final synchronized void zza(byte[] bArr) {
        if (bArr != null) {
            if (bArr.length <= this.zzbu) {
                this.zzbr.add(bArr);
                int iBinarySearch = Collections.binarySearch(this.zzbs, bArr, zzbv);
                if (iBinarySearch < 0) {
                    iBinarySearch = (-iBinarySearch) - 1;
                }
                this.zzbs.add(iBinarySearch, bArr);
                this.zzbt += bArr.length;
                zzn();
            }
        }
    }

    public final synchronized byte[] zzb(int i) {
        byte[] bArr;
        int i2 = 0;
        while (true) {
            int i3 = i2;
            if (i3 >= this.zzbs.size()) {
                bArr = new byte[i];
                break;
            }
            bArr = this.zzbs.get(i3);
            if (bArr.length >= i) {
                this.zzbt -= bArr.length;
                this.zzbs.remove(i3);
                this.zzbr.remove(bArr);
                break;
            }
            i2 = i3 + 1;
        }
        return bArr;
    }
}
