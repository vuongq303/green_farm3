package android.support.v7.c.a;

import android.content.DialogInterface;
import android.os.IBinder;
import android.support.v7.appcompat.R;
import android.view.KeyEvent;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;

/* JADX INFO: compiled from: MenuDialogHelper.java */
/* JADX INFO: loaded from: classes.dex */
class v implements DialogInterface.OnClickListener, DialogInterface.OnDismissListener, DialogInterface.OnKeyListener, aj {
    private s nw;
    private aj pO;
    private android.support.v7.app.t qP;
    p qQ;

    public v(s sVar) {
        this.nw = sVar;
    }

    public void c(IBinder iBinder) {
        s sVar = this.nw;
        android.support.v7.app.u uVar = new android.support.v7.app.u(sVar.getContext());
        this.qQ = new p(uVar.getContext(), R.layout.abc_list_menu_item_layout);
        this.qQ.b(this);
        this.nw.a(this.qQ);
        uVar.a(this.qQ.getAdapter(), this);
        View viewDj = sVar.dj();
        if (viewDj != null) {
            uVar.y(viewDj);
        } else {
            uVar.f(sVar.di()).i(sVar.dh());
        }
        uVar.a(this);
        this.qP = uVar.bu();
        this.qP.setOnDismissListener(this);
        WindowManager.LayoutParams attributes = this.qP.getWindow().getAttributes();
        attributes.type = 1003;
        if (iBinder != null) {
            attributes.token = iBinder;
        }
        attributes.flags |= 131072;
        this.qP.show();
    }

    @Override // android.content.DialogInterface.OnKeyListener
    public boolean onKey(DialogInterface dialogInterface, int i, KeyEvent keyEvent) {
        Window window;
        View decorView;
        KeyEvent.DispatcherState keyDispatcherState;
        View decorView2;
        KeyEvent.DispatcherState keyDispatcherState2;
        if (i == 82 || i == 4) {
            if (keyEvent.getAction() == 0 && keyEvent.getRepeatCount() == 0) {
                Window window2 = this.qP.getWindow();
                if (window2 != null && (decorView2 = window2.getDecorView()) != null && (keyDispatcherState2 = decorView2.getKeyDispatcherState()) != null) {
                    keyDispatcherState2.startTracking(keyEvent, this);
                    return true;
                }
            } else if (keyEvent.getAction() == 1 && !keyEvent.isCanceled() && (window = this.qP.getWindow()) != null && (decorView = window.getDecorView()) != null && (keyDispatcherState = decorView.getKeyDispatcherState()) != null && keyDispatcherState.isTracking(keyEvent)) {
                this.nw.u(true);
                dialogInterface.dismiss();
                return true;
            }
        }
        return this.nw.performShortcut(i, keyEvent, 0);
    }

    public void dismiss() {
        if (this.qP != null) {
            this.qP.dismiss();
        }
    }

    @Override // android.content.DialogInterface.OnDismissListener
    public void onDismiss(DialogInterface dialogInterface) {
        this.qQ.b(this.nw, true);
    }

    @Override // android.support.v7.c.a.aj
    public void b(s sVar, boolean z) {
        if (z || sVar == this.nw) {
            dismiss();
        }
        if (this.pO != null) {
            this.pO.b(sVar, z);
        }
    }

    @Override // android.support.v7.c.a.aj
    public boolean c(s sVar) {
        if (this.pO != null) {
            return this.pO.c(sVar);
        }
        return false;
    }

    @Override // android.content.DialogInterface.OnClickListener
    public void onClick(DialogInterface dialogInterface, int i) {
        this.nw.a((w) this.qQ.getAdapter().getItem(i), 0);
    }
}
