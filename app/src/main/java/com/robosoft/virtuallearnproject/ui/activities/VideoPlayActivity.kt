package com.robosoft.virtuallearnproject.ui.activities

import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.MediaController
import android.widget.Toast
import com.robosoft.virtuallearnproject.R
import com.robosoft.virtuallearnproject.databinding.ActivityLoginRegisterContainerBinding
import com.robosoft.virtuallearnproject.databinding.ActivitySplashScreenBinding
import com.robosoft.virtuallearnproject.databinding.ActivityVideoPlayBinding

class VideoPlayActivity : AppCompatActivity() {
    lateinit var binding: ActivityVideoPlayBinding
    var mediaControls: MediaController? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityVideoPlayBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.backIbtn.setOnClickListener {
            onBackPressed()
        }
        val uri = Uri.parse("https://res.cloudinary.com/dghcx4s2l/video/upload/v1667819444/video/e5uxookrb0gfchlll5iw.mp4")
        val videoView = binding.videoView

        if (mediaControls == null) {
            mediaControls = MediaController(this)
            mediaControls!!.setAnchorView(videoView)
        }
        videoView.setMediaController(mediaControls)
        videoView.setVideoURI(uri)
        videoView.requestFocus()
        videoView.start()

        binding.videoView.setOnCompletionListener {
            Toast.makeText(this,"Thanks for watching", Toast.LENGTH_SHORT).show()
        }

        binding.videoView.setOnErrorListener { _, _, _ ->
            Toast.makeText(this, "An Error Occurred " +
                    "While Playing Video !!!", Toast.LENGTH_LONG).show()
            false
        }
    }
}