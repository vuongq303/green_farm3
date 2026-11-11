package android.support.v7.c;

import android.content.Context;
import android.support.v7.c.a.am;
import android.view.ActionMode;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.View;

/* JADX INFO: compiled from: SupportActionModeWrapper.java */
/* JADX INFO: loaded from: classes.dex */
public class g extends ActionMode {
    final Context mContext;
    final b nL;

    public g(Context context, b bVar) {
        this.mContext = context;
        this.nL = bVar;
    }

    @Override // android.view.ActionMode
    public Object getTag() {
        return this.nL.getTag();
    }

    @Override // android.view.ActionMode
    public void setTag(Object obj) {
        this.nL.setTag(obj);
    }

    @Override // android.view.ActionMode
    public void setTitle(CharSequence charSequence) {
        this.nL.setTitle(charSequence);
    }

    @Override // android.view.ActionMode
    public void setSubtitle(CharSequence charSequence) {
        this.nL.setSubtitle(charSequence);
    }

    @Override // android.view.ActionMode
    public void invalidate() {
        this.nL.invalidate();
    }

    @Override // android.view.ActionMode
    public void finish() {
        this.nL.finish();
    }

    @Override // android.view.ActionMode
    public Menu getMenu() {
        return am.a(this.mContext, (android.support.v4.b.a.a) this.nL.getMenu());
    }

    @Override // android.view.ActionMode
    public CharSequence getTitle() {
        return this.nL.getTitle();
    }

    @Override // android.view.ActionMode
    public void setTitle(int i) {
        this.nL.setTitle(i);
    }

    @Override // android.view.ActionMode
    public CharSequence getSubtitle() {
        return this.nL.getSubtitle();
    }

    @Override // android.view.ActionMode
    public void setSubtitle(int i) {
        this.nL.setSubtitle(i);
    }

    @Override // android.view.ActionMode
    public View getCustomView() {
        return this.nL.getCustomView();
    }

    @Override // android.view.ActionMode
    public void setCustomView(View view) {
        this.nL.setCustomView(view);
    }

    @Override // android.view.ActionMode
    public MenuInflater getMenuInflater() {
        return this.nL.getMenuInflater();
    }

    @Override // android.view.ActionMode
    public boolean getTitleOptionalHint() {
        return this.nL.getTitleOptionalHint();
    }

    @Override // android.view.ActionMode
    public void setTitleOptionalHint(boolean z) {
        this.nL.setTitleOptionalHint(z);
    }

    @Override // android.view.ActionMode
    public boolean isTitleOptional() {
        return this.nL.isTitleOptional();
    }
}
