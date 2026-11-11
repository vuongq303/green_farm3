package com.gameloft.android2d.socialnetwork;

import android.view.ViewGroup;
import com.gameloft.android.ANMP.GloftGF2F.R;
import com.gameloft.android.wrapper.au;
import com.google.android.gms.games.Player;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/* JADX INFO: compiled from: SocialNetwork_Google.java */
/* JADX INFO: loaded from: classes.dex */
public class aj implements e {
    public static aj brv = null;
    private static a bpj = null;
    private static int bpk = 3;
    private static boolean bma = true;
    private static Player brw = null;
    private static ViewGroup brx = null;
    private static com.google.android.gms.plus.g bry = null;
    private static boolean brz = false;
    private static String brA = "";
    private static int brB = -1;
    private static int brC = -1;
    private static int brD = -1;
    private static int brE = -1;
    private static boolean brF = false;
    private static com.google.android.gms.plus.h brG = new ak();
    private static com.google.android.gms.games.a.a brH = null;
    private static com.google.android.gms.games.a.f brI = null;
    private static List<Object> brJ = new ArrayList();
    private static List<Object> brK = new ArrayList();
    private static boolean brL = false;
    private static boolean brM = false;
    private static HashMap<String, javax.microedition.lcdui.ao> brN = new HashMap<>();
    private static HashMap<String, String> brO = new HashMap<>();
    private static List<String> brP = new ArrayList();
    private static List<String> brQ = new ArrayList();
    private static int brR = 50;
    private static int brS = 50;
    private static String brT = null;
    private static boolean brU = false;

    @Override // com.gameloft.android2d.socialnetwork.e
    public void UK() {
        au.a("GPlus_logged_in", new Boolean(true), "GooglePlus");
        if (com.google.android.gms.games.c.bSc.getCurrentPlayer(Uu().Uw()) != null) {
        }
        Vl();
        Vn();
        if (Vj() != null) {
            r.a(52014, 115493, Vj());
        } else {
            r.a(52014, 115493, "N/A");
        }
    }

    @Override // com.gameloft.android2d.socialnetwork.e
    public void UJ() {
        au.a("GPlus_logged_in", new Boolean(false), "GooglePlus");
        au.a("GPlus_logged_in_account", "", "GooglePlus");
        if (!Uu().Uy()) {
            if (Vj() != null) {
                r.a(52015, 115493, Vj());
            } else {
                r.a(52015, 115493, "N/A");
            }
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static a Uu() {
        if (bpj == null) {
            bpj = new a(au.getActivity());
            bpj.bJ(bma);
        }
        return bpj;
    }

    public static void ar(int i, int i2) {
        brR = i;
        brS = i2;
    }

    public static void nT() {
        if (!Vh() && !Vi()) {
            new Thread(new am()).start();
        }
    }

    public static void nU() {
        au.a("GPlus_logged_in", new Boolean(false), "GooglePlus");
        au.a("GPlus_logged_in_account", "", "GooglePlus");
        if (Vh()) {
            Uu().Uz();
        }
        Vp();
        Vk();
        if (Vv()) {
            Vu();
        }
        brz = false;
        Vo();
        r.a(317968, 115493, "N/A");
    }

    public static boolean Vh() {
        return Uu().Ux();
    }

    public static boolean Vi() {
        return Uu().isConnecting();
    }

    public static String Vj() {
        Player playerVm;
        if (!Vh() || (playerVm = Vm()) == null) {
            return null;
        }
        return playerVm.acq();
    }

    public static String ju() {
        return brT;
    }

    private static void Vk() {
        brT = null;
    }

    private static String Vl() {
        Uu();
        return a.bmK;
    }

    private static Player Vm() {
        if (Vh()) {
            if (brw == null) {
                brw = com.google.android.gms.games.c.bSc.getCurrentPlayer(Uu().Uw());
            }
        } else {
            brw = null;
        }
        return brw;
    }

    public static void Vn() {
        if (Vh()) {
            com.google.android.gms.games.c.a(Uu().Uw(), au.getActivity().getString(R.string.game_api_server_client_id)).setResultCallback(new ao());
        }
    }

    private static void Vo() {
        brw = null;
    }

    public static void Vp() {
        Vq();
        Vs();
        Vt();
        Vw();
        Vx();
    }

    public static void Vq() {
        brK.clear();
    }

    public static boolean Vr() {
        return brH != null;
    }

    public static void Vs() {
        if (Vr()) {
            brH = null;
        }
    }

    public static void Vt() {
        if (brI != null) {
            if (!brI.isClosed()) {
                brI.close();
            }
            brI = null;
        }
        brJ.clear();
    }

    public static void Vu() {
        au.getActivity().runOnUiThread(new al());
    }

    public static boolean Vv() {
        return brF;
    }

    public static void Vw() {
        brM = true;
        brN.clear();
    }

    public static void Vx() {
        brO.clear();
    }
}
