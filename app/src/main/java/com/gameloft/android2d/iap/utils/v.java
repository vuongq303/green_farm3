package com.gameloft.android2d.iap.utils;

import android.app.Activity;
import android.content.Context;
import android.content.res.Configuration;
import android.database.Cursor;
import android.net.ConnectivityManager;
import android.net.Uri;
import android.os.Build;
import android.os.Environment;
import android.provider.Settings;
import com.facebook.appevents.codeless.internal.Constants;
import com.gameloft.android.wrapper.au;
import com.gameloft.glads.AndroidWebView;
import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.DataInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.math.BigInteger;
import java.net.HttpURLConnection;
import java.net.URL;
import java.nio.charset.Charset;
import java.security.MessageDigest;
import java.text.SimpleDateFormat;
import java.util.Locale;
import java.util.Random;
import javax.microedition.lcdui.ao;
import javax.microedition.midlet.MIDlet;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

/* JADX INFO: compiled from: SUtils.java */
/* JADX INFO: loaded from: classes.dex */
public class v {
    private static Context bfp = null;
    private static char[] Nl = {' ', '{', '}', ':', '*', ',', '|', '\"', '+', '-', '/', '$', '='};

    public static void an(Context context) {
        bfp = context;
    }

    public static Context getContext() {
        if (bfp == null) {
        }
        return bfp;
    }

    public static String cn(int i, int i2) {
        int iAbs = Math.abs(new Random().nextInt() % 9999);
        while (iAbs < 1000) {
            if (iAbs < 1000) {
                iAbs += Constants.APP_INDEXING_SCHEDULE_INTERVAL_MS;
            }
        }
        return String.valueOf(iAbs);
    }

    public static boolean ah(String str, String str2) {
        return str.equals(String.valueOf(Integer.parseInt(str2) ^ 53412));
    }

    public static String TK() {
        try {
            InputStream inputStreamOpenRawResource = getContext().getResources().openRawResource(getContext().getResources().getIdentifier("serialkey", "raw", getContext().getPackageName()));
            BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(inputStreamOpenRawResource, Charset.forName(AndroidWebView.UTF_8)));
            String line = bufferedReader.readLine();
            if (line == null) {
                line = "";
            }
            inputStreamOpenRawResource.close();
            bufferedReader.close();
            return line;
        } catch (Exception e) {
            return "";
        }
    }

    public static String TL() {
        try {
            String str = getContext().getPackageManager().getPackageInfo(getContext().getPackageName(), 0).versionName;
            if (str == null || str.equals("")) {
                return "100";
            }
            return str;
        } catch (Exception e) {
            return "100";
        }
    }

    public static boolean TM() {
        return ((ConnectivityManager) getContext().getSystemService("connectivity")).getNetworkInfo(0).isConnected();
    }

    public static boolean TN() {
        return ((ConnectivityManager) getContext().getSystemService("connectivity")).getNetworkInfo(1).isConnected();
    }

    public static int fZ(String str) {
        try {
            return Integer.parseInt(str);
        } catch (NumberFormatException e) {
            return -1;
        }
    }

    public static long ga(String str) {
        try {
            return Long.parseLong(str);
        } catch (NumberFormatException e) {
            return -1L;
        }
    }

    public static float gb(String str) {
        try {
            return Float.parseFloat(str.replace(',', '.'));
        } catch (NumberFormatException e) {
            return -1.0f;
        }
    }

    public static long gc(String str) {
        if (str == null || str.equals("")) {
            return 0L;
        }
        str.trim();
        try {
            return new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").parse(Integer.parseInt(str.substring(0, 4)) + "-" + Integer.parseInt(str.substring(5, 7)) + "-" + Integer.parseInt(str.substring(8, 10)) + " " + Integer.parseInt(str.substring(11, 13)) + ":" + Integer.parseInt(str.substring(14, 16)) + ":" + Integer.parseInt(str.substring(17, 19))).getTime();
        } catch (Exception e) {
            return 0L;
        }
    }

    public static boolean Pm() {
        return Settings.System.getInt(getContext().getContentResolver(), "airplane_mode_on", 0) != 0;
    }

    public static String gd(String str) {
        try {
            MessageDigest messageDigest = MessageDigest.getInstance("MD5");
            messageDigest.update(str.getBytes());
            byte[] bArrDigest = messageDigest.digest();
            return String.format("%0" + (bArrDigest.length << 1) + "x", new BigInteger(1, bArrDigest));
        } catch (Exception e) {
            return null;
        }
    }

    public static String ge(String str) {
        StringBuffer stringBuffer = new StringBuffer();
        try {
            MessageDigest messageDigest = MessageDigest.getInstance("SHA-256");
            messageDigest.reset();
            messageDigest.update(str.getBytes(AndroidWebView.UTF_8));
            byte[] bArrDigest = messageDigest.digest();
            for (byte b : bArrDigest) {
                stringBuffer.append(Integer.toString((b & 255) + 256, 16).substring(1));
            }
        } catch (Exception e) {
        }
        return stringBuffer.toString();
    }

    public static long ai(String str, String str2) {
        try {
            long jGc = gc(str);
            long jGc2 = gc(str2);
            if (jGc <= 0 || jGc2 <= 0) {
                return -2000L;
            }
            return jGc - jGc2;
        } catch (Exception e) {
            return -2000L;
        }
    }

    public static boolean gf(String str) {
        return (str == null || str.equals("")) ? false : true;
    }

    public static boolean TO() throws Throwable {
        try {
            String strTP = TP();
            if (strTP != null) {
                File file = new File(strTP);
                if (file.exists()) {
                    if (!file.isDirectory()) {
                        file.delete();
                        file.mkdirs();
                    }
                } else {
                    file.mkdirs();
                }
            }
            x xVarQr = com.gameloft.android2d.iap.b.Qr();
            if (xVarQr == null) {
                return false;
            }
            String strMJ = mJ(com.b.d.cgz);
            int iAB = com.gameloft.android2d.iap.b.aB(strMJ);
            for (int i = 0; i < iAB; i++) {
                r rVarF = xVarQr.F(strMJ, i);
                if (rVarF != null) {
                    a(rVarF.SS(), strTP, rVarF.getId() + ".png", rVarF);
                    rVarF.fI(strTP + "/" + rVarF.getId() + ".png");
                }
            }
            String strMJ2 = mJ(com.b.d.cgA);
            int iAB2 = com.gameloft.android2d.iap.b.aB(strMJ2);
            for (int i2 = 0; i2 < iAB2; i2++) {
                r rVarF2 = xVarQr.F(strMJ2, i2);
                if (rVarF2 != null) {
                    a(rVarF2.SS(), strTP, rVarF2.getId() + ".png", rVarF2);
                    rVarF2.fI(strTP + "/" + rVarF2.getId() + ".png");
                }
            }
            return true;
        } catch (Exception e) {
            return false;
        }
    }

    static String TP() {
        if (!Environment.getExternalStorageState().equals("mounted")) {
            return null;
        }
        if (Build.VERSION.SDK_INT >= 8) {
            return getContext().getExternalFilesDir(null) + mJ(com.b.d.cgu);
        }
        return Environment.getDownloadCacheDirectory() + mJ(com.b.d.cgu);
    }

    static boolean a(String str, String str2, String str3, r rVar) throws Throwable {
        Throwable th;
        HttpURLConnection httpURLConnection;
        boolean z;
        int length;
        FileOutputStream fileOutputStream;
        DataInputStream dataInputStream;
        HttpURLConnection httpURLConnection2 = null;
        int i = 0;
        try {
            httpURLConnection = (HttpURLConnection) new URL(str).openConnection();
            try {
                httpURLConnection.setConnectTimeout(10000);
                httpURLConnection.setRequestMethod("GET");
                httpURLConnection.setRequestProperty("Connection", "close");
                String str4 = str2 + "/" + str3;
                if (str2 != null) {
                    File file = new File(str4);
                    if (file.exists()) {
                        length = (int) file.length();
                    } else {
                        file.createNewFile();
                        length = 0;
                    }
                    z = file.exists();
                } else {
                    z = false;
                    length = 0;
                }
                if (httpURLConnection.getResponseCode() != 200) {
                    if (httpURLConnection != null) {
                        httpURLConnection.disconnect();
                    }
                    return false;
                }
                int contentLength = httpURLConnection.getContentLength();
                if (contentLength <= 1) {
                }
                if (contentLength == length) {
                    if (httpURLConnection == null) {
                        return true;
                    }
                    httpURLConnection.disconnect();
                    return true;
                }
                synchronized (httpURLConnection) {
                    try {
                        BufferedInputStream bufferedInputStream = new BufferedInputStream(httpURLConnection.getInputStream(), 10240);
                        bufferedInputStream.mark(10240);
                        if (z) {
                            DataInputStream dataInputStream2 = new DataInputStream(bufferedInputStream);
                            FileOutputStream fileOutputStream2 = new FileOutputStream(str4);
                            while (i < contentLength) {
                                int i2 = contentLength - i;
                                if (i2 > 131072) {
                                    i2 = 131072;
                                }
                                byte[] bArr = new byte[i2];
                                dataInputStream2.readFully(bArr);
                                fileOutputStream2.write(bArr);
                                fileOutputStream2.flush();
                                i += i2;
                            }
                            if (dataInputStream2.markSupported()) {
                                dataInputStream2.reset();
                            }
                            fileOutputStream = fileOutputStream2;
                            dataInputStream = dataInputStream2;
                        } else {
                            fileOutputStream = null;
                            dataInputStream = null;
                        }
                        rVar.a(ao.m(bufferedInputStream));
                        if (fileOutputStream != null) {
                            fileOutputStream.close();
                        }
                        if (dataInputStream != null) {
                            dataInputStream.close();
                        }
                        if (bufferedInputStream != null) {
                            bufferedInputStream.close();
                        }
                    } catch (Throwable th2) {
                        throw th2;
                    }
                }
                if (httpURLConnection == null) {
                    return true;
                }
                httpURLConnection.disconnect();
                return true;
            } catch (Exception e) {
                if (httpURLConnection == null) {
                    return true;
                }
                httpURLConnection.disconnect();
                return true;
            } catch (Throwable th3) {
                th = th3;
                httpURLConnection2 = httpURLConnection;
                if (httpURLConnection2 == null) {
                    throw th;
                }
                httpURLConnection2.disconnect();
                throw th;
            }
        } catch (Exception e2) {
            httpURLConnection = null;
        } catch (Throwable th4) {
            th = th4;
        }
    }

    public static boolean TQ() {
        try {
            x xVarQr = com.gameloft.android2d.iap.b.Qr();
            return xVarQr != null && a(xVarQr, mJ(com.b.d.cgz)) && a(xVarQr, mJ(com.b.d.cgA));
        } catch (Exception e) {
            return false;
        }
    }

    public static boolean a(x xVar, String str) {
        if (xVar == null) {
            return false;
        }
        int iAB = com.gameloft.android2d.iap.b.aB(str);
        for (int i = 0; i < iAB; i++) {
            r rVarF = xVar.F(str, i);
            if (rVarF != null && rVarF.SW() == null) {
                FileInputStream fileInputStreamGg = gg(rVarF.SV());
                if (fileInputStreamGg == null) {
                    return false;
                }
                rVarF.a(ao.m(fileInputStreamGg));
            }
        }
        return true;
    }

    public static FileInputStream gg(String str) {
        File file = new File(str);
        if (file.exists()) {
            return new FileInputStream(file);
        }
        return null;
    }

    public static byte[] p(String str, boolean z) {
        return b(str, z, false);
    }

    public static byte[] b(String str, boolean z, boolean z2) {
        InputStream resourceAsStream;
        byte[] bArr;
        byte[] bArr2 = null;
        try {
            if (!z) {
                resourceAsStream = getContext().getResources().getAssets().open(str);
            } else {
                resourceAsStream = au.getResourceAsStream(str);
            }
            if (z2) {
                bArr = null;
            } else {
                bArr = new byte[resourceAsStream.available()];
                resourceAsStream.read(bArr);
            }
            resourceAsStream.close();
            bArr2 = bArr;
            return bArr2;
        } catch (Exception e) {
            return bArr2;
        }
    }

    public static String gh(String str) {
        MIDlet mIDletQp = com.gameloft.android2d.iap.b.Qp();
        if (mIDletQp == null) {
            return null;
        }
        String strBY = mIDletQp.bY(str);
        if (gf(strBY)) {
            return strBY;
        }
        return null;
    }

    public static String mI(int i) {
        return Y(i, com.gameloft.android2d.iap.b.getLanguage());
    }

    public static String Y(int i, String str) {
        String lowerCase = str.toLowerCase();
        Locale locale = Locale.getDefault();
        Locale locale2 = new Locale(lowerCase);
        Locale.setDefault(locale2);
        Configuration configuration = new Configuration();
        configuration.locale = locale2;
        getContext().getResources().updateConfiguration(configuration, getContext().getResources().getDisplayMetrics());
        String string = ((Activity) getContext()).getString(i);
        Locale.setDefault(locale);
        Configuration configuration2 = new Configuration();
        configuration2.locale = locale;
        getContext().getResources().updateConfiguration(configuration2, getContext().getResources().getDisplayMetrics());
        return string;
    }

    public static String mJ(int i) {
        return ((Activity) getContext()).getString(i);
    }

    public static String l(InputStream inputStream) {
        try {
            StringBuilder sb = new StringBuilder();
            BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(inputStream));
            do {
                String line = bufferedReader.readLine();
                if (line == null) {
                    break;
                }
                sb.append(line);
            } while (bufferedReader.ready());
            bufferedReader.close();
            return sb.toString();
        } catch (Exception e) {
            return "";
        }
    }

    public static String E(String str, int i) {
        String strMJ = mJ(com.b.d.cgM);
        String strMJ2 = i == 1 ? mJ(com.b.d.cgf) : "";
        if (i == 2) {
            strMJ2 = mJ(com.b.d.cgg);
        }
        String strReplace = strMJ2;
        for (int i2 = 0; i2 < str.length(); i2++) {
            char cCharAt = str.charAt(i2);
            strReplace = strReplace.replace(strReplace.charAt(strMJ.indexOf(cCharAt) % strReplace.length()), cCharAt);
        }
        String str2 = i == 1 ? strReplace + str : strReplace;
        return i == 2 ? str + str2 : str2;
    }

    public static String gi(String str) {
        for (String str2 : str.replace('.', ' ').replace(':', ' ').replace(',', ' ').replace(';', ' ').split(" ")) {
            if (str2.startsWith("#") && str2.length() > 2) {
                String strSubstring = str2.substring(1);
                if (ga(strSubstring) > 0) {
                    return strSubstring;
                }
            }
        }
        return "11111";
    }

    public static String gj(String str) {
        try {
            Cursor cursorQuery = com.gameloft.android2d.iap.b.Qo().getContentResolver().query(Uri.parse(mJ(com.b.d.cgH)), null, null, null, null);
            if (cursorQuery == null) {
                return "11111";
            }
            if (cursorQuery.moveToFirst()) {
                do {
                    String string = cursorQuery.getString(cursorQuery.getColumnIndex("body"));
                    if (string.indexOf(str) != -1) {
                        return gi(string);
                    }
                } while (cursorQuery.moveToNext());
            }
            cursorQuery.close();
            return "11111";
        } catch (Exception e) {
        }
    }

    public static String TR() {
        return mJ(com.b.d.cgE);
    }

    public static String TS() {
        return mJ(com.b.d.cgK);
    }

    public static boolean gk(String str) {
        try {
            new JSONObject(str);
        } catch (JSONException e) {
            try {
                new JSONArray(str);
            } catch (JSONException e2) {
                return false;
            }
        }
        return true;
    }
}
