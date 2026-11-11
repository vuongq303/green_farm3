package android.support.v7.app;

import android.content.Intent;
import android.content.res.Configuration;
import android.content.res.Resources;
import android.os.Build;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.bn;
import android.support.v4.app.dq;
import android.support.v4.app.dr;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;

/* JADX INFO: loaded from: classes.dex */
public class AppCompatActivity extends FragmentActivity implements dr, v {
    private w kJ;
    private int kK = 0;
    private Resources kL;

    @Override // android.support.v4.app.FragmentActivity, android.app.Activity
    protected void onCreate(Bundle bundle) {
        w wVarBy = by();
        wVarBy.bz();
        wVarBy.onCreate(bundle);
        if (wVarBy.bA() && this.kK != 0) {
            if (Build.VERSION.SDK_INT >= 23) {
                onApplyThemeResource(getTheme(), this.kK, false);
            } else {
                setTheme(this.kK);
            }
        }
        super.onCreate(bundle);
    }

    @Override // android.app.Activity, android.view.ContextThemeWrapper, android.content.ContextWrapper, android.content.Context
    public void setTheme(int i) {
        super.setTheme(i);
        this.kK = i;
    }

    @Override // android.app.Activity
    protected void onPostCreate(Bundle bundle) {
        super.onPostCreate(bundle);
        by().onPostCreate(bundle);
    }

    public a bv() {
        return by().bv();
    }

    @Override // android.app.Activity
    public MenuInflater getMenuInflater() {
        return by().getMenuInflater();
    }

    @Override // android.app.Activity
    public void setContentView(int i) {
        by().setContentView(i);
    }

    @Override // android.app.Activity
    public void setContentView(View view) {
        by().setContentView(view);
    }

    @Override // android.app.Activity
    public void setContentView(View view, ViewGroup.LayoutParams layoutParams) {
        by().setContentView(view, layoutParams);
    }

    @Override // android.app.Activity
    public void addContentView(View view, ViewGroup.LayoutParams layoutParams) {
        by().addContentView(view, layoutParams);
    }

    @Override // android.support.v4.app.FragmentActivity, android.app.Activity, android.content.ComponentCallbacks
    public void onConfigurationChanged(Configuration configuration) {
        super.onConfigurationChanged(configuration);
        by().onConfigurationChanged(configuration);
        if (this.kL != null) {
            this.kL.updateConfiguration(configuration, super.getResources().getDisplayMetrics());
        }
    }

    @Override // android.support.v4.app.FragmentActivity, android.app.Activity
    protected void onPostResume() {
        super.onPostResume();
        by().onPostResume();
    }

    @Override // android.support.v4.app.FragmentActivity, android.app.Activity
    protected void onStart() {
        super.onStart();
        by().onStart();
    }

    @Override // android.support.v4.app.FragmentActivity, android.app.Activity
    protected void onStop() {
        super.onStop();
        by().onStop();
    }

    @Override // android.app.Activity
    public <T extends View> T findViewById(int i) {
        return (T) by().findViewById(i);
    }

    @Override // android.support.v4.app.FragmentActivity, android.app.Activity, android.view.Window.Callback
    public final boolean onMenuItemSelected(int i, MenuItem menuItem) {
        if (super.onMenuItemSelected(i, menuItem)) {
            return true;
        }
        a aVarBv = bv();
        if (menuItem.getItemId() == 16908332 && aVarBv != null && (aVarBv.getDisplayOptions() & 4) != 0) {
            return bw();
        }
        return false;
    }

    @Override // android.support.v4.app.FragmentActivity, android.app.Activity
    protected void onDestroy() {
        super.onDestroy();
        by().onDestroy();
    }

    @Override // android.app.Activity
    protected void onTitleChanged(CharSequence charSequence, int i) {
        super.onTitleChanged(charSequence, i);
        by().setTitle(charSequence);
    }

    @Override // android.support.v4.app.FragmentActivity
    public void supportInvalidateOptionsMenu() {
        by().invalidateOptionsMenu();
    }

    @Override // android.app.Activity
    public void invalidateOptionsMenu() {
        by().invalidateOptionsMenu();
    }

    @Override // android.support.v7.app.v
    public void a(android.support.v7.c.b bVar) {
    }

    @Override // android.support.v7.app.v
    public void b(android.support.v7.c.b bVar) {
    }

    @Override // android.support.v7.app.v
    public android.support.v7.c.b b(android.support.v7.c.c cVar) {
        return null;
    }

    public void a(dq dqVar) {
        dqVar.h(this);
    }

    public void b(dq dqVar) {
    }

    public boolean bw() {
        Intent intentAi = ai();
        if (intentAi != null) {
            if (c(intentAi)) {
                dq dqVarD = dq.d(this);
                a(dqVarD);
                b(dqVarD);
                dqVarD.startActivities();
                try {
                    android.support.v4.app.a.a(this);
                } catch (IllegalStateException e) {
                    finish();
                }
            } else {
                d(intentAi);
            }
            return true;
        }
        return false;
    }

    @Override // android.support.v4.app.dr
    public Intent ai() {
        return bn.f(this);
    }

    public boolean c(Intent intent) {
        return bn.a(this, intent);
    }

    public void d(Intent intent) {
        bn.b(this, intent);
    }

    @Override // android.app.Activity, android.view.Window.Callback
    public void onContentChanged() {
        bx();
    }

    @Deprecated
    public void bx() {
    }

    @Override // android.app.Activity, android.view.Window.Callback
    public boolean onMenuOpened(int i, Menu menu) {
        return super.onMenuOpened(i, menu);
    }

    @Override // android.support.v4.app.FragmentActivity, android.app.Activity, android.view.Window.Callback
    public void onPanelClosed(int i, Menu menu) {
        super.onPanelClosed(i, menu);
    }

    @Override // android.support.v4.app.FragmentActivity, android.app.Activity
    protected void onSaveInstanceState(Bundle bundle) {
        super.onSaveInstanceState(bundle);
        by().onSaveInstanceState(bundle);
    }

    public w by() {
        if (this.kJ == null) {
            this.kJ = w.a(this, this);
        }
        return this.kJ;
    }

    @Override // android.app.Activity, android.view.Window.Callback
    public boolean dispatchKeyEvent(KeyEvent keyEvent) {
        int keyCode = keyEvent.getKeyCode();
        a aVarBv = bv();
        if (keyCode == 82 && aVarBv != null && aVarBv.a(keyEvent)) {
            return true;
        }
        return super.dispatchKeyEvent(keyEvent);
    }

    @Override // android.view.ContextThemeWrapper, android.content.ContextWrapper, android.content.Context
    public Resources getResources() {
        if (this.kL == null && android.support.v7.widget.dr.fp()) {
            this.kL = new android.support.v7.widget.dr(this, super.getResources());
        }
        return this.kL == null ? super.getResources() : this.kL;
    }

    private boolean a(int i, KeyEvent keyEvent) {
        Window window;
        return (Build.VERSION.SDK_INT >= 26 || keyEvent.isCtrlPressed() || KeyEvent.metaStateHasNoModifiers(keyEvent.getMetaState()) || keyEvent.getRepeatCount() != 0 || KeyEvent.isModifierKey(keyEvent.getKeyCode()) || (window = getWindow()) == null || window.getDecorView() == null || !window.getDecorView().dispatchKeyShortcutEvent(keyEvent)) ? false : true;
    }

    @Override // android.app.Activity, android.view.KeyEvent.Callback
    public boolean onKeyDown(int i, KeyEvent keyEvent) {
        if (a(i, keyEvent)) {
            return true;
        }
        return super.onKeyDown(i, keyEvent);
    }

    @Override // android.app.Activity
    public void openOptionsMenu() {
        a aVarBv = bv();
        if (getWindow().hasFeature(0)) {
            if (aVarBv == null || !aVarBv.bo()) {
                super.openOptionsMenu();
            }
        }
    }

    @Override // android.app.Activity
    public void closeOptionsMenu() {
        a aVarBv = bv();
        if (getWindow().hasFeature(0)) {
            if (aVarBv == null || !aVarBv.bp()) {
                super.closeOptionsMenu();
            }
        }
    }
}
