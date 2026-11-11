package com.gameloft.android2d.iap.a.a;

import android.app.Activity;
import android.content.Intent;
import android.os.Build;
import android.text.TextUtils;
import com.facebook.appevents.AppEventsConstants;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

/* JADX INFO: compiled from: GoogleBilling.java */
/* JADX INFO: loaded from: classes.dex */
public class a extends com.gameloft.android2d.iap.a.a {
    private static r blM;
    private static boolean blO = false;
    private static int blS = 0;
    private String blN = null;
    ac blP = new d(this);
    aa blQ = new e(this);
    y blR = new g(this);
    ac blT = new n(this);

    static /* synthetic */ int Sj() {
        int i = blS;
        blS = i - 1;
        return i;
    }

    public a() {
        eQ(com.gameloft.android2d.iap.utils.v.mJ(com.b.d.cgl));
    }

    public static boolean Se() {
        return blO;
    }

    public void Sf() {
        if (blO) {
            blM.a(this.blP);
            return;
        }
        String strQC = com.gameloft.android2d.iap.b.QC();
        if (strQC == null || strQC == "") {
            com.gameloft.android2d.iap.b.setResult(3);
            com.gameloft.android2d.iap.b.mB(-5);
            throw new RuntimeException("The public key is invalid.");
        }
        blM = new r((Activity) com.gameloft.android2d.iap.utils.v.getContext(), strQC);
        blM.enableDebugLogging(true);
        blM.a(new b(this));
    }

    @Override // com.gameloft.android2d.iap.a.a
    public void eP(String str) {
        try {
            com.gameloft.android2d.iap.b.QV();
        } catch (Exception e) {
        }
        if (com.gameloft.android2d.iap.b.bkM == 1) {
            com.gameloft.android2d.iap.b.setResult(3);
            com.gameloft.android2d.iap.b.mB(-21);
            return;
        }
        this.blN = super.Ry();
        com.gameloft.android2d.iap.utils.y.boZ = true;
        com.gameloft.android2d.iap.b.setResult(1);
        com.gameloft.android2d.iap.b.bkg = false;
        Sf();
    }

    public static boolean a(int i, int i2, Intent intent) {
        return blM != null && blM.a(i, i2, intent);
    }

    public static void onDestroy() {
        if (blM != null) {
            blM.Sm();
        }
        blM = null;
    }

    void a(af afVar) {
        boolean z = false;
        if (TextUtils.isEmpty(afVar.Sw()) && TextUtils.isEmpty(afVar.St()) && !TextUtils.isEmpty(afVar.getToken())) {
            z = true;
        }
        if (!z && !com.gameloft.android2d.iap.utils.aa.an(afVar.Sw(), com.gameloft.android2d.iap.utils.v.mJ(com.b.d.cgh)).equals(afVar.Su() + com.gameloft.android2d.iap.utils.v.mJ(com.b.d.cgh))) {
            com.gameloft.android2d.iap.b.setResult(3);
            com.gameloft.android2d.iap.b.mB(5);
            return;
        }
        com.gameloft.android2d.iap.b.eM(afVar.Sx());
        o(afVar.Su(), true);
        com.gameloft.android2d.iap.utils.u.fX(afVar.Su());
        com.gameloft.android2d.iap.utils.u.fT(afVar.St());
        com.gameloft.android2d.iap.utils.u.fR(afVar.getToken());
        com.gameloft.android2d.iap.utils.u.fY(afVar.Sw());
        new Thread(new f(this)).start();
    }

    @Override // com.gameloft.android2d.iap.a.a
    public boolean RW() {
        return false;
    }

    @Override // com.gameloft.android2d.iap.a.a
    public boolean RV() {
        String strTx = com.gameloft.android2d.iap.utils.u.Tx();
        String strTw = com.gameloft.android2d.iap.utils.u.Tw();
        return ((strTx == null || strTx.equals("")) && (strTw == null || strTw.equals(""))) ? false : true;
    }

    public void Sg() {
        if (com.gameloft.android2d.iap.b.bkg) {
            Sf();
        }
    }

    public boolean fp(String str) {
        com.gameloft.android2d.iap.utils.r rVarGn;
        if (!com.gameloft.android2d.iap.b.bkg || (rVarGn = com.gameloft.android2d.iap.b.Qr().gn(str)) == null) {
            return false;
        }
        com.gameloft.android2d.iap.a.a aVarSM = rVarGn.SM();
        com.gameloft.android2d.iap.utils.u.af(bjj[6], rVarGn.getType());
        com.gameloft.android2d.iap.utils.u.fW(com.gameloft.android2d.iap.b.P(com.gameloft.android2d.iap.utils.v.fZ(aVarSM.RE()), rVarGn.getType()));
        com.gameloft.android2d.iap.utils.u.fQ(String.valueOf(rVarGn.SO()));
        com.gameloft.android2d.iap.utils.u.fK(aVarSM.getPrice());
        com.gameloft.android2d.iap.utils.u.fL(aVarSM.getProfileId());
        com.gameloft.android2d.iap.utils.u.fM(aVarSM.getCurrency());
        com.gameloft.android2d.iap.utils.u.b(aVarSM);
        com.gameloft.android2d.iap.utils.u.D(rVarGn.ST(), 50);
        return true;
    }

    public void a(String str, String str2, String[] strArr) {
        String strMG = com.gameloft.android2d.iap.utils.u.mG(35);
        if (strMG != null && !strMG.equals("")) {
            try {
                JSONArray jSONArray = new JSONArray(strMG);
                for (int i = 0; i < jSONArray.length(); i++) {
                    if (jSONArray.getJSONObject(i).getString("uid").equals(str)) {
                        return;
                    }
                }
                jSONArray.put(b(str, str2, strArr));
                com.gameloft.android2d.iap.utils.u.D(jSONArray.toString(), 35);
                return;
            } catch (JSONException e) {
                return;
            }
        }
        JSONArray jSONArray2 = new JSONArray();
        jSONArray2.put(b(str, str2, strArr));
        com.gameloft.android2d.iap.utils.u.D(jSONArray2.toString(), 35);
    }

    public boolean o(String str, boolean z) {
        String strMG = com.gameloft.android2d.iap.utils.u.mG(35);
        if (strMG == null || strMG.equals("")) {
            return false;
        }
        try {
            JSONArray jSONArray = new JSONArray(strMG);
            for (int i = 0; i < jSONArray.length(); i++) {
                JSONObject jSONObject = jSONArray.getJSONObject(i);
                if (jSONObject.getString("uid").equals(str)) {
                    if (z) {
                        com.gameloft.android2d.iap.utils.u.fQ(jSONObject.getString("amount"));
                        com.gameloft.android2d.iap.utils.u.o(jSONObject.getString("offline-items").split(";"));
                    }
                    ArrayList arrayList = new ArrayList();
                    for (int i2 = 0; i2 < jSONArray.length(); i2++) {
                        arrayList.add(jSONArray.getJSONObject(i2).toString());
                    }
                    arrayList.remove(i);
                    com.gameloft.android2d.iap.utils.u.D(new JSONArray((Collection) arrayList).toString(), 35);
                    return true;
                }
            }
            return false;
        } catch (JSONException e) {
            return false;
        }
    }

    public JSONObject b(String str, String str2, String[] strArr) {
        HashMap map = new HashMap();
        String str3 = "";
        if (strArr != null) {
            for (String str4 : strArr) {
                str3 = str3 + str4 + ";";
            }
        }
        map.put("uid", str);
        map.put("amount", str2);
        map.put("offline-items", str3);
        return new JSONObject(map);
    }

    @Override // com.gameloft.android2d.iap.a.a
    public void Rc() {
        String strTx = com.gameloft.android2d.iap.utils.u.Tx();
        if (TextUtils.isEmpty(strTx)) {
            strTx = com.gameloft.android2d.iap.utils.u.Tw();
        }
        if (!TextUtils.isEmpty(strTx)) {
            com.gameloft.android2d.iap.utils.n nVarSJ = com.gameloft.android2d.iap.utils.n.SJ();
            StringBuffer stringBuffer = new StringBuffer();
            stringBuffer.append(com.gameloft.android2d.iap.utils.v.mJ(com.b.d.chl));
            stringBuffer.append("|");
            stringBuffer.append(nVarSJ.encodeString(strTx));
            stringBuffer.append("|");
            stringBuffer.append(com.gameloft.android2d.iap.utils.v.mJ(com.b.d.cgD));
            com.gameloft.android2d.iap.utils.n.SJ().a(com.gameloft.android2d.iap.utils.v.mJ(com.b.d.cgj), stringBuffer.toString(), new h(this));
        }
    }

    @Override // com.gameloft.android2d.iap.a.a
    public void RU() {
        boolean z = true;
        com.gameloft.android2d.iap.b.setResult(1);
        com.gameloft.android2d.iap.utils.n nVarSJ = com.gameloft.android2d.iap.utils.n.SJ();
        StringBuffer stringBuffer = new StringBuffer();
        stringBuffer.append(com.gameloft.android2d.iap.utils.v.mJ(com.b.d.chf));
        stringBuffer.append("|");
        stringBuffer.append(com.gameloft.android2d.iap.utils.u.TB());
        stringBuffer.append("|");
        stringBuffer.append(nVarSJ.encodeString(com.gameloft.android2d.iap.utils.u.Tn()));
        stringBuffer.append("|");
        stringBuffer.append(nVarSJ.encodeString(com.gameloft.android2d.iap.utils.u.Tz()));
        stringBuffer.append("|");
        stringBuffer.append(com.gameloft.android2d.iap.b.Qs());
        stringBuffer.append("|");
        String strTx = com.gameloft.android2d.iap.utils.u.Tx();
        String strTI = com.gameloft.android2d.iap.utils.u.TI();
        String strTw = com.gameloft.android2d.iap.utils.u.Tw();
        if (TextUtils.isEmpty(strTx)) {
            stringBuffer.append("");
            stringBuffer.append("|");
        } else {
            stringBuffer.append(nVarSJ.encodeString(strTx));
            stringBuffer.append("|");
        }
        stringBuffer.append(nVarSJ.encodeString(strTw));
        stringBuffer.append("&");
        stringBuffer.append(com.gameloft.android2d.iap.utils.v.mJ(com.b.d.che));
        String property = System.getProperty("injectedigp");
        if (!TextUtils.isEmpty(property)) {
            stringBuffer.append("&" + com.gameloft.android2d.iap.utils.v.mJ(com.b.d.chk) + "=" + property);
        }
        String property2 = System.getProperty("injectedserialkey");
        if (!TextUtils.isEmpty(property2)) {
            stringBuffer.append("&" + com.gameloft.android2d.iap.utils.v.mJ(com.b.d.chg) + "=" + property2);
        }
        if (!TextUtils.isEmpty(strTx) || !TextUtils.isEmpty(strTI) || TextUtils.isEmpty(strTw)) {
            z = false;
        } else {
            stringBuffer.append("&" + com.gameloft.android2d.iap.utils.v.mJ(com.b.d.chK) + "=1");
        }
        nVarSJ.a(com.gameloft.android2d.iap.utils.u.TA(), stringBuffer.toString(), Sh(), new j(this, z));
    }

    public String Sh() {
        JSONObject jSONObject = new JSONObject();
        try {
            String strGh = com.gameloft.android2d.iap.utils.v.gh(com.gameloft.android2d.iap.utils.v.mJ(com.b.d.cgZ));
            if (strGh == null) {
                strGh = AppEventsConstants.EVENT_PARAM_VALUE_NO;
            }
            jSONObject.put(com.gameloft.android2d.iap.utils.v.mJ(com.b.d.chV), strGh);
            jSONObject.put(com.gameloft.android2d.iap.utils.v.mJ(com.b.d.chX), com.gameloft.android2d.iap.b.Qt());
            String strRu = com.gameloft.android2d.iap.af.Ru();
            if (TextUtils.isEmpty(strRu) || strRu.equalsIgnoreCase("null:null")) {
                strRu = "";
            }
            jSONObject.put(com.gameloft.android2d.iap.utils.v.mJ(com.b.d.chQ), strRu);
            String strRv = com.gameloft.android2d.iap.af.Rv();
            if (TextUtils.isEmpty(strRv) || strRv.equalsIgnoreCase("null:null")) {
                strRv = "";
            }
            jSONObject.put(com.gameloft.android2d.iap.utils.v.mJ(com.b.d.chW), strRv);
            String strRk = com.gameloft.android2d.iap.af.rk();
            if (TextUtils.isEmpty(strRk)) {
                strRk = AppEventsConstants.EVENT_PARAM_VALUE_NO;
            }
            jSONObject.put(com.gameloft.android2d.iap.utils.v.mJ(com.b.d.chT), strRk);
            String strRj = com.gameloft.android2d.iap.af.rj();
            if (TextUtils.isEmpty(strRj)) {
                strRj = "";
            }
            jSONObject.put(com.gameloft.android2d.iap.utils.v.mJ(com.b.d.chU), strRj);
            if (com.gameloft.android2d.iap.b.QL() != 2) {
                jSONObject.put(com.gameloft.android2d.iap.utils.v.mJ(com.b.d.chZ), com.gameloft.android2d.iap.utils.d.getDeviceId());
            }
            if (com.gameloft.android2d.iap.b.QL() != 0) {
                jSONObject.put(com.gameloft.android2d.iap.utils.v.mJ(com.b.d.chY), com.gameloft.android2d.iap.utils.d.fA("HDIDFV"));
            }
            jSONObject.put(com.gameloft.android2d.iap.utils.v.mJ(com.b.d.cin), com.gameloft.android2d.iap.utils.d.getUserAgent());
            jSONObject.put(com.gameloft.android2d.iap.utils.v.mJ(com.b.d.chN), Build.MODEL);
            jSONObject.put(com.gameloft.android2d.iap.utils.v.mJ(com.b.d.chL), com.gameloft.android2d.iap.utils.v.mJ(com.b.d.cgQ));
            jSONObject.put(com.gameloft.android2d.iap.utils.v.mJ(com.b.d.cil), com.gameloft.android2d.iap.utils.d.Pb());
            return jSONObject.toString();
        } catch (JSONException e) {
            return null;
        }
    }

    public boolean fq(String str) {
        com.gameloft.android2d.iap.utils.r rVarGn = com.gameloft.android2d.iap.b.Qr().gn(str);
        if (rVarGn == null) {
            return false;
        }
        return rVarGn.SU().equals(AppEventsConstants.EVENT_PARAM_VALUE_YES) || rVarGn.SU().equals("2");
    }

    public void a(af afVar, boolean z) {
        a(afVar);
    }

    @Override // com.gameloft.android2d.iap.a.a
    public void RZ() {
        try {
            com.gameloft.android2d.iap.b.QV();
        } catch (Exception e) {
        }
        if (com.gameloft.android2d.iap.b.bkM == 1) {
            com.gameloft.android2d.iap.b.setResult(3);
            return;
        }
        if (blO) {
            blM.a(this.blT);
            return;
        }
        String strQC = com.gameloft.android2d.iap.b.QC();
        if (strQC == null || strQC == "") {
            com.gameloft.android2d.iap.b.setResult(3);
            throw new RuntimeException("The public key is invalid.");
        }
        blM = new r((Activity) com.gameloft.android2d.iap.utils.v.getContext(), strQC);
        blM.enableDebugLogging(true);
        blM.a(new m(this));
    }

    @Override // com.gameloft.android2d.iap.a.a
    public void Sa() {
        com.gameloft.android2d.iap.b.setResult(1);
        com.gameloft.android2d.iap.b.bkg = true;
        Sg();
    }

    @Override // com.gameloft.android2d.iap.a.a
    public void Sb() {
        if (com.gameloft.android2d.iap.b.Qq() == 10) {
            com.gameloft.android2d.iap.b.bkg = true;
            com.gameloft.android2d.iap.b.bkh = true;
            com.gameloft.android2d.iap.b.setResult(1);
            if (blO) {
                blM.a(this.blP);
                return;
            }
            String strQC = com.gameloft.android2d.iap.b.QC();
            if (strQC == null || strQC == "") {
                com.gameloft.android2d.iap.b.setResult(10);
                com.gameloft.android2d.iap.b.mB(0);
                throw new RuntimeException("The public key is invalid.");
            }
            if (blM != null) {
                blM.Sm();
            }
            blM = null;
            blM = new r((Activity) com.gameloft.android2d.iap.utils.v.getContext(), strQC);
            blM.enableDebugLogging(true);
            blM.a(new c(this));
        }
    }

    public af a(ae aeVar, String str, int i) {
        List<String> listSr = aeVar.Sr();
        String str2 = "";
        if (i == 0) {
            return aeVar.fw(str);
        }
        if (i == 1) {
            String strMG = com.gameloft.android2d.iap.utils.u.mG(53);
            int i2 = 0;
            while (i2 < listSr.size()) {
                String str3 = listSr.get(i2);
                if (!TextUtils.isEmpty(aeVar.fw(str3).St()) || !TextUtils.isEmpty(aeVar.fw(str3).Sw()) || TextUtils.isEmpty(aeVar.fw(str3).getToken()) || (!str2.equals("") && aeVar.fw(str3).Sv() <= aeVar.fw(str2).Sv())) {
                    str3 = str2;
                }
                i2++;
                str2 = str3;
            }
            if (strMG.indexOf(str2) == -1 && fp(str2)) {
                return aeVar.fw(str2);
            }
        }
        return null;
    }

    @Override // com.gameloft.android2d.iap.a.a
    public void i(String str, String str2, String str3, String str4, String str5, String str6) {
        JSONObject jSONObject;
        StringBuffer stringBuffer = new StringBuffer();
        stringBuffer.append("|");
        stringBuffer.append(str);
        stringBuffer.append("|");
        stringBuffer.append(com.gameloft.android2d.iap.utils.a.fz(str2));
        stringBuffer.append("|");
        stringBuffer.append(com.gameloft.android2d.iap.utils.a.fz(str3));
        stringBuffer.append("|");
        stringBuffer.append(com.gameloft.android2d.iap.b.Qs());
        stringBuffer.append("|");
        if (TextUtils.isEmpty(str5)) {
            stringBuffer.append("");
            stringBuffer.append("|");
        } else {
            stringBuffer.append(com.gameloft.android2d.iap.utils.a.fz(str5));
            stringBuffer.append("|");
        }
        stringBuffer.append(com.gameloft.android2d.iap.utils.a.fz(str6));
        String property = System.getProperty("injectedigp");
        if (!TextUtils.isEmpty(property)) {
            stringBuffer.append("&");
            stringBuffer.append(com.gameloft.android2d.iap.utils.v.mJ(com.b.d.chk) + "=" + property);
        }
        String property2 = System.getProperty("injectedserialkey");
        if (!TextUtils.isEmpty(property2)) {
            stringBuffer.append("&");
            stringBuffer.append(com.gameloft.android2d.iap.utils.v.mJ(com.b.d.chg) + "=" + property2);
        }
        stringBuffer.append("&");
        stringBuffer.append(com.gameloft.android2d.iap.utils.v.mJ(com.b.d.chv) + "=1");
        String string = stringBuffer.toString();
        com.gameloft.android2d.iap.utils.y yVar = new com.gameloft.android2d.iap.utils.y(new com.gameloft.android2d.iap.utils.d(com.gameloft.android2d.iap.utils.u.TC(), com.gameloft.android2d.iap.utils.u.TD()), false);
        yVar.ak(str4, string);
        while (!yVar.Um()) {
            try {
                Thread.sleep(50L);
            } catch (Exception e) {
            }
        }
        if (yVar.Uj() == 0) {
            String strQs = yVar.Qs();
            String transactionId = yVar.getTransactionId();
            if (com.gameloft.android2d.iap.b.n(strQs, true)) {
                com.gameloft.android2d.iap.b.bkw.add(str);
                try {
                    String strMG = com.gameloft.android2d.iap.utils.u.mG(60);
                    if (com.gameloft.android2d.iap.utils.v.gk(strMG)) {
                        jSONObject = new JSONObject(strMG);
                    } else {
                        jSONObject = new JSONObject();
                    }
                    jSONObject.put(str, transactionId);
                    com.gameloft.android2d.iap.utils.u.D(jSONObject.toString(), 60);
                } catch (JSONException e2) {
                }
            }
        }
    }
}
