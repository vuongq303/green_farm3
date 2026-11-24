package com.quanhoang.greenfarm3.migration

import android.app.Activity
import android.content.Intent
import android.content.pm.ShortcutInfo
import android.content.pm.ShortcutManager
import android.os.Build

/** One game launcher; Android25+ exposes save management by long-pressing that icon. */
object SaveTransferShortcuts {
    @JvmStatic fun install(activity: Activity) {
        if (Build.VERSION.SDK_INT >= 25) installOnSupportedAndroid(activity)
    }

    @android.annotation.TargetApi(25)
    private fun installOnSupportedAndroid(activity: Activity) {
        val manager = activity.getSystemService(ShortcutManager::class.java) ?: return
        val intent = Intent(activity, SaveTransferActivity::class.java).setAction(Intent.ACTION_VIEW)
        val shortcut = ShortcutInfo.Builder(activity, "greenfarm-save-transfer")
            .setShortLabel("Chuyển bản lưu")
            .setLongLabel("Xuất / nhập tiến độ Green Farm 3")
            .setIntent(intent).build()
        // Some launchers or managed profiles prohibit publishing shortcuts. The native
        // game menu remains available; such a policy never blocks game startup.
        try { manager.addDynamicShortcuts(listOf(shortcut)) }
        catch (_: IllegalStateException) { }
        catch (_: SecurityException) { }
    }
}
