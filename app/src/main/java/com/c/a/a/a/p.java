package com.c.a.a.a;

import android.os.AsyncTask;
import android.text.TextUtils;
import java.io.BufferedInputStream;
import java.io.IOException;
import java.io.StringWriter;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;

/* JADX INFO: compiled from: DownloadAvidTask.java */
/* JADX INFO: loaded from: classes.dex */
public class p extends AsyncTask<String, Void, String> {
    private q cjk;

    public void a(q qVar) {
        this.cjk = qVar;
    }

    /* JADX INFO: Access modifiers changed from: protected */
    /* JADX WARN: Code duplicated, block: B:52:0x00b3 A[EXC_TOP_SPLITTER, SYNTHETIC] */
    /* JADX WARN: Code duplicated, block: B:74:? A[SYNTHETIC] */
    /* JADX WARN: Not initialized variable reg: 2, insn: 0x00c2: MOVE (r4 I:??[OBJECT, ARRAY]) = (r2 I:??[OBJECT, ARRAY]), block:B:42:0x00c1 */
    @Override // android.os.AsyncTask
    /* JADX INFO: renamed from: p, reason: merged with bridge method [inline-methods] */
    public String doInBackground(String... strArr) throws Throwable {
        Throwable th;
        BufferedInputStream bufferedInputStream;
        BufferedInputStream bufferedInputStream2;
        BufferedInputStream bufferedInputStream3;
        BufferedInputStream bufferedInputStream4;
        String str = strArr[0];
        try {
            if (TextUtils.isEmpty(str)) {
                com.c.a.a.a.g.c.e("AvidLoader: URL is empty");
                return null;
            }
            try {
                URLConnection uRLConnectionOpenConnection = new URL(str).openConnection();
                uRLConnectionOpenConnection.connect();
                bufferedInputStream3 = new BufferedInputStream(uRLConnectionOpenConnection.getInputStream());
                try {
                    StringWriter stringWriter = new StringWriter();
                    byte[] bArr = new byte[1024];
                    while (true) {
                        int i = bufferedInputStream3.read(bArr);
                        if (i == -1) {
                            break;
                        }
                        stringWriter.write(new String(bArr, 0, i));
                    }
                    String string = stringWriter.toString();
                    if (bufferedInputStream3 != null) {
                        try {
                            bufferedInputStream3.close();
                        } catch (IOException e) {
                            com.c.a.a.a.g.c.b("AvidLoader: can not close Stream", e);
                            return null;
                        }
                    }
                    return string;
                } catch (MalformedURLException e2) {
                    bufferedInputStream4 = bufferedInputStream3;
                    try {
                        com.c.a.a.a.g.c.e("AvidLoader: something is wrong with the URL '" + str + "'");
                        if (bufferedInputStream4 == null) {
                            return null;
                        }
                        try {
                            bufferedInputStream4.close();
                            return null;
                        } catch (IOException e3) {
                            com.c.a.a.a.g.c.b("AvidLoader: can not close Stream", e3);
                            return null;
                        }
                    } catch (Throwable th2) {
                        th = th2;
                        bufferedInputStream2 = bufferedInputStream4;
                        if (bufferedInputStream2 != null) {
                            throw th;
                        }
                        try {
                            bufferedInputStream2.close();
                            throw th;
                        } catch (IOException e4) {
                            com.c.a.a.a.g.c.b("AvidLoader: can not close Stream", e4);
                            return null;
                        }
                    }
                } catch (IOException e5) {
                    e = e5;
                    com.c.a.a.a.g.c.e("AvidLoader: IO error " + e.getLocalizedMessage());
                    if (bufferedInputStream3 == null) {
                        return null;
                    }
                    try {
                        bufferedInputStream3.close();
                        return null;
                    } catch (IOException e6) {
                        com.c.a.a.a.g.c.b("AvidLoader: can not close Stream", e6);
                        return null;
                    }
                }
            } catch (MalformedURLException e7) {
                bufferedInputStream4 = null;
            } catch (IOException e8) {
                e = e8;
                bufferedInputStream3 = null;
            } catch (Throwable th3) {
                th = th3;
                bufferedInputStream2 = null;
                if (bufferedInputStream2 != null) {
                    throw th;
                }
                bufferedInputStream2.close();
                throw th;
            }
        } catch (Throwable th4) {
            th = th4;
            bufferedInputStream2 = bufferedInputStream;
        }
    }

    /* JADX INFO: Access modifiers changed from: protected */
    @Override // android.os.AsyncTask
    /* JADX INFO: renamed from: iO, reason: merged with bridge method [inline-methods] */
    public void onPostExecute(String str) {
        if (this.cjk != null) {
            if (!TextUtils.isEmpty(str)) {
                this.cjk.iM(str);
            } else {
                this.cjk.aiD();
            }
        }
    }

    @Override // android.os.AsyncTask
    protected void onCancelled() {
        if (this.cjk != null) {
            this.cjk.aiD();
        }
    }
}
