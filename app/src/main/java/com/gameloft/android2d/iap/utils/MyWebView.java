package com.gameloft.android2d.iap.utils;

import android.app.Activity;
import android.os.Bundle;
import android.telephony.SmsManager;
import android.view.KeyEvent;
import android.webkit.WebView;
import com.gameloft.android2d.iap.ae;
import java.util.HashMap;
import javax.microedition.midlet.MIDlet;
import org.json.JSONException;

/* JADX INFO: loaded from: classes.dex */
public class MyWebView extends Activity {
    private static String boh;
    private WebView bog = null;
    private boolean boj = false;
    private boolean bok = false;
    private String bol;
    private String bom;
    private static com.gameloft.android2d.iap.a.a bof = null;
    private static HashMap<String, String> boi = new HashMap<>();

    @Override // android.app.Activity
    public void onCreate(Bundle bundle) {
        super.onCreate(bundle);
        MIDlet.coD = 1;
        this.boj = false;
        setContentView(com.b.c.cfK);
        this.bog = (WebView) findViewById(com.b.b.cfD);
        this.bog.getSettings().setJavaScriptEnabled(true);
        this.bog.loadUrl(boh, boi);
        this.bog.setWebViewClient(new t(this, this));
    }

    public void Ta() {
        runOnUiThread(new s(this));
    }

    /* JADX INFO: Access modifiers changed from: private */
    public String Tb() {
        String strSubstring = this.bog.getUrl().substring(this.bog.getUrl().indexOf("&id=") + 4, this.bog.getUrl().length());
        return strSubstring.substring(0, strSubstring.indexOf("&trxid="));
    }

    /* JADX INFO: Access modifiers changed from: private */
    public String Tc() {
        String strSubstring = this.bog.getUrl().substring(this.bog.getUrl().indexOf("&trxid=") + 7, this.bog.getUrl().length());
        return strSubstring.substring(0, strSubstring.indexOf("&v="));
    }

    /* JADX INFO: Access modifiers changed from: private */
    public String Td() {
        return this.bog.getUrl().substring(this.bog.getUrl().indexOf("&error=") + 7, this.bog.getUrl().indexOf("&message="));
    }

    @Override // android.app.Activity, android.view.KeyEvent.Callback
    public boolean onKeyUp(int i, KeyEvent keyEvent) throws JSONException {
        if (i == 4 && this.bog != null && !this.bok) {
            if (!this.bog.canGoBack()) {
                if (!this.boj) {
                    com.gameloft.android2d.iap.b.setResult(10);
                    com.gameloft.android2d.iap.b.mB(-100);
                    ae.KB();
                    Te();
                }
            } else {
                this.bog.goBack();
            }
            return true;
        }
        return super.onKeyDown(i, keyEvent);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void Te() {
        try {
            if (this.bog != null) {
                this.bog.clearHistory();
            }
        } catch (Exception e) {
        }
        finish();
    }

    @Override // android.app.Activity
    public void onRequestPermissionsResult(int i, String[] strArr, int[] iArr) {
        if (strArr[0].equals("android.permission.SEND_SMS")) {
            if (iArr[0] == 0) {
                Tf();
            } else {
                com.gameloft.android2d.iap.b.eN("android.permission.SEND_SMS");
            }
        }
        Te();
    }

    public void Tf() {
        SmsManager.getDefault().sendTextMessage(this.bol, null, this.bom, null, null);
        u.fV(boi.get(v.mJ(com.b.d.cio)));
        bof.G(System.currentTimeMillis());
    }

    @Override // android.app.Activity
    protected void onDestroy() {
        if (this.bog != null) {
            this.bog.removeAllViews();
            this.bog.destroy();
        }
        super.onDestroy();
        MIDlet.coD = 2;
    }
}
