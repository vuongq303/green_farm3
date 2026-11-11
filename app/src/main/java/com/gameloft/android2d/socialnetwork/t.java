package com.gameloft.android2d.socialnetwork;

import com.facebook.AccessToken;
import com.facebook.FacebookCallback;
import com.facebook.FacebookException;
import com.facebook.Profile;
import com.facebook.login.LoginResult;
import org.json.JSONException;

/* JADX INFO: compiled from: SocialNetwork_Facebook.java */
/* JADX INFO: loaded from: classes.dex */
final class t implements FacebookCallback<LoginResult> {
    t() {
    }

    @Override // com.facebook.FacebookCallback
    /* JADX INFO: renamed from: c, reason: merged with bridge method [inline-methods] */
    public void onSuccess(LoginResult loginResult) throws JSONException {
        s.bqq = Profile.getCurrentProfile();
        AccessToken unused = s.bqt = AccessToken.getCurrentAccessToken();
        r.E(1, "OPENED");
    }

    @Override // com.facebook.FacebookCallback
    public void onCancel() throws JSONException {
        r.E(4, "CANCEL");
    }

    @Override // com.facebook.FacebookCallback
    public void onError(FacebookException facebookException) throws JSONException {
        r.E(2, "ERROR");
    }
}
