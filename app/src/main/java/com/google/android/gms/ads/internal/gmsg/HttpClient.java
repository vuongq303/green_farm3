package com.google.android.gms.ads.internal.gmsg;

import android.content.Context;
import android.text.TextUtils;
import com.google.android.gms.ads.internal.zzbv;
import com.google.android.gms.internal.ads.zzadh;
import com.google.android.gms.internal.ads.zzakb;
import com.google.android.gms.internal.ads.zzaki;
import com.google.android.gms.internal.ads.zzakk;
import com.google.android.gms.internal.ads.zzamy;
import com.google.android.gms.internal.ads.zzang;
import com.google.android.gms.internal.ads.zzue;
import com.google.firebase.analytics.FirebaseAnalytics;
import java.io.BufferedOutputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

/* JADX INFO: loaded from: classes.dex */
@zzadh
public class HttpClient implements zzv<zzue> {
    private final Context mContext;
    private final zzang zzyf;

    public HttpClient(Context context, zzang zzangVar) {
        this.mContext = context;
        this.zzyf = zzangVar;
    }

    private static b M(JSONObject jSONObject) {
        URL url;
        String strOptString = jSONObject.optString("http_request_id");
        String strOptString2 = jSONObject.optString("url");
        String strOptString3 = jSONObject.optString("post_body", null);
        try {
            url = new URL(strOptString2);
        } catch (MalformedURLException e) {
            zzakb.zzb("Error constructing http request.", e);
            url = null;
        }
        ArrayList arrayList = new ArrayList();
        JSONArray jSONArrayOptJSONArray = jSONObject.optJSONArray("headers");
        if (jSONArrayOptJSONArray == null) {
            jSONArrayOptJSONArray = new JSONArray();
        }
        for (int i = 0; i < jSONArrayOptJSONArray.length(); i++) {
            JSONObject jSONObjectOptJSONObject = jSONArrayOptJSONArray.optJSONObject(i);
            if (jSONObjectOptJSONObject != null) {
                arrayList.add(new a(jSONObjectOptJSONObject.optString("key"), jSONObjectOptJSONObject.optString(FirebaseAnalytics.Param.VALUE)));
            }
        }
        return new b(strOptString, url, arrayList, strOptString3);
    }

    /* JADX WARN: Code duplicated, block: B:30:0x00cb  */
    private final c a(b bVar) throws Throwable {
        Throwable th;
        Exception e;
        HttpURLConnection httpURLConnection;
        HttpURLConnection httpURLConnection2 = null;
        byte[] bytes = null;
        try {
            HttpURLConnection httpURLConnection3 = (HttpURLConnection) bVar.WM().openConnection();
            try {
                zzbv.zzek().zza(this.mContext, this.zzyf.zzcw, false, httpURLConnection3);
                ArrayList<a> arrayListWN = bVar.WN();
                int size = arrayListWN.size();
                int i = 0;
                while (i < size) {
                    a aVar = arrayListWN.get(i);
                    i++;
                    a aVar2 = aVar;
                    httpURLConnection3.addRequestProperty(aVar2.getKey(), aVar2.getValue());
                }
                if (!TextUtils.isEmpty(bVar.WO())) {
                    httpURLConnection3.setDoOutput(true);
                    bytes = bVar.WO().getBytes();
                    httpURLConnection3.setFixedLengthStreamingMode(bytes.length);
                    BufferedOutputStream bufferedOutputStream = new BufferedOutputStream(httpURLConnection3.getOutputStream());
                    bufferedOutputStream.write(bytes);
                    bufferedOutputStream.close();
                }
                zzamy zzamyVar = new zzamy();
                zzamyVar.zza(httpURLConnection3, bytes);
                ArrayList arrayList = new ArrayList();
                if (httpURLConnection3.getHeaderFields() != null) {
                    for (Map.Entry<String, List<String>> entry : httpURLConnection3.getHeaderFields().entrySet()) {
                        Iterator<String> it = entry.getValue().iterator();
                        while (it.hasNext()) {
                            arrayList.add(new a(entry.getKey(), it.next()));
                        }
                    }
                }
                String strWL = bVar.WL();
                int responseCode = httpURLConnection3.getResponseCode();
                zzbv.zzek();
                d dVar = new d(strWL, responseCode, arrayList, zzakk.zza(new InputStreamReader(httpURLConnection3.getInputStream())));
                zzamyVar.zza(httpURLConnection3, dVar.getResponseCode());
                zzamyVar.zzdg(dVar.getBody());
                c cVar = new c(this, true, dVar, null);
                if (httpURLConnection3 != null) {
                    httpURLConnection3.disconnect();
                }
                return cVar;
            } catch (Exception e2) {
                e = e2;
                httpURLConnection = httpURLConnection3;
                try {
                    c cVar2 = new c(this, false, null, e.toString());
                    if (httpURLConnection == null) {
                        return cVar2;
                    }
                    httpURLConnection.disconnect();
                    return cVar2;
                } catch (Throwable th2) {
                    th = th2;
                    httpURLConnection2 = httpURLConnection;
                    if (httpURLConnection2 != null) {
                        httpURLConnection2.disconnect();
                    }
                    throw th;
                }
            } catch (Throwable th3) {
                th = th3;
                httpURLConnection2 = httpURLConnection3;
                if (httpURLConnection2 != null) {
                    httpURLConnection2.disconnect();
                }
                throw th;
            }
        } catch (Exception e3) {
            e = e3;
            httpURLConnection = null;
        } catch (Throwable th4) {
            th = th4;
        }
    }

    private static JSONObject a(d dVar) {
        JSONObject jSONObject = new JSONObject();
        try {
            jSONObject.put("http_request_id", dVar.WL());
            if (dVar.getBody() != null) {
                jSONObject.put("body", dVar.getBody());
            }
            JSONArray jSONArray = new JSONArray();
            for (a aVar : dVar.WQ()) {
                jSONArray.put(new JSONObject().put("key", aVar.getKey()).put(FirebaseAnalytics.Param.VALUE, aVar.getValue()));
            }
            jSONObject.put("headers", jSONArray);
            jSONObject.put("response_code", dVar.getResponseCode());
        } catch (JSONException e) {
            zzakb.zzb("Error constructing JSON for http response.", e);
        }
        return jSONObject;
    }

    public JSONObject send(JSONObject jSONObject) throws Throwable {
        JSONObject jSONObject2 = new JSONObject();
        String strOptString = "";
        try {
            strOptString = jSONObject.optString("http_request_id");
            c cVarA = a(M(jSONObject));
            if (cVarA.Sp()) {
                jSONObject2.put("response", a(cVarA.WP()));
                jSONObject2.put("success", true);
            } else {
                jSONObject2.put("response", new JSONObject().put("http_request_id", strOptString));
                jSONObject2.put("success", false);
                jSONObject2.put("reason", cVarA.getReason());
            }
        } catch (Exception e) {
            zzakb.zzb("Error executing http request.", e);
            try {
                jSONObject2.put("response", new JSONObject().put("http_request_id", strOptString));
                jSONObject2.put("success", false);
                jSONObject2.put("reason", e.toString());
            } catch (JSONException e2) {
                zzakb.zzb("Error executing http request.", e2);
            }
        }
        return jSONObject2;
    }

    @Override // com.google.android.gms.ads.internal.gmsg.zzv
    public final /* synthetic */ void zza(zzue zzueVar, Map map) {
        zzaki.zzb(new t(this, map, zzueVar));
    }
}
