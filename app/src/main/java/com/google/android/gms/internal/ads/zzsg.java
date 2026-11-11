package com.google.android.gms.internal.ads;

import android.os.Parcel;
import android.os.Parcelable;
import com.google.android.gms.common.internal.safeparcel.AbstractSafeParcelable;
import com.google.android.gms.common.internal.safeparcel.c;
import java.util.Iterator;
import java.util.Map;

/* JADX INFO: loaded from: classes.dex */
@zzadh
public final class zzsg extends AbstractSafeParcelable {
    public static final Parcelable.Creator<zzsg> CREATOR = new zzsh();
    private final String url;
    private final String[] zzbnh;
    private final String[] zzbni;

    zzsg(String str, String[] strArr, String[] strArr2) {
        this.url = str;
        this.zzbnh = strArr;
        this.zzbni = strArr2;
    }

    public static zzsg zzh(zzr zzrVar) {
        Map<String, String> headers = zzrVar.getHeaders();
        int size = headers.size();
        String[] strArr = new String[size];
        String[] strArr2 = new String[size];
        int i = 0;
        Iterator<Map.Entry<String, String>> it = headers.entrySet().iterator();
        while (true) {
            int i2 = i;
            if (!it.hasNext()) {
                return new zzsg(zzrVar.getUrl(), strArr, strArr2);
            }
            Map.Entry<String, String> next = it.next();
            strArr[i2] = next.getKey();
            strArr2[i2] = next.getValue();
            i = i2 + 1;
        }
    }

    @Override // android.os.Parcelable
    public final void writeToParcel(Parcel parcel, int i) {
        int iX = c.x(parcel);
        c.a(parcel, 1, this.url, false);
        c.a(parcel, 2, this.zzbnh, false);
        c.a(parcel, 3, this.zzbni, false);
        c.G(parcel, iX);
    }
}
