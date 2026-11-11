package com.gameloft.android2d.iap;

import android.os.Bundle;
import com.facebook.internal.ServerProtocol;
import org.json.JSONException;
import org.json.JSONObject;

/* JADX INFO: compiled from: IAPLib.java */
/* JADX INFO: loaded from: classes.dex */
final class f extends com.gameloft.android2d.iap.utils.m {
    f() {
    }

    @Override // com.gameloft.android2d.iap.utils.m
    public void p(Bundle bundle) {
        String string;
        if (bundle.getInt("a5") == 1) {
            try {
                JSONObject jSONObject = new JSONObject(bundle.getString("a4"));
                if (jSONObject.length() > 0 && (string = jSONObject.getString("enabled")) != null && !string.equals("") && string.equals(ServerProtocol.DIALOG_RETURN_SCOPES_TRUE)) {
                    ae.bm(true);
                } else {
                    ae.bm(false);
                }
                return;
            } catch (JSONException e) {
                return;
            }
        }
        ae.bm(false);
    }
}
