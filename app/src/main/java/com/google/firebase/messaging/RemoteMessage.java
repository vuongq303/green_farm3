package com.google.firebase.messaging;

import android.net.Uri;
import android.os.Bundle;
import android.os.Parcel;
import android.os.Parcelable;
import android.support.v4.e.a;
import android.text.TextUtils;
import android.util.Log;
import com.google.android.gms.common.internal.safeparcel.AbstractSafeParcelable;
import com.google.android.gms.common.internal.safeparcel.c;
import java.util.Map;

/* JADX INFO: loaded from: classes.dex */
public final class RemoteMessage extends AbstractSafeParcelable {
    public static final Parcelable.Creator<RemoteMessage> CREATOR = new zzf();
    Bundle zzafw;
    private Notification zzbst;
    private Map<String, String> zzrr;

    public class Builder {
        private final Bundle zzafw = new Bundle();
        private final Map<String, String> zzrr = new a();

        public Builder(String str) {
            if (TextUtils.isEmpty(str)) {
                String strValueOf = String.valueOf(str);
                throw new IllegalArgumentException(strValueOf.length() != 0 ? "Invalid to: ".concat(strValueOf) : new String("Invalid to: "));
            }
            this.zzafw.putString("google.to", str);
        }

        public Builder addData(String str, String str2) {
            this.zzrr.put(str, str2);
            return this;
        }

        public RemoteMessage build() {
            Bundle bundle = new Bundle();
            for (Map.Entry<String, String> entry : this.zzrr.entrySet()) {
                bundle.putString(entry.getKey(), entry.getValue());
            }
            bundle.putAll(this.zzafw);
            this.zzafw.remove("from");
            return new RemoteMessage(bundle);
        }

        public Builder clearData() {
            this.zzrr.clear();
            return this;
        }

        public Builder setCollapseKey(String str) {
            this.zzafw.putString("collapse_key", str);
            return this;
        }

        public Builder setData(Map<String, String> map) {
            this.zzrr.clear();
            this.zzrr.putAll(map);
            return this;
        }

        public Builder setMessageId(String str) {
            this.zzafw.putString("google.message_id", str);
            return this;
        }

        public Builder setMessageType(String str) {
            this.zzafw.putString("message_type", str);
            return this;
        }

        public Builder setTtl(int i) {
            this.zzafw.putString("google.ttl", String.valueOf(i));
            return this;
        }
    }

    public class Notification {
        private final String tag;
        private final String zzbsu;
        private final String zzbsv;
        private final String[] zzbsw;
        private final String zzbsx;
        private final String zzbsy;
        private final String[] zzbsz;
        private final String zzbta;
        private final String zzbtb;
        private final String zzbtc;
        private final String zzbtd;
        private final Uri zzbte;

        private Notification(Bundle bundle) {
            this.zzbsu = zza.zza(bundle, "gcm.n.title");
            this.zzbsv = zza.zzb(bundle, "gcm.n.title");
            this.zzbsw = zze(bundle, "gcm.n.title");
            this.zzbsx = zza.zza(bundle, "gcm.n.body");
            this.zzbsy = zza.zzb(bundle, "gcm.n.body");
            this.zzbsz = zze(bundle, "gcm.n.body");
            this.zzbta = zza.zza(bundle, "gcm.n.icon");
            this.zzbtb = zza.zzo(bundle);
            this.tag = zza.zza(bundle, "gcm.n.tag");
            this.zzbtc = zza.zza(bundle, "gcm.n.color");
            this.zzbtd = zza.zza(bundle, "gcm.n.click_action");
            this.zzbte = zza.zzm(bundle);
        }

        private static String[] zze(Bundle bundle, String str) {
            Object[] objArrZzc = zza.zzc(bundle, str);
            if (objArrZzc == null) {
                return null;
            }
            String[] strArr = new String[objArrZzc.length];
            for (int i = 0; i < objArrZzc.length; i++) {
                strArr[i] = String.valueOf(objArrZzc[i]);
            }
            return strArr;
        }

        public String getBody() {
            return this.zzbsx;
        }

        public String[] getBodyLocalizationArgs() {
            return this.zzbsz;
        }

        public String getBodyLocalizationKey() {
            return this.zzbsy;
        }

        public String getClickAction() {
            return this.zzbtd;
        }

        public String getColor() {
            return this.zzbtc;
        }

        public String getIcon() {
            return this.zzbta;
        }

        public Uri getLink() {
            return this.zzbte;
        }

        public String getSound() {
            return this.zzbtb;
        }

        public String getTag() {
            return this.tag;
        }

        public String getTitle() {
            return this.zzbsu;
        }

        public String[] getTitleLocalizationArgs() {
            return this.zzbsw;
        }

        public String getTitleLocalizationKey() {
            return this.zzbsv;
        }
    }

    public RemoteMessage(Bundle bundle) {
        this.zzafw = bundle;
    }

    public final String getCollapseKey() {
        return this.zzafw.getString("collapse_key");
    }

    public final Map<String, String> getData() {
        if (this.zzrr == null) {
            this.zzrr = new a();
            for (String str : this.zzafw.keySet()) {
                Object obj = this.zzafw.get(str);
                if (obj instanceof String) {
                    String str2 = (String) obj;
                    if (!str.startsWith("google.") && !str.startsWith("gcm.") && !str.equals("from") && !str.equals("message_type") && !str.equals("collapse_key")) {
                        this.zzrr.put(str, str2);
                    }
                }
            }
        }
        return this.zzrr;
    }

    public final String getFrom() {
        return this.zzafw.getString("from");
    }

    public final String getMessageId() {
        String string = this.zzafw.getString("google.message_id");
        return string == null ? this.zzafw.getString("message_id") : string;
    }

    public final String getMessageType() {
        return this.zzafw.getString("message_type");
    }

    public final Notification getNotification() {
        if (this.zzbst == null && zza.zzl(this.zzafw)) {
            this.zzbst = new Notification(this.zzafw);
        }
        return this.zzbst;
    }

    public final long getSentTime() {
        Object obj = this.zzafw.get("google.sent_time");
        if (obj instanceof Long) {
            return ((Long) obj).longValue();
        }
        if (obj instanceof String) {
            try {
                return Long.parseLong((String) obj);
            } catch (NumberFormatException e) {
                String strValueOf = String.valueOf(obj);
                Log.w("FirebaseMessaging", new StringBuilder(String.valueOf(strValueOf).length() + 19).append("Invalid sent time: ").append(strValueOf).toString());
            }
        }
        return 0L;
    }

    public final String getTo() {
        return this.zzafw.getString("google.to");
    }

    public final int getTtl() {
        Object obj = this.zzafw.get("google.ttl");
        if (obj instanceof Integer) {
            return ((Integer) obj).intValue();
        }
        if (obj instanceof String) {
            try {
                return Integer.parseInt((String) obj);
            } catch (NumberFormatException e) {
                String strValueOf = String.valueOf(obj);
                Log.w("FirebaseMessaging", new StringBuilder(String.valueOf(strValueOf).length() + 13).append("Invalid TTL: ").append(strValueOf).toString());
            }
        }
        return 0;
    }

    @Override // android.os.Parcelable
    public final void writeToParcel(Parcel parcel, int i) {
        int iX = c.x(parcel);
        c.a(parcel, 2, this.zzafw, false);
        c.G(parcel, iX);
    }
}
