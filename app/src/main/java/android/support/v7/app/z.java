package android.support.v7.app;

import android.view.KeyEvent;
import android.view.Menu;
import android.view.View;
import android.view.Window;

/* JADX INFO: compiled from: AppCompatDelegateImplBase.java */
/* JADX INFO: loaded from: classes.dex */
class z extends android.support.v7.c.n {
    final /* synthetic */ x le;

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    z(x xVar, Window.Callback callback) {
        super(callback);
        this.le = xVar;
    }

    @Override // android.support.v7.c.n, android.view.Window.Callback
    public boolean dispatchKeyEvent(KeyEvent keyEvent) {
        return this.le.dispatchKeyEvent(keyEvent) || super.dispatchKeyEvent(keyEvent);
    }

    @Override // android.support.v7.c.n, android.view.Window.Callback
    public boolean dispatchKeyShortcutEvent(KeyEvent keyEvent) {
        return super.dispatchKeyShortcutEvent(keyEvent) || this.le.onKeyShortcut(keyEvent.getKeyCode(), keyEvent);
    }

    @Override // android.support.v7.c.n, android.view.Window.Callback
    public boolean onCreatePanelMenu(int i, Menu menu) {
        if (i != 0 || (menu instanceof android.support.v7.c.a.s)) {
            return super.onCreatePanelMenu(i, menu);
        }
        return false;
    }

    @Override // android.support.v7.c.n, android.view.Window.Callback
    public void onContentChanged() {
    }

    @Override // android.support.v7.c.n, android.view.Window.Callback
    public boolean onPreparePanel(int i, View view, Menu menu) {
        android.support.v7.c.a.s sVar = menu instanceof android.support.v7.c.a.s ? (android.support.v7.c.a.s) menu : null;
        if (i == 0 && sVar == null) {
            return false;
        }
        if (sVar != null) {
            sVar.w(true);
        }
        boolean zOnPreparePanel = super.onPreparePanel(i, view, menu);
        if (sVar != null) {
            sVar.w(false);
            return zOnPreparePanel;
        }
        return zOnPreparePanel;
    }

    @Override // android.support.v7.c.n, android.view.Window.Callback
    public boolean onMenuOpened(int i, Menu menu) {
        super.onMenuOpened(i, menu);
        this.le.onMenuOpened(i, menu);
        return true;
    }

    @Override // android.support.v7.c.n, android.view.Window.Callback
    public void onPanelClosed(int i, Menu menu) {
        super.onPanelClosed(i, menu);
        this.le.onPanelClosed(i, menu);
    }
}
