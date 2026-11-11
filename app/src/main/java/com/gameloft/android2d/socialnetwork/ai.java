package com.gameloft.android2d.socialnetwork;

import com.facebook.FacebookException;
import com.facebook.FacebookRequestError;
import com.facebook.GraphRequest;
import com.facebook.GraphResponse;
import com.facebook.share.internal.ShareConstants;
import java.util.ArrayList;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

/* JADX INFO: compiled from: SocialNetwork_Facebook.java */
/* JADX INFO: loaded from: classes.dex */
final class ai implements GraphRequest.Callback {
    ai() {
    }

    @Override // com.facebook.GraphRequest.Callback
    public void onCompleted(GraphResponse graphResponse) {
        if (graphResponse != null) {
            FacebookRequestError error = graphResponse.getError();
            FacebookException exception = error == null ? null : error.getException();
            if (graphResponse.getJSONObject() == null && exception == null) {
                exception = new FacebookException("GraphObjectPagingLoader received neither a result nor an error.");
            }
            if (exception == null) {
                JSONObject jSONObject = graphResponse.getJSONObject();
                if (jSONObject != null && jSONObject.has(ShareConstants.WEB_DIALOG_PARAM_DATA)) {
                    try {
                        h<i> hVar = new h<>();
                        JSONArray jSONArray = jSONObject.getJSONArray(ShareConstants.WEB_DIALOG_PARAM_DATA);
                        ArrayList arrayList = new ArrayList();
                        for (int i = 0; i < jSONArray.length(); i++) {
                            arrayList.add(s.e(jSONArray.getJSONObject(i)));
                        }
                        hVar.a(arrayList, false);
                        s.bqo = hVar;
                        s.bqZ = true;
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }
                s.bqX = true;
                GraphRequest unused = s.brh = graphResponse.getRequestForPagedResults(GraphResponse.PagingDirection.NEXT);
            }
        }
    }
}
