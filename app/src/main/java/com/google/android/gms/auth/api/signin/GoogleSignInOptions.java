package com.google.android.gms.auth.api.signin;

import android.accounts.Account;
import android.os.Parcel;
import android.os.Parcelable;
import android.text.TextUtils;
import com.google.android.gms.auth.api.signin.internal.GoogleSignInOptionsExtensionParcelable;
import com.google.android.gms.common.api.Scope;
import com.google.android.gms.common.api.j;
import com.google.android.gms.common.internal.ReflectedParcelable;
import com.google.android.gms.common.internal.safeparcel.AbstractSafeParcelable;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

/* JADX INFO: loaded from: classes.dex */
public class GoogleSignInOptions extends AbstractSafeParcelable implements j, ReflectedParcelable {
    private final ArrayList<Scope> bGN;
    private boolean bGO;
    private final boolean bGP;
    private final boolean bGQ;
    private ArrayList<GoogleSignInOptionsExtensionParcelable> bGR;
    private Map<Integer, GoogleSignInOptionsExtensionParcelable> bGS;
    private final int versionCode;
    private Account zzs;
    private String zzw;
    private String zzx;
    public static final Scope bGG = new Scope("profile");
    public static final Scope bGH = new Scope("email");
    public static final Scope bGI = new Scope("openid");
    public static final Scope bGJ = new Scope("https://www.googleapis.com/auth/games_lite");
    public static final Scope bGK = new Scope("https://www.googleapis.com/auth/games");
    public static final GoogleSignInOptions bGL = new c().Yn().Yo().Yp();
    public static final GoogleSignInOptions bGM = new c().a(bGJ, new Scope[0]).Yp();
    public static final Parcelable.Creator<GoogleSignInOptions> CREATOR = new d();
    private static Comparator<Scope> bGT = new f();

    GoogleSignInOptions(int i, ArrayList<Scope> arrayList, Account account, boolean z, boolean z2, boolean z3, String str, String str2, ArrayList<GoogleSignInOptionsExtensionParcelable> arrayList2) {
        this(i, arrayList, account, z, z2, z3, str, str2, f(arrayList2));
    }

    private GoogleSignInOptions(int i, ArrayList<Scope> arrayList, Account account, boolean z, boolean z2, boolean z3, String str, String str2, Map<Integer, GoogleSignInOptionsExtensionParcelable> map) {
        this.versionCode = i;
        this.bGN = arrayList;
        this.zzs = account;
        this.bGO = z;
        this.bGP = z2;
        this.bGQ = z3;
        this.zzw = str;
        this.zzx = str2;
        this.bGR = new ArrayList<>(map.values());
        this.bGS = map;
    }

    /* synthetic */ GoogleSignInOptions(int i, ArrayList arrayList, Account account, boolean z, boolean z2, boolean z3, String str, String str2, Map map, f fVar) {
        this(3, (ArrayList<Scope>) arrayList, account, z, z2, z3, str, str2, (Map<Integer, GoogleSignInOptionsExtensionParcelable>) map);
    }

    private final JSONObject Yg() {
        JSONObject jSONObject = new JSONObject();
        try {
            JSONArray jSONArray = new JSONArray();
            Collections.sort(this.bGN, bGT);
            ArrayList<Scope> arrayList = this.bGN;
            int size = arrayList.size();
            int i = 0;
            while (i < size) {
                Scope scope = arrayList.get(i);
                i++;
                jSONArray.put(scope.YQ());
            }
            jSONObject.put("scopes", jSONArray);
            if (this.zzs != null) {
                jSONObject.put("accountName", this.zzs.name);
            }
            jSONObject.put("idTokenRequested", this.bGO);
            jSONObject.put("forceCodeForRefreshToken", this.bGQ);
            jSONObject.put("serverAuthRequested", this.bGP);
            if (!TextUtils.isEmpty(this.zzw)) {
                jSONObject.put("serverClientId", this.zzw);
            }
            if (!TextUtils.isEmpty(this.zzx)) {
                jSONObject.put("hostedDomain", this.zzx);
            }
            return jSONObject;
        } catch (JSONException e) {
            throw new RuntimeException(e);
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static Map<Integer, GoogleSignInOptionsExtensionParcelable> f(List<GoogleSignInOptionsExtensionParcelable> list) {
        HashMap map = new HashMap();
        if (list == null) {
            return map;
        }
        for (GoogleSignInOptionsExtensionParcelable googleSignInOptionsExtensionParcelable : list) {
            map.put(Integer.valueOf(googleSignInOptionsExtensionParcelable.getType()), googleSignInOptionsExtensionParcelable);
        }
        return map;
    }

    public static GoogleSignInOptions hO(String str) throws JSONException {
        if (TextUtils.isEmpty(str)) {
            return null;
        }
        JSONObject jSONObject = new JSONObject(str);
        HashSet hashSet = new HashSet();
        JSONArray jSONArray = jSONObject.getJSONArray("scopes");
        int length = jSONArray.length();
        for (int i = 0; i < length; i++) {
            hashSet.add(new Scope(jSONArray.getString(i)));
        }
        String strOptString = jSONObject.optString("accountName", null);
        return new GoogleSignInOptions(3, (ArrayList<Scope>) new ArrayList(hashSet), !TextUtils.isEmpty(strOptString) ? new Account(strOptString, "com.google") : null, jSONObject.getBoolean("idTokenRequested"), jSONObject.getBoolean("serverAuthRequested"), jSONObject.getBoolean("forceCodeForRefreshToken"), jSONObject.optString("serverClientId", null), jSONObject.optString("hostedDomain", null), new HashMap());
    }

    public boolean XS() {
        return this.bGO;
    }

    public String XT() {
        return this.zzw;
    }

    public ArrayList<Scope> Yh() {
        return new ArrayList<>(this.bGN);
    }

    public boolean Yi() {
        return this.bGP;
    }

    public boolean Yj() {
        return this.bGQ;
    }

    public String Yk() {
        return this.zzx;
    }

    public ArrayList<GoogleSignInOptionsExtensionParcelable> Yl() {
        return this.bGR;
    }

    public String Ym() {
        return Yg().toString();
    }

    public boolean equals(Object obj) {
        if (obj == null) {
            return false;
        }
        try {
            GoogleSignInOptions googleSignInOptions = (GoogleSignInOptions) obj;
            if (this.bGR.size() > 0 || googleSignInOptions.bGR.size() > 0 || this.bGN.size() != googleSignInOptions.Yh().size() || !this.bGN.containsAll(googleSignInOptions.Yh())) {
                return false;
            }
            if (this.zzs == null) {
                if (googleSignInOptions.getAccount() != null) {
                    return false;
                }
            } else if (!this.zzs.equals(googleSignInOptions.getAccount())) {
                return false;
            }
            if (TextUtils.isEmpty(this.zzw)) {
                if (!TextUtils.isEmpty(googleSignInOptions.XT())) {
                    return false;
                }
            } else if (!this.zzw.equals(googleSignInOptions.XT())) {
                return false;
            }
            return this.bGQ == googleSignInOptions.Yj() && this.bGO == googleSignInOptions.XS() && this.bGP == googleSignInOptions.Yi();
        } catch (ClassCastException e) {
            return false;
        }
    }

    public Account getAccount() {
        return this.zzs;
    }

    public int hashCode() {
        ArrayList arrayList = new ArrayList();
        ArrayList<Scope> arrayList2 = this.bGN;
        int size = arrayList2.size();
        int i = 0;
        while (i < size) {
            Scope scope = arrayList2.get(i);
            i++;
            arrayList.add(scope.YQ());
        }
        Collections.sort(arrayList);
        return new com.google.android.gms.auth.api.signin.internal.b().u(arrayList).u(this.zzs).u(this.zzw).bX(this.bGQ).bX(this.bGO).bX(this.bGP).Yr();
    }

    @Override // android.os.Parcelable
    public void writeToParcel(Parcel parcel, int i) {
        int iX = com.google.android.gms.common.internal.safeparcel.c.x(parcel);
        com.google.android.gms.common.internal.safeparcel.c.c(parcel, 1, this.versionCode);
        com.google.android.gms.common.internal.safeparcel.c.c(parcel, 2, Yh(), false);
        com.google.android.gms.common.internal.safeparcel.c.a(parcel, 3, (Parcelable) getAccount(), i, false);
        com.google.android.gms.common.internal.safeparcel.c.a(parcel, 4, XS());
        com.google.android.gms.common.internal.safeparcel.c.a(parcel, 5, Yi());
        com.google.android.gms.common.internal.safeparcel.c.a(parcel, 6, Yj());
        com.google.android.gms.common.internal.safeparcel.c.a(parcel, 7, XT(), false);
        com.google.android.gms.common.internal.safeparcel.c.a(parcel, 8, Yk(), false);
        com.google.android.gms.common.internal.safeparcel.c.c(parcel, 9, Yl(), false);
        com.google.android.gms.common.internal.safeparcel.c.G(parcel, iX);
    }
}
