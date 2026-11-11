package com.gameloft.android2d.socialnetwork;

import com.facebook.FacebookCallback;
import com.facebook.FacebookException;
import com.facebook.share.Sharer;
import org.json.JSONException;

/* JADX INFO: compiled from: SocialNetwork_Facebook.java */
/* JADX INFO: loaded from: classes.dex */
final class ac implements FacebookCallback<Sharer.Result> {
    ac() {
    }

    @Override // com.facebook.FacebookCallback
    /* JADX INFO: renamed from: a, reason: merged with bridge method [inline-methods] */
    public void onSuccess(Sharer.Result result) throws JSONException {
        r.F(0, "Posted story");
    }

    @Override // com.facebook.FacebookCallback
    public void onCancel() throws JSONException {
        r.F(1, "Publish cancelled");
    }

    @Override // com.facebook.FacebookCallback
    public void onError(FacebookException facebookException) throws JSONException {
        r.F(2, "Error posting story");
    }
}
