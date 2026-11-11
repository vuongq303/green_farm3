package com.google.android.gms.ads.internal.gmsg;

import android.text.TextUtils;
import com.facebook.share.internal.ShareConstants;
import com.google.android.gms.internal.ads.zzadh;
import com.google.android.gms.internal.ads.zzakb;
import java.util.HashMap;
import java.util.Map;
import javax.annotation.ParametersAreNonnullByDefault;
import javax.annotation.concurrent.GuardedBy;
import org.json.JSONException;
import org.json.JSONObject;

/* JADX INFO: loaded from: classes.dex */
@ParametersAreNonnullByDefault
@zzadh
public final class zzaf implements zzv<Object> {
    private final Object mLock = new Object();

    @GuardedBy("mLock")
    private final Map<String, zzag> bAb = new HashMap();

    @Override // com.google.android.gms.ads.internal.gmsg.zzv
    public final void zza(Object obj, Map<String, String> map) {
        String strConcat;
        String str = map.get(ShareConstants.WEB_DIALOG_PARAM_ID);
        String str2 = map.get("fail");
        String str3 = map.get("fail_reason");
        String str4 = map.get("fail_stack");
        String str5 = map.get("result");
        String str6 = TextUtils.isEmpty(str4) ? "Unknown Fail Reason." : str3;
        if (TextUtils.isEmpty(str4)) {
            strConcat = "";
        } else {
            String strValueOf = String.valueOf(str4);
            strConcat = strValueOf.length() != 0 ? "\n".concat(strValueOf) : new String("\n");
        }
        synchronized (this.mLock) {
            zzag zzagVarRemove = this.bAb.remove(str);
            if (zzagVarRemove == null) {
                String strValueOf2 = String.valueOf(str);
                zzakb.zzdk(strValueOf2.length() != 0 ? "Received result for unexpected method invocation: ".concat(strValueOf2) : new String("Received result for unexpected method invocation: "));
                return;
            }
            if (!TextUtils.isEmpty(str2)) {
                String strValueOf3 = String.valueOf(str6);
                String strValueOf4 = String.valueOf(strConcat);
                zzagVarRemove.zzau(strValueOf4.length() != 0 ? strValueOf3.concat(strValueOf4) : new String(strValueOf3));
            } else {
                if (str5 == null) {
                    zzagVarRemove.zzd(null);
                    return;
                }
                try {
                    JSONObject jSONObject = new JSONObject(str5);
                    if (zzakb.zzqp()) {
                        String strValueOf5 = String.valueOf(jSONObject.toString(2));
                        zzakb.v(strValueOf5.length() != 0 ? "Result GMSG: ".concat(strValueOf5) : new String("Result GMSG: "));
                    }
                    zzagVarRemove.zzd(jSONObject);
                } catch (JSONException e) {
                    zzagVarRemove.zzau(e.getMessage());
                }
            }
        }
    }

    public final void zza(String str, zzag zzagVar) {
        synchronized (this.mLock) {
            this.bAb.put(str, zzagVar);
        }
    }
}
