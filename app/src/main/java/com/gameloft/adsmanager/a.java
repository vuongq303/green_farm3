package com.gameloft.adsmanager;

import android.view.ViewGroup;
import android.widget.RelativeLayout;

/* JADX INFO: compiled from: AdsManager.java */
/* JADX INFO: loaded from: classes.dex */
final class a implements Runnable {
    a() {
    }

    @Override // java.lang.Runnable
    public void run() {
        AdsManager.relativeLayout = new RelativeLayout(AdsManager.parentViewGroup.getContext());
        AdsManager.parentViewGroup.addView(AdsManager.relativeLayout, new ViewGroup.LayoutParams(-1, -1));
    }
}
