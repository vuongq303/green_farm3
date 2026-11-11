package android.support.v4.app;

import android.graphics.Rect;
import android.view.View;
import java.util.ArrayList;

/* JADX INFO: compiled from: FragmentTransition.java */
/* JADX INFO: loaded from: classes.dex */
class az implements Runnable {
    private final /* synthetic */ Object bT;
    private final /* synthetic */ View bU;
    private final /* synthetic */ Fragment bV;
    private final /* synthetic */ ArrayList bW;
    private final /* synthetic */ Fragment bZ;
    private final /* synthetic */ boolean ca;
    private final /* synthetic */ android.support.v4.e.a ce;
    private final /* synthetic */ Object cf;
    private final /* synthetic */ ba cg;
    private final /* synthetic */ ArrayList ch;
    private final /* synthetic */ Rect ci;

    az(android.support.v4.e.a aVar, Object obj, ba baVar, ArrayList arrayList, View view, Fragment fragment, Fragment fragment2, boolean z, ArrayList arrayList2, Object obj2, Rect rect) {
        this.ce = aVar;
        this.cf = obj;
        this.cg = baVar;
        this.bW = arrayList;
        this.bU = view;
        this.bV = fragment;
        this.bZ = fragment2;
        this.ca = z;
        this.ch = arrayList2;
        this.bT = obj2;
        this.ci = rect;
    }

    @Override // java.lang.Runnable
    public void run() {
        android.support.v4.e.a aVarB = av.b((android.support.v4.e.a<String, String>) this.ce, this.cf, this.cg);
        if (aVarB != null) {
            this.bW.addAll(aVarB.values());
            this.bW.add(this.bU);
        }
        av.a(this.bV, this.bZ, this.ca, (android.support.v4.e.a<String, View>) aVarB, false);
        if (this.cf != null) {
            bb.a(this.cf, (ArrayList<View>) this.ch, (ArrayList<View>) this.bW);
            View viewA = av.a((android.support.v4.e.a<String, View>) aVarB, this.cg, this.bT, this.ca);
            if (viewA != null) {
                bb.a(viewA, this.ci);
            }
        }
    }
}
