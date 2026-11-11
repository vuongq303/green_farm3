package android.support.v7.app;

import android.R;
import android.content.Context;
import android.content.ContextWrapper;
import android.content.res.TypedArray;
import android.os.Build;
import android.support.v7.widget.AppCompatImageView;
import android.support.v7.widget.cx;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;
import java.lang.reflect.Constructor;
import java.util.Map;

/* JADX INFO: compiled from: AppCompatViewInflater.java */
/* JADX INFO: loaded from: classes.dex */
class ay {
    private static final Class<?>[] mj = {Context.class, AttributeSet.class};
    private static final int[] mk = {R.attr.onClick};
    private static final String[] ml = {"android.widget.", "android.view.", "android.webkit."};
    private static final Map<String, Constructor<? extends View>> mm = new android.support.v4.e.a();
    private final Object[] mn = new Object[2];

    ay() {
    }

    public final View a(View view, String str, Context context, AttributeSet attributeSet, boolean z, boolean z2, boolean z3, boolean z4) {
        Context context2 = (!z || view == null) ? context : view.getContext();
        if (z2 || z3) {
            context2 = a(context2, attributeSet, z2, z3);
        }
        if (z4) {
            context2 = cx.t(context2);
        }
        View aqVar = null;
        switch (str) {
            case "TextView":
                aqVar = new android.support.v7.widget.bb(context2, attributeSet);
                break;
            case "ImageView":
                aqVar = new AppCompatImageView(context2, attributeSet);
                break;
            case "Button":
                aqVar = new android.support.v7.widget.y(context2, attributeSet);
                break;
            case "EditText":
                aqVar = new android.support.v7.widget.ah(context2, attributeSet);
                break;
            case "Spinner":
                aqVar = new android.support.v7.widget.as(context2, attributeSet);
                break;
            case "ImageButton":
                aqVar = new android.support.v7.widget.ai(context2, attributeSet);
                break;
            case "CheckBox":
                aqVar = new android.support.v7.widget.z(context2, attributeSet);
                break;
            case "RadioButton":
                aqVar = new android.support.v7.widget.ao(context2, attributeSet);
                break;
            case "CheckedTextView":
                aqVar = new android.support.v7.widget.aa(context2, attributeSet);
                break;
            case "AutoCompleteTextView":
                aqVar = new android.support.v7.widget.w(context2, attributeSet);
                break;
            case "MultiAutoCompleteTextView":
                aqVar = new android.support.v7.widget.ak(context2, attributeSet);
                break;
            case "RatingBar":
                aqVar = new android.support.v7.widget.ap(context2, attributeSet);
                break;
            case "SeekBar":
                aqVar = new android.support.v7.widget.aq(context2, attributeSet);
                break;
        }
        View viewA = (aqVar != null || context == context2) ? aqVar : a(context2, str, attributeSet);
        if (viewA != null) {
            a(viewA, attributeSet);
        }
        return viewA;
    }

    private View a(Context context, String str, AttributeSet attributeSet) {
        if (str.equals("view")) {
            str = attributeSet.getAttributeValue(null, "class");
        }
        try {
            this.mn[0] = context;
            this.mn[1] = attributeSet;
            if (-1 != str.indexOf(46)) {
                return b(context, str, null);
            }
            for (int i = 0; i < ml.length; i++) {
                View viewB = b(context, str, ml[i]);
                if (viewB != null) {
                    return viewB;
                }
            }
            return null;
        } catch (Exception e) {
            return null;
        } finally {
            this.mn[0] = null;
            this.mn[1] = null;
        }
    }

    private void a(View view, AttributeSet attributeSet) {
        Context context = view.getContext();
        if (context instanceof ContextWrapper) {
            if (Build.VERSION.SDK_INT < 15 || android.support.v4.view.aa.q(view)) {
                TypedArray typedArrayObtainStyledAttributes = context.obtainStyledAttributes(attributeSet, mk);
                String string = typedArrayObtainStyledAttributes.getString(0);
                if (string != null) {
                    view.setOnClickListener(new az(view, string));
                }
                typedArrayObtainStyledAttributes.recycle();
            }
        }
    }

    private View b(Context context, String str, String str2) {
        Constructor<? extends View> constructor = mm.get(str);
        if (constructor == null) {
            try {
                constructor = context.getClassLoader().loadClass(str2 != null ? str2 + str : str).asSubclass(View.class).getConstructor(mj);
                mm.put(str, constructor);
            } catch (Exception e) {
                return null;
            }
        }
        constructor.setAccessible(true);
        return constructor.newInstance(this.mn);
    }

    private static Context a(Context context, AttributeSet attributeSet, boolean z, boolean z2) {
        TypedArray typedArrayObtainStyledAttributes = context.obtainStyledAttributes(attributeSet, android.support.v7.appcompat.R.styleable.View, 0, 0);
        int resourceId = z ? typedArrayObtainStyledAttributes.getResourceId(android.support.v7.appcompat.R.styleable.View_android_theme, 0) : 0;
        if (z2 && resourceId == 0 && (resourceId = typedArrayObtainStyledAttributes.getResourceId(android.support.v7.appcompat.R.styleable.View_theme, 0)) != 0) {
            Log.i("AppCompatViewInflater", "app:theme is now deprecated. Please move to using android:theme instead.");
        }
        int i = resourceId;
        typedArrayObtainStyledAttributes.recycle();
        if (i != 0) {
            if (!(context instanceof android.support.v7.c.e) || ((android.support.v7.c.e) context).ct() != i) {
                return new android.support.v7.c.e(context, i);
            }
            return context;
        }
        return context;
    }
}
