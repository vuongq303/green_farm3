package com.quanhoang.greenfarm3.S800x480

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.content.IntentFilter
import android.os.Build

/** The retained BaseActivity broadcasts this command to close other game activities. */
object ActivityExitBroadcast {
    @JvmStatic
    fun register(context: Context, receiver: BroadcastReceiver, filter: IntentFilter): Intent? {
        if (Build.VERSION.SDK_INT >= 33) {
            return context.registerReceiver(receiver, filter, Context.RECEIVER_NOT_EXPORTED)
        }
        // The signature permission also protects the receiver on Android 4.1–12.
        return context.registerReceiver(receiver, filter, context.packageName + ".permission.INTERNAL_BROADCAST", null)
    }

    @JvmStatic
    fun send(context: Context, intent: Intent) {
        context.sendBroadcast(intent.setPackage(context.packageName))
    }
}
