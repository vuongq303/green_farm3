package com.gameloft.android.ANMP.GloftGF2F.S800x480;

import java.io.InputStream;
import java.io.OutputStream;
import java.util.Vector;

/* JADX INFO: compiled from: TCP.java */
/* JADX INFO: loaded from: classes.dex */
public class bx extends Thread {
    private static int akw = -1;
    public Exception acI;
    private by akA;
    protected Vector akB;
    protected Vector akC;
    private boolean akG;
    public boolean akx;
    public boolean aky;
    public String akz;
    protected String url;
    protected javax.microedition.a.o akD = null;
    protected InputStream akE = null;
    protected OutputStream akF = null;
    public boolean BT = false;

    static /* synthetic */ int eP(int i) {
        int i2 = akw - i;
        akw = i2;
        return i2;
    }

    public bx(String str, boolean z) {
        if (w.XX) {
            this.akz = null;
        }
        this.akB = new Vector();
        this.akC = new Vector();
        this.url = str;
        this.akG = z;
    }

    public void i(byte[] bArr) {
        synchronized (this.akC) {
            this.akC.addElement(bArr);
            if (w.XX) {
                GLLib.b("Adding packet to outgoing queue", "TCP", 3);
            }
        }
    }

    public byte[] oj() {
        byte[] bArr = null;
        synchronized (this.akB) {
            if (this.akB.size() > 0) {
                bArr = (byte[]) this.akB.firstElement();
                this.akB.removeElementAt(0);
            }
        }
        return bArr;
    }

    public void connect() {
        if (!this.akx) {
            try {
                new Thread(this).start();
            } catch (Exception e) {
                if (w.XX) {
                    GLLib.b("TCP: connect exception: " + e.toString(), "TCP", 6);
                    GLLib.d(e);
                }
            }
        }
    }

    public void disconnect() {
        if (w.XX) {
            GLLib.b("disconnecting tcp ... ", "TCP", 3);
        }
        this.aky = true;
    }

    private void bM() {
        if (w.XX) {
            GLLib.b("cleanup tcp .. ", "TCP", 3);
        }
        if (this.akA != null) {
            this.akA.akK = false;
        }
        try {
            if (this.akE != null) {
                this.akE.close();
            }
        } catch (Exception e) {
        }
        try {
            if (this.akF != null) {
                this.akF.close();
            }
        } catch (Exception e2) {
        }
        try {
            if (this.akD != null) {
                this.akD.close();
            }
        } catch (Exception e3) {
        }
        this.akE = null;
        this.akF = null;
        this.akD = null;
        synchronized (this.akC) {
            while (this.akC.size() > 0) {
                this.akC.removeElementAt(0);
            }
        }
        synchronized (this.akB) {
            while (this.akB.size() > 0) {
                this.akB.removeElementAt(0);
            }
        }
    }

    @Override // java.lang.Thread, java.lang.Runnable
    public void run() {
        byte[] bArr;
        this.akx = false;
        this.BT = false;
        try {
            if (w.XX) {
                GLLib.b("TCP: Opening connection to " + this.url, "TCP", 3);
            }
            this.akD = (javax.microedition.a.o) javax.microedition.a.c.jd(this.url);
            if (this.akD == null) {
                if (w.XX) {
                    this.akz = "StreamConnection.open to " + this.url;
                }
                this.BT = true;
                return;
            }
            this.akF = null;
            try {
                if (w.XX) {
                    GLLib.b("TCP: Opening output stream", "TCP", 3);
                }
                this.akF = this.akD.ajP();
                if (this.akF == null) {
                    this.BT = true;
                    if (w.XX) {
                        this.akz = "StreamConnection.openOutputStream to " + this.url;
                        return;
                    }
                    return;
                }
                this.akE = null;
                try {
                    if (w.XX) {
                        GLLib.b("TCP: Opening input stream", "TCP", 3);
                    }
                    this.akE = this.akD.ajO();
                    if (this.akE == null) {
                        this.BT = true;
                        if (w.XX) {
                            this.akz = "StreamConnection.openInputStream to " + this.url;
                            return;
                        }
                        return;
                    }
                    this.akx = true;
                    if (w.XX) {
                        GLLib.b("TCP: Connection established, starting recv thread", "TCP", 3);
                    }
                    this.akA = new by(this, this.akE, this.akB, this.akG, this);
                    this.akA.start();
                    while (this.akx && this.akA.akK) {
                        synchronized (this.akC) {
                            if (this.akC.size() > 0) {
                                byte[] bArr2 = (byte[]) this.akC.firstElement();
                                this.akC.removeElementAt(0);
                                bArr = bArr2;
                            } else {
                                if (this.aky) {
                                    this.akx = false;
                                }
                                bArr = null;
                            }
                        }
                        if (bArr == null) {
                            try {
                                sleep(300L);
                            } catch (InterruptedException e) {
                            }
                        } else {
                            if (w.XX) {
                                GLLib.b("TCP: new packet to send", "TCP", 3);
                            }
                            try {
                                this.akF.write(bArr);
                            } catch (Exception e2) {
                                if (w.XX) {
                                    GLLib.b("TCP: send exception: " + e2.toString(), "TCP", 6);
                                    GLLib.d(e2);
                                }
                                this.BT = true;
                                this.acI = e2;
                                if (w.XX) {
                                    this.akz = "Send: " + (e2.getMessage() == null ? e2.toString() : e2.getMessage());
                                }
                                this.akx = false;
                            }
                            if (w.XX) {
                                GLLib.b("TCP: Sent " + bArr.length + " bytes packet:" + new String(bArr), "TCP", 3);
                            }
                            try {
                                sleep(300L);
                            } catch (InterruptedException e3) {
                            }
                        }
                    }
                    this.akx = false;
                    bM();
                } catch (Exception e4) {
                    if (w.XX) {
                        GLLib.b("TCP: run exception: " + e4.toString(), "TCP", 6);
                    }
                    this.BT = true;
                    this.acI = e4;
                    if (w.XX) {
                        this.akz = "StreamConnection.openInputStream to " + this.url + " exception: " + e4;
                    }
                    GLLib.d(e4);
                }
            } catch (Exception e5) {
                if (w.XX) {
                    GLLib.b("TCP: run exception: " + e5.toString(), "TCP", 6);
                }
                this.BT = true;
                this.acI = e5;
                if (w.XX) {
                    this.akz = "StreamConnection.openOutputStream to " + this.url + " exception: " + e5;
                }
                GLLib.d(e5);
            }
        } catch (Exception e6) {
            if (w.XX) {
                GLLib.b("TCP: run exception: " + e6.toString(), "TCP", 6);
            }
            this.BT = true;
            this.acI = e6;
            if (w.XX) {
                this.akz = "StreamConnection.open to " + this.url + " exception: " + e6;
            }
            GLLib.d(e6);
        }
    }
}
