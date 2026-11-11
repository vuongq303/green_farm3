package com.google.android.gms.internal.measurement;

import android.content.Context;
import com.facebook.appevents.AppEventsConstants;
import com.google.android.gms.common.internal.bf;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Locale;
import java.util.UUID;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Future;

/* JADX INFO: loaded from: classes.dex */
public final class zzbl extends zzar {
    private volatile String zztz;
    private Future<String> zzxn;

    protected zzbl(zzat zzatVar) {
        super(zzatVar);
    }

    /* JADX WARN: Multi-variable type inference failed */
    private final boolean zzb(Context context, String str) {
        boolean z = false;
        bf.id(str);
        bf.hW("ClientId should be saved from worker thread");
        FileOutputStream fileOutputStreamOpenFileOutput = 0;
        fileOutputStreamOpenFileOutput = 0;
        fileOutputStreamOpenFileOutput = 0;
        try {
            try {
                zza("Storing clientId", str);
                fileOutputStreamOpenFileOutput = context.openFileOutput("gaClientId", 0);
                fileOutputStreamOpenFileOutput.write(str.getBytes());
                if (fileOutputStreamOpenFileOutput != 0) {
                    try {
                        fileOutputStreamOpenFileOutput.close();
                    } catch (IOException e) {
                        zze("Failed to close clientId writing stream", e);
                    }
                }
                z = true;
                fileOutputStreamOpenFileOutput = fileOutputStreamOpenFileOutput;
            } catch (FileNotFoundException e2) {
                zze("Error creating clientId file", e2);
                fileOutputStreamOpenFileOutput = fileOutputStreamOpenFileOutput;
                if (fileOutputStreamOpenFileOutput != 0) {
                    try {
                        fileOutputStreamOpenFileOutput.close();
                        fileOutputStreamOpenFileOutput = fileOutputStreamOpenFileOutput;
                    } catch (IOException e3) {
                        zze("Failed to close clientId writing stream", e3);
                        fileOutputStreamOpenFileOutput = "Failed to close clientId writing stream";
                    }
                }
            } catch (IOException e4) {
                zze("Error writing to clientId file", e4);
                fileOutputStreamOpenFileOutput = fileOutputStreamOpenFileOutput;
                if (fileOutputStreamOpenFileOutput != 0) {
                    try {
                        fileOutputStreamOpenFileOutput.close();
                        fileOutputStreamOpenFileOutput = fileOutputStreamOpenFileOutput;
                    } catch (IOException e5) {
                        zze("Failed to close clientId writing stream", e5);
                        fileOutputStreamOpenFileOutput = "Failed to close clientId writing stream";
                    }
                }
            }
            return z;
        } catch (Throwable th) {
            if (fileOutputStreamOpenFileOutput != 0) {
                try {
                    fileOutputStreamOpenFileOutput.close();
                } catch (IOException e6) {
                    zze("Failed to close clientId writing stream", e6);
                }
            }
            throw th;
        }
    }

    /* JADX INFO: Removed unreachable split cross block B:70:0x0033 */
    /* JADX WARN: Code duplicated, block: B:68:0x00ab A[EXC_TOP_SPLITTER, SYNTHETIC] */
    /* JADX WARN: Multi-variable type inference failed */
    /* JADX WARN: Type inference failed for: r2v0 */
    /* JADX WARN: Type inference failed for: r2v2, types: [java.io.FileInputStream] */
    /* JADX WARN: Type inference failed for: r2v27 */
    private final String zzd(Context context) throws Throwable {
        ?? r2;
        FileInputStream fileInputStreamOpenFileInput;
        FileInputStream fileInputStream;
        Object obj;
        Object obj2;
        String str = null;
        bf.hW("ClientId should be loaded from worker thread");
        try {
            try {
                fileInputStreamOpenFileInput = context.openFileInput("gaClientId");
                try {
                    byte[] bArr = new byte[36];
                    int i = fileInputStreamOpenFileInput.read(bArr, 0, 36);
                    if (fileInputStreamOpenFileInput.available() > 0) {
                        zzt("clientId file seems corrupted, deleting it.");
                        fileInputStreamOpenFileInput.close();
                        context.deleteFile("gaClientId");
                        if (fileInputStreamOpenFileInput != null) {
                            try {
                                obj = fileInputStreamOpenFileInput;
                                fileInputStreamOpenFileInput.close();
                                obj = fileInputStreamOpenFileInput;
                            } catch (IOException e) {
                                zze("Failed to close client id reading stream", e);
                                obj = "Failed to close client id reading stream";
                            }
                        }
                    } else if (i < 14) {
                        zzt("clientId file is empty, deleting it.");
                        fileInputStreamOpenFileInput.close();
                        context.deleteFile("gaClientId");
                        if (fileInputStreamOpenFileInput != null) {
                            try {
                                obj = fileInputStreamOpenFileInput;
                                fileInputStreamOpenFileInput.close();
                                obj = fileInputStreamOpenFileInput;
                            } catch (IOException e2) {
                                zze("Failed to close client id reading stream", e2);
                                obj = "Failed to close client id reading stream";
                            }
                        }
                    } else {
                        fileInputStreamOpenFileInput.close();
                        String str2 = new String(bArr, 0, i);
                        zza("Read client id from disk", str2);
                        if (fileInputStreamOpenFileInput != null) {
                            try {
                                obj2 = fileInputStreamOpenFileInput;
                                fileInputStreamOpenFileInput.close();
                                obj2 = fileInputStreamOpenFileInput;
                            } catch (IOException e3) {
                                zze("Failed to close client id reading stream", e3);
                                obj2 = "Failed to close client id reading stream";
                            }
                        }
                        obj2 = fileInputStreamOpenFileInput;
                        str = str2;
                        obj = obj2;
                    }
                } catch (FileNotFoundException e4) {
                    fileInputStream = fileInputStreamOpenFileInput;
                    if (fileInputStream != null) {
                        try {
                            fileInputStream.close();
                        } catch (IOException e5) {
                            zze("Failed to close client id reading stream", e5);
                        }
                    }
                    return str;
                } catch (IOException e6) {
                    e = e6;
                    zze("Error reading client id file, deleting it", e);
                    context.deleteFile("gaClientId");
                    obj = fileInputStreamOpenFileInput;
                    if (fileInputStreamOpenFileInput != null) {
                        try {
                            fileInputStreamOpenFileInput.close();
                            obj = fileInputStreamOpenFileInput;
                        } catch (IOException e7) {
                            zze("Failed to close client id reading stream", e7);
                            obj = "Failed to close client id reading stream";
                        }
                    }
                }
            } catch (FileNotFoundException e8) {
                fileInputStream = null;
            } catch (IOException e9) {
                e = e9;
                fileInputStreamOpenFileInput = null;
            } catch (Throwable th) {
                th = th;
                r2 = 0;
                if (r2 != 0) {
                    try {
                        r2.close();
                    } catch (IOException e10) {
                        zze("Failed to close client id reading stream", e10);
                    }
                }
                throw th;
            }
            obj = fileInputStreamOpenFileInput;
            obj = fileInputStreamOpenFileInput;
            return str;
        } catch (Throwable th2) {
            th = th2;
            r2 = obj;
            if (r2 != 0) {
                r2.close();
            }
            throw th;
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public final String zzdq() {
        String lowerCase = UUID.randomUUID().toString().toLowerCase(Locale.US);
        try {
            return !zzb(zzbw().getContext(), lowerCase) ? AppEventsConstants.EVENT_PARAM_VALUE_NO : lowerCase;
        } catch (Exception e) {
            zze("Error saving clientId file", e);
            return AppEventsConstants.EVENT_PARAM_VALUE_NO;
        }
    }

    @Override // com.google.android.gms.internal.measurement.zzar
    protected final void zzac() {
    }

    public final String zzdn() {
        String str;
        zzch();
        synchronized (this) {
            if (this.zztz == null) {
                this.zzxn = zzbw().a(new zzbm(this));
            }
            if (this.zzxn != null) {
                try {
                    this.zztz = this.zzxn.get();
                } catch (InterruptedException e) {
                    zzd("ClientId loading or generation was interrupted", e);
                    this.zztz = AppEventsConstants.EVENT_PARAM_VALUE_NO;
                } catch (ExecutionException e2) {
                    zze("Failed to load or generate client id", e2);
                    this.zztz = AppEventsConstants.EVENT_PARAM_VALUE_NO;
                }
                if (this.zztz == null) {
                    this.zztz = AppEventsConstants.EVENT_PARAM_VALUE_NO;
                }
                zza("Loaded clientId", this.zztz);
                this.zzxn = null;
                str = this.zztz;
            } else {
                str = this.zztz;
            }
            throw th;
        }
        return str;
    }

    final String zzdo() {
        synchronized (this) {
            this.zztz = null;
            this.zzxn = zzbw().a(new zzbn(this));
        }
        return zzdn();
    }

    final String zzdp() throws Throwable {
        String strZzd = zzd(zzbw().getContext());
        return strZzd == null ? zzdq() : strZzd;
    }
}
