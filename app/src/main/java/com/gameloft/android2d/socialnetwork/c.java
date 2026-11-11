package com.gameloft.android2d.socialnetwork;

import java.io.IOException;
import java.util.concurrent.Callable;

/* JADX INFO: compiled from: GameHelper.java */
/* JADX INFO: loaded from: classes.dex */
class c implements Callable<Void> {
    final /* synthetic */ a bpG;

    c(a aVar) {
        this.bpG = aVar;
    }

    @Override // java.util.concurrent.Callable
    public Void call() {
        try {
            com.google.android.gms.auth.b.v(this.bpG.eJ, a.bmK);
        } catch (com.google.android.gms.auth.a e) {
            this.bpG.gT("GameHelper: invalidateAccessToken: exception thrown:" + e.toString());
        } catch (com.google.android.gms.auth.c e2) {
            this.bpG.gT("GameHelper: invalidateAccessToken: exception thrown:" + e2.toString());
        } catch (IOException e3) {
            this.bpG.gT("GameHelper: invalidateAccessToken: exception thrown:" + e3.toString());
        } finally {
            a.bmK = null;
        }
        return null;
    }
}
