package android.support.v7.app;

import android.R;
import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.content.res.Configuration;
import android.content.res.TypedArray;
import android.support.v7.widget.Toolbar;
import android.support.v7.widget.bo;
import android.support.v7.widget.cq;
import android.util.TypedValue;
import android.view.ContextThemeWrapper;
import android.view.KeyCharacterMap;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.View;
import android.view.animation.AccelerateInterpolator;
import android.view.animation.DecelerateInterpolator;
import android.view.animation.Interpolator;
import java.util.ArrayList;

/* JADX INFO: compiled from: WindowDecorActionBar.java */
/* JADX INFO: loaded from: classes.dex */
public class bh extends a implements android.support.v7.widget.g {
    static final /* synthetic */ boolean $assertionsDisabled;
    private static final Interpolator mT;
    private static final Interpolator mU;
    bo mA;
    private Activity mActivity;
    Context mContext;
    private boolean mD;
    private Dialog mDialog;
    private Context mV;
    android.support.v7.widget.f mW;
    android.support.v7.widget.c mX;
    android.support.v7.widget.d mY;
    View mZ;
    cq na;
    private boolean nd;
    bl ne;
    android.support.v7.c.b nf;
    android.support.v7.c.c ng;
    private boolean nh;
    boolean nk;
    boolean nl;
    private boolean nm;
    android.support.v7.c.l no;
    private boolean np;
    boolean nq;
    private ArrayList<Object> nb = new ArrayList<>();
    private int nc = -1;
    private ArrayList<c> mE = new ArrayList<>();
    private int ni = 0;
    boolean nj = true;
    private boolean nn = true;
    final android.support.v4.view.av nr = new bi(this);
    final android.support.v4.view.av ns = new bj(this);
    final android.support.v4.view.ax nt = new bk(this);

    static {
        $assertionsDisabled = !bh.class.desiredAssertionStatus();
        mT = new AccelerateInterpolator();
        mU = new DecelerateInterpolator();
    }

    public bh(Activity activity, boolean z) {
        this.mActivity = activity;
        View decorView = activity.getWindow().getDecorView();
        z(decorView);
        if (!z) {
            this.mZ = decorView.findViewById(R.id.content);
        }
    }

    public bh(Dialog dialog) {
        this.mDialog = dialog;
        z(dialog.getWindow().getDecorView());
    }

    private void z(View view) {
        this.mW = (android.support.v7.widget.f) view.findViewById(android.support.v7.appcompat.R.id.decor_content_parent);
        if (this.mW != null) {
            this.mW.a(this);
        }
        this.mA = A(view.findViewById(android.support.v7.appcompat.R.id.action_bar));
        this.mY = (android.support.v7.widget.d) view.findViewById(android.support.v7.appcompat.R.id.action_context_bar);
        this.mX = (android.support.v7.widget.c) view.findViewById(android.support.v7.appcompat.R.id.action_bar_container);
        if (this.mA == null || this.mY == null || this.mX == null) {
            throw new IllegalStateException(getClass().getSimpleName() + " can only be used with a compatible window decor layout");
        }
        this.mContext = this.mA.getContext();
        boolean z = (this.mA.getDisplayOptions() & 4) != 0;
        if (z) {
            this.nd = true;
        }
        android.support.v7.c.a aVarM = android.support.v7.c.a.m(this.mContext);
        setHomeButtonEnabled(aVarM.cq() || z);
        j(aVarM.co());
        TypedArray typedArrayObtainStyledAttributes = this.mContext.obtainStyledAttributes(null, android.support.v7.appcompat.R.styleable.ActionBar, android.support.v7.appcompat.R.attr.actionBarStyle, 0);
        if (typedArrayObtainStyledAttributes.getBoolean(android.support.v7.appcompat.R.styleable.ActionBar_hideOnContentScroll, false)) {
            setHideOnContentScrollEnabled(true);
        }
        int dimensionPixelSize = typedArrayObtainStyledAttributes.getDimensionPixelSize(android.support.v7.appcompat.R.styleable.ActionBar_elevation, 0);
        if (dimensionPixelSize != 0) {
            setElevation(dimensionPixelSize);
        }
        typedArrayObtainStyledAttributes.recycle();
    }

    /* JADX WARN: Multi-variable type inference failed */
    private bo A(View view) {
        if (view instanceof bo) {
            return (bo) view;
        }
        if (view instanceof Toolbar) {
            return ((Toolbar) view).fh();
        }
        throw new IllegalStateException(new StringBuilder().append("Can't make a decor toolbar out of ").append(view).toString() != null ? view.getClass().getSimpleName() : "null");
    }

    @Override // android.support.v7.app.a
    public void setElevation(float f) {
        android.support.v4.view.aa.a(this.mX, f);
    }

    @Override // android.support.v7.app.a
    public void onConfigurationChanged(Configuration configuration) {
        j(android.support.v7.c.a.m(this.mContext).co());
    }

    private void j(boolean z) {
        this.nh = z;
        if (!this.nh) {
            this.mA.b((cq) null);
            this.mX.a(this.na);
        } else {
            this.mX.a(null);
            this.mA.b(this.na);
        }
        boolean z2 = getNavigationMode() == 2;
        if (this.na != null) {
            if (z2) {
                this.na.setVisibility(0);
                if (this.mW != null) {
                    android.support.v4.view.aa.k(this.mW);
                }
            } else {
                this.na.setVisibility(8);
            }
        }
        this.mA.L(!this.nh && z2);
        this.mW.F(!this.nh && z2);
    }

    void cb() {
        if (this.ng != null) {
            this.ng.c(this.nf);
            this.nf = null;
            this.ng = null;
        }
    }

    @Override // android.support.v7.widget.g
    public void onWindowVisibilityChanged(int i) {
        this.ni = i;
    }

    @Override // android.support.v7.app.a
    public void h(boolean z) {
        this.np = z;
        if (!z && this.no != null) {
            this.no.cancel();
        }
    }

    @Override // android.support.v7.app.a
    public void i(boolean z) {
        if (z != this.mD) {
            this.mD = z;
            int size = this.mE.size();
            for (int i = 0; i < size; i++) {
                this.mE.get(i).onMenuVisibilityChanged(z);
            }
        }
    }

    public void setDisplayHomeAsUpEnabled(boolean z) {
        setDisplayOptions(z ? 4 : 0, 4);
    }

    @Override // android.support.v7.app.a
    public void setHomeButtonEnabled(boolean z) {
        this.mA.setHomeButtonEnabled(z);
    }

    @Override // android.support.v7.app.a
    public void h(CharSequence charSequence) {
        this.mA.h(charSequence);
    }

    public void setDisplayOptions(int i, int i2) {
        int displayOptions = this.mA.getDisplayOptions();
        if ((i2 & 4) != 0) {
            this.nd = true;
        }
        this.mA.setDisplayOptions((displayOptions & (i2 ^ (-1))) | (i & i2));
    }

    public int getNavigationMode() {
        return this.mA.getNavigationMode();
    }

    @Override // android.support.v7.app.a
    public int getDisplayOptions() {
        return this.mA.getDisplayOptions();
    }

    @Override // android.support.v7.app.a
    public android.support.v7.c.b a(android.support.v7.c.c cVar) {
        if (this.ne != null) {
            this.ne.finish();
        }
        this.mW.setHideOnContentScrollEnabled(false);
        this.mY.dH();
        bl blVar = new bl(this, this.mY.getContext(), cVar);
        if (!blVar.cj()) {
            return null;
        }
        this.ne = blVar;
        blVar.invalidate();
        this.mY.e(blVar);
        o(true);
        this.mY.sendAccessibilityEvent(32);
        return blVar;
    }

    @Override // android.support.v7.widget.g
    public void k(boolean z) {
        this.nj = z;
    }

    private void cc() {
        if (!this.nm) {
            this.nm = true;
            if (this.mW != null) {
                this.mW.G(true);
            }
            l(false);
        }
    }

    @Override // android.support.v7.widget.g
    public void cd() {
        if (this.nl) {
            this.nl = false;
            l(true);
        }
    }

    private void ce() {
        if (this.nm) {
            this.nm = false;
            if (this.mW != null) {
                this.mW.G(false);
            }
            l(false);
        }
    }

    @Override // android.support.v7.widget.g
    public void cf() {
        if (!this.nl) {
            this.nl = true;
            l(true);
        }
    }

    @Override // android.support.v7.app.a
    public void setHideOnContentScrollEnabled(boolean z) {
        if (z && !this.mW.dI()) {
            throw new IllegalStateException("Action bar must be in overlay mode (Window.FEATURE_OVERLAY_ACTION_BAR) to enable hide on content scroll");
        }
        this.nq = z;
        this.mW.setHideOnContentScrollEnabled(z);
    }

    static boolean a(boolean z, boolean z2, boolean z3) {
        if (z3) {
            return true;
        }
        return (z || z2) ? false : true;
    }

    private void l(boolean z) {
        if (a(this.nk, this.nl, this.nm)) {
            if (!this.nn) {
                this.nn = true;
                m(z);
                return;
            }
            return;
        }
        if (this.nn) {
            this.nn = false;
            n(z);
        }
    }

    public void m(boolean z) {
        if (this.no != null) {
            this.no.cancel();
        }
        this.mX.setVisibility(0);
        if (this.ni == 0 && (this.np || z)) {
            this.mX.setTranslationY(0.0f);
            float f = -this.mX.getHeight();
            if (z) {
                int[] iArr = {0, 0};
                this.mX.getLocationInWindow(iArr);
                f -= iArr[1];
            }
            this.mX.setTranslationY(f);
            android.support.v7.c.l lVar = new android.support.v7.c.l();
            android.support.v4.view.ar arVarB = android.support.v4.view.aa.h(this.mX).b(0.0f);
            arVarB.a(this.nt);
            lVar.a(arVarB);
            if (this.nj && this.mZ != null) {
                this.mZ.setTranslationY(f);
                lVar.a(android.support.v4.view.aa.h(this.mZ).b(0.0f));
            }
            lVar.b(mU);
            lVar.g(250L);
            lVar.b(this.ns);
            this.no = lVar;
            lVar.start();
        } else {
            this.mX.setAlpha(1.0f);
            this.mX.setTranslationY(0.0f);
            if (this.nj && this.mZ != null) {
                this.mZ.setTranslationY(0.0f);
            }
            this.ns.s(null);
        }
        if (this.mW != null) {
            android.support.v4.view.aa.k(this.mW);
        }
    }

    public void n(boolean z) {
        if (this.no != null) {
            this.no.cancel();
        }
        if (this.ni == 0 && (this.np || z)) {
            this.mX.setAlpha(1.0f);
            this.mX.C(true);
            android.support.v7.c.l lVar = new android.support.v7.c.l();
            float f = -this.mX.getHeight();
            if (z) {
                int[] iArr = {0, 0};
                this.mX.getLocationInWindow(iArr);
                f -= iArr[1];
            }
            android.support.v4.view.ar arVarB = android.support.v4.view.aa.h(this.mX).b(f);
            arVarB.a(this.nt);
            lVar.a(arVarB);
            if (this.nj && this.mZ != null) {
                lVar.a(android.support.v4.view.aa.h(this.mZ).b(f));
            }
            lVar.b(mT);
            lVar.g(250L);
            lVar.b(this.nr);
            this.no = lVar;
            lVar.start();
            return;
        }
        this.nr.s(null);
    }

    public void o(boolean z) {
        android.support.v4.view.ar arVarA;
        android.support.v4.view.ar arVarA2;
        if (z) {
            cc();
        } else {
            ce();
        }
        if (cg()) {
            if (z) {
                arVarA2 = this.mA.a(4, 100L);
                arVarA = this.mY.a(0, 200L);
            } else {
                arVarA = this.mA.a(0, 200L);
                arVarA2 = this.mY.a(8, 100L);
            }
            android.support.v7.c.l lVar = new android.support.v7.c.l();
            lVar.a(arVarA2, arVarA);
            lVar.start();
            return;
        }
        if (z) {
            this.mA.setVisibility(4);
            this.mY.setVisibility(0);
        } else {
            this.mA.setVisibility(0);
            this.mY.setVisibility(8);
        }
    }

    private boolean cg() {
        return android.support.v4.view.aa.p(this.mX);
    }

    @Override // android.support.v7.app.a
    public Context getThemedContext() {
        if (this.mV == null) {
            TypedValue typedValue = new TypedValue();
            this.mContext.getTheme().resolveAttribute(android.support.v7.appcompat.R.attr.actionBarWidgetTheme, typedValue, true);
            int i = typedValue.resourceId;
            if (i != 0) {
                this.mV = new ContextThemeWrapper(this.mContext, i);
            } else {
                this.mV = this.mContext;
            }
        }
        return this.mV;
    }

    @Override // android.support.v7.widget.g
    public void ch() {
        if (this.no != null) {
            this.no.cancel();
            this.no = null;
        }
    }

    @Override // android.support.v7.widget.g
    public void ci() {
    }

    @Override // android.support.v7.app.a
    public boolean collapseActionView() {
        if (this.mA == null || !this.mA.hasExpandedActionView()) {
            return false;
        }
        this.mA.collapseActionView();
        return true;
    }

    @Override // android.support.v7.app.a
    public void g(boolean z) {
        if (!this.nd) {
            setDisplayHomeAsUpEnabled(z);
        }
    }

    @Override // android.support.v7.app.a
    public boolean onKeyShortcut(int i, KeyEvent keyEvent) {
        Menu menu;
        if (this.ne == null || (menu = this.ne.getMenu()) == null) {
            return false;
        }
        menu.setQwertyMode(KeyCharacterMap.load(keyEvent != null ? keyEvent.getDeviceId() : -1).getKeyboardType() != 1);
        return menu.performShortcut(i, keyEvent, 0);
    }
}
