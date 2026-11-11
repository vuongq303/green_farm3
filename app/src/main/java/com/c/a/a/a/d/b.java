package com.c.a.a.a.d;

import android.view.View;
import java.util.Iterator;
import org.json.JSONObject;

/* JADX INFO: compiled from: AvidSceenProcessor.java */
/* JADX INFO: loaded from: classes.dex */
public class b implements d {
    private final d cjr;

    public b(d dVar) {
        this.cjr = dVar;
    }

    @Override // com.c.a.a.a.d.d
    public JSONObject ab(View view) {
        return com.c.a.a.a.g.b.P(0, 0, 0, 0);
    }

    @Override // com.c.a.a.a.d.d
    public void a(View view, JSONObject jSONObject, e eVar, boolean z) {
        Iterator<View> it = com.c.a.a.a.a.a.aiT().aiU().iterator();
        while (it.hasNext()) {
            eVar.a(it.next(), this.cjr, jSONObject);
        }
    }
}
