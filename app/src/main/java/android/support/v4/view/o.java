package android.support.v4.view;

import android.content.res.ColorStateList;
import android.graphics.PorterDuff;
import android.util.Log;
import android.view.MenuItem;

/* JADX INFO: compiled from: MenuItemCompat.java */
/* JADX INFO: loaded from: classes.dex */
public final class o {
    static final r hk;

    static {
        if (android.support.v4.c.a.ax()) {
            hk = new p();
        } else {
            hk = new q();
        }
    }

    public static MenuItem a(MenuItem menuItem, g gVar) {
        if (menuItem instanceof android.support.v4.b.a.b) {
            return ((android.support.v4.b.a.b) menuItem).a(gVar);
        }
        Log.w("MenuItemCompat", "setActionProvider: item does not implement SupportMenuItem; ignoring");
        return menuItem;
    }

    public static void a(MenuItem menuItem, CharSequence charSequence) {
        if (menuItem instanceof android.support.v4.b.a.b) {
            ((android.support.v4.b.a.b) menuItem).setContentDescription(charSequence);
        } else {
            hk.a(menuItem, charSequence);
        }
    }

    public static void b(MenuItem menuItem, CharSequence charSequence) {
        if (menuItem instanceof android.support.v4.b.a.b) {
            ((android.support.v4.b.a.b) menuItem).setTooltipText(charSequence);
        } else {
            hk.b(menuItem, charSequence);
        }
    }

    public static void a(MenuItem menuItem, char c, int i) {
        if (menuItem instanceof android.support.v4.b.a.b) {
            ((android.support.v4.b.a.b) menuItem).setNumericShortcut(c, i);
        } else {
            hk.a(menuItem, c, i);
        }
    }

    public static void b(MenuItem menuItem, char c, int i) {
        if (menuItem instanceof android.support.v4.b.a.b) {
            ((android.support.v4.b.a.b) menuItem).setAlphabeticShortcut(c, i);
        } else {
            hk.b(menuItem, c, i);
        }
    }

    public static void a(MenuItem menuItem, ColorStateList colorStateList) {
        if (menuItem instanceof android.support.v4.b.a.b) {
            ((android.support.v4.b.a.b) menuItem).setIconTintList(colorStateList);
        } else {
            hk.a(menuItem, colorStateList);
        }
    }

    public static void a(MenuItem menuItem, PorterDuff.Mode mode) {
        if (menuItem instanceof android.support.v4.b.a.b) {
            ((android.support.v4.b.a.b) menuItem).setIconTintMode(mode);
        } else {
            hk.a(menuItem, mode);
        }
    }
}
