package com.gameloft.android.wrapper;

/* JADX INFO: compiled from: Utils.java */
/* JADX INFO: loaded from: classes.dex */
final class bb implements Runnable {
    bb() {
    }

    @Override // java.lang.Runnable
    public void run() {
        try {
            au.userAgent = System.getProperty("http.agent").trim();
            if (au.userAgent == null || au.userAgent.equalsIgnoreCase("")) {
                au.userAgent = au.A("userAgent", "", "userAgent" + au.Pz());
            } else {
                au.a("userAgent", au.userAgent, "userAgent" + au.Pz());
            }
        } catch (Exception e) {
            au.userAgent = "GL_EMU_001";
        }
    }
}
