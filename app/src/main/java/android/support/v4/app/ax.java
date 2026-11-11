package android.support.v4.app;

import android.view.View;
import java.util.ArrayList;

/* JADX INFO: compiled from: FragmentTransition.java */
/* JADX INFO: loaded from: classes.dex */
class ax implements Runnable {
    private final /* synthetic */ ArrayList bS;
    private final /* synthetic */ Object bT;
    private final /* synthetic */ View bU;
    private final /* synthetic */ Fragment bV;
    private final /* synthetic */ ArrayList bW;
    private final /* synthetic */ ArrayList bX;
    private final /* synthetic */ Object bY;

    ax(Object obj, View view, Fragment fragment, ArrayList arrayList, ArrayList arrayList2, ArrayList arrayList3, Object obj2) {
        this.bT = obj;
        this.bU = view;
        this.bV = fragment;
        this.bW = arrayList;
        this.bX = arrayList2;
        this.bS = arrayList3;
        this.bY = obj2;
    }

    @Override // java.lang.Runnable
    public void run() {
        if (this.bT != null) {
            bb.c(this.bT, this.bU);
            this.bX.addAll(av.a(this.bT, this.bV, (ArrayList<View>) this.bW, this.bU));
        }
        if (this.bS != null) {
            if (this.bY != null) {
                ArrayList arrayList = new ArrayList();
                arrayList.add(this.bU);
                bb.b(this.bY, (ArrayList<View>) this.bS, (ArrayList<View>) arrayList);
            }
            this.bS.clear();
            this.bS.add(this.bU);
        }
    }
}
