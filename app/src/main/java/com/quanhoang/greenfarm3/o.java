package com.quanhoang.greenfarm3;

import android.os.Environment;
import com.gameloft.glads.AndroidWebView;
import java.io.BufferedInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.charset.Charset;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.TimeZone;
import java.util.zip.Deflater;
import org.json.JSONArray;
import org.json.JSONException;

/* JADX INFO: compiled from: GlotUtils.java */
/* JADX INFO: loaded from: classes.dex */
public class o {
    public static int Cp = 1;
    public static boolean Cq = false;

    public static void a(int i, byte[] bArr, int i2) {
        bArr[i2] = (byte) ((i >> 24) & 255);
        bArr[i2 + 1] = (byte) ((i >> 16) & 255);
        bArr[i2 + 2] = (byte) ((i >> 8) & 255);
        bArr[i2 + 3] = (byte) (i & 255);
    }

    public static int a(byte[] bArr, int i) {
        return ((bArr[i] & 255) << 24) | ((bArr[i + 1] & 255) << 16) | ((bArr[i + 2] & 255) << 8) | (bArr[i + 3] & 255);
    }

    public static void a(long j, byte[] bArr, int i) {
        bArr[i] = (byte) ((j >> 56) & 255);
        bArr[i + 1] = (byte) ((j >> 48) & 255);
        bArr[i + 2] = (byte) ((j >> 40) & 255);
        bArr[i + 3] = (byte) ((j >> 32) & 255);
        bArr[i + 4] = (byte) ((j >> 24) & 255);
        bArr[i + 5] = (byte) ((j >> 16) & 255);
        bArr[i + 6] = (byte) ((j >> 8) & 255);
        bArr[i + 7] = (byte) (255 & j);
    }

    public static long b(byte[] bArr, int i) {
        return (((long) (bArr[i] & 255)) << 56) | (((long) (bArr[i + 1] & 255)) << 48) | (((long) (bArr[i + 2] & 255)) << 40) | (((long) (bArr[i + 3] & 255)) << 32) | (((long) (bArr[i + 4] & 255)) << 24) | (((long) (bArr[i + 5] & 255)) << 16) | (((long) (bArr[i + 6] & 255)) << 8) | ((long) (bArr[i + 7] & 255));
    }

    public static String k(long j) {
        Date date = new Date(1000 * j);
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss z");
        simpleDateFormat.setTimeZone(TimeZone.getTimeZone("GMT"));
        return simpleDateFormat.format(date);
    }

    public static String s(String str) {
        if (str == null || str.isEmpty()) {
            str = "N/A";
        }
        String str2 = "";
        for (int i = 0; i < str.length(); i++) {
            str2 = str2 + "\\u" + Integer.toHexString(str.charAt(i) | 0).substring(1);
        }
        return str2;
    }

    public static String c(byte[] bArr, int i) {
        return a(bArr, i, bArr.length - i);
    }

    public static String a(byte[] bArr, int i, int i2) {
        return new String(bArr, i, i2, Charset.forName(AndroidWebView.UTF_8));
    }

    public static void t(String str) {
        String str2 = "[GLOTv3] >>>\t" + str;
        if (Cq) {
            try {
                com.gameloft.android.wrapper.au.eo(str);
            } catch (Exception e) {
                d(e);
            }
        }
    }

    public static void u(String str) {
        b(str, 2);
    }

    public static void b(String str, int i) {
        w(str);
        if (Cq && i <= Cp) {
            try {
                com.gameloft.android.wrapper.au.eo(str);
            } catch (Exception e) {
                d(e);
            }
        }
    }

    private static void w(String str) {
        if (k.DEBUG) {
            String str2 = "[GLOTv3] >>>\t" + str;
        }
    }

    public static void d(Exception exc) {
        exc.printStackTrace();
    }

    public static void gs() {
        try {
            for (StackTraceElement stackTraceElement : new Throwable().getStackTrace()) {
                u("" + stackTraceElement);
            }
        } catch (Exception e) {
        }
    }

    public static String a(String str, int i, char c) {
        int i2 = 0;
        int iIndexOf = str.indexOf(c, 1);
        int i3 = i;
        while (i3 > 0) {
            if (i2 == -1) {
                return null;
            }
            i3--;
            i2 = iIndexOf;
            iIndexOf = str.indexOf(c, iIndexOf + 1);
        }
        if (i2 == -1) {
            return null;
        }
        if (iIndexOf == -1) {
            iIndexOf = str.length();
        }
        if (i > 0) {
            i2++;
        }
        if (i2 == iIndexOf) {
            return "";
        }
        if (i2 > iIndexOf) {
            return null;
        }
        try {
            char[] cArr = new char[iIndexOf - i2];
            str.getChars(i2, iIndexOf, cArr, 0);
            return new String(cArr);
        } catch (IndexOutOfBoundsException e) {
            return null;
        }
    }

    public static JSONArray a(JSONArray jSONArray, int i) {
        if (jSONArray == null) {
            return null;
        }
        JSONArray jSONArray2 = new JSONArray();
        int length = jSONArray.length();
        for (int i2 = 0; i2 < length; i2++) {
            if (i2 != i) {
                try {
                    jSONArray2.put(jSONArray.get(i2));
                } catch (JSONException e) {
                    w("[GlotUtils]: exception catched when remove JSONArray element!");
                    e.printStackTrace();
                    return null;
                }
            }
        }
        return jSONArray2;
    }

    public static boolean e(String str, String str2) {
        if (!Cq) {
            return true;
        }
        try {
            File file = new File(Environment.getExternalStorageDirectory(), "GlotDebug/gv3");
            if (!file.exists()) {
                return false;
            }
            FileWriter fileWriter = new FileWriter(new File(file, str), true);
            fileWriter.append((CharSequence) (str2 + "\n"));
            fileWriter.flush();
            fileWriter.close();
            w("writeFileToExternalStorage(" + str + "): len = " + str2.length());
            return true;
        } catch (IOException e) {
            e.printStackTrace();
            return true;
        }
    }

    public static boolean a(String str, byte[] bArr) {
        boolean z = false;
        FileOutputStream fileOutputStreamOpenFileOutput = null;
        try {
            try {
                fileOutputStreamOpenFileOutput = com.gameloft.android.wrapper.au.getContext().openFileOutput(str, 0);
                byte[] bArr2 = new byte[4];
                a(bArr.length, bArr2, 0);
                fileOutputStreamOpenFileOutput.write(bArr2, 0, bArr2.length);
                fileOutputStreamOpenFileOutput.write(bArr, 0, bArr.length);
                fileOutputStreamOpenFileOutput.getFD().sync();
                w("writeFileToInternalStorage(" + str + "): len = " + bArr.length);
                if (fileOutputStreamOpenFileOutput != null) {
                    try {
                        fileOutputStreamOpenFileOutput.close();
                    } catch (IOException e) {
                        d(e);
                    }
                }
                z = true;
            } catch (Exception e2) {
                w("writeFileToInternalStorage(" + str + ") can't open file");
                d(e2);
                if (fileOutputStreamOpenFileOutput != null) {
                    try {
                        fileOutputStreamOpenFileOutput.close();
                    } catch (IOException e3) {
                        d(e3);
                    }
                }
            }
            return z;
        } catch (Throwable th) {
            if (fileOutputStreamOpenFileOutput != null) {
                try {
                    fileOutputStreamOpenFileOutput.close();
                } catch (IOException e4) {
                    d(e4);
                }
            }
            throw th;
        }
    }

    public static byte[] x(String str) {
        byte[] bArr = null;
        try {
            FileInputStream fileInputStreamOpenFileInput = com.gameloft.android.wrapper.au.getContext().openFileInput(str);
            if (fileInputStreamOpenFileInput == null) {
                w("[WARNING] No data in file '" + str + "'");
            } else {
                BufferedInputStream bufferedInputStream = new BufferedInputStream(fileInputStreamOpenFileInput);
                byte[] bArr2 = new byte[4];
                for (int i = 0; i < bArr2.length; i++) {
                    bArr2[i] = (byte) bufferedInputStream.read();
                }
                int i2 = ((bArr2[0] & 255) << 24) | ((bArr2[1] & 255) << 16) | ((bArr2[2] & 255) << 8) | (bArr2[3] & 255);
                if (com.gameloft.android.wrapper.au.getContext().getFileStreamPath(str).length() == i2 + 4) {
                    w("Size of '" + str + "' file: " + i2);
                    bArr = new byte[i2];
                    bufferedInputStream.read(bArr, 0, bArr.length);
                    w("readFileFromInternalStorage(" + str + "): size = " + i2 + ", len = " + bArr.length);
                }
                bufferedInputStream.close();
                fileInputStreamOpenFileInput.close();
            }
        } catch (FileNotFoundException e) {
            w("TrackingManager.readFileFromInternalStorage: '" + str + "' is not exist!");
        } catch (Exception e2) {
            d(e2);
        }
        return bArr;
    }

    public static boolean y(String str) {
        return com.gameloft.android.wrapper.au.getContext().deleteFile(str);
    }

    public static boolean z(String str) {
        return com.gameloft.android.wrapper.au.getContext().getFileStreamPath(str).exists();
    }

    public static byte[] a(int[] iArr, boolean z, byte[] bArr) {
        int length = iArr.length << 2;
        if (z) {
            int i = iArr[iArr.length - 1];
            if (i > length) {
                return null;
            }
            length = i;
        }
        if (bArr == null) {
            bArr = new byte[length];
        }
        for (int i2 = 0; i2 < length; i2++) {
            bArr[i2] = (byte) ((iArr[i2 >>> 2] >>> ((i2 & 3) << 3)) & 255);
        }
        return bArr;
    }

    public static int[] a(byte[] bArr, boolean z, int[] iArr) {
        int length = (bArr.length & 3) == 0 ? bArr.length >>> 2 : (bArr.length >>> 2) + 1;
        if (z) {
            if (iArr == null) {
                iArr = new int[length + 1];
            }
            iArr[length] = bArr.length;
        } else if (iArr == null) {
            iArr = new int[length];
        }
        int length2 = bArr.length;
        for (int i = 0; i < length2; i++) {
            int i2 = i >>> 2;
            iArr[i2] = iArr[i2] | ((bArr[i] & 255) << ((i & 3) << 3));
        }
        return iArr;
    }

    public static javax.microedition.b.b b(String str, boolean z) {
        try {
            return javax.microedition.b.b.t(str, z);
        } catch (javax.microedition.b.c e) {
            return null;
        } catch (Exception e2) {
            w(e2.getMessage());
            return null;
        }
    }

    public static void A(String str) {
        try {
            javax.microedition.b.b.ji(str);
        } catch (Exception e) {
            w(e.getMessage());
        }
    }

    public static boolean a(ByteArrayOutputStream byteArrayOutputStream, ByteArrayOutputStream byteArrayOutputStream2) {
        byte[] byteArray = byteArrayOutputStream.toByteArray();
        Deflater deflater = new Deflater(9);
        deflater.setInput(byteArray);
        deflater.finish();
        byte[] bArr = new byte[4096];
        while (!deflater.finished()) {
            byteArrayOutputStream2.write(bArr, 0, deflater.deflate(bArr));
        }
        deflater.end();
        return true;
    }

    public static boolean B(String str) {
        if (str == null || str.isEmpty()) {
            return false;
        }
        for (int i = 0; i < str.length(); i++) {
            if (Character.isISOControl(str.charAt(i))) {
                return true;
            }
        }
        return false;
    }

    public static boolean C(String str) {
        if (str.length() != 36) {
            return false;
        }
        for (int i = 0; i < str.length(); i++) {
            char cCharAt = str.charAt(i);
            if (i == 8 || i == 13 || i == 18 || i == 23) {
                if (cCharAt != '-') {
                    return false;
                }
            } else if ((cCharAt < '0' || cCharAt > '9') && ((cCharAt < 'A' || cCharAt > 'F') && (cCharAt < 'a' || cCharAt > 'f'))) {
                return false;
            }
        }
        return true;
    }
}
