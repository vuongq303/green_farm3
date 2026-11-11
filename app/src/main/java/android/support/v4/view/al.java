package android.support.v4.view;

import android.animation.ValueAnimator;
import android.content.res.ColorStateList;
import android.graphics.PorterDuff;
import android.graphics.drawable.Drawable;
import android.view.View;
import java.lang.reflect.Field;
import java.util.WeakHashMap;

/* JADX INFO: compiled from: ViewCompat.java */
/* JADX INFO: loaded from: classes.dex */
class al {
    private static Field ht;
    private static boolean hu;
    private static WeakHashMap<View, String> hv;
    static boolean hx = false;
    WeakHashMap<View, ar> hw = null;

    al() {
    }

    public void e(View view) {
        view.postInvalidate();
    }

    public void b(View view, Runnable runnable) {
        view.postDelayed(runnable, aS());
    }

    public void a(View view, Runnable runnable, long j) {
        view.postDelayed(runnable, aS() + j);
    }

    long aS() {
        return ValueAnimator.getFrameDelay();
    }

    public int f(View view) {
        return 0;
    }

    public boolean l(View view) {
        return true;
    }

    public int g(View view) {
        if (!hu) {
            try {
                ht = View.class.getDeclaredField("mMinHeight");
                ht.setAccessible(true);
            } catch (NoSuchFieldException e) {
            }
            hu = true;
        }
        if (ht != null) {
            try {
                return ((Integer) ht.get(view)).intValue();
            } catch (Exception e2) {
            }
        }
        return 0;
    }

    public ar h(View view) {
        if (this.hw == null) {
            this.hw = new WeakHashMap<>();
        }
        ar arVar = this.hw.get(view);
        if (arVar == null) {
            ar arVar2 = new ar(view);
            this.hw.put(view, arVar2);
            return arVar2;
        }
        return arVar;
    }

    public String i(View view) {
        if (hv == null) {
            return null;
        }
        return hv.get(view);
    }

    public int j(View view) {
        return 0;
    }

    public void k(View view) {
    }

    public void a(View view, float f) {
    }

    public void a(View view, y yVar) {
    }

    public ay a(View view, ay ayVar) {
        return ayVar;
    }

    public void setBackground(View view, Drawable drawable) {
        view.setBackgroundDrawable(drawable);
    }

    /* JADX WARN: Multi-variable type inference failed */
    public ColorStateList m(View view) {
        if (view instanceof z) {
            return ((z) view).aQ();
        }
        return null;
    }

    /* JADX WARN: Multi-variable type inference failed */
    public void a(View view, ColorStateList colorStateList) {
        if (view instanceof z) {
            ((z) view).a(colorStateList);
        }
    }

    /* JADX WARN: Multi-variable type inference failed */
    public void a(View view, PorterDuff.Mode mode) {
        if (view instanceof z) {
            ((z) view).a(mode);
        }
    }

    /* JADX WARN: Multi-variable type inference failed */
    public PorterDuff.Mode n(View view) {
        if (view instanceof z) {
            return ((z) view).aR();
        }
        return null;
    }

    /* JADX WARN: Multi-variable type inference failed */
    public void o(View view) {
        if (view instanceof s) {
            ((s) view).stopNestedScroll();
        }
    }

    public boolean p(View view) {
        return view.getWidth() > 0 && view.getHeight() > 0;
    }

    public boolean isAttachedToWindow(View view) {
        return view.getWindowToken() != null;
    }

    public boolean q(View view) {
        return false;
    }

    public void a(View view, int i, int i2) {
    }
}
