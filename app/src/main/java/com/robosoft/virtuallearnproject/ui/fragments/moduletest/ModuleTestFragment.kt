package com.robosoft.virtuallearnproject.ui.fragments.moduletest

import android.app.AlertDialog
import android.content.Intent
import android.os.Bundle
import android.os.CountDownTimer
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.viewpager2.widget.ViewPager2
import com.robosoft.virtuallearnproject.R
import com.robosoft.virtuallearnproject.SharedPreferenceManager
import com.robosoft.virtuallearnproject.adapter.TestAdapter
import com.robosoft.virtuallearnproject.communicator.Communicator
import com.robosoft.virtuallearnproject.databinding.FragmentModuleTestBinding
import com.robosoft.virtuallearnproject.dataclass.test.SubmitTestBody
import com.robosoft.virtuallearnproject.dataclass.test.TestBodyData
import com.robosoft.virtuallearnproject.network.test.TestApiService
import java.util.concurrent.TimeUnit


class ModuleTestFragment() : Fragment(), Communicator {

    private val testApi = TestApiService()
    private lateinit var timer: CountDownTimer
    private lateinit var moduleTestBinding: FragmentModuleTestBinding
    private var answer = listOf<Int>()
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Inflate the layout for this fragment
        moduleTestBinding = FragmentModuleTestBinding.inflate(inflater, container, false)
        var pageCount = 0

        val bundle = arguments
        val courseId = bundle?.getString("courseId")
        val testID = bundle?.getString("testID")
        val testNo = bundle?.getString("testNo")
        val chapterNo = bundle?.getString("chapterNo")
        val chapterTitle = bundle?.getString("chapterTitle")
        val courseTitle = bundle?.getString("courseTitle")
        val chapterId = bundle?.getString("chapterId")

        moduleTestBinding.testNo.text = testNo
        moduleTestBinding.chapterTv.text = "Chapter $chapterNo"
        moduleTestBinding.chapterTitleTv.text = chapterTitle


        moduleTestBinding.closeIv.setOnClickListener {
            val builder = AlertDialog.Builder(requireActivity(),R.style.CustomAlertDialog)
                .create()
            val view = layoutInflater.inflate(R.layout.custom_alertbox_exit_test,null)
            val  exitButton = view.findViewById<Button>(R.id.exit_tv)
            val  cancelButton = view.findViewById<Button>(R.id.cancel_tv)
            builder.setView(view)
            cancelButton.setOnClickListener {
                builder.dismiss()
            }
            exitButton.setOnClickListener {
                requireActivity().onBackPressed()
            }
            builder.setCanceledOnTouchOutside(false)
            builder.show()
        }

        val time = 121000
        timer = object : CountDownTimer(time.toLong(), 1000) {
            override fun onTick(remaining: Long) {
                val seconds = TimeUnit.MILLISECONDS.toSeconds(remaining)
                moduleTestBinding.remainingTime.text = ("$seconds sec")
            }
            override fun onFinish() {
                onStop()
                val builder = AlertDialog.Builder(requireActivity(),R.style.CustomAlertDialog)
                    .create()
                val view = layoutInflater.inflate(R.layout.custom_alertbox_timeout,null)
                val  exitButton = view.findViewById<Button>(R.id.ok_tv)
                builder.setView(view)
                exitButton.setOnClickListener {
                    requireActivity().onBackPressed()
                }
                builder.setCanceledOnTouchOutside(false)
                builder.show()
            }
        }

        val accessToken = SharedPreferenceManager(requireContext()).getAccessToken()!!
        val data = TestBodyData(courseId.toString(), testID.toString())
        testApi.displayTest(
            accessToken,
            data
        ) { it ->
            if (it != null) {
                Log.d("response", it.toString())
                val adapter = TestAdapter(it, this@ModuleTestFragment, activity?.applicationContext)

                moduleTestBinding.viewPager2.isUserInputEnabled = false
                moduleTestBinding.viewPager2.adapter = adapter

                moduleTestBinding.viewPager2.registerOnPageChangeCallback(object :
                    ViewPager2.OnPageChangeCallback() {
                    override fun onPageSelected(position: Int) {
                        if (position != 0) {
                         moduleTestBinding.leftBtn.visibility = View.VISIBLE
                        }

                        var endPage = it.totalQuestions - 1

                        if (position == endPage) {
                            moduleTestBinding.submitBtn.visibility = View.VISIBLE
                        } else {
                            moduleTestBinding.submitBtn.visibility = View.INVISIBLE
                        }
                    }

                    override fun onPageScrollStateChanged(state: Int) {

                    }

                    override fun onPageScrolled(
                        position: Int,
                        positionOffset: Float,
                        positionOffsetPixels: Int
                    ) {

                    }
                })

                moduleTestBinding.rightBtn.setOnClickListener {
                     if(answer.size <= pageCount){
                        Toast.makeText(
                            activity?.applicationContext,
                            "Please select an option",
                            Toast.LENGTH_SHORT
                        ).show()
                    }
                    else {
                        moduleTestBinding.viewPager2.setCurrentItem(++pageCount, true)
                    }
                }

                moduleTestBinding.leftBtn.setOnClickListener {
                    moduleTestBinding.viewPager2.setCurrentItem(--pageCount, true)
                }

                moduleTestBinding.submitBtn.setOnClickListener {

                    if (answer.isEmpty()){
                        Toast.makeText(
                            activity?.applicationContext,
                            "Please select option for all the questions",
                            Toast.LENGTH_SHORT
                        ).show()
                    } else if (answer.size < pageCount) {
                        Toast.makeText(
                            activity?.applicationContext,
                            "Please select option for all the questions",
                            Toast.LENGTH_SHORT
                        ).show()
                    } else {
                        val testData = SubmitTestBody(
                            answer,
                            chapterId!!,
                            chapterNo?.toInt()!!,
                            chapterTitle!!,
                            courseId!!,
                            testID!!
                        )
                        Log.d("Submit test body", testData.toString())
                        Log.d("Selected options", answer.toString())
                        testApi.submitTest(accessToken, testData)
                        {
                            if (it != null) {
                                Log.d("submit response", it.toString())
                                val testMsg = "Test false"
                                if(it.message.length != testMsg.length){
                                    val testBundle = Bundle()
                                    testBundle.putString("testID",testID)
                                    testBundle.putString("chapterNo", chapterNo)
                                    testBundle.putString("chapterTitle", chapterTitle)
                                    testBundle.putString("courseTitle", courseTitle)
                                    val testCompleted = TestCompletedFragment()
                                    testCompleted.arguments = bundle
                                    if (savedInstanceState == null) {
                                        activity?.supportFragmentManager?.beginTransaction()
                                            ?.replace(
                                                com.robosoft.virtuallearnproject.R.id.fragment_container_video,
                                                testCompleted
                                            )?.addToBackStack(null)
                                            ?.commit()
                                    }
                                }
                                else {
                                    Log.d("test message","retry the test")

                                    //fail part
                                        activity?.supportFragmentManager?.beginTransaction()
                                            ?.replace(
                                                com.robosoft.virtuallearnproject.R.id.fragment_container_video,
                                                TestFailedFragment()
                                            )?.addToBackStack(null)
                                            ?.commit()
                                }
                            } else {
                                Log.d("submit response", "Test Not Submitted ")
                            }
                        }
                    }
                }
            } else {
                Log.d("response", "something went wrong")
            }
        }
        return moduleTestBinding.root
    }

    override fun getOptions(answerArray: MutableList<Int>?) {
        answer = answerArray!!
        Log.d("answer size",answer.size.toString())
    }

    override fun onStart() {
        super.onStart()
        timer.start()
    }

    override fun onStop() {
        super.onStop()
        timer.cancel()
    }
}
