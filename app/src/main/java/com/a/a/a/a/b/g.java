package com.a.a.a.a.b;

/* JADX INFO: loaded from: classes.dex */
public class g {
    private final String name;
    private final String version;

    private g(String str, String str2) {
        this.name = str;
        this.version = str2;
    }

    public static g aB(String str, String str2) {
        com.a.a.a.a.e.f.aC(str, "Name is null or empty");
        com.a.a.a.a.e.f.aC(str2, "Version is null or empty");
        return new g(str, str2);
    }

    public String getName() {
        return this.name;
    }

    public String getVersion() {
        return this.version;
    }
}
