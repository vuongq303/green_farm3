package com.gameloft.android.ANMP.GloftGF2F.PushNotification;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import com.facebook.share.internal.MessengerShareContentUtility;
import com.gameloft.android.wrapper.au;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.TreeMap;

/* JADX INFO: compiled from: PNImageHelper.java */
/* JADX INFO: loaded from: classes.dex */
public class j {
    private static Map<Long, String> Fu = null;
    private static Integer Fv = 604800000;
    private static Integer Fw = 15;

    public static String L(Context context) {
        return au.Pp() + "/pn_images";
    }

    // Recovered from the original APK classes.dex, method a(Context, Bitmap, String).
    public static String a(Context context, Bitmap bitmap, String str) {
        String strL = L(context);
        File file = new File(strL);
        if (!file.exists()) {
            file.mkdir();
        }
        String str2 = strL + "/" + str;
        File file2 = new File(str2);
        if (!file2.exists()) {
            file2.mkdir();
        }
        File image = new File(str2 + "/" + MessengerShareContentUtility.MEDIA_IMAGE + ".jpg");
        FileOutputStream fileOutputStream = null;
        boolean z = false;
        try {
            fileOutputStream = new FileOutputStream(image);
            // The original bytecode ignores compress()'s boolean return value.
            bitmap.compress(Bitmap.CompressFormat.JPEG, 100, fileOutputStream);
            z = true;
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                // Preserve the original unconditional close, including its null-stream behavior.
                fileOutputStream.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return z ? str2 : null;
    }

    public static Bitmap l(Context context, String str) {
        try {
            File file = new File(L(context) + "/" + str + "/" + MessengerShareContentUtility.MEDIA_IMAGE + ".jpg");
            if (!file.exists()) {
                return null;
            }
            return BitmapFactory.decodeStream(new FileInputStream(file));
        } catch (FileNotFoundException e) {
            e.printStackTrace();
            return null;
        }
    }

    public static boolean m(Context context, String str) {
        File file = new File(L(context) + "/" + str);
        if (file.exists()) {
            File[] fileArrListFiles = file.listFiles();
            if (fileArrListFiles == null) {
                return true;
            }
            for (File file2 : fileArrListFiles) {
                file2.delete();
            }
        }
        return file.delete();
    }

    public static void c(Context context, String str, String str2) {
        String str3 = L(context) + "/" + str + "/etag.txt";
        try {
            File file = new File(str3);
            if (!file.exists()) {
                new File(file.getParent()).mkdirs();
                file.createNewFile();
            }
        } catch (Exception e) {
        }
        try {
            FileOutputStream fileOutputStream = new FileOutputStream(str3);
            OutputStreamWriter outputStreamWriter = new OutputStreamWriter(fileOutputStream);
            outputStreamWriter.write(str2);
            outputStreamWriter.flush();
            outputStreamWriter.close();
            fileOutputStream.close();
        } catch (Exception e2) {
        }
    }

    public static String n(Context context, String str) {
        String str2;
        try {
            File file = new File(L(context) + "/" + str + "/etag.txt");
            if (!file.exists()) {
                return "0000000000";
            }
            byte[] bArr = new byte[(int) file.length()];
            FileInputStream fileInputStream = new FileInputStream(file);
            fileInputStream.read(bArr);
            fileInputStream.close();
            str2 = new String(bArr);
        } catch (Exception e) {
            str2 = "0000000000";
        }
        return str2;
    }

    public static void hg() {
        Fu = new TreeMap(Fu);
    }

    public static void M(Context context) {
        String str = L(context) + "/Time_log.txt";
        Fu = null;
        Fu = new HashMap();
        try {
            File file = new File(str);
            if (file.exists()) {
                BufferedReader bufferedReader = new BufferedReader(new FileReader(file));
                while (true) {
                    String line = bufferedReader.readLine();
                    if (line == null) {
                        break;
                    }
                    String[] strArrSplit = line.split(",", 2);
                    if (strArrSplit.length >= 2) {
                        Fu.put(Long.valueOf(Long.parseLong(strArrSplit[0])), strArrSplit[1]);
                    }
                }
            } else {
                return;
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e2) {
            e2.printStackTrace();
        }
        hg();
    }

    public static void N(Context context) {
        if (Fu != null) {
            try {
                File file = new File(L(context) + "/Time_log.txt");
                if (!file.exists()) {
                    new File(file.getParent()).mkdirs();
                    file.createNewFile();
                }
                if (Fu.isEmpty()) {
                    file.delete();
                    return;
                }
                hg();
                BufferedWriter bufferedWriter = new BufferedWriter(new FileWriter(file));
                for (Long l : Fu.keySet()) {
                    bufferedWriter.write(l.toString() + "," + Fu.get(l) + "\n");
                }
                bufferedWriter.close();
            } catch (Exception e) {
            }
        }
    }

    public static void ag(String str) {
        if (Fu != null && !Fu.isEmpty()) {
            Iterator<Map.Entry<Long, String>> it = Fu.entrySet().iterator();
            while (it.hasNext()) {
                if (str.equalsIgnoreCase(it.next().getValue())) {
                    it.remove();
                }
            }
        }
    }

    public static void a(Context context, String str, Long l) {
        ag(str);
        Fu.put(l, str);
    }

    public static void O(Context context) {
        if (Fu != null && !Fu.isEmpty()) {
            Long lValueOf = Long.valueOf(System.currentTimeMillis());
            Iterator<Map.Entry<Long, String>> it = Fu.entrySet().iterator();
            while (it.hasNext()) {
                Map.Entry<Long, String> next = it.next();
                if (next.getKey().longValue() + ((long) Fv.intValue()) < lValueOf.longValue()) {
                    m(context, next.getValue());
                    it.remove();
                }
            }
        }
    }

    public static void P(Context context) {
        if (Fu != null && !Fu.isEmpty()) {
            hg();
            ArrayList arrayList = new ArrayList(Fu.keySet());
            Integer numValueOf = Integer.valueOf((Fu.size() - Fw.intValue()) + 1);
            int i = 0;
            while (true) {
                int i2 = i;
                if (i2 < numValueOf.intValue()) {
                    m(context, Fu.get(arrayList.get(i2)));
                    Fu.remove(arrayList.get(i2));
                    i = i2 + 1;
                } else {
                    return;
                }
            }
        }
    }
}
