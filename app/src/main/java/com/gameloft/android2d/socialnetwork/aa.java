package com.gameloft.android2d.socialnetwork;

import com.facebook.AccessToken;
import com.facebook.FacebookCallback;
import com.facebook.FacebookException;
import com.facebook.Profile;
import com.facebook.login.LoginResult;

/* JADX INFO: compiled from: SocialNetwork_Facebook.java */
/* JADX INFO: loaded from: classes.dex */
final class aa implements FacebookCallback<LoginResult> {
    aa() {
    }

    @Override // com.facebook.FacebookCallback
    /* JADX INFO: renamed from: c, reason: merged with bridge method [inline-methods] */
    public void onSuccess(LoginResult loginResult) {
        s.bqq = Profile.getCurrentProfile();
        AccessToken unused = s.bqt = AccessToken.getCurrentAccessToken();
        s.a(loginResult);
    }

    @Override // com.facebook.FacebookCallback
    public void onCancel() {
    }

    @Override // com.facebook.FacebookCallback
    public void onError(FacebookException facebookException) {
    }
}
