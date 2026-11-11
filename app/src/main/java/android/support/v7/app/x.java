package android.support.v7.app;

import android.R;
import android.app.Activity;
import android.content.Context;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.os.Bundle;
import android.support.v7.widget.da;
import android.util.AttributeSet;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.Window;

/* JADX INFO: compiled from: AppCompatDelegateImplBase.java */
/* JADX INFO: loaded from: classes.dex */
abstract class x extends w {
    private static boolean kO;
    private static final boolean kP;
    private static final int[] kQ;
    final Window jo;
    private CharSequence jp;
    final Window.Callback kR;
    final Window.Callback kS;
    final v kT;
    a kU;
    MenuInflater kV;
    boolean kW;
    boolean kX;
    boolean kY;
    boolean kZ;
    boolean la;
    private boolean lb;
    private boolean lc;
    final Context mContext;

    abstract void bD();

    abstract android.support.v7.c.b c(android.support.v7.c.c cVar);

    abstract boolean dispatchKeyEvent(KeyEvent keyEvent);

    abstract void j(CharSequence charSequence);

    abstract boolean onKeyShortcut(int i, KeyEvent keyEvent);

    abstract boolean onMenuOpened(int i, Menu menu);

    abstract void onPanelClosed(int i, Menu menu);

    static {
        kP = Build.VERSION.SDK_INT < 21;
        if (kP && !kO) {
            Thread.setDefaultUncaughtExceptionHandler(new y(Thread.getDefaultUncaughtExceptionHandler()));
            kO = true;
        }
        kQ = new int[]{R.attr.windowBackground};
    }

    x(Context context, Window window, v vVar) {
        this.mContext = context;
        this.jo = window;
        this.kT = vVar;
        this.kR = this.jo.getCallback();
        if (this.kR instanceof z) {
            throw new IllegalStateException("AppCompat has already installed itself into the Window");
        }
        this.kS = a(this.kR);
        this.jo.setCallback(this.kS);
        da daVarA = da.a(context, (AttributeSet) null, kQ);
        Drawable drawableAu = daVarA.au(0);
        if (drawableAu != null) {
            this.jo.setBackgroundDrawable(drawableAu);
        }
        daVarA.recycle();
    }

    Window.Callback a(Window.Callback callback) {
        return new z(this, callback);
    }

    @Override // android.support.v7.app.w
    public a bv() {
        bD();
        return this.kU;
    }

    final a bE() {
        return this.kU;
    }

    @Override // android.support.v7.app.w
    public MenuInflater getMenuInflater() {
        if (this.kV == null) {
            bD();
            this.kV = new android.support.v7.c.i(this.kU != null ? this.kU.getThemedContext() : this.mContext);
        }
        return this.kV;
    }

    final Context bF() {
        Context themedContext = null;
        a aVarBv = bv();
        if (aVarBv != null) {
            themedContext = aVarBv.getThemedContext();
        }
        if (themedContext == null) {
            return this.mContext;
        }
        return themedContext;
    }

    @Override // android.support.v7.app.w
    public void onStart() {
        this.lb = true;
    }

    @Override // android.support.v7.app.w
    public void onStop() {
        this.lb = false;
    }

    @Override // android.support.v7.app.w
    public void onDestroy() {
        this.lc = true;
    }

    public boolean bG() {
        return false;
    }

    @Override // android.support.v7.app.w
    public boolean bA() {
        return false;
    }

    final boolean isDestroyed() {
        return this.lc;
    }

    final Window.Callback bH() {
        return this.jo.getCallback();
    }

    @Override // android.support.v7.app.w
    public final void setTitle(CharSequence charSequence) {
        this.jp = charSequence;
        j(charSequence);
    }

    @Override // android.support.v7.app.w
    public void onSaveInstanceState(Bundle bundle) {
    }

    final CharSequence getTitle() {
        return this.kR instanceof Activity ? ((Activity) this.kR).getTitle() : this.jp;
    }
}
