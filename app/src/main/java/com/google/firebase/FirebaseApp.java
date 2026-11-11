package com.google.firebase;

import android.app.Application;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.support.v4.e.a;
import android.support.v4.e.c;
import android.text.TextUtils;
import android.util.Log;
import com.google.android.gms.common.api.a.b;
import com.google.android.gms.common.d.s;
import com.google.android.gms.common.d.u;
import com.google.android.gms.common.internal.bd;
import com.google.android.gms.common.internal.bf;
import com.google.android.gms.f.f;
import com.google.android.gms.f.j;
import com.google.firebase.auth.GetTokenResult;
import com.google.firebase.components.Component;
import com.google.firebase.components.zzc;
import com.google.firebase.components.zzg;
import com.google.firebase.internal.InternalTokenProvider;
import com.google.firebase.internal.InternalTokenResult;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.lang.reflect.Modifier;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.CopyOnWriteArrayList;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.concurrent.atomic.AtomicReference;
import javax.annotation.concurrent.GuardedBy;

/* JADX INFO: loaded from: classes.dex */
public class FirebaseApp {
    public static final String DEFAULT_APP_NAME = "[DEFAULT]";
    private final String mName;
    private final Context zzg;
    private final FirebaseOptions zzh;
    private final zzg zzi;
    private InternalTokenProvider zzo;
    private static final List<String> zza = Arrays.asList("com.google.firebase.auth.FirebaseAuth", "com.google.firebase.iid.FirebaseInstanceId");
    private static final List<String> zzb = Collections.singletonList("com.google.firebase.crash.FirebaseCrash");
    private static final List<String> zzc = Arrays.asList("com.google.android.gms.measurement.AppMeasurement");
    private static final List<String> zzd = Arrays.asList(new String[0]);
    private static final Set<String> zze = Collections.emptySet();
    private static final Object sLock = new Object();

    @GuardedBy("sLock")
    static final Map<String, FirebaseApp> zzf = new a();
    private final AtomicBoolean zzj = new AtomicBoolean(false);
    private final AtomicBoolean zzk = new AtomicBoolean();
    private final List<IdTokenListener> zzl = new CopyOnWriteArrayList();
    private final List<BackgroundStateChangeListener> zzm = new CopyOnWriteArrayList();
    private final List<Object> zzn = new CopyOnWriteArrayList();
    private IdTokenListenersCountChangedListener zzp = new com.google.firebase.internal.zza();

    public interface BackgroundStateChangeListener {
        void onBackgroundStateChanged(boolean z);
    }

    public interface IdTokenListener {
        void onIdTokenChanged(InternalTokenResult internalTokenResult);
    }

    public interface IdTokenListenersCountChangedListener {
        void onListenerCountChanged(int i);
    }

    final class zza extends BroadcastReceiver {
        private static AtomicReference<zza> zzq = new AtomicReference<>();
        private final Context zzg;

        private zza(Context context) {
            this.zzg = context;
        }

        /* JADX INFO: Access modifiers changed from: private */
        public static void zza(Context context) {
            if (zzq.get() == null) {
                zza zzaVar = new zza(context);
                if (zzq.compareAndSet(null, zzaVar)) {
                    context.registerReceiver(zzaVar, new IntentFilter("android.intent.action.USER_UNLOCKED"));
                }
            }
        }

        @Override // android.content.BroadcastReceiver
        public final void onReceive(Context context, Intent intent) {
            synchronized (FirebaseApp.sLock) {
                Iterator<FirebaseApp> it = FirebaseApp.zzf.values().iterator();
                while (it.hasNext()) {
                    it.next().zzc();
                }
            }
            this.zzg.unregisterReceiver(this);
        }
    }

    private FirebaseApp(Context context, String str, FirebaseOptions firebaseOptions) {
        this.zzg = (Context) bf.checkNotNull(context);
        this.mName = bf.id(str);
        this.zzh = (FirebaseOptions) bf.checkNotNull(firebaseOptions);
        this.zzi = new zzg(new zzc(this.zzg).zzj(), Component.of(Context.class, this.zzg), Component.of(FirebaseApp.class, this), Component.of(FirebaseOptions.class, this.zzh));
    }

    public static List<FirebaseApp> getApps(Context context) {
        ArrayList arrayList;
        com.google.firebase.internal.zzb.zze(context);
        synchronized (sLock) {
            arrayList = new ArrayList(zzf.values());
            com.google.firebase.internal.zzb.zzq();
            Set<String> setZzr = com.google.firebase.internal.zzb.zzr();
            setZzr.removeAll(zzf.keySet());
            for (String str : setZzr) {
                com.google.firebase.internal.zzb.zzb(str);
                arrayList.add(initializeApp(context, null, str));
            }
        }
        return arrayList;
    }

    public static FirebaseApp getInstance() {
        FirebaseApp firebaseApp;
        synchronized (sLock) {
            firebaseApp = zzf.get(DEFAULT_APP_NAME);
            if (firebaseApp == null) {
                String strAbF = u.abF();
                throw new IllegalStateException(new StringBuilder(String.valueOf(strAbF).length() + 116).append("Default FirebaseApp is not initialized in this process ").append(strAbF).append(". Make sure to call FirebaseApp.initializeApp(Context) first.").toString());
            }
        }
        return firebaseApp;
    }

    public static FirebaseApp getInstance(String str) {
        FirebaseApp firebaseApp;
        String strConcat;
        synchronized (sLock) {
            firebaseApp = zzf.get(str.trim());
            if (firebaseApp == null) {
                List<String> listZzb = zzb();
                if (listZzb.isEmpty()) {
                    strConcat = "";
                } else {
                    String strValueOf = String.valueOf(TextUtils.join(", ", listZzb));
                    strConcat = strValueOf.length() != 0 ? "Available app names: ".concat(strValueOf) : new String("Available app names: ");
                }
                throw new IllegalStateException(String.format("FirebaseApp with name %s doesn't exist. %s", str, strConcat));
            }
        }
        return firebaseApp;
    }

    public static FirebaseApp initializeApp(Context context) {
        FirebaseApp firebaseAppInitializeApp;
        synchronized (sLock) {
            if (zzf.containsKey(DEFAULT_APP_NAME)) {
                firebaseAppInitializeApp = getInstance();
            } else {
                FirebaseOptions firebaseOptionsFromResource = FirebaseOptions.fromResource(context);
                firebaseAppInitializeApp = firebaseOptionsFromResource == null ? null : initializeApp(context, firebaseOptionsFromResource);
            }
        }
        return firebaseAppInitializeApp;
    }

    public static FirebaseApp initializeApp(Context context, FirebaseOptions firebaseOptions) {
        return initializeApp(context, firebaseOptions, DEFAULT_APP_NAME);
    }

    public static FirebaseApp initializeApp(Context context, FirebaseOptions firebaseOptions, String str) {
        FirebaseApp firebaseApp;
        com.google.firebase.internal.zzb.zze(context);
        if (s.abv() && (context.getApplicationContext() instanceof Application)) {
            b.a((Application) context.getApplicationContext());
            b.YT().a(new com.google.firebase.zza());
        }
        String strTrim = str.trim();
        if (context.getApplicationContext() != null) {
            context = context.getApplicationContext();
        }
        synchronized (sLock) {
            bf.a(!zzf.containsKey(strTrim), new StringBuilder(String.valueOf(strTrim).length() + 33).append("FirebaseApp name ").append(strTrim).append(" already exists!").toString());
            bf.d(context, "Application context cannot be null.");
            firebaseApp = new FirebaseApp(context, strTrim, firebaseOptions);
            zzf.put(strTrim, firebaseApp);
        }
        com.google.firebase.internal.zzb.zzb(firebaseApp);
        firebaseApp.zzc();
        return firebaseApp;
    }

    public static void onBackgroundStateChanged(boolean z) {
        synchronized (sLock) {
            ArrayList arrayList = new ArrayList(zzf.values());
            int size = arrayList.size();
            int i = 0;
            while (i < size) {
                Object obj = arrayList.get(i);
                i++;
                FirebaseApp firebaseApp = (FirebaseApp) obj;
                if (firebaseApp.zzj.get()) {
                    firebaseApp.zza(z);
                }
            }
        }
    }

    private final void zza() {
        bf.a(!this.zzk.get(), "FirebaseApp was deleted");
    }

    /* JADX WARN: Multi-variable type inference failed */
    private static <T> void zza(Class<T> cls, T t, Iterable<String> iterable, boolean z) {
        for (String str : iterable) {
            if (z) {
                try {
                    if (zzd.contains(str)) {
                    }
                } catch (ClassNotFoundException e) {
                    if (zze.contains(str)) {
                        throw new IllegalStateException(String.valueOf(str).concat(" is missing, but is required. Check if it has been removed by Proguard."));
                    }
                    Log.d("FirebaseApp", String.valueOf(str).concat(" is not linked. Skipping initialization."));
                } catch (IllegalAccessException e2) {
                    String strValueOf = String.valueOf(str);
                    Log.wtf("FirebaseApp", strValueOf.length() != 0 ? "Failed to initialize ".concat(strValueOf) : new String("Failed to initialize "), e2);
                } catch (NoSuchMethodException e3) {
                    throw new IllegalStateException(String.valueOf(str).concat("#getInstance has been removed by Proguard. Add keep rule to prevent it."));
                } catch (InvocationTargetException e4) {
                    Log.wtf("FirebaseApp", "Firebase API initialization failure.", e4);
                }
            }
            Method method = Class.forName(str).getMethod("getInstance", cls);
            int modifiers = method.getModifiers();
            if (Modifier.isPublic(modifiers) && Modifier.isStatic(modifiers)) {
                method.invoke(null, t);
            }
        }
    }

    private final void zza(boolean z) {
        Log.d("FirebaseApp", "Notifying background state change listeners.");
        Iterator<BackgroundStateChangeListener> it = this.zzm.iterator();
        while (it.hasNext()) {
            it.next().onBackgroundStateChanged(z);
        }
    }

    private static List<String> zzb() {
        c cVar = new c();
        synchronized (sLock) {
            Iterator<FirebaseApp> it = zzf.values().iterator();
            while (it.hasNext()) {
                cVar.add(it.next().getName());
            }
            if (com.google.firebase.internal.zzb.zzq() != null) {
                cVar.addAll(com.google.firebase.internal.zzb.zzr());
            }
        }
        ArrayList arrayList = new ArrayList(cVar);
        Collections.sort(arrayList);
        return arrayList;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public final void zzc() {
        boolean zG = android.support.v4.content.c.g(this.zzg);
        if (zG) {
            zza.zza(this.zzg);
        } else {
            this.zzi.zzb(isDefaultApp());
        }
        zza(FirebaseApp.class, this, zza, zG);
        if (isDefaultApp()) {
            zza(FirebaseApp.class, this, zzb, zG);
            zza(Context.class, this.zzg, zzc, zG);
        }
    }

    public void addBackgroundStateChangeListener(BackgroundStateChangeListener backgroundStateChangeListener) {
        zza();
        if (this.zzj.get() && b.YT().isInBackground()) {
            backgroundStateChangeListener.onBackgroundStateChanged(true);
        }
        this.zzm.add(backgroundStateChangeListener);
    }

    public void addIdTokenListener(IdTokenListener idTokenListener) {
        zza();
        bf.checkNotNull(idTokenListener);
        this.zzl.add(idTokenListener);
        this.zzp.onListenerCountChanged(this.zzl.size());
    }

    public boolean equals(Object obj) {
        if (obj instanceof FirebaseApp) {
            return this.mName.equals(((FirebaseApp) obj).getName());
        }
        return false;
    }

    public <T> T get(Class<T> cls) {
        zza();
        return (T) this.zzi.get(cls);
    }

    public Context getApplicationContext() {
        zza();
        return this.zzg;
    }

    public String getName() {
        zza();
        return this.mName;
    }

    public FirebaseOptions getOptions() {
        zza();
        return this.zzh;
    }

    public String getPersistenceKey() {
        String strQ = com.google.android.gms.common.d.c.q(getName().getBytes());
        String strQ2 = com.google.android.gms.common.d.c.q(getOptions().getApplicationId().getBytes());
        return new StringBuilder(String.valueOf(strQ).length() + 1 + String.valueOf(strQ2).length()).append(strQ).append("+").append(strQ2).toString();
    }

    public f<GetTokenResult> getToken(boolean z) {
        zza();
        return this.zzo == null ? j.i(new FirebaseApiNotAvailableException("firebase-auth is not linked, please fall back to unauthenticated mode.")) : this.zzo.getAccessToken(z);
    }

    public final String getUid() throws FirebaseApiNotAvailableException {
        zza();
        if (this.zzo == null) {
            throw new FirebaseApiNotAvailableException("firebase-auth is not linked, please fall back to unauthenticated mode.");
        }
        return this.zzo.getUid();
    }

    public int hashCode() {
        return this.mName.hashCode();
    }

    public boolean isDefaultApp() {
        return DEFAULT_APP_NAME.equals(getName());
    }

    public void notifyIdTokenListeners(InternalTokenResult internalTokenResult) {
        Log.d("FirebaseApp", "Notifying auth state listeners.");
        Iterator<IdTokenListener> it = this.zzl.iterator();
        int i = 0;
        while (it.hasNext()) {
            it.next().onIdTokenChanged(internalTokenResult);
            i++;
        }
        Log.d("FirebaseApp", String.format("Notified %d auth state listeners.", Integer.valueOf(i)));
    }

    public void removeBackgroundStateChangeListener(BackgroundStateChangeListener backgroundStateChangeListener) {
        zza();
        this.zzm.remove(backgroundStateChangeListener);
    }

    public void setAutomaticResourceManagementEnabled(boolean z) {
        zza();
        if (this.zzj.compareAndSet(!z, z)) {
            boolean zIsInBackground = b.YT().isInBackground();
            if (z && zIsInBackground) {
                zza(true);
            } else {
                if (z || !zIsInBackground) {
                    return;
                }
                zza(false);
            }
        }
    }

    public void setIdTokenListenersCountChangedListener(IdTokenListenersCountChangedListener idTokenListenersCountChangedListener) {
        this.zzp = (IdTokenListenersCountChangedListener) bf.checkNotNull(idTokenListenersCountChangedListener);
        this.zzp.onListenerCountChanged(this.zzl.size());
    }

    public void setTokenProvider(InternalTokenProvider internalTokenProvider) {
        this.zzo = (InternalTokenProvider) bf.checkNotNull(internalTokenProvider);
    }

    public String toString() {
        return bd.C(this).c("name", this.mName).c("options", this.zzh).toString();
    }

    public final void zza(IdTokenListener idTokenListener) {
        zza();
        bf.checkNotNull(idTokenListener);
        this.zzl.remove(idTokenListener);
        this.zzp.onListenerCountChanged(this.zzl.size());
    }
}
