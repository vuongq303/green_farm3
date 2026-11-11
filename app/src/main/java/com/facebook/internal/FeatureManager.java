package com.facebook.internal;

import com.facebook.FacebookSdk;
import com.facebook.R;
import com.google.android.gms.ads.AdRequest;

/* JADX INFO: loaded from: classes.dex */
public final class FeatureManager {

    public interface Callback {
        void onCompleted(boolean z);
    }

    public static void checkFeature(final Feature feature, final Callback callback) {
        FetchedAppGateKeepersManager.loadAppGateKeepersAsync(new FetchedAppGateKeepersManager.Callback() { // from class: com.facebook.internal.FeatureManager.1
            @Override // com.facebook.internal.FetchedAppGateKeepersManager.Callback
            public void onCompleted() {
                callback.onCompleted(FeatureManager.isEnabled(feature));
            }
        });
    }

    public static boolean isEnabled(Feature feature) {
        if (Feature.Unknown == feature) {
            return false;
        }
        if (Feature.Core == feature) {
            return true;
        }
        Feature parent = feature.getParent();
        if (parent == feature) {
            return getGKStatus(feature);
        }
        return isEnabled(parent) && getGKStatus(feature);
    }

    private static boolean getGKStatus(Feature feature) {
        return FetchedAppGateKeepersManager.getGateKeeperForKey("FBSDKFeature" + feature.toString(), FacebookSdk.getApplicationId(), defaultStatus(feature));
    }

    private static boolean defaultStatus(Feature feature) {
        switch (feature) {
            case RestrictiveDataFiltering:
            case Instrument:
            case CrashReport:
            case ErrorReport:
                return false;
            default:
                return true;
        }
    }

    public enum Feature {
        Unknown(-1),
        Core(0),
        AppEvents(256),
        CodelessEvents(257),
        RestrictiveDataFiltering(258),
        Instrument(AdRequest.MAX_CONTENT_URL_LENGTH),
        CrashReport(513),
        ErrorReport(514),
        Login(NativeProtocol.MESSAGE_GET_ACCESS_TOKEN_REQUEST),
        Share(131072),
        Places(196608);

        private final int code;

        Feature(int i) {
            this.code = i;
        }

        @Override // java.lang.Enum
        public String toString() {
            switch (AnonymousClass2.$SwitchMap$com$facebook$internal$FeatureManager$Feature[ordinal()]) {
                case 1:
                    return "RestrictiveDataFiltering";
                case 2:
                    return "Instrument";
                case 3:
                    return "CrashReport";
                case 4:
                    return "ErrorReport";
                case 5:
                    return "CoreKit";
                case 6:
                    return "AppEvents";
                case 7:
                    return "CodelessEvents";
                case 8:
                    return "LoginKit";
                case 9:
                    return "ShareKit";
                case R.styleable.CardView_contentPaddingRight /* 10 */:
                    return "PlacesKit";
                default:
                    return "unknown";
            }
        }

        static Feature fromInt(int i) {
            for (Feature feature : values()) {
                if (feature.code == i) {
                    return feature;
                }
            }
            return Unknown;
        }

        public Feature getParent() {
            if ((this.code & 255) > 0) {
                return fromInt(this.code & 16776960);
            }
            if ((this.code & 65280) > 0) {
                return fromInt(this.code & 16711680);
            }
            return fromInt(0);
        }
    }
}
