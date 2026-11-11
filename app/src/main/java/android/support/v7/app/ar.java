package android.support.v7.app;

import android.view.Window;

/* JADX INFO: compiled from: AppCompatDelegateImplV9.java */
/* JADX INFO: loaded from: classes.dex */
final class ar implements android.support.v7.c.a.aj {
    final /* synthetic */ aj lR;

    ar(aj ajVar) {
        this.lR = ajVar;
    }

    @Override // android.support.v7.c.a.aj
    public boolean c(android.support.v7.c.a.s sVar) {
        Window.Callback callbackBH = this.lR.bH();
        if (callbackBH != null) {
            callbackBH.onMenuOpened(108, sVar);
            return true;
        }
        return true;
    }

    @Override // android.support.v7.c.a.aj
    public void b(android.support.v7.c.a.s sVar, boolean z) {
        this.lR.b(sVar);
    }
}
