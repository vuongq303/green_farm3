package android.support.v4.widget;

import android.os.Bundle;
import android.view.View;
import android.view.accessibility.AccessibilityEvent;
import android.widget.ScrollView;
import com.facebook.internal.Utility;

/* JADX INFO: compiled from: NestedScrollView.java */
/* JADX INFO: loaded from: classes.dex */
class q extends android.support.v4.view.b {
    q() {
    }

    @Override // android.support.v4.view.b
    public boolean performAccessibilityAction(View view, int i, Bundle bundle) {
        if (super.performAccessibilityAction(view, i, bundle)) {
            return true;
        }
        NestedScrollView nestedScrollView = (NestedScrollView) view;
        if (!nestedScrollView.isEnabled()) {
            return false;
        }
        switch (i) {
            case 4096:
                int iMin = Math.min(((nestedScrollView.getHeight() - nestedScrollView.getPaddingBottom()) - nestedScrollView.getPaddingTop()) + nestedScrollView.getScrollY(), nestedScrollView.bj());
                if (iMin == nestedScrollView.getScrollY()) {
                    return false;
                }
                nestedScrollView.smoothScrollTo(0, iMin);
                return true;
            case Utility.DEFAULT_STREAM_BUFFER_SIZE /* 8192 */:
                int iMax = Math.max(nestedScrollView.getScrollY() - ((nestedScrollView.getHeight() - nestedScrollView.getPaddingBottom()) - nestedScrollView.getPaddingTop()), 0);
                if (iMax == nestedScrollView.getScrollY()) {
                    return false;
                }
                nestedScrollView.smoothScrollTo(0, iMax);
                return true;
            default:
                return false;
        }
    }

    @Override // android.support.v4.view.b
    public void a(View view, android.support.v4.view.a.a aVar) {
        int iBj;
        super.a(view, aVar);
        NestedScrollView nestedScrollView = (NestedScrollView) view;
        aVar.setClassName(ScrollView.class.getName());
        if (nestedScrollView.isEnabled() && (iBj = nestedScrollView.bj()) > 0) {
            aVar.setScrollable(true);
            if (nestedScrollView.getScrollY() > 0) {
                aVar.addAction(Utility.DEFAULT_STREAM_BUFFER_SIZE);
            }
            if (nestedScrollView.getScrollY() < iBj) {
                aVar.addAction(4096);
            }
        }
    }

    @Override // android.support.v4.view.b
    public void onInitializeAccessibilityEvent(View view, AccessibilityEvent accessibilityEvent) {
        super.onInitializeAccessibilityEvent(view, accessibilityEvent);
        NestedScrollView nestedScrollView = (NestedScrollView) view;
        accessibilityEvent.setClassName(ScrollView.class.getName());
        accessibilityEvent.setScrollable(nestedScrollView.bj() > 0);
        accessibilityEvent.setScrollX(nestedScrollView.getScrollX());
        accessibilityEvent.setScrollY(nestedScrollView.getScrollY());
        android.support.v4.view.a.x.a(accessibilityEvent, nestedScrollView.getScrollX());
        android.support.v4.view.a.x.b(accessibilityEvent, nestedScrollView.bj());
    }
}
