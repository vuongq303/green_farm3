package com.gameloft.android2d.iap;

import android.os.Bundle;
import com.facebook.share.internal.ShareConstants;
import org.json.JSONObject;

/* JADX INFO: compiled from: IAPLib.java */
/* JADX INFO: loaded from: classes.dex */
final class i extends com.gameloft.android2d.iap.utils.m {
    i() {
    }

    @Override // com.gameloft.android2d.iap.utils.m
    public void p(Bundle bundle) {
        if (bundle.getInt("a5") == 2) {
            int i = bundle.getInt("a6");
            if (i == 403 || i == 429) {
                String string = "";
                String string2 = "";
                try {
                    JSONObject jSONObject = new JSONObject(bundle.getString("a4"));
                    string = jSONObject.getString(ShareConstants.WEB_DIALOG_PARAM_MESSAGE);
                    string2 = jSONObject.getString("code");
                } catch (Exception e) {
                }
                if (string != null && !string.equals("")) {
                    ae.dD(string2);
                    b.eH(string);
                } else {
                    b.setResult(3);
                    b.mB(-5);
                }
            } else {
                b.setResult(3);
                b.mB(-1);
            }
        } else {
            b.setResult(3);
            b.mB(-5);
        }
        bundle.clear();
    }
}
