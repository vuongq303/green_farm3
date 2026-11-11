package com.facebook.internal;

import android.content.Context;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import com.facebook.FacebookSdk;
import com.facebook.GraphRequest;
import com.google.firebase.analytics.FirebaseAnalytics;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentLinkedQueue;
import java.util.concurrent.Executor;
import java.util.concurrent.atomic.AtomicBoolean;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

/* JADX INFO: loaded from: classes.dex */
public final class FetchedAppGateKeepersManager {
    private static final String APPLICATION_FIELDS = "fields";
    private static final long APPLICATION_GATEKEEPER_CACHE_TIMEOUT = 3600000;
    private static final String APPLICATION_GATEKEEPER_EDGE = "mobile_sdk_gk";
    private static final String APPLICATION_GATEKEEPER_FIELD = "gatekeepers";
    private static final String APPLICATION_GRAPH_DATA = "data";
    private static final String APPLICATION_PLATFORM = "platform";
    private static final String APPLICATION_SDK_VERSION = "sdk_version";
    private static final String APP_GATEKEEPERS_PREFS_KEY_FORMAT = "com.facebook.internal.APP_GATEKEEPERS.%s";
    private static final String APP_GATEKEEPERS_PREFS_STORE = "com.facebook.internal.preferences.APP_GATEKEEPERS";
    private static final String APP_PLATFORM = "android";
    private static Long timestamp;
    private static final String TAG = FetchedAppGateKeepersManager.class.getCanonicalName();
    private static final AtomicBoolean isLoading = new AtomicBoolean(false);
    private static final ConcurrentLinkedQueue<Callback> callbacks = new ConcurrentLinkedQueue<>();
    private static final Map<String, JSONObject> fetchedAppGateKeepers = new ConcurrentHashMap();

    public interface Callback {
        void onCompleted();
    }

    static void loadAppGateKeepersAsync() {
        loadAppGateKeepersAsync(null);
    }

    /* JADX WARN: Code duplicated, block: B:11:0x0018 A[Catch: all -> 0x0065, TRY_ENTER, TryCatch #0 {, blocks: (B:5:0x0006, B:6:0x000b, B:8:0x0013, B:11:0x0018, B:13:0x002f, B:15:0x0042, B:17:0x0049, B:18:0x004c, B:20:0x0052, B:22:0x005c, B:28:0x0069), top: B:30:0x0006, inners: #1 }] */
    /* JADX WARN: Code duplicated, block: B:13:0x002f A[Catch: all -> 0x0065, TRY_LEAVE, TryCatch #0 {, blocks: (B:5:0x0006, B:6:0x000b, B:8:0x0013, B:11:0x0018, B:13:0x002f, B:15:0x0042, B:17:0x0049, B:18:0x004c, B:20:0x0052, B:22:0x005c, B:28:0x0069), top: B:30:0x0006, inners: #1 }] */
    /* JADX WARN: Code duplicated, block: B:17:0x0049 A[Catch: all -> 0x0065, TRY_ENTER, TryCatch #0 {, blocks: (B:5:0x0006, B:6:0x000b, B:8:0x0013, B:11:0x0018, B:13:0x002f, B:15:0x0042, B:17:0x0049, B:18:0x004c, B:20:0x0052, B:22:0x005c, B:28:0x0069), top: B:30:0x0006, inners: #1 }] */
    /* JADX WARN: Code duplicated, block: B:31:0x0042 A[EXC_TOP_SPLITTER, SYNTHETIC] */
    /* JADX WARN: Code duplicated, block: B:8:0x0013 A[Catch: all -> 0x0065, TRY_LEAVE, TryCatch #0 {, blocks: (B:5:0x0006, B:6:0x000b, B:8:0x0013, B:11:0x0018, B:13:0x002f, B:15:0x0042, B:17:0x0049, B:18:0x004c, B:20:0x0052, B:22:0x005c, B:28:0x0069), top: B:30:0x0006, inners: #1 }] */
    static synchronized void loadAppGateKeepersAsync(Callback callback) {
        final Context applicationContext;
        final String applicationId;
        final String str;
        String string;
        JSONObject jSONObject;
        Executor executor;
        if (callback != null) {
            callbacks.add(callback);
            if (isTimestampValid(timestamp)) {
                pollCallbacks();
            } else {
                applicationContext = FacebookSdk.getApplicationContext();
                applicationId = FacebookSdk.getApplicationId();
                str = String.format(APP_GATEKEEPERS_PREFS_KEY_FORMAT, applicationId);
                if (applicationContext != null) {
                    string = applicationContext.getSharedPreferences(APP_GATEKEEPERS_PREFS_STORE, 0).getString(str, null);
                    if (!Utility.isNullOrEmpty(string)) {
                        try {
                            jSONObject = new JSONObject(string);
                        } catch (JSONException e) {
                            Utility.logd("FacebookSDK", e);
                            jSONObject = null;
                        }
                        if (jSONObject != null) {
                            parseAppGateKeepersFromJSON(applicationId, jSONObject);
                        }
                    }
                    executor = FacebookSdk.getExecutor();
                    if (executor != null && isLoading.compareAndSet(false, true)) {
                        executor.execute(new Runnable() { // from class: com.facebook.internal.FetchedAppGateKeepersManager.1
                            @Override // java.lang.Runnable
                            public void run() {
                                JSONObject appGateKeepersQueryResponse = FetchedAppGateKeepersManager.getAppGateKeepersQueryResponse(applicationId);
                                if (appGateKeepersQueryResponse != null) {
                                    FetchedAppGateKeepersManager.parseAppGateKeepersFromJSON(applicationId, appGateKeepersQueryResponse);
                                    applicationContext.getSharedPreferences(FetchedAppGateKeepersManager.APP_GATEKEEPERS_PREFS_STORE, 0).edit().putString(str, appGateKeepersQueryResponse.toString()).apply();
                                    Long unused = FetchedAppGateKeepersManager.timestamp = Long.valueOf(System.currentTimeMillis());
                                }
                                FetchedAppGateKeepersManager.pollCallbacks();
                                FetchedAppGateKeepersManager.isLoading.set(false);
                            }
                        });
                    }
                }
            }
        } else if (isTimestampValid(timestamp)) {
            pollCallbacks();
        } else {
            applicationContext = FacebookSdk.getApplicationContext();
            applicationId = FacebookSdk.getApplicationId();
            str = String.format(APP_GATEKEEPERS_PREFS_KEY_FORMAT, applicationId);
            if (applicationContext != null) {
                string = applicationContext.getSharedPreferences(APP_GATEKEEPERS_PREFS_STORE, 0).getString(str, null);
                if (!Utility.isNullOrEmpty(string)) {
                    jSONObject = new JSONObject(string);
                    if (jSONObject != null) {
                        parseAppGateKeepersFromJSON(applicationId, jSONObject);
                    }
                }
                executor = FacebookSdk.getExecutor();
                if (executor != null) {
                    executor.execute(new Runnable() { // from class: com.facebook.internal.FetchedAppGateKeepersManager.1
                        @Override // java.lang.Runnable
                        public void run() {
                            JSONObject appGateKeepersQueryResponse = FetchedAppGateKeepersManager.getAppGateKeepersQueryResponse(applicationId);
                            if (appGateKeepersQueryResponse != null) {
                                FetchedAppGateKeepersManager.parseAppGateKeepersFromJSON(applicationId, appGateKeepersQueryResponse);
                                applicationContext.getSharedPreferences(FetchedAppGateKeepersManager.APP_GATEKEEPERS_PREFS_STORE, 0).edit().putString(str, appGateKeepersQueryResponse.toString()).apply();
                                Long unused = FetchedAppGateKeepersManager.timestamp = Long.valueOf(System.currentTimeMillis());
                            }
                            FetchedAppGateKeepersManager.pollCallbacks();
                            FetchedAppGateKeepersManager.isLoading.set(false);
                        }
                    });
                }
            }
        }
        throw th;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static void pollCallbacks() {
        Handler handler = new Handler(Looper.getMainLooper());
        while (!callbacks.isEmpty()) {
            final Callback callbackPoll = callbacks.poll();
            if (callbackPoll != null) {
                handler.post(new Runnable() { // from class: com.facebook.internal.FetchedAppGateKeepersManager.2
                    @Override // java.lang.Runnable
                    public void run() {
                        callbackPoll.onCompleted();
                    }
                });
            }
        }
    }

    static JSONObject queryAppGateKeepers(String str, boolean z) {
        if (!z && fetchedAppGateKeepers.containsKey(str)) {
            return fetchedAppGateKeepers.get(str);
        }
        JSONObject appGateKeepersQueryResponse = getAppGateKeepersQueryResponse(str);
        if (appGateKeepersQueryResponse == null) {
            return null;
        }
        FacebookSdk.getApplicationContext().getSharedPreferences(APP_GATEKEEPERS_PREFS_STORE, 0).edit().putString(String.format(APP_GATEKEEPERS_PREFS_KEY_FORMAT, str), appGateKeepersQueryResponse.toString()).apply();
        return parseAppGateKeepersFromJSON(str, appGateKeepersQueryResponse);
    }

    public static boolean getGateKeeperForKey(String str, String str2, boolean z) {
        loadAppGateKeepersAsync();
        return (str2 == null || !fetchedAppGateKeepers.containsKey(str2)) ? z : fetchedAppGateKeepers.get(str2).optBoolean(str, z);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static JSONObject getAppGateKeepersQueryResponse(String str) {
        Bundle bundle = new Bundle();
        bundle.putString(APPLICATION_PLATFORM, "android");
        bundle.putString(APPLICATION_SDK_VERSION, FacebookSdk.getSdkVersion());
        bundle.putString("fields", APPLICATION_GATEKEEPER_FIELD);
        GraphRequest graphRequestNewGraphPathRequest = GraphRequest.newGraphPathRequest(null, String.format("%s/%s", str, APPLICATION_GATEKEEPER_EDGE), null);
        graphRequestNewGraphPathRequest.setSkipClientToken(true);
        graphRequestNewGraphPathRequest.setParameters(bundle);
        return graphRequestNewGraphPathRequest.executeAndWait().getJSONObject();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static synchronized JSONObject parseAppGateKeepersFromJSON(String str, JSONObject jSONObject) {
        JSONObject jSONObject2;
        synchronized (FetchedAppGateKeepersManager.class) {
            if (fetchedAppGateKeepers.containsKey(str)) {
                jSONObject2 = fetchedAppGateKeepers.get(str);
            } else {
                jSONObject2 = new JSONObject();
            }
            JSONArray jSONArrayOptJSONArray = jSONObject.optJSONArray("data");
            JSONObject jSONObjectOptJSONObject = null;
            if (jSONArrayOptJSONArray != null) {
                jSONObjectOptJSONObject = jSONArrayOptJSONArray.optJSONObject(0);
            }
            if (jSONObjectOptJSONObject != null && jSONObjectOptJSONObject.optJSONArray(APPLICATION_GATEKEEPER_FIELD) != null) {
                JSONArray jSONArrayOptJSONArray2 = jSONObjectOptJSONObject.optJSONArray(APPLICATION_GATEKEEPER_FIELD);
                for (int i = 0; i < jSONArrayOptJSONArray2.length(); i++) {
                    try {
                        JSONObject jSONObject3 = jSONArrayOptJSONArray2.getJSONObject(i);
                        jSONObject2.put(jSONObject3.getString("key"), jSONObject3.getBoolean(FirebaseAnalytics.Param.VALUE));
                    } catch (JSONException e) {
                        Utility.logd("FacebookSDK", e);
                    }
                }
            }
            fetchedAppGateKeepers.put(str, jSONObject2);
        }
        return jSONObject2;
    }

    private static boolean isTimestampValid(Long l) {
        return l != null && System.currentTimeMillis() - l.longValue() < APPLICATION_GATEKEEPER_CACHE_TIMEOUT;
    }
}
