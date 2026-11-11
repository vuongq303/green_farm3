package android.support.v4.view.a;

import android.view.accessibility.AccessibilityRecord;

/* JADX INFO: compiled from: AccessibilityRecordCompat.java */
/* JADX INFO: loaded from: classes.dex */
class y extends aa {
    y() {
    }

    @Override // android.support.v4.view.a.aa
    public void a(AccessibilityRecord accessibilityRecord, int i) {
        accessibilityRecord.setMaxScrollX(i);
    }

    @Override // android.support.v4.view.a.aa
    public void b(AccessibilityRecord accessibilityRecord, int i) {
        accessibilityRecord.setMaxScrollY(i);
    }
}
