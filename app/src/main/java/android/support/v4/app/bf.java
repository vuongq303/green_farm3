package android.support.v4.app;

import android.view.View;
import java.util.ArrayList;
import java.util.Map;

/* JADX INFO: compiled from: FragmentTransitionCompat21.java */
/* JADX INFO: loaded from: classes.dex */
class bf implements Runnable {
    private final /* synthetic */ ArrayList bW;
    private final /* synthetic */ Map ct;

    bf(ArrayList arrayList, Map map) {
        this.bW = arrayList;
        this.ct = map;
    }

    @Override // java.lang.Runnable
    public void run() {
        int size = this.bW.size();
        for (int i = 0; i < size; i++) {
            View view = (View) this.bW.get(i);
            String transitionName = view.getTransitionName();
            if (transitionName != null) {
                view.setTransitionName(bb.a((Map<String, String>) this.ct, transitionName));
            }
        }
    }
}
