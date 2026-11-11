package com.google.android.gms.auth;

import android.accounts.Account;
import android.content.ComponentName;
import android.content.Context;
import android.os.Bundle;
import android.os.RemoteException;
import android.os.SystemClock;
import android.text.TextUtils;
import com.google.android.gms.common.internal.ab;
import com.google.android.gms.common.internal.bf;
import com.google.android.gms.common.m;
import java.io.IOException;

/* JADX INFO: loaded from: classes.dex */
public class h {
    private static final String[] bHw = {"com.google", "com.google.work", m.FEATURE_SIDEWINDER};
    public static final String KEY_CALLER_UID = "callerUid";
    public static final String KEY_ANDROID_PACKAGE_NAME = "androidPackageName";
    private static final ComponentName bHx = new ComponentName("com.google.android.gms", "com.google.android.gms.auth.GetToken");
    private static final com.google.android.gms.common.b.a bHy = new com.google.android.gms.common.b.a("Auth", "GoogleAuthUtil");

    /* JADX WARN: Type inference fix 'apply assigned field type' failed
    java.lang.UnsupportedOperationException: ArgType.getObject(), call class: class jadx.core.dex.instructions.args.ArgType$UnknownArg
    	at jadx.core.dex.instructions.args.ArgType.getObject(ArgType.java:596)
    	at jadx.core.dex.attributes.nodes.ClassTypeVarsAttr.getTypeVarsMapFor(ClassTypeVarsAttr.java:35)
    	at jadx.core.dex.nodes.utils.TypeUtils.replaceClassGenerics(TypeUtils.java:177)
    	at jadx.core.dex.visitors.typeinference.FixTypesVisitor.insertExplicitUseCast(FixTypesVisitor.java:397)
    	at jadx.core.dex.visitors.typeinference.FixTypesVisitor.tryFieldTypeWithNewCasts(FixTypesVisitor.java:359)
    	at jadx.core.dex.visitors.typeinference.FixTypesVisitor.applyFieldType(FixTypesVisitor.java:309)
    	at jadx.core.dex.visitors.typeinference.FixTypesVisitor.visit(FixTypesVisitor.java:94)
     */
    private static <T> T a(Context context, ComponentName componentName, k<T> kVar) throws IOException {
        com.google.android.gms.common.a aVar = new com.google.android.gms.common.a();
        ab abVarAH = ab.aH(context);
        try {
            if (!abVarAH.a(componentName, aVar, "GoogleAuthUtil")) {
                throw new IOException("Could not bind to service.");
            }
            try {
                T tF = kVar.f(aVar.Yx());
                abVarAH.b(componentName, aVar, "GoogleAuthUtil");
                return tF;
            } catch (RemoteException | InterruptedException e) {
                bHy.a("GoogleAuthUtil", "Error on service connection.", e);
                throw new IOException("Error on service connection.", e);
            }
        } catch (Throwable th) {
            abVarAH.b(componentName, aVar, "GoogleAuthUtil");
            throw th;
        }
    }

    public static String a(Context context, Account account, String str) {
        return a(context, account, str, new Bundle());
    }

    public static String a(Context context, Account account, String str, Bundle bundle) {
        a(account);
        return b(context, account, str, bundle).XA();
    }

    private static void a(Account account) {
        if (account == null) {
            throw new IllegalArgumentException("Account cannot be null");
        }
        if (TextUtils.isEmpty(account.name)) {
            throw new IllegalArgumentException("Account name cannot be empty!");
        }
        for (String str : bHw) {
            if (str.equals(account.type)) {
                return;
            }
        }
        throw new IllegalArgumentException("Account type not supported");
    }

    public static TokenData b(Context context, Account account, String str, Bundle bundle) throws a {
        bf.hW("Calling this from your main thread can lead to deadlock");
        bf.d(str, (Object) "Scope cannot be empty or null.");
        a(account);
        ensurePlayServicesAvailable(context, 8400000);
        Bundle bundle2 = bundle == null ? new Bundle() : new Bundle(bundle);
        String str2 = context.getApplicationInfo().packageName;
        bundle2.putString("clientPackageName", str2);
        if (TextUtils.isEmpty(bundle2.getString(KEY_ANDROID_PACKAGE_NAME))) {
            bundle2.putString(KEY_ANDROID_PACKAGE_NAME, str2);
        }
        bundle2.putLong("service_connection_start_time_millis", SystemClock.elapsedRealtime());
        return (TokenData) a(context, bHx, new i(account, str, bundle2));
    }

    @Deprecated
    public static String e(Context context, String str, String str2) {
        return a(context, new Account(str, "com.google"), str2);
    }

    private static void ensurePlayServicesAvailable(Context context, int i) throws a {
        try {
            m.ensurePlayServicesAvailable(context.getApplicationContext(), i);
        } catch (com.google.android.gms.common.k e) {
            throw new a(e.getMessage());
        } catch (com.google.android.gms.common.l e2) {
            throw new c(e2.Xz(), e2.getMessage(), e2.getIntent());
        }
    }

    public static void v(Context context, String str) throws IOException, a {
        bf.hW("Calling this from your main thread can lead to deadlock");
        ensurePlayServicesAvailable(context, 8400000);
        Bundle bundle = new Bundle();
        String str2 = context.getApplicationInfo().packageName;
        bundle.putString("clientPackageName", str2);
        if (!bundle.containsKey(KEY_ANDROID_PACKAGE_NAME)) {
            bundle.putString(KEY_ANDROID_PACKAGE_NAME, str2);
        }
        a(context, bHx, new j(str, bundle));
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static <T> T w(T t) throws IOException {
        if (t != null) {
            return t;
        }
        bHy.b("GoogleAuthUtil", "Binder call returned null.");
        throw new IOException("Service unavailable.");
    }
}
