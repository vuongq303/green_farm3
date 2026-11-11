package javax.microedition.b;

import com.gameloft.android.wrapper.au;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Vector;
import java.util.zip.CRC32;
import javax.microedition.midlet.MIDlet;

/* JADX INFO: compiled from: RecordStore.java */
/* JADX INFO: loaded from: classes.dex */
public class b {
    public static int cpe = com.gameloft.android.wrapper.d.bfg;
    private static Vector cpf = new Vector();
    private String cpa;
    private int cpb;
    private int cpc = 0;
    private int cpd = 0;

    private b() {
        this.cpa = null;
        this.cpb = 0;
        this.cpa = new String("game2.sav");
        try {
            FileInputStream fileInputStreamHc = hc(this.cpa);
            this.cpb = (byte) fileInputStreamHc.read();
            fileInputStreamHc.close();
        } catch (Exception e) {
            try {
                FileOutputStream fileOutputStreamJj = jj(this.cpa);
                fileOutputStreamJj.write(0);
                this.cpb = 0;
                fileOutputStreamJj.close();
            } catch (Exception e2) {
                if (au.bhL) {
                }
            }
            if (au.bhL) {
            }
        }
    }

    public static void akV() {
        try {
            FileInputStream fileInputStreamHc = hc("Demo_RecordAllsNames");
            int iAvailable = fileInputStreamHc.available();
            cpf.removeAllElements();
            int i = 0;
            while (i < iAvailable) {
                byte[] bArr = new byte[2];
                fileInputStreamHc.read(bArr, 0, 2);
                int i2 = (bArr[1] << 8) | bArr[0];
                byte[] bArr2 = new byte[i2];
                fileInputStreamHc.read(bArr2, 0, i2);
                cpf.addElement(new String(bArr2, 0, i2));
                i += i2 + 2;
            }
            fileInputStreamHc.close();
        } catch (Exception e) {
            String str = "Record err: " + e.getMessage();
        }
        int size = cpf.size();
        for (int i3 = 0; i3 < size; i3++) {
            try {
                ji((String) cpf.elementAt(i3));
            } catch (e e2) {
            }
        }
        for (int i4 = 0; i4 < size; i4++) {
            try {
                ji((String) cpf.elementAt(i4));
            } catch (e e3) {
            }
        }
        try {
            deleteFile("Demo_RecordAllsNames");
        } catch (Exception e4) {
        }
    }

    private b(String str, boolean z) throws e {
        boolean z2;
        boolean z3;
        boolean z4 = false;
        this.cpa = null;
        this.cpb = 0;
        String strReplace = str.replace('/', 'S');
        this.cpa = new String(strReplace);
        if (MIDlet.coj && !MIDlet.cok) {
            int size = cpf.size();
            int i = 0;
            while (true) {
                if (i >= size) {
                    z3 = false;
                    break;
                }
                if (this.cpa.equals((String) cpf.elementAt(i))) {
                    z3 = true;
                    break;
                } else {
                    try {
                        i++;
                    } catch (Exception e) {
                        String str2 = "Record err: " + e.getMessage();
                    }
                }
                String str3 = "Record err: " + e.getMessage();
            }
            if (!z3) {
                cpf.addElement(this.cpa);
                FileOutputStream fileOutputStreamJj = jj("Demo_RecordAllsNames");
                int size2 = cpf.size();
                for (int i2 = 0; i2 < size2; i2++) {
                    String str4 = (String) cpf.elementAt(i2);
                    int length = str4.length();
                    fileOutputStreamJj.write(new byte[]{(byte) (length & 255), (byte) ((length >> 8) & 255)}, 0, 2);
                    byte[] bytes = str4.getBytes();
                    fileOutputStreamJj.write(bytes, 0, bytes.length);
                }
                fileOutputStreamJj.close();
            }
        }
        try {
            FileInputStream fileInputStreamHc = hc(this.cpa);
            this.cpb = (byte) fileInputStreamHc.read();
            fileInputStreamHc.close();
        } catch (Exception e2) {
            try {
                if (z) {
                    FileOutputStream fileOutputStreamJj2 = jj(this.cpa);
                    fileOutputStreamJj2.write(0);
                    this.cpb = 0;
                    fileOutputStreamJj2.close();
                    z2 = false;
                } else {
                    if (au.bhL) {
                        String str5 = "RMS doesnt exist and will not be created. try openRecordstore(" + strReplace + ",   true  ) to force creation";
                    }
                    z2 = true;
                }
                z4 = z2;
            } catch (Exception e3) {
                if (au.bhL) {
                }
            }
            if (au.bhL) {
            }
        }
        if (z4) {
            throw new e();
        }
    }

    public static void ji(String str) {
        b bVarT = t(str, false);
        if (bVarT != null) {
            try {
                bVarT.akY();
                for (int i = 0; i < bVarT.cpb; i++) {
                    try {
                        bVarT.ph(i + 1);
                    } catch (Exception e) {
                        if (au.bhL) {
                            String str2 = "error deleting record " + (i + 1) + " - " + e.toString();
                        }
                    }
                }
            } catch (Exception e2) {
                if (au.bhL) {
                    String str3 = "error deleting recordStore - " + e2.toString();
                }
            }
            bVarT.akW();
            try {
                if (!deleteFile(bVarT.cpa)) {
                    throw new c("Can't delete file " + bVarT.cpa);
                }
            } catch (Exception e3) {
                throw new c("Can't delete file " + bVarT.cpa);
            }
        }
    }

    public static b t(String str, boolean z) {
        try {
            return new b(str, z);
        } catch (e e) {
            if (au.bhL) {
            }
            throw new e();
        }
    }

    public void akW() {
    }

    public int akX() throws f {
        if (MIDlet.coj && MIDlet.cok) {
            return 0;
        }
        try {
            akY();
            return this.cpb;
        } catch (Exception e) {
            throw new f(e.toString());
        }
    }

    public int n(byte[] bArr, int i, int i2) {
        if (MIDlet.coj && MIDlet.cok) {
            return 0;
        }
        try {
            akY();
            try {
                FileOutputStream fileOutputStreamJj = jj(this.cpa);
                this.cpc = this.cpb + 1;
                fileOutputStreamJj.write(this.cpb + 1);
                fileOutputStreamJj.close();
                b(this.cpc, bArr, i, i2);
            } catch (Exception e) {
                if (au.bhL) {
                    String str = "addRecord FAILED err:" + e.toString();
                }
                if (e.toString().contains("ENOSPC")) {
                    throw new d();
                }
            }
            this.cpb = this.cpc;
            return this.cpc;
        } catch (Exception e2) {
            return au.bhL ? 0 : 0;
        }
    }

    /* JADX WARN: Bottom block not found for handler: all -> 0x016d */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    private void b(int r7, byte[] r8, int r9, int r10) throws java.io.IOException {
        /*
            Method dump skipped, instruction units count: 374
            To view this dump change 'Code comments level' option to 'DEBUG'
        */
        throw new UnsupportedOperationException("Method not decompiled: javax.microedition.b.b.b(int, byte[], int, int):void");
    }

    private void akY() throws IOException {
        FileInputStream fileInputStreamHc = hc(this.cpa);
        this.cpb = 0;
        int i = 1;
        while (true) {
            if (jk(this.cpa + "_" + i)) {
                this.cpb++;
            } else if (!jk(this.cpa + "_" + i + "_CRC1")) {
                break;
            } else {
                this.cpb++;
            }
            i++;
        }
        if (this.cpb < 0) {
            this.cpb = 0;
        }
        fileInputStreamHc.close();
    }

    public void ph(int i) throws c {
        try {
            int iPk = pk(i);
            for (int i2 = 1; i2 <= iPk; i2++) {
                if (jk(this.cpa + "_" + i + "_saved" + i2)) {
                    deleteFile(this.cpa + "_" + i + "_saved" + i2);
                }
                if (jk(this.cpa + "_" + i + "_CRC" + i2)) {
                    deleteFile(this.cpa + "_" + i + "_CRC" + i2);
                }
            }
            if (!deleteFile(this.cpa + "_" + i)) {
                throw new c("Can't delete file " + this.cpa + "_" + i);
            }
        } catch (Exception e) {
            throw new c("Can't delete file " + this.cpa + "_" + i);
        }
    }

    public byte[] pi(int i) {
        int iPk = pk(i);
        int i2 = 1;
        int i3 = iPk;
        while (i2 <= iPk && i3 > 0) {
            if (jk(this.cpa + "_" + i + "_saved" + i3)) {
                try {
                    FileInputStream fileInputStreamHc = hc(this.cpa + "_" + i + "_saved" + i3);
                    CRC32 crc32 = new CRC32();
                    this.cpc = (byte) fileInputStreamHc.read();
                    this.cpd = a(fileInputStreamHc);
                    byte[] bArr = new byte[this.cpd];
                    fileInputStreamHc.read(bArr, 0, this.cpd);
                    fileInputStreamHc.close();
                    crc32.update(bArr);
                    FileInputStream fileInputStreamHc2 = hc(this.cpa + "_" + i + "_CRC" + i3);
                    byte[] bArr2 = new byte[8];
                    fileInputStreamHc2.read(bArr2, 0, 8);
                    fileInputStreamHc2.close();
                    byte[] bArrS = S(crc32.getValue());
                    boolean z = true;
                    for (int i4 = 0; i4 < bArr2.length; i4++) {
                        if (bArr2[i4] != bArrS[i4]) {
                            z = false;
                        }
                    }
                    if (z) {
                        aE(this.cpa + "_" + i + "_saved" + i3, this.cpa + "_" + i);
                        pl(i);
                        return bArr;
                    }
                    String str = " err(battery) from file recordName:" + this.cpa + " recordToGet:" + i + "_saved" + i3;
                } catch (Exception e) {
                    String str2 = " err(battery) from file recordName:" + this.cpa + " recordToGet:" + i + "_saved" + i3 + " err:" + e.toString();
                }
            }
            int i5 = iPk - i2;
            i2++;
            i3 = i5;
        }
        pl(i);
        try {
            FileInputStream fileInputStreamHc3 = hc(this.cpa + "_" + i);
            this.cpc = (byte) fileInputStreamHc3.read();
            this.cpd = a(fileInputStreamHc3);
            byte[] bArr3 = new byte[this.cpd];
            fileInputStreamHc3.read(bArr3, 0, this.cpd);
            fileInputStreamHc3.close();
            return bArr3;
        } catch (Exception e2) {
            if (au.bhL) {
            }
            return null;
        }
    }

    private int a(FileInputStream fileInputStream) {
        byte[] bArr = new byte[4];
        for (int i = 0; i < bArr.length; i++) {
            bArr[i] = (byte) fileInputStream.read();
        }
        return ((bArr[0] & 255) << 24) | ((bArr[1] & 255) << 16) | ((bArr[2] & 255) << 8) | (bArr[3] & 255);
    }

    /* JADX WARN: Code duplicated, block: B:25:0x004b  */
    /* JADX WARN: Code duplicated, block: B:34:? A[RETURN, SYNTHETIC] */
    public void c(int i, byte[] bArr, int i2, int i3) {
        try {
            try {
                akY();
                if (this.cpb <= 0) {
                    if (au.bhL) {
                        return;
                    } else {
                        return;
                    }
                }
                try {
                    FileOutputStream fileOutputStreamJj = jj(this.cpa);
                    fileOutputStreamJj.write(this.cpb);
                    fileOutputStreamJj.close();
                    b(i, bArr, i2, i3);
                    return;
                } catch (Exception e) {
                    if (e.toString().contains("ENOSPC")) {
                        throw new d();
                    }
                    return;
                }
            } catch (Exception e2) {
                if (au.bhL) {
                    return;
                } else {
                    return;
                }
            }
        } catch (Exception e3) {
            if (au.bhL) {
            }
        }
        if (au.bhL) {
        }
    }

    private byte[] S(long j) {
        return new byte[]{(byte) ((j >> 56) & 255), (byte) ((j >> 48) & 255), (byte) ((j >> 40) & 255), (byte) ((j >> 32) & 255), (byte) ((j >> 24) & 255), (byte) ((j >> 16) & 255), (byte) ((j >> 8) & 255), (byte) (j & 255)};
    }

    private void a(String str, byte[] bArr, int i) {
        try {
            FileOutputStream fileOutputStreamJj = jj(str);
            CRC32 crc32 = new CRC32();
            crc32.update(bArr, 0, i);
            fileOutputStreamJj.write(S(crc32.getValue()));
            fileOutputStreamJj.close();
        } catch (Exception e) {
            String str2 = "RECORDSTORE error CRC:recordName:" + this.cpa;
        }
    }

    private int pj(int i) {
        try {
            byte[] bArr = new byte[8];
            int iPk = pk(i);
            while (iPk > 0) {
                if (jk(this.cpa + "_" + i + "_CRC" + iPk) && jk(this.cpa + "_" + i + "_saved" + iPk)) {
                    FileInputStream fileInputStreamHc = hc(this.cpa + "_" + i + "_CRC" + iPk);
                    int i2 = fileInputStreamHc.read(bArr, 0, 8);
                    fileInputStreamHc.close();
                    if (i2 == 8) {
                        break;
                    }
                }
                iPk--;
            }
            int i3 = iPk + 1;
            if (i3 >= 10000) {
                return 10000;
            }
            return i3;
        } catch (Exception e) {
            return 1;
        }
    }

    private int pk(int i) {
        int i2 = 1;
        if (jk(this.cpa + "_" + i + "_CRC10000")) {
            return 10000;
        }
        int i3 = 10000;
        while (i2 < i3) {
            if (!jk(this.cpa + "_" + i + "_CRC" + ((i3 + i2) / 2))) {
                i3 = (i3 + i2) / 2;
            } else {
                int i4 = (i3 + i2) / 2;
                if (jk(this.cpa + "_" + i + "_CRC" + i4) && !jk(this.cpa + "_" + i + "_CRC" + (i4 + 1))) {
                    return i4;
                }
                i2 = i4;
            }
        }
        return 0;
    }

    private void pl(int i) {
        int iPk = pk(i);
        for (int i2 = 1; i2 <= iPk; i2++) {
            try {
                if (jk(this.cpa + "_" + i + "_CRC" + i2)) {
                    deleteFile(this.cpa + "_" + i + "_CRC" + i2);
                }
            } catch (Exception e) {
            }
        }
        for (int i3 = 1; i3 <= iPk; i3++) {
            try {
                if (jk(this.cpa + "_" + i + "_saved" + i3)) {
                    deleteFile(this.cpa + "_" + i + "_saved" + i3);
                }
            } catch (Exception e2) {
                return;
            }
        }
    }

    private void aE(String str, String str2) {
        try {
            FileInputStream fileInputStreamHc = hc(str);
            FileOutputStream fileOutputStreamJj = jj(str2);
            while (true) {
                int iAvailable = fileInputStreamHc.available();
                if (iAvailable != 0) {
                    byte[] bArr = new byte[iAvailable];
                    fileInputStreamHc.read(bArr);
                    fileOutputStreamJj.write(bArr);
                } else {
                    fileOutputStreamJj.close();
                    fileInputStreamHc.close();
                    return;
                }
            }
        } catch (Exception e) {
            String str3 = "RECORDSORE: CopyFileException " + e.toString();
        }
    }

    public static FileInputStream hc(String str) {
        if (com.gameloft.android.wrapper.d.bfb) {
            try {
                return new FileInputStream(new File(au.getContext().getExternalFilesDir(null), str));
            } catch (IOException e) {
                return null;
            }
        }
        try {
            return au.getContext().openFileInput(str);
        } catch (FileNotFoundException e2) {
            return null;
        }
    }

    public static FileOutputStream jj(String str) {
        if (com.gameloft.android.wrapper.d.bfb) {
            try {
                return new FileOutputStream(new File(au.getContext().getExternalFilesDir(null), str));
            } catch (IOException e) {
                return null;
            }
        }
        try {
            return au.getContext().openFileOutput(str, 0);
        } catch (FileNotFoundException e2) {
            return null;
        }
    }

    public static boolean jk(String str) {
        if (com.gameloft.android.wrapper.d.bfb) {
            File file = new File(au.getContext().getExternalFilesDir(null), str);
            if (file != null) {
                return file.exists();
            }
            return false;
        }
        return au.getContext().getFileStreamPath(str).exists();
    }

    public static boolean deleteFile(String str) {
        if (com.gameloft.android.wrapper.d.bfb) {
            File file = new File(au.getContext().getExternalFilesDir(null), str);
            if (file != null) {
                return file.delete();
            }
            return false;
        }
        return au.getContext().getFileStreamPath(str).delete();
    }
}
