package android.support.v7.app;

import android.content.Context;
import android.content.res.Resources;
import android.content.res.TypedArray;
import android.os.Bundle;
import android.support.v7.appcompat.R;
import android.util.TypedValue;
import android.view.View;
import android.view.ViewGroup;

/* JADX INFO: compiled from: AppCompatDelegateImplV9.java */
/* JADX INFO: loaded from: classes.dex */
public final class av {
    int background;
    int gravity;
    int lV;
    ViewGroup lW;
    View lX;
    View lY;
    android.support.v7.c.a.s lZ;
    android.support.v7.c.a.p ma;
    Context mb;
    boolean mc;
    boolean md;
    boolean me;
    public boolean mf;
    boolean mg = false;
    boolean mh;
    Bundle mi;
    int windowAnimations;
    int x;
    int y;

    av(int i) {
        this.lV = i;
    }

    public boolean bW() {
        if (this.lX == null) {
            return false;
        }
        return this.lY != null || this.ma.getAdapter().getCount() > 0;
    }

    void k(Context context) {
        TypedValue typedValue = new TypedValue();
        Resources.Theme themeNewTheme = context.getResources().newTheme();
        themeNewTheme.setTo(context.getTheme());
        themeNewTheme.resolveAttribute(R.attr.actionBarPopupTheme, typedValue, true);
        if (typedValue.resourceId != 0) {
            themeNewTheme.applyStyle(typedValue.resourceId, true);
        }
        themeNewTheme.resolveAttribute(R.attr.panelMenuListTheme, typedValue, true);
        if (typedValue.resourceId != 0) {
            themeNewTheme.applyStyle(typedValue.resourceId, true);
        } else {
            themeNewTheme.applyStyle(R.style.Theme_AppCompat_CompactMenu, true);
        }
        android.support.v7.c.e eVar = new android.support.v7.c.e(context, 0);
        eVar.getTheme().setTo(themeNewTheme);
        this.mb = eVar;
        TypedArray typedArrayObtainStyledAttributes = eVar.obtainStyledAttributes(R.styleable.AppCompatTheme);
        this.background = typedArrayObtainStyledAttributes.getResourceId(R.styleable.AppCompatTheme_panelBackground, 0);
        this.windowAnimations = typedArrayObtainStyledAttributes.getResourceId(R.styleable.AppCompatTheme_android_windowAnimationStyle, 0);
        typedArrayObtainStyledAttributes.recycle();
    }

    void d(android.support.v7.c.a.s sVar) {
        if (sVar != this.lZ) {
            if (this.lZ != null) {
                this.lZ.b(this.ma);
            }
            this.lZ = sVar;
            if (sVar == null || this.ma == null) {
                return;
            }
            sVar.a(this.ma);
        }
    }

    android.support.v7.c.a.ak a(android.support.v7.c.a.aj ajVar) {
        if (this.lZ == null) {
            return null;
        }
        if (this.ma == null) {
            this.ma = new android.support.v7.c.a.p(this.mb, R.layout.abc_list_menu_item_layout);
            this.ma.b(ajVar);
            this.lZ.a(this.ma);
        }
        return this.ma.g(this.lW);
    }
}
