package com.gameloft.android.wrapper;

import com.facebook.appevents.AppEventsConstants;
import java.util.Hashtable;
import org.json.JSONException;
import org.json.JSONObject;

/* JADX INFO: compiled from: NotificationsDetection.java */
/* JADX INFO: loaded from: classes.dex */
public class ag {
    private static ak bgx;
    private static boolean bgy;
    private static String bgt = au.Pq() + "/OLU_NOTIFICATION_STATUS.txt";
    private static String bgu = AppEventsConstants.EVENT_PARAM_VALUE_YES;
    private static String bgv = AppEventsConstants.EVENT_PARAM_VALUE_NO;
    private static boolean bgw = false;
    private static int bgz = 254523;
    private static int bgA = 255057;

    public static void a(Hashtable hashtable) {
        boolean z = ((Integer) hashtable.get("pn_launch_game")).intValue() == 1;
        boolean z2 = ((Integer) hashtable.get("pn_online")).intValue() == 1;
        boolean z3 = ((Integer) hashtable.get("discount_price")).intValue() == 1;
        String str = (String) hashtable.get("pn_title");
        ak akVar = new ak();
        if (str == null) {
            str = "N/A";
        }
        akVar.bgM = str;
        if (z) {
            if (z2) {
                akVar.bgL = am.LAUNCH_FROM_PN;
            } else if (z3) {
                akVar.bgL = am.LAUNCH_FROM_DISCOUNT_PRICE;
            } else {
                akVar.bgL = am.LAUNCH_FROM_LN;
            }
        } else if (z2) {
            akVar.bgL = am.RESUME_FROM_PN;
        } else if (z3) {
            akVar.bgL = am.RESUME_FROM_DISCOUNT_PRICE;
        } else {
            akVar.bgL = am.RESUME_FROM_LN;
        }
        bgx = akVar;
        bgw = true;
        String str2 = "catch PN type:" + akVar.bgL;
    }

    public static void OnResume() {
        if (bgw) {
            bgw = false;
            a(bgx);
        }
        OF();
    }

    public static void OF() {
        ai aiVar = new ai(null);
        aj ajVarB = b(aiVar);
        if (ajVarB == aj.NOTIFICATION_STATUS_CHANGED) {
            a(aiVar);
            if (c(aiVar) != aj.SUCCESS) {
                return;
            } else {
                return;
            }
        }
        String str = "Notification status unchanged: " + ajVarB;
    }

    private static void a(ak akVar) {
        String str = "VerifyLaunchResumeOptions " + akVar.bgL.ordinal() + " " + akVar.bgM;
        if (bgy || com.gameloft.android2d.d.b.Qm() == null) {
            if (com.gameloft.android2d.d.b.Qm() == null) {
            }
            bgx = akVar;
            bgw = true;
        }
        String strEg = eg(akVar.bgM);
        switch (akVar.bgL) {
            case LAUNCH_FROM_LN:
            case RESUME_FROM_LN:
                a(al.LOCAL_NOTIFICATIONS, strEg);
                break;
            case LAUNCH_FROM_PN:
            case RESUME_FROM_PN:
                a(al.ONLINE_NOTIFICATION, strEg);
                break;
            case LAUNCH_FROM_URL:
            case RESUME_FROM_URL:
                a(al.URL_INTERACTION, strEg);
                break;
            case LAUNCH_FROM_DISCOUNT_PRICE:
            case RESUME_FROM_DISCOUNT_PRICE:
                a(al.LOCAL_NOTIFICATIONS, strEg + " - discount");
                break;
            default:
                break;
        }
    }

    static void a(ai aiVar) {
        if (com.gameloft.android2d.d.b.Qm() != null) {
            String str = "SendNotificationStatusEvent: status: " + aiVar;
            JSONObject jSONObject = new JSONObject();
            int i = aiVar.bgC ? 1 : 0;
            try {
                jSONObject.put("notification_status", i);
            } catch (JSONException e) {
            }
            com.gameloft.android2d.d.b.Qm().b(bgA, new Object[]{new Integer(i)});
            String str2 = "[AddTrackingEvent] Add event of type " + bgA + " with value: " + jSONObject.toString();
        }
    }

    static void a(al alVar, String str) {
        String str2 = "SendLaunchResumeOptionsEvent with type: " + alVar;
        JSONObject jSONObject = new JSONObject();
        try {
            jSONObject.put("launch_resume_details", str);
            jSONObject.put("launch_resume_type", alVar.id);
        } catch (JSONException e) {
        }
        if (com.gameloft.android2d.d.b.Qm() != null) {
            com.gameloft.android2d.d.b.Qm().b(bgz, new Object[]{str, new Integer(alVar.id)});
            String str3 = "[AddTrackingEvent] Add event of type " + bgz + " with value: " + jSONObject.toString();
        }
    }

    static aj b(ai aiVar) {
        aiVar.bF(false);
        boolean zQk = au.Qk();
        if (!zQk) {
        }
        aiVar.bF(zQk);
        ai aiVar2 = new ai(null);
        aj ajVarD = d(aiVar2);
        if (ajVarD == aj.SUCCESS) {
            return aiVar.bgC != aiVar2.bgC ? aj.NOTIFICATION_STATUS_CHANGED : aj.NOTIFICATION_STATUS_UNCHANGED;
        }
        if (ajVarD == aj.ERROR_FILE_NOT_PRESENT) {
            return aj.NOTIFICATION_STATUS_CHANGED;
        }
        return ajVarD;
    }

    static aj c(ai aiVar) {
        return au.Z(bgt, aiVar.bgC ? bgu : bgv) ? aj.SUCCESS : aj.ERROR_INTERACTING_WITH_FS;
    }

    static aj d(ai aiVar) {
        String str = "Entered GetCachedNotificationStatus with param: " + aiVar.bgC;
        String strEp = au.ep(bgt);
        if (strEp == null) {
            return aj.ERROR_FILE_NOT_PRESENT;
        }
        if (strEp.equalsIgnoreCase(bgu)) {
            aiVar.bF(true);
        } else if (strEp.equalsIgnoreCase(bgv)) {
            aiVar.bF(false);
        } else {
            return aj.ERROR_INTERACTING_WITH_FS;
        }
        return aj.SUCCESS;
    }

    static String eg(String str) {
        String str2 = "";
        for (int i = 0; i < str.length(); i++) {
            char cCharAt = str.charAt(i);
            if (cCharAt > 31 && cCharAt != 127) {
                str2 = str2 + cCharAt;
            }
        }
        return str2;
    }
}
