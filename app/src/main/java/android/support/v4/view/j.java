package android.support.v4.view;

import android.os.Build;
import android.view.Gravity;

/* JADX INFO: compiled from: GravityCompat.java */
/* JADX INFO: loaded from: classes.dex */
public final class j {
    public static int getAbsoluteGravity(int i, int i2) {
        return Build.VERSION.SDK_INT >= 17 ? Gravity.getAbsoluteGravity(i, i2) : (-8388609) & i;
    }
}
