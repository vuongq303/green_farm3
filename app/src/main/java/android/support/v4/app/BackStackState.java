package android.support.v4.app;

import android.os.Parcel;
import android.os.Parcelable;
import android.text.TextUtils;
import android.util.Log;
import java.util.ArrayList;

/* JADX INFO: compiled from: BackStackRecord.java */
/* JADX INFO: loaded from: classes.dex */
final class BackStackState implements Parcelable {
    public static final Parcelable.Creator<BackStackState> CREATOR = new k();
    final int R;
    final int S;
    final int W;
    final CharSequence X;
    final int Y;
    final CharSequence Z;
    final ArrayList<String> aa;
    final ArrayList<String> ab;
    final boolean ac;
    final int[] aj;
    final int mIndex;
    final String mName;

    public BackStackState(i iVar) {
        int size = iVar.M.size();
        this.aj = new int[size * 6];
        if (!iVar.T) {
            throw new IllegalStateException("Not on back stack");
        }
        int i = 0;
        for (int i2 = 0; i2 < size; i2++) {
            j jVar = iVar.M.get(i2);
            int i3 = i + 1;
            this.aj[i] = jVar.ae;
            int i4 = i3 + 1;
            this.aj[i3] = jVar.fragment != null ? jVar.fragment.mIndex : -1;
            int i5 = i4 + 1;
            this.aj[i4] = jVar.af;
            int i6 = i5 + 1;
            this.aj[i5] = jVar.ag;
            int i7 = i6 + 1;
            this.aj[i6] = jVar.ah;
            i = i7 + 1;
            this.aj[i7] = jVar.ai;
        }
        this.R = iVar.R;
        this.S = iVar.S;
        this.mName = iVar.mName;
        this.mIndex = iVar.mIndex;
        this.W = iVar.W;
        this.X = iVar.X;
        this.Y = iVar.Y;
        this.Z = iVar.Z;
        this.aa = iVar.aa;
        this.ab = iVar.ab;
        this.ac = iVar.ac;
    }

    public BackStackState(Parcel parcel) {
        this.aj = parcel.createIntArray();
        this.R = parcel.readInt();
        this.S = parcel.readInt();
        this.mName = parcel.readString();
        this.mIndex = parcel.readInt();
        this.W = parcel.readInt();
        this.X = (CharSequence) TextUtils.CHAR_SEQUENCE_CREATOR.createFromParcel(parcel);
        this.Y = parcel.readInt();
        this.Z = (CharSequence) TextUtils.CHAR_SEQUENCE_CREATOR.createFromParcel(parcel);
        this.aa = parcel.createStringArrayList();
        this.ab = parcel.createStringArrayList();
        this.ac = parcel.readInt() != 0;
    }

    public i a(ad adVar) {
        i iVar = new i(adVar);
        int i = 0;
        int i2 = 0;
        while (i2 < this.aj.length) {
            j jVar = new j();
            int i3 = i2 + 1;
            jVar.ae = this.aj[i2];
            if (ad.DEBUG) {
                Log.v("FragmentManager", "Instantiate " + iVar + " op #" + i + " base fragment #" + this.aj[i3]);
            }
            int i4 = i3 + 1;
            int i5 = this.aj[i3];
            if (i5 >= 0) {
                jVar.fragment = adVar.aS.get(i5);
            } else {
                jVar.fragment = null;
            }
            int i6 = i4 + 1;
            jVar.af = this.aj[i4];
            int i7 = i6 + 1;
            jVar.ag = this.aj[i6];
            int i8 = i7 + 1;
            jVar.ah = this.aj[i7];
            i2 = i8 + 1;
            jVar.ai = this.aj[i8];
            iVar.N = jVar.af;
            iVar.O = jVar.ag;
            iVar.P = jVar.ah;
            iVar.Q = jVar.ai;
            iVar.a(jVar);
            i++;
        }
        iVar.R = this.R;
        iVar.S = this.S;
        iVar.mName = this.mName;
        iVar.mIndex = this.mIndex;
        iVar.T = true;
        iVar.W = this.W;
        iVar.X = this.X;
        iVar.Y = this.Y;
        iVar.Z = this.Z;
        iVar.aa = this.aa;
        iVar.ab = this.ab;
        iVar.ac = this.ac;
        iVar.a(1);
        return iVar;
    }

    @Override // android.os.Parcelable
    public int describeContents() {
        return 0;
    }

    @Override // android.os.Parcelable
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeIntArray(this.aj);
        parcel.writeInt(this.R);
        parcel.writeInt(this.S);
        parcel.writeString(this.mName);
        parcel.writeInt(this.mIndex);
        parcel.writeInt(this.W);
        TextUtils.writeToParcel(this.X, parcel, 0);
        parcel.writeInt(this.Y);
        TextUtils.writeToParcel(this.Z, parcel, 0);
        parcel.writeStringList(this.aa);
        parcel.writeStringList(this.ab);
        parcel.writeInt(this.ac ? 1 : 0);
    }
}
