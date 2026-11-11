package com.gameloft.android.ANMP.GloftGF2F.S800x480;

import java.io.InputStream;
import java.util.Vector;

/* JADX INFO: compiled from: TCP.java */
/* JADX INFO: loaded from: classes.dex */
class by extends Thread {
    private bx akH;
    private InputStream akI;
    private Vector akJ;
    private boolean akK;
    private byte[] akL = new byte[2048];
    private int akM;
    private byte[] akN;
    private boolean akO;
    final /* synthetic */ bx akP;
    private int len;

    public by(bx bxVar, InputStream inputStream, Vector vector, boolean z, bx bxVar2) {
        this.akP = bxVar;
        this.akI = inputStream;
        this.akJ = vector;
        this.akO = z;
        this.akH = bxVar2;
    }

    void ok() {
        Exception e;
        byte b;
        byte b2;
        this.len = -2;
        try {
            if (this.akO) {
                if (w.XT) {
                    b2 = (byte) this.akI.read();
                    try {
                        this.len = this.akI.read(this.akL, 0, b2);
                    } catch (Exception e2) {
                        e = e2;
                        b = b2;
                        if (!this.akP.aky) {
                            this.akP.BT = true;
                            this.akH.acI = e;
                            if (w.XX) {
                                this.akP.akz = "Recv: " + (e.getMessage() == null ? e.toString() : e.getMessage());
                            }
                            this.akK = false;
                            if (w.XX) {
                                GLLib.b("TCP: recv exception: " + e.toString(), "TCP", 6);
                                GLLib.d(e);
                            }
                        }
                        b2 = b;
                    }
                } else {
                    this.len = this.akI.read(this.akL, 0, this.akL.length);
                    b2 = 0;
                }
            } else if (w.XU) {
                byte b3 = (byte) this.akI.read();
                if (b3 > 0) {
                    this.akL[0] = b3;
                    this.len = this.akI.read(this.akL, 1, Math.min(this.akI.available(), this.akL.length - 1)) + 1;
                } else {
                    this.len = -1;
                }
                b2 = 0;
            } else if (w.XW) {
                byte b4 = (byte) this.akI.read();
                if (b4 > 0) {
                    int i = 0;
                    byte b5 = b4;
                    while (this.akI.available() > 0 && i < this.akL.length - 1) {
                        this.akL[i] = b5;
                        b5 = (byte) this.akI.read();
                        i++;
                    }
                    this.akL[i] = b5;
                    this.len = i + 1;
                } else {
                    this.len = -1;
                }
                b2 = 0;
            } else if (w.XV) {
                byte b6 = (byte) this.akI.read();
                if (b6 > 0) {
                    this.akL[0] = b6;
                    if (b6 != 123) {
                        String str = new String();
                        if (bx.akw <= 0) {
                            int i2 = 0;
                            while (b6 != 124 && i2 < 4 && this.akI.available() > 0) {
                                str = str + ((char) b6);
                                b6 = (byte) this.akI.read();
                                i2++;
                                this.akL[i2] = b6;
                            }
                            if (b6 == 124) {
                                int i3 = Integer.parseInt(str);
                                if (i3 + i2 > this.akL.length) {
                                    int unused = bx.akw = (i3 + i2) - this.akL.length;
                                } else {
                                    int unused2 = bx.akw = -1;
                                }
                                this.len = this.akI.read(this.akL, i2 + 1, Math.min(i3 - 1, (this.akL.length - i2) - 1)) + i2 + 1;
                            } else if (i2 == 4) {
                                this.len = this.akI.read(this.akL, i2 + 1, Math.min(this.akI.available(), (this.akL.length - i2) - 1)) + i2 + 1;
                            } else {
                                this.len = i2 + 1;
                            }
                        } else if (bx.akw > this.akL.length) {
                            this.len = this.akI.read(this.akL, 1, this.akL.length - 1) + 1;
                            bx.eP(this.akL.length);
                        } else {
                            this.len = this.akI.read(this.akL, 1, bx.akw - 1) + 1;
                            int unused3 = bx.akw = -1;
                        }
                    } else {
                        this.len = this.akI.read(this.akL, 1, Math.min(this.akI.available(), this.akL.length - 1)) + 1;
                    }
                } else {
                    this.len = -1;
                }
                b2 = 0;
            } else {
                this.len = this.akI.read(this.akL, 0, this.akL.length);
                b2 = 0;
            }
        } catch (Exception e3) {
            e = e3;
            b = 0;
        }
        if (this.len < 0) {
            this.akK = false;
            return;
        }
        if (this.len != 0) {
            if (this.akO) {
                if (w.XT) {
                    this.len++;
                }
                byte[] bArr = new byte[(this.len + this.akN.length) - this.akM];
                System.arraycopy(this.akN, this.akM, bArr, 0, this.akN.length - this.akM);
                if (w.XT) {
                    bArr[this.akN.length - this.akM] = b2;
                    System.arraycopy(this.akL, 0, bArr, (this.akN.length - this.akM) + 1, this.len - 1);
                } else {
                    System.arraycopy(this.akL, 0, bArr, this.akN.length - this.akM, this.len);
                }
                this.akM = 0;
                this.akN = bArr;
            }
            yield();
        }
    }

    int ol() {
        while (this.akK && this.akN.length - this.akM < 1) {
            ok();
        }
        if (!this.akK) {
            return -1;
        }
        byte[] bArr = this.akN;
        int i = this.akM;
        this.akM = i + 1;
        short s = (short) (0 | ((bArr[i] & 255) << 8));
        byte[] bArr2 = this.akN;
        int i2 = this.akM;
        this.akM = i2 + 1;
        return (short) (s | (bArr2[i2] & 255));
    }

    byte[] getBytes(int i) {
        while (this.akK && this.akN.length - this.akM < i) {
            ok();
        }
        byte[] bArr = new byte[i];
        System.arraycopy(this.akN, this.akM, bArr, 0, i);
        this.akM += i;
        return bArr;
    }

    @Override // java.lang.Thread
    public void start() {
        this.akK = true;
        super.start();
    }

    /* JADX WARN: Code duplicated, block: B:20:0x003a  */
    /* JADX WARN: Code duplicated, block: B:33:0x0030 A[EXC_TOP_SPLITTER, SYNTHETIC] */
    @Override // java.lang.Thread, java.lang.Runnable
    public void run() {
        int iOl;
        byte[] bytes;
        this.akN = new byte[0];
        this.akM = 0;
        if (w.XX) {
            GLLib.b("TCPRecv: Starting receiver", "TCP", 3);
        }
        while (this.akK) {
            if (this.akO) {
                iOl = ol();
                if (iOl == -1) {
                    this.akK = false;
                } else if (iOl > 0) {
                    bytes = getBytes(iOl);
                    synchronized (this.akJ) {
                        this.akJ.addElement(bytes);
                    }
                    if (w.XX) {
                        GLLib.b("TCPRecv: Received " + iOl + " bytes packet:" + new String(bytes), "TCP", 3);
                    }
                    try {
                        sleep(300L);
                    } catch (InterruptedException e) {
                    }
                } else {
                    continue;
                }
            } else {
                ok();
                if (this.len > 0) {
                    bytes = new byte[this.len];
                    System.arraycopy(this.akL, 0, bytes, 0, this.len);
                    iOl = this.len;
                    synchronized (this.akJ) {
                        this.akJ.addElement(bytes);
                        if (w.XX) {
                            GLLib.b("TCPRecv: Received " + iOl + " bytes packet:" + new String(bytes), "TCP", 3);
                        }
                        sleep(300L);
                    }
                } else {
                    continue;
                }
            }
        }
    }
}
