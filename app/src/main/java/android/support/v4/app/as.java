package android.support.v4.app;

import android.os.Parcel;
import android.os.Parcelable;

/* JADX INFO: compiled from: FragmentManager.java */
/* JADX INFO: loaded from: classes.dex */
class as implements Parcelable.Creator<FragmentManagerState> {
    as() {
    }

    @Override // android.os.Parcelable.Creator
    /* JADX INFO: renamed from: c, reason: merged with bridge method [inline-methods] */
    public FragmentManagerState createFromParcel(Parcel parcel) {
        return new FragmentManagerState(parcel);
    }

    @Override // android.os.Parcelable.Creator
    /* JADX INFO: renamed from: j, reason: merged with bridge method [inline-methods] */
    public FragmentManagerState[] newArray(int i) {
        return new FragmentManagerState[i];
    }
}
