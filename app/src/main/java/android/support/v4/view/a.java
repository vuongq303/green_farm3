package android.support.v4.view;

import android.os.Parcel;
import android.os.Parcelable;

/* JADX INFO: compiled from: AbsSavedState.java */
/* JADX INFO: loaded from: classes.dex */
class a implements Parcelable.ClassLoaderCreator<AbsSavedState> {
    a() {
    }

    @Override // android.os.Parcelable.ClassLoaderCreator
    /* JADX INFO: renamed from: a, reason: merged with bridge method [inline-methods] */
    public AbsSavedState createFromParcel(Parcel parcel, ClassLoader classLoader) {
        if (parcel.readParcelable(classLoader) != null) {
            throw new IllegalStateException("superState must be null");
        }
        return AbsSavedState.gX;
    }

    @Override // android.os.Parcelable.Creator
    /* JADX INFO: renamed from: e, reason: merged with bridge method [inline-methods] */
    public AbsSavedState createFromParcel(Parcel parcel) {
        return createFromParcel(parcel, null);
    }

    @Override // android.os.Parcelable.Creator
    /* JADX INFO: renamed from: u, reason: merged with bridge method [inline-methods] */
    public AbsSavedState[] newArray(int i) {
        return new AbsSavedState[i];
    }
}
