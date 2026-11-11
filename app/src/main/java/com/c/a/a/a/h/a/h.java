package com.c.a.a.a.h.a;

import android.text.TextUtils;
import java.util.HashSet;
import org.json.JSONObject;

/* JADX INFO: compiled from: AvidPublishAsyncTask.java */
/* JADX INFO: loaded from: classes.dex */
public class h extends a {
    public h(d dVar, com.c.a.a.a.e.a aVar, HashSet<String> hashSet, JSONObject jSONObject, double d) {
        super(dVar, aVar, hashSet, jSONObject, d);
    }

    /* JADX INFO: Access modifiers changed from: protected */
    @Override // android.os.AsyncTask
    /* JADX INFO: renamed from: z, reason: merged with bridge method [inline-methods] */
    public String doInBackground(Object... objArr) {
        if (com.c.a.a.a.g.b.i(this.ckA, this.ckD.ajD())) {
            return null;
        }
        this.ckD.Q(this.ckA);
        return com.c.a.a.a.g.a.iS(com.c.a.a.a.g.b.a(this.ckA, this.ckB).toString());
    }

    /* JADX INFO: Access modifiers changed from: protected */
    @Override // com.c.a.a.a.h.a.b, android.os.AsyncTask
    /* JADX INFO: renamed from: iO */
    public void onPostExecute(String str) {
        if (!TextUtils.isEmpty(str)) {
            iY(str);
        }
        super.onPostExecute(str);
    }

    private void iY(String str) {
        for (com.c.a.a.a.f.a.a aVar : this.ckm.aiX()) {
            if (this.ckz.contains(aVar.aiZ())) {
                aVar.c(str, this.ckB);
            }
        }
    }
}
