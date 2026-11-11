package android.support.v7.a.a;

import android.content.Context;
import android.content.res.ColorStateList;
import android.content.res.Resources;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.support.v7.widget.ac;
import android.util.Log;
import android.util.SparseArray;
import android.util.TypedValue;
import java.util.WeakHashMap;

/* JADX INFO: compiled from: AppCompatResources.java */
/* JADX INFO: loaded from: classes.dex */
public final class b {
    private static final ThreadLocal<TypedValue> nz = new ThreadLocal<>();
    private static final WeakHashMap<Context, SparseArray<c>> nA = new WeakHashMap<>(0);
    private static final Object nB = new Object();

    public static ColorStateList b(Context context, int i) {
        if (Build.VERSION.SDK_INT >= 23) {
            return context.getColorStateList(i);
        }
        ColorStateList colorStateListG = g(context, i);
        if (colorStateListG == null) {
            ColorStateList colorStateListF = f(context, i);
            if (colorStateListF != null) {
                a(context, i, colorStateListF);
                return colorStateListF;
            }
            return android.support.v4.content.c.b(context, i);
        }
        return colorStateListG;
    }

    public static Drawable a(Context context, int i) {
        return ac.eg().a(context, i);
    }

    private static ColorStateList f(Context context, int i) {
        if (h(context, i)) {
            return null;
        }
        Resources resources = context.getResources();
        try {
            return a.createFromXml(resources, resources.getXml(i), context.getTheme());
        } catch (Exception e) {
            Log.e("AppCompatResources", "Failed to inflate ColorStateList, leaving it to the framework", e);
            return null;
        }
    }

    private static ColorStateList g(Context context, int i) {
        c cVar;
        synchronized (nB) {
            SparseArray<c> sparseArray = nA.get(context);
            if (sparseArray != null && sparseArray.size() > 0 && (cVar = sparseArray.get(i)) != null) {
                if (cVar.nD.equals(context.getResources().getConfiguration())) {
                    return cVar.nC;
                }
                sparseArray.remove(i);
            }
            return null;
        }
    }

    private static void a(Context context, int i, ColorStateList colorStateList) {
        synchronized (nB) {
            SparseArray<c> sparseArray = nA.get(context);
            if (sparseArray == null) {
                sparseArray = new SparseArray<>();
                nA.put(context, sparseArray);
            }
            sparseArray.append(i, new c(colorStateList, context.getResources().getConfiguration()));
        }
    }

    private static boolean h(Context context, int i) {
        Resources resources = context.getResources();
        TypedValue typedValueCk = ck();
        resources.getValue(i, typedValueCk, true);
        return typedValueCk.type >= 28 && typedValueCk.type <= 31;
    }

    private static TypedValue ck() {
        TypedValue typedValue = nz.get();
        if (typedValue == null) {
            TypedValue typedValue2 = new TypedValue();
            nz.set(typedValue2);
            return typedValue2;
        }
        return typedValue;
    }
}
