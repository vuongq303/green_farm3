package com.a.a.a.a.h;

import android.os.Handler;
import android.os.Looper;
import android.view.View;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import org.json.JSONObject;

/* JADX INFO: loaded from: classes.dex */
public class a implements com.a.a.a.a.d.b {
    private int ceU;
    private double ceY;
    private static a ceS = new a();
    private static Handler b = new Handler(Looper.getMainLooper());
    private static Handler c = null;
    private static final Runnable ceZ = new c();
    private static final Runnable cfa = new d();
    private List<e> ceT = new ArrayList();
    private f ceW = new f();
    private com.a.a.a.a.d.c ceV = new com.a.a.a.a.d.c();
    private g ceX = new g(new com.a.a.a.a.h.a.e());

    a() {
    }

    private void a(long j) {
        if (this.ceT.size() > 0) {
            Iterator<e> it = this.ceT.iterator();
            while (it.hasNext()) {
                it.next().c(this.ceU, j);
            }
        }
    }

    private void a(View view, com.a.a.a.a.d.a aVar, JSONObject jSONObject, h hVar) {
        aVar.a(view, jSONObject, this, hVar == h.PARENT_VIEW);
    }

    private boolean a(View view, JSONObject jSONObject) {
        String strY = this.ceW.Y(view);
        if (strY == null) {
            return false;
        }
        com.a.a.a.a.e.b.i(jSONObject, strY);
        this.ceW.e();
        return true;
    }

    public static a aik() {
        return ceS;
    }

    private void ail() {
        a((long) (com.a.a.a.a.e.e.aii() - this.ceY));
    }

    private void aim() {
        if (c != null) {
            c.removeCallbacks(cfa);
            c = null;
        }
    }

    private void b(View view, JSONObject jSONObject) {
        ArrayList<String> arrayListZ = this.ceW.Z(view);
        if (arrayListZ != null) {
            com.a.a.a.a.e.b.a(jSONObject, arrayListZ);
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void h() {
        i();
        d();
        ail();
    }

    private void i() {
        this.ceU = 0;
        this.ceY = com.a.a.a.a.e.e.aii();
    }

    private void k() {
        if (c == null) {
            c = new Handler(Looper.getMainLooper());
            c.post(ceZ);
            c.postDelayed(cfa, 200L);
        }
    }

    public void a() {
        k();
    }

    @Override // com.a.a.a.a.d.b
    public void a(View view, com.a.a.a.a.d.a aVar, JSONObject jSONObject) {
        h hVarAa;
        if (com.a.a.a.a.e.g.X(view) && (hVarAa = this.ceW.aa(view)) != h.UNDERLYING_VIEW) {
            JSONObject jSONObjectU = aVar.U(view);
            com.a.a.a.a.e.b.b(jSONObject, jSONObjectU);
            if (!a(view, jSONObjectU)) {
                b(view, jSONObjectU);
                a(view, aVar, jSONObjectU, hVarAa);
            }
            this.ceU++;
        }
    }

    public void b() {
        c();
        this.ceT.clear();
        b.post(new b(this));
    }

    public void c() {
        aim();
    }

    void d() {
        this.ceW.c();
        double dAii = com.a.a.a.a.e.e.aii();
        com.a.a.a.a.d.a aVarAie = this.ceV.aie();
        if (this.ceW.air().size() > 0) {
            this.ceX.b(aVarAie.U(null), this.ceW.air(), dAii);
        }
        if (this.ceW.aiq().size() > 0) {
            JSONObject jSONObjectU = aVarAie.U(null);
            a(null, aVarAie, jSONObjectU, h.PARENT_VIEW);
            com.a.a.a.a.e.b.O(jSONObjectU);
            this.ceX.a(jSONObjectU, this.ceW.aiq(), dAii);
        } else {
            this.ceX.a();
        }
        this.ceW.d();
    }
}
