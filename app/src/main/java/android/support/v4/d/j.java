package android.support.v4.d;

import android.os.Handler;
import android.os.Message;

/* JADX INFO: compiled from: SelfDestructiveThread.java */
/* JADX INFO: loaded from: classes.dex */
class j implements Handler.Callback {
    final /* synthetic */ i gg;

    j(i iVar) {
        this.gg = iVar;
    }

    /* JADX WARN: Can't fix incorrect switch cases order, some code will duplicate */
    @Override // android.os.Handler.Callback
    public boolean handleMessage(Message message) {
        switch (message.what) {
            case 0:
                this.gg.aF();
                return true;
            case 1:
                this.gg.b((Runnable) message.obj);
                return true;
            default:
                return true;
        }
    }
}
