package android.support.v7.c;

import android.content.res.ColorStateList;
import android.content.res.TypedArray;
import android.graphics.PorterDuff;
import android.support.v4.view.o;
import android.support.v7.appcompat.R;
import android.support.v7.c.a.w;
import android.support.v7.c.a.y;
import android.support.v7.widget.bp;
import android.util.AttributeSet;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.SubMenu;
import android.view.View;
import java.lang.reflect.Constructor;

/* JADX INFO: compiled from: SupportMenuInflater.java */
/* JADX INFO: loaded from: classes.dex */
class k {
    private Menu nW;
    private int nX;
    private int nY;
    private int nZ;
    final /* synthetic */ i oB;
    private int oa;
    private boolean ob;
    private boolean oc;
    private boolean od;
    private int oe;
    private int of;
    private CharSequence og;
    private CharSequence oh;
    private int oi;
    private char oj;
    private int ok;
    private char ol;
    private int om;
    private int on;
    private boolean oo;
    private boolean op;
    private boolean oq;
    private int or;
    private int os;
    private String ot;
    private String ou;
    private String ov;
    android.support.v4.view.g ow;
    private CharSequence ox;
    private CharSequence oy;
    private ColorStateList oz = null;
    private PorterDuff.Mode oA = null;

    public k(i iVar, Menu menu) {
        this.oB = iVar;
        this.nW = menu;
        cw();
    }

    public void cw() {
        this.nX = 0;
        this.nY = 0;
        this.nZ = 0;
        this.oa = 0;
        this.ob = true;
        this.oc = true;
    }

    public void a(AttributeSet attributeSet) {
        TypedArray typedArrayObtainStyledAttributes = this.oB.mContext.obtainStyledAttributes(attributeSet, R.styleable.MenuGroup);
        this.nX = typedArrayObtainStyledAttributes.getResourceId(R.styleable.MenuGroup_android_id, 0);
        this.nY = typedArrayObtainStyledAttributes.getInt(R.styleable.MenuGroup_android_menuCategory, 0);
        this.nZ = typedArrayObtainStyledAttributes.getInt(R.styleable.MenuGroup_android_orderInCategory, 0);
        this.oa = typedArrayObtainStyledAttributes.getInt(R.styleable.MenuGroup_android_checkableBehavior, 0);
        this.ob = typedArrayObtainStyledAttributes.getBoolean(R.styleable.MenuGroup_android_visible, true);
        this.oc = typedArrayObtainStyledAttributes.getBoolean(R.styleable.MenuGroup_android_enabled, true);
        typedArrayObtainStyledAttributes.recycle();
    }

    public void b(AttributeSet attributeSet) {
        TypedArray typedArrayObtainStyledAttributes = this.oB.mContext.obtainStyledAttributes(attributeSet, R.styleable.MenuItem);
        this.oe = typedArrayObtainStyledAttributes.getResourceId(R.styleable.MenuItem_android_id, 0);
        this.of = (typedArrayObtainStyledAttributes.getInt(R.styleable.MenuItem_android_menuCategory, this.nY) & (-65536)) | (typedArrayObtainStyledAttributes.getInt(R.styleable.MenuItem_android_orderInCategory, this.nZ) & 65535);
        this.og = typedArrayObtainStyledAttributes.getText(R.styleable.MenuItem_android_title);
        this.oh = typedArrayObtainStyledAttributes.getText(R.styleable.MenuItem_android_titleCondensed);
        this.oi = typedArrayObtainStyledAttributes.getResourceId(R.styleable.MenuItem_android_icon, 0);
        this.oj = l(typedArrayObtainStyledAttributes.getString(R.styleable.MenuItem_android_alphabeticShortcut));
        this.ok = typedArrayObtainStyledAttributes.getInt(R.styleable.MenuItem_alphabeticModifiers, 4096);
        this.ol = l(typedArrayObtainStyledAttributes.getString(R.styleable.MenuItem_android_numericShortcut));
        this.om = typedArrayObtainStyledAttributes.getInt(R.styleable.MenuItem_numericModifiers, 4096);
        if (typedArrayObtainStyledAttributes.hasValue(R.styleable.MenuItem_android_checkable)) {
            this.on = typedArrayObtainStyledAttributes.getBoolean(R.styleable.MenuItem_android_checkable, false) ? 1 : 0;
        } else {
            this.on = this.oa;
        }
        this.oo = typedArrayObtainStyledAttributes.getBoolean(R.styleable.MenuItem_android_checked, false);
        this.op = typedArrayObtainStyledAttributes.getBoolean(R.styleable.MenuItem_android_visible, this.ob);
        this.oq = typedArrayObtainStyledAttributes.getBoolean(R.styleable.MenuItem_android_enabled, this.oc);
        this.or = typedArrayObtainStyledAttributes.getInt(R.styleable.MenuItem_showAsAction, -1);
        this.ov = typedArrayObtainStyledAttributes.getString(R.styleable.MenuItem_android_onClick);
        this.os = typedArrayObtainStyledAttributes.getResourceId(R.styleable.MenuItem_actionLayout, 0);
        this.ot = typedArrayObtainStyledAttributes.getString(R.styleable.MenuItem_actionViewClass);
        this.ou = typedArrayObtainStyledAttributes.getString(R.styleable.MenuItem_actionProviderClass);
        boolean z = this.ou != null;
        if (z && this.os == 0 && this.ot == null) {
            this.ow = (android.support.v4.view.g) a(this.ou, i.nQ, this.oB.nS);
        } else {
            if (z) {
                Log.w("SupportMenuInflater", "Ignoring attribute 'actionProviderClass'. Action view already specified.");
            }
            this.ow = null;
        }
        this.ox = typedArrayObtainStyledAttributes.getText(R.styleable.MenuItem_contentDescription);
        this.oy = typedArrayObtainStyledAttributes.getText(R.styleable.MenuItem_tooltipText);
        if (typedArrayObtainStyledAttributes.hasValue(R.styleable.MenuItem_iconTintMode)) {
            this.oA = bp.d(typedArrayObtainStyledAttributes.getInt(R.styleable.MenuItem_iconTintMode, -1), this.oA);
        } else {
            this.oA = null;
        }
        if (typedArrayObtainStyledAttributes.hasValue(R.styleable.MenuItem_iconTint)) {
            this.oz = typedArrayObtainStyledAttributes.getColorStateList(R.styleable.MenuItem_iconTint);
        } else {
            this.oz = null;
        }
        typedArrayObtainStyledAttributes.recycle();
        this.od = false;
    }

    private char l(String str) {
        if (str == null) {
            return (char) 0;
        }
        return str.charAt(0);
    }

    private void a(MenuItem menuItem) {
        boolean z = true;
        menuItem.setChecked(this.oo).setVisible(this.op).setEnabled(this.oq).setCheckable(this.on >= 1).setTitleCondensed(this.oh).setIcon(this.oi);
        if (this.or >= 0) {
            menuItem.setShowAsAction(this.or);
        }
        if (this.ov != null) {
            if (this.oB.mContext.isRestricted()) {
                throw new IllegalStateException("The android:onClick attribute cannot be used within a restricted context");
            }
            menuItem.setOnMenuItemClickListener(new j(this.oB.cv(), this.ov));
        }
        if (menuItem instanceof w) {
        }
        if (this.on >= 2) {
            if (menuItem instanceof w) {
                ((w) menuItem).x(true);
            } else if (menuItem instanceof y) {
                ((y) menuItem).x(true);
            }
        }
        if (this.ot != null) {
            menuItem.setActionView((View) a(this.ot, i.nP, this.oB.nR));
        } else {
            z = false;
        }
        if (this.os > 0) {
            if (!z) {
                menuItem.setActionView(this.os);
            } else {
                Log.w("SupportMenuInflater", "Ignoring attribute 'itemActionViewLayout'. Action view already specified.");
            }
        }
        if (this.ow != null) {
            o.a(menuItem, this.ow);
        }
        o.a(menuItem, this.ox);
        o.b(menuItem, this.oy);
        o.b(menuItem, this.oj, this.ok);
        o.a(menuItem, this.ol, this.om);
        if (this.oA != null) {
            o.a(menuItem, this.oA);
        }
        if (this.oz != null) {
            o.a(menuItem, this.oz);
        }
    }

    public void cx() {
        this.od = true;
        a(this.nW.add(this.nX, this.oe, this.of, this.og));
    }

    public SubMenu cy() {
        this.od = true;
        SubMenu subMenuAddSubMenu = this.nW.addSubMenu(this.nX, this.oe, this.of, this.og);
        a(subMenuAddSubMenu.getItem());
        return subMenuAddSubMenu;
    }

    public boolean cz() {
        return this.od;
    }

    private <T> T a(String str, Class<?>[] clsArr, Object[] objArr) {
        try {
            Constructor<?> constructor = this.oB.mContext.getClassLoader().loadClass(str).getConstructor(clsArr);
            constructor.setAccessible(true);
            return (T) constructor.newInstance(objArr);
        } catch (Exception e) {
            Log.w("SupportMenuInflater", "Cannot instantiate class: " + str, e);
            return null;
        }
    }
}
