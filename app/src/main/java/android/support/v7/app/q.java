package android.support.v7.app;

import android.content.DialogInterface;
import android.os.Handler;
import android.os.Message;
import com.facebook.login.widget.ProfilePictureView;
import java.lang.ref.WeakReference;

/* JADX INFO: compiled from: AlertController.java */
/* JADX INFO: loaded from: classes.dex */
final class q extends Handler {
    private WeakReference<DialogInterface> kE;

    public q(DialogInterface dialogInterface) {
        this.kE = new WeakReference<>(dialogInterface);
    }

    @Override // android.os.Handler
    public void handleMessage(Message message) {
        switch (message.what) {
            case ProfilePictureView.NORMAL /* -3 */:
            case -2:
            case -1:
                ((DialogInterface.OnClickListener) message.obj).onClick(this.kE.get(), message.what);
                break;
            case 1:
                ((DialogInterface) message.obj).dismiss();
                break;
        }
    }
}
