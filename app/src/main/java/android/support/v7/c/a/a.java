package android.support.v7.c.a;

import android.content.Context;
import android.content.Intent;
import android.content.res.ColorStateList;
import android.graphics.PorterDuff;
import android.graphics.drawable.Drawable;
import android.view.ActionProvider;
import android.view.ContextMenu;
import android.view.KeyEvent;
import android.view.MenuItem;
import android.view.SubMenu;
import android.view.View;

/* JADX INFO: compiled from: ActionMenuItem.java */
/* JADX INFO: loaded from: classes.dex */
public class a implements android.support.v4.b.a.b {
    private CharSequence jp;
    private Context mContext;
    private final int mId;
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
    private Intent v;
    private int oP = 4096;
    private int oR = 4096;
    private int oT = 0;
    private ColorStateList oX = null;
    private PorterDuff.Mode oY = null;
    private boolean oZ = false;
    private boolean pa = false;
    private int bF = 16;

    public a(Context context, int i, int i2, int i3, int i4, CharSequence charSequence) {
        this.mContext = context;
        this.mId = i2;
        this.oK = i;
        this.oL = i3;
        this.oM = i4;
        this.jp = charSequence;
    }

    @Override // android.view.MenuItem
    public char getAlphabeticShortcut() {
        return this.oQ;
    }

    @Override // android.support.v4.b.a.b, android.view.MenuItem
    public int getAlphabeticModifiers() {
        return this.oR;
    }

    @Override // android.view.MenuItem
    public int getGroupId() {
        return this.oK;
    }

    @Override // android.view.MenuItem
    public Drawable getIcon() {
        return this.oS;
    }

    @Override // android.view.MenuItem
    public Intent getIntent() {
        return this.v;
    }

    @Override // android.view.MenuItem
    public int getItemId() {
        return this.mId;
    }

    @Override // android.view.MenuItem
    public ContextMenu.ContextMenuInfo getMenuInfo() {
        return null;
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
    public int getOrder() {
        return this.oM;
    }

    @Override // android.view.MenuItem
    public SubMenu getSubMenu() {
        return null;
    }

    @Override // android.view.MenuItem
    public CharSequence getTitle() {
        return this.jp;
    }

    @Override // android.view.MenuItem
    public CharSequence getTitleCondensed() {
        return this.oN != null ? this.oN : this.jp;
    }

    @Override // android.view.MenuItem
    public boolean hasSubMenu() {
        return false;
    }

    @Override // android.view.MenuItem
    public boolean isCheckable() {
        return (this.bF & 1) != 0;
    }

    @Override // android.view.MenuItem
    public boolean isChecked() {
        return (this.bF & 2) != 0;
    }

    @Override // android.view.MenuItem
    public boolean isEnabled() {
        return (this.bF & 16) != 0;
    }

    @Override // android.view.MenuItem
    public boolean isVisible() {
        return (this.bF & 8) == 0;
    }

    @Override // android.view.MenuItem
    public MenuItem setAlphabeticShortcut(char c) {
        this.oQ = Character.toLowerCase(c);
        return this;
    }

    @Override // android.support.v4.b.a.b, android.view.MenuItem
    public MenuItem setAlphabeticShortcut(char c, int i) {
        this.oQ = Character.toLowerCase(c);
        this.oR = KeyEvent.normalizeMetaState(i);
        return this;
    }

    @Override // android.view.MenuItem
    public MenuItem setCheckable(boolean z) {
        this.bF = (z ? 1 : 0) | (this.bF & (-2));
        return this;
    }

    @Override // android.view.MenuItem
    public MenuItem setChecked(boolean z) {
        this.bF = (z ? 2 : 0) | (this.bF & (-3));
        return this;
    }

    @Override // android.view.MenuItem
    public MenuItem setEnabled(boolean z) {
        this.bF = (z ? 16 : 0) | (this.bF & (-17));
        return this;
    }

    @Override // android.view.MenuItem
    public MenuItem setIcon(Drawable drawable) {
        this.oS = drawable;
        this.oT = 0;
        cC();
        return this;
    }

    @Override // android.view.MenuItem
    public MenuItem setIcon(int i) {
        this.oT = i;
        this.oS = android.support.v4.content.c.a(this.mContext, i);
        cC();
        return this;
    }

    @Override // android.view.MenuItem
    public MenuItem setIntent(Intent intent) {
        this.v = intent;
        return this;
    }

    @Override // android.view.MenuItem
    public MenuItem setNumericShortcut(char c) {
        this.oO = c;
        return this;
    }

    @Override // android.support.v4.b.a.b, android.view.MenuItem
    public MenuItem setNumericShortcut(char c, int i) {
        this.oO = c;
        this.oP = KeyEvent.normalizeMetaState(i);
        return this;
    }

    @Override // android.view.MenuItem
    public MenuItem setOnMenuItemClickListener(MenuItem.OnMenuItemClickListener onMenuItemClickListener) {
        this.oU = onMenuItemClickListener;
        return this;
    }

    @Override // android.view.MenuItem
    public MenuItem setShortcut(char c, char c2) {
        this.oO = c;
        this.oQ = Character.toLowerCase(c2);
        return this;
    }

    @Override // android.support.v4.b.a.b, android.view.MenuItem
    public MenuItem setShortcut(char c, char c2, int i, int i2) {
        this.oO = c;
        this.oP = KeyEvent.normalizeMetaState(i);
        this.oQ = Character.toLowerCase(c2);
        this.oR = KeyEvent.normalizeMetaState(i2);
        return this;
    }

    @Override // android.view.MenuItem
    public MenuItem setTitle(CharSequence charSequence) {
        this.jp = charSequence;
        return this;
    }

    @Override // android.view.MenuItem
    public MenuItem setTitle(int i) {
        this.jp = this.mContext.getResources().getString(i);
        return this;
    }

    @Override // android.view.MenuItem
    public MenuItem setTitleCondensed(CharSequence charSequence) {
        this.oN = charSequence;
        return this;
    }

    @Override // android.view.MenuItem
    public MenuItem setVisible(boolean z) {
        this.bF = (z ? 0 : 8) | (this.bF & 8);
        return this;
    }

    @Override // android.support.v4.b.a.b, android.view.MenuItem
    public void setShowAsAction(int i) {
    }

    @Override // android.support.v4.b.a.b, android.view.MenuItem
    /* JADX INFO: renamed from: B, reason: merged with bridge method [inline-methods] */
    public android.support.v4.b.a.b setActionView(View view) {
        throw new UnsupportedOperationException();
    }

    @Override // android.support.v4.b.a.b, android.view.MenuItem
    public View getActionView() {
        return null;
    }

    @Override // android.view.MenuItem
    public MenuItem setActionProvider(ActionProvider actionProvider) {
        throw new UnsupportedOperationException();
    }

    @Override // android.view.MenuItem
    public ActionProvider getActionProvider() {
        throw new UnsupportedOperationException();
    }

    @Override // android.support.v4.b.a.b, android.view.MenuItem
    /* JADX INFO: renamed from: X, reason: merged with bridge method [inline-methods] */
    public android.support.v4.b.a.b setActionView(int i) {
        throw new UnsupportedOperationException();
    }

    @Override // android.support.v4.b.a.b
    public android.support.v4.view.g aw() {
        return null;
    }

    @Override // android.support.v4.b.a.b
    public android.support.v4.b.a.b a(android.support.v4.view.g gVar) {
        throw new UnsupportedOperationException();
    }

    @Override // android.support.v4.b.a.b, android.view.MenuItem
    /* JADX INFO: renamed from: Y, reason: merged with bridge method [inline-methods] */
    public android.support.v4.b.a.b setShowAsActionFlags(int i) {
        setShowAsAction(i);
        return this;
    }

    @Override // android.support.v4.b.a.b, android.view.MenuItem
    public boolean expandActionView() {
        return false;
    }

    @Override // android.support.v4.b.a.b, android.view.MenuItem
    public boolean collapseActionView() {
        return false;
    }

    @Override // android.support.v4.b.a.b, android.view.MenuItem
    public boolean isActionViewExpanded() {
        return false;
    }

    @Override // android.view.MenuItem
    public MenuItem setOnActionExpandListener(MenuItem.OnActionExpandListener onActionExpandListener) {
        throw new UnsupportedOperationException();
    }

    @Override // android.view.MenuItem
    /* JADX INFO: renamed from: f */
    public android.support.v4.b.a.b setContentDescription(CharSequence charSequence) {
        this.oV = charSequence;
        return this;
    }

    @Override // android.support.v4.b.a.b, android.view.MenuItem
    public CharSequence getContentDescription() {
        return this.oV;
    }

    @Override // android.view.MenuItem
    /* JADX INFO: renamed from: g */
    public android.support.v4.b.a.b setTooltipText(CharSequence charSequence) {
        this.oW = charSequence;
        return this;
    }

    @Override // android.support.v4.b.a.b, android.view.MenuItem
    public CharSequence getTooltipText() {
        return this.oW;
    }

    @Override // android.support.v4.b.a.b, android.view.MenuItem
    public MenuItem setIconTintList(ColorStateList colorStateList) {
        this.oX = colorStateList;
        this.oZ = true;
        cC();
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
        cC();
        return this;
    }

    @Override // android.support.v4.b.a.b, android.view.MenuItem
    public PorterDuff.Mode getIconTintMode() {
        return this.oY;
    }

    private void cC() {
        if (this.oS != null) {
            if (this.oZ || this.pa) {
                this.oS = android.support.v4.a.a.a.d(this.oS);
                this.oS = this.oS.mutate();
                if (this.oZ) {
                    android.support.v4.a.a.a.a(this.oS, this.oX);
                }
                if (this.pa) {
                    android.support.v4.a.a.a.a(this.oS, this.oY);
                }
            }
        }
    }
}
