package android.support.v7.app;

import android.content.res.Resources;
import android.os.Build;
import android.util.Log;
import android.util.LongSparseArray;
import java.lang.reflect.Field;
import java.util.Map;

/* JADX INFO: compiled from: ResourcesFlusher.java */
/* JADX INFO: loaded from: classes.dex */
class ba {
    private static Field ms;
    private static boolean mt;
    private static Class mu;
    private static boolean mv;
    private static Field mw;
    private static boolean mx;
    private static Field my;
    private static boolean mz;

    static boolean b(Resources resources) {
        if (Build.VERSION.SDK_INT >= 24) {
            return e(resources);
        }
        if (Build.VERSION.SDK_INT >= 23) {
            return d(resources);
        }
        if (Build.VERSION.SDK_INT >= 21) {
            return c(resources);
        }
        return false;
    }

    private static boolean c(Resources resources) {
        Map map;
        if (!mt) {
            try {
                ms = Resources.class.getDeclaredField("mDrawableCache");
                ms.setAccessible(true);
            } catch (NoSuchFieldException e) {
                Log.e("ResourcesFlusher", "Could not retrieve Resources#mDrawableCache field", e);
            }
            mt = true;
        }
        if (ms != null) {
            try {
                map = (Map) ms.get(resources);
            } catch (IllegalAccessException e2) {
                Log.e("ResourcesFlusher", "Could not retrieve value from Resources#mDrawableCache", e2);
                map = null;
            }
            if (map != null) {
                map.clear();
                return true;
            }
        }
        return false;
    }

    private static boolean d(Resources resources) {
        Object obj;
        if (!mt) {
            try {
                ms = Resources.class.getDeclaredField("mDrawableCache");
                ms.setAccessible(true);
            } catch (NoSuchFieldException e) {
                Log.e("ResourcesFlusher", "Could not retrieve Resources#mDrawableCache field", e);
            }
            mt = true;
        }
        if (ms == null) {
            obj = null;
        } else {
            try {
                obj = ms.get(resources);
            } catch (IllegalAccessException e2) {
                Log.e("ResourcesFlusher", "Could not retrieve value from Resources#mDrawableCache", e2);
                obj = null;
            }
        }
        if (obj == null) {
            return false;
        }
        return obj != null && m(obj);
    }

    private static boolean e(Resources resources) throws IllegalAccessException {
        Object obj;
        Object obj2;
        if (!mz) {
            try {
                my = Resources.class.getDeclaredField("mResourcesImpl");
                my.setAccessible(true);
            } catch (NoSuchFieldException e) {
                Log.e("ResourcesFlusher", "Could not retrieve Resources#mResourcesImpl field", e);
            }
            mz = true;
        }
        if (my == null) {
            return false;
        }
        try {
            obj = my.get(resources);
        } catch (IllegalAccessException e2) {
            Log.e("ResourcesFlusher", "Could not retrieve value from Resources#mResourcesImpl", e2);
            obj = null;
        }
        if (obj == null) {
            return false;
        }
        if (!mt) {
            try {
                ms = obj.getClass().getDeclaredField("mDrawableCache");
                ms.setAccessible(true);
            } catch (NoSuchFieldException e3) {
                Log.e("ResourcesFlusher", "Could not retrieve ResourcesImpl#mDrawableCache field", e3);
            }
            mt = true;
        }
        if (ms != null) {
            try {
                obj2 = ms.get(obj);
            } catch (IllegalAccessException e4) {
                Log.e("ResourcesFlusher", "Could not retrieve value from ResourcesImpl#mDrawableCache", e4);
                obj2 = null;
            }
        } else {
            obj2 = null;
        }
        return obj2 != null && m(obj2);
    }

    private static boolean m(Object obj) {
        LongSparseArray longSparseArray;
        if (!mv) {
            try {
                mu = Class.forName("android.content.res.ThemedResourceCache");
            } catch (ClassNotFoundException e) {
                Log.e("ResourcesFlusher", "Could not find ThemedResourceCache class", e);
            }
            mv = true;
        }
        if (mu == null) {
            return false;
        }
        if (!mx) {
            try {
                mw = mu.getDeclaredField("mUnthemedEntries");
                mw.setAccessible(true);
            } catch (NoSuchFieldException e2) {
                Log.e("ResourcesFlusher", "Could not retrieve ThemedResourceCache#mUnthemedEntries field", e2);
            }
            mx = true;
        }
        if (mw == null) {
            return false;
        }
        try {
            longSparseArray = (LongSparseArray) mw.get(obj);
        } catch (IllegalAccessException e3) {
            Log.e("ResourcesFlusher", "Could not retrieve value from ThemedResourceCache#mUnthemedEntries", e3);
            longSparseArray = null;
        }
        if (longSparseArray == null) {
            return false;
        }
        longSparseArray.clear();
        return true;
    }
}
