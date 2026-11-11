package android.support.v4.widget;

import android.os.Build;
import android.widget.EdgeEffect;

/* JADX INFO: compiled from: EdgeEffectCompat.java */
/* JADX INFO: loaded from: classes.dex */
public final class h {
    private static final j iD;

    static {
        if (Build.VERSION.SDK_INT >= 21) {
            iD = new i();
        } else {
            iD = new j();
        }
    }

    public static void a(EdgeEffect edgeEffect, float f, float f2) {
        iD.a(edgeEffect, f, f2);
    }
}
