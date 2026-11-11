package android.support.v4.view.a;

import android.os.Bundle;
import java.util.List;

/* JADX INFO: compiled from: AccessibilityNodeProviderCompatKitKat.java */
/* JADX INFO: loaded from: classes.dex */
interface w {
    Object B(int i);

    Object C(int i);

    List<Object> findAccessibilityNodeInfosByText(String str, int i);

    boolean performAction(int i, int i2, Bundle bundle);
}
