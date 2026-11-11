package android.support.v4.content;

/* JADX INFO: compiled from: ModernAsyncTask.java */
/* JADX INFO: loaded from: classes.dex */
public enum s {
    PENDING,
    RUNNING,
    FINISHED;

    public static s[] am() {
        s[] sVarArrValues = values();
        int length = sVarArrValues.length;
        s[] sVarArr = new s[length];
        System.arraycopy(sVarArrValues, 0, sVarArr, 0, length);
        return sVarArr;
    }
}
