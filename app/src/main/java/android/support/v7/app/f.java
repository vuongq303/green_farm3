package android.support.v7.app;

import android.os.Message;
import android.view.View;

/* JADX INFO: compiled from: AlertController.java */
/* JADX INFO: loaded from: classes.dex */
class f implements View.OnClickListener {
    final /* synthetic */ e jZ;

    f(e eVar) {
        this.jZ = eVar;
    }

    @Override // android.view.View.OnClickListener
    public void onClick(View view) {
        Message messageObtain;
        if (view == this.jZ.jy && this.jZ.jA != null) {
            messageObtain = Message.obtain(this.jZ.jA);
        } else if (view == this.jZ.jB && this.jZ.jD != null) {
            messageObtain = Message.obtain(this.jZ.jD);
        } else if (view == this.jZ.jE && this.jZ.jG != null) {
            messageObtain = Message.obtain(this.jZ.jG);
        } else {
            messageObtain = null;
        }
        if (messageObtain != null) {
            messageObtain.sendToTarget();
        }
        this.jZ.mHandler.obtainMessage(1, this.jZ.jn).sendToTarget();
    }
}
