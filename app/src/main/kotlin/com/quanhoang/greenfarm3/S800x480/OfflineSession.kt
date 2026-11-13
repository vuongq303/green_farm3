package com.quanhoang.greenfarm3.S800x480

import android.widget.Toast
import javax.microedition.midlet.MIDlet

/** Explicit local test mode. This never authorizes a server account. */
object OfflineSession {
    /** Consume the original social-login action without opening its SDK. */
    @JvmStatic
    fun rejectOnlineAction(): Boolean {
        if (!OfflineTestConfig.enabled()) return false
        val activity = MIDlet.cnA
        if (activity != null && !activity.isFinishing) {
            activity.runOnUiThread {
                Toast.makeText(activity,
                    "Bản kiểm thử offline: đăng nhập và kết nối máy chủ đang tạm dừng.",
                    Toast.LENGTH_LONG).show()
            }
        }
        return true
    }
}
