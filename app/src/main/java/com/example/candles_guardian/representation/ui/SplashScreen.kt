package com.example.candles_guardian.representation.ui

import android.content.Intent
import android.content.SharedPreferences
import android.os.Bundle
import android.os.Handler
import android.view.WindowManager
import androidx.appcompat.app.AppCompatActivity
import com.example.candles_guardian.R
import com.example.candles_guardian.representation.ui.main_activity.MainActivity

class SplashScreen : AppCompatActivity() {
    private lateinit var mPreferences: SharedPreferences

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash_screen)
        init()
// This is used to hide the status bar and make
        // the splash screen as a full screen activity.
        window.setFlags(
            WindowManager.LayoutParams.FLAG_FULLSCREEN,
            WindowManager.LayoutParams.FLAG_FULLSCREEN
        )

        // we used the postDelayed(Runnable, time) method
        // to send a message with a delayed time.
        Handler().postDelayed({
            if (mPreferences.getString("openChildernList", "").equals("open")) {
                startActivity(Intent(this, HomeActivity::class.java))
                finish()
            } else {
                startActivity(Intent(this, MainActivity::class.java))
                finish()
            }

        }, 3000) // 3000 is the delayed time in milliseconds

    }

    private fun init() {
        mPreferences = getSharedPreferences("prefs", AppCompatActivity.MODE_PRIVATE)
    }
}