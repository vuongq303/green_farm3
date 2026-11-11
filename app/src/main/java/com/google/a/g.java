package com.google.a;

/* JADX INFO: compiled from: ReaderException.java */
/* JADX INFO: loaded from: classes.dex */
public abstract class g extends Exception {
    protected static final boolean bZO;
    protected static final StackTraceElement[] bZP;

    static {
        bZO = System.getProperty("surefire.test.class.path") != null;
        bZP = new StackTraceElement[0];
    }

    g() {
    }

    @Override // java.lang.Throwable
    public final synchronized Throwable fillInStackTrace() {
        return null;
    }
}
