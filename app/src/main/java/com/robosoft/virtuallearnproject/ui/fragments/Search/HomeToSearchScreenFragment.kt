package com.robosoft.virtuallearnproject.ui.fragments.Search

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
import com.robosoft.virtuallearnproject.adapter.ApplyFilterAdapter
import com.robosoft.virtuallearnproject.adapter.SearchSuggestionRecyclerAdapter
import com.robosoft.virtuallearnproject.databinding.FragmentSearchSuggestionVideosListBinding
import com.robosoft.virtuallearnproject.dataclass.filters.ApplyFilterRequest
import com.robosoft.virtuallearnproject.dataclass.home.choicecourse.ChoiceCourseRequest
import com.robosoft.virtuallearnproject.dataclass.home.choicecourse.ChoiceCourseResponse
import com.robosoft.virtuallearnproject.dataclass.home.choicecourse.ChoiceCourseResponseItem
import com.robosoft.virtuallearnproject.network.home.HomeApi
import com.robosoft.virtuallearnproject.network.home.HomeApiService
import com.robosoft.virtuallearnproject.network.loginregister.ServiceBuilderObject
import com.robosoft.virtuallearnproject.ui.fragments.categories.IndividualCategorieOnClickDetailScreenFragment
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class HomeToSearchScreenFragment : Fragment() {
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

    private lateinit var SearchSuggestionVideosListBinding: FragmentSearchSuggestionVideosListBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        SearchSuggestionVideosListBinding =
            FragmentSearchSuggestionVideosListBinding.inflate(inflater, container, false)

        SearchSuggestionVideosListBinding.searchviewEt.setOnClickListener {
        }

        SearchSuggestionVideosListBinding.searchbackBtn.setOnClickListener {
            activity?.supportFragmentManager?.popBackStack()
        }
        SearchSuggestionVideosListBinding.itsoftwareSelectBtn.setOnClickListener {
            startIndividualCategory("IT & Software")
        }

        SearchSuggestionVideosListBinding.businessSelectBtn.setOnClickListener {
            startIndividualCategory("Business")
        }
        SearchSuggestionVideosListBinding.designSelectBtn.setOnClickListener {
            startIndividualCategory("Design")
        }

        SearchSuggestionVideosListBinding.healthfitnessSelectBtn.setOnClickListener {
            startIndividualCategory("Health & Fitness")
        }

        SearchSuggestionVideosListBinding.marketingSelectBtn.setOnClickListener {
            startIndividualCategory("Marketing")
        }

        SearchSuggestionVideosListBinding.musicSelectBtn.setOnClickListener {
            startIndividualCategory("Music")
        }

        SearchSuggestionVideosListBinding.teachingSelectBtn.setOnClickListener {
            startIndividualCategory("Teaching")
        }

        SearchSuggestionVideosListBinding.webdevelopmentSelectBtn.setOnClickListener {
            startIndividualCategory("Web Development")
        }

        SearchSuggestionVideosListBinding.searchFilterBtn.setOnClickListener {
            val dialog = context?.let { BottomSheetDialog(it) }
            val view = layoutInflater.inflate(R.layout.fragment_search_bottomsheet, null)
            val btnClose = view.findViewById<ImageButton>(R.id.searchfilterclose_btn)
            btnClose.setOnClickListener {
                dialog?.dismiss()
            }

            // position 0
            val design = view.findViewById<LinearLayout>(R.id.design_layout)
            design.setOnClickListener {
                updateSelectedCategory(0, design)
            }
            // position 1
            val webDevelopment = view.findViewById<LinearLayout>(R.id.webdevelopment_layout)
            webDevelopment.setOnClickListener {
                updateSelectedCategory(1, webDevelopment)
//                webDevelopment.background =
//                    resources.getDrawable(R.drawable.search_categories_onclick_frame_box)
            }
            // position 2
            val business = view.findViewById<LinearLayout>(R.id.business_layout)
            business.setOnClickListener {
                updateSelectedCategory(2, business)
//                business.background =
//                    resources.getDrawable(R.drawable.search_categories_onclick_frame_box)
            }
            // position 3
            val healthFitness = view.findViewById<LinearLayout>(R.id.healthfitness_layout)
            healthFitness.setOnClickListener {
                updateSelectedCategory(3, healthFitness)
//                healthFitness.background =
//                    resources.getDrawable(R.drawable.search_categories_onclick_frame_box)
            }
            // position 4
            val music = view.findViewById<LinearLayout>(R.id.music_layout)
            music.setOnClickListener {
                updateSelectedCategory(4, music)
//                music.background =
//                    resources.getDrawable(R.drawable.search_categories_onclick_frame_box)
            }
            // position 5
            val teaching = view.findViewById<LinearLayout>(R.id.teaching_layout)
            teaching.setOnClickListener {
                updateSelectedCategory(5, teaching)
//                teaching.background =
//                    resources.getDrawable(R.drawable.search_categories_onclick_frame_box)
            }
            // position 6
            val itSoftware = view.findViewById<LinearLayout>(R.id.itsoftware_layout)
            itSoftware.setOnClickListener {
                updateSelectedCategory(6, itSoftware)
//                itSoftwate.background =
//                    resources.getDrawable(R.drawable.search_categories_onclick_frame_box)
            }
            // position 7
            val marketing = view.findViewById<LinearLayout>(R.id.marketing_layout)
            marketing.setOnClickListener {
                updateSelectedCategory(7, marketing)
//                marketing.background =
//                    resources.getDrawable(R.drawable.search_categories_onclick_frame_box)
            }

            // position 0
            val one_two_tv = view.findViewById<TextView>(R.id.one_two_tv)
            one_two_tv.setOnClickListener {
//                one_two_tv.background =
//                    resources.getDrawable(R.drawable.search_categories_onclick_frame_box)
                updateSelectedChapter(0, one_two_tv)
            }
            // position 1
            val two_three_tv = view.findViewById<TextView>(R.id.two_three_tv)
            two_three_tv.setOnClickListener {
//                two_three_tv.background =
//                    resources.getDrawable(R.drawable.search_categories_onclick_frame_box)
                updateSelectedChapter(1, two_three_tv)
            }
            // position 2
            val three_four_tv = view.findViewById<TextView>(R.id.three_four_tv)
            three_four_tv.setOnClickListener {
//                three_four_tv.background =
//                    resources.getDrawable(R.drawable.search_categories_onclick_frame_box)
                updateSelectedChapter(2, three_four_tv)
            }
            // position 3
            val four_five_tv = view.findViewById<TextView>(R.id.four_five_tv)
            four_five_tv.setOnClickListener {
//                four_five_tv.background =
//                    resources.getDrawable(R.drawable.search_categories_onclick_frame_box)
                updateSelectedChapter(3, four_five_tv)

            }
            // position 4
            val five_tv = view.findViewById<TextView>(R.id.five_tv)
            five_tv.setOnClickListener {
//                five_tv.background =
//                    resources.getDrawable(R.drawable.search_categories_onclick_frame_box)
                updateSelectedChapter(4, five_tv)
            }

            val applyFilter = view.findViewById<Button>(R.id.applyfilter_btn)
            applyFilter.setOnClickListener {
                val selectedCategory = mutableListOf<String?>()
                val selectedChapterCount = mutableListOf<List<Int?>?>()
                dialog?.dismiss()
                SearchSuggestionVideosListBinding.SuggestionLayout.visibility = View.INVISIBLE
                SearchSuggestionVideosListBinding.recycleView.visibility = View.VISIBLE

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
                                SearchSuggestionVideosListBinding.recycleView.layoutManager =
                                    LinearLayoutManager(activity?.applicationContext)
                                SearchSuggestionVideosListBinding.recycleView.adapter =
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
                                SearchSuggestionVideosListBinding.SuggestionLayout.visibility =
                                    View.VISIBLE
                                SearchSuggestionVideosListBinding.resultNotFoundLayout?.visibility =
                                    View.VISIBLE
                                SearchSuggestionVideosListBinding.searchCategoriesLayout?.visibility =
                                    View.VISIBLE
                                SearchSuggestionVideosListBinding.recycleView.visibility =
                                    View.INVISIBLE
                            }
                        }
                    }
                }
            }
            val clearAll = view.findViewById<Button>(R.id.clearall_btn)
            clearAll.setOnClickListener {
                design.background =
                    resources.getDrawable(R.drawable.search_categories_frame_box)
                webDevelopment.background =
                    resources.getDrawable(R.drawable.search_categories_frame_box)
                business.background =
                    resources.getDrawable(R.drawable.search_categories_frame_box)
                healthFitness.background =
                    resources.getDrawable(R.drawable.search_categories_frame_box)
                music.background = resources.getDrawable(R.drawable.search_categories_frame_box)
                teaching.background =
                    resources.getDrawable(R.drawable.search_categories_frame_box)
                itSoftware.background =
                    resources.getDrawable(R.drawable.search_categories_frame_box)
                marketing.background =
                    resources.getDrawable(R.drawable.search_categories_frame_box)
                one_two_tv.background =
                    resources.getDrawable(R.drawable.search_categories_frame_box)
                two_three_tv.background =
                    resources.getDrawable(R.drawable.search_categories_frame_box)
                three_four_tv.background =
                    resources.getDrawable(R.drawable.search_categories_frame_box)
                four_five_tv.background =
                    resources.getDrawable(R.drawable.search_categories_frame_box)
                five_tv.background = resources.getDrawable(R.drawable.search_categories_frame_box)
            }
            dialog?.setCancelable(false)
            dialog?.setContentView(view)
            dialog?.show()
        }

        SearchSuggestionVideosListBinding.searchviewEt.addTextChangedListener(object :
            TextWatcher {
            override fun afterTextChanged(s: Editable?) {
            }

            override fun beforeTextChanged(
                s: CharSequence?,
                start: Int,
                count: Int,
                after: Int
            ) {
            }

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                SearchSuggestionVideosListBinding.SuggestionLayout.visibility = View.INVISIBLE
                SearchSuggestionVideosListBinding.recycleView.visibility = View.VISIBLE
                s?.toString()?.trim()?.let {
                    if (isAdded) {
                        GlobalScope.launch {
                            getsearchcources(it)
                        }
                    }
                }

            }
        })

        return SearchSuggestionVideosListBinding.root
    }

    private suspend fun getsearchcources(key: String) {
        Log.d("key", key)
        val accessToken = SharedPreferenceManager(activity?.applicationContext!!).getAccessToken()
        val data = ChoiceCourseRequest("All", "seeAll")
        var searchResult: MutableList<ChoiceCourseResponseItem> = mutableListOf()

        val homeApi = ServiceBuilderObject.buildService(HomeApi::class.java)
        homeApi.choiceYourCourse(accessToken!!, data).enqueue(object :
            Callback<ChoiceCourseResponse> {
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
                        if (isAdded) {
                            SearchSuggestionVideosListBinding.recycleView.layoutManager =
                                LinearLayoutManager(SearchSuggestionVideosListBinding.root.context)
                            SearchSuggestionVideosListBinding.recycleView.adapter =
                                SearchSuggestionRecyclerAdapter(
                                    activity?.applicationContext!!,
                                    searchResult
                                )
                        }

                    } else {
                        Log.d("not found", searchResult.toString())
                        SearchSuggestionVideosListBinding.SuggestionLayout.visibility = View.VISIBLE
                        SearchSuggestionVideosListBinding.resultNotFoundLayout?.visibility =
                            View.VISIBLE
                        SearchSuggestionVideosListBinding.searchCategoriesLayout?.visibility =
                            View.VISIBLE
                        SearchSuggestionVideosListBinding.topSearchLayout?.visibility = View.GONE
                        SearchSuggestionVideosListBinding.recycleView.visibility = View.GONE
                    }

                }
            }

            override fun onFailure(call: Call<ChoiceCourseResponse>, t: Throwable) {

            }

        })
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

