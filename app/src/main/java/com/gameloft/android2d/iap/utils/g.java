package com.gameloft.android2d.iap.utils;

import android.os.Build;
import android.text.TextUtils;
import com.facebook.appevents.AppEventsConstants;
import com.facebook.share.internal.ShareConstants;
import com.gameloft.android.wrapper.au;
import com.gameloft.android2d.iap.af;
import java.io.ByteArrayOutputStream;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.InetAddress;
import java.net.InetSocketAddress;
import java.net.Proxy;
import java.net.SocketException;
import java.net.URL;
import java.net.UnknownHostException;
import java.security.SecureRandom;
import javax.net.ssl.HttpsURLConnection;
import javax.net.ssl.SSLContext;
import javax.net.ssl.TrustManager;

/* JADX INFO: compiled from: HTTP.java */
/* JADX INFO: loaded from: classes.dex */
public final class g implements Runnable {
    public static final String[] bnt = {"GET", "POST", "PUT"};
    private String BH;
    private String BI;
    public String BM;
    private boolean acC;
    private y bnr;
    private String bnv;
    private c bns = null;
    private int bnu = 0;
    private final int BF = 16;
    private Thread BG = null;
    private HttpURLConnection acD = null;
    private HttpURLConnection bnw = null;
    private HttpsURLConnection bnx = null;
    private InputStream bny = null;
    private OutputStream bnz = null;
    boolean BR = false;
    public boolean BT = false;

    public boolean isInProgress() {
        return this.BR;
    }

    public void cancel() {
        if (this.acD != null) {
            try {
                synchronized (this.acD) {
                    this.bny.close();
                }
            } catch (Exception e) {
            }
            try {
                synchronized (this.acD) {
                    this.acD.disconnect();
                }
            } catch (Exception e2) {
            }
            if (this.bnu == 1 || this.bnu == 2) {
                try {
                    synchronized (this.bnz) {
                        this.bnz.close();
                    }
                } catch (Exception e3) {
                }
                this.bnz = null;
            }
        }
        this.bny = null;
        this.acD = null;
        this.BG = null;
        au.Qi();
        this.BR = false;
    }

    public g(y yVar, boolean z, String str) {
        this.bnr = null;
        this.bnv = "";
        this.bnr = yVar;
        this.acC = z;
        this.bnv = str;
        try {
            SSLContext sSLContext = SSLContext.getInstance("TLS");
            sSLContext.init(null, new TrustManager[]{new i(this)}, new SecureRandom());
            HttpsURLConnection.setDefaultSSLSocketFactory(sSLContext.getSocketFactory());
            HttpsURLConnection.setDefaultHostnameVerifier(HttpsURLConnection.getDefaultHostnameVerifier());
        } catch (Exception e) {
        }
    }

    public void ad(String str, String str2) {
        while (this.BR) {
            try {
                if (System.currentTimeMillis() - this.bnr.Cd > 60000) {
                    cancel();
                }
                synchronized (this) {
                    try {
                        wait(50L);
                    } catch (Throwable th) {
                        throw th;
                    }
                }
            } catch (Exception e) {
            }
        }
        this.BR = true;
        if (this.bnu == 1 || this.bnu == 2) {
            this.BH = str;
            this.BI = str2;
        } else if (str.indexOf("?") != -1) {
            this.BH = str + "&" + str2;
        } else {
            this.BH = str + "?" + str2;
        }
        if (y.bpc.equals("TextHtml") || y.bpc.equals("texthtml") || y.bpc.equals("TEXTHTML")) {
            this.BH += "&texthtml=1";
        } else if (y.bpc.equals("TextPlain") || y.bpc.equals("textplain") || y.bpc.equals("TEXTPLAIN")) {
            this.BH += "&textplain=1";
        }
        if (this.BG != null) {
            try {
                this.BG.join();
            } catch (Exception e2) {
            }
        }
        this.bns = new c();
        this.bns.a(60000L, this);
        this.BT = false;
        this.BG = new Thread(this);
        this.BG.start();
    }

    private boolean SG() {
        return this.BH.indexOf("https") != -1;
    }

    @Override // java.lang.Runnable
    public void run() {
        String str;
        String str2;
        if (!SG()) {
            if ((this.bnu == 1 || this.bnu == 2) && this.BI == null) {
                cancel();
                this.BT = true;
                this.BR = false;
                SH();
                return;
            }
            try {
                this.BT = false;
                b bVarSF = y.SF();
                System.setProperty("http.keepAlive", "false");
                URL url = new URL(this.BH);
                if (bVarSF.Sz() && v.TM()) {
                    this.bnw = (HttpURLConnection) url.openConnection(new Proxy(Proxy.Type.HTTP, new InetSocketAddress(InetAddress.getByName(bVarSF.RF()), bVarSF.getProxyPort())));
                } else {
                    this.bnw = (HttpURLConnection) url.openConnection();
                }
                this.bnw.setRequestMethod(bnt[this.bnu]);
                this.bnw.setDoInput(true);
                if (this.bnu == 1 || this.bnu == 2) {
                    this.bnw.setDoOutput(true);
                }
                this.bnw.setRequestProperty("Connection", "close");
                if (com.gameloft.android2d.iap.b.bjI != null && !com.gameloft.android2d.iap.b.bjI.equals("")) {
                    this.bnw.setRequestProperty(v.mJ(com.b.d.cin), com.gameloft.android2d.iap.b.bjI);
                } else {
                    HttpURLConnection httpURLConnection = this.bnw;
                    String strMJ = v.mJ(com.b.d.cin);
                    y.Uh();
                    httpURLConnection.setRequestProperty(strMJ, d.getUserAgent());
                }
                this.bnw.setRequestProperty(v.mJ(com.b.d.chN), Build.MODEL);
                HttpURLConnection httpURLConnection2 = this.bnw;
                String strMJ2 = v.mJ(com.b.d.cil);
                y.Uh();
                httpURLConnection2.setRequestProperty(strMJ2, d.Pb());
                if (com.gameloft.android2d.iap.b.QL() != 2) {
                    this.bnw.setRequestProperty(v.mJ(com.b.d.chZ), y.Uh().NK());
                }
                if (com.gameloft.android2d.iap.b.QL() != 0) {
                    this.bnw.setRequestProperty(v.mJ(com.b.d.chY), y.Uh().Os());
                }
                if (v.TN()) {
                    if (v.TK() != null) {
                        this.bnw.setRequestProperty(v.mJ(com.b.d.chR), v.TK());
                    }
                    HttpURLConnection httpURLConnection3 = this.bnw;
                    String strMJ3 = v.mJ(com.b.d.cib);
                    y.Uh();
                    httpURLConnection3.setRequestProperty(strMJ3, d.Pb());
                    HttpURLConnection httpURLConnection4 = this.bnw;
                    String strMJ4 = v.mJ(com.b.d.cic);
                    y.Uh();
                    httpURLConnection4.setRequestProperty(strMJ4, d.Pb());
                }
                String strRu = af.Ru();
                if (TextUtils.isEmpty(strRu) || strRu.equalsIgnoreCase("null:null")) {
                    strRu = "";
                }
                this.bnw.setRequestProperty(v.mJ(com.b.d.chQ), strRu);
                this.bnw.setRequestProperty(v.mJ(com.b.d.chT), af.rk());
                this.bnw.setRequestProperty(v.mJ(com.b.d.chU), af.rj());
                this.bnw.setRequestProperty(v.mJ(com.b.d.cij), y.Uh().getSimOperator());
                this.bnw.setRequestProperty(v.mJ(com.b.d.cik), y.Uh().getSimOperatorName());
                this.bnw.setRequestProperty(v.mJ(com.b.d.cii), y.Uh().getSimCountryIso());
                this.bnw.setRequestProperty(v.mJ(com.b.d.cie), y.Uh().getNetworkOperator());
                this.bnw.setRequestProperty(v.mJ(com.b.d.cif), y.Uh().getNetworkOperatorName());
                this.bnw.setRequestProperty(v.mJ(com.b.d.cid), y.Uh().getNetworkCountryIso());
                this.bnw.setRequestProperty(v.mJ(com.b.d.cig), y.Uh().SE() + "");
                this.bnw.setRequestProperty(v.mJ(com.b.d.cim), AppEventsConstants.EVENT_PARAM_VALUE_YES);
                String strGh = v.gh(v.mJ(com.b.d.cgZ));
                if (strGh == null) {
                    strGh = AppEventsConstants.EVENT_PARAM_VALUE_NO;
                }
                this.bnw.setRequestProperty(v.mJ(com.b.d.chV), strGh);
                this.bnw.setRequestProperty(v.mJ(com.b.d.cih), u.TE());
                this.bnw.setRequestProperty(v.mJ(com.b.d.chL), com.gameloft.android2d.iap.b.bkr);
                this.bnw.setRequestProperty(v.mJ(com.b.d.cia), com.gameloft.android2d.iap.b.bkm);
                if (com.gameloft.android2d.iap.b.bkv) {
                    this.bnw.setRequestProperty(v.mJ(com.b.d.cit), com.gameloft.android2d.iap.b.Qu());
                    this.bnw.setRequestProperty(v.mJ(com.b.d.cis), v.gh(v.mJ(com.b.d.cha)));
                    this.bnw.setRequestProperty(v.mJ(com.b.d.cir), this.bnr.Uq());
                }
                if (!this.bnv.equalsIgnoreCase("")) {
                    this.bnw.setRequestProperty(v.mJ(com.b.d.chM), this.bnv);
                }
                String strRv = af.Rv();
                if (!TextUtils.isEmpty(strRv) && !strRv.equalsIgnoreCase("null:null")) {
                    this.bnw.setRequestProperty(v.mJ(com.b.d.chW), strRv);
                }
                if (this.bnu == 1 || this.bnu == 2) {
                    this.bnw.setRequestProperty(v.mJ(com.b.d.chP), com.gameloft.android2d.iap.b.bks);
                    if (this.acC) {
                        str2 = "b=" + this.BI;
                    } else {
                        str2 = this.BI;
                    }
                    this.bnw.setRequestProperty(v.mJ(com.b.d.chO), String.valueOf(str2.length()));
                    this.bnz = this.bnw.getOutputStream();
                    this.bnz.write(str2.getBytes(), 0, str2.length());
                    this.bnz.flush();
                }
                String headerField = this.bnw.getHeaderField(v.mJ(com.b.d.chS));
                if (headerField != null) {
                    com.gameloft.android2d.iap.b.bkm = headerField;
                }
                if (com.gameloft.android2d.iap.b.bkv) {
                    this.bnr.gN(this.bnw.getHeaderField(v.mJ(com.b.d.ciu)));
                }
                this.bnr.gL(v.l(this.bnw.getErrorStream()));
                if (!TextUtils.isEmpty(this.bnr.gM(ShareConstants.WEB_DIALOG_PARAM_MESSAGE)) && this.bnw.getResponseCode() == 409) {
                    this.bnr.mO(429);
                } else {
                    this.bnr.mO(this.bnw.getResponseCode());
                }
                if (this.bnw.getResponseCode() != 200 && this.bnw.getResponseCode() != 201) {
                    cancel();
                    this.BT = true;
                    this.BR = false;
                    SH();
                    return;
                }
                synchronized (this.bnw) {
                    this.bny = this.bnw.getInputStream();
                }
                ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
                byte[] bArr = new byte[16];
                int i = 0;
                while (i != -1) {
                    i = this.bny.read(bArr, 0, 16);
                    if (i != -1) {
                        byteArrayOutputStream.write(bArr, 0, i);
                    }
                }
                if (this.bnv.equalsIgnoreCase("")) {
                    this.BM = byteArrayOutputStream.toString();
                } else {
                    this.BM = f.m(byteArrayOutputStream.toByteArray());
                }
                this.bnr.gL(this.BM);
                cancel();
                this.BR = false;
                SH();
            } catch (SocketException e) {
                this.BT = true;
                this.BR = false;
                this.bnr.mO(-2);
            } catch (UnknownHostException e2) {
                this.BT = true;
                this.BR = false;
                this.bnr.mO(-2);
            } catch (Exception e3) {
                this.BT = true;
                this.BR = false;
            }
        } else {
            if ((this.bnu == 1 || this.bnu == 2) && this.BI == null) {
                cancel();
                this.BT = true;
                this.BR = false;
                SH();
                return;
            }
            try {
                this.BT = false;
                SSLContext sSLContext = SSLContext.getInstance("TLS");
                sSLContext.init(null, new TrustManager[]{new h(this)}, null);
                HttpsURLConnection.setDefaultSSLSocketFactory(sSLContext.getSocketFactory());
                HttpsURLConnection.setDefaultHostnameVerifier(HttpsURLConnection.getDefaultHostnameVerifier());
                b bVarSF2 = y.SF();
                System.setProperty("http.keepAlive", "false");
                URL url2 = new URL(this.BH);
                if (bVarSF2.Sz() && v.TM()) {
                    this.bnx = (HttpsURLConnection) url2.openConnection(new Proxy(Proxy.Type.HTTP, new InetSocketAddress(InetAddress.getByName(bVarSF2.RF()), bVarSF2.getProxyPort())));
                } else {
                    this.bnx = (HttpsURLConnection) url2.openConnection();
                }
                this.bnx.setRequestMethod(bnt[this.bnu]);
                this.bnx.setDoInput(true);
                if (this.bnu == 1 || this.bnu == 2) {
                    this.bnx.setDoOutput(true);
                }
                this.bnx.setRequestProperty("Connection", "close");
                if (com.gameloft.android2d.iap.b.bjI != null && !com.gameloft.android2d.iap.b.bjI.equals("")) {
                    this.bnx.setRequestProperty(v.mJ(com.b.d.cin), com.gameloft.android2d.iap.b.bjI);
                } else {
                    HttpsURLConnection httpsURLConnection = this.bnx;
                    String strMJ5 = v.mJ(com.b.d.cin);
                    y.Uh();
                    httpsURLConnection.setRequestProperty(strMJ5, d.getUserAgent());
                }
                this.bnx.setRequestProperty(v.mJ(com.b.d.chN), Build.MODEL);
                HttpsURLConnection httpsURLConnection2 = this.bnx;
                String strMJ6 = v.mJ(com.b.d.cil);
                y.Uh();
                httpsURLConnection2.setRequestProperty(strMJ6, d.Pb());
                if (com.gameloft.android2d.iap.b.QL() != 2) {
                    this.bnx.setRequestProperty(v.mJ(com.b.d.chZ), y.Uh().NK());
                }
                if (com.gameloft.android2d.iap.b.QL() != 0) {
                    this.bnx.setRequestProperty(v.mJ(com.b.d.chY), y.Uh().Os());
                }
                if (v.TN()) {
                    if (v.TK() != null) {
                        this.bnx.setRequestProperty(v.mJ(com.b.d.chR), v.TK());
                    }
                    HttpsURLConnection httpsURLConnection3 = this.bnx;
                    String strMJ7 = v.mJ(com.b.d.cib);
                    y.Uh();
                    httpsURLConnection3.setRequestProperty(strMJ7, d.Pb());
                    HttpsURLConnection httpsURLConnection4 = this.bnx;
                    String strMJ8 = v.mJ(com.b.d.cic);
                    y.Uh();
                    httpsURLConnection4.setRequestProperty(strMJ8, d.Pb());
                }
                String strRu2 = af.Ru();
                if (TextUtils.isEmpty(strRu2) || strRu2.equalsIgnoreCase("null:null")) {
                    strRu2 = "";
                }
                this.bnx.setRequestProperty(v.mJ(com.b.d.chQ), strRu2);
                this.bnx.setRequestProperty(v.mJ(com.b.d.chT), af.rk());
                this.bnx.setRequestProperty(v.mJ(com.b.d.chU), af.rj());
                this.bnx.setRequestProperty(v.mJ(com.b.d.cij), y.Uh().getSimOperator());
                this.bnx.setRequestProperty(v.mJ(com.b.d.cik), y.Uh().getSimOperatorName());
                this.bnx.setRequestProperty(v.mJ(com.b.d.cii), y.Uh().getSimCountryIso());
                this.bnx.setRequestProperty(v.mJ(com.b.d.cie), y.Uh().getNetworkOperator());
                this.bnx.setRequestProperty(v.mJ(com.b.d.cif), y.Uh().getNetworkOperatorName());
                this.bnx.setRequestProperty(v.mJ(com.b.d.cid), y.Uh().getNetworkCountryIso());
                this.bnx.setRequestProperty(v.mJ(com.b.d.cig), y.Uh().SE() + "");
                this.bnx.setRequestProperty(v.mJ(com.b.d.cim), AppEventsConstants.EVENT_PARAM_VALUE_YES);
                String strGh2 = v.gh(v.mJ(com.b.d.cgZ));
                if (strGh2 == null) {
                    strGh2 = AppEventsConstants.EVENT_PARAM_VALUE_NO;
                }
                this.bnx.setRequestProperty(v.mJ(com.b.d.chV), strGh2);
                this.bnx.setRequestProperty(v.mJ(com.b.d.cih), u.TE());
                this.bnx.setRequestProperty(v.mJ(com.b.d.chL), com.gameloft.android2d.iap.b.bkr);
                this.bnx.setRequestProperty(v.mJ(com.b.d.cia), com.gameloft.android2d.iap.b.bkm);
                if (com.gameloft.android2d.iap.b.bkv) {
                    this.bnx.setRequestProperty(v.mJ(com.b.d.ciq), com.gameloft.android2d.iap.b.Qt());
                    this.bnx.setRequestProperty(v.mJ(com.b.d.cit), com.gameloft.android2d.iap.b.Qu());
                    this.bnx.setRequestProperty(v.mJ(com.b.d.cis), v.gh(v.mJ(com.b.d.cha)));
                    this.bnx.setRequestProperty(v.mJ(com.b.d.cir), this.bnr.Uq());
                    this.bnx.setRequestProperty(v.mJ(com.b.d.cip), u.getUserID());
                }
                if (!this.bnv.equalsIgnoreCase("")) {
                    this.bnx.setRequestProperty(v.mJ(com.b.d.chM), this.bnv);
                }
                String strRv2 = af.Rv();
                if (!TextUtils.isEmpty(strRv2) && !strRv2.equalsIgnoreCase("null:null")) {
                    this.bnx.setRequestProperty(v.mJ(com.b.d.chW), strRv2);
                }
                if (this.bnu == 1 || this.bnu == 2) {
                    this.bnx.setRequestProperty(v.mJ(com.b.d.chP), com.gameloft.android2d.iap.b.bks);
                    if (this.acC) {
                        str = "b=" + this.BI;
                    } else {
                        str = this.BI;
                    }
                    this.bnx.setRequestProperty(v.mJ(com.b.d.chO), String.valueOf(str.length()));
                    this.bnz = this.bnx.getOutputStream();
                    this.bnz.write(str.getBytes(), 0, str.length());
                    this.bnz.flush();
                }
                String headerField2 = this.bnx.getHeaderField(v.mJ(com.b.d.chS));
                if (headerField2 != null) {
                    com.gameloft.android2d.iap.b.bkm = headerField2;
                }
                if (com.gameloft.android2d.iap.b.bkv) {
                    this.bnr.gN(this.bnx.getHeaderField(v.mJ(com.b.d.ciu)));
                }
                this.bnr.gL(v.l(this.bnx.getErrorStream()));
                if (!TextUtils.isEmpty(this.bnr.gM(ShareConstants.WEB_DIALOG_PARAM_MESSAGE)) && this.bnx.getResponseCode() == 409) {
                    this.bnr.mO(429);
                } else {
                    this.bnr.mO(this.bnx.getResponseCode());
                }
                if (this.bnx.getResponseCode() != 200 && this.bnx.getResponseCode() != 201) {
                    cancel();
                    this.BT = true;
                    this.BR = false;
                    SH();
                    return;
                }
                synchronized (this.bnx) {
                    this.bny = this.bnx.getInputStream();
                }
                ByteArrayOutputStream byteArrayOutputStream2 = new ByteArrayOutputStream();
                byte[] bArr2 = new byte[16];
                int i2 = 0;
                while (i2 != -1) {
                    i2 = this.bny.read(bArr2, 0, 16);
                    if (i2 != -1) {
                        byteArrayOutputStream2.write(bArr2, 0, i2);
                    }
                }
                if (this.bnv.equalsIgnoreCase("")) {
                    this.BM = byteArrayOutputStream2.toString();
                } else {
                    this.BM = f.m(byteArrayOutputStream2.toByteArray());
                }
                this.bnr.gL(this.BM);
                for (int i3 = 0; i3 < this.BM.split("\n").length; i3++) {
                }
                cancel();
                this.BR = false;
                SH();
            } catch (SocketException e4) {
                this.BT = true;
                this.BR = false;
                this.bnr.mO(-2);
            } catch (UnknownHostException e5) {
                this.BT = true;
                this.BR = false;
                this.bnr.mO(-2);
            } catch (Exception e6) {
                this.BT = true;
                this.BR = false;
            }
        }
    }

    private void SH() {
        this.bns.stop();
    }
}
