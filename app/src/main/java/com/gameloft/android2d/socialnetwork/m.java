package com.gameloft.android2d.socialnetwork;

/* JADX INFO: compiled from: SocialNetwork.java */
/* JADX INFO: loaded from: classes.dex */
final class m implements Runnable {
    final /* synthetic */ String bqd;
    final /* synthetic */ String bqe;
    final /* synthetic */ String bqf;
    final /* synthetic */ String bqg;
    final /* synthetic */ String bqh;
    final /* synthetic */ String bqi;
    final /* synthetic */ String bqj;
    final /* synthetic */ String val$message;
    final /* synthetic */ String val$name;

    m(String str, String str2, String str3, String str4, String str5, String str6, String str7, String str8, String str9) {
        this.val$message = str;
        this.bqd = str2;
        this.val$name = str3;
        this.bqe = str4;
        this.bqf = str5;
        this.bqg = str6;
        this.bqh = str7;
        this.bqi = str8;
        this.bqj = str9;
    }

    @Override // java.lang.Runnable
    public void run() {
        if (l.Kx == 1) {
            s.d(this.val$message, this.bqd, this.val$name, this.bqe, this.bqf, this.bqg, this.bqh, this.bqi, this.bqj);
        }
    }
}
