package android.support.v7.app;

import android.content.Context;
import android.content.res.Configuration;
import android.support.v7.widget.bo;
import android.view.KeyCharacterMap;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.Window;
import java.util.ArrayList;

/* JADX INFO: compiled from: ToolbarActionBar.java */
/* JADX INFO: loaded from: classes.dex */
class bb extends a {
    bo mA;
    Window.Callback mB;
    private boolean mC;
    private boolean mD;
    private ArrayList<c> mE;
    private final Runnable mF;

    @Override // android.support.v7.app.a
    public void setHomeButtonEnabled(boolean z) {
    }

    @Override // android.support.v7.app.a
    public void setElevation(float f) {
        android.support.v4.view.aa.a(this.mA.eE(), f);
    }

    @Override // android.support.v7.app.a
    public Context getThemedContext() {
        return this.mA.getContext();
    }

    @Override // android.support.v7.app.a
    public void g(boolean z) {
    }

    @Override // android.support.v7.app.a
    public void h(boolean z) {
    }

    @Override // android.support.v7.app.a
    public void onConfigurationChanged(Configuration configuration) {
        super.onConfigurationChanged(configuration);
    }

    @Override // android.support.v7.app.a
    public void h(CharSequence charSequence) {
        this.mA.h(charSequence);
    }

    @Override // android.support.v7.app.a
    public int getDisplayOptions() {
        return this.mA.getDisplayOptions();
    }

    @Override // android.support.v7.app.a
    public boolean bo() {
        return this.mA.showOverflowMenu();
    }

    @Override // android.support.v7.app.a
    public boolean bp() {
        return this.mA.hideOverflowMenu();
    }

    @Override // android.support.v7.app.a
    public boolean bq() {
        this.mA.eE().removeCallbacks(this.mF);
        android.support.v4.view.aa.b(this.mA.eE(), this.mF);
        return true;
    }

    @Override // android.support.v7.app.a
    public boolean collapseActionView() {
        if (!this.mA.hasExpandedActionView()) {
            return false;
        }
        this.mA.collapseActionView();
        return true;
    }

    @Override // android.support.v7.app.a
    public boolean a(KeyEvent keyEvent) {
        if (keyEvent.getAction() == 1) {
            bo();
        }
        return true;
    }

    @Override // android.support.v7.app.a
    public boolean onKeyShortcut(int i, KeyEvent keyEvent) {
        Menu menu = getMenu();
        if (menu == null) {
            return false;
        }
        menu.setQwertyMode(KeyCharacterMap.load(keyEvent != null ? keyEvent.getDeviceId() : -1).getKeyboardType() != 1);
        return menu.performShortcut(i, keyEvent, 0);
    }

    @Override // android.support.v7.app.a
    void onDestroy() {
        this.mA.eE().removeCallbacks(this.mF);
    }

    @Override // android.support.v7.app.a
    public void i(boolean z) {
        if (z != this.mD) {
            this.mD = z;
            int size = this.mE.size();
            for (int i = 0; i < size; i++) {
                this.mE.get(i).onMenuVisibilityChanged(z);
            }
        }
    }

    private Menu getMenu() {
        if (!this.mC) {
            this.mA.a(new bc(this), new bd(this));
            this.mC = true;
        }
        return this.mA.getMenu();
    }
}
