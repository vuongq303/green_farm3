package com.google.android.gms.internal.plus;

import android.os.Parcel;
import android.os.Parcelable;
import com.facebook.R;
import com.facebook.appevents.codeless.internal.Constants;
import com.facebook.internal.FacebookRequestErrorClassification;
import com.google.android.gms.common.internal.safeparcel.a;
import com.google.android.gms.common.internal.safeparcel.b;
import java.util.ArrayList;
import java.util.HashSet;

/* JADX INFO: loaded from: classes.dex */
public final class zzs implements Parcelable.Creator<zzr> {
    @Override // android.os.Parcelable.Creator
    public final /* synthetic */ zzr createFromParcel(Parcel parcel) {
        int iW = a.w(parcel);
        HashSet hashSet = new HashSet();
        int iD = 0;
        String strN = null;
        zzr.zza zzaVar = null;
        String strN2 = null;
        String strN3 = null;
        int iD2 = 0;
        zzr.zzb zzbVar = null;
        String strN4 = null;
        String strN5 = null;
        int iD3 = 0;
        String strN6 = null;
        zzr.zzc zzcVar = null;
        boolean zC = false;
        String strN7 = null;
        zzr.zzd zzdVar = null;
        String strN8 = null;
        int iD4 = 0;
        ArrayList arrayListC = null;
        ArrayList arrayListC2 = null;
        int iD5 = 0;
        int iD6 = 0;
        String strN9 = null;
        String strN10 = null;
        ArrayList arrayListC3 = null;
        boolean zC2 = false;
        while (parcel.dataPosition() < iW) {
            int iV = a.v(parcel);
            switch (a.nT(iV)) {
                case 1:
                    iD = a.d(parcel, iV);
                    hashSet.add(1);
                    break;
                case 2:
                    strN = a.n(parcel, iV);
                    hashSet.add(2);
                    break;
                case 3:
                    zzr.zza zzaVar2 = (zzr.zza) a.a(parcel, iV, zzr.zza.CREATOR);
                    hashSet.add(3);
                    zzaVar = zzaVar2;
                    break;
                case 4:
                    strN2 = a.n(parcel, iV);
                    hashSet.add(4);
                    break;
                case 5:
                    strN3 = a.n(parcel, iV);
                    hashSet.add(5);
                    break;
                case 6:
                    iD2 = a.d(parcel, iV);
                    hashSet.add(6);
                    break;
                case 7:
                    zzr.zzb zzbVar2 = (zzr.zzb) a.a(parcel, iV, zzr.zzb.CREATOR);
                    hashSet.add(7);
                    zzbVar = zzbVar2;
                    break;
                case 8:
                    strN4 = a.n(parcel, iV);
                    hashSet.add(8);
                    break;
                case 9:
                    strN5 = a.n(parcel, iV);
                    hashSet.add(9);
                    break;
                case R.styleable.CardView_contentPaddingRight /* 10 */:
                case R.styleable.CardView_contentPaddingTop /* 11 */:
                case 13:
                case FacebookRequestErrorClassification.EC_USER_TOO_MANY_CALLS /* 17 */:
                default:
                    a.b(parcel, iV);
                    break;
                case R.styleable.CardView_contentPaddingBottom /* 12 */:
                    iD3 = a.d(parcel, iV);
                    hashSet.add(12);
                    break;
                case 14:
                    strN6 = a.n(parcel, iV);
                    hashSet.add(14);
                    break;
                case 15:
                    zzr.zzc zzcVar2 = (zzr.zzc) a.a(parcel, iV, zzr.zzc.CREATOR);
                    hashSet.add(15);
                    zzcVar = zzcVar2;
                    break;
                case 16:
                    zC = a.c(parcel, iV);
                    hashSet.add(16);
                    break;
                case 18:
                    strN7 = a.n(parcel, iV);
                    hashSet.add(18);
                    break;
                case 19:
                    zzr.zzd zzdVar2 = (zzr.zzd) a.a(parcel, iV, zzr.zzd.CREATOR);
                    hashSet.add(19);
                    zzdVar = zzdVar2;
                    break;
                case 20:
                    strN8 = a.n(parcel, iV);
                    hashSet.add(20);
                    break;
                case 21:
                    iD4 = a.d(parcel, iV);
                    hashSet.add(21);
                    break;
                case 22:
                    arrayListC = a.c(parcel, iV, zzr.zze.CREATOR);
                    hashSet.add(22);
                    break;
                case 23:
                    arrayListC2 = a.c(parcel, iV, zzr.zzf.CREATOR);
                    hashSet.add(23);
                    break;
                case 24:
                    iD5 = a.d(parcel, iV);
                    hashSet.add(24);
                    break;
                case Constants.MAX_TREE_DEPTH /* 25 */:
                    iD6 = a.d(parcel, iV);
                    hashSet.add(25);
                    break;
                case 26:
                    strN9 = a.n(parcel, iV);
                    hashSet.add(26);
                    break;
                case 27:
                    strN10 = a.n(parcel, iV);
                    hashSet.add(27);
                    break;
                case 28:
                    arrayListC3 = a.c(parcel, iV, zzr.zzg.CREATOR);
                    hashSet.add(28);
                    break;
                case 29:
                    zC2 = a.c(parcel, iV);
                    hashSet.add(29);
                    break;
            }
        }
        if (parcel.dataPosition() != iW) {
            throw new b(new StringBuilder(37).append("Overread allowed size end=").append(iW).toString(), parcel);
        }
        return new zzr(hashSet, iD, strN, zzaVar, strN2, strN3, iD2, zzbVar, strN4, strN5, iD3, strN6, zzcVar, zC, strN7, zzdVar, strN8, iD4, arrayListC, arrayListC2, iD5, iD6, strN9, strN10, arrayListC3, zC2);
    }

    @Override // android.os.Parcelable.Creator
    public final /* synthetic */ zzr[] newArray(int i) {
        return new zzr[i];
    }
}
