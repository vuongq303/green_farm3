package android.support.v7.c.a;

import android.content.ActivityNotFoundException;
import android.content.Context;
import android.content.Intent;
import android.content.res.ColorStateList;
import android.graphics.PorterDuff;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.util.Log;
import android.view.ActionProvider;
import android.view.ContextMenu;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.SubMenu;
import android.view.View;
import android.view.ViewDebug;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import com.facebook.R;

/* JADX INFO: compiled from: MenuItemImpl.java */
/* JADX INFO: loaded from: classes.dex */
public final class w implements android.support.v4.b.a.b {
    private static String ra;
    private static String rb;
    private static String rc;
    private static String rd;
    private CharSequence jp;
    private final int mId;
    s nw;
    private final int oK;
    private final int oL;
    private final int oM;
    private CharSequence oN;
    private char oO;
    private char oQ;
    private Drawable oS;
    private MenuItem.OnMenuItemClickListener oU;
    private CharSequence oV;
    private CharSequence oW;
    private as qR;
    private Runnable qS;
    private int qU;
    private View qV;
    private android.support.v4.view.g qW;
    private MenuItem.OnActionExpandListener qX;
    private ContextMenu.ContextMenuInfo qZ;
    private Intent v;
    private int oP = 4096;
    private int oR = 4096;
    private int oT = 0;
    private ColorStateList oX = null;
    private PorterDuff.Mode oY = null;
    private boolean oZ = false;
    private boolean pa = false;
    private boolean qT = false;
    private int bF = 16;
    private boolean qY = false;

    w(s sVar, int i, int i2, int i3, int i4, CharSequence charSequence, int i5) {
        this.qU = 0;
        this.nw = sVar;
        this.mId = i2;
        this.oK = i;
        this.oL = i3;
        this.oM = i4;
        this.jp = charSequence;
        this.qU = i5;
    }

    public boolean dn() {
        if ((this.oU != null && this.oU.onMenuItemClick(this)) || this.nw.d(this.nw, this)) {
            return true;
        }
        if (this.qS != null) {
            this.qS.run();
            return true;
        }
        if (this.v != null) {
            try {
                this.nw.getContext().startActivity(this.v);
                return true;
            } catch (ActivityNotFoundException e) {
                Log.e("MenuItemImpl", "Can't find activity to handle intent; ignoring", e);
            }
        }
        return this.qW != null && this.qW.onPerformDefaultAction();
    }

    @Override // android.view.MenuItem
    public boolean isEnabled() {
        return (this.bF & 16) != 0;
    }

    @Override // android.view.MenuItem
    public MenuItem setEnabled(boolean z) {
        if (z) {
            this.bF |= 16;
        } else {
            this.bF &= -17;
        }
        this.nw.v(false);
        return this;
    }

    @Override // android.view.MenuItem
    public int getGroupId() {
        return this.oK;
    }

    @Override // android.view.MenuItem
    @ViewDebug.CapturedViewProperty
    public int getItemId() {
        return this.mId;
    }

    @Override // android.view.MenuItem
    public int getOrder() {
        return this.oL;
    }

    public int getOrdering() {
        return this.oM;
    }

    @Override // android.view.MenuItem
    public Intent getIntent() {
        return this.v;
    }

    @Override // android.view.MenuItem
    public MenuItem setIntent(Intent intent) {
        this.v = intent;
        return this;
    }

    @Override // android.view.MenuItem
    public char getAlphabeticShortcut() {
        return this.oQ;
    }

    @Override // android.view.MenuItem
    public MenuItem setAlphabeticShortcut(char c) {
        if (this.oQ != c) {
            this.oQ = Character.toLowerCase(c);
            this.nw.v(false);
        }
        return this;
    }

    @Override // android.support.v4.b.a.b, android.view.MenuItem
    public MenuItem setAlphabeticShortcut(char c, int i) {
        if (this.oQ != c || this.oR != i) {
            this.oQ = Character.toLowerCase(c);
            this.oR = KeyEvent.normalizeMetaState(i);
            this.nw.v(false);
        }
        return this;
    }

    @Override // android.support.v4.b.a.b, android.view.MenuItem
    public int getAlphabeticModifiers() {
        return this.oR;
    }

    @Override // android.view.MenuItem
    public char getNumericShortcut() {
        return this.oO;
    }

    @Override // android.support.v4.b.a.b, android.view.MenuItem
    public int getNumericModifiers() {
        return this.oP;
    }

    @Override // android.view.MenuItem
    public MenuItem setNumericShortcut(char c) {
        if (this.oO != c) {
            this.oO = c;
            this.nw.v(false);
        }
        return this;
    }

    @Override // android.support.v4.b.a.b, android.view.MenuItem
    public MenuItem setNumericShortcut(char c, int i) {
        if (this.oO != c || this.oP != i) {
            this.oO = c;
            this.oP = KeyEvent.normalizeMetaState(i);
            this.nw.v(false);
        }
        return this;
    }

    @Override // android.view.MenuItem
    public MenuItem setShortcut(char c, char c2) {
        this.oO = c;
        this.oQ = Character.toLowerCase(c2);
        this.nw.v(false);
        return this;
    }

    @Override // android.support.v4.b.a.b, android.view.MenuItem
    public MenuItem setShortcut(char c, char c2, int i, int i2) {
        this.oO = c;
        this.oP = KeyEvent.normalizeMetaState(i);
        this.oQ = Character.toLowerCase(c2);
        this.oR = KeyEvent.normalizeMetaState(i2);
        this.nw.v(false);
        return this;
    }

    /* JADX INFO: renamed from: do, reason: not valid java name */
    char m0do() {
        return this.nw.cY() ? this.oQ : this.oO;
    }

    String dp() {
        char cM0do = m0do();
        if (cM0do == 0) {
            return "";
        }
        StringBuilder sb = new StringBuilder(ra);
        switch (cM0do) {
            case '\b':
                sb.append(rc);
                break;
            case R.styleable.CardView_contentPaddingRight /* 10 */:
                sb.append(rb);
                break;
            case ' ':
                sb.append(rd);
                break;
            default:
                sb.append(cM0do);
                break;
        }
        return sb.toString();
    }

    boolean dq() {
        return this.nw.cZ() && m0do() != 0;
    }

    @Override // android.view.MenuItem
    public SubMenu getSubMenu() {
        return this.qR;
    }

    @Override // android.view.MenuItem
    public boolean hasSubMenu() {
        return this.qR != null;
    }

    public void b(as asVar) {
        this.qR = asVar;
        asVar.setHeaderTitle(getTitle());
    }

    @Override // android.view.MenuItem
    @ViewDebug.CapturedViewProperty
    public CharSequence getTitle() {
        return this.jp;
    }

    CharSequence a(al alVar) {
        return (alVar == null || !alVar.cF()) ? getTitle() : getTitleCondensed();
    }

    @Override // android.view.MenuItem
    public MenuItem setTitle(CharSequence charSequence) {
        this.jp = charSequence;
        this.nw.v(false);
        if (this.qR != null) {
            this.qR.setHeaderTitle(charSequence);
        }
        return this;
    }

    @Override // android.view.MenuItem
    public MenuItem setTitle(int i) {
        return setTitle(this.nw.getContext().getString(i));
    }

    @Override // android.view.MenuItem
    public CharSequence getTitleCondensed() {
        CharSequence charSequence = this.oN != null ? this.oN : this.jp;
        if (Build.VERSION.SDK_INT < 18 && charSequence != null && !(charSequence instanceof String)) {
            return charSequence.toString();
        }
        return charSequence;
    }

    @Override // android.view.MenuItem
    public MenuItem setTitleCondensed(CharSequence charSequence) {
        this.oN = charSequence;
        if (charSequence == null) {
            CharSequence charSequence2 = this.jp;
        }
        this.nw.v(false);
        return this;
    }

    @Override // android.view.MenuItem
    public Drawable getIcon() {
        if (this.oS != null) {
            return h(this.oS);
        }
        if (this.oT != 0) {
            Drawable drawableA = android.support.v7.a.a.b.a(this.nw.getContext(), this.oT);
            this.oT = 0;
            this.oS = drawableA;
            return h(drawableA);
        }
        return null;
    }

    @Override // android.view.MenuItem
    public MenuItem setIcon(Drawable drawable) {
        this.oT = 0;
        this.oS = drawable;
        this.qT = true;
        this.nw.v(false);
        return this;
    }

    @Override // android.view.MenuItem
    public MenuItem setIcon(int i) {
        this.oS = null;
        this.oT = i;
        this.qT = true;
        this.nw.v(false);
        return this;
    }

    @Override // android.support.v4.b.a.b, android.view.MenuItem
    public MenuItem setIconTintList(ColorStateList colorStateList) {
        this.oX = colorStateList;
        this.oZ = true;
        this.qT = true;
        this.nw.v(false);
        return this;
    }

    @Override // android.support.v4.b.a.b, android.view.MenuItem
    public ColorStateList getIconTintList() {
        return this.oX;
    }

    @Override // android.support.v4.b.a.b, android.view.MenuItem
    public MenuItem setIconTintMode(PorterDuff.Mode mode) {
        this.oY = mode;
        this.pa = true;
        this.qT = true;
        this.nw.v(false);
        return this;
    }

    @Override // android.support.v4.b.a.b, android.view.MenuItem
    public PorterDuff.Mode getIconTintMode() {
        return this.oY;
    }

    private Drawable h(Drawable drawable) {
        if (drawable != null && this.qT && (this.oZ || this.pa)) {
            drawable = android.support.v4.a.a.a.d(drawable).mutate();
            if (this.oZ) {
                android.support.v4.a.a.a.a(drawable, this.oX);
            }
            if (this.pa) {
                android.support.v4.a.a.a.a(drawable, this.oY);
            }
            this.qT = false;
        }
        return drawable;
    }

    @Override // android.view.MenuItem
    public boolean isCheckable() {
        return (this.bF & 1) == 1;
    }

    @Override // android.view.MenuItem
    public MenuItem setCheckable(boolean z) {
        int i = this.bF;
        this.bF = (z ? 1 : 0) | (this.bF & (-2));
        if (i != this.bF) {
            this.nw.v(false);
        }
        return this;
    }

    public void x(boolean z) {
        this.bF = (z ? 4 : 0) | (this.bF & (-5));
    }

    public boolean dr() {
        return (this.bF & 4) != 0;
    }

    @Override // android.view.MenuItem
    public boolean isChecked() {
        return (this.bF & 2) == 2;
    }

    @Override // android.view.MenuItem
    public MenuItem setChecked(boolean z) {
        if ((this.bF & 4) != 0) {
            this.nw.c((MenuItem) this);
        } else {
            y(z);
        }
        return this;
    }

    void y(boolean z) {
        int i = this.bF;
        this.bF = (z ? 2 : 0) | (this.bF & (-3));
        if (i != this.bF) {
            this.nw.v(false);
        }
    }

    @Override // android.view.MenuItem
    public boolean isVisible() {
        if (this.qW == null || !this.qW.overridesItemVisibility()) {
            return (this.bF & 8) == 0;
        }
        return (this.bF & 8) == 0 && this.qW.isVisible();
    }

    boolean z(boolean z) {
        int i = this.bF;
        this.bF = (z ? 0 : 8) | (this.bF & (-9));
        return i != this.bF;
    }

    @Override // android.view.MenuItem
    public MenuItem setVisible(boolean z) {
        if (z(z)) {
            this.nw.b(this);
        }
        return this;
    }

    @Override // android.view.MenuItem
    public MenuItem setOnMenuItemClickListener(MenuItem.OnMenuItemClickListener onMenuItemClickListener) {
        this.oU = onMenuItemClickListener;
        return this;
    }

    public String toString() {
        if (this.jp != null) {
            return this.jp.toString();
        }
        return null;
    }

    void a(ContextMenu.ContextMenuInfo contextMenuInfo) {
        this.qZ = contextMenuInfo;
    }

    @Override // android.view.MenuItem
    public ContextMenu.ContextMenuInfo getMenuInfo() {
        return this.qZ;
    }

    public boolean shouldShowIcon() {
        return this.nw.dl();
    }

    public boolean ds() {
        return (this.bF & 32) == 32;
    }

    public boolean dt() {
        return (this.qU & 1) == 1;
    }

    public boolean du() {
        return (this.qU & 2) == 2;
    }

    public void A(boolean z) {
        if (z) {
            this.bF |= 32;
        } else {
            this.bF &= -33;
        }
    }

    public boolean dv() {
        return (this.qU & 4) == 4;
    }

    @Override // android.support.v4.b.a.b, android.view.MenuItem
    public void setShowAsAction(int i) {
        switch (i & 3) {
            case 0:
            case 1:
            case 2:
                this.qU = i;
                this.nw.c(this);
                return;
            default:
                throw new IllegalArgumentException("SHOW_AS_ACTION_ALWAYS, SHOW_AS_ACTION_IF_ROOM, and SHOW_AS_ACTION_NEVER are mutually exclusive.");
        }
    }

    @Override // android.support.v4.b.a.b, android.view.MenuItem
    /* JADX INFO: renamed from: B, reason: merged with bridge method [inline-methods] */
    public android.support.v4.b.a.b setActionView(View view) {
        this.qV = view;
        this.qW = null;
        if (view != null && view.getId() == -1 && this.mId > 0) {
            view.setId(this.mId);
        }
        this.nw.c(this);
        return this;
    }

    @Override // android.support.v4.b.a.b, android.view.MenuItem
    /* JADX INFO: renamed from: X, reason: merged with bridge method [inline-methods] */
    public android.support.v4.b.a.b setActionView(int i) {
        Context context = this.nw.getContext();
        setActionView(LayoutInflater.from(context).inflate(i, (ViewGroup) new LinearLayout(context), false));
        return this;
    }

    @Override // android.support.v4.b.a.b, android.view.MenuItem
    public View getActionView() {
        if (this.qV != null) {
            return this.qV;
        }
        if (this.qW != null) {
            this.qV = this.qW.onCreateActionView(this);
            return this.qV;
        }
        return null;
    }

    @Override // android.view.MenuItem
    public MenuItem setActionProvider(ActionProvider actionProvider) {
        throw new UnsupportedOperationException("This is not supported, use MenuItemCompat.setActionProvider()");
    }

    @Override // android.view.MenuItem
    public ActionProvider getActionProvider() {
        throw new UnsupportedOperationException("This is not supported, use MenuItemCompat.getActionProvider()");
    }

    @Override // android.support.v4.b.a.b
    public android.support.v4.view.g aw() {
        return this.qW;
    }

    @Override // android.support.v4.b.a.b
    public android.support.v4.b.a.b a(android.support.v4.view.g gVar) {
        if (this.qW != null) {
            this.qW.reset();
        }
        this.qV = null;
        this.qW = gVar;
        this.nw.v(true);
        if (this.qW != null) {
            this.qW.a(new x(this));
        }
        return this;
    }

    @Override // android.support.v4.b.a.b, android.view.MenuItem
    /* JADX INFO: renamed from: Y, reason: merged with bridge method [inline-methods] */
    public android.support.v4.b.a.b setShowAsActionFlags(int i) {
        setShowAsAction(i);
        return this;
    }

    @Override // android.support.v4.b.a.b, android.view.MenuItem
    public boolean expandActionView() {
        if (!dw()) {
            return false;
        }
        if (this.qX == null || this.qX.onMenuItemActionExpand(this)) {
            return this.nw.d(this);
        }
        return false;
    }

    @Override // android.support.v4.b.a.b, android.view.MenuItem
    public boolean collapseActionView() {
        if ((this.qU & 8) == 0) {
            return false;
        }
        if (this.qV == null) {
            return true;
        }
        if (this.qX == null || this.qX.onMenuItemActionCollapse(this)) {
            return this.nw.e(this);
        }
        return false;
    }

    public boolean dw() {
        if ((this.qU & 8) == 0) {
            return false;
        }
        if (this.qV == null && this.qW != null) {
            this.qV = this.qW.onCreateActionView(this);
        }
        return this.qV != null;
    }

    public void B(boolean z) {
        this.qY = z;
        this.nw.v(false);
    }

    @Override // android.support.v4.b.a.b, android.view.MenuItem
    public boolean isActionViewExpanded() {
        return this.qY;
    }

    @Override // android.view.MenuItem
    public MenuItem setOnActionExpandListener(MenuItem.OnActionExpandListener onActionExpandListener) {
        this.qX = onActionExpandListener;
        return this;
    }

    @Override // android.view.MenuItem
    /* JADX INFO: renamed from: f, reason: merged with bridge method [inline-methods] */
    public android.support.v4.b.a.b setContentDescription(CharSequence charSequence) {
        this.oV = charSequence;
        this.nw.v(false);
        return this;
    }

    @Override // android.support.v4.b.a.b, android.view.MenuItem
    public CharSequence getContentDescription() {
        return this.oV;
    }

    @Override // android.view.MenuItem
    /* JADX INFO: renamed from: g, reason: merged with bridge method [inline-methods] */
    public android.support.v4.b.a.b setTooltipText(CharSequence charSequence) {
        this.oW = charSequence;
        this.nw.v(false);
        return this;
    }

    @Override // android.support.v4.b.a.b, android.view.MenuItem
    public CharSequence getTooltipText() {
        return this.oW;
    }
}
