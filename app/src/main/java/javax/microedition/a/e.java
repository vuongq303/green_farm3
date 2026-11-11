package javax.microedition.a;

/* JADX INFO: compiled from: HttpConnection.java */
/* JADX INFO: loaded from: classes.dex */
public interface e extends d {
    long getDate();

    String getHeaderField(String str);

    int getHeaderFieldInt(String str, int i);

    int getResponseCode();

    void setRequestMethod(String str);

    void setRequestProperty(String str, String str2);
}
