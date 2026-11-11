package android.support.v4.app;

import android.os.Parcel;
import android.os.Parcelable;

/* JADX INFO: compiled from: FragmentManager.java */
/* JADX INFO: loaded from: classes.dex */
final class FragmentManagerState implements Parcelable {
    public static final Parcelable.Creator<FragmentManagerState> CREATOR = new as();
    int aQ;
    FragmentState[] bL;
    int[] bM;
    BackStackState[] bN;
    int bO;

    public FragmentManagerState() {
        this.bO = -1;
    }

    public FragmentManagerState(Parcel parcel) {
        this.bO = -1;
        this.bL = (FragmentState[]) parcel.createTypedArray(FragmentState.CREATOR);
        this.bM = parcel.createIntArray();
        this.bN = (BackStackState[]) parcel.createTypedArray(BackStackState.CREATOR);
        this.bO = parcel.readInt();
        this.aQ = parcel.readInt();
    }

    @Override // android.os.Parcelable
    public int describeContents() {
        return 0;
    }

    @Override // android.os.Parcelable
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeTypedArray(this.bL, i);
        parcel.writeIntArray(this.bM);
        parcel.writeTypedArray(this.bN, i);
        parcel.writeInt(this.bO);
        parcel.writeInt(this.aQ);
    }
}
