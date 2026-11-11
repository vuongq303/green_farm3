package android.support.v7.widget;

import android.content.Context;
import android.content.res.ColorStateList;
import android.content.res.Resources;
import android.content.res.XmlResourceParser;
import android.graphics.PorterDuff;
import android.graphics.PorterDuffColorFilter;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.LayerDrawable;
import android.os.Build;
import android.support.graphics.drawable.VectorDrawableCompat;
import android.support.v7.appcompat.R;
import android.util.AttributeSet;
import android.util.Log;
import android.util.TypedValue;
import android.util.Xml;
import java.lang.ref.WeakReference;
import java.util.WeakHashMap;
import org.xmlpull.v1.XmlPullParserException;

/* JADX INFO: compiled from: AppCompatDrawableManager.java */
/* JADX INFO: loaded from: classes.dex */
public final class ac {
    private static ac tF;
    private WeakHashMap<Context, android.support.v4.e.s<ColorStateList>> tN;
    private android.support.v4.e.a<String, af> tO;
    private android.support.v4.e.s<String> tP;
    private final Object tQ = new Object();
    private final WeakHashMap<Context, android.support.v4.e.h<WeakReference<Drawable.ConstantState>>> tR = new WeakHashMap<>(0);
    private TypedValue tS;
    private boolean tT;
    private static final PorterDuff.Mode tE = PorterDuff.Mode.SRC_IN;
    private static final ae tG = new ae(6);
    private static final int[] tH = {R.drawable.abc_textfield_search_default_mtrl_alpha, R.drawable.abc_textfield_default_mtrl_alpha, R.drawable.abc_ab_share_pack_mtrl_alpha};
    private static final int[] tI = {R.drawable.abc_ic_commit_search_api_mtrl_alpha, R.drawable.abc_seekbar_tick_mark_material, R.drawable.abc_ic_menu_share_mtrl_alpha, R.drawable.abc_ic_menu_copy_mtrl_am_alpha, R.drawable.abc_ic_menu_cut_mtrl_alpha, R.drawable.abc_ic_menu_selectall_mtrl_alpha, R.drawable.abc_ic_menu_paste_mtrl_am_alpha};
    private static final int[] tJ = {R.drawable.abc_textfield_activated_mtrl_alpha, R.drawable.abc_textfield_search_activated_mtrl_alpha, R.drawable.abc_cab_background_top_mtrl_alpha, R.drawable.abc_text_cursor_material, R.drawable.abc_text_select_handle_left_mtrl_dark, R.drawable.abc_text_select_handle_middle_mtrl_dark, R.drawable.abc_text_select_handle_right_mtrl_dark, R.drawable.abc_text_select_handle_left_mtrl_light, R.drawable.abc_text_select_handle_middle_mtrl_light, R.drawable.abc_text_select_handle_right_mtrl_light};
    private static final int[] tK = {R.drawable.abc_popup_background_mtrl_mult, R.drawable.abc_cab_background_internal_bg, R.drawable.abc_menu_hardkey_panel_mtrl_mult};
    private static final int[] tL = {R.drawable.abc_tab_indicator_material, R.drawable.abc_textfield_search_material};
    private static final int[] tM = {R.drawable.abc_btn_check_material, R.drawable.abc_btn_radio_material};

    public static ac eg() {
        if (tF == null) {
            tF = new ac();
            a(tF);
        }
        return tF;
    }

    private static void a(ac acVar) {
        if (Build.VERSION.SDK_INT < 24) {
            acVar.a("vector", new ag());
            if (Build.VERSION.SDK_INT >= 11) {
                acVar.a("animated-vector", new ad());
            }
        }
    }

    public Drawable a(Context context, int i) {
        return a(context, i, false);
    }

    Drawable a(Context context, int i, boolean z) {
        s(context);
        Drawable drawableJ = j(context, i);
        if (drawableJ == null) {
            drawableJ = i(context, i);
        }
        if (drawableJ == null) {
            drawableJ = android.support.v4.content.c.a(context, i);
        }
        if (drawableJ != null) {
            drawableJ = a(context, i, z, drawableJ);
        }
        if (drawableJ != null) {
            bp.n(drawableJ);
        }
        return drawableJ;
    }

    public void n(Context context) {
        synchronized (this.tQ) {
            android.support.v4.e.h<WeakReference<Drawable.ConstantState>> hVar = this.tR.get(context);
            if (hVar != null) {
                hVar.clear();
            }
        }
    }

    private static long a(TypedValue typedValue) {
        return (((long) typedValue.assetCookie) << 32) | ((long) typedValue.data);
    }

    private Drawable i(Context context, int i) {
        if (this.tS == null) {
            this.tS = new TypedValue();
        }
        TypedValue typedValue = this.tS;
        context.getResources().getValue(i, typedValue, true);
        long jA = a(typedValue);
        Drawable drawableA = a(context, jA);
        if (drawableA == null) {
            if (i == R.drawable.abc_cab_background_top_material) {
                drawableA = new LayerDrawable(new Drawable[]{a(context, R.drawable.abc_cab_background_internal_bg), a(context, R.drawable.abc_cab_background_top_mtrl_alpha)});
            }
            if (drawableA != null) {
                drawableA.setChangingConfigurations(typedValue.changingConfigurations);
                a(context, jA, drawableA);
            }
        }
        return drawableA;
    }

    private Drawable a(Context context, int i, boolean z, Drawable drawable) {
        ColorStateList colorStateListK = k(context, i);
        if (colorStateListK != null) {
            if (bp.o(drawable)) {
                drawable = drawable.mutate();
            }
            Drawable drawableD = android.support.v4.a.a.a.d(drawable);
            android.support.v4.a.a.a.a(drawableD, colorStateListK);
            PorterDuff.Mode modeAp = ap(i);
            if (modeAp != null) {
                android.support.v4.a.a.a.a(drawableD, modeAp);
                return drawableD;
            }
            return drawableD;
        }
        if (i == R.drawable.abc_seekbar_track_material) {
            LayerDrawable layerDrawable = (LayerDrawable) drawable;
            a(layerDrawable.findDrawableByLayerId(android.R.id.background), cv.o(context, R.attr.colorControlNormal), tE);
            a(layerDrawable.findDrawableByLayerId(android.R.id.secondaryProgress), cv.o(context, R.attr.colorControlNormal), tE);
            a(layerDrawable.findDrawableByLayerId(android.R.id.progress), cv.o(context, R.attr.colorControlActivated), tE);
            return drawable;
        }
        if (i == R.drawable.abc_ratingbar_material || i == R.drawable.abc_ratingbar_indicator_material || i == R.drawable.abc_ratingbar_small_material) {
            LayerDrawable layerDrawable2 = (LayerDrawable) drawable;
            a(layerDrawable2.findDrawableByLayerId(android.R.id.background), cv.q(context, R.attr.colorControlNormal), tE);
            a(layerDrawable2.findDrawableByLayerId(android.R.id.secondaryProgress), cv.o(context, R.attr.colorControlActivated), tE);
            a(layerDrawable2.findDrawableByLayerId(android.R.id.progress), cv.o(context, R.attr.colorControlActivated), tE);
            return drawable;
        }
        if (!a(context, i, drawable) && z) {
            return null;
        }
        return drawable;
    }

    private Drawable j(Context context, int i) {
        Drawable drawable;
        int next;
        if (this.tO == null || this.tO.isEmpty()) {
            return null;
        }
        if (this.tP != null) {
            String str = this.tP.get(i);
            if ("appcompat_skip_skip".equals(str) || (str != null && this.tO.get(str) == null)) {
                return null;
            }
        } else {
            this.tP = new android.support.v4.e.s<>();
        }
        if (this.tS == null) {
            this.tS = new TypedValue();
        }
        TypedValue typedValue = this.tS;
        Resources resources = context.getResources();
        resources.getValue(i, typedValue, true);
        long jA = a(typedValue);
        Drawable drawableA = a(context, jA);
        if (drawableA != null) {
            return drawableA;
        }
        if (typedValue.string == null || !typedValue.string.toString().endsWith(".xml")) {
            drawable = drawableA;
        } else {
            try {
                XmlResourceParser xml = resources.getXml(i);
                AttributeSet attributeSetAsAttributeSet = Xml.asAttributeSet(xml);
                do {
                    next = xml.next();
                    if (next == 2) {
                        break;
                    }
                } while (next != 1);
                if (next != 2) {
                    throw new XmlPullParserException("No start tag found");
                }
                String name = xml.getName();
                this.tP.append(i, name);
                af afVar = this.tO.get(name);
                if (afVar != null) {
                    drawableA = afVar.a(context, xml, attributeSetAsAttributeSet, context.getTheme());
                }
                if (drawableA != null) {
                    drawableA.setChangingConfigurations(typedValue.changingConfigurations);
                    if (a(context, jA, drawableA)) {
                    }
                }
                drawable = drawableA;
            } catch (Exception e) {
                Log.e("AppCompatDrawableManager", "Exception while inflating drawable", e);
                drawable = drawableA;
            }
        }
        if (drawable == null) {
            this.tP.append(i, "appcompat_skip_skip");
            return drawable;
        }
        return drawable;
    }

    private Drawable a(Context context, long j) {
        synchronized (this.tQ) {
            android.support.v4.e.h<WeakReference<Drawable.ConstantState>> hVar = this.tR.get(context);
            if (hVar == null) {
                return null;
            }
            WeakReference<Drawable.ConstantState> weakReference = hVar.get(j);
            if (weakReference != null) {
                Drawable.ConstantState constantState = weakReference.get();
                if (constantState != null) {
                    return constantState.newDrawable(context.getResources());
                }
                hVar.delete(j);
            }
            return null;
        }
    }

    private boolean a(Context context, long j, Drawable drawable) {
        Drawable.ConstantState constantState = drawable.getConstantState();
        if (constantState != null) {
            synchronized (this.tQ) {
                android.support.v4.e.h<WeakReference<Drawable.ConstantState>> hVar = this.tR.get(context);
                if (hVar == null) {
                    hVar = new android.support.v4.e.h<>();
                    this.tR.put(context, hVar);
                }
                hVar.put(j, new WeakReference<>(constantState));
            }
            return true;
        }
        return false;
    }

    Drawable a(Context context, dr drVar, int i) {
        Drawable drawableJ = j(context, i);
        if (drawableJ == null) {
            drawableJ = drVar.az(i);
        }
        if (drawableJ != null) {
            return a(context, i, false, drawableJ);
        }
        return null;
    }

    static boolean a(Context context, int i, Drawable drawable) {
        int iRound;
        boolean z;
        int i2 = android.R.attr.colorBackground;
        PorterDuff.Mode mode = tE;
        if (a(tH, i)) {
            i2 = R.attr.colorControlNormal;
            iRound = -1;
            z = true;
        } else if (a(tJ, i)) {
            i2 = R.attr.colorControlActivated;
            iRound = -1;
            z = true;
        } else if (a(tK, i)) {
            mode = PorterDuff.Mode.MULTIPLY;
            iRound = -1;
            z = true;
        } else if (i == R.drawable.abc_list_divider_mtrl_alpha) {
            i2 = android.R.attr.colorForeground;
            iRound = Math.round(40.8f);
            z = true;
        } else if (i == R.drawable.abc_dialog_material_background) {
            iRound = -1;
            z = true;
        } else {
            iRound = -1;
            i2 = 0;
            z = false;
        }
        if (!z) {
            return false;
        }
        if (bp.o(drawable)) {
            drawable = drawable.mutate();
        }
        drawable.setColorFilter(a(cv.o(context, i2), mode));
        if (iRound == -1) {
            return true;
        }
        drawable.setAlpha(iRound);
        return true;
    }

    private void a(String str, af afVar) {
        if (this.tO == null) {
            this.tO = new android.support.v4.e.a<>();
        }
        this.tO.put(str, afVar);
    }

    private static boolean a(int[] iArr, int i) {
        for (int i2 : iArr) {
            if (i2 == i) {
                return true;
            }
        }
        return false;
    }

    static PorterDuff.Mode ap(int i) {
        if (i != R.drawable.abc_switch_thumb_material) {
            return null;
        }
        return PorterDuff.Mode.MULTIPLY;
    }

    ColorStateList k(Context context, int i) {
        ColorStateList colorStateListL = l(context, i);
        if (colorStateListL == null) {
            if (i == R.drawable.abc_edit_text_material) {
                colorStateListL = android.support.v7.a.a.b.b(context, R.color.abc_tint_edittext);
            } else if (i == R.drawable.abc_switch_track_mtrl_alpha) {
                colorStateListL = android.support.v7.a.a.b.b(context, R.color.abc_tint_switch_track);
            } else if (i == R.drawable.abc_switch_thumb_material) {
                colorStateListL = r(context);
            } else if (i == R.drawable.abc_btn_default_mtrl_shape) {
                colorStateListL = o(context);
            } else if (i == R.drawable.abc_btn_borderless_material) {
                colorStateListL = p(context);
            } else if (i == R.drawable.abc_btn_colored_material) {
                colorStateListL = q(context);
            } else if (i == R.drawable.abc_spinner_mtrl_am_alpha || i == R.drawable.abc_spinner_textfield_background_material) {
                colorStateListL = android.support.v7.a.a.b.b(context, R.color.abc_tint_spinner);
            } else if (a(tI, i)) {
                colorStateListL = cv.p(context, R.attr.colorControlNormal);
            } else if (a(tL, i)) {
                colorStateListL = android.support.v7.a.a.b.b(context, R.color.abc_tint_default);
            } else if (a(tM, i)) {
                colorStateListL = android.support.v7.a.a.b.b(context, R.color.abc_tint_btn_checkable);
            } else if (i == R.drawable.abc_seekbar_thumb_material) {
                colorStateListL = android.support.v7.a.a.b.b(context, R.color.abc_tint_seek_thumb);
            }
            if (colorStateListL != null) {
                b(context, i, colorStateListL);
            }
        }
        return colorStateListL;
    }

    private ColorStateList l(Context context, int i) {
        android.support.v4.e.s<ColorStateList> sVar;
        if (this.tN != null && (sVar = this.tN.get(context)) != null) {
            return sVar.get(i);
        }
        return null;
    }

    private void b(Context context, int i, ColorStateList colorStateList) {
        if (this.tN == null) {
            this.tN = new WeakHashMap<>();
        }
        android.support.v4.e.s<ColorStateList> sVar = this.tN.get(context);
        if (sVar == null) {
            sVar = new android.support.v4.e.s<>();
            this.tN.put(context, sVar);
        }
        sVar.append(i, colorStateList);
    }

    private ColorStateList o(Context context) {
        return m(context, cv.o(context, R.attr.colorButtonNormal));
    }

    private ColorStateList p(Context context) {
        return m(context, 0);
    }

    private ColorStateList q(Context context) {
        return m(context, cv.o(context, R.attr.colorAccent));
    }

    private ColorStateList m(Context context, int i) {
        int iO = cv.o(context, R.attr.colorControlHighlight);
        return new ColorStateList(new int[][]{cv.xG, cv.PRESSED_STATE_SET, cv.FOCUSED_STATE_SET, cv.EMPTY_STATE_SET}, new int[]{cv.q(context, R.attr.colorButtonNormal), android.support.v4.a.a.a(iO, i), android.support.v4.a.a.a(iO, i), i});
    }

    private ColorStateList r(Context context) {
        int[][] iArr = new int[3][];
        int[] iArr2 = new int[3];
        ColorStateList colorStateListP = cv.p(context, R.attr.colorSwitchThumbNormal);
        if (colorStateListP != null && colorStateListP.isStateful()) {
            iArr[0] = cv.xG;
            iArr2[0] = colorStateListP.getColorForState(iArr[0], 0);
            iArr[1] = cv.xI;
            iArr2[1] = cv.o(context, R.attr.colorControlActivated);
            iArr[2] = cv.EMPTY_STATE_SET;
            iArr2[2] = colorStateListP.getDefaultColor();
        } else {
            iArr[0] = cv.xG;
            iArr2[0] = cv.q(context, R.attr.colorSwitchThumbNormal);
            iArr[1] = cv.xI;
            iArr2[1] = cv.o(context, R.attr.colorControlActivated);
            iArr[2] = cv.EMPTY_STATE_SET;
            iArr2[2] = cv.o(context, R.attr.colorSwitchThumbNormal);
        }
        return new ColorStateList(iArr, iArr2);
    }

    static void a(Drawable drawable, cy cyVar, int[] iArr) {
        if (bp.o(drawable) && drawable.mutate() != drawable) {
            Log.d("AppCompatDrawableManager", "Mutated drawable is not the same instance as the input.");
            return;
        }
        if (cyVar.xP || cyVar.xO) {
            drawable.setColorFilter(a(cyVar.xP ? cyVar.xN : null, cyVar.xO ? cyVar.fK : tE, iArr));
        } else {
            drawable.clearColorFilter();
        }
        if (Build.VERSION.SDK_INT <= 23) {
            drawable.invalidateSelf();
        }
    }

    private static PorterDuffColorFilter a(ColorStateList colorStateList, PorterDuff.Mode mode, int[] iArr) {
        if (colorStateList == null || mode == null) {
            return null;
        }
        return a(colorStateList.getColorForState(iArr, 0), mode);
    }

    public static PorterDuffColorFilter a(int i, PorterDuff.Mode mode) {
        PorterDuffColorFilter porterDuffColorFilterB = tG.b(i, mode);
        if (porterDuffColorFilterB == null) {
            PorterDuffColorFilter porterDuffColorFilter = new PorterDuffColorFilter(i, mode);
            tG.a(i, mode, porterDuffColorFilter);
            return porterDuffColorFilter;
        }
        return porterDuffColorFilterB;
    }

    private static void a(Drawable drawable, int i, PorterDuff.Mode mode) {
        if (bp.o(drawable)) {
            drawable = drawable.mutate();
        }
        if (mode == null) {
            mode = tE;
        }
        drawable.setColorFilter(a(i, mode));
    }

    private void s(Context context) {
        if (!this.tT) {
            this.tT = true;
            Drawable drawableA = a(context, R.drawable.abc_vector_test);
            if (drawableA == null || !k(drawableA)) {
                this.tT = false;
                throw new IllegalStateException("This app has been built with an incorrect configuration. Please configure your build for VectorDrawableCompat.");
            }
        }
    }

    private static boolean k(Drawable drawable) {
        return (drawable instanceof VectorDrawableCompat) || "android.graphics.drawable.VectorDrawable".equals(drawable.getClass().getName());
    }
}
