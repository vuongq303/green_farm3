package com.quanhoang.greenfarm3.S800x480

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import com.quanhoang.greenfarm3.Start

/** Transparent handoff only; the game's renderer displays success, errors and loading. */
class SocialAuthCallbackActivity : Activity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        receive(intent)
    }

    override fun onNewIntent(newIntent: Intent) {
        super.onNewIntent(newIntent)
        setIntent(newIntent)
        receive(newIntent)
    }

    private fun receive(callback: Intent?) {
        if (SocialAccountController.recordCallback(this, callback?.data?.toString())) {
            // The launcher can sit below GloftGF2F in the existing task. CLEAR_TOP
            // on Start would destroy the live gameplay Activity above it.
            val target = SocialAccountController.hostActivity()?.javaClass ?: Start::class.java
            startActivity(Intent(this, target).addFlags(
                Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_REORDER_TO_FRONT or Intent.FLAG_ACTIVITY_SINGLE_TOP))
        }
        finish()
    }
}
