package android.support.v7.c;

import android.content.Context;
import android.support.v7.c.a.s;
import android.support.v7.c.a.t;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import java.lang.ref.WeakReference;

/* JADX INFO: compiled from: StandaloneActionMode.java */
/* JADX INFO: loaded from: classes.dex */
public class f extends b implements t {
    private Context mContext;
    private android.support.v7.widget.d mY;
    private boolean nJ;
    private boolean nK;
    private s nw;
    private c nx;
    private WeakReference<View> ny;

    public f(Context context, android.support.v7.widget.d dVar, c cVar, boolean z) {
        this.mContext = context;
        this.mY = dVar;
        this.nx = cVar;
        this.nw = new s(dVar.getContext()).ad(1);
        this.nw.a(this);
        this.nK = z;
    }

    @Override // android.support.v7.c.b
    public void setTitle(CharSequence charSequence) {
        this.mY.setTitle(charSequence);
    }

    @Override // android.support.v7.c.b
    public void setSubtitle(CharSequence charSequence) {
        this.mY.setSubtitle(charSequence);
    }

    @Override // android.support.v7.c.b
    public void setTitle(int i) {
        setTitle(this.mContext.getString(i));
    }

    @Override // android.support.v7.c.b
    public void setSubtitle(int i) {
        setSubtitle(this.mContext.getString(i));
    }

    @Override // android.support.v7.c.b
    public void setTitleOptionalHint(boolean z) {
        super.setTitleOptionalHint(z);
        this.mY.D(z);
    }

    @Override // android.support.v7.c.b
    public boolean isTitleOptional() {
        return this.mY.isTitleOptional();
    }

    @Override // android.support.v7.c.b
    public void setCustomView(View view) {
        this.mY.setCustomView(view);
        this.ny = view != null ? new WeakReference<>(view) : null;
    }

    @Override // android.support.v7.c.b
    public void invalidate() {
        this.nx.b(this, this.nw);
    }

    @Override // android.support.v7.c.b
    public void finish() {
        if (!this.nJ) {
            this.nJ = true;
            this.mY.sendAccessibilityEvent(32);
            this.nx.c(this);
        }
    }

    @Override // android.support.v7.c.b
    public Menu getMenu() {
        return this.nw;
    }

    @Override // android.support.v7.c.b
    public CharSequence getTitle() {
        return this.mY.getTitle();
    }

    @Override // android.support.v7.c.b
    public CharSequence getSubtitle() {
        return this.mY.getSubtitle();
    }

    @Override // android.support.v7.c.b
    public View getCustomView() {
        if (this.ny != null) {
            return this.ny.get();
        }
        return null;
    }

    @Override // android.support.v7.c.b
    public MenuInflater getMenuInflater() {
        return new i(this.mY.getContext());
    }

    @Override // android.support.v7.c.a.t
    public boolean a(s sVar, MenuItem menuItem) {
        return this.nx.a(this, menuItem);
    }

    @Override // android.support.v7.c.a.t
    public void a(s sVar) {
        invalidate();
        this.mY.showOverflowMenu();
    }
}
