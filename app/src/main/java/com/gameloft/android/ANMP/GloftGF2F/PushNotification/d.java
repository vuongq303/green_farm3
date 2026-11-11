package com.gameloft.android.ANMP.GloftGF2F.PushNotification;

import android.content.Context;
import java.util.Calendar;

/* JADX INFO: compiled from: DontDisturbPolicy.java */
/* JADX INFO: loaded from: classes.dex */
public class d {
    static boolean v(Context context) {
        if (k.T(context)) {
            Calendar calendar = Calendar.getInstance();
            int i = calendar.get(12) + (calendar.get(11) * 60);
            if (i <= 480 || i >= 1380) {
                return true;
            }
        }
        return false;
    }
}
