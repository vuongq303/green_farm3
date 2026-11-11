package com.gameloft.android2d.socialnetwork;

/* JADX INFO: compiled from: SocialNetwork.java */
/* JADX INFO: loaded from: classes.dex */
final class q implements Runnable {
    final /* synthetic */ String val$data;
    final /* synthetic */ String val$message;

    q(String str, String str2) {
        this.val$message = str;
        this.val$data = str2;
    }

    @Override // java.lang.Runnable
    public void run() {
        if (l.Kx == 1) {
            s.ao(this.val$message, this.val$data);
        }
    }
}
