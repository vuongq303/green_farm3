package com.gameloft.android.ANMP.GloftGF2F;

import com.gameloft.glads.AndroidWebView;
import java.nio.charset.Charset;

/* JADX INFO: compiled from: CircularSynchronizer.java */
/* JADX INFO: loaded from: classes.dex */
public class d {
    private final Object AD = new Object();
    private final Object AE = new Object();
    private final Object AF = new Object();
    private bx AG;
    private static String Aw = "GLOT_Priority";
    private static String Ax = "GLOT_Stream";
    private static String Ay = "GLOT_Batched";
    private static String Az = "GLOT_Priority_Async";
    private static String AA = "GLOT_Stream_Async";
    private static String AB = "GLOT_Batched_Async";
    private static d AC = null;

    public d(bx bxVar) {
        this.AG = bxVar;
    }

    public static d a(bx bxVar) {
        if (AC == null) {
            AC = new d(bxVar);
        }
        return AC;
    }

    public boolean aA(int i) {
        bd bdVarAK = aK(0);
        if (bdVarAK == null) {
            bdVarAK = aK(1);
        }
        if (bdVarAK == null) {
            bdVarAK = aK(2);
        }
        if (bdVarAK == null || i == bdVarAK.ha()) {
            return true;
        }
        aI(0);
        aI(1);
        aI(2);
        aJ(0);
        aJ(1);
        aJ(2);
        return false;
    }

    public bd aB(int i) {
        return aK(i);
    }

    public boolean a(bd bdVar, int i) {
        byte[] bytes = this.AG.dU(bdVar.toString()).getBytes(Charset.forName(AndroidWebView.UTF_8));
        try {
            switch (i) {
                case 0:
                    if (!o.a(Ax, bytes)) {
                        o.t("CircularSynchronizer.serialize: cannot save '" + Ax + "' file!!!");
                    }
                    break;
                case 1:
                    if (!o.a(Aw, bytes)) {
                        o.t("CircularSynchronizer.serialize: cannot save '" + Aw + "' file!!!");
                    }
                    break;
                case 2:
                    if (!o.a(Ay, bytes)) {
                        o.t("CircularSynchronizer.serialize: cannot save '" + Ay + "' file!!!");
                    }
                    break;
                default:
                    return true;
            }
            return true;
        } catch (Exception e) {
            o.d(e);
            return false;
        }
    }

    /* JADX WARN: Can't fix incorrect switch cases order, some code will duplicate */
    public boolean b(bd bdVar, int i) {
        byte[] bytes = this.AG.dU(bdVar.toString()).getBytes(Charset.forName(AndroidWebView.UTF_8));
        try {
            switch (i) {
                case 0:
                    synchronized (this.AE) {
                        if (!o.a(AA, bytes)) {
                            o.t("CircularSynchronizer.serialize: cannot save '" + AA + "' file!!!");
                        }
                        break;
                    }
                    return true;
                case 1:
                    synchronized (this.AD) {
                        if (!o.a(Az, bytes)) {
                            o.t("CircularSynchronizer.serialize: cannot save '" + Az + "' file!!!");
                        }
                        break;
                    }
                    return true;
                case 2:
                    synchronized (this.AF) {
                        if (!o.a(AB, bytes)) {
                            o.t("CircularSynchronizer.serialize: cannot save '" + AB + "' file!!!");
                        }
                        break;
                    }
                    return true;
                default:
                    return true;
            }
        } catch (Exception e) {
            o.d(e);
            return false;
        }
    }

    /* JADX WARN: Can't fix incorrect switch cases order, some code will duplicate */
    public boolean aC(int i) {
        boolean z = false;
        switch (i) {
            case 0:
                synchronized (this.AE) {
                    z = o.z(AA);
                    break;
                }
                return z;
            case 1:
                synchronized (this.AD) {
                    z = o.z(Az);
                    break;
                }
                return z;
            case 2:
                synchronized (this.AF) {
                    z = o.z(AB);
                    break;
                }
                return z;
            default:
                return z;
        }
    }

    public boolean aD(int i) {
        return aC(i) && aL(i) != null;
    }

    public void aE(int i) {
        aJ(i);
    }

    public void aF(int i) {
        aI(i);
    }

    public bd aG(int i) {
        bd bdVarAK = aK(i);
        if (bdVarAK == null || !b(bdVarAK, i)) {
            return null;
        }
        aF(i);
        return bdVarAK;
    }

    public bd aH(int i) {
        return aL(i);
    }

    private void aI(int i) {
        try {
            switch (i) {
                case 0:
                    o.y(Ax);
                    break;
                case 1:
                    o.y(Aw);
                    break;
                case 2:
                    o.y(Ay);
                    break;
                default:
                    return;
            }
        } catch (Exception e) {
        }
    }

    private void aJ(int i) {
        try {
            switch (i) {
                case 0:
                    synchronized (this.AE) {
                        o.y(AA);
                        break;
                    }
                    return;
                case 1:
                    synchronized (this.AD) {
                        o.y(Az);
                        break;
                    }
                    return;
                case 2:
                    synchronized (this.AF) {
                        o.y(AB);
                        break;
                    }
                    return;
                default:
                    return;
            }
        } catch (Exception e) {
        }
    }

    private bd aK(int i) {
        byte[] bArrX;
        switch (i) {
            case 0:
                bArrX = o.x(Ax);
                break;
            case 1:
                bArrX = o.x(Aw);
                break;
            case 2:
                bArrX = o.x(Ay);
                break;
            default:
                bArrX = null;
                break;
        }
        if (bArrX == null) {
            return null;
        }
        try {
            String strC = o.c(bArrX, 0);
            bx bxVar = this.AG;
            String strDV = strC.startsWith("JavaGLOTv3Encrypted") ? this.AG.dV(strC) : strC;
            if (o.B(strDV)) {
                o.u("Data read on GetMessage from file " + i + " have control character!");
                this.AG.c(162005, "[Manager]: (!) Sending isolated invalid package over the network!", "N/A");
                switch (i) {
                    case 0:
                        aI(0);
                        return null;
                    case 1:
                        aI(1);
                        return null;
                    case 2:
                        aI(2);
                        return null;
                    default:
                        return null;
                }
            }
            return new bd(strDV);
        } catch (Exception e) {
            o.d(e);
            return null;
        }
    }

    private bd aL(int i) {
        byte[] bArrX;
        switch (i) {
            case 0:
                synchronized (this.AE) {
                    bArrX = o.x(AA);
                    break;
                }
                break;
            case 1:
                synchronized (this.AD) {
                    bArrX = o.x(Az);
                    break;
                }
                break;
            case 2:
                synchronized (this.AF) {
                    bArrX = o.x(AB);
                    break;
                }
                break;
            default:
                bArrX = null;
                break;
        }
        if (bArrX == null) {
            return null;
        }
        try {
            String strC = o.c(bArrX, 0);
            bx bxVar = this.AG;
            String strDV = strC.startsWith("JavaGLOTv3Encrypted") ? this.AG.dV(strC) : strC;
            if (o.B(strDV)) {
                o.u("Data read on GetMessageAsync from file " + i + " have control character!");
                this.AG.c(162005, "[Manager]: (!) Sending isolated invalid package over the network!", "N/A");
                return null;
            }
            return new bd(strDV);
        } catch (Exception e) {
            o.d(e);
            return null;
        }
    }
}
