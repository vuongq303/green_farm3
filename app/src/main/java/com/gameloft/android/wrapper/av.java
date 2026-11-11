package com.gameloft.android.wrapper;

import android.content.Context;
import android.view.OrientationEventListener;

/* JADX INFO: compiled from: Utils.java */
/* JADX INFO: loaded from: classes.dex */
final class av extends OrientationEventListener {
    av(Context context) {
        super(context);
    }

    @Override // android.view.OrientationEventListener
    public void onOrientationChanged(int i) {
        if (au.biv != i && Math.abs(au.biv - i) >= 90) {
            au.bH(true);
            int unused = au.biv = i;
        }
    }
}
