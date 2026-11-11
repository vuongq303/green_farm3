package android.support.v4.view.a;

import android.graphics.Rect;
import android.os.Build;
import android.view.accessibility.AccessibilityNodeInfo;
import com.facebook.internal.NativeProtocol;
import com.facebook.internal.Utility;
import com.gameloft.glads.GLURLConnection;
import com.google.android.gms.ads.AdRequest;

/* JADX INFO: compiled from: AccessibilityNodeInfoCompat.java */
/* JADX INFO: loaded from: classes.dex */
public class a {
    static final j hK;
    private final AccessibilityNodeInfo hL;
    public int hM = -1;

    static {
        if (Build.VERSION.SDK_INT >= 24) {
            hK = new i();
            return;
        }
        if (Build.VERSION.SDK_INT >= 23) {
            hK = new h();
            return;
        }
        if (Build.VERSION.SDK_INT >= 22) {
            hK = new g();
            return;
        }
        if (Build.VERSION.SDK_INT >= 21) {
            hK = new f();
            return;
        }
        if (Build.VERSION.SDK_INT >= 19) {
            hK = new e();
            return;
        }
        if (Build.VERSION.SDK_INT >= 18) {
            hK = new d();
            return;
        }
        if (Build.VERSION.SDK_INT >= 17) {
            hK = new c();
        } else if (Build.VERSION.SDK_INT >= 16) {
            hK = new b();
        } else {
            hK = new j();
        }
    }

    private a(AccessibilityNodeInfo accessibilityNodeInfo) {
        this.hL = accessibilityNodeInfo;
    }

    public static a a(AccessibilityNodeInfo accessibilityNodeInfo) {
        return new a(accessibilityNodeInfo);
    }

    public AccessibilityNodeInfo aT() {
        return this.hL;
    }

    public int getActions() {
        return this.hL.getActions();
    }

    public void addAction(int i) {
        this.hL.addAction(i);
    }

    public void getBoundsInParent(Rect rect) {
        this.hL.getBoundsInParent(rect);
    }

    public void getBoundsInScreen(Rect rect) {
        this.hL.getBoundsInScreen(rect);
    }

    public boolean isCheckable() {
        return this.hL.isCheckable();
    }

    public boolean isChecked() {
        return this.hL.isChecked();
    }

    public boolean isFocusable() {
        return this.hL.isFocusable();
    }

    public boolean isFocused() {
        return this.hL.isFocused();
    }

    public boolean isSelected() {
        return this.hL.isSelected();
    }

    public boolean isClickable() {
        return this.hL.isClickable();
    }

    public boolean isLongClickable() {
        return this.hL.isLongClickable();
    }

    public boolean isEnabled() {
        return this.hL.isEnabled();
    }

    public boolean isPassword() {
        return this.hL.isPassword();
    }

    public boolean isScrollable() {
        return this.hL.isScrollable();
    }

    public void setScrollable(boolean z) {
        this.hL.setScrollable(z);
    }

    public CharSequence getPackageName() {
        return this.hL.getPackageName();
    }

    public CharSequence getClassName() {
        return this.hL.getClassName();
    }

    public void setClassName(CharSequence charSequence) {
        this.hL.setClassName(charSequence);
    }

    public CharSequence getText() {
        return this.hL.getText();
    }

    public CharSequence getContentDescription() {
        return this.hL.getContentDescription();
    }

    public String getViewIdResourceName() {
        return hK.b(this.hL);
    }

    public int hashCode() {
        if (this.hL == null) {
            return 0;
        }
        return this.hL.hashCode();
    }

    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj != null && getClass() == obj.getClass()) {
            a aVar = (a) obj;
            if (this.hL == null) {
                return aVar.hL == null;
            }
            return this.hL.equals(aVar.hL);
        }
        return false;
    }

    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append(super.toString());
        Rect rect = new Rect();
        getBoundsInParent(rect);
        sb.append("; boundsInParent: " + rect);
        getBoundsInScreen(rect);
        sb.append("; boundsInScreen: " + rect);
        sb.append("; packageName: ").append(getPackageName());
        sb.append("; className: ").append(getClassName());
        sb.append("; text: ").append(getText());
        sb.append("; contentDescription: ").append(getContentDescription());
        sb.append("; viewId: ").append(getViewIdResourceName());
        sb.append("; checkable: ").append(isCheckable());
        sb.append("; checked: ").append(isChecked());
        sb.append("; focusable: ").append(isFocusable());
        sb.append("; focused: ").append(isFocused());
        sb.append("; selected: ").append(isSelected());
        sb.append("; clickable: ").append(isClickable());
        sb.append("; longClickable: ").append(isLongClickable());
        sb.append("; enabled: ").append(isEnabled());
        sb.append("; password: ").append(isPassword());
        sb.append("; scrollable: " + isScrollable());
        sb.append("; [");
        int actions = getActions();
        while (actions != 0) {
            int iNumberOfTrailingZeros = 1 << Integer.numberOfTrailingZeros(actions);
            actions &= iNumberOfTrailingZeros ^ (-1);
            sb.append(y(iNumberOfTrailingZeros));
            if (actions != 0) {
                sb.append(", ");
            }
        }
        sb.append("]");
        return sb.toString();
    }

    private static String y(int i) {
        switch (i) {
            case 1:
                return "ACTION_FOCUS";
            case 2:
                return "ACTION_CLEAR_FOCUS";
            case 4:
                return "ACTION_SELECT";
            case 8:
                return "ACTION_CLEAR_SELECTION";
            case 16:
                return "ACTION_CLICK";
            case 32:
                return "ACTION_LONG_CLICK";
            case 64:
                return "ACTION_ACCESSIBILITY_FOCUS";
            case 128:
                return "ACTION_CLEAR_ACCESSIBILITY_FOCUS";
            case 256:
                return "ACTION_NEXT_AT_MOVEMENT_GRANULARITY";
            case AdRequest.MAX_CONTENT_URL_LENGTH /* 512 */:
                return "ACTION_PREVIOUS_AT_MOVEMENT_GRANULARITY";
            case 1024:
                return "ACTION_NEXT_HTML_ELEMENT";
            case 2048:
                return "ACTION_PREVIOUS_HTML_ELEMENT";
            case 4096:
                return "ACTION_SCROLL_FORWARD";
            case Utility.DEFAULT_STREAM_BUFFER_SIZE /* 8192 */:
                return "ACTION_SCROLL_BACKWARD";
            case GLURLConnection.BUFFER_SIZE /* 16384 */:
                return "ACTION_COPY";
            case 32768:
                return "ACTION_PASTE";
            case NativeProtocol.MESSAGE_GET_ACCESS_TOKEN_REQUEST /* 65536 */:
                return "ACTION_CUT";
            case 131072:
                return "ACTION_SET_SELECTION";
            default:
                return "ACTION_UNKNOWN";
        }
    }
}
