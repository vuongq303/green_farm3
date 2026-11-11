package com.gameloft.android.ANMP.GloftGF2F.PushNotification;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import com.facebook.appevents.AppEventsConstants;
import com.facebook.share.internal.MessengerShareContentUtility;
import java.util.Calendar;
import java.util.Iterator;
import org.json.JSONObject;

/* JADX INFO: loaded from: classes.dex */
public class LocalPushReceiver extends BroadcastReceiver {
    @Override // android.content.BroadcastReceiver
    public void onReceive(Context context, Intent intent) {
        String str;
        Intent intent2;
        long j;
        int i;
        try {
            i.K(context);
            if ("android.intent.action.BOOT_COMPLETED".equals(intent.getAction()) || "android.intent.action.ACTION_BOOT_COMPLETED".equals(intent.getAction())) {
                i.he();
                return;
            }
            boolean zR = k.R(context);
            Bundle extras = intent.getExtras();
            JSONObject jSONObject = new JSONObject();
            if (extras != null) {
                String string = null;
                String string2 = null;
                String string3 = null;
                String string4 = null;
                String string5 = null;
                String string6 = null;
                Bundle bundle = extras.getBundle(context.getPackageName() + ".alarm_content");
                if (bundle != null) {
                    Iterator<String> it = bundle.keySet().iterator();
                    String string7 = null;
                    while (true) {
                        str = string6;
                        if (!it.hasNext()) {
                            break;
                        }
                        String next = it.next();
                        if (next.equals("body")) {
                            string = bundle.getString("body");
                            string6 = str;
                        } else if (next.equals("subject")) {
                            string2 = bundle.getString("subject");
                            string6 = str;
                        } else if (next.equals("type")) {
                            string3 = bundle.getString("type");
                            string6 = str;
                        } else if (next.equals("url")) {
                            string5 = bundle.getString("url");
                            string6 = str;
                        } else if (next.equals("lID")) {
                            string4 = bundle.getString("lID");
                            string6 = str;
                        } else if (next.equals("infopush")) {
                            string6 = bundle.getString("infopush");
                        } else if (next.equals("limit_time")) {
                            string7 = bundle.getString("limit_time");
                            string6 = str;
                        } else {
                            string6 = str;
                        }
                        try {
                            if (bundle.getString(next) != null) {
                                jSONObject.put(next, bundle.getString(next));
                            }
                        } catch (Exception e) {
                        }
                    }
                    if (zR) {
                        if (string7 != null && string7.equals(AppEventsConstants.EVENT_PARAM_VALUE_YES)) {
                            Calendar calendar = Calendar.getInstance();
                            int i2 = calendar.get(11);
                            int i3 = calendar.get(12);
                            int i4 = calendar.get(13);
                            if (i2 < 9 || i2 > 13) {
                                if (i2 > 12) {
                                    j = (32400 - (i4 + ((((i2 - 12) * 60) * 60) + (i3 * 60)))) + 43200;
                                } else {
                                    j = 32400 - (i4 + (((i2 * 60) * 60) + (i3 * 60)));
                                }
                                int i5 = -1;
                                String string8 = bundle.getString("pn_group_ID");
                                if (!q.ai(string8)) {
                                    try {
                                        i5 = Integer.parseInt(string8);
                                    } catch (Exception e2) {
                                    }
                                }
                                if (i5 == -1) {
                                    i = i5;
                                } else if (string3 != null && string3.equals("launch")) {
                                    i = i5 - 101;
                                } else {
                                    i = i5 - 201;
                                }
                                if (string4 != null && string4.length() > 0) {
                                    i.ac(string4);
                                }
                                q.a(context, bundle, "" + j, AppEventsConstants.EVENT_PARAM_VALUE_NO, true, i);
                                return;
                            }
                        }
                        String strAe = i.ae(AppEventsConstants.EVENT_PARAM_VALUE_NO);
                        if (str != null) {
                            SharedPreferences sharedPreferencesQ = k.Q(context);
                            SharedPreferences.Editor editorEdit = sharedPreferencesQ.edit();
                            int i6 = sharedPreferencesQ.getInt("info_" + str, 0);
                            int i7 = sharedPreferencesQ.getInt("count_" + str, 0);
                            if (i6 != 0) {
                                if (i7 == 0) {
                                    editorEdit.putString("time_" + str, strAe);
                                    editorEdit.commit();
                                }
                                String string9 = sharedPreferencesQ.getString("time_" + str, strAe);
                                if (!i.ad(string9)) {
                                    editorEdit.putString("time_" + str, strAe);
                                    editorEdit.commit();
                                    string9 = strAe;
                                }
                                if (i.i(string9, strAe)) {
                                    editorEdit.putInt("count_" + str, 0);
                                    editorEdit.putString("time_" + str, strAe);
                                    editorEdit.commit();
                                    i7 = 0;
                                }
                                if (i7 < i6) {
                                    editorEdit.putInt("count_" + str, i7 + 1);
                                    editorEdit.commit();
                                } else {
                                    return;
                                }
                            }
                        }
                        int iV = k.V(context);
                        if (iV == 0) {
                            k.o(context, strAe);
                        }
                        String strU = k.U(context);
                        if (!i.ad(strU)) {
                            k.o(context, strAe);
                            strU = strAe;
                        }
                        if (i.i(strU, strAe)) {
                            k.t(context, 0);
                            k.o(context, strAe);
                            iV = 0;
                        }
                        if (iV < 2) {
                            k.t(context, iV + 1);
                            if (string3 != null && string != null && !q.ah(string3)) {
                                if (bundle.containsKey("customIcon")) {
                                    q.FI = true;
                                    q.FJ = bundle.getString("customIcon");
                                } else {
                                    q.FI = false;
                                    q.FJ = null;
                                }
                                if (bundle.containsKey(MessengerShareContentUtility.MEDIA_IMAGE)) {
                                    q.FM = true;
                                    q.FN = bundle.getString(MessengerShareContentUtility.MEDIA_IMAGE);
                                    aa.af(context);
                                } else {
                                    q.FM = false;
                                    q.FN = null;
                                }
                                if (bundle.containsKey("sound")) {
                                    q.FK = true;
                                    q.FL = bundle.getString("sound");
                                } else {
                                    q.FK = false;
                                    q.FL = null;
                                }
                                Intent intentA = q.a(context, string, string3, string5, bundle);
                                if (intentA != null) {
                                    q.a(context, string, string2, string3, intentA, bundle);
                                }
                                if (string4 != null) {
                                    try {
                                        if (string4.length() > 0) {
                                            i.ac(string4);
                                        }
                                    } catch (Exception e3) {
                                        return;
                                    }
                                }
                                if (jSONObject != null && (intent2 = new Intent("com.gameloft.android.ANMP.GloftGF2F_pushbroadcast")) != null) {
                                    intent2.putExtra("get_broadcast_push", jSONObject.toString());
                                    context.sendBroadcast(intent2);
                                }
                            }
                        }
                    }
                }
            }
        } catch (Exception e4) {
        }
    }
}
