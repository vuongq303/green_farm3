package com.google.android.gms.internal.measurement;

import android.text.TextUtils;
import com.google.android.gms.ads.identifier.AdvertisingIdClient;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.math.BigInteger;
import java.security.MessageDigest;
import java.util.Locale;

/* JADX INFO: loaded from: classes.dex */
public final class zzah extends zzar {
    private static boolean zzuu;
    private AdvertisingIdClient.Info zzuv;
    private final zzcz zzuw;
    private String zzux;
    private boolean zzuy;
    private final Object zzuz;

    zzah(zzat zzatVar) {
        super(zzatVar);
        this.zzuy = false;
        this.zzuz = new Object();
        this.zzuw = new zzcz(zzatVar.zzbt());
    }

    private final boolean zza(AdvertisingIdClient.Info info, AdvertisingIdClient.Info info2) {
        String strZzdo;
        String id = info2 == null ? null : info2.getId();
        if (TextUtils.isEmpty(id)) {
            return true;
        }
        String strZzdn = zzcc().zzdn();
        synchronized (this.zzuz) {
            if (!this.zzuy) {
                this.zzux = zzbm();
                this.zzuy = true;
            } else if (TextUtils.isEmpty(this.zzux)) {
                String id2 = info != null ? info.getId() : null;
                if (id2 == null) {
                    String strValueOf = String.valueOf(id);
                    String strValueOf2 = String.valueOf(strZzdn);
                    return zzp(strValueOf2.length() != 0 ? strValueOf.concat(strValueOf2) : new String(strValueOf));
                }
                String strValueOf3 = String.valueOf(id2);
                String strValueOf4 = String.valueOf(strZzdn);
                this.zzux = zzo(strValueOf4.length() != 0 ? strValueOf3.concat(strValueOf4) : new String(strValueOf3));
            }
            String strValueOf5 = String.valueOf(id);
            String strValueOf6 = String.valueOf(strZzdn);
            String strZzo = zzo(strValueOf6.length() != 0 ? strValueOf5.concat(strValueOf6) : new String(strValueOf5));
            if (TextUtils.isEmpty(strZzo)) {
                return false;
            }
            if (strZzo.equals(this.zzux)) {
                return true;
            }
            if (TextUtils.isEmpty(this.zzux)) {
                strZzdo = strZzdn;
            } else {
                zzq("Resetting the client id because Advertising Id changed.");
                strZzdo = zzcc().zzdo();
                zza("New client Id", strZzdo);
            }
            String strValueOf7 = String.valueOf(id);
            String strValueOf8 = String.valueOf(strZzdo);
            return zzp(strValueOf8.length() != 0 ? strValueOf7.concat(strValueOf8) : new String(strValueOf7));
        }
    }

    private final synchronized AdvertisingIdClient.Info zzbk() {
        if (this.zzuw.zzj(1000L)) {
            this.zzuw.start();
            AdvertisingIdClient.Info infoZzbl = zzbl();
            if (zza(this.zzuv, infoZzbl)) {
                this.zzuv = infoZzbl;
            } else {
                zzu("Failed to reset client id on adid change. Not using adid");
                this.zzuv = new AdvertisingIdClient.Info("", false);
            }
        }
        return this.zzuv;
    }

    private final AdvertisingIdClient.Info zzbl() {
        try {
            return AdvertisingIdClient.getAdvertisingIdInfo(getContext());
        } catch (IllegalStateException e) {
            zzt("IllegalStateException getting Ad Id Info. If you would like to see Audience reports, please ensure that you have added '<meta-data android:name=\"com.google.android.gms.version\" android:value=\"@integer/google_play_services_version\" />' to your application manifest file. See http://goo.gl/naFqQk for details.");
            return null;
        } catch (Throwable th) {
            if (zzuu) {
                return null;
            }
            zzuu = true;
            zzd("Error getting advertiser id", th);
            return null;
        }
    }

    private final String zzbm() {
        IOException e;
        String str = null;
        try {
            FileInputStream fileInputStreamOpenFileInput = getContext().openFileInput("gaClientIdData");
            byte[] bArr = new byte[128];
            int i = fileInputStreamOpenFileInput.read(bArr, 0, 128);
            if (fileInputStreamOpenFileInput.available() > 0) {
                zzt("Hash file seems corrupted, deleting it.");
                fileInputStreamOpenFileInput.close();
                getContext().deleteFile("gaClientIdData");
            } else if (i <= 0) {
                zzq("Hash file is empty.");
                fileInputStreamOpenFileInput.close();
            } else {
                String str2 = new String(bArr, 0, i);
                try {
                    fileInputStreamOpenFileInput.close();
                    str = str2;
                } catch (FileNotFoundException e2) {
                    str = str2;
                } catch (IOException e3) {
                    e = e3;
                    str = str2;
                    zzd("Error reading Hash file, deleting it", e);
                    getContext().deleteFile("gaClientIdData");
                    return str;
                }
            }
        } catch (FileNotFoundException e4) {
        } catch (IOException e5) {
            e = e5;
        }
        return str;
    }

    private static String zzo(String str) {
        MessageDigest messageDigest = zzdd.getMessageDigest("MD5");
        if (messageDigest == null) {
            return null;
        }
        return String.format(Locale.US, "%032X", new BigInteger(1, messageDigest.digest(str.getBytes())));
    }

    private final boolean zzp(String str) {
        try {
            String strZzo = zzo(str);
            zzq("Storing hashed adid.");
            FileOutputStream fileOutputStreamOpenFileOutput = getContext().openFileOutput("gaClientIdData", 0);
            fileOutputStreamOpenFileOutput.write(strZzo.getBytes());
            fileOutputStreamOpenFileOutput.close();
            this.zzux = strZzo;
            return true;
        } catch (IOException e) {
            zze("Error creating hash file", e);
            return false;
        }
    }

    @Override // com.google.android.gms.internal.measurement.zzar
    protected final void zzac() {
    }

    public final boolean zzbc() {
        zzch();
        AdvertisingIdClient.Info infoZzbk = zzbk();
        return (infoZzbk == null || infoZzbk.isLimitAdTrackingEnabled()) ? false : true;
    }

    public final String zzbj() {
        zzch();
        AdvertisingIdClient.Info infoZzbk = zzbk();
        String id = infoZzbk != null ? infoZzbk.getId() : null;
        if (TextUtils.isEmpty(id)) {
            return null;
        }
        return id;
    }
}
