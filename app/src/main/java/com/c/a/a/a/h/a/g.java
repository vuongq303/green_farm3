package com.c.a.a.a.h.a;

import java.util.HashSet;
import org.json.JSONObject;

/* JADX INFO: compiled from: AvidEmptyPublishAsyncTask.java */
/* JADX INFO: loaded from: classes.dex */
public class g extends a {
    public g(d dVar, com.c.a.a.a.e.a aVar, HashSet<String> hashSet, JSONObject jSONObject, double d) {
        super(dVar, aVar, hashSet, jSONObject, d);
    }

    /* JADX INFO: Access modifiers changed from: protected */
    @Override // android.os.AsyncTask
    /* JADX INFO: renamed from: z, reason: merged with bridge method [inline-methods] */
    public String doInBackground(Object... objArr) {
        return com.c.a.a.a.g.a.iS(com.c.a.a.a.g.b.a(this.ckA, this.ckB).toString());
    }

    /* JADX INFO: Access modifiers changed from: protected */
    @Override // com.c.a.a.a.h.a.b, android.os.AsyncTask
    /* JADX INFO: renamed from: iO */
    public void onPostExecute(String str) {
        for (com.c.a.a.a.f.a.a aVar : this.ckm.aiX()) {
            if (this.ckz.contains(aVar.aiZ())) {
                aVar.d(str, this.ckB);
            }
        }
        super.onPostExecute(str);
    }
}
