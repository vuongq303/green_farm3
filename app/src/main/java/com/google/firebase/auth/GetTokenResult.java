package com.google.firebase.auth;

import java.util.Map;

/* JADX INFO: loaded from: classes.dex */
public class GetTokenResult {
    private Map<String, Object> zzaa;
    private String zzz;

    public GetTokenResult(String str, Map<String, Object> map) {
        this.zzz = str;
        this.zzaa = map;
    }

    private final long zza(String str) {
        Integer num = (Integer) this.zzaa.get(str);
        if (num == null) {
            return 0L;
        }
        return num.longValue();
    }

    public long getAuthTimestamp() {
        return zza("auth_time");
    }

    public Map<String, Object> getClaims() {
        return this.zzaa;
    }

    public long getExpirationTimestamp() {
        return zza("exp");
    }

    public long getIssuedAtTimestamp() {
        return zza("iat");
    }

    public String getSignInProvider() {
        Map map = (Map) this.zzaa.get("firebase");
        if (map != null) {
            return (String) map.get("sign_in_provider");
        }
        return null;
    }

    public String getToken() {
        return this.zzz;
    }
}
