package com.google.android.gms.common.stats;

import com.google.android.gms.common.internal.ReflectedParcelable;
import com.google.android.gms.common.internal.safeparcel.AbstractSafeParcelable;

/* JADX INFO: loaded from: classes.dex */
public abstract class StatsEvent extends AbstractSafeParcelable implements ReflectedParcelable {
    public abstract long aaZ();

    public abstract String aba();

    public abstract String abb();

    public abstract long abc();

    public abstract long getElapsedRealtime();

    public abstract int getEventType();

    public abstract long getTimeMillis();

    public String toString() {
        long timeMillis = getTimeMillis();
        int eventType = getEventType();
        long jAaZ = aaZ();
        String strAbb = abb();
        return new StringBuilder(String.valueOf(strAbb).length() + 53).append(timeMillis).append("\t").append(eventType).append("\t").append(jAaZ).append(strAbb).toString();
    }
}
