package com.quanhoang.greenfarm3.PushNotification;

import android.content.Context;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import com.facebook.internal.AnalyticsEvents;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.UnknownHostException;
import java.util.HashMap;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

/* JADX INFO: compiled from: RemoteImageManager.java */
/* JADX INFO: loaded from: classes.dex */
public class aa {
    private static Bitmap Gj = null;
    private static Boolean Gk = false;
    private static String Gl = null;
    private static String Gm = null;
    private static HashMap<String, String> Gn;

    public static Bitmap hp() {
        return Gj;
    }

    public static boolean af(Context context) {
        int identifier;
        Gj = null;
        try {
            identifier = context.getResources().getIdentifier(q.FN, "raw", context.getPackageName());
            try {
                Gj = BitmapFactory.decodeResource(context.getResources(), identifier);
            } catch (Exception e) {
                Gj = null;
            }
        } catch (Exception e2) {
            identifier = 0;
        }
        return Gj != null && identifier > 0;
    }

    public static boolean ag(Context context) {
        Gj = null;
        Gl = null;
        Gk = false;
        new ab(context).start();
        int i = 0;
        do {
            i++;
            if (Gj != null || Gk.booleanValue()) {
                break;
            }
            try {
                Thread.sleep(500L);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        } while (i <= 20);
        Gl = null;
        return Gj != null;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static String ah(Context context) {
        String strAi = ai(context);
        if (strAi == null) {
            strAi = aj(context);
        }
        if (strAi == null) {
            return null;
        }
        return "https://" + strAi + "/assets/" + q.FQ + "/" + q.FN;
    }

    private static String ai(Context context) {
        SharedPreferences sharedPreferencesQ = k.Q(context);
        sharedPreferencesQ.edit();
        if (!sharedPreferencesQ.contains("iris_service")) {
            return null;
        }
        return sharedPreferencesQ.getString("iris_service", "");
    }

    public static String aj(Context context) {
        String strHq = hq();
        if (strHq != null) {
            try {
                if (!strHq.isEmpty()) {
                    HttpURLConnection httpURLConnection = (HttpURLConnection) new URL(strHq + "/locate?service=asset").openConnection();
                    if (httpURLConnection.getResponseCode() == 200) {
                        BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(httpURLConnection.getInputStream()));
                        StringBuffer stringBuffer = new StringBuffer();
                        while (true) {
                            String line = bufferedReader.readLine();
                            if (line == null) {
                                break;
                            }
                            stringBuffer.append(line);
                            stringBuffer.append('\r');
                        }
                        bufferedReader.close();
                        if (stringBuffer != null) {
                            String string = stringBuffer.toString();
                            if (string != null) {
                                try {
                                    if (!string.isEmpty()) {
                                        SharedPreferences.Editor editorEdit = k.Q(context).edit();
                                        editorEdit.putString("iris_service", string);
                                        editorEdit.commit();
                                        return string;
                                    }
                                    return string;
                                } catch (UnknownHostException e) {
                                    return string;
                                } catch (Exception e2) {
                                    return string;
                                }
                            }
                            return string;
                        }
                    }
                }
            } catch (UnknownHostException e3) {
                return null;
            } catch (Exception e4) {
                return null;
            }
        }
        return null;
    }

    private static String hq() {
        String string = null;
        if (q.FQ != null && !q.FQ.isEmpty()) {
            if (Gn == null) {
                Gn = new HashMap<>();
            }
            string = Gn.get("pandora");
            if (string == null || string.isEmpty()) {
                try {
                    String strHr = hr();
                    if (strHr != null) {
                        HttpURLConnection httpURLConnection = (HttpURLConnection) new URL("https://eve.gameloft.com:443/config/" + q.FQ + "/datacenters/" + strHr + "/urls").openConnection();
                        if (httpURLConnection.getResponseCode() == 200) {
                            BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(httpURLConnection.getInputStream()));
                            StringBuffer stringBuffer = new StringBuffer();
                            while (true) {
                                String line = bufferedReader.readLine();
                                if (line == null) {
                                    break;
                                }
                                stringBuffer.append(line);
                                stringBuffer.append('\r');
                            }
                            bufferedReader.close();
                            try {
                                string = new JSONObject(stringBuffer.toString()).getString("pandora");
                            } catch (JSONException e) {
                                try {
                                    string = new JSONObject(new JSONArray(stringBuffer.toString()).get(0).toString()).getString("pandora");
                                } catch (JSONException e2) {
                                }
                            }
                            if (string != null && !string.isEmpty()) {
                                Gn.put("pandora", string);
                            }
                        }
                        httpURLConnection.disconnect();
                    }
                } catch (UnknownHostException e3) {
                } catch (Exception e4) {
                }
            }
        }
        return string;
    }

    private static String hr() {
        String string;
        try {
            HttpURLConnection httpURLConnection = (HttpURLConnection) new URL("https://eve.gameloft.com:443/config/" + q.FQ + "/datacenters").openConnection();
            if (httpURLConnection.getResponseCode() != 200) {
                return null;
            }
            BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(httpURLConnection.getInputStream()));
            StringBuffer stringBuffer = new StringBuffer();
            while (true) {
                String line = bufferedReader.readLine();
                if (line != null) {
                    stringBuffer.append(line);
                    stringBuffer.append('\r');
                } else {
                    bufferedReader.close();
                    try {
                        return new JSONObject(stringBuffer.toString()).getString("name");
                    } catch (JSONException e) {
                        try {
                            JSONArray jSONArray = new JSONArray(stringBuffer.toString());
                            int i = 0;
                            while (true) {
                                int i2 = i;
                                if (i2 >= jSONArray.length()) {
                                    string = null;
                                    break;
                                }
                                JSONObject jSONObject = jSONArray.getJSONObject(i2);
                                string = jSONObject.getString("name");
                                String string2 = jSONObject.getString(AnalyticsEvents.PARAMETER_SHARE_DIALOG_CONTENT_STATUS);
                                Boolean boolValueOf = Boolean.valueOf(jSONObject.getBoolean("preferred"));
                                if (string2.equals("active") && boolValueOf.booleanValue()) {
                                    break;
                                }
                                i = i2 + 1;
                            }
                            return string;
                        } catch (JSONException e2) {
                            return null;
                        }
                    }
                }
            }
        } catch (Exception e3) {
            return null;
        }
    }
}
