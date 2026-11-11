package com.gameloft.android2d.socialnetwork;

import com.facebook.AccessToken;
import com.facebook.AccessTokenTracker;
import org.json.JSONException;

/* JADX INFO: compiled from: SocialNetwork_Facebook.java */
/* JADX INFO: loaded from: classes.dex */
final class af extends AccessTokenTracker {
    af() {
    }

    @Override // com.facebook.AccessTokenTracker
    protected void onCurrentAccessTokenChanged(AccessToken accessToken, AccessToken accessToken2) throws JSONException {
        if (accessToken2 == null) {
            r.E(3, "CLOSED");
        }
        if (accessToken != null) {
        }
        if (accessToken2 != null) {
        }
    }
}
