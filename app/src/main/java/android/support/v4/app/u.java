package android.support.v4.app;

import android.os.Handler;
import android.os.Message;

/* JADX INFO: compiled from: FragmentActivity.java */
/* JADX INFO: loaded from: classes.dex */
class u extends Handler {
    final /* synthetic */ FragmentActivity aH;

    u(FragmentActivity fragmentActivity) {
        this.aH = fragmentActivity;
    }

    @Override // android.os.Handler
    public void handleMessage(Message message) {
        switch (message.what) {
            case 1:
                if (this.aH.mStopped) {
                    this.aH.doReallyStop(false);
                }
                break;
            case 2:
                this.aH.onResumeFragments();
                this.aH.mFragments.execPendingActions();
                break;
            default:
                super.handleMessage(message);
                break;
        }
    }
}
