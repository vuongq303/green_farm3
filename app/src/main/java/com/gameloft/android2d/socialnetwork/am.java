package com.gameloft.android2d.socialnetwork;

import android.os.Looper;
import com.gameloft.android.wrapper.au;

/* JADX INFO: compiled from: SocialNetwork_Google.java */
/* JADX INFO: loaded from: classes.dex */
final class am implements Runnable {
    am() {
    }

    @Override // java.lang.Runnable
    public void run() {
        Looper.prepare();
        boolean unused = aj.brz = false;
        au.getActivity().runOnUiThread(new an(this));
    }
}
