package com.google.android.gms.auth.api.signin;

import android.accounts.Account;
import android.net.Uri;
import android.os.Parcel;
import android.os.Parcelable;
import android.text.TextUtils;
import com.facebook.share.internal.ShareConstants;
import com.google.android.gms.common.api.Scope;
import com.google.android.gms.common.d.h;
import com.google.android.gms.common.internal.ReflectedParcelable;
import com.google.android.gms.common.internal.bf;
import com.google.android.gms.common.internal.safeparcel.AbstractSafeParcelable;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

/* JADX INFO: loaded from: classes.dex */
public class GoogleSignInAccount extends AbstractSafeParcelable implements ReflectedParcelable {
    public static final Parcelable.Creator<GoogleSignInAccount> CREATOR = new a();
    public static com.google.android.gms.common.d.e bGx = h.abr();
    private String bER;
    private String bGA;
    private Uri bGB;
    private String bGC;
    private String bGD;
    private String bGE;
    private Set<Scope> bGF = new HashSet();
    private String bGy;
    private String bGz;
    private final int versionCode;
    private String zze;
    private long zzk;
    private List<Scope> zzm;

    GoogleSignInAccount(int i, String str, String str2, String str3, String str4, Uri uri, String str5, long j, String str6, List<Scope> list, String str7, String str8) {
        this.versionCode = i;
        this.zze = str;
        this.bGy = str2;
        this.bGz = str3;
        this.bGA = str4;
        this.bGB = uri;
        this.bER = str5;
        this.zzk = j;
        this.bGC = str6;
        this.zzm = list;
        this.bGD = str7;
        this.bGE = str8;
    }

    private final JSONObject Yg() {
        JSONObject jSONObject = new JSONObject();
        try {
            if (getId() != null) {
                jSONObject.put(ShareConstants.WEB_DIALOG_PARAM_ID, getId());
            }
            if (XX() != null) {
                jSONObject.put("tokenId", XX());
            }
            if (XZ() != null) {
                jSONObject.put("email", XZ());
            }
            if (getDisplayName() != null) {
                jSONObject.put("displayName", getDisplayName());
            }
            if (getGivenName() != null) {
                jSONObject.put("givenName", getGivenName());
            }
            if (getFamilyName() != null) {
                jSONObject.put("familyName", getFamilyName());
            }
            if (Ya() != null) {
                jSONObject.put("photoUrl", Ya().toString());
            }
            if (Yb() != null) {
                jSONObject.put("serverAuthCode", Yb());
            }
            jSONObject.put("expirationTime", this.zzk);
            jSONObject.put("obfuscatedIdentifier", Yd());
            JSONArray jSONArray = new JSONArray();
            Scope[] scopeArr = (Scope[]) this.zzm.toArray(new Scope[this.zzm.size()]);
            Arrays.sort(scopeArr, e.bHt);
            for (Scope scope : scopeArr) {
                jSONArray.put(scope.YQ());
            }
            jSONObject.put("grantedScopes", jSONArray);
            return jSONObject;
        } catch (JSONException e) {
            throw new RuntimeException(e);
        }
    }

    public static GoogleSignInAccount a(String str, String str2, String str3, String str4, String str5, String str6, Uri uri, Long l, String str7, Set<Scope> set) {
        if (l == null) {
            l = Long.valueOf(bGx.currentTimeMillis() / 1000);
        }
        return new GoogleSignInAccount(3, str, str2, str3, str4, uri, null, l.longValue(), bf.id(str7), new ArrayList((Collection) bf.checkNotNull(set)), str5, str6);
    }

    public static GoogleSignInAccount hM(String str) throws JSONException {
        if (TextUtils.isEmpty(str)) {
            return null;
        }
        JSONObject jSONObject = new JSONObject(str);
        String strOptString = jSONObject.optString("photoUrl", null);
        Uri uri = !TextUtils.isEmpty(strOptString) ? Uri.parse(strOptString) : null;
        long j = Long.parseLong(jSONObject.getString("expirationTime"));
        HashSet hashSet = new HashSet();
        JSONArray jSONArray = jSONObject.getJSONArray("grantedScopes");
        int length = jSONArray.length();
        for (int i = 0; i < length; i++) {
            hashSet.add(new Scope(jSONArray.getString(i)));
        }
        return a(jSONObject.optString(ShareConstants.WEB_DIALOG_PARAM_ID), jSONObject.optString("tokenId", null), jSONObject.optString("email", null), jSONObject.optString("displayName", null), jSONObject.optString("givenName", null), jSONObject.optString("familyName", null), uri, Long.valueOf(j), jSONObject.getString("obfuscatedIdentifier"), hashSet).hN(jSONObject.optString("serverAuthCode", null));
    }

    public String XX() {
        return this.bGy;
    }

    public String XZ() {
        return this.bGz;
    }

    public Uri Ya() {
        return this.bGB;
    }

    public String Yb() {
        return this.bER;
    }

    public long Yc() {
        return this.zzk;
    }

    public String Yd() {
        return this.bGC;
    }

    public Set<Scope> Ye() {
        HashSet hashSet = new HashSet(this.zzm);
        hashSet.addAll(this.bGF);
        return hashSet;
    }

    public String Yf() {
        JSONObject jSONObjectYg = Yg();
        jSONObjectYg.remove("serverAuthCode");
        return jSONObjectYg.toString();
    }

    public boolean equals(Object obj) {
        if (obj == this) {
            return true;
        }
        if (!(obj instanceof GoogleSignInAccount)) {
            return false;
        }
        GoogleSignInAccount googleSignInAccount = (GoogleSignInAccount) obj;
        return googleSignInAccount.Yd().equals(Yd()) && googleSignInAccount.Ye().equals(Ye());
    }

    public Account getAccount() {
        if (this.bGz == null) {
            return null;
        }
        return new Account(this.bGz, "com.google");
    }

    public String getDisplayName() {
        return this.bGA;
    }

    public String getFamilyName() {
        return this.bGE;
    }

    public String getGivenName() {
        return this.bGD;
    }

    public String getId() {
        return this.zze;
    }

    public GoogleSignInAccount hN(String str) {
        this.bER = str;
        return this;
    }

    public int hashCode() {
        return ((Yd().hashCode() + 527) * 31) + Ye().hashCode();
    }

    @Override // android.os.Parcelable
    public void writeToParcel(Parcel parcel, int i) {
        int iX = com.google.android.gms.common.internal.safeparcel.c.x(parcel);
        com.google.android.gms.common.internal.safeparcel.c.c(parcel, 1, this.versionCode);
        com.google.android.gms.common.internal.safeparcel.c.a(parcel, 2, getId(), false);
        com.google.android.gms.common.internal.safeparcel.c.a(parcel, 3, XX(), false);
        com.google.android.gms.common.internal.safeparcel.c.a(parcel, 4, XZ(), false);
        com.google.android.gms.common.internal.safeparcel.c.a(parcel, 5, getDisplayName(), false);
        com.google.android.gms.common.internal.safeparcel.c.a(parcel, 6, (Parcelable) Ya(), i, false);
        com.google.android.gms.common.internal.safeparcel.c.a(parcel, 7, Yb(), false);
        com.google.android.gms.common.internal.safeparcel.c.a(parcel, 8, Yc());
        com.google.android.gms.common.internal.safeparcel.c.a(parcel, 9, Yd(), false);
        com.google.android.gms.common.internal.safeparcel.c.c(parcel, 10, this.zzm, false);
        com.google.android.gms.common.internal.safeparcel.c.a(parcel, 11, getGivenName(), false);
        com.google.android.gms.common.internal.safeparcel.c.a(parcel, 12, getFamilyName(), false);
        com.google.android.gms.common.internal.safeparcel.c.G(parcel, iX);
    }
}
