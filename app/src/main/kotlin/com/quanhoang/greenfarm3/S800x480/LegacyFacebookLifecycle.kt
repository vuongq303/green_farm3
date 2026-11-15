package com.quanhoang.greenfarm3.S800x480

import android.content.Intent
import android.content.res.Configuration
import android.os.Bundle
import javax.microedition.midlet.v

/** Owns only the original Facebook SDK lifecycle; online accounts use their official browser flow. */
class LegacyFacebookLifecycle private constructor() {
    companion object {
        private fun enabled() = !LocalDevelopmentConfig.ONLINE_ACCOUNTS

        @JvmStatic fun onCreate(bundle: Bundle?) { if (enabled()) v.onCreate(bundle) }
        @JvmStatic fun onDestroy() { if (enabled()) v.onDestroy() }
        @JvmStatic fun onPause() { if (enabled()) v.onPause() }
        @JvmStatic fun onSaveInstanceState(bundle: Bundle?) { if (enabled()) v.onSaveInstanceState(bundle) }
        @JvmStatic fun onRestart() { if (enabled()) v.onRestart() }
        @JvmStatic fun onResume() { if (enabled()) v.onResume() }
        @JvmStatic fun onStart() { if (enabled()) v.onStart() }
        @JvmStatic fun onStop() { if (enabled()) v.onStop() }
        @JvmStatic fun onNewIntent(intent: Intent?) { if (enabled()) v.onNewIntent(intent) }
        @JvmStatic fun onActivityResult(requestCode: Int, resultCode: Int, intent: Intent?) {
            if (enabled()) v.onActivityResult(requestCode, resultCode, intent)
        }
        @JvmStatic fun onWindowFocusChanged(focused: Boolean) { if (enabled()) v.onWindowFocusChanged(focused) }
        @JvmStatic fun onConfigurationChanged(configuration: Configuration?) {
            if (enabled()) v.onConfigurationChanged(configuration)
        }
    }
}
