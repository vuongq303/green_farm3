package android.support.v4.view.a;

import android.os.Bundle;
import java.util.ArrayList;
import java.util.List;

/* JADX INFO: compiled from: AccessibilityNodeProviderCompat.java */
/* JADX INFO: loaded from: classes.dex */
class n implements t {
    final /* synthetic */ m hP;
    private final /* synthetic */ k hQ;

    n(m mVar, k kVar) {
        this.hP = mVar;
        this.hQ = kVar;
    }

    @Override // android.support.v4.view.a.t
    public boolean performAction(int i, int i2, Bundle bundle) {
        return this.hQ.performAction(i, i2, bundle);
    }

    @Override // android.support.v4.view.a.t
    public List<Object> findAccessibilityNodeInfosByText(String str, int i) {
        List<a> listFindAccessibilityNodeInfosByText = this.hQ.findAccessibilityNodeInfosByText(str, i);
        if (listFindAccessibilityNodeInfosByText == null) {
            return null;
        }
        ArrayList arrayList = new ArrayList();
        int size = listFindAccessibilityNodeInfosByText.size();
        for (int i2 = 0; i2 < size; i2++) {
            arrayList.add(listFindAccessibilityNodeInfosByText.get(i2).aT());
        }
        return arrayList;
    }

    @Override // android.support.v4.view.a.t
    public Object B(int i) {
        a aVarZ = this.hQ.z(i);
        if (aVarZ == null) {
            return null;
        }
        return aVarZ.aT();
    }
}
