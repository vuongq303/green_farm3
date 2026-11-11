package javax.microedition.a;

import javax.net.ssl.HostnameVerifier;
import javax.net.ssl.SSLSession;

/* JADX INFO: compiled from: HttpsConnectionImpl.java */
/* JADX INFO: loaded from: classes.dex */
final class j implements HostnameVerifier {
    j() {
    }

    @Override // javax.net.ssl.HostnameVerifier
    public boolean verify(String str, SSLSession sSLSession) {
        return true;
    }
}
