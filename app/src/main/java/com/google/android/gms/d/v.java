package com.google.android.gms.d;

import dalvik.system.PathClassLoader;

/* JADX INFO: loaded from: classes.dex */
final class v extends PathClassLoader {
    v(String str, ClassLoader classLoader) {
        super(str, classLoader);
    }

    @Override // java.lang.ClassLoader
    protected final Class<?> loadClass(String str, boolean z) {
        if (!str.startsWith("java.") && !str.startsWith("android.")) {
            try {
                return findClass(str);
            } catch (ClassNotFoundException e) {
            }
        }
        return super.loadClass(str, z);
    }
}
