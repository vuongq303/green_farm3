package com.facebook.applinks;

import a.d;
import a.e;
import a.m;
import a.o;
import android.net.Uri;
import android.os.Bundle;
import com.facebook.AccessToken;
import com.facebook.FacebookRequestError;
import com.facebook.GraphRequest;
import com.facebook.GraphResponse;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

/* JADX INFO: loaded from: classes.dex */
public class FacebookAppLinkResolver {
    private static final String APP_LINK_ANDROID_TARGET_KEY = "android";
    private static final String APP_LINK_KEY = "app_links";
    private static final String APP_LINK_TARGET_APP_NAME_KEY = "app_name";
    private static final String APP_LINK_TARGET_CLASS_KEY = "class";
    private static final String APP_LINK_TARGET_PACKAGE_KEY = "package";
    private static final String APP_LINK_TARGET_SHOULD_FALLBACK_KEY = "should_fallback";
    private static final String APP_LINK_TARGET_URL_KEY = "url";
    private static final String APP_LINK_WEB_TARGET_KEY = "web";
    private final HashMap<Uri, d> cachedAppLinks = new HashMap<>();

    public o<d> getAppLinkFromUrlInBackground(final Uri uri) {
        ArrayList arrayList = new ArrayList();
        arrayList.add(uri);
        return getAppLinkFromUrlsInBackground(arrayList).b((m<Map<Uri, d>, TContinuationResult>) new m<Map<Uri, d>, d>() { // from class: com.facebook.applinks.FacebookAppLinkResolver.1
            /* JADX WARN: Can't rename method to resolve collision */
            @Override // a.m
            public d then(o<Map<Uri, d>> oVar) {
                return oVar.getResult().get(uri);
            }
        });
    }

    public o<Map<Uri, d>> getAppLinkFromUrlsInBackground(List<Uri> list) {
        d dVar;
        final HashMap map = new HashMap();
        final HashSet hashSet = new HashSet();
        StringBuilder sb = new StringBuilder();
        for (Uri uri : list) {
            synchronized (this.cachedAppLinks) {
                dVar = this.cachedAppLinks.get(uri);
            }
            if (dVar != null) {
                map.put(uri, dVar);
            } else {
                if (!hashSet.isEmpty()) {
                    sb.append(',');
                }
                sb.append(uri.toString());
                hashSet.add(uri);
            }
        }
        if (hashSet.isEmpty()) {
            return o.o(map);
        }
        final o.v vVarFA = o.fA();
        Bundle bundle = new Bundle();
        bundle.putString("ids", sb.toString());
        bundle.putString(GraphRequest.FIELDS_PARAM, String.format("%s.fields(%s,%s)", APP_LINK_KEY, "android", "web"));
        new GraphRequest(AccessToken.getCurrentAccessToken(), "", bundle, null, new GraphRequest.Callback() { // from class: com.facebook.applinks.FacebookAppLinkResolver.2
            @Override // com.facebook.GraphRequest.Callback
            public void onCompleted(GraphResponse graphResponse) {
                FacebookRequestError error = graphResponse.getError();
                if (error != null) {
                    vVarFA.c(error.getException());
                    return;
                }
                JSONObject jSONObject = graphResponse.getJSONObject();
                if (jSONObject == null) {
                    vVarFA.setResult(map);
                    return;
                }
                for (Uri uri2 : hashSet) {
                    if (jSONObject.has(uri2.toString())) {
                        try {
                            JSONObject jSONObject2 = jSONObject.getJSONObject(uri2.toString()).getJSONObject(FacebookAppLinkResolver.APP_LINK_KEY);
                            JSONArray jSONArray = jSONObject2.getJSONArray("android");
                            int length = jSONArray.length();
                            ArrayList arrayList = new ArrayList(length);
                            for (int i = 0; i < length; i++) {
                                e androidTargetFromJson = FacebookAppLinkResolver.getAndroidTargetFromJson(jSONArray.getJSONObject(i));
                                if (androidTargetFromJson != null) {
                                    arrayList.add(androidTargetFromJson);
                                }
                            }
                            d dVar2 = new d(uri2, arrayList, FacebookAppLinkResolver.getWebFallbackUriFromJson(uri2, jSONObject2));
                            map.put(uri2, dVar2);
                            synchronized (FacebookAppLinkResolver.this.cachedAppLinks) {
                                try {
                                    FacebookAppLinkResolver.this.cachedAppLinks.put(uri2, dVar2);
                                } catch (Throwable th) {
                                    throw th;
                                }
                            }
                        } catch (JSONException e) {
                        }
                    }
                }
                vVarFA.setResult(map);
            }
        }).executeAsync();
        return vVarFA.fF();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static e getAndroidTargetFromJson(JSONObject jSONObject) {
        String strTryGetStringFromJson = tryGetStringFromJson(jSONObject, APP_LINK_TARGET_PACKAGE_KEY, null);
        if (strTryGetStringFromJson == null) {
            return null;
        }
        String strTryGetStringFromJson2 = tryGetStringFromJson(jSONObject, APP_LINK_TARGET_CLASS_KEY, null);
        String strTryGetStringFromJson3 = tryGetStringFromJson(jSONObject, "app_name", null);
        String strTryGetStringFromJson4 = tryGetStringFromJson(jSONObject, "url", null);
        return new e(strTryGetStringFromJson, strTryGetStringFromJson2, strTryGetStringFromJson4 != null ? Uri.parse(strTryGetStringFromJson4) : null, strTryGetStringFromJson3);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static Uri getWebFallbackUriFromJson(Uri uri, JSONObject jSONObject) {
        try {
            JSONObject jSONObject2 = jSONObject.getJSONObject("web");
            if (!tryGetBooleanFromJson(jSONObject2, APP_LINK_TARGET_SHOULD_FALLBACK_KEY, true)) {
                return null;
            }
            String strTryGetStringFromJson = tryGetStringFromJson(jSONObject2, "url", null);
            Uri uri2 = strTryGetStringFromJson != null ? Uri.parse(strTryGetStringFromJson) : null;
            return uri2 != null ? uri2 : uri;
        } catch (JSONException e) {
            return uri;
        }
    }

    private static String tryGetStringFromJson(JSONObject jSONObject, String str, String str2) {
        try {
            return jSONObject.getString(str);
        } catch (JSONException e) {
            return str2;
        }
    }

    private static boolean tryGetBooleanFromJson(JSONObject jSONObject, String str, boolean z) {
        try {
            return jSONObject.getBoolean(str);
        } catch (JSONException e) {
            return z;
        }
    }
}
