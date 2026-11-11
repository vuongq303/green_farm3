package android.support.v7.app;

import android.content.Context;
import android.content.res.Configuration;
import android.view.KeyEvent;

/* JADX INFO: compiled from: ActionBar.java */
/* JADX INFO: loaded from: classes.dex */
public abstract class a {
    public abstract int getDisplayOptions();

    public void setHomeButtonEnabled(boolean z) {
    }

    public Context getThemedContext() {
        return null;
    }

    public void setHideOnContentScrollEnabled(boolean z) {
        if (z) {
            throw new UnsupportedOperationException("Hide on content scroll is not supported in this action bar configuration.");
        }
    }

    public void setElevation(float f) {
        if (f != 0.0f) {
            throw new UnsupportedOperationException("Setting a non-zero elevation is not supported in this action bar configuration.");
        }
    }

    public void g(boolean z) {
    }

    public void h(boolean z) {
    }

    public void onConfigurationChanged(Configuration configuration) {
    }

    public void i(boolean z) {
    }

    public android.support.v7.c.b a(android.support.v7.c.c cVar) {
        return null;
    }

    public boolean bo() {
        return false;
    }

    public boolean bp() {
        return false;
    }

    public boolean bq() {
        return false;
    }

    public boolean a(KeyEvent keyEvent) {
        return false;
    }

    public boolean onKeyShortcut(int i, KeyEvent keyEvent) {
        return false;
    }

    public boolean collapseActionView() {
        return false;
    }

    public void h(CharSequence charSequence) {
    }

    void onDestroy() {
    }
}
