package com.gameloft.android2d.socialnetwork;

/* JADX INFO: compiled from: SocialNetwork.java */
/* JADX INFO: loaded from: classes.dex */
final class n implements Runnable {
    final /* synthetic */ int bhK;

    n(int i) {
        this.bhK = i;
    }

    @Override // java.lang.Runnable
    public void run() {
        if (this.bhK == 1) {
            s.nV();
        }
    }
}
