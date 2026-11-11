package com.facebook.appevents.codeless;

import android.app.Activity;
import android.content.Context;
import android.hardware.Sensor;
import android.hardware.SensorManager;
import android.os.Build;
import android.os.Bundle;
import com.facebook.FacebookSdk;
import com.facebook.GraphRequest;
import com.facebook.appevents.AppEventsConstants;
import com.facebook.appevents.codeless.internal.Constants;
import com.facebook.appevents.internal.AppEventUtility;
import com.facebook.core.BuildConfig;
import com.facebook.internal.AttributionIdentifiers;
import com.facebook.internal.FeatureManager;
import com.facebook.internal.FetchedAppSettings;
import com.facebook.internal.FetchedAppSettingsManager;
import com.facebook.internal.Utility;
import java.util.Locale;
import java.util.UUID;
import org.json.JSONArray;
import org.json.JSONObject;

/* JADX INFO: loaded from: classes.dex */
public final class CodelessManager {
    private static SensorManager sensorManager;
    private static ViewIndexer viewIndexer;
    private static final ViewIndexingTrigger viewIndexingTrigger = new ViewIndexingTrigger();
    private static String deviceSessionID = null;
    private static Boolean isAppIndexingEnabled = false;
    private static volatile Boolean isCheckingSession = false;

    public static void onActivityResumed(final Activity activity) {
        FeatureManager.checkFeature(FeatureManager.Feature.CodelessEvents, new FeatureManager.Callback() { // from class: com.facebook.appevents.codeless.CodelessManager.1
            @Override // com.facebook.internal.FeatureManager.Callback
            public void onCompleted(boolean z) {
                if (z) {
                    CodelessMatcher.getInstance().add(activity);
                    Context applicationContext = activity.getApplicationContext();
                    final String applicationId = FacebookSdk.getApplicationId();
                    final FetchedAppSettings appSettingsWithoutQuery = FetchedAppSettingsManager.getAppSettingsWithoutQuery(applicationId);
                    if ((appSettingsWithoutQuery != null && appSettingsWithoutQuery.getCodelessEventsEnabled()) || (BuildConfig.DEBUG && AppEventUtility.isEmulator())) {
                        SensorManager unused = CodelessManager.sensorManager = (SensorManager) applicationContext.getSystemService("sensor");
                        if (CodelessManager.sensorManager != null) {
                            Sensor defaultSensor = CodelessManager.sensorManager.getDefaultSensor(1);
                            ViewIndexer unused2 = CodelessManager.viewIndexer = new ViewIndexer(activity);
                            CodelessManager.viewIndexingTrigger.setOnShakeListener(new ViewIndexingTrigger.OnShakeListener() { // from class: com.facebook.appevents.codeless.CodelessManager.1.1
                                @Override // com.facebook.appevents.codeless.ViewIndexingTrigger.OnShakeListener
                                public void onShake() {
                                    boolean z2 = false;
                                    boolean z3 = appSettingsWithoutQuery != null && appSettingsWithoutQuery.getCodelessEventsEnabled();
                                    if (FacebookSdk.getCodelessSetupEnabled() || (BuildConfig.DEBUG && AppEventUtility.isEmulator())) {
                                        z2 = true;
                                    }
                                    if (z3 && z2) {
                                        CodelessManager.checkCodelessSession(applicationId);
                                    }
                                }
                            });
                            CodelessManager.sensorManager.registerListener(CodelessManager.viewIndexingTrigger, defaultSensor, 2);
                            if (appSettingsWithoutQuery != null && appSettingsWithoutQuery.getCodelessEventsEnabled()) {
                                CodelessManager.viewIndexer.schedule();
                            }
                        } else {
                            return;
                        }
                    }
                    if (BuildConfig.DEBUG && AppEventUtility.isEmulator() && !CodelessManager.isAppIndexingEnabled.booleanValue()) {
                        CodelessManager.checkCodelessSession(applicationId);
                    }
                }
            }
        });
    }

    public static void onActivityPaused(final Activity activity) {
        FeatureManager.checkFeature(FeatureManager.Feature.CodelessEvents, new FeatureManager.Callback() { // from class: com.facebook.appevents.codeless.CodelessManager.2
            @Override // com.facebook.internal.FeatureManager.Callback
            public void onCompleted(boolean z) {
                if (z) {
                    CodelessMatcher.getInstance().remove(activity);
                    if (CodelessManager.viewIndexer != null) {
                        CodelessManager.viewIndexer.unschedule();
                    }
                    if (CodelessManager.sensorManager != null) {
                        CodelessManager.sensorManager.unregisterListener(CodelessManager.viewIndexingTrigger);
                    }
                }
            }
        });
    }

    public static void onActivityDestroyed(Activity activity) {
        CodelessMatcher.getInstance().destroy(activity);
    }

    public static void checkCodelessSession(final String str) {
        if (!isCheckingSession.booleanValue()) {
            isCheckingSession = true;
            FacebookSdk.getExecutor().execute(new Runnable() { // from class: com.facebook.appevents.codeless.CodelessManager.3
                @Override // java.lang.Runnable
                public void run() {
                    GraphRequest graphRequestNewPostRequest = GraphRequest.newPostRequest(null, String.format(Locale.US, "%s/app_indexing_session", str), null, null);
                    Bundle parameters = graphRequestNewPostRequest.getParameters();
                    if (parameters == null) {
                        parameters = new Bundle();
                    }
                    AttributionIdentifiers attributionIdentifiers = AttributionIdentifiers.getAttributionIdentifiers(FacebookSdk.getApplicationContext());
                    JSONArray jSONArray = new JSONArray();
                    jSONArray.put(Build.MODEL != null ? Build.MODEL : "");
                    if (attributionIdentifiers != null && attributionIdentifiers.getAndroidAdvertiserId() != null) {
                        jSONArray.put(attributionIdentifiers.getAndroidAdvertiserId());
                    } else {
                        jSONArray.put("");
                    }
                    jSONArray.put(BuildConfig.DEBUG ? AppEventsConstants.EVENT_PARAM_VALUE_YES : AppEventsConstants.EVENT_PARAM_VALUE_NO);
                    jSONArray.put(AppEventUtility.isEmulator() ? AppEventsConstants.EVENT_PARAM_VALUE_YES : AppEventsConstants.EVENT_PARAM_VALUE_NO);
                    Locale currentLocale = Utility.getCurrentLocale();
                    jSONArray.put(currentLocale.getLanguage() + "_" + currentLocale.getCountry());
                    String string = jSONArray.toString();
                    parameters.putString(Constants.DEVICE_SESSION_ID, CodelessManager.getCurrentDeviceSessionID());
                    parameters.putString(Constants.EXTINFO, string);
                    graphRequestNewPostRequest.setParameters(parameters);
                    if (graphRequestNewPostRequest != null) {
                        JSONObject jSONObject = graphRequestNewPostRequest.executeAndWait().getJSONObject();
                        Boolean unused = CodelessManager.isAppIndexingEnabled = Boolean.valueOf(jSONObject != null && jSONObject.optBoolean(Constants.APP_INDEXING_ENABLED, false));
                        if (!CodelessManager.isAppIndexingEnabled.booleanValue()) {
                            String unused2 = CodelessManager.deviceSessionID = null;
                        } else {
                            CodelessManager.viewIndexer.schedule();
                        }
                    }
                    Boolean unused3 = CodelessManager.isCheckingSession = false;
                }
            });
        }
    }

    public static String getCurrentDeviceSessionID() {
        if (deviceSessionID == null) {
            deviceSessionID = UUID.randomUUID().toString();
        }
        return deviceSessionID;
    }

    public static boolean getIsAppIndexingEnabled() {
        return isAppIndexingEnabled.booleanValue();
    }

    public static void updateAppIndexing(Boolean bool) {
        isAppIndexingEnabled = bool;
    }
}
