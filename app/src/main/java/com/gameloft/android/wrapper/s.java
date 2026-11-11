package com.gameloft.android.wrapper;

import android.app.AlertDialog;
import android.view.WindowManager;
import android.widget.TextView;
import com.gameloft.android.ANMP.GloftGF2F.R;

/* JADX INFO: compiled from: GamePermission.java */
/* JADX INFO: loaded from: classes.dex */
class s implements Runnable {
    final /* synthetic */ String[] bfB;
    final /* synthetic */ GamePermission bfC;

    s(GamePermission gamePermission, String[] strArr) {
        this.bfC = gamePermission;
        this.bfB = strArr;
    }

    @Override // java.lang.Runnable
    public void run() {
        String str = "";
        String str2 = "Show explain dialog with language:" + GamePermission.bfy[GamePermission.DG];
        if (au.biP) {
            str = "" + GamePermission.bex.getString(GamePermission.bfz[GamePermission.DG][37]);
        } else {
            for (int i = 0; i < this.bfB.length; i++) {
                if (this.bfB[i].equals("android.permission.READ_PHONE_STATE")) {
                    str = str + GamePermission.bex.getString(GamePermission.bfz[GamePermission.DG][18]);
                }
                if (this.bfB[i].equals("android.permission.WRITE_EXTERNAL_STORAGE")) {
                    str = str + GamePermission.bex.getString(GamePermission.bfz[GamePermission.DG][19]);
                }
                if (this.bfB[i].equals("android.permission.ACCESS_COARSE_LOCATION")) {
                    str = str + GamePermission.bex.getString(GamePermission.bfz[GamePermission.DG][20]);
                }
                if (this.bfB[i].equals("android.permission.SEND_SMS")) {
                    str = str + GamePermission.bex.getString(GamePermission.bfz[GamePermission.DG][21]);
                }
                if (this.bfB[i].equals("android.permission.GET_ACCOUNTS")) {
                    str = str + GamePermission.bex.getString(GamePermission.bfz[GamePermission.DG][22]);
                }
                if (this.bfB[i].equals("android.permission.CAMERA")) {
                    str = str + GamePermission.bex.getString(GamePermission.bfz[GamePermission.DG][34]);
                }
                str = str + "\n\n";
            }
        }
        TextView textView = new TextView(GamePermission.bex);
        textView.setBackgroundColor(-7829368);
        textView.setPadding(10, 10, 10, 10);
        textView.setGravity(17);
        textView.setTextColor(-1);
        textView.setTextSize(20.0f);
        String str3 = str + GamePermission.bex.getString(GamePermission.bfz[GamePermission.DG][23]);
        String string = GamePermission.bex.getString(GamePermission.bfz[GamePermission.DG][24]);
        AlertDialog.Builder builder = new AlertDialog.Builder(GamePermission.bex);
        builder.setPositiveButton(GamePermission.bex.getString(GamePermission.bfz[GamePermission.DG][3]), new t(this));
        if (this.bfC.b(GamePermission.bfw, 1) == null) {
            textView.setText(GamePermission.bex.getString(GamePermission.bfz[GamePermission.DG][30]));
            builder.setNegativeButton(GamePermission.bex.getString(GamePermission.bfz[GamePermission.DG][1]), new u(this));
        } else {
            textView.setText(GamePermission.bex.getString(GamePermission.bfz[GamePermission.DG][4]));
            builder.setNegativeButton(GamePermission.bex.getString(GamePermission.bfz[GamePermission.DG][1]), new v(this));
        }
        builder.setIcon(GamePermission.bex.getDrawable(R.drawable.ic_launcher));
        builder.setCustomTitle(textView);
        builder.setMessage(str3);
        AlertDialog alertDialogCreate = builder.create();
        alertDialogCreate.setCancelable(false);
        alertDialogCreate.setCanceledOnTouchOutside(false);
        alertDialogCreate.setOnKeyListener(new w(this, string));
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
    }
}
