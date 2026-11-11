package com.google.android.gms.d;

import android.content.Context;
import android.database.Cursor;
import android.net.Uri;
import android.os.IBinder;
import android.os.RemoteException;
import android.util.Log;
import com.google.android.gms.common.internal.bf;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import javax.annotation.concurrent.GuardedBy;

/* JADX INFO: loaded from: classes.dex */
public final class a {

    @GuardedBy("DynamiteModule.class")
    private static Boolean bRm;

    @GuardedBy("DynamiteModule.class")
    private static i bRn;

    @GuardedBy("DynamiteModule.class")
    private static l bRo;

    @GuardedBy("DynamiteModule.class")
    private static String bRp;
    private static final ThreadLocal<g> bRq = new ThreadLocal<>();
    private static final e bRr = new o();
    public static final d bRs = new p();
    public static final d bRt = new q();
    public static final d bRu = new r();
    public static final d bRv = new s();
    public static final d bRw = new t();
    public static final d bRx = new u();
    private final Context bRy;

    private a(Context context) {
        this.bRy = (Context) bf.checkNotNull(context);
    }

    public static int D(Context context, String str) {
        int i;
        try {
            Class<?> clsLoadClass = context.getApplicationContext().getClassLoader().loadClass(new StringBuilder(String.valueOf(str).length() + 61).append("com.google.android.gms.dynamite.descriptors.").append(str).append(".ModuleDescriptor").toString());
            Field declaredField = clsLoadClass.getDeclaredField("MODULE_ID");
            Field declaredField2 = clsLoadClass.getDeclaredField("MODULE_VERSION");
            if (declaredField.get(null).equals(str)) {
                i = declaredField2.getInt(null);
            } else {
                String strValueOf = String.valueOf(declaredField.get(null));
                Log.e("DynamiteModule", new StringBuilder(String.valueOf(strValueOf).length() + 51 + String.valueOf(str).length()).append("Module descriptor id '").append(strValueOf).append("' didn't match expected id '").append(str).append("'").toString());
                i = 0;
            }
            return i;
        } catch (ClassNotFoundException e) {
            Log.w("DynamiteModule", new StringBuilder(String.valueOf(str).length() + 45).append("Local module descriptor class for ").append(str).append(" not found.").toString());
            return 0;
        } catch (Exception e2) {
            String strValueOf2 = String.valueOf(e2.getMessage());
            Log.e("DynamiteModule", strValueOf2.length() != 0 ? "Failed to load module descriptor class: ".concat(strValueOf2) : new String("Failed to load module descriptor class: "));
            return 0;
        }
    }

    public static int E(Context context, String str) {
        return a(context, str, false);
    }

    private static a F(Context context, String str) {
        String strValueOf = String.valueOf(str);
        Log.i("DynamiteModule", strValueOf.length() != 0 ? "Selected local version of ".concat(strValueOf) : new String("Selected local version of "));
        return new a(context.getApplicationContext());
    }

    public static int a(Context context, String str, boolean z) {
        synchronized (a.class) {
            Boolean bool = bRm;
            if (bool == null) {
                try {
                    Class<?> clsLoadClass = context.getApplicationContext().getClassLoader().loadClass(b.class.getName());
                    Field declaredField = clsLoadClass.getDeclaredField("sClassLoader");
                    synchronized (clsLoadClass) {
                        try {
                            ClassLoader classLoader = (ClassLoader) declaredField.get(null);
                            if (classLoader != null) {
                                if (classLoader == ClassLoader.getSystemClassLoader()) {
                                    bool = Boolean.FALSE;
                                } else {
                                    try {
                                        a(classLoader);
                                    } catch (c e) {
                                    }
                                    bool = Boolean.TRUE;
                                }
                            } else if ("com.google.android.gms".equals(context.getApplicationContext().getPackageName())) {
                                declaredField.set(null, ClassLoader.getSystemClassLoader());
                                bool = Boolean.FALSE;
                            } else {
                                try {
                                    int iC = c(context, str, z);
                                    if (bRp == null || bRp.isEmpty()) {
                                        return iC;
                                    }
                                    v vVar = new v(bRp, ClassLoader.getSystemClassLoader());
                                    a(vVar);
                                    declaredField.set(null, vVar);
                                    bRm = Boolean.TRUE;
                                    return iC;
                                } catch (c e2) {
                                    declaredField.set(null, ClassLoader.getSystemClassLoader());
                                    bool = Boolean.FALSE;
                                }
                            }
                            bRm = bool;
                        } catch (Throwable th) {
                            throw th;
                        }
                    }
                } catch (ClassNotFoundException | IllegalAccessException | NoSuchFieldException e3) {
                    String strValueOf = String.valueOf(e3);
                    Log.w("DynamiteModule", new StringBuilder(String.valueOf(strValueOf).length() + 30).append("Failed to load module via V2: ").append(strValueOf).toString());
                    bool = Boolean.FALSE;
                }
            }
            if (!bool.booleanValue()) {
                return b(context, str, z);
            }
            try {
                return c(context, str, z);
            } catch (c e4) {
                String strValueOf2 = String.valueOf(e4.getMessage());
                Log.w("DynamiteModule", strValueOf2.length() != 0 ? "Failed to retrieve remote module version: ".concat(strValueOf2) : new String("Failed to retrieve remote module version: "));
                return 0;
            }
        }
    }

    private static Context a(Context context, String str, int i, Cursor cursor, l lVar) {
        try {
            return (Context) com.google.android.gms.c.d.b(lVar.a(com.google.android.gms.c.d.F(context), str, i, com.google.android.gms.c.d.F(cursor)));
        } catch (Exception e) {
            String strValueOf = String.valueOf(e.toString());
            Log.e("DynamiteModule", strValueOf.length() != 0 ? "Failed to load DynamiteLoader: ".concat(strValueOf) : new String("Failed to load DynamiteLoader: "));
            return null;
        }
    }

    public static a a(Context context, d dVar, String str) {
        g gVar = bRq.get();
        g gVar2 = new g(null);
        bRq.set(gVar2);
        try {
            f fVarA = dVar.a(context, str, bRr);
            Log.i("DynamiteModule", new StringBuilder(String.valueOf(str).length() + 68 + String.valueOf(str).length()).append("Considering local module ").append(str).append(":").append(fVarA.bRz).append(" and remote module ").append(str).append(":").append(fVarA.bRA).toString());
            if (fVarA.bRB == 0 || ((fVarA.bRB == -1 && fVarA.bRz == 0) || (fVarA.bRB == 1 && fVarA.bRA == 0))) {
                throw new c(new StringBuilder(91).append("No acceptable module found. Local version is ").append(fVarA.bRz).append(" and remote version is ").append(fVarA.bRA).append(".").toString(), (o) null);
            }
            if (fVarA.bRB == -1) {
                a aVarF = F(context, str);
                if (gVar2.bRC != null) {
                    gVar2.bRC.close();
                }
                bRq.set(gVar);
                return aVarF;
            }
            if (fVarA.bRB != 1) {
                throw new c(new StringBuilder(47).append("VersionPolicy returned invalid code:").append(fVarA.bRB).toString(), (o) null);
            }
            try {
                a aVarA = a(context, str, fVarA.bRA);
                if (gVar2.bRC != null) {
                    gVar2.bRC.close();
                }
                bRq.set(gVar);
                return aVarA;
            } catch (c e) {
                String strValueOf = String.valueOf(e.getMessage());
                Log.w("DynamiteModule", strValueOf.length() != 0 ? "Failed to load remote module: ".concat(strValueOf) : new String("Failed to load remote module: "));
                if (fVarA.bRz == 0 || dVar.a(context, str, new h(fVarA.bRz, 0)).bRB != -1) {
                    throw new c("Remote load failed. No local fallback found.", e, null);
                }
                a aVarF2 = F(context, str);
                if (gVar2.bRC != null) {
                    gVar2.bRC.close();
                }
                bRq.set(gVar);
                return aVarF2;
            }
        } catch (Throwable th) {
            if (gVar2.bRC != null) {
                gVar2.bRC.close();
            }
            bRq.set(gVar);
            throw th;
        }
    }

    private static a a(Context context, String str, int i) throws c {
        Boolean bool;
        synchronized (a.class) {
            bool = bRm;
        }
        if (bool == null) {
            throw new c("Failed to determine which loading route to use.", (o) null);
        }
        return bool.booleanValue() ? c(context, str, i) : b(context, str, i);
    }

    @GuardedBy("DynamiteModule.class")
    private static void a(ClassLoader classLoader) throws c {
        try {
            bRo = m.r((IBinder) classLoader.loadClass("com.google.android.gms.dynamiteloader.DynamiteLoaderV2").getConstructor(new Class[0]).newInstance(new Object[0]));
        } catch (ClassNotFoundException | IllegalAccessException | InstantiationException | NoSuchMethodException | InvocationTargetException e) {
            throw new c("Failed to instantiate dynamite loader", e, null);
        }
    }

    private static i aW(Context context) {
        synchronized (a.class) {
            if (bRn != null) {
                return bRn;
            }
            if (com.google.android.gms.common.g.YD().isGooglePlayServicesAvailable(context) != 0) {
                return null;
            }
            try {
                i iVarQ = j.q((IBinder) context.createPackageContext("com.google.android.gms", 3).getClassLoader().loadClass("com.google.android.gms.chimera.container.DynamiteLoaderImpl").newInstance());
                if (iVarQ != null) {
                    bRn = iVarQ;
                    return iVarQ;
                }
            } catch (Exception e) {
                String strValueOf = String.valueOf(e.getMessage());
                Log.e("DynamiteModule", strValueOf.length() != 0 ? "Failed to load IDynamiteLoader from GmsCore: ".concat(strValueOf) : new String("Failed to load IDynamiteLoader from GmsCore: "));
            }
            return null;
        }
    }

    private static int b(Context context, String str, boolean z) {
        i iVarAW = aW(context);
        if (iVarAW == null) {
            return 0;
        }
        try {
            return iVarAW.a(com.google.android.gms.c.d.F(context), str, z);
        } catch (RemoteException e) {
            String strValueOf = String.valueOf(e.getMessage());
            Log.w("DynamiteModule", strValueOf.length() != 0 ? "Failed to retrieve remote module version: ".concat(strValueOf) : new String("Failed to retrieve remote module version: "));
            return 0;
        }
    }

    private static a b(Context context, String str, int i) throws c {
        o oVar = null;
        Log.i("DynamiteModule", new StringBuilder(String.valueOf(str).length() + 51).append("Selected remote version of ").append(str).append(", version >= ").append(i).toString());
        i iVarAW = aW(context);
        if (iVarAW == null) {
            throw new c("Failed to create IDynamiteLoader.", oVar);
        }
        try {
            com.google.android.gms.c.a aVarA = iVarAW.a(com.google.android.gms.c.d.F(context), str, i);
            if (com.google.android.gms.c.d.b(aVarA) == null) {
                throw new c("Failed to load remote module.", oVar);
            }
            return new a((Context) com.google.android.gms.c.d.b(aVarA));
        } catch (RemoteException e) {
            throw new c("Failed to load remote module.", e, oVar);
        }
    }

    /* JADX WARN: Code duplicated, block: B:15:0x0029  */
    private static int c(Context context, String str, boolean z) throws Throwable {
        Cursor cursor = null;
        try {
            try {
                Cursor cursorD = d(context, str, z);
                if (cursorD != null) {
                    try {
                        if (cursorD.moveToFirst()) {
                            int i = cursorD.getInt(0);
                            if (i > 0) {
                                synchronized (a.class) {
                                    bRp = cursorD.getString(2);
                                }
                                g gVar = bRq.get();
                                if (gVar != null && gVar.bRC == null) {
                                    gVar.bRC = cursorD;
                                    cursorD = null;
                                }
                            }
                            if (cursorD != null) {
                                cursorD.close();
                            }
                            return i;
                        }
                    } catch (Exception e) {
                        e = e;
                        if (e instanceof c) {
                            throw e;
                        }
                        throw new c("V2 version check failed", e, null);
                    }
                }
                Log.w("DynamiteModule", "Failed to retrieve remote module version.");
                throw new c("Failed to connect to dynamite module ContentResolver.", (o) null);
            } catch (Exception e2) {
                e = e2;
            } catch (Throwable th) {
                th = th;
                if (0 != 0) {
                    cursor.close();
                }
                throw th;
            }
        } catch (Throwable th2) {
            th = th2;
            if (0 != 0) {
                cursor.close();
            }
            throw th;
        }
    }

    private static a c(Context context, String str, int i) throws c {
        l lVar;
        o oVar = null;
        Log.i("DynamiteModule", new StringBuilder(String.valueOf(str).length() + 51).append("Selected remote version of ").append(str).append(", version >= ").append(i).toString());
        synchronized (a.class) {
            lVar = bRo;
        }
        if (lVar == null) {
            throw new c("DynamiteLoaderV2 was not cached.", oVar);
        }
        g gVar = bRq.get();
        if (gVar == null || gVar.bRC == null) {
            throw new c("No result cursor", oVar);
        }
        Context contextA = a(context.getApplicationContext(), str, i, gVar.bRC, lVar);
        if (contextA == null) {
            throw new c("Failed to get module context", oVar);
        }
        return new a(contextA);
    }

    public static Cursor d(Context context, String str, boolean z) {
        return context.getContentResolver().query(s(str, z), null, null, null, null);
    }

    public static Uri s(String str, boolean z) {
        String str2 = z ? "api_force_staging" : "api";
        return Uri.parse(new StringBuilder(String.valueOf(str2).length() + 42 + String.valueOf(str).length()).append("content://com.google.android.gms.chimera/").append(str2).append("/").append(str).toString());
    }

    public final Context abR() {
        return this.bRy;
    }

    public final IBinder ip(String str) throws c {
        try {
            return (IBinder) this.bRy.getClassLoader().loadClass(str).newInstance();
        } catch (ClassNotFoundException | IllegalAccessException | InstantiationException e) {
            String strValueOf = String.valueOf(str);
            throw new c(strValueOf.length() != 0 ? "Failed to instantiate module class: ".concat(strValueOf) : new String("Failed to instantiate module class: "), e, null);
        }
    }
}
