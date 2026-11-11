package android.support.v4.app;

import android.transition.Transition;
import android.view.View;
import java.util.ArrayList;

/* JADX INFO: compiled from: FragmentTransitionCompat21.java */
/* JADX INFO: loaded from: classes.dex */
class bg implements Transition.TransitionListener {
    private final /* synthetic */ ArrayList bS;
    private final /* synthetic */ Object bT;
    private final /* synthetic */ ArrayList bW;
    private final /* synthetic */ ArrayList bX;
    private final /* synthetic */ Object bY;
    private final /* synthetic */ Object cu;

    bg(Object obj, ArrayList arrayList, Object obj2, ArrayList arrayList2, Object obj3, ArrayList arrayList3) {
        this.bT = obj;
        this.bX = arrayList;
        this.bY = obj2;
        this.bS = arrayList2;
        this.cu = obj3;
        this.bW = arrayList3;
    }

    @Override // android.transition.Transition.TransitionListener
    public void onTransitionStart(Transition transition) {
        if (this.bT != null) {
            bb.b(this.bT, (ArrayList<View>) this.bX, (ArrayList<View>) null);
        }
        if (this.bY != null) {
            bb.b(this.bY, (ArrayList<View>) this.bS, (ArrayList<View>) null);
        }
        if (this.cu != null) {
            bb.b(this.cu, (ArrayList<View>) this.bW, (ArrayList<View>) null);
        }
    }

    @Override // android.transition.Transition.TransitionListener
    public void onTransitionEnd(Transition transition) {
    }

    @Override // android.transition.Transition.TransitionListener
    public void onTransitionCancel(Transition transition) {
    }

    @Override // android.transition.Transition.TransitionListener
    public void onTransitionPause(Transition transition) {
    }

    @Override // android.transition.Transition.TransitionListener
    public void onTransitionResume(Transition transition) {
    }
}
