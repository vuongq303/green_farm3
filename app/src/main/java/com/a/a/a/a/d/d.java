package com.a.a.a.a.d;

import android.view.View;
import com.a.a.a.a.b.i;
import com.a.a.a.a.e.g;
import java.util.ArrayList;
import java.util.Collection;
import java.util.IdentityHashMap;
import java.util.Iterator;
import org.json.JSONObject;

/* JADX INFO: loaded from: classes.dex */
public class d implements a {
    private final a ceD;

    public d(a aVar) {
        this.ceD = aVar;
    }

    @Override // com.a.a.a.a.d.a
    public JSONObject U(View view) {
        return com.a.a.a.a.e.b.O(0, 0, 0, 0);
    }

    @Override // com.a.a.a.a.d.a
    public void a(View view, JSONObject jSONObject, b bVar, boolean z) {
        Iterator<View> it = aif().iterator();
        while (it.hasNext()) {
            bVar.a(it.next(), this.ceD, jSONObject);
        }
    }

    ArrayList<View> aif() {
        View rootView;
        ArrayList<View> arrayList = new ArrayList<>();
        com.a.a.a.a.c.a aVarAhU = com.a.a.a.a.c.a.ahU();
        if (aVarAhU != null) {
            Collection<i> collectionAhW = aVarAhU.ahW();
            IdentityHashMap identityHashMap = new IdentityHashMap((collectionAhW.size() * 2) + 3);
            Iterator<i> it = collectionAhW.iterator();
            while (it.hasNext()) {
                View viewAhS = it.next().ahS();
                if (viewAhS != null && g.W(viewAhS) && (rootView = viewAhS.getRootView()) != null && !identityHashMap.containsKey(rootView)) {
                    identityHashMap.put(rootView, rootView);
                    float fA = g.a(rootView);
                    int size = arrayList.size();
                    while (size > 0 && g.a(arrayList.get(size - 1)) > fA) {
                        size--;
                    }
                    arrayList.add(size, rootView);
                }
            }
        }
        return arrayList;
    }
}
