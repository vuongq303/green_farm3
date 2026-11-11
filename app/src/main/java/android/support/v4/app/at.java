package android.support.v4.app;

import android.os.Parcel;
import android.os.Parcelable;

/* JADX INFO: compiled from: Fragment.java */
/* JADX INFO: loaded from: classes.dex */
class at implements Parcelable.Creator<FragmentState> {
    at() {
    }

    @Override // android.os.Parcelable.Creator
    /* JADX INFO: renamed from: d, reason: merged with bridge method [inline-methods] */
    public FragmentState createFromParcel(Parcel parcel) {
        return new FragmentState(parcel);
    }

    @Override // android.os.Parcelable.Creator
    /* JADX INFO: renamed from: k, reason: merged with bridge method [inline-methods] */
    public FragmentState[] newArray(int i) {
        return new FragmentState[i];
    }
}
