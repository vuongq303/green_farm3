package com.a.a.a.a.g;

import android.os.Handler;
import android.webkit.WebView;
import com.a.a.a.a.b.h;
import com.a.a.a.a.c.f;
import java.util.Iterator;
import java.util.List;

/* JADX INFO: loaded from: classes.dex */
public class d extends a {
    private final String c;
    private List<h> ceP;
    private WebView cet;

    public d(List<h> list, String str) {
        this.ceP = list;
        this.c = str;
    }

    @Override // com.a.a.a.a.g.a
    public void a() {
        super.a();
        i();
    }

    @Override // com.a.a.a.a.g.a
    public void b() {
        super.b();
        new Handler().postDelayed(new e(this), 2000L);
        this.cet = null;
    }

    void i() {
        this.cet = new WebView(com.a.a.a.a.c.e.ahY().ahZ());
        this.cet.getSettings().setJavaScriptEnabled(true);
        b(this.cet);
        f.aia().a(this.cet, this.c);
        Iterator<h> it = this.ceP.iterator();
        while (it.hasNext()) {
            f.aia().b(this.cet, it.next().ahM().toExternalForm());
        }
    }
}
