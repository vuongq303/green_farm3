import java.util.*;public class OriginalGameplayOracle { static int aCY=9,aCZ=-1413816336; static int ih(int i) {
        return ((i << aCY) | (i >> (32 - aCY))) ^ aCZ;
    }
static int ii(int i) {
        int i2 = aCZ ^ i;
        return (i2 << (32 - aCY)) | (i2 >> aCY);
    }
static long w(long j) {
        return ((j << aCY) | (j >> (64 - aCY))) ^ ((long) aCZ);
    }
static long x(long j) {
        long j2 = ((long) aCZ) ^ j;
        return (j2 << (64 - aCY)) | (j2 >> aCY);
    }
public static int d(byte[] bArr, int i, int i2) {
        int i3 = i + 1;
        bArr[i] = (byte) (i2 & 255);
        int i4 = i3 + 1;
        bArr[i3] = (byte) ((i2 >>> 8) & 255);
        int i5 = i4 + 1;
        bArr[i4] = (byte) ((i2 >>> 16) & 255);
        int i6 = i5 + 1;
        bArr[i5] = (byte) ((i2 >>> 24) & 255);
        return i6;
    }
public static int a(byte[] bArr, int i, long j) {
        int i2 = i + 1;
        bArr[i] = (byte) (j & 255);
        int i3 = i2 + 1;
        bArr[i2] = (byte) ((j >>> 8) & 255);
        int i4 = i3 + 1;
        bArr[i3] = (byte) ((j >>> 16) & 255);
        int i5 = i4 + 1;
        bArr[i4] = (byte) ((j >>> 24) & 255);
        int i6 = i5 + 1;
        bArr[i5] = (byte) ((j >>> 32) & 255);
        int i7 = i6 + 1;
        bArr[i6] = (byte) ((j >>> 40) & 255);
        int i8 = i7 + 1;
        bArr[i7] = (byte) ((j >>> 48) & 255);
        int i9 = i8 + 1;
        bArr[i8] = (byte) ((j >>> 56) & 255);
        return i9;
    }
public static int l(byte[] bArr, int i) {
        int i2 = i + 1;
        int i3 = i2 + 1;
        int i4 = ((bArr[i2] & 255) << 8) | (bArr[i] & 255);
        int i5 = i3 + 1;
        int i6 = i4 | ((bArr[i3] & 255) << 16);
        int i7 = i5 + 1;
        return i6 | ((bArr[i5] & 255) << 24);
    }
public static long m(byte[] bArr, int i) {
        int i2 = i + 1;
        int i3 = i2 + 1;
        long j = ((long) (bArr[i] & 255)) | (((long) (bArr[i2] & 255)) << 8);
        int i4 = i3 + 1;
        long j2 = j | (((long) (bArr[i3] & 255)) << 16);
        int i5 = i4 + 1;
        long j3 = j2 | (((long) (bArr[i4] & 255)) << 24);
        int i6 = i5 + 1;
        long j4 = j3 | (((long) (bArr[i5] & 255)) << 32);
        int i7 = i6 + 1;
        long j5 = j4 | (((long) (bArr[i6] & 255)) << 40);
        int i8 = i7 + 1;
        long j6 = j5 | (((long) (bArr[i7] & 255)) << 48);
        int i9 = i8 + 1;
        return (((long) (bArr[i8] & 255)) << 56) | j6;
    }
public static void main(String[]args)throws Exception{
long[][]vectors={{1,0,2000,50},{1,12,0,0},{2,18,99999999,3000000},{49,249999,123456,98765},{50,0,99999999,3000000}};
System.out.print("{\"vectors\":[");for(int n=0;n<vectors.length;n++){long[]v=vectors[n];byte[]b=new byte[20];d(b,0,ih((int)v[0]));d(b,4,ih((int)v[1]));a(b,8,w(v[2]));d(b,16,ih((int)v[3]));if(ii(l(b,0))!=v[0]||ii(l(b,4))!=v[1]||x(m(b,8))!=v[2]||ii(l(b,16))!=v[3])throw new AssertionError("roundtrip");if(n>0)System.out.print(",");System.out.print("{\"level\":"+v[0]+",\"experience\":"+v[1]+",\"coins\":\""+v[2]+"\",\"cash\":"+v[3]+",\"encodedHex\":\""+java.util.HexFormat.of().formatHex(b)+"\"}");}
byte[]input=System.in.readAllBytes();if(input.length==0){System.out.println("],\"knownNormalBackup\":null}");return;}byte[]save=Base64.getDecoder().decode(new String(input).trim());int prefix=5+l(save,1),pos=prefix+27+4*23+304*4;System.out.println("],\"knownNormalBackup\":{\"offsetFromPrefixEnd\":"+(pos-prefix)+",\"level\":"+ii(l(save,pos))+",\"experience\":"+ii(l(save,pos+4))+",\"coins\":\""+x(m(save,pos+8))+"\",\"cash\":"+ii(l(save,pos+16))+",\"tutorial\":"+save[prefix+4]+"}}");}}
