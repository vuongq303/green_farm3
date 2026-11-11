package com.gameloft.android2d.socialnetwork;

/* JADX INFO: compiled from: SocialNetwork.java */
/* JADX INFO: loaded from: classes.dex */
final class p implements Runnable {
    final /* synthetic */ i[] bql;
    final /* synthetic */ i[] bqm;
    final /* synthetic */ String val$data;
    final /* synthetic */ String val$message;

    p(i[] iVarArr, i[] iVarArr2, String str, String str2) {
        this.bqm = iVarArr;
        this.bql = iVarArr2;
        this.val$message = str;
        this.val$data = str2;
    }

    @Override // java.lang.Runnable
    public void run() {
        if (l.Kx == 1) {
            s.a(this.bqm, this.bql, this.val$message, this.val$data);
        }
    }
}
