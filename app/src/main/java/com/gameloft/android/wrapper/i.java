package com.gameloft.android.wrapper;

import android.app.AlertDialog;
import android.view.WindowManager;
import android.widget.TextView;
import com.gameloft.android.ANMP.GloftGF2F.R;

/* JADX INFO: compiled from: GamePermission.java */
/* JADX INFO: loaded from: classes.dex */
class i implements Runnable {
    final /* synthetic */ String[] bfB;
    final /* synthetic */ GamePermission bfC;

    i(GamePermission gamePermission, String[] strArr) {
        this.bfC = gamePermission;
        this.bfB = strArr;
    }

    @Override // java.lang.Runnable
    public void run() {
        String str = "";
        String[] strArrB = this.bfC.b(GamePermission.bfw, 1);
        String string = GamePermission.bex.getString(GamePermission.bfz[GamePermission.DG][24]);
        String str2 = "Show redirect dialog with language:" + GamePermission.bfy[GamePermission.DG];
        TextView textView = new TextView(GamePermission.bex);
        textView.setBackgroundColor(-7829368);
        textView.setPadding(10, 10, 10, 10);
        textView.setGravity(17);
        textView.setTextColor(-1);
        textView.setTextSize(20.0f);
        if (strArrB != null) {
            textView.setText(GamePermission.bex.getString(GamePermission.bfz[GamePermission.DG][4]));
            AlertDialog alertDialogCreate = new AlertDialog.Builder(GamePermission.bex).setPositiveButton(GamePermission.bex.getString(GamePermission.bfz[GamePermission.DG][0]), new k(this)).setNegativeButton(GamePermission.bex.getString(GamePermission.bfz[GamePermission.DG][1]), new j(this)).setIcon(GamePermission.bex.getDrawable(R.drawable.ic_launcher)).setCustomTitle(textView).setMessage("" + GamePermission.bex.getString(GamePermission.bfz[GamePermission.DG][11])).create();
            alertDialogCreate.setCancelable(false);
            alertDialogCreate.setCanceledOnTouchOutside(false);
            alertDialogCreate.setOnKeyListener(new l(this, string));
            if (d.bfm != 0 || d.bfn != 0) {
                WindowManager.LayoutParams layoutParams = new WindowManager.LayoutParams();
                alertDialogCreate.getWindow().setGravity(17);
                layoutParams.copyFrom(alertDialogCreate.getWindow().getAttributes());
                layoutParams.width = -1;
                layoutParams.height = -1;
                if (d.bfm > 0) {
                    layoutParams.x = d.bfm / 2;
                } else if (d.bfn > 0) {
                    layoutParams.y = d.bfn / 2;
                }
                alertDialogCreate.getWindow().setAttributes(layoutParams);
            }
            alertDialogCreate.show();
            return;
        }
        if (au.biP) {
            str = "" + GamePermission.bex.getString(GamePermission.bfz[GamePermission.DG][36]);
        } else {
            for (int i = 0; i < this.bfB.length; i++) {
                if (this.bfB[i].equals("android.permission.READ_PHONE_STATE")) {
                    str = str + GamePermission.bex.getString(GamePermission.bfz[GamePermission.DG][12]);
                }
                if (this.bfB[i].equals("android.permission.WRITE_EXTERNAL_STORAGE")) {
                    str = str + GamePermission.bex.getString(GamePermission.bfz[GamePermission.DG][13]);
                }
                if (this.bfB[i].equals("android.permission.ACCESS_COARSE_LOCATION")) {
                    str = str + GamePermission.bex.getString(GamePermission.bfz[GamePermission.DG][14]);
                }
                if (this.bfB[i].equals("android.permission.SEND_SMS")) {
                    str = str + GamePermission.bex.getString(GamePermission.bfz[GamePermission.DG][15]);
                }
                if (this.bfB[i].equals("android.permission.GET_ACCOUNTS")) {
                    str = str + GamePermission.bex.getString(GamePermission.bfz[GamePermission.DG][16]);
                }
                if (this.bfB[i].equals("android.permission.CAMERA")) {
                    str = str + GamePermission.bex.getString(GamePermission.bfz[GamePermission.DG][33]);
                }
                str = str + "\n\n";
            }
        }
        textView.setText(GamePermission.bex.getString(GamePermission.bfz[GamePermission.DG][30]));
        AlertDialog alertDialogCreate2 = new AlertDialog.Builder(GamePermission.bex).setPositiveButton(GamePermission.bex.getString(GamePermission.bfz[GamePermission.DG][0]), new n(this)).setNegativeButton(GamePermission.bex.getString(GamePermission.bfz[GamePermission.DG][1]), new m(this)).setIcon(GamePermission.bex.getDrawable(R.drawable.ic_launcher)).setCustomTitle(textView).setMessage(str + GamePermission.bex.getString(GamePermission.bfz[GamePermission.DG][17])).create();
        alertDialogCreate2.setCancelable(false);
        alertDialogCreate2.setCanceledOnTouchOutside(false);
        alertDialogCreate2.setOnKeyListener(new o(this, string));
        if (d.bfm != 0 || d.bfn != 0) {
            WindowManager.LayoutParams layoutParams2 = new WindowManager.LayoutParams();
            alertDialogCreate2.getWindow().setGravity(17);
            layoutParams2.copyFrom(alertDialogCreate2.getWindow().getAttributes());
            layoutParams2.width = -1;
            layoutParams2.height = -1;
            if (d.bfm > 0) {
                layoutParams2.x = d.bfm / 2;
            } else if (d.bfn > 0) {
                layoutParams2.y = d.bfn / 2;
            }
            alertDialogCreate2.getWindow().setAttributes(layoutParams2);
        }
        alertDialogCreate2.show();
    }
}
