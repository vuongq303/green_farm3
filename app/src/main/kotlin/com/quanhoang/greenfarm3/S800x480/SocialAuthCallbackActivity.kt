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
            startActivity(Intent(this, Start::class.java).addFlags(
                Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TOP or Intent.FLAG_ACTIVITY_SINGLE_TOP))
        }
        finish()
    }
}
