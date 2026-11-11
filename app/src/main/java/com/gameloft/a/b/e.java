package com.gameloft.a.b;

import com.gameloft.android.wrapper.au;
import com.gameloft.glads.AndroidWebView;
import java.io.ByteArrayOutputStream;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.InetSocketAddress;
import java.net.Proxy;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;

/* JADX INFO: compiled from: JW_HTTP.java */
/* JADX INFO: loaded from: classes.dex */
public class e implements Runnable {
    private static String acO;
    private static int acP;
    public static String userAgent;
    private Thread BG;
    private String BH;
    private String BI;
    private InputStream BK;
    private OutputStream BL;
    public String BM;
    public byte[] BP;
    private boolean BR;
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
    private boolean bwS;
    private boolean bwT;
    private f bwU;
    private boolean bwV;
    private HashMap<String, String> bwX;
    private ArrayList<String> bwY;
    public HashMap<String, String> bwZ;
    private final int bwQ = 1024;
    private boolean bwR = true;
    byte[] bwW = new byte[1024];

    public void bP(String str) {
        this.acJ = str;
    }

    private void e(Exception exc) {
        this.acI = exc;
    }

    public void C(String str, String str2) {
        if (this.bwX == null) {
            this.bwX = new HashMap<>();
        }
        this.bwX.put(str, str2);
    }

    private void mo() {
        this.bwX.clear();
    }

    public void bQ(String str) {
        if (this.bwY == null) {
            this.bwY = new ArrayList<>();
        }
        if (!this.bwY.contains(str)) {
            this.bwY.add(str);
        }
        if (this.bwZ == null) {
            this.bwZ = new HashMap<>();
        }
    }

    public void mp() {
        this.bwY.clear();
    }

    public void bR(String str) {
        this.acK = str;
    }

    public e() {
        bR("application/x-www-form-urlencoded");
        bP("" + hashCode());
    }

    public synchronized void cancel() {
        if (!this.bwS) {
            this.bwS = true;
            if (this.acD != null) {
                if (this.BK != null) {
                    try {
                        synchronized (this.acD) {
                            this.BK.close();
                        }
                    } catch (Exception e) {
                        e(e);
                    }
                }
                if (this.acD != null) {
                    try {
                        synchronized (this.acD) {
                            this.acD.disconnect();
                        }
                    } catch (Exception e2) {
                        e(e2);
                    }
                }
                if (this.BL != null) {
                    try {
                        synchronized (this.BL) {
                            this.BL.close();
                        }
                    } catch (Exception e3) {
                        e(e3);
                    }
                }
                this.BL = null;
            }
            this.BK = null;
            this.acD = null;
            this.BR = false;
            this.BG = null;
            au.Qi();
            if (this.bwV && this.bwU != null) {
                this.bwU.Wu();
            }
            this.bwV = false;
        }
    }

    public void bM() {
        cancel();
        this.BT = false;
        this.BM = null;
        this.BP = null;
    }

    public void c(String str, String str2, int i) {
        a(str, str2, i, true);
    }

    public void a(String str, String str2, int i, boolean z) {
        this.acE = i;
        String[] strArr = {"GET", "POST", "DELETE", "HEAD"};
        mq();
        bM();
        this.BR = true;
        this.bwS = false;
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
        if (z) {
            this.BG = new Thread(this);
            this.BG.start();
        }
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
        this.acB = System.currentTimeMillis();
        if (this.BH == null) {
            this.bwV = true;
            cancel();
            this.BT = true;
            this.BR = false;
            return;
        }
        if (this.acE == 1 && this.BI == null) {
            this.bwV = true;
            cancel();
            this.BT = true;
            this.BR = false;
            return;
        }
        try {
            try {
                this.BT = false;
                if (this.bwS) {
                    this.BR = false;
                    if (!this.acF) {
                        this.bwV = true;
                        cancel();
                    }
                    this.acF = false;
                    return;
                }
                String strBy = by(this.BH);
                String strBA = bA(this.BH);
                String strBC = bC(this.BH);
                String strBB = bB(this.BH);
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
                if (this.acE == 3) {
                    this.acD.setRequestMethod("HEAD");
                    this.acD.setRequestProperty("Accept-Encoding", "");
                }
                if (this.acE == 1) {
                    this.acD.setRequestMethod("POST");
                } else if (this.acE == 0) {
                    this.acD.setRequestMethod("GET");
                }
                if (userAgent != null) {
                    this.acD.setRequestProperty("User-Agent", userAgent);
                }
                if (this.bwX != null && this.bwX.size() > 0) {
                    for (String str : this.bwX.keySet()) {
                        this.acD.setRequestProperty(str, this.bwX.get(str));
                    }
                    mo();
                }
                if (this.acE == 1) {
                    this.acD.setRequestProperty("Content-Type", this.acK);
                    String str2 = this.acC ? "b=" + this.BI : this.BI;
                    this.acD.setRequestProperty("Content-Length", String.valueOf(str2.length()));
                    this.BL = this.acD.getOutputStream();
                    this.BL.write(str2.getBytes());
                    this.BL.flush();
                }
                this.Bg = this.acD.getResponseCode();
                this.acH = this.acD.getResponseMessage();
                this.BX = this.acD.getDate();
                if (this.Bg == 302) {
                    this.BR = false;
                    this.acF = true;
                    c(this.acD.getHeaderField("Location"), this.BI, this.acE);
                    if (!this.acF) {
                        this.bwV = true;
                        cancel();
                    }
                    this.acF = false;
                    return;
                }
                if (!Wt()) {
                    Ws();
                    this.BM = "Auto redirect is disabled";
                    if (!this.acF) {
                        this.bwV = true;
                        cancel();
                    }
                    this.acF = false;
                    return;
                }
                if (this.Bg == 302) {
                    this.BR = false;
                    this.acF = true;
                    c(this.acD.getHeaderField("Location"), this.BI, this.acE);
                    if (!this.acF) {
                        this.bwV = true;
                        cancel();
                    }
                    this.acF = false;
                    return;
                }
                Ws();
                if (this.Bg != 200 && this.Bg != 202) {
                    this.bwV = true;
                    cancel();
                    this.BT = true;
                    this.BR = false;
                    if (!this.acF) {
                        this.bwV = true;
                        cancel();
                    }
                    this.acF = false;
                    return;
                }
                if (this.bwS) {
                    this.BR = false;
                    if (!this.acF) {
                        this.bwV = true;
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
                int i = 0;
                int i2 = 0;
                while (i2 != -1) {
                    if (this.bwS) {
                        this.BR = false;
                        if (!this.acF) {
                            this.bwV = true;
                            cancel();
                        }
                        this.acF = false;
                        return;
                    }
                    i2 = this.BK.read(this.bwW, 0, 1024);
                    if (i2 == -1) {
                        break;
                    }
                    if (!this.acG) {
                        byteArrayOutputStream.write(this.bwW, 0, i2);
                        if (this.bwT) {
                            String string = byteArrayOutputStream.toString(AndroidWebView.UTF_8);
                            if (string.indexOf(10) != -1) {
                                a(byteArrayOutputStream, string);
                            }
                        }
                    } else if (i2 > 0 && i + i2 <= this.BP.length) {
                        System.arraycopy(this.bwW, 0, this.BP, i, i2);
                        i += i2;
                        this.BW = i;
                    }
                }
                if (!this.acG) {
                }
                if (this.acG) {
                    this.BM = new String(this.BP, AndroidWebView.UTF_8);
                } else {
                    a(byteArrayOutputStream, null);
                }
                if (!this.acF) {
                    this.bwV = true;
                    cancel();
                }
                this.acF = false;
            } catch (Exception e) {
                e.printStackTrace();
                this.BT = true;
                this.BR = false;
                e(e);
                if (!this.acF) {
                    this.bwV = true;
                    cancel();
                }
                this.acF = false;
            }
        } catch (Throwable th) {
            if (!this.acF) {
                this.bwV = true;
                cancel();
            }
            this.acF = false;
            throw th;
        }
    }

    protected void Ws() {
        if (this.bwY != null && this.bwY.size() > 0) {
            int i = 0;
            while (true) {
                int i2 = i;
                if (i2 < this.bwY.size()) {
                    String str = this.bwY.get(i2);
                    try {
                        String headerField = this.acD.getHeaderField(str);
                        if (headerField != null) {
                            this.bwZ.put(str, headerField);
                        }
                    } catch (Exception e) {
                    }
                    i = i2 + 1;
                } else {
                    mp();
                    return;
                }
            }
        }
    }

    private synchronized void a(ByteArrayOutputStream byteArrayOutputStream, String str) {
        try {
            if (str != null) {
                this.BM = str;
            } else {
                this.BM = byteArrayOutputStream.toString(AndroidWebView.UTF_8);
            }
            byteArrayOutputStream.reset();
            if (this.bwU != null) {
                this.bwU.d(this.Bg, this.acH, this.BM);
                if (this.bwT) {
                    this.BM = null;
                }
            }
        } catch (Throwable th) {
            throw th;
        }
    }

    public String toString() {
        return this.acJ + ": " + this.BH;
    }

    public static String by(String str) {
        int iIndexOf = str.indexOf("://");
        if (iIndexOf != -1) {
            return str.substring(0, iIndexOf);
        }
        return null;
    }

    private static String bz(String str) {
        if (by(str) != null) {
            str = bD(str);
        }
        int iIndexOf = str.indexOf("/");
        if (iIndexOf != -1) {
            str = str.substring(0, iIndexOf);
        }
        return "" + str;
    }

    public static String bA(String str) {
        String strBz = bz(str);
        int iIndexOf = strBz.indexOf(":");
        if (iIndexOf != -1) {
            return strBz.substring(0, iIndexOf);
        }
        return strBz;
    }

    public boolean Wt() {
        return this.bwR;
    }

    public static String bB(String str) {
        if (by(str) != null) {
            str = bD(str);
        }
        int iIndexOf = str.indexOf("/");
        return iIndexOf != -1 ? str.substring(iIndexOf) : "";
    }

    public static String bC(String str) {
        String strBz = bz(str);
        int iIndexOf = strBz.indexOf(":");
        if (iIndexOf != -1) {
            return strBz.substring(iIndexOf + 1, strBz.length());
        }
        return null;
    }

    public static String bD(String str) {
        return str.substring(by(str).length() + 3);
    }
}
