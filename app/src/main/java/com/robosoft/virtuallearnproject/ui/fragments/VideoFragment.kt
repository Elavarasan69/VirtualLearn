package com.robosoft.virtuallearnproject.ui.fragments

import android.net.Uri
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.MediaController
import android.widget.Toast
import com.robosoft.virtuallearnproject.SharedPreferenceManager
import com.robosoft.virtuallearnproject.databinding.FragmentVideoBinding
import com.robosoft.virtuallearnproject.dataclass.updateProgress.UpdateProgressRequest
import com.robosoft.virtuallearnproject.network.course.CourseApiService

class VideoFragment : Fragment() {

    lateinit var binding: FragmentVideoBinding
    var mediaControls: MediaController? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentVideoBinding.inflate(inflater, container, false)

        val bundle = arguments
        val videoUrl = bundle?.getString("videoUrl")
        val courseId = bundle?.getString("courseId")
        val videoSerialNumber = bundle?.getInt("videoSerialNumber")

        binding.backIbtn.setOnClickListener {
            requireActivity().onBackPressed()
        }
        val url = videoUrl.toString().replaceRange(4,4,"s")
        val uri = Uri.parse(url)
        val videoView = binding.videoView

        if (mediaControls == null) {
            mediaControls = MediaController(context)
            mediaControls!!.setAnchorView(videoView)
        }
        videoView.setMediaController(mediaControls)
        videoView.setVideoURI(uri)
        videoView.requestFocus()
        videoView.start()

        binding.videoView.setOnCompletionListener {
            Toast.makeText(context,"Thanks for watching", Toast.LENGTH_SHORT).show()
            updateProgress(courseId,videoSerialNumber)
        }

        binding.videoView.setOnErrorListener { _, _, _ ->
            Toast.makeText(context, "An Error Occurred " +
                    "While Playing Video !!!", Toast.LENGTH_LONG).show()
            false
        }

        return binding.root
    }

    private fun updateProgress(courseId: String?, videoSerialNumber: Int?) {
       val accessToken = SharedPreferenceManager(activity?.applicationContext!!).getAccessToken()
        val apiService = CourseApiService()
        val data = UpdateProgressRequest(courseId,"0", "true", videoSerialNumber )
        apiService.updateProgress(accessToken!!, data){
            if(it?.message == "Lesson completed") {
                activity?.supportFragmentManager?.popBackStack()
                activity?.onBackPressed()
            }
        }
    }
}