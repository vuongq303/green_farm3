package android.support.v7.widget;

import android.os.Parcel;
import android.os.Parcelable;

/* JADX INFO: compiled from: Toolbar.java */
/* JADX INFO: loaded from: classes.dex */
final class de implements Parcelable.ClassLoaderCreator<Toolbar.SavedState> {
    de() {
    }

    @Override // android.os.Parcelable.ClassLoaderCreator
    /* JADX INFO: renamed from: b, reason: merged with bridge method [inline-methods] */
    public Toolbar.SavedState createFromParcel(Parcel parcel, ClassLoader classLoader) {
        return new Toolbar.SavedState(parcel, classLoader);
    }

    @Override // android.os.Parcelable.Creator
    /* JADX INFO: renamed from: g, reason: merged with bridge method [inline-methods] */
    public Toolbar.SavedState createFromParcel(Parcel parcel) {
        return new Toolbar.SavedState(parcel, null);
    }

    @Override // android.os.Parcelable.Creator
    /* JADX INFO: renamed from: ax, reason: merged with bridge method [inline-methods] */
    public Toolbar.SavedState[] newArray(int i) {
        return new Toolbar.SavedState[i];
    }
}
