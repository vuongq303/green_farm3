package android.support.v4.view;

import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.view.accessibility.AccessibilityEvent;

/* JADX INFO: compiled from: AccessibilityDelegateCompat.java */
/* JADX INFO: loaded from: classes.dex */
public class b {
    private static final e gZ;
    private static final View.AccessibilityDelegate ha;
    final View.AccessibilityDelegate hb = gZ.a(this);

    static {
        if (Build.VERSION.SDK_INT >= 16) {
            gZ = new c();
        } else {
            gZ = new e();
        }
        ha = new View.AccessibilityDelegate();
    }

    public void sendAccessibilityEvent(View view, int i) {
        ha.sendAccessibilityEvent(view, i);
    }

    public void sendAccessibilityEventUnchecked(View view, AccessibilityEvent accessibilityEvent) {
        ha.sendAccessibilityEventUnchecked(view, accessibilityEvent);
    }

    public boolean dispatchPopulateAccessibilityEvent(View view, AccessibilityEvent accessibilityEvent) {
        return ha.dispatchPopulateAccessibilityEvent(view, accessibilityEvent);
    }

    public void onPopulateAccessibilityEvent(View view, AccessibilityEvent accessibilityEvent) {
        ha.onPopulateAccessibilityEvent(view, accessibilityEvent);
    }

    public void onInitializeAccessibilityEvent(View view, AccessibilityEvent accessibilityEvent) {
        ha.onInitializeAccessibilityEvent(view, accessibilityEvent);
    }

    public void a(View view, android.support.v4.view.a.a aVar) {
        ha.onInitializeAccessibilityNodeInfo(view, aVar.aT());
    }

    public boolean onRequestSendAccessibilityEvent(ViewGroup viewGroup, View view, AccessibilityEvent accessibilityEvent) {
        return ha.onRequestSendAccessibilityEvent(viewGroup, view, accessibilityEvent);
    }

    public android.support.v4.view.a.k d(View view) {
        return gZ.a(ha, view);
    }

    public boolean performAccessibilityAction(View view, int i, Bundle bundle) {
        return gZ.a(ha, view, i, bundle);
    }
}
