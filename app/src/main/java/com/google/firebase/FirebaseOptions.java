package com.google.firebase;

import android.content.Context;
import android.text.TextUtils;
import com.google.android.gms.common.d.x;
import com.google.android.gms.common.internal.bd;
import com.google.android.gms.common.internal.bf;
import com.google.android.gms.common.internal.bj;

/* JADX INFO: loaded from: classes.dex */
public final class FirebaseOptions {
    private final String zzr;
    private final String zzs;
    private final String zzt;
    private final String zzu;
    private final String zzv;
    private final String zzw;
    private final String zzx;

    public final class Builder {
        private String zzr;
        private String zzs;
        private String zzt;
        private String zzu;
        private String zzv;
        private String zzw;
        private String zzx;

        public Builder(FirebaseOptions firebaseOptions) {
            this.zzs = firebaseOptions.zzs;
            this.zzr = firebaseOptions.zzr;
            this.zzt = firebaseOptions.zzt;
            this.zzu = firebaseOptions.zzu;
            this.zzv = firebaseOptions.zzv;
            this.zzw = firebaseOptions.zzw;
            this.zzx = firebaseOptions.zzx;
        }

        public final FirebaseOptions build() {
            return new FirebaseOptions(this.zzs, this.zzr, this.zzt, this.zzu, this.zzv, this.zzw, this.zzx);
        }

        public final Builder setApiKey(String str) {
            this.zzr = bf.d(str, (Object) "ApiKey must be set.");
            return this;
        }

        public final Builder setApplicationId(String str) {
            this.zzs = bf.d(str, (Object) "ApplicationId must be set.");
            return this;
        }

        public final Builder setDatabaseUrl(String str) {
            this.zzt = str;
            return this;
        }

        public final Builder setGcmSenderId(String str) {
            this.zzv = str;
            return this;
        }

        public final Builder setProjectId(String str) {
            this.zzx = str;
            return this;
        }

        public final Builder setStorageBucket(String str) {
            this.zzw = str;
            return this;
        }
    }

    private FirebaseOptions(String str, String str2, String str3, String str4, String str5, String str6, String str7) {
        bf.a(!x.im(str), "ApplicationId must be set.");
        this.zzs = str;
        this.zzr = str2;
        this.zzt = str3;
        this.zzu = str4;
        this.zzv = str5;
        this.zzw = str6;
        this.zzx = str7;
    }

    public static FirebaseOptions fromResource(Context context) {
        bj bjVar = new bj(context);
        String string = bjVar.getString("google_app_id");
        if (TextUtils.isEmpty(string)) {
            return null;
        }
        return new FirebaseOptions(string, bjVar.getString("google_api_key"), bjVar.getString("firebase_database_url"), bjVar.getString("ga_trackingId"), bjVar.getString("gcm_defaultSenderId"), bjVar.getString("google_storage_bucket"), bjVar.getString("project_id"));
    }

    public final boolean equals(Object obj) {
        if (!(obj instanceof FirebaseOptions)) {
            return false;
        }
        FirebaseOptions firebaseOptions = (FirebaseOptions) obj;
        return bd.b(this.zzs, firebaseOptions.zzs) && bd.b(this.zzr, firebaseOptions.zzr) && bd.b(this.zzt, firebaseOptions.zzt) && bd.b(this.zzu, firebaseOptions.zzu) && bd.b(this.zzv, firebaseOptions.zzv) && bd.b(this.zzw, firebaseOptions.zzw) && bd.b(this.zzx, firebaseOptions.zzx);
    }

    public final String getApiKey() {
        return this.zzr;
    }

    public final String getApplicationId() {
        return this.zzs;
    }

    public final String getDatabaseUrl() {
        return this.zzt;
    }

    public final String getGcmSenderId() {
        return this.zzv;
    }

    public final String getProjectId() {
        return this.zzx;
    }

    public final String getStorageBucket() {
        return this.zzw;
    }

    public final int hashCode() {
        return bd.hashCode(this.zzs, this.zzr, this.zzt, this.zzu, this.zzv, this.zzw, this.zzx);
    }

    public final String toString() {
        return bd.C(this).c("applicationId", this.zzs).c("apiKey", this.zzr).c("databaseUrl", this.zzt).c("gcmSenderId", this.zzv).c("storageBucket", this.zzw).c("projectId", this.zzx).toString();
    }
}
