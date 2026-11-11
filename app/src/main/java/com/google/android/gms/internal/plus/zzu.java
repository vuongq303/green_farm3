package com.google.android.gms.internal.plus;

import android.os.Parcel;
import android.os.Parcelable;
import com.google.android.gms.common.internal.safeparcel.a;
import com.google.android.gms.common.internal.safeparcel.b;
import java.util.HashSet;

/* JADX INFO: loaded from: classes.dex */
public final class zzu implements Parcelable.Creator<zzr.zzb> {
    @Override // android.os.Parcelable.Creator
    public final /* synthetic */ zzr.zzb createFromParcel(Parcel parcel) {
        int iW = a.w(parcel);
        HashSet hashSet = new HashSet();
        int iD = 0;
        zzr.zzb.C0004zzb c0004zzb = null;
        zzr.zzb.zza zzaVar = null;
        int iD2 = 0;
        while (parcel.dataPosition() < iW) {
            int iV = a.v(parcel);
            switch (a.nT(iV)) {
                case 1:
                    iD2 = a.d(parcel, iV);
                    hashSet.add(1);
                    break;
                case 2:
                    zzr.zzb.zza zzaVar2 = (zzr.zzb.zza) a.a(parcel, iV, zzr.zzb.zza.CREATOR);
                    hashSet.add(2);
                    zzaVar = zzaVar2;
                    break;
                case 3:
                    zzr.zzb.C0004zzb c0004zzb2 = (zzr.zzb.C0004zzb) a.a(parcel, iV, zzr.zzb.C0004zzb.CREATOR);
                    hashSet.add(3);
                    c0004zzb = c0004zzb2;
                    break;
                case 4:
                    iD = a.d(parcel, iV);
                    hashSet.add(4);
                    break;
                default:
                    a.b(parcel, iV);
                    break;
            }
        }
        if (parcel.dataPosition() != iW) {
            throw new b(new StringBuilder(37).append("Overread allowed size end=").append(iW).toString(), parcel);
        }
        return new zzr.zzb(hashSet, iD2, zzaVar, c0004zzb, iD);
    }

    @Override // android.os.Parcelable.Creator
    public final /* synthetic */ zzr.zzb[] newArray(int i) {
        return new zzr.zzb[i];
    }
}
