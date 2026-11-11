package com.gameloft.android.ANMP.GloftGF2F;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import com.facebook.share.internal.MessengerShareContentUtility;
import com.facebook.share.internal.ShareConstants;
import java.util.Vector;
import org.json.JSONObject;

/* JADX INFO: compiled from: GCMIntentService.java */
/* JADX INFO: loaded from: classes.dex */
public class g extends com.gameloft.android.ANMP.GloftGF2F.PushNotification.e {
    static Vector<String> AW;

    @Override // com.gameloft.android.ANMP.GloftGF2F.PushNotification.e
    protected void f(Context context, String str) {
        if (str != null && str.length() > 0) {
            com.gameloft.android.ANMP.GloftGF2F.PushNotification.q.hk();
        }
    }

    @Override // com.gameloft.android.ANMP.GloftGF2F.PushNotification.e
    protected void g(Context context, String str) {
        if (com.gameloft.android.ANMP.GloftGF2F.PushNotification.h.F(context)) {
        }
    }

    @Override // com.gameloft.android.ANMP.GloftGF2F.PushNotification.e
    protected void a(Context context, Intent intent) {
        b(context, intent);
    }

    @Override // com.gameloft.android.ANMP.GloftGF2F.PushNotification.e
    protected void r(Context context, int i) {
    }

    @Override // com.gameloft.android.ANMP.GloftGF2F.PushNotification.e
    public void h(Context context, String str) {
    }

    @Override // com.gameloft.android.ANMP.GloftGF2F.PushNotification.e
    protected boolean i(Context context, String str) {
        return super.i(context, str);
    }

    public static void b(Context context, Intent intent) {
        String string;
        String string2;
        String str;
        Bundle extras = intent.getExtras();
        if (extras != null && (string = extras.getString(ShareConstants.WEB_DIALOG_PARAM_ID)) != null) {
            if (AW == null) {
                AW = new Vector<>();
            }
            if (!AW.contains(string)) {
                if (AW.size() > 5) {
                    AW.clear();
                }
                AW.add(string);
                boolean zR = com.gameloft.android.ANMP.GloftGF2F.PushNotification.k.R(context);
                boolean zS = com.gameloft.android.ANMP.GloftGF2F.PushNotification.k.S(context);
                if (zR && zS) {
                    JSONObject jSONObject = new JSONObject();
                    for (String str2 : extras.keySet()) {
                        try {
                            jSONObject.put(str2, extras.get(str2));
                        } catch (Exception e) {
                        }
                    }
                    if (extras != null && !extras.isEmpty()) {
                        String string3 = extras.getString("subject");
                        String string4 = extras.getString("reply_to");
                        String string5 = extras.getString("to");
                        String string6 = extras.getString("title");
                        String string7 = extras.getString("type");
                        String string8 = extras.getString("body");
                        String string9 = extras.getString("url");
                        String string10 = extras.getString("receive");
                        String string11 = extras.getString("body_loc");
                        String string12 = extras.getString("title_loc");
                        String string13 = extras.getString("category");
                        SharedPreferences sharedPreferencesQ = com.gameloft.android.ANMP.GloftGF2F.PushNotification.k.Q(context);
                        sharedPreferencesQ.getString("UserID", "");
                        String str3 = "anonymous:" + sharedPreferencesQ.getString("RandomUserID", "");
                        if (string4 == null || string4.equals("None") || com.gameloft.android.ANMP.GloftGF2F.PushNotification.q.s(context, string4) || string4.equals(str3)) {
                            if (string5 == null || string5.equals("None") || com.gameloft.android.ANMP.GloftGF2F.PushNotification.q.s(context, string5) || string5.equals(str3)) {
                                if (string13 == null || !com.gameloft.android.ANMP.GloftGF2F.PushNotification.q.t(context, string13)) {
                                    if (string3 == null || string3.length() == 0) {
                                        string3 = (string6 == null || string6.length() <= 0) ? context.getString(R.string.MIDlet_Name) : string6;
                                    }
                                    if (string7 == null || string7.length() == 0) {
                                        string7 = "info";
                                    }
                                    if (string7 != null && string8 != null && !com.gameloft.android.ANMP.GloftGF2F.PushNotification.q.ah(string7)) {
                                        if (string7.equals("play") && string4 != null) {
                                            extras.putString("friend_id", string4);
                                        }
                                        if (extras.containsKey("customIcon")) {
                                            com.gameloft.android.ANMP.GloftGF2F.PushNotification.q.FI = true;
                                            com.gameloft.android.ANMP.GloftGF2F.PushNotification.q.FJ = extras.getString("customIcon");
                                        } else {
                                            com.gameloft.android.ANMP.GloftGF2F.PushNotification.q.FI = false;
                                            com.gameloft.android.ANMP.GloftGF2F.PushNotification.q.FJ = null;
                                        }
                                        if (extras.containsKey(MessengerShareContentUtility.MEDIA_IMAGE)) {
                                            com.gameloft.android.ANMP.GloftGF2F.PushNotification.q.FM = true;
                                            com.gameloft.android.ANMP.GloftGF2F.PushNotification.q.FN = extras.getString(MessengerShareContentUtility.MEDIA_IMAGE);
                                            if (!com.gameloft.android.ANMP.GloftGF2F.PushNotification.aa.af(context)) {
                                                com.gameloft.android.ANMP.GloftGF2F.PushNotification.j.M(context);
                                                com.gameloft.android.ANMP.GloftGF2F.PushNotification.j.O(context);
                                                com.gameloft.android.ANMP.GloftGF2F.PushNotification.j.P(context);
                                                com.gameloft.android.ANMP.GloftGF2F.PushNotification.j.N(context);
                                                com.gameloft.android.ANMP.GloftGF2F.PushNotification.aa.ag(context);
                                            }
                                        } else {
                                            com.gameloft.android.ANMP.GloftGF2F.PushNotification.q.FM = false;
                                            com.gameloft.android.ANMP.GloftGF2F.PushNotification.q.FN = null;
                                        }
                                        if (extras.containsKey("sound")) {
                                            com.gameloft.android.ANMP.GloftGF2F.PushNotification.q.FK = true;
                                            com.gameloft.android.ANMP.GloftGF2F.PushNotification.q.FL = extras.getString("sound");
                                        } else {
                                            com.gameloft.android.ANMP.GloftGF2F.PushNotification.q.FK = false;
                                            com.gameloft.android.ANMP.GloftGF2F.PushNotification.q.FL = null;
                                        }
                                        if (string10 == null || !string10.equals("user") || string11 == null) {
                                            string2 = string3;
                                        } else {
                                            String upperCase = com.gameloft.android.ANMP.GloftGF2F.PushNotification.k.W(context).toUpperCase();
                                            String[] strArrSplit = string11.split(":");
                                            String string14 = sharedPreferencesQ.getString("PN_TEMPLATE_" + strArrSplit[0] + "_" + upperCase, "");
                                            if (string14.equals("")) {
                                                string14 = sharedPreferencesQ.getString("PN_TEMPLATE_" + strArrSplit[0] + "_EN", "");
                                            }
                                            if (string14.equals("")) {
                                                str = string8;
                                            } else {
                                                String strReplaceAll = string14;
                                                for (int i = 1; i < strArrSplit.length; i++) {
                                                    strReplaceAll = strReplaceAll.replaceAll("%s" + (i - 1), strArrSplit[i]);
                                                }
                                                str = strReplaceAll;
                                            }
                                            String string15 = "";
                                            if (string12 != null) {
                                                string15 = sharedPreferencesQ.getString("PN_TEMPLATE_" + string12 + "_" + upperCase, "");
                                            }
                                            string2 = string15.equals("") ? sharedPreferencesQ.getString("PN_TEMPLATE_" + string12 + "_EN", "") : string15;
                                            if (string2.equals("")) {
                                                string8 = str;
                                                string2 = string3;
                                            } else {
                                                string8 = str;
                                            }
                                        }
                                        Intent intentA = com.gameloft.android.ANMP.GloftGF2F.PushNotification.q.a(context, string8, string7, string9, extras);
                                        if (intentA != null) {
                                            com.gameloft.android.ANMP.GloftGF2F.PushNotification.q.a(context, string8, string2, string7, intentA, extras);
                                        }
                                        if (jSONObject != null) {
                                            try {
                                                Intent intent2 = new Intent("com.gameloft.android.ANMP.GloftGF2F_pushbroadcast");
                                                if (intent2 != null) {
                                                    intent2.putExtra("get_broadcast_push", jSONObject.toString());
                                                    context.sendBroadcast(intent2);
                                                }
                                            } catch (Exception e2) {
                                            }
                                        }
                                    }
                                }
                            }
                        }
                    }
                }
            }
        }
    }
}
