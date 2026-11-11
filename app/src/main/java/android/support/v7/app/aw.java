package android.support.v7.app;

import android.view.Menu;
import android.view.Window;

/* JADX INFO: compiled from: AppCompatDelegateImplV9.java */
/* JADX INFO: loaded from: classes.dex */
final class aw implements android.support.v7.c.a.aj {
    final /* synthetic */ aj lR;

    aw(aj ajVar) {
        this.lR = ajVar;
    }

    @Override // android.support.v7.c.a.aj
    public void b(android.support.v7.c.a.s sVar, boolean z) {
        android.support.v7.c.a.s sVarDk = sVar.dk();
        boolean z2 = sVarDk != sVar;
        aj ajVar = this.lR;
        if (z2) {
            sVar = sVarDk;
        }
        av avVarA = ajVar.a((Menu) sVar);
        if (avVarA != null) {
            if (z2) {
                this.lR.a(avVarA.lV, avVarA, sVarDk);
                this.lR.a(avVarA, true);
            } else {
                this.lR.a(avVarA, z);
            }
        }
    }

    @Override // android.support.v7.c.a.aj
    public boolean c(android.support.v7.c.a.s sVar) {
        Window.Callback callbackBH;
        if (sVar == null && this.lR.kW && (callbackBH = this.lR.bH()) != null && !this.lR.isDestroyed()) {
            callbackBH.onMenuOpened(108, sVar);
            return true;
        }
        return true;
    }
}
