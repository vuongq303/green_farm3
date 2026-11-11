package javax.microedition.lcdui;

import android.graphics.Color;
import android.graphics.Point;
import android.os.Build;
import android.view.Display;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.FrameLayout;
import com.gameloft.android.wrapper.au;
import java.lang.reflect.Method;

/* JADX INFO: compiled from: Display.java */
/* JADX INFO: loaded from: classes.dex */
class s implements Runnable {
    final /* synthetic */ r cmq;

    s(r rVar) {
        this.cmq = rVar;
    }

    /* JADX WARN: Type inference fix 'apply assigned field type' failed
    java.lang.UnsupportedOperationException: ArgType.getObject(), call class: class jadx.core.dex.instructions.args.ArgType$UnknownArg
    	at jadx.core.dex.instructions.args.ArgType.getObject(ArgType.java:596)
    	at jadx.core.dex.attributes.nodes.ClassTypeVarsAttr.getTypeVarsMapFor(ClassTypeVarsAttr.java:35)
    	at jadx.core.dex.nodes.utils.TypeUtils.replaceClassGenerics(TypeUtils.java:177)
    	at jadx.core.dex.visitors.typeinference.FixTypesVisitor.insertExplicitUseCast(FixTypesVisitor.java:397)
    	at jadx.core.dex.visitors.typeinference.FixTypesVisitor.tryFieldTypeWithNewCasts(FixTypesVisitor.java:359)
    	at jadx.core.dex.visitors.typeinference.FixTypesVisitor.applyFieldType(FixTypesVisitor.java:309)
    	at jadx.core.dex.visitors.typeinference.FixTypesVisitor.visit(FixTypesVisitor.java:94)
     */
    @Override // java.lang.Runnable
    public void run() {
        int i;
        int i2;
        if (((ViewGroup) ((View) r.cmm.ajT()).getParent()) != null && ((ViewGroup) ((View) r.cmm.ajT()).getParent()).indexOfChild((View) r.cmm.ajT()) >= 0) {
            ((ViewGroup) ((View) r.cmm.ajT()).getParent()).removeView((View) r.cmm.ajT());
        }
        if (r.cmp) {
            r.cmp = false;
            FrameLayout frameLayout = new FrameLayout(au.getActivity());
            Display defaultDisplay = ((WindowManager) au.getContext().getSystemService("window")).getDefaultDisplay();
            int width = defaultDisplay.getWidth() - com.gameloft.android.wrapper.d.bfm;
            int height = defaultDisplay.getHeight() - com.gameloft.android.wrapper.d.bfn;
            String str = "default width=" + width + " and default height=" + height;
            String str2 = "canvas width=" + r.cmm.getWidth() + " and canvas height=" + r.cmm.getHeight();
            if (com.gameloft.android.wrapper.d.beH || com.gameloft.android.wrapper.d.beI) {
                width = com.gameloft.android.wrapper.ae.bga;
                height = com.gameloft.android.wrapper.ae.bgb;
            }
            if (com.gameloft.android.wrapper.d.beM && !Build.MODEL.equals("SM-N915FY") && com.gameloft.android.wrapper.d.bfm == 0 && com.gameloft.android.wrapper.d.bfn == 0) {
                Point point = new Point();
                try {
                    Method declaredMethod = Class.forName("android.view.Display").getDeclaredMethod("getRealSize", Point.class);
                    if (!declaredMethod.isAccessible()) {
                        declaredMethod.setAccessible(true);
                    }
                    declaredMethod.invoke(defaultDisplay, point);
                } catch (Exception e) {
                }
                width = point.x;
                height = point.y;
            }
            String str3 = "Orientation:" + com.gameloft.android.wrapper.d.beG;
            if ((com.gameloft.android.wrapper.d.beG.compareTo("sensorLandscape") != 0 || width >= height) && (com.gameloft.android.wrapper.d.beG.compareTo("portrait") != 0 || width <= height)) {
                i = height;
                i2 = width;
            } else {
                i = width;
                i2 = height;
            }
            String str4 = "width=" + i2 + " and height=" + i;
            FrameLayout.LayoutParams layoutParams = new FrameLayout.LayoutParams(new ViewGroup.LayoutParams(-1, -1));
            if (com.gameloft.android.wrapper.d.bfm != 0) {
                String str5 = "m_CAMERA_SPACING_LEFT: " + com.gameloft.android.wrapper.d.bfm;
                layoutParams.setMargins(com.gameloft.android.wrapper.d.bfm, 0, 0, 0);
            } else if (com.gameloft.android.wrapper.d.bfn != 0) {
                String str6 = "m_CAMERA_SPACING_TOP: " + com.gameloft.android.wrapper.d.bfn;
                layoutParams.setMargins(0, com.gameloft.android.wrapper.d.bfn, 0, 0);
            }
            frameLayout.setBackgroundColor(Color.parseColor("#FF000000"));
            frameLayout.addView((View) r.cmm.ajT(), layoutParams);
            this.cmq.cmo.setContentView(frameLayout);
            this.cmq.cmo.cou = r.cmm;
        }
    }
}
