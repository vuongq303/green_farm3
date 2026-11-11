package com.google.android.gms.common.data;

import android.os.Parcel;
import android.os.Parcelable;
import com.facebook.share.internal.ShareConstants;
import com.google.android.gms.common.internal.safeparcel.SafeParcelable;

/* JADX INFO: loaded from: classes.dex */
public class f<T extends SafeParcelable> extends a<T> {
    private static final String[] bNc = {ShareConstants.WEB_DIALOG_PARAM_DATA};
    private final Parcelable.Creator<T> bNd;

    public f(DataHolder dataHolder, Parcelable.Creator<T> creator) {
        super(dataHolder);
        this.bNd = creator;
    }

    @Override // com.google.android.gms.common.data.c
    /* JADX INFO: renamed from: nE, reason: merged with bridge method [inline-methods] */
    public T get(int i) {
        byte[] bArrV = this.mDataHolder.v(ShareConstants.WEB_DIALOG_PARAM_DATA, i, this.mDataHolder.nF(i));
        Parcel parcelObtain = Parcel.obtain();
        parcelObtain.unmarshall(bArrV, 0, bArrV.length);
        parcelObtain.setDataPosition(0);
        T tCreateFromParcel = this.bNd.createFromParcel(parcelObtain);
        parcelObtain.recycle();
        return tCreateFromParcel;
    }
}
