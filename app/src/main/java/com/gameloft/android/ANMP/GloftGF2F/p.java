package com.gameloft.android.ANMP.GloftGF2F;

import com.facebook.share.internal.ShareConstants;
import java.util.Enumeration;
import java.util.Hashtable;
import java.util.Vector;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

/* JADX INFO: compiled from: GlotWriter.java */
/* JADX INFO: loaded from: classes.dex */
public class p extends k implements Runnable {
    private bx AG;
    private d Ce;
    private boolean Cs;
    private Vector Cr = new Vector();
    private Thread BG = null;
    public boolean Ct = true;
    public boolean Cu = true;
    public boolean Cv = false;

    p(bx bxVar) {
        this.AG = bxVar;
        this.Ce = d.a(this.AG);
        this.Ce.aA(bx.Nv());
        this.Cs = false;
    }

    public void gk() {
        if (this.BG != null) {
            try {
                this.BG.join();
            } catch (Exception e) {
            }
        }
        this.BG = new Thread(this);
        this.BG.start();
    }

    @Override // java.lang.Runnable
    public void run() {
        gv();
        gw();
        while (!this.Cs) {
            try {
                synchronized (this) {
                    try {
                        wait(50L);
                    } catch (Throwable th) {
                        throw th;
                    }
                }
            } catch (Exception e) {
            }
            if (!this.Cs || this.Cr.size() != 0) {
                while (this.Cr.size() > 0) {
                    bw bwVar = (bw) this.Cr.elementAt(0);
                    if (bwVar.type == 3) {
                        this.Cr.removeElement(bwVar);
                        if (!this.Cv) {
                            this.Cv = true;
                            gu();
                        }
                        o.u("m_manager.HasConnectivity() : " + this.AG.NJ());
                        if (this.AG.NJ()) {
                            if (this.Cu && !this.Ce.aC(1)) {
                                this.Ce.aG(1);
                            }
                            if (this.Cu && !this.Ce.aC(0)) {
                                this.Ce.aG(0);
                            }
                            if (this.Cu && !this.Ce.aC(2)) {
                                this.Ce.aG(2);
                            }
                            this.AG.bej = true;
                        }
                    } else {
                        b(bwVar, this.AG.NJ(), false);
                    }
                }
            } else {
                return;
            }
        }
    }

    public boolean a(bw bwVar, boolean z, boolean z2) {
        if (bwVar == null) {
            return false;
        }
        if (C0008r.aU(bwVar.type)) {
            o.u("(!) Deactivated event added! Event type: " + bwVar.type);
            return false;
        }
        if (z2) {
            b(bwVar, this.AG.NJ(), z2);
            return true;
        }
        this.Cr.addElement(bwVar);
        return true;
    }

    private void b(bw bwVar, boolean z, boolean z2) {
        if (a(bwVar) && !z2) {
            this.Cr.removeElement(bwVar);
        }
    }

    private boolean a(bw bwVar) {
        boolean zA;
        synchronized (this) {
            try {
                bd bdVarAB = this.Ce.aB(bwVar.type);
                if (bdVarAB != null) {
                    if (bdVarAB.b(bwVar)) {
                        o.u("Saving event " + bwVar.id + " to old message");
                        zA = this.Ce.a(bdVarAB, bwVar.type);
                    } else {
                        o.u("Can not saving event " + bwVar.id + " to old message");
                        bd bdVar = new bd(bx.Nv());
                        bdVar.b(bwVar);
                        o.u("Saving event " + bwVar.id + " to new message");
                        zA = this.Ce.a(bdVar, bwVar.type);
                    }
                } else {
                    bd bdVar2 = new bd(bx.Nv());
                    bdVar2.b(bwVar);
                    o.u("Saving event " + bwVar.id + " to new message");
                    zA = this.Ce.a(bdVar2, bwVar.type);
                }
            } catch (Exception e) {
                o.u("ERROR when saving message");
                o.d(e);
                zA = false;
            }
        }
        return zA;
    }

    public void gt() {
        this.Cs = true;
        if (this.BG != null) {
            try {
                this.BG.join();
            } catch (Exception e) {
            }
        }
        this.BG = null;
    }

    private void gu() {
        aP(1);
        aP(0);
        aP(2);
    }

    private void aP(int i) {
        synchronized (this) {
            if (this.Ce.aC(i)) {
                try {
                    bd bdVarAH = this.Ce.aH(i);
                    bd bdVarAB = this.Ce.aB(i);
                    JSONArray jSONArray = bdVarAH.EJ.getJSONArray("events");
                    JSONArray jSONArray2 = bdVarAB.EJ.getJSONArray("events");
                    for (int length = jSONArray.length() - 1; length >= 0; length--) {
                        int i2 = ((JSONObject) jSONArray.get(length)).getInt("type");
                        jSONArray2.put(jSONArray.get(length));
                        o.b("[Recover]: Pushing back event " + i2 + ":" + ((JSONObject) jSONArray.get(length)).toString(), 1);
                    }
                    this.Ce.a(bdVarAB, i);
                } catch (Exception e) {
                    o.u("ERROR when pushback event: " + i);
                }
                this.Ce.aE(i);
            }
        }
    }

    private void gv() {
        javax.microedition.b.b bVarB = o.b("GLOT_synchronizerCounter", false);
        if (bVarB == null) {
            o.u("No GLOTv2 counter record found!");
            return;
        }
        try {
            try {
                byte[] bArrPi = bVarB.pi(1);
                int iA = o.a(bArrPi, 0);
                int iA2 = o.a(bArrPi, 4);
                o.u("Counter from GLOTv2 found: " + iA + " : " + iA2);
                bVarB.akW();
                o.A("GLOT_synchronizerCounter");
                while (iA <= iA2) {
                    aQ(iA);
                    iA++;
                }
            } catch (Exception e) {
                o.u(e.getMessage());
                o.A("GLOT_synchronizerCounter");
            }
        } catch (Throwable th) {
            o.A("GLOT_synchronizerCounter");
            throw th;
        }
    }

    private void aQ(int i) {
        String str = "GLOT_" + i;
        javax.microedition.b.b bVarB = o.b(str, false);
        if (bVarB == null) {
            o.u("No GLOTv2 message record " + i + " found!");
            return;
        }
        try {
            if (bVarB.akX() > 0) {
                String strC = o.c(bVarB.pi(1), 4);
                JSONArray jSONArray = new JSONObject(strC).getJSONArray("events");
                for (int i2 = 0; i2 < jSONArray.length(); i2++) {
                    JSONObject jSONObject = jSONArray.getJSONObject(i2);
                    bw bwVar = new bw(jSONObject.getInt("type"), 0);
                    bwVar.bdd = jSONObject;
                    a(bwVar);
                }
                o.u("Push back from GLOTv2 message record " + i + " : " + strC);
            }
            bVarB.akW();
        } catch (Exception e) {
            o.u(e.getMessage());
        } finally {
            o.A(str);
        }
    }

    private void gw() {
        javax.microedition.b.b bVarB = o.b("SAGFdfajkshSF2345sMHL", false);
        if (bVarB == null) {
            o.u("No GLOTv2 batch events record found!");
            return;
        }
        Hashtable hashtable = new Hashtable();
        try {
            byte[] bArrPi = bVarB.pi(1);
            int iA = o.a(bArrPi, 0);
            int i = 4;
            for (int i2 = 0; i2 < iA; i2++) {
                int iA2 = o.a(bArrPi, i);
                int i3 = i + 4;
                String strA = o.a(bArrPi, i3, iA2);
                int i4 = i3 + iA2;
                int iA3 = o.a(bArrPi, i4);
                int i5 = i4 + 4;
                String strA2 = o.a(bArrPi, i5, iA3);
                i = i5 + iA3;
                o.b("LOAD SAVED GLOTv2 BATCH EV  " + strA + "/" + strA2, 1);
                hashtable.put(strA, new JSONObject(strA2));
            }
            bVarB.akW();
            if (hashtable.size() > 0) {
                c(hashtable);
            }
        } catch (Exception e) {
            o.u("ERROR when load batch " + e.getMessage());
        } finally {
            o.A("SAGFdfajkshSF2345sMHL");
        }
    }

    private void c(Hashtable hashtable) {
        Enumeration enumerationKeys = hashtable.keys();
        while (enumerationKeys.hasMoreElements()) {
            try {
                Object objNextElement = enumerationKeys.nextElement();
                String string = objNextElement.toString();
                JSONObject jSONObject = new JSONObject(string);
                bw bwVar = new bw(jSONObject.getInt("type"), 2);
                JSONObject jSONObject2 = jSONObject.getJSONObject(ShareConstants.WEB_DIALOG_PARAM_DATA);
                JSONObject jSONObject3 = new JSONObject(hashtable.get(string).toString());
                JSONArray jSONArrayNames = jSONObject3.names();
                int length = jSONArrayNames.length();
                for (int i = 0; i < length; i++) {
                    String string2 = jSONArrayNames.getString(i);
                    if (string2.compareTo("automaticevent") != 0) {
                        jSONObject2.put(string2, jSONObject3.getInt(string2));
                    }
                }
                if (length > 2) {
                    jSONObject2.put("count_sb", jSONObject2.getInt("count"));
                    jSONObject2.put("count", 1);
                } else {
                    jSONObject2.put("count_sb", 1);
                }
                bwVar.bdd = jSONObject;
                this.AG.c(bwVar);
                o.u("FLUSH Batched event to message" + jSONObject.toString());
                a(bwVar);
                hashtable.remove(objNextElement);
            } catch (JSONException e) {
                o.u(e.getMessage());
            }
        }
    }
}
