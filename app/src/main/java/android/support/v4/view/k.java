package android.support.v4.view;

import android.os.Build;
import android.util.Log;
import android.view.LayoutInflater;
import java.lang.reflect.Field;

/* JADX INFO: compiled from: LayoutInflaterCompat.java */
/* JADX INFO: loaded from: classes.dex */
public final class k {
    private static Field hh;
    private static boolean hi;
    static final m hj;

    static void a(LayoutInflater layoutInflater, LayoutInflater.Factory2 factory2) {
        if (!hi) {
            try {
                hh = LayoutInflater.class.getDeclaredField("mFactory2");
                hh.setAccessible(true);
            } catch (NoSuchFieldException e) {
                Log.e("LayoutInflaterCompatHC", "forceSetFactory2 Could not find field 'mFactory2' on class " + LayoutInflater.class.getName() + "; inflation may have unexpected results.", e);
            }
            hi = true;
        }
        if (hh != null) {
            try {
                hh.set(layoutInflater, factory2);
            } catch (IllegalAccessException e2) {
                Log.e("LayoutInflaterCompatHC", "forceSetFactory2 could not set the Factory2 on LayoutInflater " + layoutInflater + "; inflation may have unexpected results.", e2);
            }
        }
    }

    static {
        if (Build.VERSION.SDK_INT >= 21) {
            hj = new l();
        } else {
            hj = new m();
        }
    }

    public static void b(LayoutInflater layoutInflater, LayoutInflater.Factory2 factory2) {
        hj.b(layoutInflater, factory2);
    }
}
