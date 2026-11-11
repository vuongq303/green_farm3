package javax.microedition.a;

/* JADX INFO: compiled from: Connector.java */
/* JADX INFO: loaded from: classes.dex */
public class c {
    public static a jd(String str) {
        if ("android://sensorinfo_JSR256_acceleration".equals(str)) {
            return javax.microedition.c.b.akZ();
        }
        if (str.startsWith("sms:") || str.startsWith("sms:".toUpperCase())) {
            return new javax.a.a.b(str);
        }
        if (str.startsWith("http://")) {
            return new f(str, 3);
        }
        if (str.startsWith("https://")) {
            return new h(str, 3);
        }
        if (str.startsWith("socket://")) {
            return new n(str, 3);
        }
        return null;
    }

    public static a V(String str, int i) {
        return null;
    }
}
