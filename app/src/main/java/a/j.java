package a;

import java.util.Locale;

/* JADX INFO: compiled from: CancellationToken.java */
/* JADX INFO: loaded from: classes.dex */
public class j {
    private final l zy;

    public boolean fw() {
        return this.zy.fw();
    }

    public String toString() {
        return String.format(Locale.US, "%s@%s[cancellationRequested=%s]", getClass().getName(), Integer.toHexString(hashCode()), Boolean.toString(this.zy.fw()));
    }
}
