package com.robosoft.virtuallearnproject.ui.activities

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.robosoft.virtuallearnproject.databinding.ActivityVideoTestContainerBinding
import com.robosoft.virtuallearnproject.ui.fragments.VideoFragment
import com.robosoft.virtuallearnproject.ui.fragments.moduletest.ModuleTestFragment
import com.robosoft.virtuallearnproject.ui.fragments.testresult.ResultFragment

class VideoTestContainerActivity : AppCompatActivity() {
    lateinit var binding: ActivityVideoTestContainerBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityVideoTestContainerBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val intent = getIntent()
        val isLesson = intent.getBooleanExtra("isLesson", false)
        val videoUrl = intent.getStringExtra("videoUrl")
        val courseId = intent.getStringExtra("courseId")
        val videoSerialNumber = intent.getIntExtra("videoSerialNumber", 0)

        val courseTitle = intent.getStringExtra("courseTitle")
        val testId = intent.getStringExtra("testID")
        val testNo = intent.getStringExtra("testNo")
        val chapterNo = intent.getStringExtra("chapterNo")
        val chapterTitle = intent.getStringExtra("chapterTitle")
        val chapterId = intent.getStringExtra("chapterId")

        val testStatus = intent.getBooleanExtra("testStatus", false)

        if (savedInstanceState == null) {
            if (isLesson == true) {
                val bundle = Bundle()
                bundle.putString("videoUrl", videoUrl)
                bundle.putString("courseId", courseId)
                bundle.putInt("videoSerialNumber", videoSerialNumber)
                val videoFragment = VideoFragment()
                videoFragment.arguments = bundle
                supportFragmentManager.beginTransaction()
                    .replace(binding.fragmentContainerVideo.id, videoFragment).commit()
            } else {
                if (testStatus) {
                    val bundle = Bundle()
                    bundle.putString("testID", testId)
                    bundle.putString("chapterNo", chapterNo)
                    bundle.putString("chapterTitle", chapterTitle)
                    bundle.putString("courseTitle", courseTitle)
                    val result = ResultFragment()
                    result.arguments = bundle
                    supportFragmentManager.beginTransaction()
                        .replace(binding.fragmentContainerVideo.id, result).commit()
                } else {
                    val bundle = Bundle()
                    bundle.putString("courseTitle", courseTitle)
                    bundle.putString("courseId", courseId)
                    bundle.putString("testID", testId)
                    bundle.putString("testNo", testNo)
                    bundle.putString("chapterNo", chapterNo)
                    bundle.putString("chapterTitle", chapterTitle)
                    bundle.putString("chapterId", chapterId)
                    val test = ModuleTestFragment()
                    test.arguments = bundle
                    supportFragmentManager.beginTransaction()
                        .replace(binding.fragmentContainerVideo.id, test).commit()
                }
            }
        }
    }
}