package android.support.v4.app;

import android.os.Bundle;
import android.os.IBinder;
import android.util.Log;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

/* JADX INFO: compiled from: BundleCompat.java */
/* JADX INFO: loaded from: classes.dex */
class m {
    private static Method ak;
    private static boolean al;

    public static void a(Bundle bundle, String str, IBinder iBinder) {
        if (!al) {
            try {
                ak = Bundle.class.getMethod("putIBinder", String.class, IBinder.class);
                ak.setAccessible(true);
            } catch (NoSuchMethodException e) {
                Log.i("BundleCompatBaseImpl", "Failed to retrieve putIBinder method", e);
            }
            al = true;
        }
        if (ak != null) {
            try {
                ak.invoke(bundle, str, iBinder);
            } catch (IllegalAccessException | IllegalArgumentException | InvocationTargetException e2) {
                Log.i("BundleCompatBaseImpl", "Failed to invoke putIBinder via reflection", e2);
                ak = null;
            }
        }
    }
}
