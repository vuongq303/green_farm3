package com.gameloft.android2d.socialnetwork;

import com.facebook.share.internal.ShareConstants;

/* JADX INFO: compiled from: SocialNetwork_Facebook.java */
/* JADX INFO: loaded from: classes.dex */
final class u implements Runnable {
    u() {
    }

    @Override // java.lang.Runnable
    public void run() {
        boolean unused = s.bqW = false;
        s.bqD = null;
        if (s.bqt != null) {
            s.a(s.bqt, ShareConstants.WEB_DIALOG_PARAM_ID, "name", "first_name", "middle_name", "last_name", "photos", "installed");
        }
    }
}
