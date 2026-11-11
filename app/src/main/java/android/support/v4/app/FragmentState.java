package android.support.v4.app;

import android.content.Context;
import android.os.Bundle;
import android.os.Parcel;
import android.os.Parcelable;
import android.util.Log;

/* JADX INFO: compiled from: Fragment.java */
/* JADX INFO: loaded from: classes.dex */
final class FragmentState implements Parcelable {
    public static final Parcelable.Creator<FragmentState> CREATOR = new at();
    final String bP;
    Fragment bQ;
    final Bundle mArguments;
    final int mContainerId;
    final boolean mDetached;
    final int mFragmentId;
    final boolean mFromLayout;
    final boolean mHidden;
    final int mIndex;
    final boolean mRetainInstance;
    Bundle mSavedFragmentState;
    final String mTag;

    public FragmentState(Fragment fragment) {
        this.bP = fragment.getClass().getName();
        this.mIndex = fragment.mIndex;
        this.mFromLayout = fragment.mFromLayout;
        this.mFragmentId = fragment.mFragmentId;
        this.mContainerId = fragment.mContainerId;
        this.mTag = fragment.mTag;
        this.mRetainInstance = fragment.mRetainInstance;
        this.mDetached = fragment.mDetached;
        this.mArguments = fragment.mArguments;
        this.mHidden = fragment.mHidden;
    }

    public FragmentState(Parcel parcel) {
        this.bP = parcel.readString();
        this.mIndex = parcel.readInt();
        this.mFromLayout = parcel.readInt() != 0;
        this.mFragmentId = parcel.readInt();
        this.mContainerId = parcel.readInt();
        this.mTag = parcel.readString();
        this.mRetainInstance = parcel.readInt() != 0;
        this.mDetached = parcel.readInt() != 0;
        this.mArguments = parcel.readBundle();
        this.mHidden = parcel.readInt() != 0;
        this.mSavedFragmentState = parcel.readBundle();
    }

    public Fragment a(z zVar, x xVar, Fragment fragment, ar arVar) {
        if (this.bQ == null) {
            Context context = zVar.getContext();
            if (this.mArguments != null) {
                this.mArguments.setClassLoader(context.getClassLoader());
            }
            if (xVar != null) {
                this.bQ = xVar.instantiate(context, this.bP, this.mArguments);
            } else {
                this.bQ = Fragment.instantiate(context, this.bP, this.mArguments);
            }
            if (this.mSavedFragmentState != null) {
                this.mSavedFragmentState.setClassLoader(context.getClassLoader());
                this.bQ.mSavedFragmentState = this.mSavedFragmentState;
            }
            this.bQ.setIndex(this.mIndex, fragment);
            this.bQ.mFromLayout = this.mFromLayout;
            this.bQ.mRestored = true;
            this.bQ.mFragmentId = this.mFragmentId;
            this.bQ.mContainerId = this.mContainerId;
            this.bQ.mTag = this.mTag;
            this.bQ.mRetainInstance = this.mRetainInstance;
            this.bQ.mDetached = this.mDetached;
            this.bQ.mHidden = this.mHidden;
            this.bQ.mFragmentManager = zVar.mFragmentManager;
            if (ad.DEBUG) {
                Log.v("FragmentManager", "Instantiated fragment " + this.bQ);
            }
        }
        this.bQ.mChildNonConfig = arVar;
        return this.bQ;
    }

    @Override // android.os.Parcelable
    public int describeContents() {
        return 0;
    }

    @Override // android.os.Parcelable
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeString(this.bP);
        parcel.writeInt(this.mIndex);
        parcel.writeInt(this.mFromLayout ? 1 : 0);
        parcel.writeInt(this.mFragmentId);
        parcel.writeInt(this.mContainerId);
        parcel.writeString(this.mTag);
        parcel.writeInt(this.mRetainInstance ? 1 : 0);
        parcel.writeInt(this.mDetached ? 1 : 0);
        parcel.writeBundle(this.mArguments);
        parcel.writeInt(this.mHidden ? 1 : 0);
        parcel.writeBundle(this.mSavedFragmentState);
    }
}
