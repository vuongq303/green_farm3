package javax.microedition.lcdui;

import android.content.Context;
import android.graphics.Paint;
import android.os.Build;
import android.view.Display;
import android.view.KeyEvent;
import android.view.MotionEvent;
import android.view.WindowManager;
import com.gameloft.android.wrapper.au;
import java.lang.reflect.Method;
import java.util.ArrayList;
import javax.microedition.khronos.egl.EGLConfig;
import javax.microedition.khronos.opengles.GL10;
import javax.microedition.midlet.MIDlet;

/* JADX INFO: compiled from: Canvas.java */
/* JADX INFO: loaded from: classes.dex */
public abstract class f extends t {
    private boolean clG = false;
    private Method clH = null;
    boolean clS = false;
    public boolean clU = false;
    public ArrayList<Boolean> clV = new ArrayList<>();
    n clW;
    m cma;
    public static f clA = null;
    public static boolean clB = false;
    public static boolean clC = false;
    public static boolean clD = true;
    public static int clE = 2;
    public static boolean clF = true;
    static int bgn = -1;
    static int bgo = -1;
    private static float clI = 1.0f;
    private static float clJ = 1.0f;
    private static boolean clK = false;
    private static int bgr = 0;
    private static int bgs = 0;
    private static int clL = 0;
    private static int clM = 0;
    private static boolean clN = false;
    private static boolean clO = false;
    private static Paint clP = null;
    static long clQ = 0;
    static int brs = 1;
    static int clR = 0;
    public static int clT = 0;
    private static int clX = -7;
    private static int clY = -8;
    private static boolean clZ = true;

    public f() {
        clA = this;
        Context context = au.getContext();
        if (context != null) {
            WindowManager windowManager = (WindowManager) context.getSystemService("window");
            if (windowManager != null) {
                Display defaultDisplay = windowManager.getDefaultDisplay();
                if (defaultDisplay != null) {
                    if (!clO) {
                        bgn = defaultDisplay.getWidth();
                        bgo = defaultDisplay.getHeight();
                        if (com.gameloft.android.wrapper.d.beG.compareTo("landscape") == 0 && bgn < bgo) {
                            int i = bgn;
                            bgn = bgo;
                            bgo = i;
                        } else if (com.gameloft.android.wrapper.d.beG.compareTo("portrait") == 0 && bgn > bgo) {
                            int i2 = bgn;
                            bgn = bgo;
                            bgo = i2;
                        }
                    }
                    String str = "orientation: " + defaultDisplay.getOrientation();
                    return;
                }
                String str2 = "Can't detect screen size. display is null!!! Using default: " + bgn + "x" + bgo;
                return;
            }
            String str3 = "Can't detect screen size. windowManager is null!!! Using default: " + bgn + "x" + bgo;
            return;
        }
        String str4 = "Can't detect screen size. context is null!!! Using default: " + bgn + "x" + bgo;
    }

    @Override // javax.microedition.lcdui.t
    public int getHeight() {
        return bgo == -1 ? super.getHeight() : bgo;
    }

    @Override // javax.microedition.lcdui.t
    public int getWidth() {
        return bgn == -1 ? super.getWidth() : bgn;
    }

    @Override // javax.microedition.lcdui.t
    public void cI(int i, int i2) {
        int i3;
        int i4;
        if (com.gameloft.android.wrapper.d.beG.compareTo("landscape") == 0 && i < i2) {
            i3 = i;
            i4 = i2;
        } else if (com.gameloft.android.wrapper.d.beG.compareTo("portrait") != 0 || i <= i2) {
            i3 = i2;
            i4 = i;
        } else {
            i3 = i;
            i4 = i2;
        }
        String str = "Canvas.onSizeChange: " + i4 + ", " + i3 + " orientation:" + com.gameloft.android.wrapper.d.beG;
        cK(i4, i3);
    }

    public static void setScale(float f, float f2) {
        clI = f;
        clJ = f2;
        clK = true;
    }

    public static float getScaleX() {
        return clI;
    }

    public static float getScaleY() {
        return clJ;
    }

    public static void Q(int i, int i2, int i3, int i4) {
        bgr = i;
        bgs = i2;
        clL = i3;
        clM = i4;
        clN = true;
    }

    public static void cJ(int i, int i2) {
        clO = true;
        bgn = i;
        bgo = i2;
    }

    public static int ajV() {
        return bgr;
    }

    public static int ajW() {
        return bgs;
    }

    public static void cm(boolean z) {
        if (z) {
            if (clP == null) {
                clP = new Paint();
                clP.setFilterBitmap(true);
                return;
            }
            return;
        }
        clP = null;
    }

    @Override // javax.microedition.lcdui.t
    public void onWindowFocusChanged(boolean z) {
        String str = "Canvas.onWindowFocusChanged: " + z;
        T(0, z);
    }

    @Override // javax.microedition.lcdui.t
    public void T(int i, boolean z) {
        String str = "onGameInterrupt from:" + i + " and hasWindowFocus:" + z;
        if (this.clS != z) {
            synchronized (this.clV) {
                this.clV.add(new Boolean(z));
            }
            if (!MIDlet.con) {
                if (!z || !MIDlet.coo) {
                    if (!this.clV.get(0).booleanValue() || !MIDlet.cop) {
                        if (!z || !au.biO) {
                            if (!this.clU) {
                                this.clU = true;
                                ajX();
                            }
                            this.clS = z;
                            return;
                        }
                        return;
                    }
                    return;
                }
                return;
            }
            return;
        }
        String str2 = "Canvas.onGameInterrupt IGNORED twice!: " + z;
    }

    private void ajX() {
        new Thread(new g(this)).start();
    }

    public n ajY() {
        Exception e;
        Method method;
        g gVar = null;
        try {
            Class<?> cls = getClass();
            if (clF) {
                Class<?> cls2 = getClass();
                if (com.gameloft.android.wrapper.d.beP || com.gameloft.android.wrapper.d.beQ) {
                    cls = Class.forName("javax.microedition.lcdui.GLLibInstances");
                } else {
                    cls = Class.forName(cls2.getPackage().getName() + ".GLLib");
                }
            }
            Method declaredMethod = cls.getDeclaredMethod("Pointer_GetNumPointers", new Class[0]);
            if (!declaredMethod.isAccessible()) {
                declaredMethod.setAccessible(true);
            }
            Method declaredMethod2 = cls.getDeclaredMethod("Pointer_SetCurrentPointer", Integer.TYPE);
            try {
                if (!declaredMethod2.isAccessible()) {
                    declaredMethod2.setAccessible(true);
                }
                clE = ((Integer) declaredMethod.invoke(this, new Object[0])).intValue();
                method = declaredMethod2;
            } catch (Exception e2) {
                e = e2;
                method = declaredMethod2;
                String str = "Exception get num pointer:" + e.toString();
                clE = 1;
            }
        } catch (Exception e3) {
            e = e3;
            method = null;
        }
        String str2 = "Num pointer:" + clE;
        if (clE == 1 || method == null) {
            return new i(this, gVar);
        }
        int i = Integer.parseInt(Build.VERSION.SDK);
        if (i < 5) {
            return new i(this, gVar);
        }
        if (i < 8) {
            return new k(this, method);
        }
        return new l(this, method);
    }

    public static void cn(boolean z) {
        clZ = z;
    }

    public static void pc(int i) {
        clX = i;
    }

    public static void pd(int i) {
        clY = i;
    }

    public m ajZ() {
        g gVar = null;
        if (Integer.parseInt(Build.VERSION.SDK) < 5) {
            return new h(this, gVar);
        }
        return new j(this, gVar);
    }

    @Override // javax.microedition.lcdui.t
    public boolean onTouchEvent(MotionEvent motionEvent) {
        if (this.clW == null) {
            this.clW = ajY();
        }
        return this.clW.onTouchEvent(motionEvent);
    }

    @Override // javax.microedition.lcdui.t
    public boolean onKeyEvent(KeyEvent keyEvent) {
        if (this.cma == null) {
            this.cma = ajZ();
        }
        return this.cma.onKeyEvent(keyEvent);
    }

    public void co(boolean z) {
    }

    protected void cM(int i) {
    }

    protected void pe(int i) {
    }

    protected void cN(int i) {
    }

    protected void V(int i, int i2) {
    }

    protected void U(int i, int i2) {
    }

    protected void W(int i, int i2) {
    }

    public boolean k(float f, float f2) {
        return false;
    }

    public final void aka() {
        postInvalidate();
    }

    public void jG() {
    }

    public void jF() {
    }

    @Override // javax.microedition.lcdui.t
    protected void cK(int i, int i2) {
    }

    @Override // javax.microedition.lcdui.t
    public void onDrawFrame(GL10 gl10) {
    }

    @Override // javax.microedition.lcdui.t
    public void onSurfaceCreated(GL10 gl10, EGLConfig eGLConfig) {
    }

    @Override // javax.microedition.lcdui.t
    public void onSurfaceChanged(GL10 gl10, int i, int i2) {
    }

    public void onPause() {
    }

    public void onResume() {
    }

    @Override // javax.microedition.lcdui.t
    public void cl(boolean z) {
        ajT().cl(z);
    }
}
