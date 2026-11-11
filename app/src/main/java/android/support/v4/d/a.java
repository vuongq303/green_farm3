package android.support.v4.d;

import android.support.v4.e.q;
import android.util.Base64;
import java.util.List;

/* JADX INFO: compiled from: FontRequest.java */
/* JADX INFO: loaded from: classes.dex */
public final class a {
    private final String fL;
    private final String fM;
    private final String fN;
    private final List<List<byte[]>> fO;
    private final int fP = 0;
    private final String fQ;

    public a(String str, String str2, String str3, List<List<byte[]>> list) {
        this.fL = (String) q.checkNotNull(str);
        this.fM = (String) q.checkNotNull(str2);
        this.fN = (String) q.checkNotNull(str3);
        this.fO = (List) q.checkNotNull(list);
        this.fQ = this.fL + "-" + this.fM + "-" + this.fN;
    }

    public String getProviderAuthority() {
        return this.fL;
    }

    public String getProviderPackage() {
        return this.fM;
    }

    public String getQuery() {
        return this.fN;
    }

    public List<List<byte[]>> getCertificates() {
        return this.fO;
    }

    public int az() {
        return this.fP;
    }

    public String getIdentifier() {
        return this.fQ;
    }

    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("FontRequest {mProviderAuthority: " + this.fL + ", mProviderPackage: " + this.fM + ", mQuery: " + this.fN + ", mCertificates:");
        for (int i = 0; i < this.fO.size(); i++) {
            sb.append(" [");
            List<byte[]> list = this.fO.get(i);
            for (int i2 = 0; i2 < list.size(); i2++) {
                sb.append(" \"");
                sb.append(Base64.encodeToString(list.get(i2), 0));
                sb.append("\"");
            }
            sb.append(" ]");
        }
        sb.append("}");
        sb.append("mCertificatesArray: " + this.fP);
        return sb.toString();
    }
}
