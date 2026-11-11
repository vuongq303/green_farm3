package com.google.android.gms.common.b;

import android.util.Log;
import com.google.android.gms.common.internal.ae;
import java.util.Locale;

/* JADX INFO: loaded from: classes.dex */
public class a {
    private final ae bOO;
    private final int bOP;
    private final String mTag;
    private final String zzud;

    private a(String str, String str2) {
        this.zzud = str2;
        this.mTag = str;
        this.bOO = new ae(str);
        int i = 2;
        while (7 >= i && !Log.isLoggable(this.mTag, i)) {
            i++;
        }
        this.bOP = i;
    }

    /* JADX WARN: Illegal instructions before constructor call */
    public a(String str, String... strArr) {
        String string;
        if (strArr.length == 0) {
            string = "";
        } else {
            StringBuilder sb = new StringBuilder();
            sb.append('[');
            for (String str2 : strArr) {
                if (sb.length() > 1) {
                    sb.append(",");
                }
                sb.append(str2);
            }
            sb.append(']').append(' ');
            string = sb.toString();
        }
        this(str, string);
    }

    public void a(String str, Object... objArr) {
        Log.i(this.mTag, format(str, objArr));
    }

    public void b(String str, Object... objArr) {
        Log.w(this.mTag, format(str, objArr));
    }

    public void d(String str, Object... objArr) {
        if (isLoggable(3)) {
            Log.d(this.mTag, format(str, objArr));
        }
    }

    protected String format(String str, Object... objArr) {
        if (objArr != null && objArr.length > 0) {
            str = String.format(Locale.US, str, objArr);
        }
        return this.zzud.concat(str);
    }

    public boolean isLoggable(int i) {
        return this.bOP <= i;
    }
}
