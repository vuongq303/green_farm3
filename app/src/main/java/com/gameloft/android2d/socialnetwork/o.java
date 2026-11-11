package com.gameloft.android2d.socialnetwork;

/* JADX INFO: compiled from: SocialNetwork.java */
/* JADX INFO: loaded from: classes.dex */
final class o implements Runnable {
    final /* synthetic */ i bqk;
    final /* synthetic */ i[] bql;
    final /* synthetic */ String val$data;
    final /* synthetic */ String val$message;

    o(i iVar, i[] iVarArr, String str, String str2) {
        this.bqk = iVar;
        this.bql = iVarArr;
        this.val$message = str;
        this.val$data = str2;
    }

    @Override // java.lang.Runnable
    public void run() {
        if (l.Kx == 1) {
            s.a(this.bqk, this.bql, this.val$message, this.val$data);
        }
    }
}
