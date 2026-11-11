package javax.microedition.a;

import android.os.Build;
import com.gameloft.glads.GLURLConnection;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.URL;
import java.net.URLConnection;
import java.security.SecureRandom;
import javax.net.ssl.HttpsURLConnection;
import javax.net.ssl.SSLContext;
import javax.net.ssl.TrustManager;

/* JADX INFO: compiled from: HttpsConnectionImpl.java */
/* JADX INFO: loaded from: classes.dex */
public class h implements g {
    boolean AL = false;
    URLConnection clo;
    HttpsURLConnection clq;

    private static void ajQ() {
        TrustManager[] trustManagerArr = {new i()};
        try {
            if (Build.VERSION.SDK_INT < 16) {
                SSLContext sSLContext = SSLContext.getInstance("TLS");
                sSLContext.init(null, trustManagerArr, new SecureRandom());
                HttpsURLConnection.setDefaultSSLSocketFactory(sSLContext.getSocketFactory());
            } else {
                HttpsURLConnection.setDefaultSSLSocketFactory(new p());
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        HttpsURLConnection.setDefaultHostnameVerifier(new j());
    }

    public h(String str, int i) {
        ajQ();
        this.clo = new URL(str).openConnection();
        if (this.clo instanceof HttpsURLConnection) {
            this.clq = (HttpsURLConnection) this.clo;
        }
        this.clo.setDoInput((i & 1) != 0);
        this.clo.setDoOutput((i & 2) != 0);
        if (this.AL && this.clq == null) {
        }
    }

    @Override // javax.microedition.a.e
    public void setRequestMethod(String str) {
        if (this.clq != null) {
            if (this.AL) {
                String str2 = "HttpsConnectionIml.setRequestMethod:::" + str;
            }
            this.clq.setRequestMethod(str);
        }
    }

    @Override // javax.microedition.a.e
    public void setRequestProperty(String str, String str2) {
        if (this.AL) {
            String str3 = "HttpsConnectionIml.setRequestProperty::::" + str + ":::" + str2;
        }
        this.clo.setRequestProperty(str, str2);
    }

    @Override // javax.microedition.a.e
    public int getResponseCode() {
        if (this.AL) {
            String str = "HttpsConnectionIml.getResponseCode::::" + (this.clq != null ? this.clq.getResponseCode() : 200);
        }
        return this.clq != null ? this.clq.getResponseCode() : GLURLConnection.HTTP_OK;
    }

    @Override // javax.microedition.a.e
    public long getDate() {
        if (this.AL) {
            String str = "HttpsConnectionIml.getDate()::" + this.clo.getDate();
        }
        return this.clo.getDate();
    }

    @Override // javax.microedition.a.e
    public String getHeaderField(String str) {
        if (this.AL) {
            String str2 = "HttpsConnectionIml.getHeaderField:::" + this.clo.getHeaderField(str);
        }
        return this.clo.getHeaderField(str);
    }

    @Override // javax.microedition.a.e
    public int getHeaderFieldInt(String str, int i) {
        if (this.AL) {
            String str2 = "HttpsConnectionIml.getHeaderFieldInt:::" + this.clo.getHeaderFieldInt(str, i);
        }
        return this.clo.getHeaderFieldInt(str, i);
    }

    @Override // javax.microedition.a.a
    public void close() {
        if (this.clq != null) {
            if (this.AL) {
            }
            this.clq.disconnect();
        }
    }

    @Override // javax.microedition.a.k
    public InputStream ajO() {
        if (this.AL) {
        }
        return this.clo.getInputStream();
    }

    @Override // javax.microedition.a.l
    public OutputStream ajP() {
        if (this.AL) {
        }
        return this.clo.getOutputStream();
    }
}
