package android.support.v7.app;

import android.R;
import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.content.res.Configuration;
import android.content.res.Resources;
import android.content.res.TypedArray;
import android.graphics.Rect;
import android.media.AudioManager;
import android.os.Build;
import android.os.Bundle;
import android.support.v7.widget.bn;
import android.support.v7.widget.br;
import android.support.v7.widget.dr;
import android.support.v7.widget.ds;
import android.support.v7.widget.du;
import android.text.TextUtils;
import android.util.AndroidRuntimeException;
import android.util.AttributeSet;
import android.util.Log;
import android.util.TypedValue;
import android.view.KeyCharacterMap;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewConfiguration;
import android.view.ViewGroup;
import android.view.ViewParent;
import android.view.Window;
import android.view.WindowManager;
import android.widget.FrameLayout;
import android.widget.PopupWindow;
import android.widget.TextView;
import org.xmlpull.v1.XmlPullParser;

/* JADX INFO: compiled from: AppCompatDelegateImplV9.java */
/* JADX INFO: loaded from: classes.dex */
class aj extends x implements android.support.v7.c.a.t, LayoutInflater.Factory2 {
    private static final boolean ls;
    private TextView jL;
    android.support.v4.view.ar lA;
    private boolean lB;
    private ViewGroup lC;
    private View lD;
    private boolean lE;
    private boolean lF;
    private boolean lG;
    private av[] lH;
    private av lI;
    private boolean lJ;
    boolean lK;
    int lL;
    private final Runnable lM;
    private boolean lN;
    private Rect lO;
    private Rect lP;
    private ay lQ;
    private bn lt;
    private ar lu;
    private aw lv;
    android.support.v7.c.b lw;
    android.support.v7.widget.d lx;
    PopupWindow ly;
    Runnable lz;

    static {
        ls = Build.VERSION.SDK_INT < 21;
    }

    aj(Context context, Window window, v vVar) {
        super(context, window, vVar);
        this.lA = null;
        this.lM = new ak(this);
    }

    @Override // android.support.v7.app.w
    public void onCreate(Bundle bundle) {
        if ((this.kR instanceof Activity) && android.support.v4.app.bn.g((Activity) this.kR) != null) {
            a aVarBE = bE();
            if (aVarBE == null) {
                this.lN = true;
            } else {
                aVarBE.g(true);
            }
        }
    }

    @Override // android.support.v7.app.w
    public void onPostCreate(Bundle bundle) {
        bN();
    }

    @Override // android.support.v7.app.x
    public void bD() {
        bN();
        if (this.kW && this.kU == null) {
            if (this.kR instanceof Activity) {
                this.kU = new bh((Activity) this.kR, this.kX);
            } else if (this.kR instanceof Dialog) {
                this.kU = new bh((Dialog) this.kR);
            }
            if (this.kU != null) {
                this.kU.g(this.lN);
            }
        }
    }

    @Override // android.support.v7.app.w
    public <T extends View> T findViewById(int i) {
        bN();
        return (T) this.jo.findViewById(i);
    }

    @Override // android.support.v7.app.w
    public void onConfigurationChanged(Configuration configuration) {
        a aVarBv;
        if (this.kW && this.lB && (aVarBv = bv()) != null) {
            aVarBv.onConfigurationChanged(configuration);
        }
        android.support.v7.widget.ac.eg().n(this.mContext);
        bA();
    }

    @Override // android.support.v7.app.x, android.support.v7.app.w
    public void onStop() {
        a aVarBv = bv();
        if (aVarBv != null) {
            aVarBv.h(false);
        }
    }

    @Override // android.support.v7.app.w
    public void onPostResume() {
        a aVarBv = bv();
        if (aVarBv != null) {
            aVarBv.h(true);
        }
    }

    @Override // android.support.v7.app.w
    public void setContentView(View view) {
        bN();
        ViewGroup viewGroup = (ViewGroup) this.lC.findViewById(R.id.content);
        viewGroup.removeAllViews();
        viewGroup.addView(view);
        this.kR.onContentChanged();
    }

    @Override // android.support.v7.app.w
    public void setContentView(int i) {
        bN();
        ViewGroup viewGroup = (ViewGroup) this.lC.findViewById(R.id.content);
        viewGroup.removeAllViews();
        LayoutInflater.from(this.mContext).inflate(i, viewGroup);
        this.kR.onContentChanged();
    }

    @Override // android.support.v7.app.w
    public void setContentView(View view, ViewGroup.LayoutParams layoutParams) {
        bN();
        ViewGroup viewGroup = (ViewGroup) this.lC.findViewById(R.id.content);
        viewGroup.removeAllViews();
        viewGroup.addView(view, layoutParams);
        this.kR.onContentChanged();
    }

    @Override // android.support.v7.app.w
    public void addContentView(View view, ViewGroup.LayoutParams layoutParams) {
        bN();
        ((ViewGroup) this.lC.findViewById(R.id.content)).addView(view, layoutParams);
        this.kR.onContentChanged();
    }

    @Override // android.support.v7.app.x, android.support.v7.app.w
    public void onDestroy() {
        if (this.lK) {
            this.jo.getDecorView().removeCallbacks(this.lM);
        }
        super.onDestroy();
        if (this.kU != null) {
            this.kU.onDestroy();
        }
    }

    private void bN() {
        if (!this.lB) {
            this.lC = bO();
            CharSequence title = getTitle();
            if (!TextUtils.isEmpty(title)) {
                j(title);
            }
            bP();
            f(this.lC);
            this.lB = true;
            av avVarD = d(0, false);
            if (isDestroyed()) {
                return;
            }
            if (avVarD == null || avVarD.lZ == null) {
                invalidatePanelMenu(108);
            }
        }
    }

    private ViewGroup bO() {
        ViewGroup viewGroup;
        ViewGroup viewGroup2;
        Context eVar;
        TypedArray typedArrayObtainStyledAttributes = this.mContext.obtainStyledAttributes(android.support.v7.appcompat.R.styleable.AppCompatTheme);
        if (!typedArrayObtainStyledAttributes.hasValue(android.support.v7.appcompat.R.styleable.AppCompatTheme_windowActionBar)) {
            typedArrayObtainStyledAttributes.recycle();
            throw new IllegalStateException("You need to use a Theme.AppCompat theme (or descendant) with this activity.");
        }
        if (typedArrayObtainStyledAttributes.getBoolean(android.support.v7.appcompat.R.styleable.AppCompatTheme_windowNoTitle, false)) {
            requestWindowFeature(1);
        } else if (typedArrayObtainStyledAttributes.getBoolean(android.support.v7.appcompat.R.styleable.AppCompatTheme_windowActionBar, false)) {
            requestWindowFeature(108);
        }
        if (typedArrayObtainStyledAttributes.getBoolean(android.support.v7.appcompat.R.styleable.AppCompatTheme_windowActionBarOverlay, false)) {
            requestWindowFeature(109);
        }
        if (typedArrayObtainStyledAttributes.getBoolean(android.support.v7.appcompat.R.styleable.AppCompatTheme_windowActionModeOverlay, false)) {
            requestWindowFeature(10);
        }
        this.kZ = typedArrayObtainStyledAttributes.getBoolean(android.support.v7.appcompat.R.styleable.AppCompatTheme_android_windowIsFloating, false);
        typedArrayObtainStyledAttributes.recycle();
        this.jo.getDecorView();
        LayoutInflater layoutInflaterFrom = LayoutInflater.from(this.mContext);
        if (!this.la) {
            if (this.kZ) {
                ViewGroup viewGroup3 = (ViewGroup) layoutInflaterFrom.inflate(android.support.v7.appcompat.R.layout.abc_dialog_title_material, (ViewGroup) null);
                this.kX = false;
                this.kW = false;
                viewGroup2 = viewGroup3;
            } else if (this.kW) {
                TypedValue typedValue = new TypedValue();
                this.mContext.getTheme().resolveAttribute(android.support.v7.appcompat.R.attr.actionBarTheme, typedValue, true);
                if (typedValue.resourceId != 0) {
                    eVar = new android.support.v7.c.e(this.mContext, typedValue.resourceId);
                } else {
                    eVar = this.mContext;
                }
                ViewGroup viewGroup4 = (ViewGroup) LayoutInflater.from(eVar).inflate(android.support.v7.appcompat.R.layout.abc_screen_toolbar, (ViewGroup) null);
                this.lt = (bn) viewGroup4.findViewById(android.support.v7.appcompat.R.id.decor_content_parent);
                this.lt.b(bH());
                if (this.kX) {
                    this.lt.al(109);
                }
                if (this.lE) {
                    this.lt.al(2);
                }
                if (this.lF) {
                    this.lt.al(5);
                }
                viewGroup2 = viewGroup4;
            } else {
                viewGroup2 = null;
            }
        } else {
            if (this.kY) {
                viewGroup = (ViewGroup) layoutInflaterFrom.inflate(android.support.v7.appcompat.R.layout.abc_screen_simple_overlay_action_mode, (ViewGroup) null);
            } else {
                viewGroup = (ViewGroup) layoutInflaterFrom.inflate(android.support.v7.appcompat.R.layout.abc_screen_simple, (ViewGroup) null);
            }
            if (Build.VERSION.SDK_INT >= 21) {
                android.support.v4.view.aa.a(viewGroup, new al(this));
                viewGroup2 = viewGroup;
            } else {
                ((br) viewGroup).a(new am(this));
                viewGroup2 = viewGroup;
            }
        }
        if (viewGroup2 == null) {
            throw new IllegalArgumentException("AppCompat does not support the current theme features: { windowActionBar: " + this.kW + ", windowActionBarOverlay: " + this.kX + ", android:windowIsFloating: " + this.kZ + ", windowActionModeOverlay: " + this.kY + ", windowNoTitle: " + this.la + " }");
        }
        if (this.lt == null) {
            this.jL = (TextView) viewGroup2.findViewById(android.support.v7.appcompat.R.id.title);
        }
        du.O(viewGroup2);
        android.support.v7.widget.bl blVar = (android.support.v7.widget.bl) viewGroup2.findViewById(android.support.v7.appcompat.R.id.action_bar_activity_content);
        ViewGroup viewGroup5 = (ViewGroup) this.jo.findViewById(R.id.content);
        if (viewGroup5 != null) {
            while (viewGroup5.getChildCount() > 0) {
                View childAt = viewGroup5.getChildAt(0);
                viewGroup5.removeViewAt(0);
                blVar.addView(childAt);
            }
            viewGroup5.setId(-1);
            blVar.setId(R.id.content);
            if (viewGroup5 instanceof FrameLayout) {
                ((FrameLayout) viewGroup5).setForeground(null);
            }
        }
        this.jo.setContentView(viewGroup2);
        blVar.a(new an(this));
        return viewGroup2;
    }

    void f(ViewGroup viewGroup) {
    }

    private void bP() {
        android.support.v7.widget.bl blVar = (android.support.v7.widget.bl) this.lC.findViewById(R.id.content);
        View decorView = this.jo.getDecorView();
        blVar.c(decorView.getPaddingLeft(), decorView.getPaddingTop(), decorView.getPaddingRight(), decorView.getPaddingBottom());
        TypedArray typedArrayObtainStyledAttributes = this.mContext.obtainStyledAttributes(android.support.v7.appcompat.R.styleable.AppCompatTheme);
        typedArrayObtainStyledAttributes.getValue(android.support.v7.appcompat.R.styleable.AppCompatTheme_windowMinWidthMajor, blVar.ey());
        typedArrayObtainStyledAttributes.getValue(android.support.v7.appcompat.R.styleable.AppCompatTheme_windowMinWidthMinor, blVar.ez());
        if (typedArrayObtainStyledAttributes.hasValue(android.support.v7.appcompat.R.styleable.AppCompatTheme_windowFixedWidthMajor)) {
            typedArrayObtainStyledAttributes.getValue(android.support.v7.appcompat.R.styleable.AppCompatTheme_windowFixedWidthMajor, blVar.eA());
        }
        if (typedArrayObtainStyledAttributes.hasValue(android.support.v7.appcompat.R.styleable.AppCompatTheme_windowFixedWidthMinor)) {
            typedArrayObtainStyledAttributes.getValue(android.support.v7.appcompat.R.styleable.AppCompatTheme_windowFixedWidthMinor, blVar.eB());
        }
        if (typedArrayObtainStyledAttributes.hasValue(android.support.v7.appcompat.R.styleable.AppCompatTheme_windowFixedHeightMajor)) {
            typedArrayObtainStyledAttributes.getValue(android.support.v7.appcompat.R.styleable.AppCompatTheme_windowFixedHeightMajor, blVar.eC());
        }
        if (typedArrayObtainStyledAttributes.hasValue(android.support.v7.appcompat.R.styleable.AppCompatTheme_windowFixedHeightMinor)) {
            typedArrayObtainStyledAttributes.getValue(android.support.v7.appcompat.R.styleable.AppCompatTheme_windowFixedHeightMinor, blVar.eD());
        }
        typedArrayObtainStyledAttributes.recycle();
        blVar.requestLayout();
    }

    @Override // android.support.v7.app.w
    public boolean requestWindowFeature(int i) {
        int iU = U(i);
        if (this.la && iU == 108) {
            return false;
        }
        if (this.kW && iU == 1) {
            this.kW = false;
        }
        switch (iU) {
            case 1:
                bT();
                this.la = true;
                return true;
            case 2:
                bT();
                this.lE = true;
                return true;
            case 5:
                bT();
                this.lF = true;
                return true;
            case com.facebook.R.styleable.CardView_contentPaddingRight /* 10 */:
                bT();
                this.kY = true;
                return true;
            case 108:
                bT();
                this.kW = true;
                return true;
            case 109:
                bT();
                this.kX = true;
                return true;
            default:
                return this.jo.requestFeature(iU);
        }
    }

    @Override // android.support.v7.app.x
    void j(CharSequence charSequence) {
        if (this.lt != null) {
            this.lt.h(charSequence);
        } else if (bE() != null) {
            bE().h(charSequence);
        } else if (this.jL != null) {
            this.jL.setText(charSequence);
        }
    }

    @Override // android.support.v7.app.x
    void onPanelClosed(int i, Menu menu) {
        if (i == 108) {
            a aVarBv = bv();
            if (aVarBv != null) {
                aVarBv.i(false);
                return;
            }
            return;
        }
        if (i == 0) {
            av avVarD = d(i, true);
            if (avVarD.me) {
                a(avVarD, false);
            }
        }
    }

    @Override // android.support.v7.app.x
    boolean onMenuOpened(int i, Menu menu) {
        if (i != 108) {
            return false;
        }
        a aVarBv = bv();
        if (aVarBv == null) {
            return true;
        }
        aVarBv.i(true);
        return true;
    }

    @Override // android.support.v7.c.a.t
    public boolean a(android.support.v7.c.a.s sVar, MenuItem menuItem) {
        av avVarA;
        Window.Callback callbackBH = bH();
        if (callbackBH == null || isDestroyed() || (avVarA = a((Menu) sVar.dk())) == null) {
            return false;
        }
        return callbackBH.onMenuItemSelected(avVarA.lV, menuItem);
    }

    @Override // android.support.v7.c.a.t
    public void a(android.support.v7.c.a.s sVar) {
        a(sVar, true);
    }

    public android.support.v7.c.b d(android.support.v7.c.c cVar) {
        if (cVar == null) {
            throw new IllegalArgumentException("ActionMode callback can not be null.");
        }
        if (this.lw != null) {
            this.lw.finish();
        }
        as asVar = new as(this, cVar);
        a aVarBv = bv();
        if (aVarBv != null) {
            this.lw = aVarBv.a(asVar);
            if (this.lw != null && this.kT != null) {
                this.kT.a(this.lw);
            }
        }
        if (this.lw == null) {
            this.lw = c(asVar);
        }
        return this.lw;
    }

    @Override // android.support.v7.app.w
    public void invalidateOptionsMenu() {
        a aVarBv = bv();
        if (aVarBv == null || !aVarBv.bq()) {
            invalidatePanelMenu(0);
        }
    }

    @Override // android.support.v7.app.x
    android.support.v7.c.b c(android.support.v7.c.c cVar) {
        android.support.v7.c.b bVarB;
        Context eVar;
        bR();
        if (this.lw != null) {
            this.lw.finish();
        }
        if (!(cVar instanceof as)) {
            cVar = new as(this, cVar);
        }
        if (this.kT == null || isDestroyed()) {
            bVarB = null;
        } else {
            try {
                bVarB = this.kT.b(cVar);
            } catch (AbstractMethodError e) {
                bVarB = null;
            }
        }
        if (bVarB != null) {
            this.lw = bVarB;
        } else {
            if (this.lx == null) {
                if (this.kZ) {
                    TypedValue typedValue = new TypedValue();
                    Resources.Theme theme = this.mContext.getTheme();
                    theme.resolveAttribute(android.support.v7.appcompat.R.attr.actionBarTheme, typedValue, true);
                    if (typedValue.resourceId != 0) {
                        Resources.Theme themeNewTheme = this.mContext.getResources().newTheme();
                        themeNewTheme.setTo(theme);
                        themeNewTheme.applyStyle(typedValue.resourceId, true);
                        eVar = new android.support.v7.c.e(this.mContext, 0);
                        eVar.getTheme().setTo(themeNewTheme);
                    } else {
                        eVar = this.mContext;
                    }
                    this.lx = new android.support.v7.widget.d(eVar);
                    this.ly = new PopupWindow(eVar, (AttributeSet) null, android.support.v7.appcompat.R.attr.actionModePopupWindowStyle);
                    android.support.v4.widget.t.a(this.ly, 2);
                    this.ly.setContentView(this.lx);
                    this.ly.setWidth(-1);
                    eVar.getTheme().resolveAttribute(android.support.v7.appcompat.R.attr.actionBarSize, typedValue, true);
                    this.lx.aj(TypedValue.complexToDimensionPixelSize(typedValue.data, eVar.getResources().getDisplayMetrics()));
                    this.ly.setHeight(-2);
                    this.lz = new ao(this);
                } else {
                    ds dsVar = (ds) this.lC.findViewById(android.support.v7.appcompat.R.id.action_mode_bar_stub);
                    if (dsVar != null) {
                        dsVar.setLayoutInflater(LayoutInflater.from(bF()));
                        this.lx = (android.support.v7.widget.d) dsVar.inflate();
                    }
                }
            }
            if (this.lx != null) {
                bR();
                this.lx.dH();
                android.support.v7.c.f fVar = new android.support.v7.c.f(this.lx.getContext(), this.lx, cVar, this.ly == null);
                if (cVar.a(fVar, fVar.getMenu())) {
                    fVar.invalidate();
                    this.lx.e(fVar);
                    this.lw = fVar;
                    if (bQ()) {
                        this.lx.setAlpha(0.0f);
                        this.lA = android.support.v4.view.aa.h(this.lx).a(1.0f);
                        this.lA.a(new aq(this));
                    } else {
                        this.lx.setAlpha(1.0f);
                        this.lx.setVisibility(0);
                        this.lx.sendAccessibilityEvent(32);
                        if (this.lx.getParent() instanceof View) {
                            android.support.v4.view.aa.k((View) this.lx.getParent());
                        }
                    }
                    if (this.ly != null) {
                        this.jo.getDecorView().post(this.lz);
                    }
                } else {
                    this.lw = null;
                }
            }
        }
        if (this.lw != null && this.kT != null) {
            this.kT.a(this.lw);
        }
        return this.lw;
    }

    final boolean bQ() {
        return this.lB && this.lC != null && android.support.v4.view.aa.p(this.lC);
    }

    void bR() {
        if (this.lA != null) {
            this.lA.cancel();
        }
    }

    boolean bS() {
        if (this.lw != null) {
            this.lw.finish();
            return true;
        }
        a aVarBv = bv();
        return aVarBv != null && aVarBv.collapseActionView();
    }

    @Override // android.support.v7.app.x
    boolean onKeyShortcut(int i, KeyEvent keyEvent) {
        a aVarBv = bv();
        if (aVarBv != null && aVarBv.onKeyShortcut(i, keyEvent)) {
            return true;
        }
        if (this.lI != null && a(this.lI, keyEvent.getKeyCode(), keyEvent, 1)) {
            if (this.lI == null) {
                return true;
            }
            this.lI.md = true;
            return true;
        }
        if (this.lI == null) {
            av avVarD = d(0, true);
            b(avVarD, keyEvent);
            boolean zA = a(avVarD, keyEvent.getKeyCode(), keyEvent, 1);
            avVarD.mc = false;
            if (zA) {
                return true;
            }
        }
        return false;
    }

    @Override // android.support.v7.app.x
    boolean dispatchKeyEvent(KeyEvent keyEvent) {
        if (keyEvent.getKeyCode() == 82 && this.kR.dispatchKeyEvent(keyEvent)) {
            return true;
        }
        int keyCode = keyEvent.getKeyCode();
        return keyEvent.getAction() == 0 ? onKeyDown(keyCode, keyEvent) : onKeyUp(keyCode, keyEvent);
    }

    boolean onKeyUp(int i, KeyEvent keyEvent) {
        switch (i) {
            case 4:
                boolean z = this.lJ;
                this.lJ = false;
                av avVarD = d(0, false);
                if (avVarD != null && avVarD.me) {
                    if (z) {
                        return true;
                    }
                    a(avVarD, true);
                    return true;
                }
                if (bS()) {
                    return true;
                }
                break;
            case 82:
                c(0, keyEvent);
                return true;
        }
        return false;
    }

    boolean onKeyDown(int i, KeyEvent keyEvent) {
        switch (i) {
            case 4:
                this.lJ = (keyEvent.getFlags() & 128) != 0;
                break;
            case 82:
                b(0, keyEvent);
                return true;
        }
        if (Build.VERSION.SDK_INT < 11) {
            onKeyShortcut(i, keyEvent);
        }
        return false;
    }

    /* JADX WARN: Multi-variable type inference failed */
    public View b(View view, String str, Context context, AttributeSet attributeSet) {
        boolean z;
        boolean zA;
        if (this.lQ == null) {
            this.lQ = new ay();
        }
        if (ls) {
            if (attributeSet instanceof XmlPullParser) {
                zA = ((XmlPullParser) attributeSet).getDepth() > 1;
            } else {
                zA = a((ViewParent) view);
            }
            z = zA;
        } else {
            z = false;
        }
        return this.lQ.a(view, str, context, attributeSet, z, ls, true, dr.fp());
    }

    private boolean a(ViewParent viewParent) {
        if (viewParent == null) {
            return false;
        }
        View decorView = this.jo.getDecorView();
        for (ViewParent parent = viewParent; parent != null; parent = parent.getParent()) {
            if (parent == decorView || !(parent instanceof View) || android.support.v4.view.aa.isAttachedToWindow((View) parent)) {
                return false;
            }
        }
        return true;
    }

    @Override // android.support.v7.app.w
    public void bz() {
        LayoutInflater layoutInflaterFrom = LayoutInflater.from(this.mContext);
        if (layoutInflaterFrom.getFactory() == null) {
            android.support.v4.view.k.b(layoutInflaterFrom, this);
        } else if (!(layoutInflaterFrom.getFactory2() instanceof aj)) {
            Log.i("AppCompatDelegate", "The Activity's LayoutInflater already has a Factory installed so we can not install AppCompat's");
        }
    }

    @Override // android.view.LayoutInflater.Factory2
    public final View onCreateView(View view, String str, Context context, AttributeSet attributeSet) {
        View viewA = a(view, str, context, attributeSet);
        return viewA != null ? viewA : b(view, str, context, attributeSet);
    }

    @Override // android.view.LayoutInflater.Factory
    public View onCreateView(String str, Context context, AttributeSet attributeSet) {
        return onCreateView(null, str, context, attributeSet);
    }

    View a(View view, String str, Context context, AttributeSet attributeSet) {
        View viewOnCreateView;
        if (!(this.kR instanceof LayoutInflater.Factory) || (viewOnCreateView = ((LayoutInflater.Factory) this.kR).onCreateView(str, context, attributeSet)) == null) {
            return null;
        }
        return viewOnCreateView;
    }

    private void a(av avVar, KeyEvent keyEvent) {
        ViewGroup.LayoutParams layoutParams;
        int i = -1;
        if (!avVar.me && !isDestroyed()) {
            if (avVar.lV == 0) {
                Context context = this.mContext;
                boolean z = (context.getResources().getConfiguration().screenLayout & 15) == 4;
                boolean z2 = context.getApplicationInfo().targetSdkVersion >= 11;
                if (z && z2) {
                    return;
                }
            }
            Window.Callback callbackBH = bH();
            if (callbackBH != null && !callbackBH.onMenuOpened(avVar.lV, avVar.lZ)) {
                a(avVar, true);
                return;
            }
            WindowManager windowManager = (WindowManager) this.mContext.getSystemService("window");
            if (windowManager != null && b(avVar, keyEvent)) {
                if (avVar.lW == null || avVar.mg) {
                    if (avVar.lW == null) {
                        if (!a(avVar) || avVar.lW == null) {
                            return;
                        }
                    } else if (avVar.mg && avVar.lW.getChildCount() > 0) {
                        avVar.lW.removeAllViews();
                    }
                    if (c(avVar) && avVar.bW()) {
                        ViewGroup.LayoutParams layoutParams2 = avVar.lX.getLayoutParams();
                        ViewGroup.LayoutParams layoutParams3 = layoutParams2 == null ? new ViewGroup.LayoutParams(-2, -2) : layoutParams2;
                        avVar.lW.setBackgroundResource(avVar.background);
                        ViewParent parent = avVar.lX.getParent();
                        if (parent != null && (parent instanceof ViewGroup)) {
                            ((ViewGroup) parent).removeView(avVar.lX);
                        }
                        avVar.lW.addView(avVar.lX, layoutParams3);
                        if (!avVar.lX.hasFocus()) {
                            avVar.lX.requestFocus();
                        }
                        i = -2;
                    } else {
                        return;
                    }
                } else if (avVar.lY == null || (layoutParams = avVar.lY.getLayoutParams()) == null || layoutParams.width != -1) {
                    i = -2;
                }
                avVar.md = false;
                WindowManager.LayoutParams layoutParams4 = new WindowManager.LayoutParams(i, -2, avVar.x, avVar.y, 1002, 8519680, -3);
                layoutParams4.gravity = avVar.gravity;
                layoutParams4.windowAnimations = avVar.windowAnimations;
                windowManager.addView(avVar.lW, layoutParams4);
                avVar.me = true;
            }
        }
    }

    private boolean a(av avVar) {
        avVar.k(bF());
        avVar.lW = new au(this, avVar.mb);
        avVar.gravity = 81;
        return true;
    }

    private void a(android.support.v7.c.a.s sVar, boolean z) {
        if (this.lt != null && this.lt.dR() && (!ViewConfiguration.get(this.mContext).hasPermanentMenuKey() || this.lt.dS())) {
            Window.Callback callbackBH = bH();
            if (!this.lt.isOverflowMenuShowing() || !z) {
                if (callbackBH != null && !isDestroyed()) {
                    if (this.lK && (this.lL & 1) != 0) {
                        this.jo.getDecorView().removeCallbacks(this.lM);
                        this.lM.run();
                    }
                    av avVarD = d(0, true);
                    if (avVarD.lZ != null && !avVarD.mh && callbackBH.onPreparePanel(0, avVarD.lY, avVarD.lZ)) {
                        callbackBH.onMenuOpened(108, avVarD.lZ);
                        this.lt.showOverflowMenu();
                        return;
                    }
                    return;
                }
                return;
            }
            this.lt.hideOverflowMenu();
            if (!isDestroyed()) {
                callbackBH.onPanelClosed(108, d(0, true).lZ);
                return;
            }
            return;
        }
        av avVarD2 = d(0, true);
        avVarD2.mg = true;
        a(avVarD2, false);
        a(avVarD2, (KeyEvent) null);
    }

    /* JADX WARN: Code duplicated, block: B:22:0x0071  */
    private boolean b(av avVar) {
        Context eVar;
        Context context = this.mContext;
        if ((avVar.lV == 0 || avVar.lV == 108) && this.lt != null) {
            TypedValue typedValue = new TypedValue();
            Resources.Theme theme = context.getTheme();
            theme.resolveAttribute(android.support.v7.appcompat.R.attr.actionBarTheme, typedValue, true);
            Resources.Theme themeNewTheme = null;
            if (typedValue.resourceId != 0) {
                themeNewTheme = context.getResources().newTheme();
                themeNewTheme.setTo(theme);
                themeNewTheme.applyStyle(typedValue.resourceId, true);
                themeNewTheme.resolveAttribute(android.support.v7.appcompat.R.attr.actionBarWidgetTheme, typedValue, true);
            } else {
                theme.resolveAttribute(android.support.v7.appcompat.R.attr.actionBarWidgetTheme, typedValue, true);
            }
            if (typedValue.resourceId != 0) {
                if (themeNewTheme == null) {
                    themeNewTheme = context.getResources().newTheme();
                    themeNewTheme.setTo(theme);
                }
                themeNewTheme.applyStyle(typedValue.resourceId, true);
            }
            Resources.Theme theme2 = themeNewTheme;
            if (theme2 != null) {
                eVar = new android.support.v7.c.e(context, 0);
                eVar.getTheme().setTo(theme2);
            } else {
                eVar = context;
            }
        } else {
            eVar = context;
        }
        android.support.v7.c.a.s sVar = new android.support.v7.c.a.s(eVar);
        sVar.a(this);
        avVar.d(sVar);
        return true;
    }

    private boolean c(av avVar) {
        if (avVar.lY != null) {
            avVar.lX = avVar.lY;
            return true;
        }
        if (avVar.lZ == null) {
            return false;
        }
        if (this.lv == null) {
            this.lv = new aw(this);
        }
        avVar.lX = (View) avVar.a(this.lv);
        return avVar.lX != null;
    }

    private boolean b(av avVar, KeyEvent keyEvent) {
        if (isDestroyed()) {
            return false;
        }
        if (avVar.mc) {
            return true;
        }
        if (this.lI != null && this.lI != avVar) {
            a(this.lI, false);
        }
        Window.Callback callbackBH = bH();
        if (callbackBH != null) {
            avVar.lY = callbackBH.onCreatePanelView(avVar.lV);
        }
        boolean z = avVar.lV == 0 || avVar.lV == 108;
        if (z && this.lt != null) {
            this.lt.dT();
        }
        if (avVar.lY == null && (!z || !(bE() instanceof bb))) {
            if (avVar.lZ == null || avVar.mh) {
                if (avVar.lZ == null && (!b(avVar) || avVar.lZ == null)) {
                    return false;
                }
                if (z && this.lt != null) {
                    if (this.lu == null) {
                        this.lu = new ar(this);
                    }
                    this.lt.a(avVar.lZ, this.lu);
                }
                avVar.lZ.db();
                if (!callbackBH.onCreatePanelMenu(avVar.lV, avVar.lZ)) {
                    avVar.d(null);
                    if (!z || this.lt == null) {
                        return false;
                    }
                    this.lt.a(null, this.lu);
                    return false;
                }
                avVar.mh = false;
            }
            avVar.lZ.db();
            if (avVar.mi != null) {
                avVar.lZ.c(avVar.mi);
                avVar.mi = null;
            }
            if (!callbackBH.onPreparePanel(0, avVar.lY, avVar.lZ)) {
                if (z && this.lt != null) {
                    this.lt.a(null, this.lu);
                }
                avVar.lZ.dc();
                return false;
            }
            avVar.mf = KeyCharacterMap.load(keyEvent != null ? keyEvent.getDeviceId() : -1).getKeyboardType() != 1;
            avVar.lZ.setQwertyMode(avVar.mf);
            avVar.lZ.dc();
        }
        avVar.mc = true;
        avVar.md = false;
        this.lI = avVar;
        return true;
    }

    void b(android.support.v7.c.a.s sVar) {
        if (!this.lG) {
            this.lG = true;
            this.lt.bU();
            Window.Callback callbackBH = bH();
            if (callbackBH != null && !isDestroyed()) {
                callbackBH.onPanelClosed(108, sVar);
            }
            this.lG = false;
        }
    }

    void closePanel(int i) {
        a(d(i, true), true);
    }

    void a(av avVar, boolean z) {
        if (z && avVar.lV == 0 && this.lt != null && this.lt.isOverflowMenuShowing()) {
            b(avVar.lZ);
            return;
        }
        WindowManager windowManager = (WindowManager) this.mContext.getSystemService("window");
        if (windowManager != null && avVar.me && avVar.lW != null) {
            windowManager.removeView(avVar.lW);
            if (z) {
                a(avVar.lV, avVar, (Menu) null);
            }
        }
        avVar.mc = false;
        avVar.md = false;
        avVar.me = false;
        avVar.lX = null;
        avVar.mg = true;
        if (this.lI == avVar) {
            this.lI = null;
        }
    }

    private boolean b(int i, KeyEvent keyEvent) {
        if (keyEvent.getRepeatCount() == 0) {
            av avVarD = d(i, true);
            if (!avVarD.me) {
                return b(avVarD, keyEvent);
            }
        }
        return false;
    }

    /* JADX WARN: Code duplicated, block: B:40:0x0088  */
    private boolean c(int i, KeyEvent keyEvent) {
        boolean zB;
        boolean zHideOverflowMenu = true;
        if (this.lw != null) {
            return false;
        }
        av avVarD = d(i, true);
        if (i == 0 && this.lt != null && this.lt.dR() && !ViewConfiguration.get(this.mContext).hasPermanentMenuKey()) {
            if (!this.lt.isOverflowMenuShowing()) {
                if (isDestroyed() || !b(avVarD, keyEvent)) {
                    zHideOverflowMenu = false;
                } else {
                    zHideOverflowMenu = this.lt.showOverflowMenu();
                }
            } else {
                zHideOverflowMenu = this.lt.hideOverflowMenu();
            }
        } else if (avVarD.me || avVarD.md) {
            boolean z = avVarD.me;
            a(avVarD, true);
            zHideOverflowMenu = z;
        } else if (avVarD.mc) {
            if (avVarD.mh) {
                avVarD.mc = false;
                zB = b(avVarD, keyEvent);
            } else {
                zB = true;
            }
            if (zB) {
                a(avVarD, keyEvent);
            } else {
                zHideOverflowMenu = false;
            }
        } else {
            zHideOverflowMenu = false;
        }
        if (zHideOverflowMenu) {
            AudioManager audioManager = (AudioManager) this.mContext.getSystemService("audio");
            if (audioManager != null) {
                audioManager.playSoundEffect(0);
            } else {
                Log.w("AppCompatDelegate", "Couldn't get audio manager");
            }
        }
        return zHideOverflowMenu;
    }

    void a(int i, av avVar, Menu menu) {
        if (menu == null) {
            if (avVar == null && i >= 0 && i < this.lH.length) {
                avVar = this.lH[i];
            }
            if (avVar != null) {
                menu = avVar.lZ;
            }
        }
        if ((avVar == null || avVar.me) && !isDestroyed()) {
            this.kR.onPanelClosed(i, menu);
        }
    }

    av a(Menu menu) {
        av[] avVarArr = this.lH;
        int length = avVarArr != null ? avVarArr.length : 0;
        for (int i = 0; i < length; i++) {
            av avVar = avVarArr[i];
            if (avVar != null && avVar.lZ == menu) {
                return avVar;
            }
        }
        return null;
    }

    protected av d(int i, boolean z) {
        av[] avVarArr = this.lH;
        if (avVarArr == null || avVarArr.length <= i) {
            av[] avVarArr2 = new av[i + 1];
            if (avVarArr != null) {
                System.arraycopy(avVarArr, 0, avVarArr2, 0, avVarArr.length);
            }
            this.lH = avVarArr2;
            avVarArr = avVarArr2;
        }
        av avVar = avVarArr[i];
        if (avVar != null) {
            return avVar;
        }
        av avVar2 = new av(i);
        avVarArr[i] = avVar2;
        return avVar2;
    }

    private boolean a(av avVar, int i, KeyEvent keyEvent, int i2) {
        boolean zPerformShortcut = false;
        if (!keyEvent.isSystem()) {
            if ((avVar.mc || b(avVar, keyEvent)) && avVar.lZ != null) {
                zPerformShortcut = avVar.lZ.performShortcut(i, keyEvent, i2);
            }
            if (zPerformShortcut && (i2 & 1) == 0 && this.lt == null) {
                a(avVar, true);
            }
        }
        return zPerformShortcut;
    }

    private void invalidatePanelMenu(int i) {
        this.lL |= 1 << i;
        if (!this.lK) {
            android.support.v4.view.aa.b(this.jo.getDecorView(), this.lM);
            this.lK = true;
        }
    }

    void S(int i) {
        av avVarD;
        av avVarD2 = d(i, true);
        if (avVarD2.lZ != null) {
            Bundle bundle = new Bundle();
            avVarD2.lZ.b(bundle);
            if (bundle.size() > 0) {
                avVarD2.mi = bundle;
            }
            avVarD2.lZ.db();
            avVarD2.lZ.clear();
        }
        avVarD2.mh = true;
        avVarD2.mg = true;
        if ((i == 108 || i == 0) && this.lt != null && (avVarD = d(0, false)) != null) {
            avVarD.mc = false;
            b(avVarD, (KeyEvent) null);
        }
    }

    int T(int i) {
        boolean z;
        boolean z2;
        boolean z3;
        if (this.lx == null || !(this.lx.getLayoutParams() instanceof ViewGroup.MarginLayoutParams)) {
            z = false;
        } else {
            ViewGroup.MarginLayoutParams marginLayoutParams = (ViewGroup.MarginLayoutParams) this.lx.getLayoutParams();
            if (this.lx.isShown()) {
                if (this.lO == null) {
                    this.lO = new Rect();
                    this.lP = new Rect();
                }
                Rect rect = this.lO;
                Rect rect2 = this.lP;
                rect.set(0, i, 0, 0);
                du.a(this.lC, rect, rect2);
                if (marginLayoutParams.topMargin != (rect2.top == 0 ? i : 0)) {
                    marginLayoutParams.topMargin = i;
                    if (this.lD == null) {
                        this.lD = new View(this.mContext);
                        this.lD.setBackgroundColor(this.mContext.getResources().getColor(android.support.v7.appcompat.R.color.abc_input_method_navigation_guard));
                        this.lC.addView(this.lD, -1, new ViewGroup.LayoutParams(-1, i));
                        z2 = true;
                    } else {
                        ViewGroup.LayoutParams layoutParams = this.lD.getLayoutParams();
                        if (layoutParams.height != i) {
                            layoutParams.height = i;
                            this.lD.setLayoutParams(layoutParams);
                        }
                        z2 = true;
                    }
                } else {
                    z2 = false;
                }
                boolean z4 = this.lD != null;
                if (!this.kY && z4) {
                    i = 0;
                }
                z3 = z4;
            } else if (marginLayoutParams.topMargin != 0) {
                marginLayoutParams.topMargin = 0;
                z2 = true;
                z3 = false;
            } else {
                z2 = false;
                z3 = false;
            }
            if (z2) {
                this.lx.setLayoutParams(marginLayoutParams);
            }
            z = z3;
        }
        if (this.lD != null) {
            this.lD.setVisibility(z ? 0 : 8);
        }
        return i;
    }

    private void bT() {
        if (this.lB) {
            throw new AndroidRuntimeException("Window feature must be requested before adding content");
        }
    }

    private int U(int i) {
        if (i == 8) {
            Log.i("AppCompatDelegate", "You should now use the AppCompatDelegate.FEATURE_SUPPORT_ACTION_BAR id when requesting this feature.");
            return 108;
        }
        if (i == 9) {
            Log.i("AppCompatDelegate", "You should now use the AppCompatDelegate.FEATURE_SUPPORT_ACTION_BAR_OVERLAY id when requesting this feature.");
            return 109;
        }
        return i;
    }

    void bU() {
        if (this.lt != null) {
            this.lt.bU();
        }
        if (this.ly != null) {
            this.jo.getDecorView().removeCallbacks(this.lz);
            if (this.ly.isShowing()) {
                try {
                    this.ly.dismiss();
                } catch (IllegalArgumentException e) {
                }
            }
            this.ly = null;
        }
        bR();
        av avVarD = d(0, false);
        if (avVarD != null && avVarD.lZ != null) {
            avVarD.lZ.close();
        }
    }
}
