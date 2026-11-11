package com.gameloft.android.ANMP.GloftGF2F;

import android.content.DialogInterface;
import android.view.KeyEvent;

/* JADX INFO: compiled from: ActivityMessage.java */
/* JADX INFO: loaded from: classes.dex */
class b implements DialogInterface.OnKeyListener {
    final /* synthetic */ ActivityMessage Aj;

    b(ActivityMessage activityMessage) {
        this.Aj = activityMessage;
    }

    @Override // android.content.DialogInterface.OnKeyListener
    public boolean onKey(DialogInterface dialogInterface, int i, KeyEvent keyEvent) {
        if (i == 4) {
        }
        return true;
    }
}
