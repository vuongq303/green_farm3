package com.gameloft.android.ANMP.GloftGF2F;

import com.facebook.appevents.codeless.internal.Constants;
import com.gameloft.glads.AndroidWebView;
import java.nio.charset.Charset;
import java.util.HashSet;
import java.util.Set;
import java.util.Vector;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

/* JADX INFO: renamed from: com.gameloft.android.ANMP.GloftGF2F.r, reason: case insensitive filesystem */
/* JADX INFO: compiled from: HestiaConfigManager.java */
/* JADX INFO: loaded from: classes.dex */
public class C0008r {
    private static bx CC = null;
    private static Vector<Integer> CD = null;
    private static String CE = "";
    private static boolean CF = false;

    public static boolean F(String str) {
        if (CF) {
            o.u("[HestiaConfigManager][ResyncJsonConfig] Update is in progress!");
            return false;
        }
        CF = true;
        if (CC == null) {
            CC = bx.ND();
        }
        if (CE.compareTo(str) != 0) {
            CE = str;
            o.u("[HestiaConfigManager][ResyncJsonConfig] JsonConfig received: " + str);
            gA();
            return true;
        }
        o.u("[HestiaConfigManager][ResyncJsonConfig] No new update!");
        CF = false;
        return false;
    }

    private static void gA() {
        int i;
        int i2;
        try {
            JSONObject jSONObject = new JSONObject(CE);
            a(null);
            aR(bx.By);
            aS(n.Bu);
            if (jSONObject.has("disabled_events")) {
                JSONArray jSONArray = jSONObject.getJSONArray("disabled_events");
                int length = jSONArray.length();
                HashSet hashSet = new HashSet();
                for (int i3 = 0; i3 < length; i3++) {
                    hashSet.add(Integer.valueOf(jSONArray.getInt(i3)));
                }
                if (!hashSet.isEmpty()) {
                    a(hashSet);
                }
            } else {
                o.u("[HestiaConfigManager][ParseJsonConfig] the Hestia config does not contain disabled_events field!");
            }
            if (jSONObject.has("network_send_interval")) {
                int i4 = jSONObject.getInt("network_send_interval") * Constants.APP_INDEXING_SCHEDULE_INTERVAL_MS;
                if (i4 < bx.By) {
                    i4 = bx.By;
                }
                if (i4 != CC.bdk) {
                    aR(i4);
                }
            }
            if (jSONObject.has("network_max_events_per_package") && (i2 = jSONObject.getInt("network_max_events_per_package")) != n.Bu) {
                aS(i2);
            }
            if (jSONObject.has("max_events_of_one_type") && (i = jSONObject.getInt("max_events_of_one_type")) != bx.BE) {
                aT(i);
            }
            gB();
        } catch (JSONException e) {
            o.t("HestiaConfigManager.ParseJsonConfig: cannot parse json config!!!\n" + CE);
            o.u(e.getMessage());
        } finally {
            CF = false;
        }
    }

    private static void a(Set<Integer> set) {
        if (set == null || set.isEmpty()) {
            if (CD != null) {
                CD = null;
                o.u("[HestiaConfigManager][UpdateDeactivatedEventsList] List is empty for now!");
                return;
            }
            return;
        }
        if (CD == null) {
            CD = new Vector<>();
        }
        CD.clear();
        CD.addAll(set);
        o.u("[HestiaConfigManager][UpdateDeactivatedEventsList] Deactivated events list updated: " + CD.toString());
    }

    private static void aR(int i) {
        if (CC.bdk != i) {
            CC.bdk = i;
            CC.bdq = i;
            o.u("[HestiaConfigManager][UpdateNetworkSendInterval] Network send interval updated: " + i);
        }
    }

    private static void aS(int i) {
        if (n.Bu != i) {
            n.Bu = i;
            o.u("[HestiaConfigManager][UpdateNetworkPackagesLimit] Network packages limit updated: " + i);
        }
    }

    private static void aT(int i) {
        if (bx.BE != i) {
            bx.BE = i;
            o.u("[HestiaConfigManager][UpdateMaxEventsOfOneType] Max events of one type updated: " + i);
        }
    }

    public static boolean aU(int i) {
        if (CD == null || CD.isEmpty()) {
            return false;
        }
        return CD.contains(Integer.valueOf(i));
    }

    private static void gB() {
        byte[] bytes = CE.getBytes(Charset.forName(AndroidWebView.UTF_8));
        if (CC == null) {
            CC = bx.ND();
        }
        if (!o.a("GLOT_TRACKING", CC.dU(new String(bytes, Charset.forName(AndroidWebView.UTF_8))).getBytes(Charset.forName(AndroidWebView.UTF_8)))) {
            o.t("HestiaConfigManager.WriteConfigToFile: cannot save 'GLOT_TRACKING' file!!!");
        }
    }

    public static void gC() {
        byte[] bArrX = o.x("GLOT_TRACKING");
        if (bArrX != null) {
            String strC = o.c(bArrX, 0);
            if (CC == null) {
                CC = bx.ND();
            }
            bx bxVar = CC;
            if (strC.startsWith("JavaGLOTv3Encrypted")) {
                strC = CC.dV(strC);
            }
            F(strC);
            return;
        }
        o.t("HestiaConfigManager.GetConfigFromFile: cannot read config GLOT_TRACKING' file!!!");
    }
}
