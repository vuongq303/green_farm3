package com.google.android.gms.internal.ads;

import com.facebook.R;
import com.facebook.appevents.codeless.internal.Constants;
import com.facebook.internal.FacebookRequestErrorClassification;

/* JADX INFO: loaded from: classes.dex */
public final class zzhu {

    public final class zza extends zzbbo<zza, C0003zza> implements zzbcw {
        private static final zza zzakg = new zza();
        private static volatile zzbdf<zza> zzakh;

        /* JADX INFO: renamed from: com.google.android.gms.internal.ads.zzhu$zza$zza, reason: collision with other inner class name */
        public final class C0003zza extends zzbbo.zza<zza, C0003zza> implements zzbcw {
            private C0003zza() {
                super(zza.zzakg);
            }

            /* synthetic */ C0003zza(zzhv zzhvVar) {
                this();
            }
        }

        public enum zzb implements zzbbr {
            UNKNOWN_EVENT_TYPE(0),
            AD_REQUEST(1),
            AD_LOADED(2),
            AD_FAILED_TO_LOAD(3),
            AD_FAILED_TO_LOAD_NO_FILL(4),
            AD_IMPRESSION(5),
            AD_FIRST_CLICK(6),
            AD_SUBSEQUENT_CLICK(7),
            REQUEST_WILL_START(8),
            REQUEST_DID_END(9),
            REQUEST_WILL_UPDATE_SIGNALS(10),
            REQUEST_DID_UPDATE_SIGNALS(11),
            REQUEST_WILL_BUILD_URL(12),
            REQUEST_DID_BUILD_URL(13),
            REQUEST_WILL_MAKE_NETWORK_REQUEST(14),
            REQUEST_DID_RECEIVE_NETWORK_RESPONSE(15),
            REQUEST_WILL_PROCESS_RESPONSE(16),
            REQUEST_DID_PROCESS_RESPONSE(17),
            REQUEST_WILL_RENDER(18),
            REQUEST_DID_RENDER(19),
            REQUEST_WILL_UPDATE_GMS_SIGNALS(Constants.APP_INDEXING_SCHEDULE_INTERVAL_MS),
            REQUEST_DID_UPDATE_GMS_SIGNALS(1001),
            REQUEST_FAILED_TO_UPDATE_GMS_SIGNALS(1002),
            REQUEST_FAILED_TO_BUILD_URL(1003),
            REQUEST_FAILED_TO_MAKE_NETWORK_REQUEST(1004),
            REQUEST_FAILED_TO_PROCESS_RESPONSE(1005),
            REQUEST_FAILED_TO_UPDATE_SIGNALS(1006),
            BANNER_SIZE_INVALID(10000),
            BANNER_SIZE_VALID(10001);

            private static final zzbbs<zzb> zzall = new zzhw();
            private final int value;

            zzb(int i) {
                this.value = i;
            }

            public static zzb zzp(int i) {
                switch (i) {
                    case 0:
                        return UNKNOWN_EVENT_TYPE;
                    case 1:
                        return AD_REQUEST;
                    case 2:
                        return AD_LOADED;
                    case 3:
                        return AD_FAILED_TO_LOAD;
                    case 4:
                        return AD_FAILED_TO_LOAD_NO_FILL;
                    case 5:
                        return AD_IMPRESSION;
                    case 6:
                        return AD_FIRST_CLICK;
                    case 7:
                        return AD_SUBSEQUENT_CLICK;
                    case 8:
                        return REQUEST_WILL_START;
                    case 9:
                        return REQUEST_DID_END;
                    case R.styleable.CardView_contentPaddingRight /* 10 */:
                        return REQUEST_WILL_UPDATE_SIGNALS;
                    case R.styleable.CardView_contentPaddingTop /* 11 */:
                        return REQUEST_DID_UPDATE_SIGNALS;
                    case R.styleable.CardView_contentPaddingBottom /* 12 */:
                        return REQUEST_WILL_BUILD_URL;
                    case 13:
                        return REQUEST_DID_BUILD_URL;
                    case 14:
                        return REQUEST_WILL_MAKE_NETWORK_REQUEST;
                    case 15:
                        return REQUEST_DID_RECEIVE_NETWORK_RESPONSE;
                    case 16:
                        return REQUEST_WILL_PROCESS_RESPONSE;
                    case FacebookRequestErrorClassification.EC_USER_TOO_MANY_CALLS /* 17 */:
                        return REQUEST_DID_PROCESS_RESPONSE;
                    case 18:
                        return REQUEST_WILL_RENDER;
                    case 19:
                        return REQUEST_DID_RENDER;
                    case Constants.APP_INDEXING_SCHEDULE_INTERVAL_MS /* 1000 */:
                        return REQUEST_WILL_UPDATE_GMS_SIGNALS;
                    case 1001:
                        return REQUEST_DID_UPDATE_GMS_SIGNALS;
                    case 1002:
                        return REQUEST_FAILED_TO_UPDATE_GMS_SIGNALS;
                    case 1003:
                        return REQUEST_FAILED_TO_BUILD_URL;
                    case 1004:
                        return REQUEST_FAILED_TO_MAKE_NETWORK_REQUEST;
                    case 1005:
                        return REQUEST_FAILED_TO_PROCESS_RESPONSE;
                    case 1006:
                        return REQUEST_FAILED_TO_UPDATE_SIGNALS;
                    case 10000:
                        return BANNER_SIZE_INVALID;
                    case 10001:
                        return BANNER_SIZE_VALID;
                    default:
                        return null;
                }
            }

            @Override // com.google.android.gms.internal.ads.zzbbr
            public final int zzhq() {
                return this.value;
            }
        }

        static {
            zzbbo.zza((Class<zza>) zza.class, zzakg);
        }

        private zza() {
        }

        /* JADX WARN: Multi-variable type inference failed */
        /* JADX WARN: Type inference failed for: r0v9, types: [com.google.android.gms.internal.ads.zzbbo$zzb, com.google.android.gms.internal.ads.zzbdf<com.google.android.gms.internal.ads.zzhu$zza>] */
        @Override // com.google.android.gms.internal.ads.zzbbo
        protected final Object zza(int i, Object obj, Object obj2) {
            Object obj3;
            zzhv zzhvVar = null;
            switch (zzhv.zzakf[i - 1]) {
                case 1:
                    return new zza();
                case 2:
                    return new C0003zza(zzhvVar);
                case 3:
                    return zza(zzakg, "\u0001\u0000", (Object[]) null);
                case 4:
                    return zzakg;
                case 5:
                    zzbdf<zza> zzbdfVar = zzakh;
                    if (zzbdfVar != null) {
                        return zzbdfVar;
                    }
                    synchronized (zza.class) {
                        zzbdf<zza> zzbdfVar2 = zzakh;
                        obj3 = zzbdfVar2;
                        if (zzbdfVar2 == null) {
                            zzbbo.zzb zzbVar = new zzbbo.zzb(zzakg);
                            zzakh = zzbVar;
                            obj3 = zzbVar;
                        }
                        break;
                    }
                    return obj3;
                case 6:
                    return (byte) 1;
                case 7:
                    return null;
                default:
                    throw new UnsupportedOperationException();
            }
        }
    }
}
