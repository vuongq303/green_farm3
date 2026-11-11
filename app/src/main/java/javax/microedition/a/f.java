package javax.microedition.a;

import com.gameloft.glads.GLURLConnection;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.ProtocolException;
import java.net.URL;
import java.net.URLConnection;

/* JADX INFO: compiled from: HttpConnectionImpl.java */
/* JADX INFO: loaded from: classes.dex */
public class f implements e {
    URLConnection clo;
    HttpURLConnection clp;

    public f(String str, int i) throws ProtocolException {
        this.clo = new URL(str).openConnection();
        if (this.clo instanceof HttpURLConnection) {
            this.clp = (HttpURLConnection) this.clo;
            this.clp.setRequestMethod("GET");
        }
        this.clo.setDoInput((i & 1) != 0);
        this.clo.setDoOutput((i & 2) != 0);
    }

    @Override // javax.microedition.a.e
    public void setRequestMethod(String str) throws ProtocolException {
        if (this.clp != null) {
            this.clp.setRequestMethod(str);
        }
    }

    @Override // javax.microedition.a.e
    public void setRequestProperty(String str, String str2) {
        this.clo.setRequestProperty(str, str2);
    }

    @Override // javax.microedition.a.e
    public int getResponseCode() {
        return this.clp != null ? this.clp.getResponseCode() : GLURLConnection.HTTP_OK;
    }

    @Override // javax.microedition.a.e
    public long getDate() {
        return this.clo.getDate();
    }

    @Override // javax.microedition.a.e
    public String getHeaderField(String str) {
        return this.clo.getHeaderField(str);
    }

    @Override // javax.microedition.a.e
    public int getHeaderFieldInt(String str, int i) {
        return this.clo.getHeaderFieldInt(str, i);
    }

    @Override // javax.microedition.a.a
    public void close() {
        if (this.clp != null) {
            this.clp.disconnect();
        }
    }

    @Override // javax.microedition.a.k
    public InputStream ajO() {
        return this.clo.getInputStream();
    }

    @Override // javax.microedition.a.l
    public OutputStream ajP() {
        return this.clo.getOutputStream();
    }
}
