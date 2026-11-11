package android.support.v4.a;

import android.content.Context;
import android.content.res.Resources;
import android.graphics.Typeface;
import android.net.Uri;
import android.os.Build;
import android.support.v4.d.h;
import android.support.v4.e.i;
import android.util.Log;
import android.widget.TextView;
import java.io.File;
import java.nio.ByteBuffer;
import java.util.Map;

/* JADX INFO: compiled from: TypefaceCompat.java */
/* JADX INFO: loaded from: classes.dex */
public class b {
    private static final c fs;
    private static final i<String, Typeface> ft;

    static {
        if (Build.VERSION.SDK_INT < 24) {
            fs = new e();
        } else if (!d.ar()) {
            Log.w("TypefaceCompat", "Unable to collect necessary private methods.Fallback to legacy implementation.");
            fs = new e();
        } else {
            fs = new d();
        }
        ft = new i<>(16);
    }

    public static Typeface a(Resources resources, int i, int i2) {
        return ft.get(b(resources, i, i2));
    }

    private static String b(Resources resources, int i, int i2) {
        return String.valueOf(resources.getResourcePackageName(i)) + "-" + i + "-" + i2;
    }

    public static Typeface a(Context context, android.support.v4.content.a.b bVar, Resources resources, int i, int i2, TextView textView) {
        Typeface typefaceA;
        if (bVar instanceof android.support.v4.content.a.e) {
            android.support.v4.content.a.e eVar = (android.support.v4.content.a.e) bVar;
            typefaceA = android.support.v4.d.b.a(context, eVar.ap(), textView, eVar.aq(), eVar.getTimeout(), i2);
        } else {
            typefaceA = fs.a(context, (android.support.v4.content.a.c) bVar, resources, i2);
        }
        if (typefaceA != null) {
            ft.put(b(resources, i, i2), typefaceA);
        }
        return typefaceA;
    }

    public static Typeface a(Context context, Resources resources, int i, int i2) {
        File fileI = f.i(context);
        if (fileI == null) {
            return null;
        }
        try {
            if (!f.a(fileI, resources, i)) {
                return null;
            }
            Typeface typefaceCreateFromFile = Typeface.createFromFile(fileI.getPath());
            if (typefaceCreateFromFile != null) {
                ft.put(b(resources, i, i2), typefaceCreateFromFile);
            }
            return typefaceCreateFromFile;
        } catch (RuntimeException e) {
            return null;
        } finally {
            fileI.delete();
        }
    }

    public static Typeface a(Context context, h[] hVarArr, Map<Uri, ByteBuffer> map) {
        return fs.a(context, hVarArr, map);
    }
}
