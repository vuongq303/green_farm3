package com.quanhoang.greenfarm3;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;

/* JADX INFO: compiled from: HTMLResourceDownloader.java */
/* JADX INFO: loaded from: classes.dex */
public class q {
    private String Cw;
    private String Cx;
    private String url;
    private boolean Cy = true;
    private boolean CA = true;
    boolean CB = false;
    public ArrayList<String> Cz = new ArrayList<>();

    public q(String str, String str2, String str3) {
        this.url = str;
        this.Cx = str2;
        this.Cw = str3;
    }

    public void gx() {
        BufferedReader bufferedReader;
        File file = new File(this.Cx);
        file.mkdirs();
        try {
            if (file.isDirectory()) {
                for (String str : file.list()) {
                    new File(file, str).delete();
                }
            }
            PrintWriter printWriter = new PrintWriter(new FileWriter(this.Cx + "/" + this.Cw));
            HttpURLConnection httpURLConnection = (HttpURLConnection) new URL(this.url).openConnection();
            int responseCode = httpURLConnection.getResponseCode();
            if (responseCode == 200) {
                BufferedReader bufferedReader2 = new BufferedReader(new InputStreamReader(httpURLConnection.getInputStream()));
                while (true) {
                    String line = bufferedReader2.readLine();
                    if (line == null) {
                        break;
                    } else {
                        printWriter.println(D(line));
                    }
                }
                bufferedReader = bufferedReader2;
            } else {
                if (responseCode == 404) {
                    this.CA = false;
                }
                this.Cy = true;
                bufferedReader = null;
            }
            if (bufferedReader != null) {
                bufferedReader.close();
            }
            if (printWriter != null) {
                printWriter.close();
            }
            if (this.CB) {
                this.Cy = false;
            }
        } catch (Exception e) {
            this.Cy = true;
            e.printStackTrace();
        }
    }

    private String D(String str) {
        int iIndexOf;
        if (str.contains("<img") && str.contains("usemap") && (iIndexOf = str.indexOf("http")) != -1) {
            int i = iIndexOf;
            while (str.charAt(i) != '\"') {
                i++;
            }
            String strSubstring = str.substring(iIndexOf, i);
            f(strSubstring, this.Cx + "/" + E(strSubstring));
            return str.replace(strSubstring, E(strSubstring));
        }
        return str;
    }

    public boolean gy() {
        return this.CA;
    }

    public boolean gz() {
        return this.Cy;
    }

    public String E(String str) {
        return str.substring(str.lastIndexOf(47) + 1);
    }

    public void f(String str, String str2) {
        try {
            BufferedInputStream bufferedInputStream = new BufferedInputStream(new URL(str).openConnection().getInputStream());
            BufferedOutputStream bufferedOutputStream = new BufferedOutputStream(new FileOutputStream(str2));
            while (true) {
                int i = bufferedInputStream.read();
                if (i != -1) {
                    bufferedOutputStream.write(i);
                    this.CB = true;
                } else {
                    bufferedOutputStream.flush();
                    bufferedOutputStream.close();
                    bufferedInputStream.close();
                    return;
                }
            }
        } catch (IOException e) {
            this.Cy = true;
            e.printStackTrace();
        }
    }
}
