package com.a.a.a.a.d;

import android.os.Build;
import android.view.View;
import android.view.ViewGroup;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.Iterator;
import org.json.JSONObject;

/* JADX INFO: loaded from: classes.dex */
public class e implements a {
    private final int[] ceE = new int[2];

    private void a(ViewGroup viewGroup, JSONObject jSONObject, b bVar) {
        for (int i = 0; i < viewGroup.getChildCount(); i++) {
            bVar.a(viewGroup.getChildAt(i), this, jSONObject);
        }
    }

    private void b(ViewGroup viewGroup, JSONObject jSONObject, b bVar) {
        HashMap map = new HashMap();
        int i = 0;
        while (true) {
            int i2 = i;
            if (i2 >= viewGroup.getChildCount()) {
                break;
            }
            View childAt = viewGroup.getChildAt(i2);
            ArrayList arrayList = (ArrayList) map.get(Float.valueOf(childAt.getZ()));
            if (arrayList == null) {
                arrayList = new ArrayList();
                map.put(Float.valueOf(childAt.getZ()), arrayList);
            }
            arrayList.add(childAt);
            i = i2 + 1;
        }
        ArrayList arrayList2 = new ArrayList(map.keySet());
        Collections.sort(arrayList2);
        Iterator it = arrayList2.iterator();
        while (it.hasNext()) {
            Iterator it2 = ((ArrayList) map.get((Float) it.next())).iterator();
            while (it2.hasNext()) {
                bVar.a((View) it2.next(), this, jSONObject);
            }
        }
    }

    @Override // com.a.a.a.a.d.a
    public JSONObject U(View view) {
        int width = view.getWidth();
        int height = view.getHeight();
        view.getLocationOnScreen(this.ceE);
        return com.a.a.a.a.e.b.O(this.ceE[0], this.ceE[1], width, height);
    }

    @Override // com.a.a.a.a.d.a
    public void a(View view, JSONObject jSONObject, b bVar, boolean z) {
        if (view instanceof ViewGroup) {
            ViewGroup viewGroup = (ViewGroup) view;
            if (!z || Build.VERSION.SDK_INT < 21) {
                a(viewGroup, jSONObject, bVar);
            } else {
                b(viewGroup, jSONObject, bVar);
            }
        }
    }
}
