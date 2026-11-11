package com.moat.analytics.mobile.glft;

import java.util.HashMap;
import java.util.Map;

/* JADX INFO: loaded from: classes.dex */
public class MoatAdEvent {
    Integer ckK;
    Double ckL;
    MoatAdEventType ckM;
    private final Long f;
    static final Integer ckJ = Integer.MIN_VALUE;
    private static final Double e = Double.valueOf(Double.NaN);
    public static final Double VOLUME_MUTED = Double.valueOf(0.0d);
    public static final Double VOLUME_UNMUTED = Double.valueOf(1.0d);

    public MoatAdEvent(MoatAdEventType moatAdEventType) {
        this(moatAdEventType, ckJ, e);
    }

    public MoatAdEvent(MoatAdEventType moatAdEventType, Integer num) {
        this(moatAdEventType, num, e);
    }

    public MoatAdEvent(MoatAdEventType moatAdEventType, Integer num, Double d) {
        this.f = Long.valueOf(System.currentTimeMillis());
        this.ckM = moatAdEventType;
        this.ckL = d;
        this.ckK = num;
    }

    Map<String, Object> ajF() {
        HashMap map = new HashMap();
        map.put("adVolume", this.ckL);
        map.put("playhead", this.ckK);
        map.put("aTimeStamp", this.f);
        map.put("type", this.ckM.toString());
        return map;
    }
}
