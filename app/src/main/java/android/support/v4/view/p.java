package android.support.v4.view;

import android.content.res.ColorStateList;
import android.graphics.PorterDuff;
import android.view.MenuItem;

/* JADX INFO: compiled from: MenuItemCompat.java */
/* JADX INFO: loaded from: classes.dex */
class p extends q {
    p() {
    }

    @Override // android.support.v4.view.q, android.support.v4.view.r
    public void a(MenuItem menuItem, CharSequence charSequence) {
        menuItem.setContentDescription(charSequence);
    }

    @Override // android.support.v4.view.q, android.support.v4.view.r
    public void b(MenuItem menuItem, CharSequence charSequence) {
        menuItem.setTooltipText(charSequence);
    }

    @Override // android.support.v4.view.q, android.support.v4.view.r
    public void b(MenuItem menuItem, char c, int i) {
        menuItem.setAlphabeticShortcut(c, i);
    }

    @Override // android.support.v4.view.q, android.support.v4.view.r
    public void a(MenuItem menuItem, char c, int i) {
        menuItem.setNumericShortcut(c, i);
    }

    @Override // android.support.v4.view.q, android.support.v4.view.r
    public void a(MenuItem menuItem, ColorStateList colorStateList) {
        menuItem.setIconTintList(colorStateList);
    }

    @Override // android.support.v4.view.q, android.support.v4.view.r
    public void a(MenuItem menuItem, PorterDuff.Mode mode) {
        menuItem.setIconTintMode(mode);
    }
}
