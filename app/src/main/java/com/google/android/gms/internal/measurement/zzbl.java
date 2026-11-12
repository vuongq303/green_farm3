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

    private final String zzd(Context context) {
        // Restored from play-services-analytics-impl:15.0.0 bytecode; see docs/BYTECODE_RECOVERY.md.
        bf.hW("ClientId should be loaded from worker thread");
        FileInputStream input = null;
        try {
            input = context.openFileInput("gaClientId");
            byte[] data = new byte[36];
            int count = input.read(data, 0, 36);
            if (input.available() > 0) {
                zzt("clientId file seems corrupted, deleting it.");
                input.close();
                context.deleteFile("gaClientId");
                return null;
            }
            if (count < 14) {
                zzt("clientId file is empty, deleting it.");
                input.close();
                context.deleteFile("gaClientId");
                return null;
            }
            input.close();
            String clientId = new String(data, 0, count);
            zza("Read client id from disk", clientId);
            return clientId;
        } catch (FileNotFoundException e) {
            return null;
        } catch (IOException e) {
            zze("Error reading client id file, deleting it", e);
            context.deleteFile("gaClientId");
            return null;
        } finally {
            if (input != null) {
                try {
                    input.close();
                } catch (IOException e) {
                    zze("Failed to close client id reading stream", e);
                }
            }
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
