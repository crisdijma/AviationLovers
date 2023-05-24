package com.example.aviationlovers

import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.MediaController
import android.widget.Toast
import android.widget.VideoView

class VideoActivity : AppCompatActivity() {

    private var myVideoView: VideoView? = null
    private var myMediaController: MediaController? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_video)

        myVideoView = findViewById(R.id.myVideoView)

        setUpVideoPlay()

    }

    private fun setUpVideoPlay() {

        if(myMediaController == null){
            myMediaController = MediaController(this)
            myMediaController!!.setAnchorView(this.myVideoView)
        }

        myVideoView!!.setMediaController(myMediaController)

        myVideoView!!.setVideoURI(
            Uri.parse("android.resource://"
            + packageName + "/" + R.raw.intro)
        )

        myVideoView!!.requestFocus()

        myVideoView!!.pause()

        myVideoView!!.setOnCompletionListener {
            Toast.makeText(applicationContext, "Video Completed", Toast.LENGTH_SHORT).show()
        }

        myVideoView!!.setOnErrorListener{mp, what, extra ->
            Toast.makeText(applicationContext, "An error occured", Toast.LENGTH_SHORT).show()
            false
        }

    }

}