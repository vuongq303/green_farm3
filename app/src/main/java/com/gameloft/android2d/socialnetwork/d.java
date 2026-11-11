package com.gameloft.android2d.socialnetwork;

import java.io.IOException;

/* JADX INFO: compiled from: GameHelper.java */
/* JADX INFO: loaded from: classes.dex */
class d implements Runnable {
    final /* synthetic */ a bpG;

    d(a aVar) {
        this.bpG = aVar;
    }

    @Override // java.lang.Runnable
    public void run() {
        try {
            if (this.bpG.mP(2)) {
                a.bmK = com.google.android.gms.auth.b.e(this.bpG.eJ, com.google.android.gms.plus.d.bXP.getAccountName(this.bpG.bps), "oauth2:https://www.googleapis.com/auth/plus.login");
            } else {
                a.bmK = null;
                this.bpG.gT("GameHelper: Google: No Plus Client => no access token");
            }
            if (a.bmK != null) {
                this.bpG.gT("GameHelper: Google: GetAccessToken: " + a.bmK);
                this.bpG.UE();
            } else {
                this.bpG.a(new f(30));
            }
        } catch (com.google.android.gms.auth.c e) {
            this.bpG.a(new f(e.Xz()));
        } catch (com.google.android.gms.auth.d e2) {
            this.bpG.mActivity.startActivityForResult(e2.getIntent(), 9001);
        } catch (com.google.android.gms.auth.a e3) {
            this.bpG.gT("GameHelper: Google: Error getting token! GoogleAuthException:" + e3.toString());
            this.bpG.a(new f(30));
        } catch (IOException e4) {
            this.bpG.gT("GameHelper: Google: Error getting token! IOException:" + e4.toString());
            this.bpG.a(new f(30));
        } catch (Exception e5) {
            this.bpG.gT("GameHelper: Google: Error getting token:" + e5.toString());
            this.bpG.a(new f(30));
        }
    }
}
