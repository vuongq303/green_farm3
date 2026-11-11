package com.gameloft.android2d.socialnetwork;

import com.facebook.GraphRequest;
import com.facebook.GraphResponse;
import com.facebook.share.internal.ShareConstants;
import org.json.JSONArray;
import org.json.JSONObject;

/* JADX INFO: compiled from: SocialNetwork_Facebook.java */
/* JADX INFO: loaded from: classes.dex */
final class ah implements GraphRequest.Callback {
    ah() {
    }

    @Override // com.facebook.GraphRequest.Callback
    public void onCompleted(GraphResponse graphResponse) {
        JSONObject jSONObject = graphResponse.getJSONObject();
        if (jSONObject != null && jSONObject.has(ShareConstants.WEB_DIALOG_PARAM_DATA)) {
            JSONArray jSONArrayB = s.b(jSONObject, ShareConstants.WEB_DIALOG_PARAM_DATA);
            s.bqr = 0;
            if (jSONArrayB != null) {
                s.bqr = jSONArrayB.length();
            }
        }
        s.bqY = true;
    }
}
