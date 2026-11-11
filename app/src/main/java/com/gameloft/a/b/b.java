package com.gameloft.a.b;

import com.gameloft.glads.GLURLConnection;

/* JADX INFO: compiled from: JW_Federation.java */
/* JADX INFO: loaded from: classes.dex */
final class b extends Thread {
    b() {
    }

    @Override // java.lang.Thread, java.lang.Runnable
    public void run() {
        String strWn = a.Wn();
        if (strWn != null && !strWn.equals("") && a.Wo()) {
            boolean unused = a.Ne = true;
            if (a.bwM != null) {
                a.bwM.a(3, null, GLURLConnection.HTTP_OK, null, null, null);
            }
        }
    }
}
