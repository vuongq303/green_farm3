package com.gameloft.android2d.socialnetwork;

import com.facebook.GraphRequest;
import com.facebook.GraphResponse;
import com.facebook.share.internal.ShareConstants;
import java.util.ArrayList;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

/* JADX INFO: compiled from: SocialNetwork_Facebook.java */
/* JADX INFO: loaded from: classes.dex */
final class y implements GraphRequest.Callback {
    y() {
    }

    @Override // com.facebook.GraphRequest.Callback
    public void onCompleted(GraphResponse graphResponse) {
        JSONObject jSONObject = graphResponse.getJSONObject();
        if (jSONObject != null && jSONObject.has(ShareConstants.WEB_DIALOG_PARAM_DATA)) {
            try {
                JSONArray jSONArray = jSONObject.getJSONArray(ShareConstants.WEB_DIALOG_PARAM_DATA);
                ArrayList arrayList = new ArrayList();
                for (int i = 0; i < jSONArray.length(); i++) {
                    arrayList.add(s.e(jSONArray.getJSONObject(i)));
                }
                s.bqo.a(arrayList, false);
                s.bqZ = true;
                s.bqX = true;
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
    }
}
