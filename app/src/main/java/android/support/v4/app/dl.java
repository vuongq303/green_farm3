package android.support.v4.app;

import android.os.Bundle;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.Set;

/* JADX INFO: compiled from: RemoteInputCompatJellybean.java */
/* JADX INFO: loaded from: classes.dex */
class dl {
    static Bundle a(dj djVar) {
        Bundle bundle = new Bundle();
        bundle.putString("resultKey", djVar.getResultKey());
        bundle.putCharSequence("label", djVar.getLabel());
        bundle.putCharSequenceArray("choices", djVar.getChoices());
        bundle.putBoolean("allowFreeFormInput", djVar.getAllowFreeFormInput());
        bundle.putBundle("extras", djVar.getExtras());
        Set<String> allowedDataTypes = djVar.getAllowedDataTypes();
        if (allowedDataTypes != null && !allowedDataTypes.isEmpty()) {
            ArrayList<String> arrayList = new ArrayList<>(allowedDataTypes.size());
            Iterator<String> it = allowedDataTypes.iterator();
            while (it.hasNext()) {
                arrayList.add(it.next());
            }
            bundle.putStringArrayList("allowedDataTypes", arrayList);
        }
        return bundle;
    }

    static Bundle[] b(dj[] djVarArr) {
        if (djVarArr == null) {
            return null;
        }
        Bundle[] bundleArr = new Bundle[djVarArr.length];
        for (int i = 0; i < djVarArr.length; i++) {
            bundleArr[i] = a(djVarArr[i]);
        }
        return bundleArr;
    }
}
