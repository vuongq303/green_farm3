package com.gameloft.android2d.iap.utils;

import android.content.Context;
import android.os.Bundle;
import android.text.TextUtils;
import com.gameloft.glads.AndroidWebView;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import javax.net.ssl.HttpsURLConnection;
import javax.net.ssl.SSLContext;
import javax.net.ssl.TrustManager;
import org.json.JSONException;
import org.json.JSONObject;

/* JADX INFO: compiled from: IABRequestHandler.java */
/* JADX INFO: loaded from: classes.dex */
public class n {
    private static n bnD = null;
    private int bnC = 10000;
    private String bnE = null;
    public final int bnF = 0;
    public final int bnG = 1;
    public final int bnH = 2;

    void mF(int i) {
        this.bnC = i;
    }

    int SI() {
        return this.bnC;
    }

    public static n SJ() {
        if (bnD == null) {
            bnD = new n();
        }
        return bnD;
    }

    public void a(String str, String str2, m mVar) {
        a(str, str2, null, mVar);
    }

    public void a(String str, String str2, String str3, m mVar) {
        a(str, str2, str3, 10000, mVar);
    }

    public void a(String str, String str2, String str3, int i, m mVar) {
        mF(i);
        Bundle bundle = new Bundle();
        bundle.putString("a1", str);
        bundle.putString("a2", str2);
        bundle.putString("a7", str3);
        bundle.putInt("a3", 0);
        new p(this, mVar).a(bundle, (Context) null);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public Bundle r(Bundle bundle) {
        URL url;
        try {
            String string = bundle.getString("a1");
            String string2 = bundle.getString("a2");
            String string3 = bundle.getString("a7");
            int i = bundle.getInt("a3");
            String str = string2 == null ? "" : string2;
            String str2 = string.contains("?") ? "&" : "?";
            if (i == 1 || i == 2 || TextUtils.isEmpty(str)) {
                url = new URL(string);
            } else {
                url = new URL(string + str2 + str);
            }
            HttpURLConnection httpURLConnectionA = a(url, i);
            if (!TextUtils.isEmpty(string3)) {
                try {
                    JSONObject jSONObject = new JSONObject(string3);
                    Iterator<String> itKeys = jSONObject.keys();
                    while (itKeys.hasNext()) {
                        String string4 = itKeys.next().toString();
                        String string5 = jSONObject.getString(string4);
                        if (!TextUtils.isEmpty(string5)) {
                            httpURLConnectionA.setRequestProperty(string4, string5);
                        }
                    }
                } catch (JSONException e) {
                }
            }
            if ((i == 1 || i == 2) && !TextUtils.isEmpty(str)) {
                httpURLConnectionA.setFixedLengthStreamingMode(str.length());
                OutputStream outputStream = httpURLConnectionA.getOutputStream();
                outputStream.write(str.getBytes(), 0, str.length());
                outputStream.flush();
            }
            int responseCode = httpURLConnectionA.getResponseCode();
            bundle.clear();
            JSONObject jSONObject2 = new JSONObject();
            Map<String, List<String>> headerFields = httpURLConnectionA.getHeaderFields();
            if (headerFields != null) {
                for (Map.Entry<String, List<String>> entry : headerFields.entrySet()) {
                    try {
                        if (entry.getKey() != null) {
                            Iterator<String> it = entry.getValue().iterator();
                            StringBuffer stringBuffer = new StringBuffer();
                            stringBuffer.append((Object) it.next());
                            while (it.hasNext()) {
                                stringBuffer.append(" ");
                                stringBuffer.append((Object) it.next());
                            }
                            jSONObject2.put(entry.getKey().toString(), stringBuffer.toString());
                        }
                    } catch (JSONException e2) {
                    }
                }
            }
            bundle.putInt("a6", responseCode);
            bundle.putInt("a5", 2);
            if (responseCode == 200 || responseCode == 201) {
                String strL = v.l(httpURLConnectionA.getInputStream());
                bundle.putInt("a5", 1);
                if (TextUtils.isEmpty(strL)) {
                    strL = "";
                }
                bundle.putString("a4", strL);
            } else {
                String strL2 = v.l(httpURLConnectionA.getErrorStream());
                if (TextUtils.isEmpty(strL2)) {
                    strL2 = "";
                }
                bundle.putString("a4", strL2);
            }
            if (jSONObject2 != null) {
                bundle.putString("a7", jSONObject2.toString());
            }
        } catch (Exception e3) {
            bundle.clear();
            bundle.putInt("a5", -1);
            bundle.putString("a4", e3.getMessage());
        }
        return bundle;
    }

    public String encodeString(String str) {
        try {
            return URLEncoder.encode(str, AndroidWebView.UTF_8);
        } catch (Exception e) {
            return "";
        }
    }

    private boolean fB(String str) {
        return str.indexOf("https") != -1;
    }

    private HttpURLConnection a(URL url, int i) {
        HttpURLConnection httpURLConnection;
        try {
            SSLContext sSLContext = SSLContext.getInstance("TLS");
            sSLContext.init(null, new TrustManager[]{new o(this)}, null);
            HttpsURLConnection.setDefaultSSLSocketFactory(sSLContext.getSocketFactory());
            HttpsURLConnection.setDefaultHostnameVerifier(HttpsURLConnection.getDefaultHostnameVerifier());
            if (fB(url.toString())) {
                httpURLConnection = (HttpsURLConnection) url.openConnection();
            } else {
                httpURLConnection = (HttpURLConnection) url.openConnection();
            }
            try {
                httpURLConnection.setConnectTimeout(SI());
                httpURLConnection.setDoInput(true);
                httpURLConnection.setUseCaches(false);
                HttpURLConnection.setFollowRedirects(true);
                httpURLConnection.setRequestProperty("Connection", "Keep-Alive");
                if (i == 1) {
                    httpURLConnection.setDoOutput(true);
                    httpURLConnection.setRequestMethod("POST");
                } else if (i == 0) {
                    httpURLConnection.setRequestMethod("GET");
                } else if (i == 2) {
                    httpURLConnection.setDoOutput(true);
                    httpURLConnection.setRequestMethod("PUT");
                }
                return httpURLConnection;
            } catch (Exception e) {
                return httpURLConnection;
            }
        } catch (Exception e2) {
            return null;
        }
    }
}
