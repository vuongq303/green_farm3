package com.facebook.appevents;

import android.app.Application;
import android.content.Context;
import android.content.SharedPreferences;
import android.os.Build;
import android.os.Bundle;
import android.webkit.WebView;
import com.facebook.AccessToken;
import com.facebook.FacebookException;
import com.facebook.FacebookSdk;
import com.facebook.GraphRequest;
import com.facebook.HttpMethod;
import com.facebook.LoggingBehavior;
import com.facebook.appevents.internal.ActivityLifecycleTracker;
import com.facebook.appevents.internal.AutomaticAnalyticsLogger;
import com.facebook.appevents.internal.Constants;
import com.facebook.internal.AnalyticsEvents;
import com.facebook.internal.AttributionIdentifiers;
import com.facebook.internal.BundleJSONConverter;
import com.facebook.internal.FetchedAppGateKeepersManager;
import com.facebook.internal.FetchedAppSettingsManager;
import com.facebook.internal.Logger;
import com.facebook.internal.Utility;
import com.facebook.internal.Validate;
import com.facebook.share.internal.ShareConstants;
import java.math.BigDecimal;
import java.util.Currency;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Locale;
import java.util.UUID;
import java.util.concurrent.Executor;
import java.util.concurrent.ScheduledThreadPoolExecutor;
import java.util.concurrent.TimeUnit;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

/* JADX INFO: loaded from: classes.dex */
class AppEventsLoggerImpl {
    private static final String ACCOUNT_KIT_EVENT_NAME_PREFIX = "fb_ak";
    private static final String APP_EVENTS_KILLSWITCH = "app_events_killswitch";
    private static final String APP_EVENT_NAME_PUSH_OPENED = "fb_mobile_push_opened";
    private static final String APP_EVENT_PREFERENCES = "com.facebook.sdk.appEventPreferences";
    private static final String APP_EVENT_PUSH_PARAMETER_ACTION = "fb_push_action";
    private static final String APP_EVENT_PUSH_PARAMETER_CAMPAIGN = "fb_push_campaign";
    private static final int APP_SUPPORTS_ATTRIBUTION_ID_RECHECK_PERIOD_IN_SECONDS = 86400;
    private static final String PUSH_PAYLOAD_CAMPAIGN_KEY = "campaign";
    private static final String PUSH_PAYLOAD_KEY = "fb_push_payload";
    private static String anonymousAppDeviceGUID;
    private static ScheduledThreadPoolExecutor backgroundExecutor;
    private static boolean isActivateAppEventRequested;
    private static String pushNotificationsRegistrationId;
    private final AccessTokenAppIdPair accessTokenAppId;
    private final String contextName;
    private static final String TAG = AppEventsLoggerImpl.class.getCanonicalName();
    private static AppEventsLogger.FlushBehavior flushBehavior = AppEventsLogger.FlushBehavior.AUTO;
    private static final Object staticLock = new Object();

    static void activateApp(Application application, String str) {
        if (!FacebookSdk.isInitialized()) {
            throw new FacebookException("The Facebook sdk must be initialized before calling activateApp");
        }
        AnalyticsUserIDStore.initStore();
        UserDataStore.initStore();
        if (str == null) {
            str = FacebookSdk.getApplicationId();
        }
        FacebookSdk.publishInstallAsync(application, str);
        ActivityLifecycleTracker.startTracking(application, str);
    }

    static void functionDEPRECATED(String str) {
        String str2 = TAG;
        String str3 = "This function is deprecated. " + str;
    }

    static void initializeLib(final Context context, String str) {
        if (FacebookSdk.getAutoLogAppEventsEnabled()) {
            final AppEventsLoggerImpl appEventsLoggerImpl = new AppEventsLoggerImpl(context, str, (AccessToken) null);
            backgroundExecutor.execute(new Runnable() { // from class: com.facebook.appevents.AppEventsLoggerImpl.1
                @Override // java.lang.Runnable
                public void run() {
                    Bundle bundle = new Bundle();
                    String[] strArr = {"com.facebook.core.Core", "com.facebook.login.Login", "com.facebook.share.Share", "com.facebook.places.Places", "com.facebook.messenger.Messenger", "com.facebook.applinks.AppLinks", "com.facebook.marketing.Marketing", "com.facebook.all.All", "com.android.billingclient.api.BillingClient", "com.android.vending.billing.IInAppBillingService"};
                    String[] strArr2 = {"core_lib_included", "login_lib_included", "share_lib_included", "places_lib_included", "messenger_lib_included", "applinks_lib_included", "marketing_lib_included", "all_lib_included", "billing_client_lib_included", "billing_service_lib_included"};
                    if (strArr.length != strArr2.length) {
                        throw new FacebookException("Number of class names and key names should match");
                    }
                    int i = 0;
                    for (int i2 = 0; i2 < strArr.length; i2++) {
                        String str2 = strArr[i2];
                        String str3 = strArr2[i2];
                        try {
                            Class.forName(str2);
                            bundle.putInt(str3, 1);
                            i |= 1 << i2;
                        } catch (ClassNotFoundException e) {
                        }
                    }
                    SharedPreferences sharedPreferences = context.getSharedPreferences(AppEventsLoggerImpl.APP_EVENT_PREFERENCES, 0);
                    if (sharedPreferences.getInt("kitsBitmask", 0) != i) {
                        sharedPreferences.edit().putInt("kitsBitmask", i).apply();
                        appEventsLoggerImpl.logEventImplicitly(AnalyticsEvents.EVENT_SDK_INITIALIZE, null, bundle);
                    }
                }
            });
        }
    }

    static AppEventsLogger.FlushBehavior getFlushBehavior() {
        AppEventsLogger.FlushBehavior flushBehavior2;
        synchronized (staticLock) {
            flushBehavior2 = flushBehavior;
        }
        return flushBehavior2;
    }

    static void setFlushBehavior(AppEventsLogger.FlushBehavior flushBehavior2) {
        synchronized (staticLock) {
            flushBehavior = flushBehavior2;
        }
    }

    void logEvent(String str) {
        logEvent(str, (Bundle) null);
    }

    void logEvent(String str, double d) {
        logEvent(str, d, null);
    }

    void logEvent(String str, Bundle bundle) {
        logEvent(str, null, bundle, false, ActivityLifecycleTracker.getCurrentSessionGuid());
    }

    void logEvent(String str, double d, Bundle bundle) {
        logEvent(str, Double.valueOf(d), bundle, false, ActivityLifecycleTracker.getCurrentSessionGuid());
    }

    void logPurchase(BigDecimal bigDecimal, Currency currency) {
        logPurchase(bigDecimal, currency, null);
    }

    void logPurchase(BigDecimal bigDecimal, Currency currency, Bundle bundle) {
        if (AutomaticAnalyticsLogger.isImplicitPurchaseLoggingEnabled()) {
            String str = TAG;
        }
        logPurchase(bigDecimal, currency, bundle, false);
    }

    void logPurchaseImplicitly(BigDecimal bigDecimal, Currency currency, Bundle bundle) {
        logPurchase(bigDecimal, currency, bundle, true);
    }

    void logPurchase(BigDecimal bigDecimal, Currency currency, Bundle bundle, boolean z) {
        if (bigDecimal == null) {
            notifyDeveloperError("purchaseAmount cannot be null");
            return;
        }
        if (currency == null) {
            notifyDeveloperError("currency cannot be null");
            return;
        }
        Bundle bundle2 = bundle == null ? new Bundle() : bundle;
        bundle2.putString(AppEventsConstants.EVENT_PARAM_CURRENCY, currency.getCurrencyCode());
        logEvent(AppEventsConstants.EVENT_NAME_PURCHASED, Double.valueOf(bigDecimal.doubleValue()), bundle2, z, ActivityLifecycleTracker.getCurrentSessionGuid());
        eagerFlush();
    }

    void logPushNotificationOpen(Bundle bundle, String str) {
        String string = null;
        try {
            String string2 = bundle.getString(PUSH_PAYLOAD_KEY);
            if (!Utility.isNullOrEmpty(string2)) {
                string = new JSONObject(string2).getString("campaign");
            } else {
                return;
            }
        } catch (JSONException e) {
        }
        if (string == null) {
            Logger.log(LoggingBehavior.DEVELOPER_ERRORS, TAG, "Malformed payload specified for logging a push notification open.");
            return;
        }
        Bundle bundle2 = new Bundle();
        bundle2.putString(APP_EVENT_PUSH_PARAMETER_CAMPAIGN, string);
        if (str != null) {
            bundle2.putString(APP_EVENT_PUSH_PARAMETER_ACTION, str);
        }
        logEvent(APP_EVENT_NAME_PUSH_OPENED, bundle2);
    }

    void logProductItem(String str, AppEventsLogger.ProductAvailability productAvailability, AppEventsLogger.ProductCondition productCondition, String str2, String str3, String str4, String str5, BigDecimal bigDecimal, Currency currency, String str6, String str7, String str8, Bundle bundle) {
        if (str == null) {
            notifyDeveloperError("itemID cannot be null");
            return;
        }
        if (productAvailability == null) {
            notifyDeveloperError("availability cannot be null");
            return;
        }
        if (productCondition == null) {
            notifyDeveloperError("condition cannot be null");
            return;
        }
        if (str2 == null) {
            notifyDeveloperError("description cannot be null");
            return;
        }
        if (str3 == null) {
            notifyDeveloperError("imageLink cannot be null");
            return;
        }
        if (str4 == null) {
            notifyDeveloperError("link cannot be null");
            return;
        }
        if (str5 == null) {
            notifyDeveloperError("title cannot be null");
            return;
        }
        if (bigDecimal == null) {
            notifyDeveloperError("priceAmount cannot be null");
            return;
        }
        if (currency == null) {
            notifyDeveloperError("currency cannot be null");
            return;
        }
        if (str6 == null && str7 == null && str8 == null) {
            notifyDeveloperError("Either gtin, mpn or brand is required");
            return;
        }
        if (bundle == null) {
            bundle = new Bundle();
        }
        bundle.putString(Constants.EVENT_PARAM_PRODUCT_ITEM_ID, str);
        bundle.putString(Constants.EVENT_PARAM_PRODUCT_AVAILABILITY, productAvailability.name());
        bundle.putString(Constants.EVENT_PARAM_PRODUCT_CONDITION, productCondition.name());
        bundle.putString(Constants.EVENT_PARAM_PRODUCT_DESCRIPTION, str2);
        bundle.putString(Constants.EVENT_PARAM_PRODUCT_IMAGE_LINK, str3);
        bundle.putString(Constants.EVENT_PARAM_PRODUCT_LINK, str4);
        bundle.putString(Constants.EVENT_PARAM_PRODUCT_TITLE, str5);
        bundle.putString(Constants.EVENT_PARAM_PRODUCT_PRICE_AMOUNT, bigDecimal.setScale(3, 4).toString());
        bundle.putString(Constants.EVENT_PARAM_PRODUCT_PRICE_CURRENCY, currency.getCurrencyCode());
        if (str6 != null) {
            bundle.putString(Constants.EVENT_PARAM_PRODUCT_GTIN, str6);
        }
        if (str7 != null) {
            bundle.putString(Constants.EVENT_PARAM_PRODUCT_MPN, str7);
        }
        if (str8 != null) {
            bundle.putString(Constants.EVENT_PARAM_PRODUCT_BRAND, str8);
        }
        logEvent(AppEventsConstants.EVENT_NAME_PRODUCT_CATALOG_UPDATE, bundle);
        eagerFlush();
    }

    void flush() {
        AppEventQueue.flush(FlushReason.EXPLICIT);
    }

    static void onContextStop() {
        AppEventQueue.persistToDisk();
    }

    boolean isValidForAccessToken(AccessToken accessToken) {
        return this.accessTokenAppId.equals(new AccessTokenAppIdPair(accessToken));
    }

    static void setPushNotificationsRegistrationId(String str) {
        synchronized (staticLock) {
            if (!Utility.stringsEqualOrEmpty(pushNotificationsRegistrationId, str)) {
                pushNotificationsRegistrationId = str;
                AppEventsLoggerImpl appEventsLoggerImpl = new AppEventsLoggerImpl(FacebookSdk.getApplicationContext(), (String) null, (AccessToken) null);
                appEventsLoggerImpl.logEvent(AppEventsConstants.EVENT_NAME_PUSH_TOKEN_OBTAINED);
                if (getFlushBehavior() != AppEventsLogger.FlushBehavior.EXPLICIT_ONLY) {
                    appEventsLoggerImpl.flush();
                }
            }
        }
    }

    static String getPushNotificationsRegistrationId() {
        String str;
        synchronized (staticLock) {
            str = pushNotificationsRegistrationId;
        }
        return str;
    }

    static void setInstallReferrer(String str) {
        SharedPreferences sharedPreferences = FacebookSdk.getApplicationContext().getSharedPreferences(APP_EVENT_PREFERENCES, 0);
        if (str != null) {
            sharedPreferences.edit().putString("install_referrer", str).apply();
        }
    }

    static String getInstallReferrer() {
        return FacebookSdk.getApplicationContext().getSharedPreferences(APP_EVENT_PREFERENCES, 0).getString("install_referrer", null);
    }

    static void augmentWebView(WebView webView, Context context) {
        String[] strArrSplit = Build.VERSION.RELEASE.split("\\.");
        int i = strArrSplit.length > 0 ? Integer.parseInt(strArrSplit[0]) : 0;
        int i2 = strArrSplit.length > 1 ? Integer.parseInt(strArrSplit[1]) : 0;
        if (Build.VERSION.SDK_INT < 17 || i < 4 || (i == 4 && i2 <= 1)) {
            Logger.log(LoggingBehavior.DEVELOPER_ERRORS, TAG, "augmentWebView is only available for Android SDK version >= 17 on devices running Android >= 4.2");
        } else {
            webView.addJavascriptInterface(new FacebookSDKJSInterface(context), "fbmq_" + FacebookSdk.getApplicationId());
        }
    }

    static void updateUserProperties(final Bundle bundle, final String str, final GraphRequest.Callback callback) {
        getAnalyticsExecutor().execute(new Runnable() { // from class: com.facebook.appevents.AppEventsLoggerImpl.2
            @Override // java.lang.Runnable
            public void run() {
                String userID = AnalyticsUserIDStore.getUserID();
                if (userID == null || userID.isEmpty()) {
                    Logger.log(LoggingBehavior.APP_EVENTS, AppEventsLoggerImpl.TAG, "AppEventsLogger userID cannot be null or empty");
                    return;
                }
                Bundle bundle2 = new Bundle();
                bundle2.putString("user_unique_id", userID);
                bundle2.putBundle("custom_data", bundle);
                AttributionIdentifiers attributionIdentifiers = AttributionIdentifiers.getAttributionIdentifiers(FacebookSdk.getApplicationContext());
                if (attributionIdentifiers != null && attributionIdentifiers.getAndroidAdvertiserId() != null) {
                    bundle2.putString("advertiser_id", attributionIdentifiers.getAndroidAdvertiserId());
                }
                Bundle bundle3 = new Bundle();
                try {
                    JSONObject jSONObjectConvertToJSON = BundleJSONConverter.convertToJSON(bundle2);
                    JSONArray jSONArray = new JSONArray();
                    jSONArray.put(jSONObjectConvertToJSON);
                    bundle3.putString(ShareConstants.WEB_DIALOG_PARAM_DATA, jSONArray.toString());
                    GraphRequest graphRequest = new GraphRequest(AccessToken.getCurrentAccessToken(), String.format(Locale.US, "%s/user_properties", str), bundle3, HttpMethod.POST, callback);
                    graphRequest.setSkipClientToken(true);
                    graphRequest.executeAsync();
                } catch (JSONException e) {
                    throw new FacebookException("Failed to construct request", e);
                }
            }
        });
    }

    void logSdkEvent(String str, Double d, Bundle bundle) {
        if (!str.startsWith(ACCOUNT_KIT_EVENT_NAME_PREFIX)) {
            String str2 = TAG;
        } else if (FacebookSdk.getAutoLogAppEventsEnabled()) {
            logEvent(str, d, bundle, true, ActivityLifecycleTracker.getCurrentSessionGuid());
        }
    }

    public String getApplicationId() {
        return this.accessTokenAppId.getApplicationId();
    }

    AppEventsLoggerImpl(Context context, String str, AccessToken accessToken) {
        this(Utility.getActivityName(context), str, accessToken);
    }

    AppEventsLoggerImpl(String str, String str2, AccessToken accessToken) {
        Validate.sdkInitialized();
        this.contextName = str;
        accessToken = accessToken == null ? AccessToken.getCurrentAccessToken() : accessToken;
        if (AccessToken.isCurrentAccessTokenActive() && (str2 == null || str2.equals(accessToken.getApplicationId()))) {
            this.accessTokenAppId = new AccessTokenAppIdPair(accessToken);
        } else {
            this.accessTokenAppId = new AccessTokenAppIdPair(null, str2 == null ? Utility.getMetadataApplicationId(FacebookSdk.getApplicationContext()) : str2);
        }
        initializeTimersIfNeeded();
    }

    private static void initializeTimersIfNeeded() {
        synchronized (staticLock) {
            if (backgroundExecutor == null) {
                backgroundExecutor = new ScheduledThreadPoolExecutor(1);
                backgroundExecutor.scheduleAtFixedRate(new Runnable() { // from class: com.facebook.appevents.AppEventsLoggerImpl.3
                    @Override // java.lang.Runnable
                    public void run() {
                        HashSet hashSet = new HashSet();
                        Iterator<AccessTokenAppIdPair> it = AppEventQueue.getKeySet().iterator();
                        while (it.hasNext()) {
                            hashSet.add(it.next().getApplicationId());
                        }
                        Iterator it2 = hashSet.iterator();
                        while (it2.hasNext()) {
                            FetchedAppSettingsManager.queryAppSettings((String) it2.next(), true);
                        }
                    }
                }, 0L, 86400L, TimeUnit.SECONDS);
            }
        }
    }

    void logEventImplicitly(String str, Double d, Bundle bundle) {
        logEvent(str, d, bundle, true, ActivityLifecycleTracker.getCurrentSessionGuid());
    }

    void logEventImplicitly(String str, BigDecimal bigDecimal, Currency currency, Bundle bundle) {
        if (bigDecimal == null || currency == null) {
            Utility.logd(TAG, "purchaseAmount and currency cannot be null");
            return;
        }
        Bundle bundle2 = bundle == null ? new Bundle() : bundle;
        bundle2.putString(AppEventsConstants.EVENT_PARAM_CURRENCY, currency.getCurrencyCode());
        logEvent(str, Double.valueOf(bigDecimal.doubleValue()), bundle2, true, ActivityLifecycleTracker.getCurrentSessionGuid());
    }

    void logEvent(String str, Double d, Bundle bundle, boolean z, UUID uuid) {
        if (str != null && !str.isEmpty()) {
            if (FetchedAppGateKeepersManager.getGateKeeperForKey(APP_EVENTS_KILLSWITCH, FacebookSdk.getApplicationId(), false)) {
                Logger.log(LoggingBehavior.APP_EVENTS, "AppEvents", "KillSwitch is enabled and fail to log app event: %s", str);
                return;
            }
            try {
                logEvent(new AppEvent(this.contextName, str, d, bundle, z, ActivityLifecycleTracker.isInBackground(), uuid), this.accessTokenAppId);
            } catch (FacebookException e) {
                Logger.log(LoggingBehavior.APP_EVENTS, "AppEvents", "Invalid app event: %s", e.toString());
            } catch (JSONException e2) {
                Logger.log(LoggingBehavior.APP_EVENTS, "AppEvents", "JSON encoding for app event failed: '%s'", e2.toString());
            }
        }
    }

    private static void logEvent(AppEvent appEvent, AccessTokenAppIdPair accessTokenAppIdPair) {
        AppEventQueue.add(accessTokenAppIdPair, appEvent);
        if (!appEvent.getIsImplicit() && !isActivateAppEventRequested) {
            if (appEvent.getName().equals(AppEventsConstants.EVENT_NAME_ACTIVATED_APP)) {
                isActivateAppEventRequested = true;
            } else {
                Logger.log(LoggingBehavior.APP_EVENTS, "AppEvents", "Warning: Please call AppEventsLogger.activateApp(...)from the long-lived activity's onResume() methodbefore logging other app events.");
            }
        }
    }

    static void eagerFlush() {
        if (getFlushBehavior() != AppEventsLogger.FlushBehavior.EXPLICIT_ONLY) {
            AppEventQueue.flush(FlushReason.EAGER_FLUSHING_EVENT);
        }
    }

    private static void notifyDeveloperError(String str) {
        Logger.log(LoggingBehavior.DEVELOPER_ERRORS, "AppEvents", str);
    }

    static Executor getAnalyticsExecutor() {
        if (backgroundExecutor == null) {
            initializeTimersIfNeeded();
        }
        return backgroundExecutor;
    }

    static String getAnonymousAppDeviceGUID(Context context) {
        if (anonymousAppDeviceGUID == null) {
            synchronized (staticLock) {
                if (anonymousAppDeviceGUID == null) {
                    anonymousAppDeviceGUID = context.getSharedPreferences(APP_EVENT_PREFERENCES, 0).getString("anonymousAppDeviceGUID", null);
                    if (anonymousAppDeviceGUID == null) {
                        anonymousAppDeviceGUID = "XZ" + UUID.randomUUID().toString();
                        context.getSharedPreferences(APP_EVENT_PREFERENCES, 0).edit().putString("anonymousAppDeviceGUID", anonymousAppDeviceGUID).apply();
                    }
                }
            }
        }
        return anonymousAppDeviceGUID;
    }
}
