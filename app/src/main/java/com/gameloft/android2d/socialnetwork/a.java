package com.gameloft.android2d.socialnetwork;

import android.R;
import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.IntentSender;
import android.content.SharedPreferences;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.GooglePlayServicesUtil;
import com.google.android.gms.common.api.Scope;
import com.google.android.gms.games.multiplayer.Invitation;
import com.google.android.gms.games.multiplayer.turnbased.TurnBasedMatch;
import com.google.android.gms.games.request.GameRequest;
import java.util.ArrayList;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import javax.microedition.midlet.MIDlet;

/* JADX INFO: compiled from: GameHelper.java */
/* JADX INFO: loaded from: classes.dex */
public class a implements com.google.android.gms.common.api.y, com.google.android.gms.common.api.z {
    public static String bmK = "";
    TurnBasedMatch bpA;
    ArrayList<GameRequest> bpB;
    Invitation bpz;
    Context eJ;
    Activity mActivity;
    public boolean bmc = false;
    private boolean bpl = false;
    public boolean bpm = false;
    boolean bpn = false;
    boolean bpo = false;
    com.google.android.gms.common.api.x bpp = null;
    com.google.android.gms.games.d bpq = com.google.android.gms.games.d.acn().aco();
    com.google.android.gms.plus.e bpr = null;
    com.google.android.gms.common.api.w bps = null;
    int bpk = 0;
    boolean bpt = true;
    boolean bpu = false;
    boolean bpv = false;
    ConnectionResult bpw = null;
    f bpx = null;
    boolean bpy = false;
    boolean bma = false;
    e bpC = null;
    int bpD = 1;
    private final String bpE = "GAMEHELPER_SHARED_PREFS";
    private final String bpF = "KEY_SIGN_IN_CANCELLATIONS";
    Handler mHandler = new Handler();

    public a(Activity activity) {
        this.mActivity = null;
        this.eJ = null;
        this.mActivity = activity;
        this.eJ = activity.getApplicationContext();
    }

    void gS(String str) {
        if (!this.bmc) {
            String str2 = "GameHelper error: Operation attempted without setup: " + str + ". The setup() method must be called before attempting any other operation.";
            fu("*** GameHelper ERROR: " + str2);
            throw new IllegalStateException(str2);
        }
    }

    public boolean mP(int i) {
        return (this.bpk & i) != 0;
    }

    public com.google.android.gms.common.api.x Uv() {
        if (this.bmc) {
            fu("*** GameHelper ERROR: GameHelper: you called GameHelper.createApiClientBuilder() after calling setup. You can only get a client builder BEFORE performing setup.");
            throw new IllegalStateException("GameHelper: you called GameHelper.createApiClientBuilder() after calling setup. You can only get a client builder BEFORE performing setup.");
        }
        com.google.android.gms.common.api.x xVar = new com.google.android.gms.common.api.x(this.mActivity, this, this);
        if (mP(1)) {
            xVar.a(com.google.android.gms.games.c.API, this.bpq);
            xVar.a(com.google.android.gms.games.c.bGK);
            xVar.a(new Scope("profile"));
        }
        if (mP(2)) {
            xVar.a(com.google.android.gms.plus.d.API, this.bpr);
            xVar.a(com.google.android.gms.plus.d.bXM);
        }
        this.bpp = xVar;
        return xVar;
    }

    public void a(e eVar, int i) {
        if (this.bmc) {
            fu("*** GameHelper ERROR: GameHelper: you cannot call GameHelper.setup() more than once!");
            throw new IllegalStateException("GameHelper: you cannot call GameHelper.setup() more than once!");
        }
        this.bpC = eVar;
        this.bpk = i;
        gT("GameHelper: Setup: requested clients: " + this.bpk);
        if (this.bpp == null) {
            Uv();
        }
        this.bps = this.bpp.YP();
        this.bpp = null;
        this.bmc = true;
    }

    public com.google.android.gms.common.api.w Uw() {
        if (this.bps == null) {
            throw new IllegalStateException("No GoogleApiClient. Did you call setup()?");
        }
        return this.bps;
    }

    public boolean Ux() {
        boolean z = this.bps != null && this.bps.isConnected();
        if (mP(2)) {
            return (bmK == null || bmK.isEmpty() || !z) ? false : true;
        }
        return z;
    }

    public boolean isConnecting() {
        return this.bpl;
    }

    public boolean Uy() {
        return this.bpo;
    }

    public void a(Activity activity, boolean z) {
        this.mActivity = activity;
        this.eJ = activity.getApplicationContext();
        gT("GameHelper: onStart");
        gS("onStart");
        if (Build.VERSION.SDK_INT >= 23) {
        }
        if (this.bpt && !z) {
            if (!this.bps.isConnected()) {
                gT("GameHelper: Connecting client.");
                this.bpl = true;
                this.bps.connect();
                return;
            }
            return;
        }
        if (z) {
            gT("GameHelper: Not attempting to connect becase deferAutoLogIn=true");
            return;
        }
        gT("GameHelper: Not attempting to connect becase mConnectOnStart=false");
        gT("GameHelper: Instead, reporting a sign-in failure.");
        this.mHandler.postDelayed(new b(this), 1000L);
    }

    public void onStop() {
        gT("GameHelper: onStop");
        gS("onStop");
        if (this.bps.isConnected()) {
            gT("GameHelper: Disconnecting client due to onStop");
            this.bps.disconnect();
        } else {
            gT("GameHelper: Client already disconnected when we got onStop.");
        }
        this.bpl = false;
        this.bpn = false;
        this.mActivity = null;
    }

    public void bJ(boolean z) {
        this.bma = z;
        if (z) {
            gT("Debug log enabled.");
        }
    }

    public void Uz() {
        if (!this.bps.isConnected()) {
            gT("GameHelper: signOut: was already disconnected, ignoring.");
            return;
        }
        UC();
        UG();
        if (mP(2)) {
            gT("GameHelper: Clearing default account on PlusClient.");
            com.google.android.gms.plus.d.bXP.clearDefaultAccount(this.bps);
        }
        if (mP(1)) {
            gT("GameHelper: Signing out from the Google API Client.");
            try {
                com.google.android.gms.games.c.b(this.bps);
            } catch (Exception e) {
                gT("Sign out on Games Exception: " + e.toString());
            }
        }
        gT("Disconnecting client.");
        this.bpt = false;
        this.bpl = false;
        this.bps.disconnect();
    }

    public void onActivityResult(int i, int i2, Intent intent) {
        gT("GameHelper: onActivityResult: req=" + (i == 9001 ? "RC_RESOLVE" : String.valueOf(i)) + ", resp=" + g.mQ(i2));
        if (i == 9002) {
            gT("onAR: responseCode=" + g.mQ(i2) + ", so giving up.");
            b(new f(this.bpw.getErrorCode(), i2));
            this.bpw = null;
            return;
        }
        if (i != 9001) {
            gT("GameHelper: onActivityResult: request code not meant for us. Ignoring.");
            return;
        }
        this.bpn = false;
        if (!this.bpl) {
            gT("GameHelper: onActivityResult: ignoring because we are not connecting.");
            return;
        }
        gT("GameHelper: javax.microedition.midlet.MIDlet.m_isLaunchGoogle = false");
        MIDlet.coo = false;
        if (i2 == -1) {
            gT("GameHelper: onAR: Resolution was RESULT_OK, so connecting current client again.");
            connect();
            return;
        }
        if (i2 == 10001) {
            gT("GameHelper: onAR: Resolution was RECONNECT_REQUIRED, so reconnecting.");
            connect();
            return;
        }
        if (i2 == 0) {
            gT("GameHelper: onAR: Got a cancellation result, so disconnecting.");
            this.bpo = true;
            this.bpt = false;
            this.bpv = false;
            this.bpx = null;
            this.bpl = false;
            this.bps.disconnect();
            gT("GameHelper: onAR: # of cancellations " + UF() + " --> " + UG() + ", max " + this.bpD);
            bK(false);
            return;
        }
        gT("GameHelper: onAR: responseCode=" + g.mQ(i2) + ", so giving up.");
        a(new f(this.bpw.getErrorCode(), i2));
    }

    void bK(boolean z) {
        String str;
        StringBuilder sbAppend = new StringBuilder().append("GameHelper: Notifying LISTENER of sign-in ");
        if (z) {
            str = "SUCCESS";
        } else {
            str = this.bpx != null ? "FAILURE (error)" : "FAILURE (no error)";
        }
        gT(sbAppend.append(str).toString());
        if (this.bpC != null) {
            if (z) {
                this.bpC.UK();
            } else {
                this.bpC.UJ();
            }
        }
    }

    public int UA() {
        int iIsGooglePlayServicesAvailable = GooglePlayServicesUtil.isGooglePlayServicesAvailable(this.eJ);
        gT("Google: isGooglePlayServicesAvailable returned " + iIsGooglePlayServicesAvailable);
        return iIsGooglePlayServicesAvailable;
    }

    public void UB() {
        gT("GameHelper: beginUserInitiatedSignIn: resetting attempt count.");
        this.bpo = false;
        int iUA = UA();
        if (iUA != 0) {
            gT("Google: Google Play services not available. Show error dialog.");
            this.bpm = true;
            if (this.mActivity == null) {
                gT("GameHelper: Google: No Activity. Can't show failure dialog!");
                return;
            } else {
                GooglePlayServicesUtil.getErrorDialog(iUA, this.mActivity, 9002, null).show();
                this.bpw = null;
                return;
            }
        }
        this.bpt = true;
        if (this.bps.isConnected()) {
            fv("!!! GameHelper WARNING: beginUserInitiatedSignIn() called when already connected. Calling listener directly to notify of success.");
            bK(true);
            return;
        }
        if (this.bpl) {
            fv("!!! GameHelper WARNING: beginUserInitiatedSignIn() called when already connecting. Be patient! You can only call this method after you get an onSignInSucceeded() or onSignInFailed() callback. Suggestion: disable the sign-in button on startup and also when it's clicked, and re-enable when you get the callback.");
            return;
        }
        gT("GameHelper: Starting USER-INITIATED sign-in flow.");
        this.bpv = true;
        if (this.bpw != null) {
            gT("GameHelper: beginUserInitiatedSignIn: continuing pending sign-in flow.");
            this.bpl = true;
            UH();
        } else {
            gT("GameHelper: beginUserInitiatedSignIn: starting new sign-in flow.");
            this.bpl = true;
            connect();
        }
    }

    void connect() {
        if (this.bps.isConnected()) {
            gT("GameHelper: Already connected.");
            return;
        }
        gT("GameHelper: Starting connection.");
        this.bpl = true;
        this.bpz = null;
        this.bpA = null;
        this.bps.connect();
    }

    @Override // com.google.android.gms.common.api.y
    public void onConnected(Bundle bundle) {
        gT("GameHelper: onConnected: connected!");
        if (bundle != null) {
            gT("GameHelper: onConnected: connection hint provided. Checking for invite.");
            Invitation invitation = (Invitation) bundle.getParcelable("invitation");
            if (invitation != null && invitation.getInvitationId() != null) {
                gT("GameHelper: onConnected: connection hint has a room invite!");
                this.bpz = invitation;
                gT("GameHelper: Invitation ID: " + this.bpz.getInvitationId());
            }
            this.bpB = com.google.android.gms.games.c.bSf.getGameRequestsFromBundle(bundle);
            if (!this.bpB.isEmpty()) {
                gT("GameHelper: onConnected: connection hint has " + this.bpB.size() + " request(s)");
            }
            gT("GameHelper: onConnected: connection hint provided. Checking for TBMP game.");
            this.bpA = (TurnBasedMatch) bundle.getParcelable("turn_based_match");
        }
        if (mP(2)) {
            UD();
        } else if (mP(1)) {
            UE();
        } else {
            a(new f(30));
        }
    }

    public void UC() {
        c cVar = new c(this);
        try {
            ExecutorService executorServiceNewSingleThreadExecutor = Executors.newSingleThreadExecutor();
            executorServiceNewSingleThreadExecutor.submit(cVar);
            executorServiceNewSingleThreadExecutor.shutdown();
        } catch (Exception e) {
            gT("GameHelper: invalidateAccessToken: exception thrown when executings:" + e.toString());
        }
    }

    public void UD() {
        bmK = "";
        d dVar = new d(this);
        try {
            ExecutorService executorServiceNewSingleThreadExecutor = Executors.newSingleThreadExecutor();
            executorServiceNewSingleThreadExecutor.submit(dVar);
            executorServiceNewSingleThreadExecutor.shutdown();
        } catch (Exception e) {
            gT("GameHelper: Google: Error startin token task:" + e.toString());
            a(new f(30));
        }
    }

    void UE() {
        gT("GameHelper: succeedSignIn");
        this.bpx = null;
        this.bpt = true;
        this.bpv = false;
        this.bpl = false;
        bK(true);
    }

    int UF() {
        return this.eJ.getSharedPreferences("GAMEHELPER_SHARED_PREFS", 0).getInt("KEY_SIGN_IN_CANCELLATIONS", 0);
    }

    int UG() {
        int iUF = UF();
        SharedPreferences.Editor editorEdit = this.eJ.getSharedPreferences("GAMEHELPER_SHARED_PREFS", 0).edit();
        editorEdit.putInt("KEY_SIGN_IN_CANCELLATIONS", iUF + 1);
        editorEdit.commit();
        return iUF + 1;
    }

    @Override // com.google.android.gms.common.api.z
    public void onConnectionFailed(ConnectionResult connectionResult) {
        boolean z = true;
        gT("GameHelper: onConnectionFailed");
        this.bpw = connectionResult;
        gT("GameHelper: Connection failure:");
        gT("GameHelper:    - code: " + g.mR(this.bpw.getErrorCode()));
        gT("GameHelper:    - resolvable: " + this.bpw.Yy());
        gT("GameHelper:    - details: " + this.bpw.toString());
        int iUF = UF();
        if (this.bpv) {
            if (this.bpu) {
                gT("GameHelper: onConnectionFailed: WILL NOT resolve because this is auto re-login.");
                z = false;
            } else {
                gT("GameHelper: onConnectionFailed: WILL resolve because user initiated sign-in.");
            }
        } else if (this.bpo) {
            gT("GameHelper: onConnectionFailed WILL NOT resolve (user already cancelled once).");
            z = false;
        } else if (iUF < this.bpD) {
            gT("GameHelper: onConnectionFailed: WILL resolve because we have below the max# of attempts, " + iUF + " < " + this.bpD);
        } else {
            gT("GameHelper: onConnectionFailed: Will NOT resolve; not user-initiated and max attempts reached: " + iUF + " >= " + this.bpD);
            z = false;
        }
        if (!z) {
            gT("GameHelper: onConnectionFailed: since we won't resolve, failing now.");
            this.bpw = connectionResult;
            this.bpl = false;
            bK(false);
            return;
        }
        gT("GameHelper: onConnectionFailed: resolving problem...");
        UH();
    }

    void UH() {
        if (this.bpn) {
            gT("GameHelper: We're already expecting the result of a previous resolution.");
            return;
        }
        gT("GameHelper: resolveConnectionResult: trying to resolve result: " + this.bpw);
        if (this.bpw.Yy()) {
            gT("GameHelper: Result has resolution. Starting it.");
            try {
                this.bpn = true;
                this.bpw.b(this.mActivity, 9001);
                gT("GameHelper: javax.microedition.midlet.MIDlet.m_isLaunchGoogle = true");
                MIDlet.coo = true;
                return;
            } catch (IntentSender.SendIntentException e) {
                gT("GameHelper: SendIntentException, so connecting again.");
                connect();
                return;
            }
        }
        gT("GameHelper: resolveConnectionResult: result has no resolution. Giving up.");
        a(new f(this.bpw.getErrorCode()));
    }

    public void disconnect() {
        if (this.bps.isConnected()) {
            gT("GameHelper: Disconnecting client.");
            this.bps.disconnect();
        }
    }

    void a(f fVar) {
        this.bpt = false;
        disconnect();
        this.bpx = fVar;
        if (fVar.bpI == 10004) {
            g.as(this.eJ);
        }
        if (fVar.UL() != 30) {
            UI();
        }
        this.bpl = false;
        bK(false);
    }

    void b(f fVar) {
        this.bpt = false;
        disconnect();
        this.bpx = fVar;
        if (fVar.bpI == 10004) {
            g.as(this.eJ);
        }
        this.bpl = false;
        bK(false);
    }

    @Override // com.google.android.gms.common.api.y
    public void onConnectionSuspended(int i) {
        gT("GameHelper: onConnectionSuspended, cause=" + i);
        disconnect();
        this.bpx = null;
        gT("GameHelper: Making extraordinary call to onSignInFailed callback");
        this.bpl = false;
        bK(false);
    }

    public void UI() {
        if (this.bpx != null) {
            int iUL = this.bpx.UL();
            int iUM = this.bpx.UM();
            if (this.bpy) {
                a(this.mActivity, iUM, iUL);
            } else {
                gT("GameHelper: Not showing error dialog because mShowErrorDialogs==false. Error was: " + this.bpx);
            }
        }
    }

    public static void a(Activity activity, int i, int i2) {
        Dialog dialogB;
        if (activity != null) {
            switch (i) {
                case 10002:
                    dialogB = b(activity, g.x(activity, 1));
                    break;
                case 10003:
                    dialogB = b(activity, g.x(activity, 3));
                    break;
                case 10004:
                    dialogB = b(activity, g.x(activity, 2));
                    break;
                default:
                    dialogB = GooglePlayServicesUtil.getErrorDialog(i2, activity, 9002, null);
                    if (dialogB == null) {
                        dialogB = b(activity, g.x(activity, 0) + " " + g.mR(i2));
                    }
                    break;
            }
            dialogB.show();
        }
    }

    static Dialog b(Activity activity, String str) {
        return new AlertDialog.Builder(activity).setMessage(str).setNeutralButton(R.string.ok, (DialogInterface.OnClickListener) null).create();
    }

    void gT(String str) {
        if (this.bma) {
            String str2 = "GameHelper: " + str;
        }
    }

    void fv(String str) {
        String str2 = "!!! GameHelper WARNING: " + str;
    }

    void fu(String str) {
        String str2 = "*** GameHelper ERROR: " + str;
    }
}
