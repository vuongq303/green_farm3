package com.unity3d.services.banners.view;

import android.content.Context;
import android.graphics.Rect;
import android.os.Build;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewParent;
import android.widget.RelativeLayout;
import com.unity3d.services.ads.webplayer.WebPlayer;
import com.unity3d.services.core.log.DeviceLog;
import com.unity3d.services.core.misc.ViewUtilities;
import com.unity3d.services.core.properties.ClientProperties;
import com.unity3d.services.core.webview.WebViewApp;
import com.unity3d.services.core.webview.WebViewEventCategory;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import org.json.JSONObject;

/* JADX INFO: loaded from: classes.dex */
public class BannerView extends RelativeLayout {
    private static final String VIEW_BANNER = "banner";
    private static final String VIEW_BANNER_PLAYER = "bannerplayer";
    private static final String VIEW_WEB_VIEW = "webview";
    private static BannerView _instance;
    private int _lastVisibility;
    private List<String> _views;
    private WebPlayer _webPlayer;
    private int height;
    private BannerPosition position;
    private int width;
    private static JSONObject _webSettings = new JSONObject();
    private static JSONObject _webPlayerSettings = new JSONObject();
    private static JSONObject _webPlayerEventSettings = new JSONObject();

    public BannerView(Context context) {
        super(context);
        this._lastVisibility = -1;
        this._webPlayer = new WebPlayer(context, VIEW_BANNER_PLAYER, _webSettings, _webPlayerSettings);
        this._webPlayer.setEventSettings(_webPlayerEventSettings);
        if (Build.VERSION.SDK_INT >= 11) {
            addOnLayoutChangeListener(new View.OnLayoutChangeListener() { // from class: com.unity3d.services.banners.view.BannerView.1
                @Override // android.view.View.OnLayoutChangeListener
                public void onLayoutChange(View view, int i, int i2, int i3, int i4, int i5, int i6, int i7, int i8) {
                    BannerView.this.onLayoutChange(view, i, i2, i3, i4, i5, i6, i7, i8);
                }
            });
        }
    }

    public static void setWebPlayerEventSettings(JSONObject jSONObject) {
        _webPlayerEventSettings = jSONObject;
    }

    public static void setWebPlayerSettings(JSONObject jSONObject, JSONObject jSONObject2) {
        _webSettings = jSONObject;
        _webPlayerSettings = jSONObject2;
    }

    public void destroy() {
        removeAllViews();
        ViewParent parent = getParent();
        if (parent != null && (parent instanceof ViewGroup)) {
            ((ViewGroup) parent).removeView(this);
        }
        this._webPlayer = null;
        _instance = null;
    }

    public static BannerView getOrCreateInstance() {
        if (_instance == null) {
            _instance = new BannerView(ClientProperties.getApplicationContext());
        }
        return _instance;
    }

    public static BannerView getInstance() {
        return _instance;
    }

    public void setViews(List<String> list) {
        ArrayList arrayList = new ArrayList(list);
        ArrayList arrayList2 = new ArrayList();
        if (this._views != null) {
            arrayList2.addAll(this._views);
            arrayList2.removeAll(list);
            arrayList.removeAll(this._views);
        }
        this._views = list;
        Iterator it = arrayList2.iterator();
        while (it.hasNext()) {
            removeView((String) it.next());
        }
        Iterator it2 = arrayList.iterator();
        while (it2.hasNext()) {
            addView((String) it2.next());
        }
    }

    private void removeView(String str) {
        View viewForName = getViewForName(str);
        if (viewForName != null) {
            ViewUtilities.removeViewFromParent(viewForName);
        }
        switch (str) {
            case "bannerplayer":
                this._webPlayer = null;
                break;
        }
    }

    private void addView(String str) {
        View viewForName = getViewForName(str);
        if (viewForName != null) {
            addView(viewForName, new RelativeLayout.LayoutParams(-1, -1));
        } else {
            DeviceLog.warning("No view defined for viewName: %s", str);
        }
    }

    public WebPlayer getWebPlayer() {
        return this._webPlayer;
    }

    @Override // android.view.View
    public void setLayoutParams(ViewGroup.LayoutParams layoutParams) {
        if (layoutParams != null) {
            layoutParams.width = this.width;
            layoutParams.height = this.height;
            super.setLayoutParams(LayoutParamsHelper.updateLayoutParamsForPosition(layoutParams, this.position));
        }
    }

    public void setViewFrame(String str, Integer num, Integer num2, Integer num3, Integer num4) {
        View viewForName = getViewForName(str);
        if (viewForName != null) {
            if (viewForName == this) {
                DeviceLog.warning("Not setting viewFrame for banner, use `setLayoutParams` instead.");
                return;
            }
            RelativeLayout.LayoutParams layoutParams = new RelativeLayout.LayoutParams(num3.intValue(), num4.intValue());
            layoutParams.setMargins(num.intValue(), num2.intValue(), 0, 0);
            viewForName.setLayoutParams(layoutParams);
        }
    }

    @Override // android.view.ViewGroup, android.view.View
    protected void onAttachedToWindow() {
        super.onAttachedToWindow();
        WebViewApp currentApp = WebViewApp.getCurrentApp();
        if (currentApp != null) {
            currentApp.sendEvent(WebViewEventCategory.BANNER, BannerEvent.BANNER_ATTACHED, new Object[0]);
        }
    }

    @Override // android.view.ViewGroup, android.view.View
    protected void onDetachedFromWindow() {
        super.onDetachedFromWindow();
        WebViewApp currentApp = WebViewApp.getCurrentApp();
        if (currentApp != null) {
            currentApp.sendEvent(WebViewEventCategory.BANNER, BannerEvent.BANNER_DETACHED, new Object[0]);
        }
    }

    @Override // android.view.View
    protected void onSizeChanged(int i, int i2, int i3, int i4) {
        if (i3 != 0 || i4 != 0) {
            int left = getLeft();
            int right = getRight();
            float alpha = 1.0f;
            if (Build.VERSION.SDK_INT >= 11) {
                alpha = getAlpha();
            }
            WebViewApp.getCurrentApp().sendEvent(WebViewEventCategory.BANNER, BannerEvent.BANNER_RESIZED, Integer.valueOf(left), Integer.valueOf(right), Integer.valueOf(i), Integer.valueOf(i2), Float.valueOf(alpha));
            Rect rect = new Rect();
            getHitRect(rect);
            if (((View) getParent()).getLocalVisibleRect(rect)) {
                onVisibilityChanged(this, 8);
            }
        }
    }

    @Override // android.view.View
    protected void onVisibilityChanged(View view, int i) {
        if (view == this) {
            if (this._lastVisibility == -1) {
                this._lastVisibility = i;
                return;
            }
            if (i != 0 && this._lastVisibility == 0) {
                WebViewApp.getCurrentApp().sendEvent(WebViewEventCategory.BANNER, BannerEvent.BANNER_VISIBILITY_CHANGED, Integer.valueOf(i));
            }
            this._lastVisibility = i;
        }
    }

    private View getViewForName(String str) {
        if (str.equals(VIEW_BANNER_PLAYER)) {
            return this._webPlayer;
        }
        if (str.equals(VIEW_WEB_VIEW)) {
            return WebViewApp.getCurrentApp().getWebView();
        }
        if (!str.equals(VIEW_BANNER)) {
            return null;
        }
        return this;
    }

    public void onLayoutChange(View view, int i, int i2, int i3, int i4, int i5, int i6, int i7, int i8) {
        float alpha = 1.0f;
        if (Build.VERSION.SDK_INT >= 11) {
            alpha = getAlpha();
        }
        WebViewApp.getCurrentApp().sendEvent(WebViewEventCategory.BANNER, BannerEvent.BANNER_RESIZED, Integer.valueOf(i), Integer.valueOf(i2), Integer.valueOf(i3), Integer.valueOf(i4), Float.valueOf(alpha));
        if (getParent() != null) {
            Rect rect = new Rect();
            getHitRect(rect);
            if ((getParent() instanceof View) && !((View) getParent()).getLocalVisibleRect(rect)) {
                onVisibilityChanged(this, 8);
            }
        }
    }

    @Override // android.view.View
    public void setAlpha(float f) {
        super.setAlpha(f);
        onLayoutChange(this, getLeft(), getTop(), getRight(), getBottom(), getLeft(), getTop(), getRight(), getBottom());
    }

    public void setBannerDimensions(int i, int i2, BannerPosition bannerPosition) {
        this.width = i;
        this.height = i2;
        this.position = bannerPosition;
    }
}
