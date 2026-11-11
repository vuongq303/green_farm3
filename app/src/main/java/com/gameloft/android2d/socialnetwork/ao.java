package com.gameloft.android2d.socialnetwork;

/* JADX INFO: compiled from: SocialNetwork_Google.java */
/* JADX INFO: loaded from: classes.dex */
final class ao implements com.google.android.gms.common.api.af<com.google.android.gms.games.f> {
    ao() {
    }

    @Override // com.google.android.gms.common.api.af
    public void a(com.google.android.gms.games.f fVar) {
        String strAcp = fVar.acp();
        if (strAcp == null || strAcp.isEmpty()) {
            r.hb("");
        } else {
            r.hb(strAcp);
        }
    }
}
