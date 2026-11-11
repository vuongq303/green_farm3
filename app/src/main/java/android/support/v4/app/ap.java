package android.support.v4.app;

import java.util.ArrayList;

/* JADX INFO: compiled from: FragmentManager.java */
/* JADX INFO: loaded from: classes.dex */
class ap implements ao {
    final int bF;
    final /* synthetic */ ad bv;
    final int mId;
    final String mName;

    ap(ad adVar, String str, int i, int i2) {
        this.bv = adVar;
        this.mName = str;
        this.mId = i;
        this.bF = i2;
    }

    @Override // android.support.v4.app.ao
    public boolean a(ArrayList<i> arrayList, ArrayList<Boolean> arrayList2) {
        aa aaVarPeekChildFragmentManager;
        if (this.bv.bc == null || this.mId >= 0 || this.mName != null || (aaVarPeekChildFragmentManager = this.bv.bc.peekChildFragmentManager()) == null || !aaVarPeekChildFragmentManager.popBackStackImmediate()) {
            return this.bv.a(arrayList, arrayList2, this.mName, this.mId, this.bF);
        }
        return false;
    }
}
