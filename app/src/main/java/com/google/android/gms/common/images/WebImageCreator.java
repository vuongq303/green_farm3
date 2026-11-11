package com.google.android.gms.common.images;

import android.net.Uri;
import android.os.Parcel;
import android.os.Parcelable;
import com.google.android.gms.common.internal.safeparcel.a;

/* JADX INFO: loaded from: classes.dex */
public class WebImageCreator implements Parcelable.Creator<WebImage> {
    public static final int CONTENT_DESCRIPTION = 0;

    /* JADX WARN: Can't rename method to resolve collision */
    @Override // android.os.Parcelable.Creator
    public WebImage createFromParcel(Parcel parcel) {
        int iW = a.w(parcel);
        Uri uri = null;
        int iD = 0;
        int iD2 = 0;
        int iD3 = 0;
        while (parcel.dataPosition() < iW) {
            int iV = a.v(parcel);
            switch (a.nT(iV)) {
                case 1:
                    iD3 = a.d(parcel, iV);
                    break;
                case 2:
                    uri = (Uri) a.a(parcel, iV, Uri.CREATOR);
                    break;
                case 3:
                    iD2 = a.d(parcel, iV);
                    break;
                case 4:
                    iD = a.d(parcel, iV);
                    break;
                default:
                    a.b(parcel, iV);
                    break;
            }
        }
        a.D(parcel, iW);
        return new WebImage(iD3, uri, iD2, iD);
    }

    /* JADX WARN: Can't rename method to resolve collision */
    @Override // android.os.Parcelable.Creator
    public WebImage[] newArray(int i) {
        return new WebImage[i];
    }
}
