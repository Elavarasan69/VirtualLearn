package com.robosoft.virtuallearnproject.ui.fragments.mycourse

import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ImageButton
import android.widget.LinearLayout
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.android.material.bottomsheet.BottomSheetDialog
import com.robosoft.virtuallearnproject.R
import com.robosoft.virtuallearnproject.SharedPreferenceManager
import com.robosoft.virtuallearnproject.adapter.AllCourseRecyclerAdapter
import com.robosoft.virtuallearnproject.adapter.ApplyFilterAdapter
import com.robosoft.virtuallearnproject.adapter.SearchSuggestionRecyclerAdapter
import com.robosoft.virtuallearnproject.databinding.FragmentChoiceYourCourseBinding
import com.robosoft.virtuallearnproject.dataclass.ChoiceYourCourseDataClass
import com.robosoft.virtuallearnproject.dataclass.filters.ApplyFilterRequest
import com.robosoft.virtuallearnproject.dataclass.home.choicecourse.ChoiceCourseRequest
import com.robosoft.virtuallearnproject.dataclass.home.choicecourse.ChoiceCourseResponse
import com.robosoft.virtuallearnproject.dataclass.home.choicecourse.ChoiceCourseResponseItem
import com.robosoft.virtuallearnproject.network.home.HomeApi
import com.robosoft.virtuallearnproject.network.home.HomeApiService
import com.robosoft.virtuallearnproject.network.loginregister.ServiceBuilderObject
import com.robosoft.virtuallearnproject.ui.fragments.categories.IndividualCategorieOnClickDetailScreenFragment
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import kotlin.reflect.jvm.internal.impl.utils.DFS


class ChoiceYourCourseFragment : Fragment() {
    private lateinit var ChoiceYourCourseBinding: FragmentChoiceYourCourseBinding
    var isCategorySelected =
        mutableListOf<Boolean>(false, false, false, false, false, false, false, false)
    var isChaptersSelected =
        mutableListOf<Boolean>(false, false, false, false, false, false)
    val availableCategory = listOf<String>(
        "Design",
        "Web Development",
        "Business",
        "Health & Fitness",
        "Music",
        "Teaching",
        "IT & Software",
        "Marketing",
    )
    val availableChapterCount = listOf<List<Int>>(
        listOf(1, 2),
        listOf(2, 3),
        listOf(3, 4),
        listOf(4, 5),
        listOf(5)
    )

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {

        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        ChoiceYourCourseBinding =
            FragmentChoiceYourCourseBinding.inflate(inflater, container, false)

//        val recyclerview = ChoiceYourCourseBinding.recyclrview
        Log.d("dfg", "dfg")
        ChoiceYourCourseBinding.backBtn.setOnClickListener {
          activity?.supportFragmentManager?.popBackStack()
        }

        ChoiceYourCourseBinding.itsoftware.setOnClickListener {
            startIndividualCategory("IT & Software")
        }

        ChoiceYourCourseBinding.business.setOnClickListener {
            startIndividualCategory("Business")
        }

        ChoiceYourCourseBinding.design.setOnClickListener {
            startIndividualCategory("Design")
        }

        ChoiceYourCourseBinding.healthfitness.setOnClickListener {
            startIndividualCategory("Health & Fitness")
        }

        ChoiceYourCourseBinding.marketing.setOnClickListener {
            startIndividualCategory("Marketing")
        }

        ChoiceYourCourseBinding.music.setOnClickListener {
            startIndividualCategory("Music")
        }

        ChoiceYourCourseBinding.teaching.setOnClickListener {
            startIndividualCategory("Teaching")
        }

        ChoiceYourCourseBinding.webdevelopment.setOnClickListener {
            startIndividualCategory("Web Development")
        }

        ChoiceYourCourseBinding.choiceyourcoursesearchfilterIv.setOnClickListener {
            val dialog = context?.let { BottomSheetDialog(it) }
            val view = layoutInflater.inflate(R.layout.fragment_search_bottomsheet, null)
            val btnClose = view.findViewById<ImageButton>(R.id.searchfilterclose_btn)
            btnClose.setOnClickListener {
                dialog?.dismiss()
            }
            val design = view.findViewById<LinearLayout>(R.id.design_layout)
            design.setOnClickListener {
                updateSelectedCategory(0, design)
//                design.background =
//                    resources.getDrawable(R.drawable.search_categories_onclick_frame_box)
            }
            val webDevelopment = view.findViewById<LinearLayout>(R.id.webdevelopment_layout)
            webDevelopment.setOnClickListener {
                updateSelectedCategory(1, webDevelopment)
//                webDevelopment.background =
//                    resources.getDrawable(R.drawable.search_categories_onclick_frame_box)
            }
            val business = view.findViewById<LinearLayout>(R.id.business_layout)
            business.setOnClickListener {
                updateSelectedCategory(2, business)
//                business.background =
//                    resources.getDrawable(R.drawable.search_categories_onclick_frame_box)
            }
            val healthFitness = view.findViewById<LinearLayout>(R.id.healthfitness_layout)
            healthFitness.setOnClickListener {
                updateSelectedCategory(3, healthFitness)
//                healthFitness.background =
//                    resources.getDrawable(R.drawable.search_categories_onclick_frame_box)
            }
            val music = view.findViewById<LinearLayout>(R.id.music_layout)
            music.setOnClickListener {
                updateSelectedCategory(4, music)
//                music.background =
//                    resources.getDrawable(R.drawable.search_categories_onclick_frame_box)
            }
            val teaching = view.findViewById<LinearLayout>(R.id.teaching_layout)
            teaching.setOnClickListener {
                updateSelectedCategory(5, teaching)
//                teaching.background =
//                    resources.getDrawable(R.drawable.search_categories_onclick_frame_box)
            }
            val itSoftware= view.findViewById<LinearLayout>(R.id.itsoftware_layout)
            itSoftware.setOnClickListener {
                updateSelectedCategory(6, itSoftware)
//                itSoftwa.background =
//                    resources.getDrawable(R.drawable.search_categories_onclick_frame_box)
            }
            val marketing = view.findViewById<LinearLayout>(R.id.marketing_layout)
            marketing.setOnClickListener {
                updateSelectedCategory(7, marketing)
//                marketing.background =
//                    resources.getDrawable(R.drawable.search_categories_onclick_frame_box)
            }
            val one_two = view.findViewById<TextView>(R.id.one_two_tv)
            one_two.setOnClickListener {
                updateSelectedChapter(0, one_two)
//                one_two.background =
//                    resources.getDrawable(R.drawable.search_categories_onclick_frame_box)
            }
            val two_three = view.findViewById<TextView>(R.id.two_three_tv)
            two_three.setOnClickListener {
                updateSelectedChapter(1, two_three)
//                two_three.background =
//                    resources.getDrawable(R.drawable.search_categories_onclick_frame_box)
            }
            val three_four = view.findViewById<TextView>(R.id.three_four_tv)
            three_four.setOnClickListener {
                updateSelectedChapter(2, three_four)
//                three_four.background =
//                    resources.getDrawable(R.drawable.search_categories_onclick_frame_box)
            }
            val four_five = view.findViewById<TextView>(R.id.four_five_tv)
            four_five.setOnClickListener {
                updateSelectedChapter(3, four_five)
//                four_five.background =
//                    resources.getDrawable(R.drawable.search_categories_onclick_frame_box)
            }
            val five = view.findViewById<TextView>(R.id.five_tv)
            five.setOnClickListener {
                updateSelectedChapter(4, five)
//                five.background =
//                    resources.getDrawable(R.drawable.search_categories_onclick_frame_box)

            }
            val applyFilter = view.findViewById<Button>(R.id.applyfilter_btn)
            applyFilter.setOnClickListener {
                val selectedCategory = mutableListOf<String?>()
                val selectedChapterCount = mutableListOf<List<Int?>?>()
                dialog?.dismiss()
                ChoiceYourCourseBinding.recyclrview.visibility = View.GONE

                for (i in 0..isCategorySelected.size - 1) {
                    if (isCategorySelected.get(i) == true) {
                        selectedCategory.add(availableCategory.get(i))
                    }
                }

                for (i in 0..isChaptersSelected.size - 1) {
                    if (isChaptersSelected.get(i) == true) {
                        selectedChapterCount.add(availableChapterCount.get(i))
                    }
                }

                val accessToken =
                    SharedPreferenceManager(activity?.applicationContext!!).getAccessToken()
                val filterData = ApplyFilterRequest(
                    selectedCategory,
                    selectedChapterCount
                )
                Log.d("filter data", filterData.toString())
                val apiService = HomeApiService()
                GlobalScope.launch {
                    apiService.applyFilters(accessToken!!, filterData) {
                        if (it != null) {
                            if (it.size > 0) {
                                Log.d("applyFilter", it.toString())

                                ChoiceYourCourseBinding.categoriesTv.visibility = View.GONE
                                ChoiceYourCourseBinding.choiceourcourseHorizontalview.visibility = View.GONE
                                ChoiceYourCourseBinding.allcourseTv.visibility = View.GONE
                                ChoiceYourCourseBinding.recyclrview.visibility = View.GONE
                                ChoiceYourCourseBinding.resultNotFoundLayout?.visibility = View.GONE
                                ChoiceYourCourseBinding.searchrecycleView.visibility = View.VISIBLE

                                ChoiceYourCourseBinding.searchrecycleView.layoutManager =
                                    LinearLayoutManager(activity?.applicationContext)
                                ChoiceYourCourseBinding.searchrecycleView.adapter =
                                    ApplyFilterAdapter(activity?.applicationContext!!, it)
                                isCategorySelected =
                                    mutableListOf<Boolean>(
                                        false,
                                        false,
                                        false,
                                        false,
                                        false,
                                        false,
                                        false,
                                        false
                                    )
                                isChaptersSelected =
                                    mutableListOf<Boolean>(false, false, false, false, false)

                                selectedCategory.clear()
                                selectedChapterCount.clear()
//                                if (selectedCategory.size == 1) {
//                                    SearchSuggestionVideosListBinding.searchviewEt.setText(
//                                        selectedCategory.get(0)
//                                    )
//                                }
                            } else {
                                ChoiceYourCourseBinding.categoriesTv.visibility =
                                    View.VISIBLE
                                ChoiceYourCourseBinding.choiceourcourseHorizontalview.visibility =
                                    View.VISIBLE
                                ChoiceYourCourseBinding.resultNotFoundLayout?.visibility =
                                    View.VISIBLE
                                ChoiceYourCourseBinding.allcourseTv.visibility = View.VISIBLE
                                ChoiceYourCourseBinding.recyclrview.visibility =
                                    View.VISIBLE
                                ChoiceYourCourseBinding.searchrecycleView.visibility = View.GONE

                            }
                        }
                    }
                }
            }
            val clearAll = view.findViewById<Button>(R.id.clearall_btn)
            clearAll.setOnClickListener {
                design.background = resources.getDrawable(R.drawable.search_categories_frame_box)
                webDevelopment.background =
                    resources.getDrawable(R.drawable.search_categories_frame_box)
                business.background = resources.getDrawable(R.drawable.search_categories_frame_box)
                healthFitness.background =
                    resources.getDrawable(R.drawable.search_categories_frame_box)
                music.background = resources.getDrawable(R.drawable.search_categories_frame_box)
                teaching.background = resources.getDrawable(R.drawable.search_categories_frame_box)
                itSoftware.background =
                    resources.getDrawable(R.drawable.search_categories_frame_box)
                marketing.background = resources.getDrawable(R.drawable.search_categories_frame_box)
                one_two.background = resources.getDrawable(R.drawable.search_categories_frame_box)
                two_three.background =
                    resources.getDrawable(R.drawable.search_categories_frame_box)
                three_four.background =
                    resources.getDrawable(R.drawable.search_categories_frame_box)
                four_five.background =
                    resources.getDrawable(R.drawable.search_categories_frame_box)
                five.background = resources.getDrawable(R.drawable.search_categories_frame_box)
            }
            dialog?.setCancelable(false)
            dialog?.setContentView(view)
            dialog?.show()
        }

        val data = ArrayList<ChoiceYourCourseDataClass>()
        for (i in 1..20) {
            data.add(ChoiceYourCourseDataClass(R.drawable.img_allcourse_one))
        }

        if (isAdded == true) {
            CoroutineScope(Dispatchers.IO).launch {
                getAllCoures()
            }
        }


        ChoiceYourCourseBinding.searchEt.addTextChangedListener(object : TextWatcher {
            override fun afterTextChanged(s: Editable?) {
            }

            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {
            }

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                ChoiceYourCourseBinding.choiceourcourseHorizontalview.visibility = View.INVISIBLE
                ChoiceYourCourseBinding.categoriesTv.visibility = View.INVISIBLE
                ChoiceYourCourseBinding.allcourseTv.visibility = View.INVISIBLE
                ChoiceYourCourseBinding.recyclrview.visibility = View.INVISIBLE
                ChoiceYourCourseBinding.searchrecycleView.visibility = View.VISIBLE
                s?.toString()?.trim()?.let {
                    if (isAdded) {
                        GlobalScope.launch {
                            getsearchcources(it)
                        }
                    }

                }
            }
        })

        return ChoiceYourCourseBinding.root
    }

    private suspend fun getAllCoures() {
        val accessToken = SharedPreferenceManager(requireContext()).getAccessToken()
        val choicerequestData = ChoiceCourseRequest("All", "seeAll")
        val homeService = HomeApiService()

        homeService.choiceCourse(accessToken!!, choicerequestData) {
            if (it != null) {
                Log.d("msg", it.toString())
                if (isAdded) {
                    ChoiceYourCourseBinding.recyclrview.layoutManager =
                        LinearLayoutManager(activity?.applicationContext)
                    ChoiceYourCourseBinding.recyclrview.adapter =
                        AllCourseRecyclerAdapter(
                            activity?.applicationContext!!,
                            it
                        )
                }
            } else {
                Log.d("msg", "something went wrong")
            }
        }


    }

    private fun startIndividualCategory(category: String) {
        val bundle = Bundle()
        bundle.putString("category", category)

        val individualCategory = IndividualCategorieOnClickDetailScreenFragment()
        individualCategory.arguments = bundle
        activity?.supportFragmentManager?.beginTransaction()?.replace(
            com.robosoft.virtuallearnproject.R.id.homescreenfragment_container,
            individualCategory
        )
            ?.addToBackStack(null)
            ?.commit()
    }

    private suspend fun getsearchcources(key: String) {
        Log.d("key", key)
        val accessToken = SharedPreferenceManager(activity?.applicationContext!!).getAccessToken()
        val data = ChoiceCourseRequest("All", "seeAll")
        var searchResult: MutableList<ChoiceCourseResponseItem> = mutableListOf()

        val homeApi = ServiceBuilderObject.buildService(HomeApi::class.java)
        homeApi.choiceYourCourse(accessToken!!, data)
            .enqueue(object : Callback<ChoiceCourseResponse> {
                override fun onResponse(
                    call: Call<ChoiceCourseResponse>,
                    response: Response<ChoiceCourseResponse>
                ) {
                    if (response.body() != null) {
                        Log.d("response for search", response.body().toString())
                        for (course in response.body()!!) {
                            if (course.courseTitle?.lowercase()?.startsWith(key.lowercase())!!) {
                                Log.d("item", course.toString())
                                searchResult.add(course)
                            }
                        }
                        Log.d("item", searchResult.toString())
                        if (searchResult.size > 0) {
                            ChoiceYourCourseBinding.searchrecycleView.layoutManager =
                                LinearLayoutManager(ChoiceYourCourseBinding.root.context)
                            ChoiceYourCourseBinding.searchrecycleView.adapter =
                                SearchSuggestionRecyclerAdapter(
                                    activity?.applicationContext!!,
                                    searchResult
                                )
                            ChoiceYourCourseBinding.resultNotFoundLayout?.visibility = View.GONE
                            ChoiceYourCourseBinding.choiceourcourseHorizontalview.visibility =
                                View.GONE
                            ChoiceYourCourseBinding.categoriesTv.visibility = View.GONE
                            ChoiceYourCourseBinding.allcourseTv.visibility = View.GONE
                            ChoiceYourCourseBinding.recyclrview.visibility = View.GONE
                            ChoiceYourCourseBinding.searchrecycleView.visibility = View.VISIBLE
                        } else {
                            // add no result view here
                            ChoiceYourCourseBinding.resultNotFoundLayout?.visibility = View.VISIBLE
                            ChoiceYourCourseBinding.choiceourcourseHorizontalview.visibility =
                                View.VISIBLE
                            ChoiceYourCourseBinding.categoriesTv.visibility = View.VISIBLE
                            ChoiceYourCourseBinding.allcourseTv.visibility = View.VISIBLE
                            ChoiceYourCourseBinding.recyclrview.visibility = View.VISIBLE
                            ChoiceYourCourseBinding.searchrecycleView.visibility = View.INVISIBLE

                        }
                    }
                }

                override fun onFailure(call: Call<ChoiceCourseResponse>, t: Throwable) {

                }

            })
    }

    private fun updateSelectedCategory(position: Int, category: View) {
        val isSelected = isCategorySelected[position]
        if (isSelected) {
            category.background =
                resources.getDrawable(R.drawable.search_categories_frame_box)
            isCategorySelected[position] = false
        } else {
            category.background =
                resources.getDrawable(R.drawable.search_categories_onclick_frame_box)
            isCategorySelected[position] = true
        }
    }

    private fun updateSelectedChapter(position: Int, chapterCount: View) {
        val isSelected = isChaptersSelected[position]
        if (isSelected) {
            chapterCount.background =
                resources.getDrawable(R.drawable.search_categories_frame_box)
            isChaptersSelected[position] = false
        } else {
            chapterCount.background =
                resources.getDrawable(R.drawable.search_categories_onclick_frame_box)
            isChaptersSelected[position] = true
        }
    }
}
