package android.support.v4.app;

import android.view.View;
import java.util.ArrayList;
import java.util.Map;

/* JADX INFO: compiled from: FragmentTransitionCompat21.java */
/* JADX INFO: loaded from: classes.dex */
class bi implements Runnable {
    private final /* synthetic */ ArrayList bW;
    private final /* synthetic */ Map ct;

    bi(ArrayList arrayList, Map map) {
        this.bW = arrayList;
        this.ct = map;
    }

    @Override // java.lang.Runnable
    public void run() {
        int size = this.bW.size();
        for (int i = 0; i < size; i++) {
            View view = (View) this.bW.get(i);
            view.setTransitionName((String) this.ct.get(view.getTransitionName()));
        }
    }
}
