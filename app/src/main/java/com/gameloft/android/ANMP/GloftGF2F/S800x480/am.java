package com.gameloft.android.ANMP.GloftGF2F.S800x480;

import android.os.SystemClock;
import com.gameloft.glads.AndroidWebView;
import java.io.ByteArrayOutputStream;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.InetSocketAddress;
import java.net.Proxy;
import java.net.URL;
import java.security.KeyManagementException;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.util.Enumeration;
import java.util.Hashtable;
import java.util.Vector;
import javax.net.ssl.HttpsURLConnection;
import javax.net.ssl.SSLContext;
import javax.net.ssl.TrustManager;

/* JADX INFO: compiled from: HTTP.java */
/* JADX INFO: loaded from: classes.dex */
public class am implements Runnable {
    private static String acO;
    private static int acP;
    public static String userAgent;
    private final int BF;
    private Thread BG;
    private String BH;
    private String BI;
    private InputStream BK;
    private OutputStream BL;
    public String BM;
    public byte[] BP;
    private boolean BR;
    private boolean BS;
    public boolean BT;
    public int BV;
    public int BW;
    public long BX;
    public int Bg;
    private long acB;
    private boolean acC;
    private HttpURLConnection acD;
    private int acE;
    private boolean acF;
    private boolean acG;
    public String acH;
    public Exception acI;
    private String acJ;
    private String acK;
    private Hashtable acL;
    private Vector acM;
    public Hashtable acN;

    public static void mn() {
        TrustManager[] trustManagerArr = {new an()};
        try {
            SSLContext sSLContext = SSLContext.getInstance("TLS");
            sSLContext.init(null, trustManagerArr, new SecureRandom());
            HttpsURLConnection.setDefaultSSLSocketFactory(sSLContext.getSocketFactory());
        } catch (KeyManagementException e) {
            e.printStackTrace();
        } catch (NoSuchAlgorithmException e2) {
            e2.printStackTrace();
        }
    }

    public void bP(String str) {
        this.acJ = str;
    }

    private void e(Exception exc) {
        this.acI = exc;
    }

    public void am(boolean z) {
        this.acG = z;
    }

    public void C(String str, String str2) {
        if (this.acL == null) {
            this.acL = new Hashtable();
        }
        this.acL.put(str, str2);
    }

    private void mo() {
        this.acL.clear();
    }

    public void bQ(String str) {
        if (this.acM == null) {
            this.acM = new Vector();
        }
        if (!this.acM.contains(str)) {
            this.acM.addElement(str);
        }
        if (this.acN == null) {
            this.acN = new Hashtable();
        }
    }

    public void mp() {
        this.acM.removeAllElements();
    }

    public void bR(String str) {
        this.acK = str;
    }

    public am() {
        this(true);
    }

    public am(boolean z) {
        this.BF = 256;
        this.acC = z;
        bR("application/x-www-form-urlencoded");
        GLLib.c(!z, "modifyPostRequest==true seems like not used anymore, so it's deprecated. Please mail to World-GLLib if you need this.");
        bP("" + hashCode());
    }

    public boolean isInProgress() {
        return this.BR;
    }

    // Recovered from original cancel() exception regions and monitor boundaries.
    public synchronized void cancel() {
        if (this.BS) {
            return;
        }
        this.BS = true;
        if (w.XR) {
            return;
        }
        if (this.acD != null) {
            if (w.XZ) {
                GLLib.b("HTTP:[" + this.acJ + "]: cancel", "HTTP", 3);
            }
            if (this.BK != null) {
                try {
                    synchronized (this.acD) {
                        this.BK.close();
                    }
                } catch (Exception failure) {
                    e(failure);
                }
            }
            if (this.acD != null) {
                try {
                    synchronized (this.acD) {
                        this.acD.disconnect();
                    }
                } catch (Exception failure) {
                    e(failure);
                }
            }
            if (this.BL != null) {
                try {
                    synchronized (this.BL) {
                        this.BL.close();
                    }
                } catch (Exception failure) {
                    e(failure);
                }
            }
            this.BL = null;
        }
        this.BK = null;
        this.acD = null;
        this.BR = false;
        this.BG = null;
    }

    public void bM() {
        cancel();
        this.BT = false;
        this.BM = null;
        this.BP = null;
    }

    public void c(String str, String str2, int i) {
        this.acE = i;
        if (w.Vf) {
        }
        if (w.XZ) {
            GLLib.b("HTTP:[" + this.acJ + "]: " + new String[]{"GET", "POST", "DELETE"}[i] + ": url [" + str + "] [" + str2 + "] ", "HTTP", 3);
        }
        long jCurrentTimeMillis = System.currentTimeMillis();
        if (w.Vl) {
            jCurrentTimeMillis = (SystemClock.elapsedRealtime() + GLLib.Tn) - GLLib.To;
        }
        this.acB = jCurrentTimeMillis;
        mq();
        bM();
        this.BR = true;
        this.BS = false;
        if (i == 1) {
            this.BH = str;
            this.BI = str2;
        } else if (str2 != null && !"".equals(str2)) {
            if (!str2.startsWith("?") && !str.endsWith("?")) {
                str2 = "?" + str2;
            }
            this.BH = str + str2;
        } else {
            this.BH = str;
        }
        this.BT = false;
        this.BG = new Thread(this);
        this.BG.start();
    }

    private void mq() {
        while (this.BR) {
            try {
                synchronized (this) {
                    try {
                        wait(50L);
                    } catch (Throwable th) {
                        throw th;
                    }
                }
            } catch (Exception e) {
                e(e);
            }
        }
    }

    @Override // java.lang.Runnable
    public void run() {
        if (this.BH == null) {
            if (w.XZ) {
                GLLib.b("HTTP:[" + this.acJ + "]: run: URL is null", "HTTP", 3);
            }
            cancel();
            this.BT = true;
            this.BR = false;
            return;
        }
        if (this.acE == 1 && this.BI == null) {
            if (w.XZ) {
                GLLib.b("HTTP:[" + this.acJ + "]: run: Query is null", "HTTP", 3);
            }
            cancel();
            this.BT = true;
            this.BR = false;
            return;
        }
        try {
            try {
                this.BT = false;
                if (w.XZ) {
                    GLLib.b("HTTP:[" + this.acJ + "]: run:connecting to [" + this.BH + "]", "HTTP", 3);
                }
                if (this.BS) {
                    this.BR = false;
                    if (w.XZ) {
                        GLLib.b("HTTP:[" + this.acJ + "]: run: finally", "HTTP", 3);
                    }
                    if (!this.acF) {
                        cancel();
                    }
                    this.acF = false;
                    return;
                }
                String strBy = GLLib.by(this.BH);
                String strBA = GLLib.bA(this.BH);
                String strBC = GLLib.bC(this.BH);
                String strBB = GLLib.bB(this.BH);
                if (strBC == null) {
                    strBC = "https".equals(strBy) ? "443" : "80";
                }
                URL url = new URL(strBy, strBA, Integer.parseInt(strBC), strBB);
                if (acO != null) {
                    this.acD = (HttpURLConnection) url.openConnection(new Proxy(Proxy.Type.HTTP, new InetSocketAddress(acO, acP)));
                } else {
                    this.acD = (HttpURLConnection) url.openConnection();
                }
                if (this.acE == 1) {
                    this.acD.setDoOutput(true);
                } else {
                    this.acD.setDoOutput(false);
                }
                if (this.acE == 2) {
                    this.acD.setRequestMethod("DELETE");
                }
                if (this.acE == 1) {
                    this.acD.setRequestMethod("POST");
                } else if (this.acE == 0) {
                    this.acD.setRequestMethod("GET");
                }
                if (userAgent != null) {
                    this.acD.setRequestProperty("User-Agent", userAgent);
                }
                if (this.acL != null && this.acL.size() > 0) {
                    Enumeration enumerationKeys = this.acL.keys();
                    while (enumerationKeys.hasMoreElements()) {
                        String str = (String) enumerationKeys.nextElement();
                        String str2 = (String) this.acL.get(str);
                        this.acD.setRequestProperty(str, str2);
                        GLLib.b("HTTP:[" + this.acJ + "]: Set header field " + str + ":" + str2, "HTTP", 3);
                    }
                    mo();
                }
                if (this.acE == 1) {
                    this.acD.setRequestProperty("Content-Type", this.acK);
                    String str3 = this.acC ? "b=" + this.BI : this.BI;
                    this.acD.setRequestProperty("Content-Length", String.valueOf(str3.length()));
                    this.BL = this.acD.getOutputStream();
                    this.BL.write(str3.getBytes(), 0, str3.length());
                    this.BL.flush();
                }
                if (w.XZ) {
                    GLLib.b("HTTP:[" + this.acJ + "]: run: receive", "HTTP", 3);
                }
                this.Bg = this.acD.getResponseCode();
                this.acH = this.acD.getResponseMessage();
                this.BX = this.acD.getDate();
                if (this.Bg == 302) {
                    this.BR = false;
                    this.acF = true;
                    c(this.acD.getHeaderField("Location"), this.BI, this.acE);
                    if (w.XZ) {
                        GLLib.b("HTTP:[" + this.acJ + "]: run: finally", "HTTP", 3);
                    }
                    if (!this.acF) {
                        cancel();
                    }
                    this.acF = false;
                    return;
                }
                if (this.acM != null && this.acM.size() > 0) {
                    for (int i = 0; i < this.acM.size(); i++) {
                        String str4 = (String) this.acM.elementAt(i);
                        String headerField = this.acD.getHeaderField(str4);
                        if (headerField != null) {
                            if (w.XZ) {
                                GLLib.b("HTTP:[" + this.acJ + "]: Get header field " + str4 + ":" + headerField, "HTTP", 3);
                            }
                            this.acN.put(str4, headerField);
                        }
                    }
                    mp();
                }
                if (this.Bg != 200 && this.Bg != 202) {
                    if (w.XZ) {
                        GLLib.b("HTTP:[" + this.acJ + "]:  Error: response code: " + this.Bg, "HTTP", 3);
                        GLLib.b("HTTP:[" + this.acJ + "]:  Error: message: " + this.acH, "HTTP", 3);
                    }
                    cancel();
                    this.BT = true;
                    this.BR = false;
                    if (w.XZ) {
                        GLLib.b("HTTP:[" + this.acJ + "]: run: finally", "HTTP", 3);
                    }
                    if (!this.acF) {
                        cancel();
                    }
                    this.acF = false;
                    return;
                }
                if (this.BS) {
                    this.BR = false;
                    if (w.XZ) {
                        GLLib.b("HTTP:[" + this.acJ + "]: run: finally", "HTTP", 3);
                    }
                    if (!this.acF) {
                        cancel();
                    }
                    this.acF = false;
                    return;
                }
                synchronized (this.acD) {
                    this.BK = this.acD.getInputStream();
                }
                Thread.yield();
                ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
                if (this.acG) {
                    int contentLength = this.acD.getContentLength();
                    this.BV = contentLength;
                    this.BW = 0;
                    if (contentLength > 0) {
                        this.BP = new byte[contentLength];
                    }
                }
                byte[] bArr = new byte[256];
                int i2 = 0;
                int i3 = 0;
                while (i3 != -1) {
                    if (this.BS) {
                        this.BR = false;
                        if (w.XZ) {
                            GLLib.b("HTTP:[" + this.acJ + "]: run: finally", "HTTP", 3);
                        }
                        if (!this.acF) {
                            cancel();
                        }
                        this.acF = false;
                        return;
                    }
                    if (w.XP) {
                        long jCurrentTimeMillis = System.currentTimeMillis();
                        if (w.Vl) {
                            jCurrentTimeMillis = (SystemClock.elapsedRealtime() + GLLib.Tn) - GLLib.To;
                        }
                        if (jCurrentTimeMillis - this.acB > w.XQ) {
                            cancel();
                            if (w.XZ) {
                                GLLib.b("HTTP:[" + this.acJ + "]: run: finally", "HTTP", 3);
                            }
                            if (!this.acF) {
                                cancel();
                            }
                            this.acF = false;
                            return;
                        }
                    }
                    if (!w.XS) {
                        for (int i4 = 0; i4 < 256; i4++) {
                            bArr[i4] = 0;
                        }
                    }
                    i3 = this.BK.read(bArr, 0, 256);
                    if (i3 == -1) {
                        break;
                    }
                    if (!w.XS && !this.acG) {
                        int i5 = 255;
                        while (i5 >= 0 && bArr[i5] == 0) {
                            i5--;
                        }
                        i3 = i5 + 1;
                    }
                    if (!this.acG) {
                        byteArrayOutputStream.write(bArr, 0, i3);
                    } else if (i3 > 0) {
                        if (i2 + i3 <= this.BP.length) {
                            System.arraycopy(bArr, 0, this.BP, i2, i3);
                            int i6 = i2 + i3;
                            this.BW = i6;
                            i2 = i6;
                        } else if (w.XZ) {
                            GLLib.b("HTTP:[" + this.acJ + "]: run: file size [ " + this.BP.length + " ] is smaller than the file received [ " + (i2 + i3) + " ]", "HTTP", 3);
                        }
                    }
                }
                if (w.XZ) {
                    if (this.acG) {
                        GLLib.b("HTTP:[" + this.acJ + "]: run: total bytes read: [" + i2 + "]", "HTTP", 3);
                    } else {
                        GLLib.b("HTTP:[" + this.acJ + "]: run: received [" + byteArrayOutputStream.toString() + "]", "HTTP", 3);
                        GLLib.b("HTTP:[" + this.acJ + "]: run: total bytes read: [" + byteArrayOutputStream.size() + "]", "HTTP", 3);
                    }
                }
                if (!this.acG) {
                    this.BP = byteArrayOutputStream.toByteArray();
                    this.BM = new String(this.BP, AndroidWebView.UTF_8);
                }
                if (w.XZ) {
                    GLLib.b("HTTP:[" + this.acJ + "]: run: finally", "HTTP", 3);
                }
                if (!this.acF) {
                    cancel();
                }
                this.acF = false;
            } catch (Exception e) {
                if (w.XZ) {
                    GLLib.b("HTTP:[" + this.acJ + "]: run: exception : " + e.toString(), "HTTP", 3);
                    GLLib.d(e);
                }
                this.BT = true;
                this.BR = false;
                e(e);
                if (w.XZ) {
                    GLLib.b("HTTP:[" + this.acJ + "]: run: finally", "HTTP", 3);
                }
                if (!this.acF) {
                    cancel();
                }
                this.acF = false;
            }
        } catch (Throwable th) {
            if (w.XZ) {
                GLLib.b("HTTP:[" + this.acJ + "]: run: finally", "HTTP", 3);
            }
            if (!this.acF) {
                cancel();
            }
            this.acF = false;
            throw th;
        }
    }

    public String toString() {
        return this.acJ + ": " + this.BH;
    }
}
