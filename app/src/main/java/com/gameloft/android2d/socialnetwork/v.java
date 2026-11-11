package com.gameloft.android2d.socialnetwork;

import com.facebook.AccessToken;
import com.facebook.GraphRequest;
import com.facebook.GraphResponse;
import com.facebook.Profile;
import com.facebook.share.internal.ShareConstants;
import java.net.MalformedURLException;
import java.net.URL;
import org.json.JSONObject;

/* JADX INFO: compiled from: SocialNetwork_Facebook.java */
/* JADX INFO: loaded from: classes.dex */
final class v implements GraphRequest.GraphJSONObjectCallback {
    final /* synthetic */ AccessToken brt;

    v(AccessToken accessToken) {
        this.brt = accessToken;
    }

    @Override // com.facebook.GraphRequest.GraphJSONObjectCallback
    public void onCompleted(JSONObject jSONObject, GraphResponse graphResponse) {
        if (this.brt != null) {
        }
        if (jSONObject != null) {
            s.bqD = new i();
            try {
                String str = (String) s.a(jSONObject, ShareConstants.WEB_DIALOG_PARAM_ID, "");
                s.bqD.fG(str);
                if (s.brf == 1) {
                    int unused = s.brf = 0;
                    r.a(52014, 52009, str);
                }
                if (s.bqD.getId() != null) {
                }
                s.bqD.setName((String) s.a(jSONObject, "name", ""));
                if (s.bqD.getName() != null) {
                }
                s.bqD.gU((String) s.a(jSONObject, "first_name", ""));
                if (s.bqD.getFirstName() != null) {
                }
                s.bqD.gV((String) s.a(jSONObject, "last_name", ""));
                if (s.bqD.getLastName() != null) {
                }
                s.bqD.gW((String) s.a(jSONObject, "middle_name", ""));
                if (s.bqD.getMiddleName() != null) {
                }
                Object objA = s.a(jSONObject, "installed", "");
                if (objA != null) {
                    s.bqD.bL(Boolean.valueOf(objA.toString()).booleanValue());
                }
                String str2 = (String) s.a(jSONObject, "locale", "");
                if (str2 != null) {
                    s.bqD.gX(str2.substring(0, 2));
                    if (s.bqD.getCountry() != null) {
                    }
                }
                JSONObject jSONObjectA = s.a(jSONObject, "cover");
                if (jSONObjectA != null) {
                    s.brg = (String) s.a(jSONObjectA, "source", "");
                    if (s.brg != null) {
                        s.brg = s.brg.replace(String.valueOf('\\'), "");
                    }
                }
                s.bqq = Profile.getCurrentProfile();
                if (s.bqq != null) {
                    s.bqD.a(new URL(s.bqq.getProfilePictureUri(s.bqy, s.bqz).toString()));
                    s.a(s.bqD.getId(), s.bqD.UQ());
                }
                boolean unused2 = s.bqW = true;
            } catch (MalformedURLException e) {
                e.printStackTrace();
            }
        }
    }
}
