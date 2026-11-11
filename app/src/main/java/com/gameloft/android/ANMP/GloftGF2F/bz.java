package com.gameloft.android.ANMP.GloftGF2F;

import android.app.Activity;
import android.content.Intent;
import com.facebook.share.internal.ShareConstants;
import com.unity3d.services.ads.adunit.AdUnitActivity;
import java.nio.ByteBuffer;

/* JADX INFO: compiled from: Zoro.java */
/* JADX INFO: loaded from: classes.dex */
public class bz {
    private static Activity bex;
    private static bz bev = null;
    public static int[] bez = {R.string.OFFLINE_BAN_MESSAGE_EN, R.string.OFFLINE_BAN_MESSAGE_DE, R.string.OFFLINE_BAN_MESSAGE_FR, R.string.OFFLINE_BAN_MESSAGE_IT, R.string.OFFLINE_BAN_MESSAGE_SP, R.string.OFFLINE_BAN_MESSAGE_BR, R.string.OFFLINE_BAN_MESSAGE_PT, R.string.OFFLINE_BAN_MESSAGE_JP, R.string.OFFLINE_BAN_MESSAGE_CN, R.string.OFFLINE_BAN_MESSAGE_KR, R.string.OFFLINE_BAN_MESSAGE_RU, R.string.OFFLINE_BAN_MESSAGE_TR, R.string.OFFLINE_BAN_MESSAGE_PL, R.string.OFFLINE_BAN_MESSAGE_EN, R.string.OFFLINE_BAN_MESSAGE_EN, R.string.OFFLINE_BAN_MESSAGE_TH, R.string.OFFLINE_BAN_MESSAGE_VI, R.string.OFFLINE_BAN_MESSAGE_AR, R.string.OFFLINE_BAN_MESSAGE_ID, R.string.OFFLINE_BAN_MESSAGE_ZT, R.string.OFFLINE_BAN_MESSAGE_SC, R.string.OFFLINE_BAN_MESSAGE_TC, R.string.OFFLINE_BAN_MESSAGE_ES_LATAM};
    private boolean EU = false;
    private byte[] EV = {82, 69, 86, 73, 83, 73, 79, 78, 45, 50, 52, 48, 56};
    private byte[] bew = {99, 108, 97, 115, 115, 101, 115, 46, 100, 101, 120};
    private byte[] EW = {0, 2, 38, -68};
    private byte[] EX = {46, 100, 101, 120};
    public final int Fa = 276856832;
    private boolean bey = false;

    public static bz On() {
        if (bev == null) {
            bev = new bz();
            bex = (Activity) com.gameloft.android.wrapper.au.getContext();
        }
        return bev;
    }

    private bz() {
    }

    public void init() {
        if (!this.EU) {
            this.EU = true;
            new Thread(new ca(this)).start();
        }
    }

    public boolean Oo() {
        return this.bey;
    }

    public int a(byte[] bArr) {
        ByteBuffer byteBufferAllocate = ByteBuffer.allocate(4);
        for (int i = 0; i < bArr.length; i++) {
            byteBufferAllocate.put(i, bArr[i]);
        }
        return byteBufferAllocate.getInt();
    }

    public void Op() {
        int iEt;
        String str;
        String packageName = com.gameloft.android.wrapper.au.getContext().getPackageName();
        String strA = com.gameloft.android.wrapper.au.A("save_game_language", "", packageName);
        if (com.gameloft.android.wrapper.au.A("tz_param", "", packageName).isEmpty()) {
            strA = "";
        }
        String str2 = "Game language saved: = " + strA;
        String strPk = com.gameloft.android.wrapper.au.Pk();
        String strPl = com.gameloft.android.wrapper.au.Pl();
        if (strA.isEmpty()) {
            String str3 = "Device language: " + strPk;
            String str4 = "Device country: " + strPl;
            if (strPk.equalsIgnoreCase("ja")) {
                str = "jp";
            } else if (strPk.equalsIgnoreCase("ch")) {
                str = "zh";
            } else if (strPk.equalsIgnoreCase("in")) {
                str = ShareConstants.WEB_DIALOG_PARAM_ID;
            } else if (strPk.equalsIgnoreCase("es")) {
                str = "sp";
            } else if (strPk.equalsIgnoreCase("ko")) {
                str = "kr";
            } else if (strPk.equalsIgnoreCase("ec")) {
                str = "es_latam";
            } else {
                str = (strPk.equalsIgnoreCase("pt") && com.gameloft.android.wrapper.au.Pl().equalsIgnoreCase("br")) ? "br" : strPk;
            }
            if (str.equalsIgnoreCase("zh") && !com.gameloft.android.wrapper.au.Pl().equalsIgnoreCase("tw")) {
                str = "sc";
            }
            String str5 = "Converted device language: " + str;
            iEt = com.gameloft.android.wrapper.au.et(str);
            if (iEt < 0) {
                iEt = 0;
            }
            String str6 = "Language index: " + iEt;
        } else {
            iEt = com.gameloft.android.wrapper.au.et(strA);
            String str7 = "Language index: " + iEt;
        }
        String string = com.gameloft.android.wrapper.au.getActivity().getString(bez[iEt]);
        Intent intent = new Intent(bex, (Class<?>) ActivityMessage.class);
        intent.putExtra("warning_message", string);
        intent.putExtra(AdUnitActivity.EXTRA_ORIENTATION, com.gameloft.android.wrapper.d.beG);
        bex.startActivity(intent);
    }
}
