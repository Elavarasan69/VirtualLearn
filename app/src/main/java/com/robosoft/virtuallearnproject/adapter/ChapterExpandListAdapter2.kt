package com.robosoft.virtuallearnproject.adapter

import android.content.Context
import android.content.Intent
import android.text.format.DateUtils
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.BaseExpandableListAdapter
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.core.content.ContextCompat
import com.robosoft.virtuallearnproject.R
import com.robosoft.virtuallearnproject.SharedPreferenceManager
import com.robosoft.virtuallearnproject.dataclass.course.Chapter
import com.robosoft.virtuallearnproject.dataclass.course.CourseResonse
import com.robosoft.virtuallearnproject.dataclass.test.TestStatusBody
import com.robosoft.virtuallearnproject.dataclass.videodata.VideoDataRequest
import com.robosoft.virtuallearnproject.network.course.CourseApiService
import com.robosoft.virtuallearnproject.network.test.TestApiService
import com.robosoft.virtuallearnproject.ui.activities.VideoTestContainerActivity


class ChapterExpandListAdapter2(
    val courseTitle: String,
    val course: CourseResonse,
    val context: Context
) : BaseExpandableListAdapter() {
    val totalChapters = course.listOfChapters?.totalChapters?.get(0)

    override fun getGroupCount(): Int {
        return totalChapters?.chapters?.size!!
    }

    override fun getChildrenCount(position: Int): Int {
        val group = getGroup(position) as Chapter
        if (group.test == null) {
            return group.lessons?.size!!
        } else {
            return group.lessons?.size!! + 1
        }
    }

    override fun getGroup(position: Int): Any {
        val data = totalChapters?.chapters?.get(position)!!
        return data
    }

    override fun getChild(groupPosition: Int, childPosition: Int): Any {
        val groupData = getGroup(groupPosition) as Chapter
        if (childPosition == groupData.lessons?.size) {
            return groupData.test!!
        } else {
            return groupData.lessons?.get(childPosition)!!
        }

    }

    override fun getGroupId(groupPosition: Int): Long {
        return groupPosition.toLong()
    }

    override fun getChildId(groupPosition: Int, childPosition: Int): Long {
        return childPosition.toLong()
    }

    override fun hasStableIds(): Boolean {
        return false
    }

    override fun getGroupView(
        groupPosition: Int,
        isExpanded: Boolean,
        converterView: View?,
        parent: ViewGroup?
    ): View {
        var converterView = converterView
        val chapters = getGroup(groupPosition) as Chapter

        val inflater =
            context.getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater
        converterView = inflater.inflate(R.layout.chapter_group_heading, null)

        converterView?.findViewById<ImageView>(R.id.group_icon)?.isSelected = isExpanded
        val heading = converterView?.findViewById<TextView>(R.id.chapter_heading)
        heading?.text = context.getString(
            R.string.chapter_heading_str,
            groupPosition + 1,
            chapters.chapterTitle
        )
        if (course.isEnrolled != null) {
            val indicatorData = course.isEnrolled
            if (groupPosition < indicatorData.chaptersCompleted!!) {
                heading?.setTextColor(ContextCompat.getColor(context, R.color.color_1EAB0D))
            }
        }
        return converterView!!
    }

    override fun getChildView(
        groupPosition: Int,
        childPosition: Int,
        isExpanded: Boolean,
        converterView: View?,
        parent: ViewGroup?
    ): View {
        var converterView = converterView

        val group = getGroup(groupPosition) as Chapter

        //assigning the different layouts
        if (group.test == null) {
            val lesson = group.lessons?.get(childPosition)

            val inflater =
                context.getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater
            converterView = inflater.inflate(R.layout.child_chapter_item, null)

            val serialNumber = converterView?.findViewById<TextView>(R.id.content_number)
            val title = converterView?.findViewById<TextView>(R.id.content_title)
            val time = converterView?.findViewById<TextView>(R.id.content_time)
            val indicator = converterView?.findViewById<ImageView>(R.id.videocompletionindicator_iv)

            serialNumber?.text = lesson?.serialNumberOfLesson.toString()
            title?.text = lesson?.title

            val formatedTime = DateUtils.formatElapsedTime(lesson?.duration?.toLong()!!)
            time?.text = context.getString(R.string.duration, formatedTime)
        } else {
            if (childPosition == group.lessons?.size) {
                val inflater =
                    context.getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater
                converterView = inflater.inflate(R.layout.child_test_items, null)

                val questionDuration = converterView?.findViewById<TextView>(R.id.test_time)
                val testTitle = converterView.findViewById<TextView>(R.id.test_title)
                val duration = DateUtils.formatElapsedTime(group.test.totalTimeAlloted?.toLong()!!)
                questionDuration?.text = context.getString(R.string.duration, duration)
                testTitle.text = group.test.testTitle

            } else {
                val lesson = group.lessons?.get(childPosition)

                val inflater =
                    context.getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater
                converterView = inflater.inflate(R.layout.child_chapter_item, null)

                val serialNumber = converterView?.findViewById<TextView>(R.id.content_number)
                val title = converterView?.findViewById<TextView>(R.id.content_title)
                val time = converterView?.findViewById<TextView>(R.id.content_time)

                serialNumber?.text = lesson?.serialNumberOfLesson.toString()
                title?.text = lesson?.title

                val formatedTime = DateUtils.formatElapsedTime(lesson?.duration?.toLong()!!)
                time?.text = context.getString(R.string.duration, formatedTime)
            }
        }

        //assigning the indicator based on states
        if (course.isEnrolled != null) {
            val enrolledData = course.isEnrolled
            val indicator = converterView?.findViewById<ImageView>(R.id.videocompletionindicator_iv)
            val playBtn = converterView?.findViewById<ImageView>(R.id.play_btn)
            if (groupPosition < enrolledData.chaptersCompleted!!) {
//               All lessons of chapters are completed
                indicator?.setImageResource(R.drawable.icn_textfield_right)
                playBtn?.setImageResource(R.drawable.icn_lessonplay_active)

                if(childPosition == group.lessons?.size){
                  val result_layout =  converterView.findViewById<View>(R.id.test_result)
                    result_layout.visibility = View.VISIBLE

                    val test_percent = result_layout.findViewById<TextView>(R.id.test_percent)
                    val test_number = group.test?.testNumber
                    test_percent.text = course.isEnrolled.testApprovalRate?.get(test_number!!-1).toString()

                }

            } else if (groupPosition == enrolledData.chaptersCompleted) {
                //only few lessons of chapter completed
                if (childPosition < enrolledData.lessonsCompleted!!) {
                    //lessons completed in a chapter
                    indicator?.setImageResource(R.drawable.icn_textfield_right)
                    playBtn?.setImageResource(R.drawable.icn_lessonplay_active)
                } else if (childPosition == enrolledData.lessonsCompleted) {
                    //ongoing lesson/test
                    indicator?.setImageResource(R.drawable.icn_timeline_active)
//                    Check if the child is test or lesson
                    if (childPosition == group.lessons.size) {
                        //ongoing is a test
                    } else {
                        //ongoing is a lesson
                        playBtn?.setImageResource(R.drawable.icn_lessonplay_active)
                    }
                } else {
                    converterView.isEnabled = false
                    converterView.isClickable = false
                }
            } else {
                converterView.isEnabled = false
                converterView.isClickable = false

            }
        }

        //onclick listeners

        converterView.setOnClickListener {
            if (childPosition != group.lessons.size) {
                val serialNumber = group.lessons.get(childPosition)?.serialNumberOfLesson
                //getProgress
                startVideo(serialNumber!!, groupPosition, childPosition)
            } else {
                val testID = group.test?._id
                val testNo = group.test?.testTitle
                val chapterNo = group.chapterNumber.toString()
                val chapterTitle = group.chapterTitle
                val chapterID = group._id
                val apiService = TestApiService()
                val accessToken = SharedPreferenceManager(context).getAccessToken()!!
                val data = TestStatusBody(testID!!)
                apiService.getTestStatus(accessToken, data) {
                    if (it != null) {
                        val testStatus = it.message.toBoolean()
                        Log.d("test Status", testStatus.toString())
                        if (testStatus) {
                            Log.d("test Status", "Enters if")
                            val intent = Intent(context, VideoTestContainerActivity::class.java)
                            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
                            intent.putExtra("testStatus", true)
                            intent.putExtra("testID", testID)
                            intent.putExtra("chapterNo", chapterNo)
                            intent.putExtra("chapterTitle", chapterTitle)
                            intent.putExtra("courseTitle", courseTitle)
                            context.startActivity(intent)
                        } else {
                            Log.d("test Status", "Enters else")
                            val intent = Intent(context, VideoTestContainerActivity::class.java)
                            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
                            intent.putExtra("isLesson", false)
                            intent.putExtra("testStatus", false)
                            intent.putExtra("courseId", course.listOfChapters?._id)
                            intent.putExtra("courseTitle", courseTitle)
                            intent.putExtra("testID", testID)
                            intent.putExtra("testNo", testNo)
                            intent.putExtra("chapterNo", chapterNo)
                            intent.putExtra("chapterTitle", chapterTitle)
                            intent.putExtra("chapterId", chapterID)
                            context.startActivity(intent)
                        }
                    }
                    else{
                        Log.d("error","error")
                    }
                }
            }
        }

        if (course.isEnrolled != null) {
            val indicator = converterView?.findViewById<ImageView>(R.id.videocompletionindicator_iv)
            indicator?.visibility = View.VISIBLE
        } else {
            converterView.isEnabled = false
            converterView.isClickable = false
        }

        return converterView!!
    }

    override fun isChildSelectable(p0: Int, p1: Int): Boolean {
        return true
    }

    override fun getChildType(groupPosition: Int, childPosition: Int): Int {
        if (groupPosition != 0 && (childPosition == totalChapters?.chapters?.get(groupPosition)?.lessons?.size)) {
            return 1
        } else {
            return 0
        }
    }

    override fun getChildTypeCount(): Int {
        return 2
    }

    private fun startVideo(ongoingSerialNumber: Int, groupPosition: Int, childPosition: Int) {
        val apiService = CourseApiService()
        val accessToken = SharedPreferenceManager(context).getAccessToken()
        val data = VideoDataRequest(course.listOfChapters?._id, ongoingSerialNumber)
        val a = apiService.getVideoData(accessToken!!, data) {
            if (it != null) {
                if (it.message != null) {
                    Toast.makeText(context, it.message, Toast.LENGTH_SHORT).show()
                } else {
//                    val startingTime = it.watchedTime?.toInt()?.div(60)
//                    Log.d("startingTime", startingTime.toString())
                    val videoUrl =
                        course.listOfChapters?.totalChapters?.get(0)?.chapters?.get(groupPosition)?.lessons?.get(
                            childPosition
                        )?.url
                    val intent = Intent(context, VideoTestContainerActivity::class.java)
                    intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
                    intent.putExtra("isLesson", true)
                    intent.putExtra("videoUrl", videoUrl)
                    intent.putExtra("courseId", course.listOfChapters?._id)
                    intent.putExtra("videoSerialNumber", ongoingSerialNumber)
                    context.startActivity(intent)
                }
            }
        }
    }
//    private fun getTestStatus(testId: String){
//        val apiService = TestApiService()
//        val accessToken = SharedPreferenceManager(context).getAccessToken()
//        val data = getTestStatus()
//    }
}