package javax.microedition.midlet;

import android.app.Activity;
import android.app.Application;
import android.content.IntentFilter;
import android.os.Build;
import android.view.View;
import android.view.ViewTreeObserver;
import com.gameloft.android.ANMP.GloftGF2F.bx;
import java.util.List;

/* JADX INFO: loaded from: classes.dex */
public class App extends Application {
    private static final int cnm = Build.VERSION.SDK_INT;
    private static App cnn;
    private c cnp;
    private ViewTreeObserver.OnWindowAttachListener cnq;
    private ViewTreeObserver.OnWindowFocusChangeListener cnr;
    private boolean cno = false;
    private Activity cns = null;

    public static App aks() {
        return cnn;
    }

    @Override // android.app.Application
    public void onCreate() {
        super.onCreate();
        cnn = this;
        this.cnp = new c(this);
        if (cnm >= 18) {
            akt();
        }
        registerActivityLifecycleCallbacks(this.cnp);
        registerReceiver(new d(this), new IntentFilter("android.intent.action.SCREEN_OFF"));
    }

    private void akt() {
        this.cnq = new a(this);
        this.cnr = new b(this);
    }

    @Override // android.app.Application, android.content.ComponentCallbacks2
    public void onTrimMemory(int i) {
        super.onTrimMemory(i);
        if (this.cno && i >= 20) {
            this.cno = false;
            akv();
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void aku() {
        if (bx.ND() != null) {
            bx.ND().NG();
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void akv() {
        if (bx.ND() != null) {
            bx.ND().NH();
        }
    }

    void cp(boolean z) {
        if (z && !this.cno) {
            this.cno = true;
            aku();
            return;
        }
        if (!z && this.cno) {
            if (!ad.akU()) {
                this.cno = false;
                akv();
            } else if (cnm >= 18) {
                View rootView = this.cns.getWindow().getDecorView().getRootView();
                View viewAkT = ad.akT();
                if (viewAkT != null && viewAkT != rootView) {
                    ViewTreeObserver viewTreeObserver = viewAkT.getViewTreeObserver();
                    viewTreeObserver.addOnWindowAttachListener(this.cnq);
                    viewTreeObserver.addOnWindowFocusChangeListener(this.cnr);
                }
            }
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void akw() {
        List<View> listAkS = ad.akS();
        int size = listAkS.size();
        for (int i = 0; i < size; i++) {
            ViewTreeObserver viewTreeObserver = listAkS.get(i).getViewTreeObserver();
            viewTreeObserver.removeOnWindowAttachListener(this.cnq);
            viewTreeObserver.removeOnWindowFocusChangeListener(this.cnr);
        }
        View rootView = null;
        if (this.cns != null) {
            rootView = this.cns.getWindow().getDecorView().getRootView();
        }
        View viewAkT = ad.akT();
        if (viewAkT != null && viewAkT != rootView) {
            ViewTreeObserver viewTreeObserver2 = viewAkT.getViewTreeObserver();
            viewTreeObserver2.addOnWindowAttachListener(this.cnq);
            viewTreeObserver2.addOnWindowFocusChangeListener(this.cnr);
        }
    }
}
