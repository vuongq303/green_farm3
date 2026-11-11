package android.support.v7.app;

import android.content.Context;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import java.lang.ref.WeakReference;

/* JADX INFO: compiled from: WindowDecorActionBar.java */
/* JADX INFO: loaded from: classes.dex */
public class bl extends android.support.v7.c.b implements android.support.v7.c.a.t {
    final /* synthetic */ bh nu;
    private final Context nv;
    private final android.support.v7.c.a.s nw;
    private android.support.v7.c.c nx;
    private WeakReference<View> ny;

    public bl(bh bhVar, Context context, android.support.v7.c.c cVar) {
        this.nu = bhVar;
        this.nv = context;
        this.nx = cVar;
        this.nw = new android.support.v7.c.a.s(context).ad(1);
        this.nw.a(this);
    }

    @Override // android.support.v7.c.b
    public MenuInflater getMenuInflater() {
        return new android.support.v7.c.i(this.nv);
    }

    @Override // android.support.v7.c.b
    public Menu getMenu() {
        return this.nw;
    }

    @Override // android.support.v7.c.b
    public void finish() {
        if (this.nu.ne == this) {
            if (!bh.a(this.nu.nk, this.nu.nl, false)) {
                this.nu.nf = this;
                this.nu.ng = this.nx;
            } else {
                this.nx.c(this);
            }
            this.nx = null;
            this.nu.o(false);
            this.nu.mY.dG();
            this.nu.mA.eE().sendAccessibilityEvent(32);
            this.nu.mW.setHideOnContentScrollEnabled(this.nu.nq);
            this.nu.ne = null;
        }
    }

    @Override // android.support.v7.c.b
    public void invalidate() {
        if (this.nu.ne == this) {
            this.nw.db();
            try {
                this.nx.b(this, this.nw);
            } finally {
                this.nw.dc();
            }
        }
    }

    public boolean cj() {
        this.nw.db();
        try {
            return this.nx.a(this, this.nw);
        } finally {
            this.nw.dc();
        }
    }

    @Override // android.support.v7.c.b
    public void setCustomView(View view) {
        this.nu.mY.setCustomView(view);
        this.ny = new WeakReference<>(view);
    }

    @Override // android.support.v7.c.b
    public void setSubtitle(CharSequence charSequence) {
        this.nu.mY.setSubtitle(charSequence);
    }

    @Override // android.support.v7.c.b
    public void setTitle(CharSequence charSequence) {
        this.nu.mY.setTitle(charSequence);
    }

    @Override // android.support.v7.c.b
    public void setTitle(int i) {
        setTitle(this.nu.mContext.getResources().getString(i));
    }

    @Override // android.support.v7.c.b
    public void setSubtitle(int i) {
        setSubtitle(this.nu.mContext.getResources().getString(i));
    }

    @Override // android.support.v7.c.b
    public CharSequence getTitle() {
        return this.nu.mY.getTitle();
    }

    @Override // android.support.v7.c.b
    public CharSequence getSubtitle() {
        return this.nu.mY.getSubtitle();
    }

    @Override // android.support.v7.c.b
    public void setTitleOptionalHint(boolean z) {
        super.setTitleOptionalHint(z);
        this.nu.mY.D(z);
    }

    @Override // android.support.v7.c.b
    public boolean isTitleOptional() {
        return this.nu.mY.isTitleOptional();
    }

    @Override // android.support.v7.c.b
    public View getCustomView() {
        if (this.ny != null) {
            return this.ny.get();
        }
        return null;
    }

    @Override // android.support.v7.c.a.t
    public boolean a(android.support.v7.c.a.s sVar, MenuItem menuItem) {
        if (this.nx != null) {
            return this.nx.a(this, menuItem);
        }
        return false;
    }

    @Override // android.support.v7.c.a.t
    public void a(android.support.v7.c.a.s sVar) {
        if (this.nx != null) {
            invalidate();
            this.nu.mY.showOverflowMenu();
        }
    }
}
