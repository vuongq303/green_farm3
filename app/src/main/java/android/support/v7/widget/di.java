package android.support.v7.widget;

import android.os.Build;
import android.view.View;

/* JADX INFO: compiled from: TooltipCompat.java */
/* JADX INFO: loaded from: classes.dex */
public class di {
    private static final dm yR;

    static {
        if (Build.VERSION.SDK_INT >= 26) {
            yR = new dk();
        } else {
            yR = new dl();
        }
    }

    public static void a(View view, CharSequence charSequence) {
        yR.a(view, charSequence);
    }
}
