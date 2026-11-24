package com.quanhoang.greenfarm3;

import com.facebook.share.internal.ShareConstants;
import java.io.ByteArrayOutputStream;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.Arrays;
import java.util.Iterator;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

/* JADX INFO: compiled from: GlotHTTP.java */
/* JADX INFO: loaded from: classes.dex */
public class m implements Runnable {
    public static String BU = "GLOTv3/";
    private static final long BY = k.BB;
    private static boolean Ca = false;
    private static boolean Cb = false;
    private static boolean Cc = false;
    private final int BF;
    private Thread BG;
    private String BH;
    private String BI;
    private javax.microedition.a.e BJ;
    private InputStream BK;
    private OutputStream BL;
    public String BM;
    public int BN;
    public int BO;
    public byte[] BP;
    private boolean BQ;
    private boolean BR;
    private boolean BS;
    public boolean BT;
    public int BV;
    public int BW;
    public long BX;
    private bv BZ;
    private long Cd;

    public m() {
        this.BF = 16;
        this.BG = null;
        this.BJ = null;
        this.BK = null;
        this.BL = null;
        this.BQ = false;
        this.BR = false;
        this.BS = false;
        this.BT = false;
        this.BV = 0;
        this.BW = 0;
        this.BZ = null;
    }

    public m(bv bvVar) {
        this.BF = 16;
        this.BG = null;
        this.BJ = null;
        this.BK = null;
        this.BL = null;
        this.BQ = false;
        this.BR = false;
        this.BS = false;
        this.BT = false;
        this.BV = 0;
        this.BW = 0;
        this.BZ = null;
        this.BZ = bvVar;
    }

    @Override // java.lang.Runnable
    public void run() {
        if (this.BH == null) {
            gi();
            this.BT = true;
            this.BR = false;
            return;
        }
        if (this.BQ && this.BI == null) {
            gi();
            this.BT = true;
            this.BR = false;
            return;
        }
        try {
            this.BT = false;
            if (this.BS) {
                this.BR = false;
                return;
            }
            if (this.BQ) {
                this.BH += "/binary";
            }
            this.BJ = (javax.microedition.a.e) javax.microedition.a.c.jd(this.BH);
            if (this.BZ != null) {
                o.u("[Retries]: " + this.BZ.gr());
                if (this.BZ.gr() == 0) {
                    Ca = false;
                    Cb = false;
                    Cc = false;
                }
            }
            try {
                this.BJ.setRequestProperty("Connection", "close");
                if (!this.BQ) {
                    if (BU != null) {
                        this.BJ.setRequestProperty("User-Agent", BU);
                    }
                    if (this.BH.contains("/assign_global_id/?")) {
                        this.BJ.setRequestProperty("X-GL-mimetype", "json");
                    }
                }
                if (this.BQ) {
                    this.BJ.setRequestMethod("POST");
                    ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
                    a(new be(byteArrayOutputStream), null);
                    ByteArrayOutputStream byteArrayOutputStream2 = new ByteArrayOutputStream();
                    o.a(byteArrayOutputStream, byteArrayOutputStream2);
                    ByteArrayOutputStream byteArrayOutputStream3 = new ByteArrayOutputStream();
                    new bg(com.gameloft.android.wrapper.au.l(byteArrayOutputStream2.toByteArray())).a(new be(byteArrayOutputStream3));
                    byteArrayOutputStream3.write(byteArrayOutputStream2.toByteArray());
                    byte[] byteArray = byteArrayOutputStream3.toByteArray();
                    this.BJ.setRequestProperty("X-ETS-sha1", com.gameloft.android.wrapper.au.l(byteArray));
                    this.BJ.setRequestProperty("Content-Type", "application/gl-stream");
                    int length = byteArray.length;
                    this.BJ.setRequestProperty("Content-Length", String.valueOf(length));
                    this.BL = this.BJ.ajP();
                    this.BL.write(byteArray, 0, length);
                    this.BL.flush();
                } else {
                    this.BJ.setRequestMethod("GET");
                }
                try {
                    this.BN = this.BJ.getResponseCode();
                    this.BX = this.BJ.getDate();
                    this.BO = this.BJ.getHeaderFieldInt("X-ETS-delay", 0);
                    if (this.BN != 200 && this.BN != 304) {
                        if (!this.BQ && this.BN == 302) {
                            try {
                                this.BM = this.BJ.getHeaderField("Location");
                            } catch (Exception e) {
                                o.u("Cannot get redirect url");
                            }
                        }
                        gi();
                        this.BT = true;
                        this.BR = false;
                        return;
                    }
                    try {
                        if (this.BS) {
                            this.BR = false;
                            return;
                        }
                        try {
                            synchronized (this.BJ) {
                                this.BK = this.BJ.ajO();
                            }
                            Thread thread = this.BG;
                            Thread.yield();
                            ByteArrayOutputStream byteArrayOutputStream4 = new ByteArrayOutputStream();
                            byte[] bArr = new byte[16];
                            int i = 0;
                            while (i != -1) {
                                if (this.BS) {
                                    this.BR = false;
                                    gi();
                                    return;
                                } else {
                                    i = this.BK.read(bArr, 0, 16);
                                    if (i == -1) {
                                        break;
                                    } else {
                                        byteArrayOutputStream4.write(bArr, 0, i);
                                    }
                                }
                            }
                            this.BM = byteArrayOutputStream4.toString();
                            this.BP = byteArrayOutputStream4.toByteArray();
                            gi();
                        } catch (Exception e2) {
                            this.BT = true;
                            this.BR = false;
                            o.u("[Network]: (!) Failed on reading content body!");
                            e2.printStackTrace();
                            if (this.BZ != null && !Cc) {
                                Cc = true;
                                this.BZ.N(1409, "[Network]: (!) Failed on reading content body!");
                            }
                            gi();
                        }
                    } catch (Throwable th) {
                        gi();
                        throw th;
                    }
                } catch (Exception e3) {
                    this.BT = true;
                    this.BR = false;
                    o.u("[Network]: (!) Failed on connecting to endpoint!");
                    e3.printStackTrace();
                    o.u(Arrays.toString(e3.getStackTrace()));
                    if (this.BZ == null || Cb) {
                        return;
                    }
                    Cb = true;
                    this.BZ.N(1402, "[Network]: (!) Failed on connecting to endpoint!");
                }
            } catch (Exception e4) {
                this.BT = true;
                this.BR = false;
                o.u("[Network]: (!) Failed on writing outputstream!");
                e4.printStackTrace();
                if (this.BZ == null || Ca) {
                    return;
                }
                Ca = true;
                this.BZ.N(1403, "[Network]: (!) Failed on writing outputstream!");
            }
        } catch (Exception e5) {
            this.BT = true;
            this.BR = false;
            o.u("[Network]: (!) Failed on open HTTP connection!");
            o.u(Arrays.toString(e5.getStackTrace()));
            e5.printStackTrace();
        }
    }

    public void c(String str, String str2) {
        if (this.Cd == 0) {
            this.Cd = System.currentTimeMillis();
        }
        if (this.BR) {
            try {
                if (System.currentTimeMillis() - this.Cd > BY) {
                    this.Cd = 0L;
                    gi();
                }
                synchronized (this) {
                    wait(50L);
                }
                return;
            } catch (Exception e) {
                return;
            }
        }
        this.Cd = 0L;
        o.u("CONNECT TO SERVER: " + str + str2);
        this.BQ = false;
        gh();
        this.BR = true;
        this.BS = false;
        this.BH = str + str2;
        if (this.BG != null) {
            try {
                this.BG.join();
            } catch (Exception e2) {
            }
        }
        this.BT = false;
        this.BG = new Thread(this);
        this.BG.start();
    }

    public void d(String str, String str2) {
        if (this.Cd == 0) {
            this.Cd = System.currentTimeMillis();
        }
        if (this.BR) {
            try {
                if (System.currentTimeMillis() - this.Cd > BY) {
                    this.Cd = 0L;
                    gi();
                }
                synchronized (this) {
                    try {
                        wait(50L);
                    } catch (Throwable th) {
                        throw th;
                    }
                }
                return;
            } catch (Exception e) {
                e.printStackTrace();
                return;
            }
        }
        this.Cd = 0L;
        o.u("CONNECT TO SERVER: " + str + "\t" + str2);
        this.BQ = true;
        com.gameloft.android.wrapper.au.Qi();
        gh();
        this.BR = true;
        this.BS = false;
        this.BH = str;
        this.BI = str2;
        if (this.BG != null) {
            try {
                this.BG.join();
            } catch (Exception e2) {
            }
        }
        this.BT = false;
        this.BG = new Thread(this);
        this.BG.start();
    }

    public void gh() {
        gi();
        this.BM = null;
        this.BP = null;
    }

    public synchronized void gi() {
        if (!this.BS) {
            this.BS = true;
            if (this.BJ != null) {
                try {
                    synchronized (this.BJ) {
                        this.BK.close();
                    }
                } catch (Exception e) {
                }
                try {
                    synchronized (this.BJ) {
                        this.BJ.close();
                    }
                } catch (Exception e2) {
                }
                try {
                    synchronized (this.BL) {
                        this.BL.close();
                    }
                } catch (Exception e3) {
                }
                this.BL = null;
            }
            this.BK = null;
            this.BJ = null;
            this.BR = false;
            this.BG = null;
            com.gameloft.android.wrapper.au.Qi();
        }
    }

    public boolean gj() {
        return this.BR;
    }

    void a(be beVar, String str) {
        JSONObject jSONObject;
        beVar.ba(19);
        beVar.ba(18);
        try {
            if (str != null) {
                jSONObject = new JSONObject(str);
            } else {
                jSONObject = new JSONObject(this.BI);
            }
            beVar.f(jSONObject.length(), true);
            Iterator<String> itKeys = jSONObject.keys();
            while (itKeys.hasNext()) {
                String next = itKeys.next();
                beVar.aa(next);
                if (next.equals("events")) {
                    try {
                        JSONArray jSONArray = jSONObject.getJSONArray("events");
                        int length = jSONArray.length();
                        beVar.ba(18);
                        beVar.f(length, true);
                        for (int i = 0; i < length; i++) {
                            JSONObject jSONObject2 = jSONArray.getJSONObject(i);
                            beVar.ba(2);
                            beVar.ba(18);
                            beVar.f(jSONObject2.length(), true);
                            Iterator<String> itKeys2 = jSONObject2.keys();
                            while (itKeys2.hasNext()) {
                                String next2 = itKeys2.next();
                                beVar.aa(next2);
                                if (next2.equals(ShareConstants.WEB_DIALOG_PARAM_DATA)) {
                                    JSONObject jSONObject3 = jSONObject2.getJSONObject(ShareConstants.WEB_DIALOG_PARAM_DATA);
                                    beVar.ba(18);
                                    beVar.f(jSONObject3.length(), true);
                                    Iterator<String> itKeys3 = jSONObject3.keys();
                                    while (itKeys3.hasNext()) {
                                        String next3 = itKeys3.next();
                                        beVar.aa(next3);
                                        a(jSONObject3.get(next3), beVar, !bx.dT(next3));
                                    }
                                } else {
                                    a(jSONObject2.get(next2), beVar, false);
                                }
                            }
                        }
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                } else {
                    try {
                        a(jSONObject.get(next), beVar, false);
                    } catch (JSONException e2) {
                        e2.printStackTrace();
                    }
                }
            }
        } catch (JSONException e3) {
            e3.printStackTrace();
        }
    }

    void a(Object obj, be beVar, boolean z) {
        boolean z2 = false;
        if (obj instanceof Integer) {
            beVar.f(((Integer) obj).intValue(), false);
            return;
        }
        if (obj instanceof Float) {
            beVar.f(((Float) obj).floatValue());
            return;
        }
        if (obj instanceof Double) {
            beVar.a(((Double) obj).doubleValue());
            return;
        }
        String string = obj.toString();
        if (string.startsWith("JavaGLOTv3Encrypted")) {
            z2 = true;
            string = string.substring(19);
        }
        if (z && !z2) {
            beVar.ab(string);
        } else {
            beVar.aa(string);
        }
    }
}
