package com.google.android.gms.common;

import android.app.Dialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.v4.app.aa;
import com.google.android.gms.common.internal.bf;

/* JADX INFO: loaded from: classes.dex */
public class q extends android.support.v4.app.n {
    private Dialog mDialog = null;
    private DialogInterface.OnCancelListener bIa = null;

    public static q b(Dialog dialog, DialogInterface.OnCancelListener onCancelListener) {
        q qVar = new q();
        Dialog dialog2 = (Dialog) bf.d(dialog, "Cannot display null dialog");
        dialog2.setOnCancelListener(null);
        dialog2.setOnDismissListener(null);
        qVar.mDialog = dialog2;
        if (onCancelListener != null) {
            qVar.bIa = onCancelListener;
        }
        return qVar;
    }

    @Override // android.support.v4.app.n, android.content.DialogInterface.OnCancelListener
    public void onCancel(DialogInterface dialogInterface) {
        if (this.bIa != null) {
            this.bIa.onCancel(dialogInterface);
        }
    }

    @Override // android.support.v4.app.n
    public Dialog onCreateDialog(Bundle bundle) {
        if (this.mDialog == null) {
            setShowsDialog(false);
        }
        return this.mDialog;
    }

    @Override // android.support.v4.app.n
    public void show(aa aaVar, String str) {
        super.show(aaVar, str);
    }
}
