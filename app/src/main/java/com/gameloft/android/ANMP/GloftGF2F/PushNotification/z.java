package com.gameloft.android.ANMP.GloftGF2F.PushNotification;

import android.content.Context;
import android.content.res.Resources;
import android.os.Build;
import com.gameloft.android.ANMP.GloftGF2F.R;

/* JADX INFO: compiled from: PushTheme.java */
/* JADX INFO: loaded from: classes.dex */
public class z {
    public static int icon = 0;
    public static int layout = 0;

    public static void init(Context context) {
        ae(context);
    }

    public static int getIcon() {
        return icon;
    }

    static void ae(Context context) {
        Resources resources = context.getResources();
        if (q.FI) {
            if (q.FJ != null) {
                icon = resources.getIdentifier(q.FJ, "drawable", context.getPackageName());
            }
            if (icon == 0) {
                icon = resources.getIdentifier("pn_custom_icon", "drawable", context.getPackageName());
            }
        } else {
            icon = resources.getIdentifier("pn_icon", "drawable", context.getPackageName());
        }
        layout = resources.getIdentifier("custom_notification_layout", "layout", context.getPackageName());
        if (icon == 0) {
            if (Build.VERSION.SDK_INT > 25) {
                icon = R.mipmap.ic_pn8;
            } else {
                icon = R.mipmap.ic_launcher;
            }
        }
    }
}
