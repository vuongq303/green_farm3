package com.gameloft.glads;

import android.text.TextUtils;
import com.facebook.appevents.codeless.internal.Constants;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.atomic.AtomicBoolean;

/* JADX INFO: loaded from: classes.dex */
public class GLURLConnection {
    public static final int BUFFER_SIZE = 16384;
    public static final int BUSY = 0;
    public static final int BUSY_READING_MESSAGES = 1;
    public static final int FINISHED = 4;
    public static final int HTTP_ACCEPTED = 202;
    public static final int HTTP_FOUND = 302;
    public static final int HTTP_NOT_MODIFIED = 304;
    public static final int HTTP_OK = 200;
    public static final int RECEIVED_BAD_HTTP_STATUS = 3;
    public static final int RECEIVED_CONNECTION_ERROR = 2;
    public static final int STOPPED = 5;
    private long parent;
    private String postString;
    private int state;
    private int timeoutInSeconds;
    private String url;
    private HttpURLConnection urlConnection;
    private int responseCode = -1;
    private boolean active = false;
    private byte[] buffer = new byte[BUFFER_SIZE];
    private int bufferSize = 0;
    private AtomicBoolean bufferHasData = new AtomicBoolean(false);
    private List<String> m_headers = null;

    public GLURLConnection(long j) {
        this.parent = j;
    }

    public void SetURL(String str) {
        this.url = str;
    }

    public void SetPostString(String str) {
        this.postString = str;
    }

    public void SetHeaderProperty(String str) {
        if (this.m_headers == null) {
            this.m_headers = new ArrayList();
        }
        if (str != null && !str.isEmpty()) {
            this.m_headers.add(str);
        }
    }

    public String GetResponseEtag() {
        try {
            return this.urlConnection.getHeaderField("ETag");
        } catch (Exception e) {
            return "";
        }
    }

    public String GetResponseMessage() {
        try {
            String responseMessage = this.urlConnection.getResponseMessage();
            return responseMessage == null ? "" : responseMessage;
        } catch (Exception e) {
            return "";
        }
    }

    public void SetTimeout(int i) {
        this.timeoutInSeconds = i;
    }

    public int GetState() {
        return this.state;
    }

    public int GetResponseCode() {
        return this.responseCode;
    }

    public byte[] GetBuffer() {
        return this.buffer;
    }

    public int GetBufferSize() {
        if (this.bufferHasData.get()) {
            return this.bufferSize;
        }
        return -1;
    }

    public void ClearBuffer() {
        this.bufferHasData.set(false);
        this.bufferSize = 0;
    }

    /* JADX WARN: Type inference failed for: r0v2, types: [com.gameloft.glads.GLURLConnection$1] */
    public void Start() {
        this.state = 0;
        this.active = true;
        new Thread() { // from class: com.gameloft.glads.GLURLConnection.1
            @Override // java.lang.Thread, java.lang.Runnable
            public void run() {
                try {
                    try {
                        URL url = new URL(GLURLConnection.this.url);
                        GLURLConnection.this.urlConnection = (HttpURLConnection) url.openConnection();
                        if (GLURLConnection.this.postString == null || TextUtils.isEmpty(GLURLConnection.this.postString)) {
                            GLURLConnection.this.urlConnection.setRequestMethod("GET");
                            GLURLConnection.this.urlConnection.setDoOutput(false);
                        } else {
                            GLURLConnection.this.urlConnection.setRequestMethod("POST");
                            GLURLConnection.this.urlConnection.setDoOutput(true);
                        }
                        GLURLConnection.this.urlConnection.setConnectTimeout(GLURLConnection.this.timeoutInSeconds * Constants.APP_INDEXING_SCHEDULE_INTERVAL_MS);
                        GLURLConnection.this.urlConnection.setReadTimeout(GLURLConnection.this.timeoutInSeconds * Constants.APP_INDEXING_SCHEDULE_INTERVAL_MS);
                        GLURLConnection.this.urlConnection.setDoInput(true);
                        GLURLConnection.this.urlConnection.setUseCaches(false);
                        GLURLConnection.this.urlConnection.setRequestProperty("Charset", AndroidWebView.UTF_8);
                        if (GLURLConnection.this.m_headers != null && !GLURLConnection.this.m_headers.isEmpty()) {
                            for (String str : GLURLConnection.this.m_headers) {
                                int iIndexOf = str.indexOf(":");
                                GLURLConnection.this.urlConnection.setRequestProperty(str.substring(0, iIndexOf + 1), str.substring(iIndexOf + 1).trim());
                            }
                        }
                        if (GLURLConnection.this.urlConnection.getRequestMethod().equals("POST")) {
                            OutputStream outputStream = GLURLConnection.this.urlConnection.getOutputStream();
                            outputStream.write(GLURLConnection.this.postString.getBytes());
                            outputStream.flush();
                        }
                        GLURLConnection.this.responseCode = GLURLConnection.this.urlConnection.getResponseCode();
                        if (GLURLConnection.this.responseCode != 200 && GLURLConnection.this.responseCode != 202 && GLURLConnection.this.responseCode != 304) {
                            GLURLConnection.this.state = 3;
                            GLURLConnection.this.Stop();
                            return;
                        }
                        if (GLURLConnection.this.responseCode == 304) {
                            GLURLConnection.this.state = 4;
                            GLURLConnection.this.Stop();
                            return;
                        }
                        InputStream inputStream = GLURLConnection.this.urlConnection.getInputStream();
                        while (GLURLConnection.this.active) {
                            if (GLURLConnection.this.bufferHasData.get()) {
                                try {
                                    Thread.sleep(1L);
                                } catch (Exception e) {
                                }
                            } else {
                                GLURLConnection.this.bufferSize = inputStream.read(GLURLConnection.this.buffer, 0, GLURLConnection.BUFFER_SIZE);
                                if (GLURLConnection.this.bufferSize == -1) {
                                    break;
                                } else {
                                    GLURLConnection.this.bufferHasData.set(true);
                                }
                            }
                        }
                        GLURLConnection.this.state = 4;
                        inputStream.close();
                        GLURLConnection.this.Stop();
                    } catch (Exception e2) {
                        GLURLConnection.this.state = 2;
                        GLURLConnection.this.Stop();
                    }
                } catch (Throwable th) {
                    GLURLConnection.this.Stop();
                    throw th;
                }
            }
        }.start();
    }

    public void Stop() {
        try {
            this.active = false;
            if (this.urlConnection != null) {
                this.urlConnection.disconnect();
            }
        } catch (Exception e) {
        }
    }
}
