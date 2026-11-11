package android.support.v4.content;

import android.os.Handler;
import android.os.Looper;
import android.os.Message;

/* JADX INFO: compiled from: ModernAsyncTask.java */
/* JADX INFO: loaded from: classes.dex */
class r extends Handler {
    public r() {
        super(Looper.getMainLooper());
    }

    @Override // android.os.Handler
    public void handleMessage(Message message) {
        q qVar = (q) message.obj;
        switch (message.what) {
            case 1:
                qVar.fe.finish(qVar.ff[0]);
                break;
            case 2:
                qVar.fe.onProgressUpdate(qVar.ff);
                break;
        }
    }
}
