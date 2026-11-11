package com.gameloft.android2d.socialnetwork;

import android.os.Looper;
import android.os.Process;
import com.gameloft.android.wrapper.au;
import com.gameloft.glads.GLURLConnection;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;

/* JADX INFO: compiled from: SocialNetwork_Facebook.java */
/* JADX INFO: loaded from: classes.dex */
final class x implements Runnable {
    x() {
    }

    /* JADX WARN: Code duplicated, block: B:121:? A[SYNTHETIC] */
    /* JADX WARN: Code duplicated, block: B:74:0x013a  */
    @Override // java.lang.Runnable
    public void run() throws Throwable {
        boolean z;
        String str;
        Throwable th;
        HttpURLConnection httpURLConnection;
        Exception e;
        boolean z2;
        boolean z3;
        URL url;
        boolean z4;
        int i;
        String str2;
        int i2;
        Looper.prepare();
        Process.setThreadPriority(10);
        byte[] bArr = new byte[GLURLConnection.BUFFER_SIZE];
        boolean z5 = false;
        boolean z6 = false;
        String str3 = null;
        URL url2 = null;
        while (true) {
            try {
                Thread.sleep(200L);
            } catch (Exception e2) {
            }
            if (z6) {
                z = z5;
                str = str3;
            } else {
                synchronized (s.lock) {
                    try {
                        if (s.brk.size() > 0) {
                            int i3 = 0;
                            while (true) {
                                if (i3 >= s.bri.size()) {
                                    i2 = 0;
                                    break;
                                } else {
                                    if (s.bri.get(i3).compareTo(s.brk.get(0)) == 0) {
                                        i2 = i3;
                                        break;
                                    }
                                    i3++;
                                }
                            }
                            s.brk.remove(0);
                            z4 = true;
                            i = i2;
                        } else {
                            z4 = false;
                            i = 0;
                        }
                        if (s.bri.size() > 0) {
                            str2 = s.bri.get(i);
                            s.bri.remove(i);
                            url = s.brl.get(i);
                            try {
                                s.brl.remove(i);
                                if (s.brm.contains(url)) {
                                    str2 = null;
                                } else {
                                    s.brm.add(url);
                                    s.brj.add(str2);
                                }
                            } catch (Exception e3) {
                                z3 = z4;
                                z = z3;
                                str = null;
                                url2 = url;
                            }
                        } else {
                            str2 = null;
                            url = null;
                        }
                        z = z4;
                        str = str2;
                        url2 = url;
                    } catch (Exception e4) {
                        z3 = z5;
                        url = null;
                    }
                }
            }
            if (str != null) {
                try {
                    HttpURLConnection httpURLConnection2 = (HttpURLConnection) url2.openConnection();
                    try {
                        try {
                            httpURLConnection2.setRequestMethod("GET");
                            InputStream inputStream = httpURLConnection2.getInputStream();
                            synchronized (s.lock) {
                                try {
                                    int i4 = s.brp;
                                    while (true) {
                                        int i5 = inputStream.read(bArr);
                                        if (i5 == -1) {
                                            break;
                                        }
                                        s.brp += i5;
                                        s.bro.write(bArr, 0, i5);
                                    }
                                    s.n(str, i4, s.brp - i4);
                                } catch (Throwable th2) {
                                    throw th2;
                                }
                            }
                            inputStream.close();
                            s.bqA.remove(str);
                            s.brs++;
                            if (z) {
                                try {
                                    s.bro.flush();
                                } catch (Exception e5) {
                                }
                            }
                            if (httpURLConnection2 != null) {
                                httpURLConnection2.disconnect();
                                z2 = false;
                            } else {
                                z2 = false;
                            }
                        } catch (Exception e6) {
                            e = e6;
                            httpURLConnection = httpURLConnection2;
                            try {
                                e.printStackTrace();
                                if (s.bqA.gY(str)) {
                                    z2 = true;
                                } else {
                                    s.bri.add(str);
                                    s.brl.add(url2);
                                    z2 = false;
                                }
                                s.n(str, 0, 0);
                                if (httpURLConnection != null) {
                                    httpURLConnection.disconnect();
                                }
                            } catch (Throwable th3) {
                                th = th3;
                                if (httpURLConnection != null) {
                                    throw th;
                                }
                                httpURLConnection.disconnect();
                                throw th;
                            }
                        }
                        s.brm.remove(url2);
                        s.brj.remove(str);
                        if (s.brs % 10 == 0) {
                        }
                        au.Qi();
                    } catch (Throwable th4) {
                        th = th4;
                        httpURLConnection = httpURLConnection2;
                        if (httpURLConnection != null) {
                            throw th;
                        }
                        httpURLConnection.disconnect();
                        throw th;
                    }
                } catch (Exception e7) {
                    e = e7;
                    httpURLConnection = null;
                } catch (Throwable th5) {
                    th = th5;
                    httpURLConnection = null;
                }
            } else {
                z2 = z6;
            }
            if (url2 == null || s.bqv) {
                s.brn--;
                try {
                    s.bro.flush();
                    return;
                } catch (Exception e8) {
                    return;
                }
            } else {
                z5 = z;
                z6 = z2;
                str3 = str;
            }
        }
    }
}
