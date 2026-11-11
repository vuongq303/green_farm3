package a;

import com.facebook.appevents.codeless.internal.Constants;
import java.util.Locale;
import java.util.concurrent.Executor;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;

/* JADX INFO: compiled from: BoltsExecutors.java */
/* JADX INFO: loaded from: classes.dex */
final class g {
    private static final g zt = new g();
    private final ExecutorService zu;
    private final ScheduledExecutorService zv;
    private final Executor zw;

    private static boolean fr() {
        String property = System.getProperty("java.runtime.name");
        if (property == null) {
            return false;
        }
        return property.toLowerCase(Locale.US).contains(Constants.PLATFORM);
    }

    private g() {
        this.zu = !fr() ? Executors.newCachedThreadPool() : a.newCachedThreadPool();
        this.zv = Executors.newSingleThreadScheduledExecutor();
        this.zw = new i();
    }

    public static ExecutorService fs() {
        return zt.zu;
    }

    static Executor ft() {
        return zt.zw;
    }
}
