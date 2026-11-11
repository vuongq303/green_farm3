package android.support.v4.app;

import android.view.View;
import java.util.ArrayList;

/* JADX INFO: compiled from: FragmentTransition.java */
/* JADX INFO: loaded from: classes.dex */
class aw implements Runnable {
    private final /* synthetic */ ArrayList bS;

    aw(ArrayList arrayList) {
        this.bS = arrayList;
    }

    @Override // java.lang.Runnable
    public void run() {
        av.a((ArrayList<View>) this.bS, 4);
    }
}
