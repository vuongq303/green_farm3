package android.support.a;

import android.os.Bundle;

/* JADX INFO: compiled from: CustomTabsClient.java */
/* JADX INFO: loaded from: classes.dex */
class c extends k {
    final /* synthetic */ a s;
    final /* synthetic */ b t;

    c(b bVar, a aVar) {
        this.t = bVar;
        this.s = aVar;
    }

    @Override // android.support.a.j
    public void a(int i, Bundle bundle) {
        if (this.s != null) {
            this.s.a(i, bundle);
        }
    }

    @Override // android.support.a.j
    public void a(String str, Bundle bundle) {
        if (this.s != null) {
            this.s.a(str, bundle);
        }
    }
}
