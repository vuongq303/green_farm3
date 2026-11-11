package com.moat.analytics.mobile.glft;

import android.app.Activity;
import android.graphics.Rect;
import android.os.Build;
import android.util.DisplayMetrics;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebView;
import android.widget.ListView;
import java.lang.ref.WeakReference;
import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;
import org.json.JSONObject;

/* JADX INFO: loaded from: classes.dex */
class b {
    final boolean c;
    final String ceu;
    i ckO;
    boolean d;
    private WeakReference<View> e;
    private WeakReference<WebView> f;
    private final boolean g;
    private boolean h;
    private a i;
    private JSONObject j;
    private Rect k;
    private Rect l;
    private JSONObject m;
    private JSONObject n;
    private Map<String, Object> o = new HashMap();
    private String p = "{}";

    class a {
        double b;
        double c = 0.0d;
        Rect ckP;

        a(Rect rect, double d) {
            this.ckP = rect;
            this.b = d;
        }
    }

    b(View view, boolean z, boolean z2) {
        n.a(3, "MoatBaseTracker", this, "Initializing.");
        if (z) {
            this.ceu = "m" + hashCode();
        } else {
            this.ceu = "";
        }
        this.e = new WeakReference<>(view);
        this.g = z;
        this.c = z2;
        this.d = false;
        this.h = false;
        this.i = new a(new Rect(0, 0, 0, 0), 0.0d);
    }

    private float a(View view) {
        float alpha = view.getAlpha();
        while (view != null && view.getParent() != null && alpha != 0.0d && (view.getParent() instanceof View)) {
            alpha *= ((View) view.getParent()).getAlpha();
            view = (View) view.getParent();
        }
        return alpha;
    }

    private static int a(Rect rect) {
        return rect.width() * rect.height();
    }

    static int a(Rect rect, Set<Rect> set) {
        int i = 0;
        if (set.isEmpty()) {
            return 0;
        }
        ArrayList<Rect> arrayList = new ArrayList();
        arrayList.addAll(set);
        Collections.sort(arrayList, new Comparator<Rect>() { // from class: com.moat.analytics.mobile.glft.b.1
            @Override // java.util.Comparator
            /* JADX INFO: renamed from: a, reason: merged with bridge method [inline-methods] */
            public int compare(Rect rect2, Rect rect3) {
                return Integer.valueOf(rect2.top).compareTo(Integer.valueOf(rect3.top));
            }
        });
        ArrayList arrayList2 = new ArrayList();
        for (Rect rect2 : arrayList) {
            arrayList2.add(Integer.valueOf(rect2.left));
            arrayList2.add(Integer.valueOf(rect2.right));
        }
        Collections.sort(arrayList2);
        int i2 = 0;
        while (true) {
            int iMax = i;
            if (i2 >= arrayList2.size() - 1) {
                return iMax;
            }
            if (!((Integer) arrayList2.get(i2)).equals(arrayList2.get(i2 + 1))) {
                Rect rect3 = new Rect(((Integer) arrayList2.get(i2)).intValue(), rect.top, ((Integer) arrayList2.get(i2 + 1)).intValue(), rect.bottom);
                int i3 = rect.top;
                Iterator it = arrayList.iterator();
                while (true) {
                    int i4 = i3;
                    if (!it.hasNext()) {
                        break;
                    }
                    Rect rect4 = (Rect) it.next();
                    if (Rect.intersects(rect4, rect3)) {
                        if (rect4.bottom > i4) {
                            iMax += (rect4.bottom - Math.max(i4, rect4.top)) * rect3.width();
                            i4 = rect4.bottom;
                        }
                        if (rect4.bottom == rect3.bottom) {
                            break;
                        }
                    }
                    i3 = i4;
                }
            }
            i = iMax;
            i2++;
        }
    }

    private Rect a(DisplayMetrics displayMetrics) {
        return new Rect(0, 0, displayMetrics.widthPixels, displayMetrics.heightPixels);
    }

    private a a(Rect rect, boolean z, boolean z2, boolean z3) {
        a aVar = new a(new Rect(0, 0, 0, 0), 0.0d);
        int iA = a(rect);
        if (k() && z && z2 && !z3 && iA > 0) {
            Rect rect2 = new Rect(0, 0, 0, 0);
            if (ahS().getGlobalVisibleRect(rect2)) {
                int iA2 = a(rect2);
                if (iA2 < iA) {
                }
                HashSet hashSet = new HashSet();
                if (ahS().getRootView() instanceof ViewGroup) {
                    aVar.ckP = rect2;
                    boolean zA = a(rect2, ahS(), hashSet);
                    if (zA) {
                        aVar.c = 1.0d;
                    }
                    if (!zA) {
                        int iA3 = a(rect2, hashSet);
                        if (iA3 > 0) {
                            aVar.c = ((double) iA3) / (((double) iA2) * 1.0d);
                        }
                        aVar.b = ((double) (iA2 - iA3)) / (((double) iA) * 1.0d);
                    }
                }
            }
        }
        return aVar;
    }

    private Map<String, String> a(Rect rect, DisplayMetrics displayMetrics) {
        return b(b(rect, displayMetrics));
    }

    /* JADX WARN: Code duplicated, block: B:39:0x00d5  */
    private static boolean a(Rect rect, View view, Set<Rect> set) {
        boolean z;
        View rootView = view.getRootView();
        ArrayDeque arrayDeque = new ArrayDeque();
        arrayDeque.add(rootView);
        n.b(2, "MoatBaseTracker", view, "starting covering rect search");
        boolean z2 = false;
        int i = 0;
        while (!arrayDeque.isEmpty() && i < 250) {
            i++;
            View view2 = (View) arrayDeque.pollLast();
            if (view2.equals(view)) {
                n.b(2, "MoatBaseTracker", rect, "found target");
                z2 = true;
            } else if (b(view2)) {
                if ((view2 instanceof ViewGroup) && !(view2 instanceof ListView)) {
                    ViewGroup viewGroup = (ViewGroup) view2;
                    for (int childCount = viewGroup.getChildCount() - 1; childCount >= 0; childCount--) {
                        arrayDeque.add(viewGroup.getChildAt(childCount));
                    }
                }
                if (Build.VERSION.SDK_INT >= 21) {
                    if (view2.getElevation() > view.getElevation()) {
                        z = true;
                    } else if (z2 && view2.getElevation() == view.getElevation()) {
                        z = true;
                    } else {
                        z = false;
                    }
                } else if (z2) {
                    z = true;
                } else {
                    z = false;
                }
                if (z) {
                    Rect rectC = c(view2);
                    if (rectC.setIntersect(rect, rectC)) {
                        n.b(2, "MoatBaseTracker", view2, "Covered by " + view2.getClass().getSimpleName() + "-" + rectC.toString());
                        set.add(rectC);
                        if (rectC.contains(rect)) {
                            return true;
                        }
                    }
                }
            }
        }
        return false;
    }

    private Rect b(Rect rect, DisplayMetrics displayMetrics) {
        float f = displayMetrics.density;
        if (f == 0.0f) {
            return rect;
        }
        return new Rect(Math.round(rect.left / f), Math.round(rect.top / f), Math.round(rect.right / f), Math.round(rect.bottom / f));
    }

    private static Map<String, String> b(Rect rect) {
        HashMap map = new HashMap();
        map.put("x", String.valueOf(rect.left));
        map.put("y", String.valueOf(rect.top));
        map.put("w", String.valueOf(rect.right - rect.left));
        map.put("h", String.valueOf(rect.bottom - rect.top));
        return map;
    }

    private static boolean b(View view) {
        return view.isShown() && ((double) view.getAlpha()) > 0.0d;
    }

    private static Rect c(View view) {
        int[] iArr = {Integer.MIN_VALUE, Integer.MIN_VALUE};
        view.getLocationInWindow(iArr);
        int i = iArr[0];
        int i2 = iArr[1];
        return new Rect(i, i2, view.getWidth() + i, view.getHeight() + i2);
    }

    private void d() {
        com.moat.analytics.mobile.glft.a.a.a.a(this.f);
        n.a(3, "MoatBaseTracker", this, "Attempting bridge installation.");
        if (!i()) {
            this.ckO = null;
            n.a(3, "MoatBaseTracker", this, "Bridge not installed, WebView is null.");
            return;
        }
        if (!this.g && !this.c) {
            this.ckO = new i(j(), i.a.WEBVIEW);
        }
        n.a(3, "MoatBaseTracker", this, "Bridge " + (this.ckO.f10a ? "" : "not ") + "installed.");
    }

    private boolean e() {
        if (Build.VERSION.SDK_INT >= 19) {
            return k() && ahS().isAttachedToWindow();
        }
        return k() && ahS().getWindowToken() != null;
    }

    private boolean f() {
        return k() && ahS().hasWindowFocus();
    }

    private boolean g() {
        return (k() && ahS().isShown()) ? false : true;
    }

    private float h() {
        if (k()) {
            return a(ahS());
        }
        return 0.0f;
    }

    private boolean i() {
        return j() != null;
    }

    private WebView j() {
        return this.f.get();
    }

    private boolean k() {
        return ahS() != null;
    }

    private DisplayMetrics l() {
        Activity activity;
        if (Build.VERSION.SDK_INT < 17 || com.moat.analytics.mobile.glft.a.ckN == null || (activity = com.moat.analytics.mobile.glft.a.ckN.get()) == null) {
            return ahS().getContext().getResources().getDisplayMetrics();
        }
        DisplayMetrics displayMetrics = new DisplayMetrics();
        activity.getWindowManager().getDefaultDisplay().getRealMetrics(displayMetrics);
        return displayMetrics;
    }

    void a(i iVar) {
        this.ckO = iVar;
    }

    void a(String str) {
        this.ckO.a(str);
    }

    String ahA() {
        boolean z = false;
        HashMap map = new HashMap();
        try {
            if (!k()) {
                return "{}";
            }
            DisplayMetrics displayMetricsL = l();
            boolean zE = e();
            boolean zF = f();
            boolean zG = g();
            float fH = h();
            map.put("dr", Float.valueOf(displayMetricsL.density));
            map.put("dv", Double.valueOf(p.aii()));
            map.put("adKey", this.ceu);
            map.put("isAttached", Integer.valueOf(zE ? 1 : 0));
            map.put("inFocus", Integer.valueOf(zF ? 1 : 0));
            map.put("isHidden", Integer.valueOf(zG ? 1 : 0));
            map.put("opacity", Float.valueOf(fH));
            Rect rectA = a(displayMetricsL);
            Rect rectAjH = ajH();
            a aVarA = a(rectAjH, zE, zF, zG);
            if (this.j == null || aVarA.b != this.i.b || !aVarA.ckP.equals(this.i.ckP) || aVarA.c != this.i.c) {
                this.i = aVarA;
                this.j = new JSONObject(a(this.i.ckP, displayMetricsL));
                z = true;
            }
            map.put("coveredPercent", Double.valueOf(aVarA.c));
            if (this.n == null || !rectA.equals(this.l)) {
                this.l = rectA;
                this.n = new JSONObject(a(rectA, displayMetricsL));
                z = true;
            }
            if (this.m == null || !rectAjH.equals(this.k)) {
                this.k = rectAjH;
                this.m = new JSONObject(a(rectAjH, displayMetricsL));
                z = true;
            }
            if (this.o == null || !map.equals(this.o)) {
                this.o = map;
                z = true;
            }
            if (!z) {
                return this.p;
            }
            JSONObject jSONObject = new JSONObject(this.o);
            jSONObject.accumulate("screen", this.n);
            jSONObject.accumulate("view", this.m);
            jSONObject.accumulate("visible", this.j);
            jSONObject.accumulate("maybe", this.j);
            jSONObject.accumulate("visiblePercent", Double.valueOf(this.i.b));
            String string = jSONObject.toString();
            this.p = string;
            return string;
        } catch (Exception e) {
            l.j(e);
            this.p = "{}";
            return "{}";
        }
    }

    View ahS() {
        return this.e.get();
    }

    Rect ajH() {
        int[] iArr = {Integer.MAX_VALUE, Integer.MAX_VALUE};
        if (!k()) {
            return new Rect(0, 0, 0, 0);
        }
        ahS().getLocationInWindow(iArr);
        int i = iArr[0];
        int i2 = iArr[1];
        return new Rect(i, i2, i + ahS().getWidth(), i2 + ahS().getHeight());
    }

    void b(WebView webView) {
        if (webView != null) {
            this.f = new WeakReference<>(webView);
            if (this.ckO == null) {
                d();
            }
            if (this.ckO == null || !this.ckO.f10a) {
                return;
            }
            this.ckO.a(this);
        }
    }

    public void changeTargetView(View view) {
        n.a(3, "MoatBaseTracker", this, "changing view to " + (view != null ? view.getClass().getSimpleName() + "@" + view.hashCode() : "null"));
        this.e = new WeakReference<>(view);
    }

    @Deprecated
    public void setActivity(Activity activity) {
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    public void startTracking() {
        n.a(3, "MoatBaseTracker", this, "Attempting to start impression.");
        if (this.h) {
            n.a(3, "MoatBaseTracker", this, "startTracking failed, tracker already started");
            return;
        }
        boolean zB = this.ckO.b(this);
        n.a(3, "MoatBaseTracker", this, "Impression " + (zB ? "" : "not ") + "started.");
        if (zB) {
            this.d = true;
            this.h = true;
        }
    }

    public void stopTracking() {
        n.a(3, "MoatBaseTracker", this, "Attempting to stop impression.");
        this.d = false;
        n.a(3, "MoatBaseTracker", this, "Impression tracking " + (this.ckO.c(this) ? "" : "not ") + "stopped.");
    }
}
