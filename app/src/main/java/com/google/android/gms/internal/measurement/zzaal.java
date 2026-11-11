package com.google.android.gms.internal.measurement;

import com.facebook.R;

/* JADX INFO: loaded from: classes.dex */
final class zzaal {
    static String zza(zzyb zzybVar) {
        zzaam zzaamVar = new zzaam(zzybVar);
        StringBuilder sb = new StringBuilder(zzaamVar.size());
        for (int i = 0; i < zzaamVar.size(); i++) {
            byte bZzag = zzaamVar.zzag(i);
            switch (bZzag) {
                case 7:
                    sb.append("\\a");
                    break;
                case 8:
                    sb.append("\\b");
                    break;
                case 9:
                    sb.append("\\t");
                    break;
                case R.styleable.CardView_contentPaddingRight /* 10 */:
                    sb.append("\\n");
                    break;
                case R.styleable.CardView_contentPaddingTop /* 11 */:
                    sb.append("\\v");
                    break;
                case R.styleable.CardView_contentPaddingBottom /* 12 */:
                    sb.append("\\f");
                    break;
                case 13:
                    sb.append("\\r");
                    break;
                case 34:
                    sb.append("\\\"");
                    break;
                case 39:
                    sb.append("\\'");
                    break;
                case 92:
                    sb.append("\\\\");
                    break;
                default:
                    if (bZzag < 32 || bZzag > 126) {
                        sb.append('\\');
                        sb.append((char) (((bZzag >>> 6) & 3) + 48));
                        sb.append((char) (((bZzag >>> 3) & 7) + 48));
                        sb.append((char) ((bZzag & 7) + 48));
                    } else {
                        sb.append((char) bZzag);
                    }
                    break;
            }
        }
        return sb.toString();
    }
}
