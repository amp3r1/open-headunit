package com.andrerinas.openheadunit.assistant

import android.content.Intent
import android.os.Build
import android.os.Bundle
import android.view.KeyEvent
import androidx.appcompat.app.AppCompatActivity
import com.andrerinas.openheadunit.App
import com.andrerinas.openheadunit.main.MainActivity
import com.andrerinas.openheadunit.utils.AppLog

class AssistantActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        AppLog.i("AssistantActivity", "Assistant action triggered, bringing app to front and sending KEYCODE_VOICE_ASSIST")
        try {
            val commManager = App.provide(this).commManager
            val keyCode = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                KeyEvent.KEYCODE_VOICE_ASSIST
            } else {
                231
            }
            commManager.sendKey(keyCode, true, null, "assistant-activity")
            commManager.sendKey(keyCode, false, null, "assistant-activity")
        } catch (e: Exception) {
            AppLog.e("AssistantActivity", "Failed to send KEYCODE_VOICE_ASSIST", e)
        }

        try {
            val intent = Intent(this, MainActivity::class.java).apply {
                addFlags(Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_REORDER_TO_FRONT or Intent.FLAG_ACTIVITY_SINGLE_TOP)
            }
            startActivity(intent)
        } catch (e: Exception) {
            AppLog.e("AssistantActivity", "Failed to bring MainActivity to front", e)
        }

        finish()
    }
}
