package android.support.v4.content;

import android.os.Handler;
import android.os.Looper;
import android.os.Message;

/* JADX INFO: compiled from: LocalBroadcastManager.java */
/* JADX INFO: loaded from: classes.dex */
class j extends Handler {
    final /* synthetic */ i eO;

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    j(i iVar, Looper looper) {
        super(looper);
        this.eO = iVar;
    }

    @Override // android.os.Handler
    public void handleMessage(Message message) {
        switch (message.what) {
            case 1:
                this.eO.ak();
                break;
            default:
                super.handleMessage(message);
                break;
        }
    }
}
