package com.example.aviationlovers

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.animation.ObjectAnimator
import android.view.animation.AlphaAnimation
import android.view.animation.Animation
import android.widget.ImageView


class UploadActivity : AppCompatActivity() {

    private lateinit var uploadImage: ImageView
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_upload)

        uploadImage = findViewById(R.id.uploadImage)

        // Create the ObjectAnimator for blinking
        val blinkAnimator = ObjectAnimator.ofFloat(uploadImage, "alpha", 0f, 1f).apply {
            duration = 2500 // Duration of each animation cycle (in milliseconds)
            repeatMode = ObjectAnimator.REVERSE // Reverse the animation
            repeatCount = ObjectAnimator.INFINITE // Repeat indefinitely
        }

        // Start the blinking animation
        blinkAnimator.start()
    }
}