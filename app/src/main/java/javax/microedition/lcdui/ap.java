package javax.microedition.lcdui;

import android.app.ProgressDialog;
import android.content.Context;
import javax.microedition.midlet.MIDlet;

/* JADX INFO: compiled from: ProgressDialog.java */
/* JADX INFO: loaded from: classes.dex */
public class ap extends ProgressDialog {
    public Boolean clt;

    public ap(Context context) {
        super(context);
        this.clt = new Boolean(false);
        com.gameloft.android.wrapper.y.a(this);
    }

    @Override // android.app.Dialog, android.content.DialogInterface
    public void cancel() {
        com.gameloft.android.wrapper.y.b(this);
        this.clt = null;
        super.cancel();
    }

    @Override // android.app.Dialog, android.content.DialogInterface
    public void dismiss() {
        com.gameloft.android.wrapper.y.b(this);
        this.clt = null;
        super.dismiss();
    }

    @Override // android.app.Dialog
    public void hide() {
        com.gameloft.android.wrapper.y.b(this);
        this.clt = null;
        super.hide();
    }

    @Override // android.app.Dialog
    public void show() {
        Boolean bool = new Boolean(true);
        this.clt = bool;
        com.gameloft.android.wrapper.y.a(this, bool);
        com.gameloft.android.wrapper.y.bfI = true;
        String str = "ProgressDialog show: " + com.gameloft.android.wrapper.y.bfI;
        super.show();
    }

    @Override // android.app.Dialog, android.view.Window.Callback
    public void onWindowFocusChanged(boolean z) {
        String str = "ProgressDialog onWindowFocusChanged: " + z + " Interrupt:" + com.gameloft.android.wrapper.y.bfI;
        super.onWindowFocusChanged(z);
        if (com.gameloft.android.wrapper.y.bfI != z) {
            if (com.gameloft.android.wrapper.y.c(this)) {
                Boolean bool = new Boolean(z);
                this.clt = bool;
                com.gameloft.android.wrapper.y.b(this, bool);
                MIDlet.cnz.onWindowFocusChanged(z);
                return;
            }
            Boolean bool2 = new Boolean(z);
            this.clt = bool2;
            com.gameloft.android.wrapper.y.b(this, bool2);
        }
    }
}
