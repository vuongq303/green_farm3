package android.support.v4.a;

import android.content.Context;
import android.content.res.Resources;
import android.graphics.Typeface;
import android.net.Uri;
import android.support.v4.d.h;
import android.util.Log;
import java.lang.reflect.Array;
import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.nio.ByteBuffer;
import java.util.List;
import java.util.Map;

/* JADX INFO: compiled from: TypefaceCompatApi24Impl.java */
/* JADX INFO: loaded from: classes.dex */
class d implements c {
    private static final Class fu;
    private static final Constructor fv;
    private static final Method fw;
    private static final Method fx;

    d() {
    }

    static {
        Method method;
        Method method2;
        Constructor<?> constructor;
        Class<?> cls;
        try {
            cls = Class.forName("android.graphics.FontFamily");
            constructor = cls.getConstructor(new Class[0]);
            method2 = cls.getMethod("addFontWeightStyle", ByteBuffer.class, Integer.TYPE, List.class, Integer.TYPE, Boolean.TYPE);
            method = Typeface.class.getMethod("createFromFamiliesWithDefault", Array.newInstance(cls, 1).getClass());
        } catch (ClassNotFoundException | NoSuchMethodException e) {
            Log.e("TypefaceCompatApi24Impl", e.getClass().getName(), e);
            method = null;
            method2 = null;
            constructor = null;
            cls = null;
        }
        fv = constructor;
        fu = cls;
        fw = method2;
        fx = method;
    }

    public static boolean ar() {
        return fw != null;
    }

    private static Object as() {
        try {
            return fv.newInstance(new Object[0]);
        } catch (IllegalAccessException | InstantiationException | InvocationTargetException e) {
            throw new RuntimeException(e);
        }
    }

    private static boolean a(Object obj, ByteBuffer byteBuffer, int i, int i2, boolean z) {
        try {
            return ((Boolean) fw.invoke(obj, byteBuffer, Integer.valueOf(i), null, Integer.valueOf(i2), Boolean.valueOf(z))).booleanValue();
        } catch (IllegalAccessException | InvocationTargetException e) {
            throw new RuntimeException(e);
        }
    }

    private static Typeface h(Object obj) {
        try {
            Object objNewInstance = Array.newInstance((Class<?>) fu, 1);
            Array.set(objNewInstance, 0, obj);
            return (Typeface) fx.invoke(null, objNewInstance);
        } catch (IllegalAccessException | InvocationTargetException e) {
            throw new RuntimeException(e);
        }
    }

    @Override // android.support.v4.a.c
    public Typeface a(Context context, h[] hVarArr, Map<Uri, ByteBuffer> map) {
        Object objAs = as();
        for (h hVar : hVarArr) {
            if (!a(objAs, map.get(hVar.getUri()), hVar.getTtcIndex(), hVar.getWeight(), hVar.isItalic())) {
                return null;
            }
        }
        return h(objAs);
    }

    @Override // android.support.v4.a.c
    public Typeface a(Context context, android.support.v4.content.a.c cVar, Resources resources, int i) {
        Object objAs = as();
        for (android.support.v4.content.a.d dVar : cVar.an()) {
            if (!a(objAs, f.a(context, resources, dVar.ao()), 0, dVar.getWeight(), dVar.isItalic())) {
                return null;
            }
        }
        return h(objAs);
    }
}
