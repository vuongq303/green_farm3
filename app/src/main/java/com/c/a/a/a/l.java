package com.c.a.a.a;

import android.view.View;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import org.json.JSONObject;

/* JADX INFO: compiled from: AvidTreeWalker.java */
/* JADX INFO: loaded from: classes.dex */
public class l implements com.c.a.a.a.d.e {
    private static o cjb;
    private int cjd;
    private double cjh;
    private double cji;
    private static l cja = new l();
    private static final Runnable cjj = new m();
    private List<n> cjc = new ArrayList();
    private com.c.a.a.a.h.a cjf = new com.c.a.a.a.h.a(com.c.a.a.a.e.a.aiW());
    private com.c.a.a.a.d.a cje = new com.c.a.a.a.d.a();
    private com.c.a.a.a.h.b cjg = new com.c.a.a.a.h.b(com.c.a.a.a.e.a.aiW(), new com.c.a.a.a.h.a.e());

    public static l aiL() {
        return cja;
    }

    public void start() {
        aiQ();
        aiM();
    }

    public void stop() {
        pause();
        this.cjc.clear();
        this.cjg.ajC();
    }

    public void pause() {
        aiR();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void aiM() {
        aiN();
        aiP();
        aiO();
    }

    private void aiN() {
        this.cjd = 0;
        this.cjh = com.c.a.a.a.g.d.ajy();
    }

    private void aiO() {
        this.cji = com.c.a.a.a.g.d.ajy();
        R((long) (this.cji - this.cjh));
    }

    void aiP() {
        this.cjf.prepare();
        double dAjy = com.c.a.a.a.g.d.ajy();
        com.c.a.a.a.d.d dVarAiV = this.cje.aiV();
        if (this.cjf.ajA().size() > 0) {
            this.cjg.d(dVarAiV.ab(null), this.cjf.ajA(), dAjy);
        }
        if (this.cjf.ajz().size() > 0) {
            JSONObject jSONObjectAb = dVarAiV.ab(null);
            a(null, dVarAiV, jSONObjectAb, com.c.a.a.a.h.c.ROOT_VIEW);
            com.c.a.a.a.g.b.P(jSONObjectAb);
            this.cjg.c(jSONObjectAb, this.cjf.ajz(), dAjy);
        } else {
            this.cjg.ajC();
        }
        this.cjf.bM();
    }

    @Override // com.c.a.a.a.d.e
    public void a(View view, com.c.a.a.a.d.d dVar, JSONObject jSONObject) {
        com.c.a.a.a.h.c cVarAh;
        if (com.c.a.a.a.g.e.ad(view) && (cVarAh = this.cjf.ah(view)) != com.c.a.a.a.h.c.UNDERLYING_VIEW) {
            JSONObject jSONObjectAb = dVar.ab(view);
            com.c.a.a.a.g.b.h(jSONObject, jSONObjectAb);
            if (!c(view, jSONObjectAb)) {
                d(view, jSONObjectAb);
                a(view, dVar, jSONObjectAb, cVarAh);
            }
            this.cjd++;
        }
    }

    private void a(View view, com.c.a.a.a.d.d dVar, JSONObject jSONObject, com.c.a.a.a.h.c cVar) {
        dVar.a(view, jSONObject, this, cVar == com.c.a.a.a.h.c.ROOT_VIEW);
    }

    private boolean c(View view, JSONObject jSONObject) {
        String strAf = this.cjf.af(view);
        if (strAf == null) {
            return false;
        }
        com.c.a.a.a.g.b.j(jSONObject, strAf);
        this.cjf.ajB();
        return true;
    }

    private void d(View view, JSONObject jSONObject) {
        ArrayList<String> arrayListAg = this.cjf.ag(view);
        if (arrayListAg != null) {
            com.c.a.a.a.g.b.b(jSONObject, arrayListAg);
        }
    }

    private void R(long j) {
        if (this.cjc.size() > 0) {
            Iterator<n> it = this.cjc.iterator();
            while (it.hasNext()) {
                it.next().c(this.cjd, j);
            }
        }
    }

    private void aiQ() {
        if (cjb == null) {
            cjb = new o(null);
            cjb.postDelayed(cjj, 200L);
        }
    }

    private void aiR() {
        if (cjb != null) {
            cjb.removeCallbacks(cjj);
            cjb = null;
        }
    }
}
