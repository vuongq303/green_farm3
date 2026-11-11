package android.support.v4.widget;

import android.os.Parcel;
import android.os.Parcelable;

/* JADX INFO: compiled from: NestedScrollView.java */
/* JADX INFO: loaded from: classes.dex */
class s implements Parcelable.Creator<NestedScrollView.SavedState> {
    s() {
    }

    @Override // android.os.Parcelable.Creator
    /* JADX INFO: renamed from: f, reason: merged with bridge method [inline-methods] */
    public NestedScrollView.SavedState createFromParcel(Parcel parcel) {
        return new NestedScrollView.SavedState(parcel);
    }

    @Override // android.os.Parcelable.Creator
    /* JADX INFO: renamed from: N, reason: merged with bridge method [inline-methods] */
    public NestedScrollView.SavedState[] newArray(int i) {
        return new NestedScrollView.SavedState[i];
    }
}
