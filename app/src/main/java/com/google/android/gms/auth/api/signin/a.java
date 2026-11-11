package com.google.android.gms.auth.api.signin;

import android.net.Uri;
import android.os.Parcel;
import android.os.Parcelable;
import com.facebook.R;
import com.google.android.gms.common.api.Scope;
import java.util.ArrayList;

/* JADX INFO: loaded from: classes.dex */
public class a implements Parcelable.Creator<GoogleSignInAccount> {
    @Override // android.os.Parcelable.Creator
    /* JADX INFO: renamed from: h, reason: merged with bridge method [inline-methods] */
    public GoogleSignInAccount createFromParcel(Parcel parcel) {
        int iW = com.google.android.gms.common.internal.safeparcel.a.w(parcel);
        int iD = 0;
        String strN = null;
        String strN2 = null;
        String strN3 = null;
        String strN4 = null;
        Uri uri = null;
        String strN5 = null;
        long jF = 0;
        String strN6 = null;
        ArrayList arrayListC = null;
        String strN7 = null;
        String strN8 = null;
        while (parcel.dataPosition() < iW) {
            int iV = com.google.android.gms.common.internal.safeparcel.a.v(parcel);
            switch (com.google.android.gms.common.internal.safeparcel.a.nT(iV)) {
                case 1:
                    iD = com.google.android.gms.common.internal.safeparcel.a.d(parcel, iV);
                    break;
                case 2:
                    strN = com.google.android.gms.common.internal.safeparcel.a.n(parcel, iV);
                    break;
                case 3:
                    strN2 = com.google.android.gms.common.internal.safeparcel.a.n(parcel, iV);
                    break;
                case 4:
                    strN3 = com.google.android.gms.common.internal.safeparcel.a.n(parcel, iV);
                    break;
                case 5:
                    strN4 = com.google.android.gms.common.internal.safeparcel.a.n(parcel, iV);
                    break;
                case 6:
                    uri = (Uri) com.google.android.gms.common.internal.safeparcel.a.a(parcel, iV, Uri.CREATOR);
                    break;
                case 7:
                    strN5 = com.google.android.gms.common.internal.safeparcel.a.n(parcel, iV);
                    break;
                case 8:
                    jF = com.google.android.gms.common.internal.safeparcel.a.f(parcel, iV);
                    break;
                case 9:
                    strN6 = com.google.android.gms.common.internal.safeparcel.a.n(parcel, iV);
                    break;
                case R.styleable.CardView_contentPaddingRight /* 10 */:
                    arrayListC = com.google.android.gms.common.internal.safeparcel.a.c(parcel, iV, Scope.CREATOR);
                    break;
                case R.styleable.CardView_contentPaddingTop /* 11 */:
                    strN7 = com.google.android.gms.common.internal.safeparcel.a.n(parcel, iV);
                    break;
                case R.styleable.CardView_contentPaddingBottom /* 12 */:
                    strN8 = com.google.android.gms.common.internal.safeparcel.a.n(parcel, iV);
                    break;
                default:
                    com.google.android.gms.common.internal.safeparcel.a.b(parcel, iV);
                    break;
            }
        }
        com.google.android.gms.common.internal.safeparcel.a.D(parcel, iW);
        return new GoogleSignInAccount(iD, strN, strN2, strN3, strN4, uri, strN5, jF, strN6, arrayListC, strN7, strN8);
    }

    @Override // android.os.Parcelable.Creator
    /* JADX INFO: renamed from: nl, reason: merged with bridge method [inline-methods] */
    public GoogleSignInAccount[] newArray(int i) {
        return new GoogleSignInAccount[i];
    }
}
