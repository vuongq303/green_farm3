package com.c.a.a.a.d;

import android.os.Build;
import android.view.View;
import android.view.ViewGroup;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.Iterator;
import org.json.JSONObject;

/* JADX INFO: compiled from: AvidViewProcessor.java */
/* JADX INFO: loaded from: classes.dex */
public class c implements d {
    private final int[] cjs = new int[2];

    @Override // com.c.a.a.a.d.d
    public JSONObject ab(View view) {
        int width = view.getWidth();
        int height = view.getHeight();
        view.getLocationOnScreen(this.cjs);
        return com.c.a.a.a.g.b.P(this.cjs[0], this.cjs[1], width, height);
    }

    @Override // com.c.a.a.a.d.d
    public void a(View view, JSONObject jSONObject, e eVar, boolean z) {
        if (view instanceof ViewGroup) {
            ViewGroup viewGroup = (ViewGroup) view;
            if (!z || Build.VERSION.SDK_INT < 21) {
                a(viewGroup, jSONObject, eVar);
            } else {
                b(viewGroup, jSONObject, eVar);
            }
        }
    }

    private void a(ViewGroup viewGroup, JSONObject jSONObject, e eVar) {
        for (int i = 0; i < viewGroup.getChildCount(); i++) {
            eVar.a(viewGroup.getChildAt(i), this, jSONObject);
        }
    }

    private void b(ViewGroup viewGroup, JSONObject jSONObject, e eVar) {
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
                eVar.a((View) it2.next(), this, jSONObject);
            }
        }
    }
}
