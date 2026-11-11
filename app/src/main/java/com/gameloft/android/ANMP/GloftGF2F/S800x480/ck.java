package com.gameloft.android.ANMP.GloftGF2F.S800x480;

import com.facebook.R;
import org.json.JSONException;
import org.json.JSONObject;

/* JADX INFO: loaded from: classes.dex */
class ck implements com.gameloft.a.a.h {
    final /* synthetic */ ci bbN;

    public ck(ci ciVar) {
        this.bbN = ciVar;
    }

    @Override // com.gameloft.a.a.h
    public void a(boolean z, JSONObject jSONObject) {
        String str = "CRM_Callback :: success = " + z + "data = " + jSONObject;
        if (z) {
            try {
                int i = jSONObject.getInt(com.gameloft.a.a.m.buv);
                if (com.gameloft.a.a.o.VW().VY()) {
                    switch (i) {
                        case 6:
                        case 9:
                        case R.styleable.CardView_contentPaddingRight /* 10 */:
                        case 19:
                        case 20:
                        case 21:
                            return;
                        case 22:
                            String str2 = "CRM_Callback :: 'CRM_ACTION_GLADS_INCENTIVIZED_VIDEO' handle: " + jSONObject.getString(com.gameloft.a.a.m.buo);
                            String string = jSONObject.getString(com.gameloft.a.a.m.buo);
                            if (b.Kb) {
                                b.Kb = false;
                                b.hL();
                                b.ar(string);
                                return;
                            }
                            return;
                        default:
                            return;
                    }
                }
            } catch (JSONException e) {
            }
        }
    }
}
