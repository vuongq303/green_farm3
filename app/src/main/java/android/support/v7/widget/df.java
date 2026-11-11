package android.support.v7.widget;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.support.v7.appcompat.R;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;

/* JADX INFO: compiled from: ToolbarWidgetWrapper.java */
/* JADX INFO: loaded from: classes.dex */
public class df implements bo {
    private Drawable jJ;
    CharSequence jp;
    Window.Callback mB;
    private i rB;
    private CharSequence rR;
    private View rT;
    Toolbar yD;
    private int yE;
    private View yF;
    private Drawable yG;
    private Drawable yH;
    private boolean yI;
    private CharSequence yJ;
    boolean yK;
    private int yL;
    private int yM;
    private Drawable yN;

    public df(Toolbar toolbar, boolean z) {
        this(toolbar, z, R.string.abc_action_bar_up_description, R.drawable.abc_ic_ab_back_material);
    }

    public df(Toolbar toolbar, boolean z, int i, int i2) {
        this.yL = 0;
        this.yM = 0;
        this.yD = toolbar;
        this.jp = toolbar.getTitle();
        this.rR = toolbar.getSubtitle();
        this.yI = this.jp != null;
        this.yH = toolbar.getNavigationIcon();
        da daVarA = da.a(toolbar.getContext(), null, R.styleable.ActionBar, R.attr.actionBarStyle, 0);
        this.yN = daVarA.getDrawable(R.styleable.ActionBar_homeAsUpIndicator);
        if (z) {
            CharSequence text = daVarA.getText(R.styleable.ActionBar_title);
            if (!TextUtils.isEmpty(text)) {
                setTitle(text);
            }
            CharSequence text2 = daVarA.getText(R.styleable.ActionBar_subtitle);
            if (!TextUtils.isEmpty(text2)) {
                setSubtitle(text2);
            }
            Drawable drawable = daVarA.getDrawable(R.styleable.ActionBar_logo);
            if (drawable != null) {
                setLogo(drawable);
            }
            Drawable drawable2 = daVarA.getDrawable(R.styleable.ActionBar_icon);
            if (drawable2 != null) {
                setIcon(drawable2);
            }
            if (this.yH == null && this.yN != null) {
                setNavigationIcon(this.yN);
            }
            setDisplayOptions(daVarA.getInt(R.styleable.ActionBar_displayOptions, 0));
            int resourceId = daVarA.getResourceId(R.styleable.ActionBar_customNavigationLayout, 0);
            if (resourceId != 0) {
                setCustomView(LayoutInflater.from(this.yD.getContext()).inflate(resourceId, (ViewGroup) this.yD, false));
                setDisplayOptions(this.yE | 16);
            }
            int layoutDimension = daVarA.getLayoutDimension(R.styleable.ActionBar_height, 0);
            if (layoutDimension > 0) {
                ViewGroup.LayoutParams layoutParams = this.yD.getLayoutParams();
                layoutParams.height = layoutDimension;
                this.yD.setLayoutParams(layoutParams);
            }
            int dimensionPixelOffset = daVarA.getDimensionPixelOffset(R.styleable.ActionBar_contentInsetStart, -1);
            int dimensionPixelOffset2 = daVarA.getDimensionPixelOffset(R.styleable.ActionBar_contentInsetEnd, -1);
            if (dimensionPixelOffset >= 0 || dimensionPixelOffset2 >= 0) {
                this.yD.setContentInsetsRelative(Math.max(dimensionPixelOffset, 0), Math.max(dimensionPixelOffset2, 0));
            }
            int resourceId2 = daVarA.getResourceId(R.styleable.ActionBar_titleTextStyle, 0);
            if (resourceId2 != 0) {
                this.yD.setTitleTextAppearance(this.yD.getContext(), resourceId2);
            }
            int resourceId3 = daVarA.getResourceId(R.styleable.ActionBar_subtitleTextStyle, 0);
            if (resourceId3 != 0) {
                this.yD.setSubtitleTextAppearance(this.yD.getContext(), resourceId3);
            }
            int resourceId4 = daVarA.getResourceId(R.styleable.ActionBar_popupTheme, 0);
            if (resourceId4 != 0) {
                this.yD.setPopupTheme(resourceId4);
            }
        } else {
            this.yE = fl();
        }
        daVarA.recycle();
        ay(i);
        this.yJ = this.yD.getNavigationContentDescription();
        this.yD.setNavigationOnClickListener(new dg(this));
    }

    public void ay(int i) {
        if (i != this.yM) {
            this.yM = i;
            if (TextUtils.isEmpty(this.yD.getNavigationContentDescription())) {
                setNavigationContentDescription(this.yM);
            }
        }
    }

    private int fl() {
        if (this.yD.getNavigationIcon() == null) {
            return 11;
        }
        this.yN = this.yD.getNavigationIcon();
        return 15;
    }

    @Override // android.support.v7.widget.bo
    public ViewGroup eE() {
        return this.yD;
    }

    @Override // android.support.v7.widget.bo
    public Context getContext() {
        return this.yD.getContext();
    }

    @Override // android.support.v7.widget.bo
    public boolean hasExpandedActionView() {
        return this.yD.hasExpandedActionView();
    }

    @Override // android.support.v7.widget.bo
    public void collapseActionView() {
        this.yD.collapseActionView();
    }

    @Override // android.support.v7.widget.bo
    public void b(Window.Callback callback) {
        this.mB = callback;
    }

    @Override // android.support.v7.widget.bo
    public void h(CharSequence charSequence) {
        if (!this.yI) {
            m(charSequence);
        }
    }

    public void setTitle(CharSequence charSequence) {
        this.yI = true;
        m(charSequence);
    }

    private void m(CharSequence charSequence) {
        this.jp = charSequence;
        if ((this.yE & 8) != 0) {
            this.yD.setTitle(charSequence);
        }
    }

    public void setSubtitle(CharSequence charSequence) {
        this.rR = charSequence;
        if ((this.yE & 8) != 0) {
            this.yD.setSubtitle(charSequence);
        }
    }

    @Override // android.support.v7.widget.bo
    public void eF() {
        Log.i("ToolbarWidgetWrapper", "Progress display unsupported");
    }

    @Override // android.support.v7.widget.bo
    public void eG() {
        Log.i("ToolbarWidgetWrapper", "Progress display unsupported");
    }

    public void setIcon(Drawable drawable) {
        this.jJ = drawable;
        fm();
    }

    public void setLogo(Drawable drawable) {
        this.yG = drawable;
        fm();
    }

    private void fm() {
        Drawable drawable = null;
        if ((this.yE & 2) != 0) {
            drawable = ((this.yE & 1) == 0 || this.yG == null) ? this.jJ : this.yG;
        }
        this.yD.setLogo(drawable);
    }

    @Override // android.support.v7.widget.bo
    public boolean dR() {
        return this.yD.dR();
    }

    @Override // android.support.v7.widget.bo
    public boolean isOverflowMenuShowing() {
        return this.yD.isOverflowMenuShowing();
    }

    @Override // android.support.v7.widget.bo
    public boolean dS() {
        return this.yD.dS();
    }

    @Override // android.support.v7.widget.bo
    public boolean showOverflowMenu() {
        return this.yD.showOverflowMenu();
    }

    @Override // android.support.v7.widget.bo
    public boolean hideOverflowMenu() {
        return this.yD.hideOverflowMenu();
    }

    @Override // android.support.v7.widget.bo
    public void dT() {
        this.yK = true;
    }

    @Override // android.support.v7.widget.bo
    public void a(Menu menu, android.support.v7.c.a.aj ajVar) {
        if (this.rB == null) {
            this.rB = new i(this.yD.getContext());
            this.rB.setId(R.id.action_menu_presenter);
        }
        this.rB.b(ajVar);
        this.yD.a((android.support.v7.c.a.s) menu, this.rB);
    }

    @Override // android.support.v7.widget.bo
    public void dismissPopupMenus() {
        this.yD.dismissPopupMenus();
    }

    @Override // android.support.v7.widget.bo
    public int getDisplayOptions() {
        return this.yE;
    }

    @Override // android.support.v7.widget.bo
    public void setDisplayOptions(int i) {
        int i2 = this.yE ^ i;
        this.yE = i;
        if (i2 != 0) {
            if ((i2 & 4) != 0) {
                if ((i & 4) != 0) {
                    fo();
                }
                fn();
            }
            if ((i2 & 3) != 0) {
                fm();
            }
            if ((i2 & 8) != 0) {
                if ((i & 8) != 0) {
                    this.yD.setTitle(this.jp);
                    this.yD.setSubtitle(this.rR);
                } else {
                    this.yD.setTitle(null);
                    this.yD.setSubtitle(null);
                }
            }
            if ((i2 & 16) != 0 && this.rT != null) {
                if ((i & 16) != 0) {
                    this.yD.addView(this.rT);
                } else {
                    this.yD.removeView(this.rT);
                }
            }
        }
    }

    @Override // android.support.v7.widget.bo
    public void b(cq cqVar) {
        if (this.yF != null && this.yF.getParent() == this.yD) {
            this.yD.removeView(this.yF);
        }
        this.yF = cqVar;
        if (cqVar != null && this.yL == 2) {
            this.yD.addView(this.yF, 0);
            dd ddVar = (dd) this.yF.getLayoutParams();
            ddVar.width = -2;
            ddVar.height = -2;
            ddVar.gravity = 8388691;
            cqVar.R(true);
        }
    }

    @Override // android.support.v7.widget.bo
    public void L(boolean z) {
        this.yD.L(z);
    }

    @Override // android.support.v7.widget.bo
    public void setHomeButtonEnabled(boolean z) {
    }

    @Override // android.support.v7.widget.bo
    public int getNavigationMode() {
        return this.yL;
    }

    public void setCustomView(View view) {
        if (this.rT != null && (this.yE & 16) != 0) {
            this.yD.removeView(this.rT);
        }
        this.rT = view;
        if (view != null && (this.yE & 16) != 0) {
            this.yD.addView(this.rT);
        }
    }

    @Override // android.support.v7.widget.bo
    public android.support.v4.view.ar a(int i, long j) {
        return android.support.v4.view.aa.h(this.yD).a(i == 0 ? 1.0f : 0.0f).d(j).a(new dh(this, i));
    }

    public void setNavigationIcon(Drawable drawable) {
        this.yH = drawable;
        fn();
    }

    private void fn() {
        if ((this.yE & 4) != 0) {
            this.yD.setNavigationIcon(this.yH != null ? this.yH : this.yN);
        } else {
            this.yD.setNavigationIcon(null);
        }
    }

    public void setNavigationContentDescription(CharSequence charSequence) {
        this.yJ = charSequence;
        fo();
    }

    public void setNavigationContentDescription(int i) {
        setNavigationContentDescription(i == 0 ? null : getContext().getString(i));
    }

    private void fo() {
        if ((this.yE & 4) != 0) {
            if (TextUtils.isEmpty(this.yJ)) {
                this.yD.setNavigationContentDescription(this.yM);
            } else {
                this.yD.setNavigationContentDescription(this.yJ);
            }
        }
    }

    @Override // android.support.v7.widget.bo
    public void setVisibility(int i) {
        this.yD.setVisibility(i);
    }

    @Override // android.support.v7.widget.bo
    public void a(android.support.v7.c.a.aj ajVar, android.support.v7.c.a.t tVar) {
        this.yD.a(ajVar, tVar);
    }

    @Override // android.support.v7.widget.bo
    public Menu getMenu() {
        return this.yD.getMenu();
    }
}
