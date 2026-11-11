package com.gameloft.a.a;

import android.os.SystemClock;
import java.util.Calendar;
import java.util.TimeZone;
import org.json.JSONException;
import org.json.JSONObject;

/* JADX INFO: compiled from: OfflineStore.java */
/* JADX INFO: loaded from: classes.dex */
public class t {
    private static t bwz = null;
    private long bwD;
    private boolean Jn = false;
    private w bwA = new w();
    private u bwB = new u();
    private long bwC = 0;
    private long bwE = 0;

    public static t Wc() {
        if (bwz == null) {
            bwz = new t();
        }
        return bwz;
    }

    public int aw(String str) {
        if (this.Jn) {
            return -2147483645;
        }
        this.bwA = new w();
        this.bwB = new u();
        try {
            int iG = G(new JSONObject(str).getJSONObject("offline_store"));
            if (iG == 0) {
                this.Jn = true;
                return iG;
            }
            return iG;
        } catch (JSONException e) {
            return -2147483622;
        }
    }

    public int cs(String str) {
        try {
            return G(new JSONObject(str).getJSONObject("offline_store")) != 0 ? -2147483622 : 0;
        } catch (Exception e) {
            return -2147483622;
        }
    }

    public int G(JSONObject jSONObject) {
        String strNy;
        if (jSONObject == null) {
            return -2147483622;
        }
        int iG = this.bwA.g(jSONObject);
        if (iG == 0) {
            this.bwB.hM();
            this.bwB.g(jSONObject);
            if (iG == 0 && (strNy = ny()) != null && !strNy.equals("")) {
                this.bwC = bU(strNy);
                this.bwE = Wd() - We();
                this.bwD = SystemClock.elapsedRealtime();
                return iG;
            }
            return iG;
        }
        return iG;
    }

    public String ny() {
        return this.bwB.nE();
    }

    private long bU(String str) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTimeZone(TimeZone.getTimeZone("GMT"));
        int iIndexOf = str.indexOf("-");
        if (iIndexOf <= 0) {
            return 0L;
        }
        int i = Integer.parseInt(str.substring(0, iIndexOf));
        String strSubstring = str.substring(iIndexOf + 1);
        int iIndexOf2 = strSubstring.indexOf("-");
        if (iIndexOf2 <= 0) {
            return 0L;
        }
        int i2 = Integer.parseInt(strSubstring.substring(0, iIndexOf2));
        String strSubstring2 = strSubstring.substring(iIndexOf2 + 1);
        int iIndexOf3 = strSubstring2.indexOf(" ");
        if (iIndexOf3 <= 0) {
            return 0L;
        }
        int i3 = Integer.parseInt(strSubstring2.substring(0, iIndexOf3));
        String strSubstring3 = strSubstring2.substring(iIndexOf3 + 1);
        int iIndexOf4 = strSubstring3.indexOf(":");
        if (iIndexOf4 <= 0) {
            return 0L;
        }
        int i4 = Integer.parseInt(strSubstring3.substring(0, iIndexOf4));
        String strSubstring4 = strSubstring3.substring(iIndexOf4 + 1);
        int iIndexOf5 = strSubstring4.indexOf(":");
        if (iIndexOf5 <= 0) {
            return 0L;
        }
        int i5 = Integer.parseInt(strSubstring4.substring(0, iIndexOf5));
        String strSubstring5 = strSubstring4.substring(iIndexOf5 + 1);
        int iIndexOf6 = strSubstring5.indexOf("Z");
        if (iIndexOf6 <= 0) {
            return 0L;
        }
        int i6 = Integer.parseInt(strSubstring5.substring(0, iIndexOf6));
        calendar.set(1, i);
        calendar.set(2, i2 - 1);
        calendar.set(5, i3);
        calendar.set(11, i4);
        calendar.set(12, i5);
        calendar.set(13, i6);
        return calendar.getTime().getTime();
    }

    public long Wd() {
        return this.bwC;
    }

    private long We() {
        long jIR = com.gameloft.a.b.a.iR();
        if (jIR <= 0) {
            return System.currentTimeMillis();
        }
        return jIR;
    }
}
