package com.gameloft.adsmanager;

import android.graphics.Point;
import android.graphics.Typeface;
import android.os.Build;
import android.util.DisplayMetrics;
import android.view.Display;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewManager;
import android.view.ViewParent;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import com.facebook.share.internal.ShareConstants;
import java.io.PrintWriter;
import java.io.StringWriter;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

/* JADX INFO: loaded from: classes.dex */
public class JavaUtils {
    static ViewGroup.LayoutParams bannerLayoutParams = null;
    static RelativeLayout.LayoutParams nativeLayoutParams = null;
    static View nativeAdView = null;
    static boolean isPhone = true;
    static int banner_width = 0;
    static int banner_height = 0;
    static int _positionX = 0;
    static int _positionY = 0;
    static int _anchor = 3;
    static float density = 1.0f;
    static int _native_pos_x = 0;
    static int _native_pos_y = 0;
    static int _native_size_x = 0;
    static int _native_size_y = 0;
    static String _native_layout_name = null;
    static int displayWidthPx = 0;
    static int displayHeightPx = 0;
    static final int displayDpiRef = 160;
    static int displayDpi = displayDpiRef;
    static float realScreenHeightDpi = 1.0f;
    static float realScreenWidthDpi = 1.0f;
    static float zoomFactor = 1.0f;
    static ImageView nativeAdIcon = null;
    static FrameLayout nativeAdFavIcon = null;
    static TextView nativeAdTitle = null;
    static TextView nativeAdBody = null;
    static TextView nativeAdSocialContext = null;
    static Button nativeAdCallToAction = null;
    static View nativeMediaView = null;
    static Button closeAdButton = null;
    static boolean hasCloseButton = false;
    static Typeface customFont = null;

    public class Constants {
        public static final int BANNER_HEIGHT_DP = 50;
        public static final int BANNER_WIDTH_DP = 320;
        public static final int NATIVE_HEIGHT_DP_100 = 100;
        public static final int NATIVE_HEIGHT_DP_300 = 300;
        public static final int TABLET_HEIGHT_DP = 90;
        public static final int TABLET_WIDTH_DP = 728;

        public class Anchor {
            public static final int AD_BOTTOM_CENTER = 3;
            public static final int AD_BOTTOM_LEFT = 4;
            public static final int AD_BOTTOM_RIGHT = 5;
            public static final int AD_CENTER = 8;
            public static final int AD_MIDDLE_LEFT = 7;
            public static final int AD_MIDDLE_RIGHT = 6;
            public static final int AD_TOP_CENTER = 2;
            public static final int AD_TOP_LEFT = 0;
            public static final int AD_TOP_RIGHT = 1;
        }
    }

    public class Errors {
        public static final int ERROR_EXCEPTION_RAISED = 8888;
        public static final int ERROR_NO_AD_AVAILABLE = 9996;
        public static final int ERROR_PROGRAMATIC_SHOW = 9997;
        public static final int ERROR_UNKNOWN = 9991;
    }

    public static native void AdsManagerLog(String str, String str2, String str3);

    public static native void AdsManagerLogError(String str, String str2, String str3);

    public static native void AdsManagerLogInfo(String str, String str2, String str3);

    public static native void AdsManagerLogWarning(String str, String str2, String str3);

    public class PlacementState {
        private Map<String, Boolean> placementMap;

        public PlacementState(String[] strArr) {
            synchronized (this) {
                this.placementMap = new HashMap();
                for (int i = 0; i < strArr.length; i++) {
                    if (strArr[i] != null) {
                        this.placementMap.put(strArr[i], true);
                    }
                }
            }
        }

        public synchronized void PushPlacement(String str) {
            if (str != null) {
                this.placementMap.put(str, true);
            }
        }

        public synchronized String PopValidPlacement() {
            String next;
            Iterator<String> it = this.placementMap.keySet().iterator();
            while (it.hasNext()) {
                next = it.next();
                if (this.placementMap.get(next).booleanValue()) {
                    this.placementMap.put(next, false);
                    if (next == null) {
                        next = "";
                    }
                }
            }
            next = "";
            return next;
        }

        public synchronized boolean PopSpecificPlacement(String str) {
            boolean z;
            if (this.placementMap.containsKey(str) && this.placementMap.get(str).booleanValue()) {
                this.placementMap.put(str, false);
                z = true;
            } else {
                z = false;
            }
            return z;
        }

        public synchronized boolean HasPlacement(String str) {
            return this.placementMap.containsKey(str);
        }
    }

    public static void LogException(String str, String str2, Throwable th) {
        if (th != null) {
            StringWriter stringWriter = new StringWriter();
            new PrintWriter(stringWriter);
            AdsManagerLogError(str, str2, "Exception message=(" + th.getLocalizedMessage() + ") stackTrace = (" + stringWriter.toString() + ")");
        }
    }

    public static void PostAndLogException(ViewGroup viewGroup, Runnable runnable) {
        PostAndLogException(viewGroup, runnable, null);
    }

    public static void PostAndLogException(ViewGroup viewGroup, Runnable runnable, Runnable runnable2) {
        if (viewGroup == null) {
            JavaLoggingDefines.LOG_ERROR("JavaUtils", "PostAndLogExcepions", "Invalid parentViewGroup");
            if (runnable2 != null) {
                runnable2.run();
            }
        }
        viewGroup.post(new c(runnable, runnable2));
    }

    public static void Init() {
        if (AdsManager.mainActivity != null) {
            Display defaultDisplay = AdsManager.mainActivity.getWindowManager().getDefaultDisplay();
            DisplayMetrics displayMetrics = new DisplayMetrics();
            defaultDisplay.getMetrics(displayMetrics);
            density = displayMetrics.density;
            displayDpi = displayMetrics.densityDpi;
            if (Build.VERSION.SDK_INT >= 19) {
                Point point = new Point();
                defaultDisplay.getRealSize(point);
                displayWidthPx = point.x;
                displayHeightPx = point.y;
            } else {
                displayWidthPx = defaultDisplay.getWidth();
                displayHeightPx = defaultDisplay.getHeight();
            }
            float f = displayWidthPx / displayMetrics.xdpi;
            float f2 = displayHeightPx / displayMetrics.ydpi;
            isPhone = (f * f) + (f2 * f2) < 48.0f;
            realScreenWidthDpi = f * 160.0f;
            realScreenHeightDpi = f2 * 160.0f;
            if (AdsManager.mainActivity.getResources().getConfiguration().orientation == 2) {
                zoomFactor = AdsManager.mainActivity.getResources().getConfiguration().screenHeightDp / realScreenHeightDpi;
            } else {
                zoomFactor = AdsManager.mainActivity.getResources().getConfiguration().screenWidthDp / realScreenWidthDpi;
            }
            JavaLoggingDefines.LOG_INFO("JavaUtils", "Init", String.format("DisplayMetrics: density = (%f), densityDpi = (%d), widthPixels = (%d), heightPixels = (%d), xdpi = (%f), ydpi = (%f)", Float.valueOf(displayMetrics.density), Integer.valueOf(displayDpi), Integer.valueOf(displayMetrics.widthPixels), Integer.valueOf(displayMetrics.heightPixels), Float.valueOf(displayMetrics.xdpi), Float.valueOf(displayMetrics.ydpi)));
        }
        SetupLayoutParams();
        SetupNativeLayoutParams();
    }

    private static void ResetNativeSettings() {
        _native_pos_x = 0;
        _native_pos_y = 0;
        _native_size_x = 0;
        _native_size_y = 0;
        _native_layout_name = "";
        nativeAdIcon = null;
        nativeAdFavIcon = null;
        nativeAdTitle = null;
        nativeAdBody = null;
        nativeAdSocialContext = null;
        nativeAdCallToAction = null;
        nativeMediaView = null;
        closeAdButton = null;
        hasCloseButton = false;
    }

    public static void ChangeNativePosition(int i, int i2) {
        _native_pos_x = i;
        _native_pos_y = i2;
        SetupNativeLayoutParams();
    }

    public static boolean SetNativeSettings(int i, int i2, int i3, int i4, String str, String str2) {
        ResetNativeSettings();
        _native_pos_x = i3;
        _native_pos_y = i4;
        _native_size_x = i;
        _native_size_y = i2;
        _native_layout_name = str;
        if (str2 != "") {
            try {
                customFont = Typeface.createFromAsset(AdsManager.mainActivity.getResources().getAssets(), str2);
            } catch (Exception e) {
                JavaLoggingDefines.LOG_INFO("JavaUtils", "SetNativeSettings", "Custom font loading failed using createFromAsset");
                try {
                    customFont = Typeface.createFromFile(str2);
                } catch (Exception e2) {
                    JavaLoggingDefines.LOG_ERROR("JavaUtils", "SetNativeSettings", "Custom font loading failed using createFromFile");
                }
            }
        }
        int identifier = AdsManager.mainActivity.getResources().getIdentifier(_native_layout_name, "layout", AdsManager.mainActivity.getPackageName());
        if (identifier != 0) {
            nativeAdView = AdsManager.mainActivity.getLayoutInflater().inflate(identifier, AdsManager.parentViewGroup, false);
            int identifier2 = AdsManager.mainActivity.getResources().getIdentifier("native_ad_icon", ShareConstants.WEB_DIALOG_PARAM_ID, AdsManager.mainActivity.getPackageName());
            int identifier3 = AdsManager.mainActivity.getResources().getIdentifier("native_ad_small_icon", ShareConstants.WEB_DIALOG_PARAM_ID, AdsManager.mainActivity.getPackageName());
            int identifier4 = AdsManager.mainActivity.getResources().getIdentifier("native_ad_title", ShareConstants.WEB_DIALOG_PARAM_ID, AdsManager.mainActivity.getPackageName());
            int identifier5 = AdsManager.mainActivity.getResources().getIdentifier("native_ad_body", ShareConstants.WEB_DIALOG_PARAM_ID, AdsManager.mainActivity.getPackageName());
            int identifier6 = AdsManager.mainActivity.getResources().getIdentifier("native_ad_social_context", ShareConstants.WEB_DIALOG_PARAM_ID, AdsManager.mainActivity.getPackageName());
            int identifier7 = AdsManager.mainActivity.getResources().getIdentifier("native_ad_call_to_action", ShareConstants.WEB_DIALOG_PARAM_ID, AdsManager.mainActivity.getPackageName());
            int identifier8 = AdsManager.mainActivity.getResources().getIdentifier("native_ad_media", ShareConstants.WEB_DIALOG_PARAM_ID, AdsManager.mainActivity.getPackageName());
            int identifier9 = AdsManager.mainActivity.getResources().getIdentifier("close_button", ShareConstants.WEB_DIALOG_PARAM_ID, AdsManager.mainActivity.getPackageName());
            if (identifier2 != 0) {
                nativeAdIcon = (ImageView) nativeAdView.findViewById(identifier2);
            }
            if (identifier3 != 0) {
                nativeAdFavIcon = (FrameLayout) nativeAdView.findViewById(identifier3);
            }
            if (identifier4 != 0) {
                nativeAdTitle = (TextView) nativeAdView.findViewById(identifier4);
                nativeAdTitle.setTypeface(customFont);
            }
            if (identifier5 != 0) {
                nativeAdBody = (TextView) nativeAdView.findViewById(identifier5);
                nativeAdBody.setTypeface(customFont);
            }
            if (identifier6 != 0) {
                nativeAdSocialContext = (TextView) nativeAdView.findViewById(identifier6);
            }
            if (identifier7 != 0) {
                nativeAdCallToAction = (Button) nativeAdView.findViewById(identifier7);
                nativeAdCallToAction.setTypeface(customFont);
            }
            if (identifier8 != 0) {
                nativeMediaView = nativeAdView.findViewById(identifier8);
            }
            if (identifier9 != 0) {
                closeAdButton = (Button) nativeAdView.findViewById(identifier9);
                if (closeAdButton != null) {
                    hasCloseButton = true;
                }
            }
            SetupNativeLayoutParams();
            return true;
        }
        JavaLoggingDefines.LOG_INFO("JavaUtils", "SetNativeSetting", String.format("Layout %s was not found. Check AdsManager_config", _native_layout_name));
        return false;
    }

    public static void SetupNativeLayoutParams() {
        nativeLayoutParams = new RelativeLayout.LayoutParams(_native_size_x, _native_size_y);
        nativeLayoutParams.addRule(10);
        nativeLayoutParams.addRule(9);
        nativeLayoutParams.setMargins(Math.min(_native_pos_x, displayWidthPx - _native_size_x), Math.min(_native_pos_y, displayHeightPx - _native_size_y), 0, 0);
    }

    public static void SetBannerPositionAndAnchor(int i, int i2, int i3) {
        _positionX = i;
        _positionY = i2;
        _anchor = i3;
        SetupLayoutParams();
    }

    public static void SetupLayoutParams() {
        SetupBannerDimensions();
        int[] iArr = {14, 12};
        switch (_anchor) {
            case 0:
                iArr[0] = 10;
                iArr[1] = 9;
                break;
            case 1:
                iArr[0] = 10;
                iArr[1] = 11;
                break;
            case 2:
                iArr[0] = 10;
                iArr[1] = 14;
                break;
            case 3:
                iArr[0] = 14;
                iArr[1] = 12;
                break;
            case 4:
                iArr[0] = 9;
                iArr[1] = 12;
                break;
            case 5:
                iArr[0] = 11;
                iArr[1] = 12;
                break;
            case 6:
                iArr[0] = 15;
                iArr[1] = 11;
                break;
            case 7:
                iArr[0] = 15;
                iArr[1] = 9;
                break;
            case 8:
                iArr[0] = 15;
                iArr[1] = 14;
                break;
        }
        bannerLayoutParams = new RelativeLayout.LayoutParams(banner_width, banner_height);
        ((RelativeLayout.LayoutParams) bannerLayoutParams).addRule(iArr[0]);
        ((RelativeLayout.LayoutParams) bannerLayoutParams).addRule(iArr[1]);
        ((RelativeLayout.LayoutParams) bannerLayoutParams).setMargins(_positionX >= 0 ? _positionX : 0, _positionY >= 0 ? _positionY : 0, _positionX < 0 ? -_positionX : 0, _positionY < 0 ? -_positionY : 0);
    }

    public static void ApplyCorrection(View view) {
        if (Build.VERSION.SDK_INT >= 23) {
            view.setScaleX(zoomFactor);
            view.setScaleY(zoomFactor);
            float f = ((1.0f - zoomFactor) * banner_width) / 2.0f;
            float f2 = ((1.0f - zoomFactor) * banner_height) / 2.0f;
            switch (_anchor) {
                case 0:
                    view.setTranslationX(-f);
                    view.setTranslationY(-f2);
                    break;
                case 1:
                    view.setTranslationX(f);
                    view.setTranslationY(-f2);
                    break;
                case 2:
                    view.setTranslationX(0.0f);
                    view.setTranslationY(-f2);
                    break;
                case 3:
                    view.setTranslationX(0.0f);
                    view.setTranslationY(f2);
                    break;
                case 4:
                    view.setTranslationX(-f);
                    view.setTranslationY(f2);
                    break;
                case 5:
                    view.setTranslationX(f);
                    view.setTranslationY(f2);
                    break;
                case 6:
                    view.setTranslationX(f);
                    view.setTranslationY(0.0f);
                    break;
                case 7:
                    view.setTranslationX(-f);
                    view.setTranslationY(0.0f);
                    break;
                case 8:
                    view.setTranslationX(0.0f);
                    view.setTranslationY(0.0f);
                    break;
            }
        }
    }

    public static void SetupBannerDimensions() {
        if (isPhone) {
            banner_width = (int) (density * 320.0f);
            banner_height = (int) (density * 50.0f);
        } else {
            banner_width = (int) (density * 728.0f);
            banner_height = (int) (density * 90.0f);
        }
        JavaLoggingDefines.LOG_INFO("JavaUtils", "SetupBannerDimensions", " width=(" + banner_width + "), height=(" + banner_height + ")");
    }

    public static void RemoveViewFromParent(View view) {
        ViewParent parent = view.getParent();
        if (parent instanceof ViewManager) {
            ((ViewManager) parent).removeView(view);
        }
    }

    public static int GetBannerWidth() {
        if (banner_width == 0) {
            SetupBannerDimensions();
        }
        return banner_width;
    }

    public static int GetBannerHeight() {
        if (banner_height == 0) {
            SetupBannerDimensions();
        }
        return banner_height;
    }

    public static int DipToPx(int i) {
        return (displayDpi * i) / displayDpiRef;
    }

    public static boolean LogErrorIfClassIsMissing(String str) {
        try {
            return Class.forName(str) != null;
        } catch (ClassNotFoundException e) {
            JavaLoggingDefines.LOG_ERROR("JavaUtils", "LogErrorIfClassIsMissing", "class = (" + str + ")");
            LogException("LogErrorIfClassIsMissing", "LogErrorIfClassIsMissing", e);
            return false;
        } catch (Throwable th) {
            JavaLoggingDefines.LOG_ERROR("JavaUtils", "LogErrorIfClassIsMissing", "class = (" + str + ")");
            LogException("LogErrorIfClassIsMissing", "LogErrorIfClassIsMissing", th);
            return false;
        }
    }
}
