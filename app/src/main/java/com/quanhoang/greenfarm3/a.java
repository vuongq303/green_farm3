package com.quanhoang.greenfarm3;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Build;
import javax.microedition.midlet.MIDlet;

/* JADX INFO: compiled from: ActivityMessage.java */
/* JADX INFO: loaded from: classes.dex */
class a implements DialogInterface.OnClickListener {
    final /* synthetic */ ActivityMessage Aj;

    a(ActivityMessage activityMessage) {
        this.Aj = activityMessage;
    }

    @Override // android.content.DialogInterface.OnClickListener
    public void onClick(DialogInterface dialogInterface, int i) {
        try {
            MIDlet.cnz.akA();
            if (Build.VERSION.SDK_INT < 15) {
                try {
                    Thread.sleep(500L);
                    MIDlet.cnz.akA();
                } catch (Exception e) {
                }
            }
            Intent intent = new Intent(com.gameloft.android.wrapper.au.getActivity(), (Class<?>) ActivityClearAllTasks.class);
            intent.addFlags(276856832);
            intent.putExtra(new String(this.Aj.Ae), true);
            com.gameloft.android.wrapper.au.getActivity().startActivity(intent);
        } catch (Exception e2) {
        }
        try {
            Thread.sleep(500L);
        } catch (Exception e3) {
        }
        MIDlet.cnz.akE();
    }
}
