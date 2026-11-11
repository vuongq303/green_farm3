package android.support.v7.app;

import android.content.BroadcastReceiver;
import android.content.IntentFilter;

/* JADX INFO: compiled from: AppCompatDelegateImplV14.java */
/* JADX INFO: loaded from: classes.dex */
final class af {
    final /* synthetic */ ad lk;
    private bf ll;
    private boolean lm;
    private BroadcastReceiver ln;
    private IntentFilter lo;

    af(ad adVar, bf bfVar) {
        this.lk = adVar;
        this.ll = bfVar;
        this.lm = bfVar.bY();
    }

    final int bK() {
        this.lm = this.ll.bY();
        return this.lm ? 2 : 1;
    }

    final void bL() {
        boolean zBY = this.ll.bY();
        if (zBY != this.lm) {
            this.lm = zBY;
            this.lk.bA();
        }
    }

    final void setup() {
        bM();
        if (this.ln == null) {
            this.ln = new ag(this);
        }
        if (this.lo == null) {
            this.lo = new IntentFilter();
            this.lo.addAction("android.intent.action.TIME_SET");
            this.lo.addAction("android.intent.action.TIMEZONE_CHANGED");
            this.lo.addAction("android.intent.action.TIME_TICK");
        }
        this.lk.mContext.registerReceiver(this.ln, this.lo);
    }

    final void bM() {
        if (this.ln != null) {
            this.lk.mContext.unregisterReceiver(this.ln);
            this.ln = null;
        }
    }
}
