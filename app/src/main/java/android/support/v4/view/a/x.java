package android.support.v4.view.a;

import android.os.Build;
import android.view.accessibility.AccessibilityRecord;

/* JADX INFO: compiled from: AccessibilityRecordCompat.java */
/* JADX INFO: loaded from: classes.dex */
public class x {
    private static final aa hU;
    private final AccessibilityRecord hV;

    static {
        if (Build.VERSION.SDK_INT >= 16) {
            hU = new z();
        } else if (Build.VERSION.SDK_INT >= 15) {
            hU = new y();
        } else {
            hU = new aa();
        }
    }

    public static void a(AccessibilityRecord accessibilityRecord, int i) {
        hU.a(accessibilityRecord, i);
    }

    public static void b(AccessibilityRecord accessibilityRecord, int i) {
        hU.b(accessibilityRecord, i);
    }

    @Deprecated
    public int hashCode() {
        if (this.hV == null) {
            return 0;
        }
        return this.hV.hashCode();
    }

    @Deprecated
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj != null && getClass() == obj.getClass()) {
            x xVar = (x) obj;
            if (this.hV == null) {
                return xVar.hV == null;
            }
            return this.hV.equals(xVar.hV);
        }
        return false;
    }
}
