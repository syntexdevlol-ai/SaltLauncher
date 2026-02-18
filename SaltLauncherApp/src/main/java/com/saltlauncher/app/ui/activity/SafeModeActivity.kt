package com.saltlauncher.app.ui.activity

import android.os.Bundle
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import com.saltlauncher.app.R

/**
 * Safe mode screen that shows startup errors instead of closing immediately.
 */
class SafeModeActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_safe_mode)

        val trace = intent.getStringExtra(EXTRA_STACKTRACE) ?: "No stacktrace provided."
        val message = intent.getStringExtra(EXTRA_MESSAGE)
            ?: getString(R.string.generic_error)

        findViewById<TextView>(R.id.safe_mode_title).text = message
        findViewById<TextView>(R.id.safe_mode_trace).text = trace
    }

    companion object {
        const val EXTRA_STACKTRACE = "safe_mode_stacktrace"
        const val EXTRA_MESSAGE = "safe_mode_message"
    }
}
