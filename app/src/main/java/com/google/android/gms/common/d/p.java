package com.google.android.gms.common.d;

import android.text.TextUtils;
import com.facebook.R;
import com.facebook.internal.FacebookRequestErrorClassification;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/* JADX INFO: loaded from: classes.dex */
public final class p {
    private static final Pattern bQF = Pattern.compile("\\\\.");
    private static final Pattern bQG = Pattern.compile("[\\\\\"/\b\f\n\r\t]");

    public static String ii(String str) {
        if (TextUtils.isEmpty(str)) {
            return str;
        }
        String strUnescape = aa.unescape(str);
        Matcher matcher = bQF.matcher(strUnescape);
        StringBuffer stringBuffer = null;
        while (matcher.find()) {
            if (stringBuffer == null) {
                stringBuffer = new StringBuffer();
            }
            switch (matcher.group().charAt(1)) {
                case '\"':
                    matcher.appendReplacement(stringBuffer, "\"");
                    break;
                case '/':
                    matcher.appendReplacement(stringBuffer, "/");
                    break;
                case '\\':
                    matcher.appendReplacement(stringBuffer, "\\\\");
                    break;
                case 'b':
                    matcher.appendReplacement(stringBuffer, "\b");
                    break;
                case FacebookRequestErrorClassification.EC_INVALID_SESSION /* 102 */:
                    matcher.appendReplacement(stringBuffer, "\f");
                    break;
                case 'n':
                    matcher.appendReplacement(stringBuffer, "\n");
                    break;
                case 'r':
                    matcher.appendReplacement(stringBuffer, "\r");
                    break;
                case 't':
                    matcher.appendReplacement(stringBuffer, "\t");
                    break;
                default:
                    throw new IllegalStateException("Found an escaped character that should never be.");
            }
        }
        if (stringBuffer == null) {
            return strUnescape;
        }
        matcher.appendTail(stringBuffer);
        return stringBuffer.toString();
    }

    public static String ij(String str) {
        if (TextUtils.isEmpty(str)) {
            return str;
        }
        Matcher matcher = bQG.matcher(str);
        StringBuffer stringBuffer = null;
        while (matcher.find()) {
            if (stringBuffer == null) {
                stringBuffer = new StringBuffer();
            }
            switch (matcher.group().charAt(0)) {
                case '\b':
                    matcher.appendReplacement(stringBuffer, "\\\\b");
                    break;
                case '\t':
                    matcher.appendReplacement(stringBuffer, "\\\\t");
                    break;
                case R.styleable.CardView_contentPaddingRight /* 10 */:
                    matcher.appendReplacement(stringBuffer, "\\\\n");
                    break;
                case R.styleable.CardView_contentPaddingBottom /* 12 */:
                    matcher.appendReplacement(stringBuffer, "\\\\f");
                    break;
                case '\r':
                    matcher.appendReplacement(stringBuffer, "\\\\r");
                    break;
                case '\"':
                    matcher.appendReplacement(stringBuffer, "\\\\\\\"");
                    break;
                case '/':
                    matcher.appendReplacement(stringBuffer, "\\\\/");
                    break;
                case '\\':
                    matcher.appendReplacement(stringBuffer, "\\\\\\\\");
                    break;
            }
        }
        if (stringBuffer == null) {
            return str;
        }
        matcher.appendTail(stringBuffer);
        return stringBuffer.toString();
    }
}
