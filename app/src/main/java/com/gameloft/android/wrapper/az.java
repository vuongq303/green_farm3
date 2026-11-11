package com.gameloft.android.wrapper;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import org.json.JSONObject;

/* JADX INFO: compiled from: Utils.java */
/* JADX INFO: loaded from: classes.dex */
final class az implements Runnable {
    final /* synthetic */ String biW;

    az(String str) {
        this.biW = str;
    }

    /* JADX WARN: Code duplicated, block: B:18:0x0061 A[DONT_INVERT] */
    /* JADX WARN: Code duplicated, block: B:19:0x0063 A[Catch: Throwable -> 0x00f1, TRY_LEAVE, TryCatch #1 {Throwable -> 0x00f1, blocks: (B:17:0x005e, B:19:0x0063), top: B:62:0x005e }] */
    /* JADX WARN: Code duplicated, block: B:33:0x00c3 A[Catch: Throwable -> 0x00c7, TRY_LEAVE, TryCatch #9 {Throwable -> 0x00c7, blocks: (B:31:0x00be, B:33:0x00c3), top: B:72:0x00be }] */
    /* JADX WARN: Code duplicated, block: B:62:0x005e A[EXC_TOP_SPLITTER, SYNTHETIC] */
    @Override // java.lang.Runnable
    public void run() throws Throwable {
        Throwable th;
        InputStream inputStream;
        HttpURLConnection httpURLConnection;
        InputStream errorStream;
        HttpURLConnection httpURLConnection2;
        String str;
        String string = null;
        String strAL = au.aL(this.biW);
        String str2 = "strUrl=" + strAL;
        try {
            httpURLConnection2 = (HttpURLConnection) new URL(strAL).openConnection();
            try {
                httpURLConnection2.setRequestMethod("GET");
                httpURLConnection2.connect();
                errorStream = httpURLConnection2.getResponseCode() != 200 ? httpURLConnection2.getErrorStream() : httpURLConnection2.getInputStream();
                try {
                    if (errorStream != null) {
                        try {
                            try {
                                ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
                                byte[] bArr = new byte[1024];
                                try {
                                    while (true) {
                                        int i = errorStream.read(bArr);
                                        if (i == -1) {
                                            break;
                                        } else {
                                            byteArrayOutputStream.write(bArr, 0, i);
                                        }
                                        if (errorStream != null) {
                                            try {
                                                errorStream.close();
                                                if (httpURLConnection2 != null) {
                                                    httpURLConnection2.disconnect();
                                                }
                                            } catch (Throwable th2) {
                                            }
                                        } else if (httpURLConnection2 != null) {
                                            httpURLConnection2.disconnect();
                                        }
                                        string = new JSONObject(str).getString("code");
                                        String str3 = "code=" + string;
                                        if (string == null && string.equals("upgrade_required_with_wifi")) {
                                            au.biV = Boolean.TRUE;
                                            return;
                                        } else {
                                            au.biV = Boolean.FALSE;
                                        }
                                    }
                                    String str4 = "strCode=" + str;
                                } catch (IOException e) {
                                    if (errorStream != null) {
                                        errorStream.close();
                                        if (httpURLConnection2 != null) {
                                            httpURLConnection2.disconnect();
                                        }
                                    } else if (httpURLConnection2 != null) {
                                        httpURLConnection2.disconnect();
                                    }
                                }
                                str = new String(byteArrayOutputStream.toByteArray());
                            } catch (Throwable th3) {
                                th = th3;
                                inputStream = errorStream;
                                httpURLConnection = httpURLConnection2;
                                if (inputStream != null) {
                                    try {
                                        inputStream.close();
                                    } catch (Throwable th4) {
                                        throw th;
                                    }
                                }
                                if (httpURLConnection != null) {
                                    httpURLConnection.disconnect();
                                    throw th;
                                }
                                throw th;
                            }
                        } catch (IOException e2) {
                            str = null;
                        }
                    } else {
                        str = null;
                    }
                    string = new JSONObject(str).getString("code");
                    String str5 = "code=" + string;
                } catch (Exception e3) {
                }
                if (errorStream != null) {
                    try {
                        errorStream.close();
                        if (httpURLConnection2 != null) {
                            httpURLConnection2.disconnect();
                        }
                    } catch (Throwable th5) {
                    }
                } else if (httpURLConnection2 != null) {
                    httpURLConnection2.disconnect();
                }
            } catch (IOException e4) {
                errorStream = null;
                str = null;
            } catch (Throwable th6) {
                th = th6;
                inputStream = null;
                httpURLConnection = httpURLConnection2;
            }
        } catch (IOException e5) {
            errorStream = null;
            httpURLConnection2 = null;
            str = null;
        } catch (Throwable th7) {
            th = th7;
            inputStream = null;
            httpURLConnection = null;
        }
        if (string == null) {
        }
        au.biV = Boolean.FALSE;
    }
}
