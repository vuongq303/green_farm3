package com.a.a.a.a.c;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.IntentFilter;
import com.a.a.a.a.b.i;
import java.util.Iterator;

/* JADX INFO: loaded from: classes.dex */
public class b {
    private static b cen = new b();
    private Context ceo;
    private BroadcastReceiver cep;
    private d ceq;
    private boolean d;
    private boolean e;

    private b() {
    }

    public static b ahX() {
        return cen;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void ch(boolean z) {
        if (this.e != z) {
            this.e = z;
            if (this.d) {
                g();
                if (this.ceq != null) {
                    this.ceq.ch(ahT());
                }
            }
        }
    }

    private void e() {
        this.cep = new c(this);
        IntentFilter intentFilter = new IntentFilter();
        intentFilter.addAction("android.intent.action.SCREEN_OFF");
        intentFilter.addAction("android.intent.action.SCREEN_ON");
        intentFilter.addAction("android.intent.action.USER_PRESENT");
        this.ceo.registerReceiver(this.cep, intentFilter);
    }

    private void f() {
        if (this.ceo == null || this.cep == null) {
            return;
        }
        this.ceo.unregisterReceiver(this.cep);
        this.cep = null;
    }

    private void g() {
        boolean z = !this.e;
        Iterator<i> it = a.ahU().ahV().iterator();
        while (it.hasNext()) {
            it.next().ahQ().ch(z);
        }
    }

    public void a(Context context) {
        this.ceo = context.getApplicationContext();
    }

    public void a(d dVar) {
        this.ceq = dVar;
    }

    public boolean ahT() {
        return !this.e;
    }

    public void b() {
        e();
        this.d = true;
        g();
    }

    public void c() {
        f();
        this.d = false;
        this.e = false;
        this.ceq = null;
    }
}
