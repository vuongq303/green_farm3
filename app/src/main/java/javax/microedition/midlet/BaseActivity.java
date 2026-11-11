package javax.microedition.midlet;

import android.app.Activity;
import android.content.BroadcastReceiver;
import android.content.Intent;
import android.content.IntentFilter;
import android.media.AudioManager;
import android.os.Bundle;
import com.gameloft.android.wrapper.au;

/* JADX INFO: loaded from: classes.dex */
public class BaseActivity extends Activity {
    public static final IntentFilter cnv = akx();
    private BroadcastReceiver cnu = new h(this);
    private boolean cnw = false;
    private AudioManager cnx;

    @Override // android.app.Activity
    protected void onCreate(Bundle bundle) {
        this.cnw = false;
        super.onCreate(bundle);
        aky();
    }

    @Override // android.app.Activity
    protected void onResume() {
        super.onResume();
        String str = "m_keepMusicOfPlayer = " + MIDlet.coq;
        if (!MIDlet.coq && au.getContext() != null) {
            if (this.cnx == null) {
                this.cnx = (AudioManager) au.getContext().getSystemService("audio");
            }
            if (this.cnx.isMusicActive()) {
                this.cnx.requestAudioFocus(new e(this), 3, 1);
            }
        }
    }

    @Override // android.app.Activity, android.view.Window.Callback
    public void onWindowFocusChanged(boolean z) {
        super.onWindowFocusChanged(z);
        if (z) {
            String str = "m_keepMusicOfPlayer = " + MIDlet.coq;
            if (!MIDlet.coq && au.getContext() != null) {
                if (this.cnx == null) {
                    this.cnx = (AudioManager) au.getContext().getSystemService("audio");
                }
                if (this.cnx.isMusicActive()) {
                    this.cnx.requestAudioFocus(new f(this), 3, 1);
                }
            }
        }
    }

    @Override // android.app.Activity
    protected void onDestroy() {
        super.onDestroy();
        akz();
    }

    private static IntentFilter akx() {
        IntentFilter intentFilter = new IntentFilter();
        intentFilter.addAction("com.gameloft.android.wrapper.FISH_ALL_ACTIVITIES_RUNNING.com.gameloft.android.ANMP.GloftGF2F");
        return intentFilter;
    }

    protected void aky() {
        this.cnw = true;
        registerReceiver(this.cnu, cnv);
    }

    protected void akz() {
        if (this.cnw) {
            this.cnw = false;
            unregisterReceiver(this.cnu);
        }
    }

    public void akA() {
        sendBroadcast(new Intent("com.gameloft.android.wrapper.FISH_ALL_ACTIVITIES_RUNNING.com.gameloft.android.ANMP.GloftGF2F"));
    }

    public boolean akB() {
        try {
            if (this.cnx == null) {
                this.cnx = (AudioManager) au.getContext().getSystemService("audio");
            }
            return this.cnx.isMusicActive();
        } catch (Exception e) {
            return false;
        }
    }

    public void akC() {
        try {
            if (this.cnx == null) {
                this.cnx = (AudioManager) au.getContext().getSystemService("audio");
            }
            if (this.cnx.isMusicActive()) {
                this.cnx.requestAudioFocus(new g(this), 3, 1);
            }
        } catch (Exception e) {
        }
    }
}
