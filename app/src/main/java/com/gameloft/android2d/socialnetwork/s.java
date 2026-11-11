package com.gameloft.android2d.socialnetwork;

import android.app.Activity;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.content.pm.Signature;
import android.net.Uri;
import android.os.Bundle;
import android.text.TextUtils;
import com.facebook.AccessToken;
import com.facebook.AccessTokenTracker;
import com.facebook.CallbackManager;
import com.facebook.FacebookCallback;
import com.facebook.FacebookSdk;
import com.facebook.GraphRequest;
import com.facebook.HttpMethod;
import com.facebook.Profile;
import com.facebook.ProfileTracker;
import com.facebook.login.LoginManager;
import com.facebook.login.LoginResult;
import com.facebook.share.ShareApi;
import com.facebook.share.internal.ShareConstants;
import com.facebook.share.model.GameRequestContent;
import com.facebook.share.model.ShareContent;
import com.facebook.share.model.ShareHashtag;
import com.facebook.share.model.ShareLinkContent;
import com.facebook.share.widget.GameRequestDialog;
import com.facebook.share.widget.ShareDialog;
import com.gameloft.android.ANMP.GloftGF2F.R;
import com.gameloft.android.wrapper.ar;
import com.gameloft.android.wrapper.au;
import com.gameloft.glads.AndroidWebView;
import com.gameloft.glads.GLURLConnection;
import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.UnsupportedEncodingException;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLEncoder;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Hashtable;
import java.util.Iterator;
import java.util.List;
import java.util.Set;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

/* JADX INFO: compiled from: SocialNetwork_Facebook.java */
/* JADX INFO: loaded from: classes.dex */
public class s {
    private static String _name;
    private static String bqG;
    private static String bqH;
    private static String bqI;
    private static String bqJ;
    private static String bqK;
    private static String bqL;
    private static String bqM;
    private static String bqN;
    static int bqP;
    private static CallbackManager bqn;
    private static ProfileTracker bqp;
    protected static Profile bqq;
    private static AccessTokenTracker bqs;
    private static AccessToken bqt;
    private static ShareDialog bqu;
    private static boolean bqv;
    private static GameRequestDialog bqw;
    private static ArrayList<String> bqx;
    protected static String brg;
    private static GraphRequest brh;
    static OutputStream bro;
    protected static Activity mActivity;
    protected static h<i> bqo = new h<>();
    static int bqr = 0;
    public static int bqy = 50;
    public static int bqz = 50;
    static k bqA = new k();
    static String bqB = "";
    static String bqC = "";
    protected static i bqD = null;
    private static HashMap<String, URL> bqE = new HashMap<>();
    static boolean bqF = false;
    static boolean bqO = false;
    static int bqQ = -1;
    static boolean bqR = false;
    static boolean bqS = false;
    static boolean bqT = false;
    static boolean bqU = false;
    static boolean bqV = false;
    private static boolean bqW = false;
    public static boolean bqX = false;
    public static boolean bqY = false;
    public static boolean bqZ = false;
    private static final List<String> bra = Arrays.asList("public_profile");
    private static final List<String> brb = Collections.singletonList("user_friends");
    private static final List<String> brc = Collections.singletonList("email");
    private static final List<String> brd = Collections.singletonList("publish_actions");
    private static final List<String> bre = Collections.singletonList("publish_actions");
    public static int[] Dc = {R.string.SOCIAL_CANNOT_GO_BACK_EN, R.string.SOCIAL_CANNOT_GO_BACK_DE, R.string.SOCIAL_CANNOT_GO_BACK_FR, R.string.SOCIAL_CANNOT_GO_BACK_IT, R.string.SOCIAL_CANNOT_GO_BACK_SP, R.string.SOCIAL_CANNOT_GO_BACK_BR, R.string.SOCIAL_CANNOT_GO_BACK_BR, R.string.SOCIAL_CANNOT_GO_BACK_JP, R.string.SOCIAL_CANNOT_GO_BACK_CN, R.string.SOCIAL_CANNOT_GO_BACK_KR, R.string.SOCIAL_CANNOT_GO_BACK_RU, R.string.SOCIAL_CANNOT_GO_BACK_TR, R.string.SOCIAL_CANNOT_GO_BACK_PL, R.string.SOCIAL_CANNOT_GO_BACK_EN, R.string.SOCIAL_CANNOT_GO_BACK_EN, R.string.SOCIAL_CANNOT_GO_BACK_TH, R.string.SOCIAL_CANNOT_GO_BACK_VI, R.string.SOCIAL_CANNOT_GO_BACK_AR, R.string.SOCIAL_CANNOT_GO_BACK_EN, R.string.SOCIAL_CANNOT_GO_BACK_ZT, R.string.SOCIAL_CANNOT_GO_BACK_CN, R.string.SOCIAL_CANNOT_GO_BACK_CN, R.string.SOCIAL_CANNOT_GO_BACK_SP};
    private static int brf = 0;
    static ArrayList<String> bri = new ArrayList<>();
    static ArrayList<String> brj = new ArrayList<>();
    static ArrayList<String> brk = new ArrayList<>();
    static ArrayList<URL> brl = new ArrayList<>();
    static ArrayList<URL> brm = new ArrayList<>();
    static int brn = 0;
    static Object lock = new Object();
    static int brp = 0;
    static Hashtable<String, Integer> brq = new Hashtable<>();
    static Hashtable<String, Integer> brr = new Hashtable<>();
    static int brs = 0;

    public static void I(String str, String str2) {
        bqB = str;
        bqC = str2;
        FacebookSdk.setApplicationId(bqB);
        a(new t());
        bqu = new ShareDialog(mActivity);
        bqu.registerCallback(bqn, new ac());
        bqw = new GameRequestDialog(mActivity);
        bqw.registerCallback(bqn, new ad());
        if (nY()) {
            if (bqt == null) {
                bqt = AccessToken.getCurrentAccessToken();
            }
            if (bqt != null) {
                a(bqt, ShareConstants.WEB_DIALOG_PARAM_ID, "name", "first_name", "middle_name", "last_name", "photos");
            }
        }
        Vd();
        UU();
    }

    public static void onCreate(Bundle bundle) {
        mActivity = au.getActivity();
        FacebookSdk.sdkInitialize(mActivity);
        bqn = CallbackManager.Factory.create();
        try {
            for (Signature signature : mActivity.getPackageManager().getPackageInfo(mActivity.getPackageName(), 64).signatures) {
                MessageDigest.getInstance("SHA").update(signature.toByteArray());
            }
        } catch (PackageManager.NameNotFoundException e) {
        } catch (NoSuchAlgorithmException e2) {
            e2.printStackTrace();
        }
        bqp = new ae();
        bqs = new af();
        bqx = new ArrayList<>();
    }

    public static void onStart() {
        if (com.gameloft.android.wrapper.d.bfm != 0) {
            au.getActivity().setRequestedOrientation(0);
        }
        if (bqp != null) {
            if (!bqp.isTracking()) {
                bqp.startTracking();
            }
            bqq = Profile.getCurrentProfile();
        }
        if (bqs != null && !bqs.isTracking()) {
            bqs.startTracking();
        }
        if (nY()) {
            a(bqt, ShareConstants.WEB_DIALOG_PARAM_ID, "name", "first_name", "middle_name", "last_name", "photos");
        }
    }

    public static void onResume() {
        if (bqO && hasPublishPermission()) {
            c(bqG, bqH, _name, bqI, bqJ, bqK, bqL, bqM, bqN);
        }
        bqO = false;
        if (!bqV) {
            UT();
        }
        bqV = false;
    }

    public static void onSaveInstanceState(Bundle bundle) {
    }

    public static void onActivityResult(int i, int i2, Intent intent) {
        bqn.onActivityResult(i, i2, intent);
    }

    public static void onPause() {
    }

    public static void onStop() {
    }

    public static void onDestroy() {
        if (bqp != null && bqp.isTracking()) {
            bqp.stopTracking();
        }
        if (bqs != null && bqs.isTracking()) {
            bqs.stopTracking();
        }
    }

    private static void UT() {
        String str = brb.get(0);
        if (bqR && hj(str)) {
            Vb();
        }
        if (bqT && hj(str)) {
            UX();
        }
        if (bqS && hj(str)) {
            UZ();
        }
        bqR = false;
        bqT = false;
        bqS = false;
        bqU = false;
    }

    private static void a(FacebookCallback<LoginResult> facebookCallback) {
        LoginManager.getInstance().registerCallback(bqn, facebookCallback);
    }

    public static void UU() {
        if (bqE != null) {
            bqE.clear();
        }
    }

    public static void UV() {
        bqv = true;
        bqW = false;
        bqX = false;
        bqZ = false;
        bqD = null;
        bqo = new h<>();
        brh = null;
    }

    public static boolean nY() {
        return (Profile.getCurrentProfile() == null || AccessToken.getCurrentAccessToken() == null) ? false : true;
    }

    public static void ar(int i, int i2) {
        bqy = i;
        bqz = i2;
    }

    public static int UW() {
        return au.Qb();
    }

    public static void mU(int i) {
        bqP = i;
    }

    public static boolean nW() {
        return bqW && bqX;
    }

    public static boolean nX() {
        return bqZ;
    }

    public static FileInputStream hc(String str) {
        if (com.gameloft.android.wrapper.d.bfd) {
            try {
                return new FileInputStream(new File(au.getContext().getExternalFilesDir(null).getAbsolutePath() + "/" + str));
            } catch (IOException e) {
                return null;
            }
        }
        try {
            return au.getContext().openFileInput(str.replace("/", "\\"));
        } catch (FileNotFoundException e2) {
            return null;
        }
    }

    public static FileOutputStream H(String str, int i) {
        if (com.gameloft.android.wrapper.d.bfd) {
            try {
                return new FileOutputStream(new File(au.getContext().getExternalFilesDir(null).getAbsolutePath() + "/" + str), i == 32768);
            } catch (IOException e) {
                return null;
            }
        }
        try {
            return au.getContext().openFileOutput(str.replace("/", "\\"), i);
        } catch (FileNotFoundException e2) {
            return null;
        }
    }

    public static File hd(String str) {
        return com.gameloft.android.wrapper.d.bfd ? new File(au.getContext().getExternalFilesDir(null).getAbsolutePath() + "/" + str) : new File((au.getContext().getFilesDir().getAbsolutePath() + "/" + str).replace("/", "\\"));
    }

    public static boolean deleteFile(String str) {
        if (com.gameloft.android.wrapper.d.bfd) {
            File file = new File(au.getContext().getExternalFilesDir(null).getAbsolutePath() + "/" + str);
            if (file != null) {
                return file.delete();
            }
            return false;
        }
        return au.getContext().getFileStreamPath(str.replace("/", "\\")).delete();
    }

    public static void nT() {
        bqF = false;
        if (!nY()) {
            a(new ag());
            bqE.clear();
            d(bra);
        }
    }

    public static void d(List<String> list) {
        UV();
        Vd();
        if (mActivity != null) {
            bqV = true;
            LoginManager.getInstance().logInWithReadPermissions(mActivity, list);
        }
    }

    public static void nU() {
        bqY = false;
        UV();
        Vd();
        bqr = 0;
        bqE.clear();
        if (AccessToken.getCurrentAccessToken() != null) {
            if (bqt != null) {
                bqt = null;
            }
            LoginManager.getInstance().logOut();
            if (bqD != null && !bqD.getId().equals("")) {
                r.a(317968, 52009, bqD.getId());
            } else {
                r.a(317968, 52009, "N/A");
            }
        }
    }

    private static void UX() {
        String str;
        ah ahVar = new ah();
        if (bqt != null) {
            if (bqP == 1) {
                str = "me/friends";
            } else if (bqP == 2) {
                str = "me/invitable_friends";
            } else {
                return;
            }
            new GraphRequest(bqt, str, null, HttpMethod.GET, ahVar).executeAsync();
        }
    }

    private static GraphRequest.Callback UY() {
        return new ai();
    }

    private static void UZ() {
        GraphRequest graphRequest;
        String[] strArr = {ShareConstants.WEB_DIALOG_PARAM_ID, "name", "first_name", "last_name", "middle_name", "installed", "locale"};
        HashSet hashSet = new HashSet();
        hashSet.addAll(Arrays.asList(strArr));
        String str = "picture.width(" + bqy + ").height(" + bqz + ")";
        if (str != null) {
            hashSet.add(str);
        }
        Bundle bundle = new Bundle();
        bundle.putString(GraphRequest.FIELDS_PARAM, TextUtils.join(",", hashSet));
        if (bqQ >= 0) {
            bundle.putString("limit", "" + bqQ);
        }
        AccessToken currentAccessToken = bqt;
        if (currentAccessToken == null) {
            currentAccessToken = AccessToken.getCurrentAccessToken();
        }
        if (currentAccessToken != null) {
            if (bqP == 1) {
                graphRequest = new GraphRequest(currentAccessToken, "me/friends", bundle, HttpMethod.GET, UY());
            } else if (bqP == 2) {
                graphRequest = new GraphRequest(currentAccessToken, "me/invitable_friends", bundle, HttpMethod.GET, UY());
            } else {
                return;
            }
            graphRequest.executeAsync();
        }
    }

    public static void Va() {
        au.getActivity().runOnUiThread(new u());
    }

    public static void nV() {
        UV();
        a(bqt, ShareConstants.WEB_DIALOG_PARAM_ID, "name", "first_name", "middle_name", "last_name", "photos", "installed");
        if (!hj(brb.get(0))) {
            e(brb);
            bqR = true;
        } else {
            Vb();
        }
    }

    private static void Vb() {
        if (bqD != null && bqD.getId() != null) {
            hi(bqD.getId());
        } else {
            hi(null);
        }
    }

    public static void a(AccessToken accessToken, String... strArr) {
        GraphRequest graphRequestNewMeRequest = GraphRequest.newMeRequest(accessToken, new v(accessToken));
        Bundle bundle = new Bundle();
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < strArr.length; i++) {
            sb.append(strArr[i]);
            if (i < strArr.length - 1) {
                sb.append(",");
            }
        }
        bundle.putString(GraphRequest.FIELDS_PARAM, sb.toString());
        try {
            graphRequestNewMeRequest.setParameters(bundle);
            graphRequestNewMeRequest.executeAsync();
        } catch (Exception e) {
        }
    }

    private static String he(String str) {
        return str.replace("\\", "");
    }

    /* JADX WARN: Code duplicated, block: B:28:0x0051  */
    /* JADX WARN: Unsupported multi-entry loop pattern (BACK_EDGE: B:27:0x004e -> B:28:0x0051). Please report as a decompilation issue!!! */
    public static Object a(JSONObject jSONObject, String str, Object obj) {
        Object objValueOf;
        if (jSONObject != null && obj != null && str != null && jSONObject.has(str)) {
            try {
                if (obj instanceof String) {
                    objValueOf = jSONObject.getString(str);
                } else if (obj instanceof Integer) {
                    objValueOf = Integer.valueOf(jSONObject.getInt(str));
                } else if (obj instanceof Long) {
                    objValueOf = Long.valueOf(jSONObject.getLong(str));
                } else if ((obj instanceof Double) || (obj instanceof Float)) {
                    objValueOf = Double.valueOf(jSONObject.getDouble(str));
                } else if (obj instanceof Boolean) {
                    objValueOf = Boolean.valueOf(jSONObject.getBoolean(str));
                } else {
                    objValueOf = null;
                }
            } catch (JSONException e) {
                e.printStackTrace();
            }
        } else {
            objValueOf = null;
        }
        return objValueOf;
    }

    public static JSONObject a(JSONObject jSONObject, String str) {
        if (jSONObject != null && str != null && jSONObject.has(str)) {
            try {
                return jSONObject.getJSONObject(str);
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
        return null;
    }

    public static JSONArray b(JSONObject jSONObject, String str) {
        if (jSONObject != null && str != null && jSONObject.has(str)) {
            try {
                return jSONObject.getJSONArray(str);
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
        return null;
    }

    public static void Vc() {
        bqv = false;
        for (int i = 0; i < 2; i++) {
            a((String) null, (URL) null);
        }
    }

    static URL hf(String str) {
        JSONObject jSONObject;
        JSONObject jSONObject2;
        JSONObject jSONObject3;
        if (bqD != null && str.equals(bqD.getId())) {
            return bqD.UQ();
        }
        int iOa = oa();
        for (int i = 0; i < iOa; i++) {
            i iVarMV = mV(i);
            if (str.equals(iVarMV.getId())) {
                return iVarMV.UQ();
            }
        }
        if (bqE.size() > 0 && bqE.containsKey(str)) {
            return bqE.get(str);
        }
        bqE.put(str, null);
        String strEncode = bqB + "|" + bqC;
        String strEncode2 = "picture.width(" + bqy + ").height(" + bqz + ")";
        try {
            strEncode = URLEncoder.encode(strEncode, AndroidWebView.UTF_8);
            strEncode2 = URLEncoder.encode(strEncode2, AndroidWebView.UTF_8);
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        String strEn = au.en("https://graph.facebook.com/" + str + "?access_token=" + strEncode + "&fields=" + strEncode2);
        if (strEn != null) {
            try {
                if (strEn.compareTo("") != 0 && (jSONObject = new JSONObject(strEn)) != null && (jSONObject2 = jSONObject.getJSONObject("picture")) != null && (jSONObject3 = jSONObject2.getJSONObject(ShareConstants.WEB_DIALOG_PARAM_DATA)) != null) {
                    try {
                        URL url = new URL(jSONObject3.getString("url"));
                        bqE.put(str, url);
                        return url;
                    } catch (MalformedURLException e2) {
                    }
                }
            } catch (JSONException e3) {
                e3.printStackTrace();
            }
        }
        return null;
    }

    public static void Vd() {
        brq = new Hashtable<>();
        brr = new Hashtable<>();
        bri = new ArrayList<>();
        brj = new ArrayList<>();
        brk = new ArrayList<>();
        brl = new ArrayList<>();
        brm = new ArrayList<>();
        try {
            deleteFile("facebook/Facebook_photos");
            au.getActivity().deleteFile("FileOfOffsets");
            au.getActivity().deleteFile("FileOfLengths");
        } catch (Exception e) {
        }
        try {
            hd("facebook").mkdir();
            if (bro != null) {
                bro.close();
            }
            brp = (int) hd("facebook/Facebook_photos").length();
            bro = new BufferedOutputStream(H("facebook/Facebook_photos", 32768), GLURLConnection.BUFFER_SIZE);
        } catch (Exception e2) {
        }
    }

    public static void hg(String str) {
        boolean z = true;
        if (brn > 0) {
            if (brk.size() >= 2) {
                z = false;
            } else if (bri.contains(str) || brj.contains(str)) {
                brk.add(str);
                z = false;
            }
        }
        if (z) {
            new Thread(new w(str)).start();
        }
    }

    public static void a(String str, URL url) {
        bqv = false;
        brs = 0;
        if (str != null) {
            if (!brq.containsKey(str)) {
                n(str, 0, 0);
            }
            if (brr.get(str).intValue() == 0 && !bri.contains(str)) {
                bri.add(str);
                if (brj.contains(str)) {
                    brj.remove(str);
                }
                brl.add(url);
                if (brm.contains(url)) {
                    brm.remove(url);
                }
            }
        }
        if (brn < 2) {
            brn++;
            new Thread(new x()).start();
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static void n(String str, int i, int i2) {
        brq.put(str, new Integer(i));
        brr.put(str, new Integer(i2));
    }

    public static InputStream hh(String str) {
        int iIntValue = brq.get(str).intValue();
        int iIntValue2 = brr.get(str).intValue();
        if (iIntValue2 == 0) {
            return null;
        }
        byte[] bArr = new byte[iIntValue2];
        try {
            BufferedInputStream bufferedInputStream = new BufferedInputStream(hc("facebook/Facebook_photos"));
            bufferedInputStream.skip(iIntValue);
            bufferedInputStream.read(bArr);
            bufferedInputStream.close();
        } catch (Exception e) {
        }
        return new ByteArrayInputStream(bArr);
    }

    /* JADX WARN: Code duplicated, block: B:12:0x001f  */
    public static javax.microedition.lcdui.ao ha(String str) {
        javax.microedition.lcdui.ao aoVarA;
        if (str != null) {
            try {
                if (str.length() <= 0) {
                    aoVarA = null;
                } else {
                    InputStream inputStreamHh = hh(str);
                    if (inputStreamHh != null) {
                        aoVarA = javax.microedition.lcdui.ao.a(inputStreamHh, bqy, bqz);
                        inputStreamHh.close();
                    } else {
                        hg(str);
                        aoVarA = null;
                    }
                }
            } catch (Exception e) {
                hg(str);
                return null;
            }
        } else {
            aoVarA = null;
        }
        return aoVarA;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static void a(LoginResult loginResult) {
        Set<String> recentlyGrantedPermissions;
        if (loginResult != null && (recentlyGrantedPermissions = loginResult.getRecentlyGrantedPermissions()) != null) {
            if (bqx == null) {
                bqx = new ArrayList<>();
            }
            bqx.clear();
            Iterator<String> it = recentlyGrantedPermissions.iterator();
            while (it.hasNext()) {
                bqx.add(it.next());
            }
        }
    }

    public static i UR() {
        return bqD != null ? bqD : new i();
    }

    public static String getPassword() {
        if (bqt == null) {
            if (AccessToken.getCurrentAccessToken() != null) {
                return AccessToken.getCurrentAccessToken().getToken();
            }
            return null;
        }
        return bqt.getToken();
    }

    private static void hi(String str) {
        String str2 = "/me/friends";
        if (str != null && !str.isEmpty()) {
            str2 = "/" + str + "/friends";
        }
        if (bqt != null) {
            Bundle bundle = new Bundle();
            bundle.putString(GraphRequest.FIELDS_PARAM, "id,first_name,middle_name,last_name,name,picture.width(" + bqy + ").height(" + bqz + "),installed");
            if (bqQ >= 0) {
                bundle.putString("limit", "" + bqQ);
            }
            GraphRequest graphRequest = new GraphRequest(bqt, str2, null, HttpMethod.GET, new y());
            graphRequest.setParameters(bundle);
            graphRequest.executeAsync();
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static i e(JSONObject jSONObject) {
        String string;
        i iVar = new i();
        try {
            if (jSONObject.has(ShareConstants.WEB_DIALOG_PARAM_ID)) {
                iVar.fG(jSONObject.getString(ShareConstants.WEB_DIALOG_PARAM_ID));
            }
            if (jSONObject.has("first_name")) {
                iVar.gU(jSONObject.getString("first_name"));
            }
            if (jSONObject.has("last_name")) {
                iVar.gV(jSONObject.getString("last_name"));
            }
            if (jSONObject.has("name")) {
                iVar.setName(jSONObject.getString("name"));
            }
            if (jSONObject.has("installed")) {
                iVar.bL(jSONObject.getBoolean("installed"));
            }
            if (jSONObject.has("picture") && jSONObject.getJSONObject("picture").has(ShareConstants.WEB_DIALOG_PARAM_DATA)) {
                JSONObject jSONObject2 = jSONObject.getJSONObject("picture").getJSONObject(ShareConstants.WEB_DIALOG_PARAM_DATA);
                if (jSONObject2.has("url") && (string = jSONObject2.getString("url")) != null) {
                    iVar.a(new URL(he(string)));
                }
            }
        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (JSONException e2) {
            e2.printStackTrace();
        }
        return iVar;
    }

    public static i mV(int i) {
        return bqo.mS(i);
    }

    public static int oa() {
        if (bqo != null) {
            return bqo.getCount();
        }
        return 0;
    }

    private static boolean hasPublishPermission() {
        if (bqx != null) {
            return bqx.contains("publish_actions");
        }
        return false;
    }

    private static void Ve() {
        if (mActivity != null) {
            a(new z());
            LoginManager.getInstance().logInWithPublishPermissions(mActivity, bre);
        }
    }

    private static boolean hj(String str) {
        if (bqt != null) {
            return bqt.getPermissions().contains(str);
        }
        if (bqx != null) {
            return bqx.contains(str);
        }
        return false;
    }

    private static void e(List<String> list) {
        if (mActivity != null) {
            a(new aa());
            LoginManager.getInstance().logInWithReadPermissions(mActivity, list);
        }
    }

    public static void c(String str, String str2, String str3, String str4, String str5, String str6, String str7, String str8, String str9) {
        if (!hasPublishPermission()) {
            try {
                Ve();
                bqG = str;
                bqH = str2;
                _name = str3;
                bqI = str4;
                bqJ = str5;
                bqK = str6;
                bqL = str7;
                bqM = str8;
                bqN = str9;
                bqO = true;
                return;
            } catch (Exception e) {
            }
        }
        e(str, str2, str3, str4, str5, str6, str7, str8, str9);
    }

    public static void d(String str, String str2, String str3, String str4, String str5, String str6, String str7, String str8, String str9) {
        f(str, str2, str3, str4, str5, str6, str7, str8, str9);
    }

    static void e(String str, String str2, String str3, String str4, String str5, String str6, String str7, String str8, String str9) {
        if (bqt != null) {
            ShareApi shareApi = new ShareApi(new ShareLinkContent.Builder().setContentUrl(Uri.parse(str2)).setContentTitle(str3).setShareHashtag(new ShareHashtag.Builder().setHashtag(str9).build()).setQuote(str5).setImageUrl(Uri.parse(str6)).setPlaceId(str8).build());
            if (shareApi.canShare()) {
                shareApi.share(new ab());
            }
            ar.cm(2, UW());
        }
    }

    static void f(String str, String str2, String str3, String str4, String str5, String str6, String str7, String str8, String str9) {
        if (bqt != null) {
            ShareLinkContent shareLinkContentBuild = new ShareLinkContent.Builder().setContentUrl(Uri.parse(str2)).setContentTitle(str3).setShareHashtag(new ShareHashtag.Builder().setHashtag(str9).build()).setQuote(str5).setImageUrl(Uri.parse(str6)).setPlaceId(str8).build();
            ShareDialog shareDialog = bqu;
            if (ShareDialog.canShow((Class<? extends ShareContent>) ShareLinkContent.class)) {
                bqu.show(shareLinkContentBuild);
            }
            ar.cm(1, UW());
        }
    }

    public static void a(i iVar, i[] iVarArr, String str, String str2) {
        ArrayList arrayList;
        ArrayList arrayList2;
        if (oa() > 0) {
            if (iVarArr == null || iVarArr.length <= 0) {
                arrayList2 = null;
            } else {
                arrayList2 = new ArrayList();
                for (i iVar2 : iVarArr) {
                    arrayList2.add(iVar2.getId());
                }
            }
            if (iVar != null) {
                arrayList = new ArrayList();
                arrayList.add(iVar.getId());
            } else {
                arrayList = null;
            }
        } else {
            arrayList = null;
            arrayList2 = null;
        }
        a(arrayList, arrayList2, str, str2);
    }

    public static void a(i[] iVarArr, i[] iVarArr2, String str, String str2) {
        ArrayList arrayList;
        ArrayList arrayList2;
        if (oa() > 0) {
            if (iVarArr2 == null || iVarArr2.length <= 0) {
                arrayList2 = null;
            } else {
                arrayList2 = new ArrayList();
                for (i iVar : iVarArr2) {
                    arrayList2.add(iVar.getId());
                }
            }
            if (iVarArr == null || iVarArr.length <= 0) {
                arrayList = null;
            } else {
                arrayList = new ArrayList();
                for (i iVar2 : iVarArr) {
                    arrayList.add(iVar2.getId());
                }
            }
        } else {
            arrayList = null;
            arrayList2 = null;
        }
        a(arrayList, arrayList2, str, str2);
    }

    public static void ao(String str, String str2) {
        D("APP_NON_USERS", str, str2);
    }

    private static void a(List<String> list, List<String> list2, String str, String str2) {
        GameRequestContent gameRequestContentBuild = new GameRequestContent.Builder().setMessage(str).setData(str2).setRecipients(list).build();
        GameRequestDialog gameRequestDialog = bqw;
        if (GameRequestDialog.canShow()) {
            GameRequestDialog gameRequestDialog2 = bqw;
            GameRequestDialog.show(mActivity, gameRequestContentBuild);
        }
    }

    private static void D(String str, String str2, String str3) {
        GameRequestContent.Filters filters = GameRequestContent.Filters.APP_NON_USERS;
        if (str != null && str.equals("APP_USERS")) {
            filters = GameRequestContent.Filters.APP_USERS;
        }
        GameRequestContent gameRequestContentBuild = new GameRequestContent.Builder().setMessage(str2).setData(str3).setFilters(filters).build();
        GameRequestDialog gameRequestDialog = bqw;
        if (GameRequestDialog.canShow()) {
            GameRequestDialog gameRequestDialog2 = bqw;
            GameRequestDialog.show(mActivity, gameRequestContentBuild);
        }
    }
}
