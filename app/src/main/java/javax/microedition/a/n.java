package javax.microedition.a;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.Socket;

/* JADX INFO: compiled from: SocketConnectionImpl.java */
/* JADX INFO: loaded from: classes.dex */
public class n implements m {
    Socket clr;

    public n(String str, int i) {
        int iIndexOf = str.indexOf("://") + 3;
        int iLastIndexOf = str.lastIndexOf(":") + 1;
        this.clr = new Socket(str.substring(iIndexOf, iLastIndexOf - 1), Integer.parseInt(str.substring(iLastIndexOf)));
    }

    @Override // javax.microedition.a.a
    public void close() throws IOException {
        this.clr.close();
    }

    @Override // javax.microedition.a.k
    public InputStream ajO() {
        return this.clr.getInputStream();
    }

    @Override // javax.microedition.a.l
    public OutputStream ajP() {
        return this.clr.getOutputStream();
    }
}
