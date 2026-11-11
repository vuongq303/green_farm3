package com.a.a.a.a.h.a;

import com.a.a.a.a.b.i;
import java.util.HashSet;
import org.json.JSONObject;

/* JADX INFO: loaded from: classes.dex */
public class g extends a {
    public g(d dVar, HashSet<String> hashSet, JSONObject jSONObject, double d) {
        super(dVar, hashSet, jSONObject, d);
    }

    private void iK(String str) {
        com.a.a.a.a.c.a aVarAhU = com.a.a.a.a.c.a.ahU();
        if (aVarAhU != null) {
            for (i iVar : aVarAhU.ahV()) {
                if (this.cfg.contains(iVar.ahR())) {
                    iVar.ahQ().b(str, this.c);
                }
            }
        }
    }

    /* JADX INFO: Access modifiers changed from: protected */
    @Override // com.a.a.a.a.h.a.b, android.os.AsyncTask
    /* JADX INFO: renamed from: a */
    public void onPostExecute(String str) {
        iK(str);
        super.onPostExecute(str);
    }

    /* JADX INFO: Access modifiers changed from: protected */
    @Override // android.os.AsyncTask
    /* JADX INFO: renamed from: y, reason: merged with bridge method [inline-methods] */
    public String doInBackground(Object... objArr) {
        return this.cfh.toString();
    }
}
