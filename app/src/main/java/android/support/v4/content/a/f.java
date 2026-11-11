package android.support.v4.content.a;

import android.content.Context;
import android.content.res.Resources;
import android.graphics.Typeface;
import android.util.Log;
import android.util.TypedValue;
import android.widget.TextView;
import java.io.IOException;
import org.xmlpull.v1.XmlPullParserException;

/* JADX INFO: compiled from: ResourcesCompat.java */
/* JADX INFO: loaded from: classes.dex */
public final class f {
    public static Typeface a(Context context, int i, TypedValue typedValue, int i2, TextView textView) {
        if (context.isRestricted()) {
            return null;
        }
        return b(context, i, typedValue, i2, textView);
    }

    private static Typeface b(Context context, int i, TypedValue typedValue, int i2, TextView textView) {
        Resources resources = context.getResources();
        resources.getValue(i, typedValue, true);
        Typeface typefaceA = a(context, resources, typedValue, i, i2, textView);
        if (typefaceA != null) {
            return typefaceA;
        }
        throw new Resources.NotFoundException("Font resource ID #0x" + Integer.toHexString(i));
    }

    private static Typeface a(Context context, Resources resources, TypedValue typedValue, int i, int i2, TextView textView) {
        Typeface typefaceA;
        if (typedValue.string == null) {
            throw new Resources.NotFoundException("Resource \"" + resources.getResourceName(i) + "\" (" + Integer.toHexString(i) + ") is not a Font: " + typedValue);
        }
        String string = typedValue.string.toString();
        if (!string.startsWith("res/")) {
            return null;
        }
        Typeface typefaceA2 = android.support.v4.a.b.a(resources, i, i2);
        if (typefaceA2 == null) {
            try {
                if (string.toLowerCase().endsWith(".xml")) {
                    b bVarA = a.a(resources.getXml(i), resources);
                    if (bVarA == null) {
                        Log.e("ResourcesCompat", "Failed to find font-family tag");
                        typefaceA = null;
                    } else {
                        typefaceA = android.support.v4.a.b.a(context, bVarA, resources, i, i2, textView);
                    }
                } else {
                    typefaceA = android.support.v4.a.b.a(context, resources, i, i2);
                }
                return typefaceA;
            } catch (IOException e) {
                Log.e("ResourcesCompat", "Failed to read xml resource " + string, e);
                return null;
            } catch (XmlPullParserException e2) {
                Log.e("ResourcesCompat", "Failed to parse xml resource " + string, e2);
                return null;
            }
        }
        return typefaceA2;
    }
}
