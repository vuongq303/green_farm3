package javax.microedition.midlet;

import android.os.Build;
import android.view.View;
import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

/* JADX INFO: compiled from: App.java */
/* JADX INFO: loaded from: classes.dex */
final class ad {
    private static final String[] coZ = {"com.android.internal.policy.DecorView", "com.android.internal.policy.impl.PhoneWindow$DecorView", "com.android.internal.policy.impl.PhoneWindow.DecorView", "com.android.internal.policy.PhoneWindow$DecorView", "com.android.internal.policy.PhoneWindow.DecorView"};

    public static List<View> akS() {
        ArrayList arrayList = new ArrayList();
        try {
            if (Build.VERSION.SDK_INT >= 17) {
                Object objInvoke = Class.forName("android.view.WindowManagerGlobal").getMethod("getInstance", new Class[0]).invoke(null, new Object[0]);
                Field declaredField = objInvoke.getClass().getDeclaredField("mViews");
                declaredField.setAccessible(true);
                Iterator it = ((ArrayList) declaredField.get(objInvoke)).iterator();
                while (it.hasNext()) {
                    arrayList.add((View) it.next());
                }
            } else {
                Field declaredField2 = Class.forName("android.view.WindowManagerImpl").getDeclaredField("sWindowManager");
                declaredField2.setAccessible(true);
                Object obj = declaredField2.get(null);
                Field declaredField3 = obj.getClass().getDeclaredField("mViews");
                declaredField3.setAccessible(true);
                for (View view : (View[]) declaredField3.get(obj)) {
                    arrayList.add(view);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        for (int size = arrayList.size() - 1; size >= 0; size--) {
            View view2 = (View) arrayList.get(size);
            if (!ai(view2)) {
                arrayList.remove(view2);
            }
        }
        return arrayList;
    }

    public static View akT() {
        List<View> listAkS = akS();
        if (listAkS.size() > 0) {
            return listAkS.get(listAkS.size() - 1);
        }
        return null;
    }

    public static boolean akU() {
        List<View> listAkS = akS();
        for (int size = listAkS.size() - 1; size >= 0; size--) {
            if (listAkS.get(size).hasWindowFocus()) {
                return true;
            }
        }
        return false;
    }

    private static boolean ai(View view) {
        Class<?> cls = view.getClass();
        for (String str : coZ) {
            try {
                if (cls == Class.forName(str)) {
                    return true;
                }
            } catch (ClassNotFoundException e) {
                e.printStackTrace();
            }
        }
        return false;
    }
}
