package android.support.v4.view;

import android.os.Bundle;
import android.view.View;
import android.view.accessibility.AccessibilityNodeProvider;

/* JADX INFO: compiled from: AccessibilityDelegateCompat.java */
/* JADX INFO: loaded from: classes.dex */
class c extends e {
    c() {
    }

    @Override // android.support.v4.view.e
    public View.AccessibilityDelegate a(b bVar) {
        return new d(this, bVar);
    }

    @Override // android.support.v4.view.e
    public android.support.v4.view.a.k a(View.AccessibilityDelegate accessibilityDelegate, View view) {
        AccessibilityNodeProvider accessibilityNodeProvider = accessibilityDelegate.getAccessibilityNodeProvider(view);
        if (accessibilityNodeProvider != null) {
            return new android.support.v4.view.a.k(accessibilityNodeProvider);
        }
        return null;
    }

    @Override // android.support.v4.view.e
    public boolean a(View.AccessibilityDelegate accessibilityDelegate, View view, int i, Bundle bundle) {
        return accessibilityDelegate.performAccessibilityAction(view, i, bundle);
    }
}
