package com.google.android.gms.internal.measurement;

import com.facebook.internal.Utility;

/* JADX INFO: loaded from: classes.dex */
public final class zzcc {
    private static zzcd<Boolean> zzyf = zzcd.zza("analytics.service_enabled", false, false);
    public static zzcd<Boolean> zzyg = zzcd.zza("analytics.service_client_enabled", true, true);
    public static zzcd<String> zzyh = zzcd.zza("analytics.log_tag", "GAv4", "GAv4-SVC");
    private static zzcd<Long> zzyi = zzcd.zza("analytics.max_tokens", 60L, 60L);
    private static zzcd<Float> zzyj = zzcd.zza("analytics.tokens_per_sec", 0.5f, 0.5f);
    public static zzcd<Integer> zzyk = zzcd.zza("analytics.max_stored_hits", 2000, 20000);
    private static zzcd<Integer> zzyl = zzcd.zza("analytics.max_stored_hits_per_app", 2000, 2000);
    public static zzcd<Integer> zzym = zzcd.zza("analytics.max_stored_properties_per_app", 100, 100);
    public static zzcd<Long> zzyn = zzcd.zza("analytics.local_dispatch_millis", 1800000L, 120000L);
    public static zzcd<Long> zzyo = zzcd.zza("analytics.initial_local_dispatch_millis", 5000L, 5000L);
    private static zzcd<Long> zzyp = zzcd.zza("analytics.min_local_dispatch_millis", 120000L, 120000L);
    private static zzcd<Long> zzyq = zzcd.zza("analytics.max_local_dispatch_millis", 7200000L, 7200000L);
    public static zzcd<Long> zzyr = zzcd.zza("analytics.dispatch_alarm_millis", 7200000L, 7200000L);
    public static zzcd<Long> zzys = zzcd.zza("analytics.max_dispatch_alarm_millis", 32400000L, 32400000L);
    public static zzcd<Integer> zzyt = zzcd.zza("analytics.max_hits_per_dispatch", 20, 20);
    public static zzcd<Integer> zzyu = zzcd.zza("analytics.max_hits_per_batch", 20, 20);
    public static zzcd<String> zzyv = zzcd.zza("analytics.insecure_host", "http://www.google-analytics.com", "http://www.google-analytics.com");
    public static zzcd<String> zzyw = zzcd.zza("analytics.secure_host", "https://ssl.google-analytics.com", "https://ssl.google-analytics.com");
    public static zzcd<String> zzyx = zzcd.zza("analytics.simple_endpoint", "/collect", "/collect");
    public static zzcd<String> zzyy = zzcd.zza("analytics.batching_endpoint", "/batch", "/batch");
    public static zzcd<Integer> zzyz = zzcd.zza("analytics.max_get_length", 2036, 2036);
    public static zzcd<String> zzza = zzcd.zza("analytics.batching_strategy.k", zzbk.BATCH_BY_COUNT.name(), zzbk.BATCH_BY_COUNT.name());
    public static zzcd<String> zzzb;
    private static zzcd<Integer> zzzc;
    public static zzcd<Integer> zzzd;
    public static zzcd<Integer> zzze;
    public static zzcd<Integer> zzzf;
    public static zzcd<String> zzzg;
    public static zzcd<Integer> zzzh;
    private static zzcd<Long> zzzi;
    public static zzcd<Integer> zzzj;
    public static zzcd<Integer> zzzk;
    public static zzcd<Long> zzzl;
    private static zzcd<String> zzzm;
    private static zzcd<Integer> zzzn;
    public static zzcd<Boolean> zzzo;
    public static zzcd<Long> zzzp;
    public static zzcd<Long> zzzq;
    private static zzcd<Long> zzzr;
    private static zzcd<Long> zzzs;
    public static zzcd<Long> zzzt;
    public static zzcd<Long> zzzu;
    public static zzcd<Long> zzzv;

    static {
        String strName = zzbq.GZIP.name();
        zzzb = zzcd.zza("analytics.compression_strategy.k", strName, strName);
        zzzc = zzcd.zza("analytics.max_hits_per_request.k", 20, 20);
        zzzd = zzcd.zza("analytics.max_hit_length.k", Utility.DEFAULT_STREAM_BUFFER_SIZE, Utility.DEFAULT_STREAM_BUFFER_SIZE);
        zzze = zzcd.zza("analytics.max_post_length.k", Utility.DEFAULT_STREAM_BUFFER_SIZE, Utility.DEFAULT_STREAM_BUFFER_SIZE);
        zzzf = zzcd.zza("analytics.max_batch_post_length", Utility.DEFAULT_STREAM_BUFFER_SIZE, Utility.DEFAULT_STREAM_BUFFER_SIZE);
        zzzg = zzcd.zza("analytics.fallback_responses.k", "404,502", "404,502");
        zzzh = zzcd.zza("analytics.batch_retry_interval.seconds.k", 3600, 3600);
        zzzi = zzcd.zza("analytics.service_monitor_interval", 86400000L, 86400000L);
        zzzj = zzcd.zza("analytics.http_connection.connect_timeout_millis", 60000, 60000);
        zzzk = zzcd.zza("analytics.http_connection.read_timeout_millis", 61000, 61000);
        zzzl = zzcd.zza("analytics.campaigns.time_limit", 86400000L, 86400000L);
        zzzm = zzcd.zza("analytics.first_party_experiment_id", "", "");
        zzzn = zzcd.zza("analytics.first_party_experiment_variant", 0, 0);
        zzzo = zzcd.zza("analytics.test.disable_receiver", false, false);
        zzzp = zzcd.zza("analytics.service_client.idle_disconnect_millis", 10000L, 10000L);
        zzzq = zzcd.zza("analytics.service_client.connect_timeout_millis", 5000L, 5000L);
        zzzr = zzcd.zza("analytics.service_client.second_connect_delay_millis", 5000L, 5000L);
        zzzs = zzcd.zza("analytics.service_client.unexpected_reconnect_millis", 60000L, 60000L);
        zzzt = zzcd.zza("analytics.service_client.reconnect_throttle_millis", 1800000L, 1800000L);
        zzzu = zzcd.zza("analytics.monitoring.sample_period_millis", 86400000L, 86400000L);
        zzzv = zzcd.zza("analytics.initialization_warning_threshold", 5000L, 5000L);
    }
}
