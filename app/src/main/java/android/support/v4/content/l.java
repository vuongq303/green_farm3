package android.support.v4.content;

import android.content.BroadcastReceiver;
import android.content.IntentFilter;

/* JADX INFO: compiled from: LocalBroadcastManager.java */
/* JADX INFO: loaded from: classes.dex */
final class l {
    boolean eQ;
    boolean eR;
    final IntentFilter filter;
    final BroadcastReceiver receiver;

    l(IntentFilter intentFilter, BroadcastReceiver broadcastReceiver) {
        this.filter = intentFilter;
        this.receiver = broadcastReceiver;
    }

    public String toString() {
        StringBuilder sb = new StringBuilder(128);
        sb.append("Receiver{");
        sb.append(this.receiver);
        sb.append(" filter=");
        sb.append(this.filter);
        if (this.eR) {
            sb.append(" DEAD");
        }
        sb.append("}");
        return sb.toString();
    }
}
