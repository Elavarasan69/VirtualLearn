package com.robosoft.virtuallearnproject.adapter

import android.content.Context
import android.content.Intent
import android.os.Bundle
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
import com.robosoft.virtuallearnproject.dataclass.course.Chapter
import com.robosoft.virtuallearnproject.dataclass.course.CourseResonse
import com.robosoft.virtuallearnproject.dataclass.course.Lesson
import com.robosoft.virtuallearnproject.ui.activities.IndividualCourseActivity
import com.robosoft.virtuallearnproject.ui.activities.VideoPlayActivity
import com.robosoft.virtuallearnproject.ui.activities.VideoTestContainerActivity


class ChapterExpandListAdapter1(
    val course: CourseResonse,
    val context: Context
) : BaseExpandableListAdapter() {
    val totalChapters = course.listOfChapters?.totalChapters?.get(0)

    override fun getGroupCount(): Int {
        return totalChapters?.chapters?.size!!
    }

    override fun getChildrenCount(position: Int): Int {
//        return 1
        var i = 0
        if (position == 0) {
            i = totalChapters?.chapters?.get(position)?.lessons?.size!!
        } else {
            i = totalChapters?.chapters?.get(position)?.lessons?.size!! + 1
        }
        return i
    }

    override fun getGroup(position: Int): Any {
        val data = totalChapters?.chapters?.get(position)!!
        return data
    }

    override fun getChild(groupPosition: Int, childPosition: Int): Any {
        if (groupPosition == 0) {
            val data = totalChapters?.chapters?.get(groupPosition)?.lessons?.get(childPosition)!!
            return data
        } else {
            if (childPosition < totalChapters?.chapters?.get(groupPosition)?.lessons?.size!!) {
                val data =
                    totalChapters.chapters.get(groupPosition)?.lessons?.get(childPosition)!!
                return data
            }
            return totalChapters.chapters.get(groupPosition)?.test!!
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
        if (converterView == null) {
            val inflater =
                context.getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater
            converterView = inflater.inflate(R.layout.chapter_group_heading, null)
        }
        converterView?.findViewById<ImageView>(R.id.group_icon)?.isSelected = isExpanded
        val heading = converterView?.findViewById<TextView>(R.id.chapter_heading)
        heading?.text = context.getString(R.string.chapter_heading_str, groupPosition+1, chapters.chapterTitle)
        if (course.isEnrolled != null) {
            val indicatorData = course.isEnrolled
            if (indicatorData.ongoingVideo?.chapterNo!! > groupPosition + 1) {
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

        if (groupPosition == 0) {
            Log.d("group position", groupPosition.toString() + "   " + childPosition.toString())
            val data = getChild(groupPosition, childPosition) as Lesson
            if (converterView == null) {
                val inflater =
                    context.getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater
                converterView = inflater.inflate(R.layout.child_chapter_item, null)
            }
            if (course.isEnrolled != null) {
                val indicatorData = course.isEnrolled
                val indicator =
                    converterView?.findViewById<ImageView>(R.id.videocompletionindicator_iv)
                val play_btn = converterView?.findViewById<ImageView>(R.id.play_btn)
                if (indicatorData.ongoingSerialNumber == data.serialNumberOfLesson!!) {
                    indicator?.setImageResource(R.drawable.icn_timeline_active)
                } else if (indicatorData.ongoingSerialNumber!! > data.serialNumberOfLesson) {
                    indicator?.setImageResource(R.drawable.icn_textfield_right)
                }

                if (indicatorData.ongoingSerialNumber < data.serialNumberOfLesson) {
                    converterView?.isClickable = false
                    converterView?.isEnabled = false
                } else {
                    converterView?.isClickable = true
                    play_btn?.setImageResource(R.drawable.icn_lessonplay_active)
                }
            } else{
                converterView?.isClickable = false
                converterView?.isEnabled = false
            }

            val contentTitle = converterView?.findViewById<TextView>(R.id.content_title)
            val contentDuration = converterView?.findViewById<TextView>(R.id.content_time)
            val lessonCount = converterView?.findViewById<TextView>(R.id.content_number)

            converterView?.setOnClickListener {
//                val activity = context as IndividualCourseActivity
                val intent = Intent(context, VideoTestContainerActivity::class.java)
                intent.putExtra("isLesson", true)
                intent.putExtra("videoUrl", data.url)
                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
                context.startActivity(intent)
            }

            contentTitle?.text = data.title
            contentDuration?.text = data.duration
            lessonCount?.text = data.serialNumberOfLesson.toString()
        }
        else {
            if (childPosition == totalChapters?.chapters?.get(groupPosition)?.lessons?.size!! && groupPosition > 0) {
                Log.d("child", groupPosition.toString() + "   " + childPosition.toString())
                val inflater =
                    context.getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater
                val data = getChild(groupPosition, childPosition) as String
                if (converterView == null) {
                    converterView = inflater.inflate(R.layout.child_test_items, null)
                }
                if (course.isEnrolled != null) {
                    val indicatorData = course.isEnrolled
                    val indicator =
                        converterView?.findViewById<ImageView>(R.id.videocompletionindicator_iv)

//                   if (indicatorData.ongoingSerialNumber == data.serialNumberOfLesson!!) {
//                        indicator?.setImageResource(R.drawable.icn_timeline_active)
//                    } else if (indicatorData.ongoingSerialNumber!! > data.serialNumberOfLesson) {
//                        indicator?.setImageResource(R.drawable.icn_textfield_right)
//                    }
//
//                    if (indicatorData.ongoingSerialNumber < data.serialNumberOfLesson) {
//                        converterView?.isClickable = false
//                        converterView?.isEnabled = false
//                    } else {
//                        converterView?.isClickable = true
//                        play_btn?.setImageResource(R.drawable.icn_lessonplay_active)
//                    }
                }
                else{
                    converterView?.isClickable = false
                    converterView?.isEnabled = false
                }

                converterView?.setOnClickListener {
                    val intent = Intent(context, VideoTestContainerActivity::class.java)
                    intent.putExtra("isLesson", false)
//                    intent.putExtra("videoUrl", data.url)
                    intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
                    context.startActivity(intent)
                }
//                val text = converterView?.findViewById<TextView>(R.id.test_time)
//                text?.text = data
//                return converterView!!
            }
            else {
                Log.d("1", groupPosition.toString() + "   " + childPosition.toString())
                val data = getChild(groupPosition, childPosition) as Lesson
                if (converterView == null) {
                    val inflater =
                        context.getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater
                    converterView = inflater.inflate(R.layout.child_chapter_item, null)
                }
                if (course.isEnrolled != null) {
                    val indicatorData = course.isEnrolled
                    val indicator =
                        converterView?.findViewById<ImageView>(R.id.videocompletionindicator_iv)
                    val play_btn = converterView?.findViewById<ImageView>(R.id.play_btn)
                    if (indicatorData.ongoingSerialNumber == data.serialNumberOfLesson!!) {
                        indicator?.setImageResource(R.drawable.icn_timeline_active)
                    } else if (indicatorData.ongoingSerialNumber!! > data.serialNumberOfLesson) {
                        indicator?.setImageResource(R.drawable.icn_textfield_right)
                    }

                    if (indicatorData.ongoingSerialNumber < data.serialNumberOfLesson) {
                        converterView?.isClickable = false
                        converterView?.isEnabled = false
                    } else {
                        converterView?.isClickable = true
                        play_btn?.setImageResource(R.drawable.icn_lessonplay_active)
                    }
                } else{
                    converterView?.isClickable = false
                    converterView?.isEnabled = false
                }
                val contentTitle = converterView?.findViewById<TextView>(R.id.content_title)
                val contentDuration = converterView?.findViewById<TextView>(R.id.content_time)
                val lessonCount = converterView?.findViewById<TextView>(R.id.content_number)
                contentTitle?.text = data.title
                contentDuration?.text = data.duration
                lessonCount?.text = data.serialNumberOfLesson.toString()

                converterView?.setOnClickListener {
//                val activity = context as IndividualCourseActivity
                    val intent = Intent(context.applicationContext, VideoTestContainerActivity::class.java)
                    intent.putExtra("isLesson", true)
                    intent.putExtra("videoUrl", data.url)
                    intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
                    context.startActivity(intent)

                }
            }
        }

        if (course.isEnrolled != null) {
            val indicator = converterView?.findViewById<ImageView>(R.id.videocompletionindicator_iv)
            indicator?.visibility = View.VISIBLE
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

}