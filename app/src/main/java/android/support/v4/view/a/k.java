package android.support.v4.view.a;

import android.os.Build;
import android.os.Bundle;
import java.util.List;

/* JADX INFO: compiled from: AccessibilityNodeProviderCompat.java */
/* JADX INFO: loaded from: classes.dex */
public class k {
    private static final l hN;
    private final Object hO;

    static {
        if (Build.VERSION.SDK_INT >= 19) {
            hN = new o();
        } else if (Build.VERSION.SDK_INT >= 16) {
            hN = new m();
        } else {
            hN = new q();
        }
    }

    public k() {
        this.hO = hN.a(this);
    }

    public k(Object obj) {
        this.hO = obj;
    }

    public Object aU() {
        return this.hO;
    }

    public a z(int i) {
        return null;
    }

    public boolean performAction(int i, int i2, Bundle bundle) {
        return false;
    }

    public List<a> findAccessibilityNodeInfosByText(String str, int i) {
        return null;
    }

    public a A(int i) {
        return null;
    }
}
