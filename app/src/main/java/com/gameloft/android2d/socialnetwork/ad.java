package com.gameloft.android2d.socialnetwork;

import com.facebook.FacebookCallback;
import com.facebook.FacebookException;
import com.facebook.share.widget.GameRequestDialog;
import java.util.List;

/* JADX INFO: compiled from: SocialNetwork_Facebook.java */
/* JADX INFO: loaded from: classes.dex */
final class ad implements FacebookCallback<GameRequestDialog.Result> {
    ad() {
    }

    @Override // com.facebook.FacebookCallback
    /* JADX INFO: renamed from: a, reason: merged with bridge method [inline-methods] */
    public void onSuccess(GameRequestDialog.Result result) {
        if (result != null) {
            if (result.getRequestId() != null) {
            }
            List<String> requestRecipients = result.getRequestRecipients();
            if (requestRecipients != null) {
                if (!requestRecipients.isEmpty()) {
                    String[] strArr = new String[requestRecipients.size()];
                    for (int i = 0; i < requestRecipients.size(); i++) {
                        strArr[i] = requestRecipients.get(i);
                    }
                    r.d(strArr);
                    for (String str : requestRecipients) {
                    }
                    r.G(0, "Request sent");
                    return;
                }
                r.G(3, "Request sent no friend");
            }
        }
    }

    @Override // com.facebook.FacebookCallback
    public void onCancel() {
        r.G(1, "Request cancelled");
    }

    @Override // com.facebook.FacebookCallback
    public void onError(FacebookException facebookException) {
        r.G(2, "Request error");
    }
}
