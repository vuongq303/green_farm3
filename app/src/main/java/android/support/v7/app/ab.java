package android.support.v7.app;

import android.view.KeyboardShortcutGroup;
import android.view.Menu;
import android.view.Window;
import java.util.List;

/* JADX INFO: compiled from: AppCompatDelegateImplN.java */
/* JADX INFO: loaded from: classes.dex */
class ab extends ai {
    final /* synthetic */ aa lf;

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    ab(aa aaVar, Window.Callback callback) {
        super(aaVar, callback);
        this.lf = aaVar;
    }

    @Override // android.support.v7.c.n, android.view.Window.Callback
    public void onProvideKeyboardShortcuts(List<KeyboardShortcutGroup> list, Menu menu, int i) {
        av avVarD = this.lf.d(0, true);
        if (avVarD != null && avVarD.lZ != null) {
            super.onProvideKeyboardShortcuts(list, avVarD.lZ, i);
        } else {
            super.onProvideKeyboardShortcuts(list, menu, i);
        }
    }
}
