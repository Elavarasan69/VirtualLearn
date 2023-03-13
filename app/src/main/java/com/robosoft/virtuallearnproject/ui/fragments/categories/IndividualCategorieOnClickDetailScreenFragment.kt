package com.robosoft.virtuallearnproject.ui.fragments.categories

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.view.ViewCompat
import androidx.recyclerview.widget.LinearLayoutManager
import com.robosoft.virtuallearnproject.SharedPreferenceManager
import com.robosoft.virtuallearnproject.adapter.*
import com.robosoft.virtuallearnproject.databinding.FragmentIndividualCategorieOnClickDetailScreenBinding
import com.robosoft.virtuallearnproject.dataclass.home.searchbycategory.SearchByCategoryAndKeywordsRequest
import com.robosoft.virtuallearnproject.dataclass.home.subcategories.SubCategorieRequest
import com.robosoft.virtuallearnproject.dataclass.home.topcourse.TopCourseRequest
import com.robosoft.virtuallearnproject.network.home.HomeApiService
import com.robosoft.virtuallearnproject.ui.fragments.Search.HomeToSearchScreenFragment
import kotlinx.coroutines.*

class IndividualCategorieOnClickDetailScreenFragment : Fragment() {
    private lateinit var individualCategorieDetailsBinding: FragmentIndividualCategorieOnClickDetailScreenBinding
    //private lateinit var subCategorieGV: GridView

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        individualCategorieDetailsBinding = FragmentIndividualCategorieOnClickDetailScreenBinding.inflate(inflater, container, false)


        val bundle = arguments
        val category = bundle?.getString("category")

        individualCategorieDetailsBinding.categoryTitle.text = category

        if(isAdded == true){
            CoroutineScope(Dispatchers.IO).launch {
                async{getTopInCategory(category)}
                async{getAllCouresInCategory(category)}
                async{getFeaturedCourse(category)}
                async{getSubCategories(category!!)}
              //  async { getSubCategory(category!!,"Nodejs") }
            }
        }



        individualCategorieDetailsBinding.inividualcategoriedetailbackBtn.setOnClickListener {
            activity?.supportFragmentManager?.popBackStack()
//            beginTransaction()
//                ?.replace(
//                    com.robosoft.virtuallearnproject.R.id.homescreenfragment_container,
//                    ChoiceYourCourseFragment()
//
//                )
//               // ?.addToBackStack(null)
//                ?.commit()
        }

        individualCategorieDetailsBinding.individualcategoriessearchBtn.setOnClickListener {
            activity?.supportFragmentManager?.beginTransaction()
                ?.replace(
                    com.robosoft.virtuallearnproject.R.id.homescreenfragment_container,
                    HomeToSearchScreenFragment()
                )
                ?.addToBackStack(null)
                ?.commit()
        }

        ViewCompat.setNestedScrollingEnabled(individualCategorieDetailsBinding.allCourseRecycleView, false)
        return individualCategorieDetailsBinding.root
    }


    suspend private fun getFeaturedCourse(category: String?) {
        val accessToken = SharedPreferenceManager(requireContext()).getAccessToken()
        val searchByCategoryAndKeywordsRequest = SearchByCategoryAndKeywordsRequest(category, "beg")
        val homeService = HomeApiService()
        homeService.searchbyCategoryKeyword(accessToken!!, searchByCategoryAndKeywordsRequest) {
            if (it != null) {
                Log.d("msg", it.toString())
                if (isAdded) {
                    individualCategorieDetailsBinding.featurecCourseRecycleView.layoutManager = LinearLayoutManager(activity?.applicationContext, LinearLayoutManager.HORIZONTAL,false)
                    individualCategorieDetailsBinding.featurecCourseRecycleView.adapter =
                        FeaturedCourseRecyclerAdapter(
                            activity?.applicationContext!!,
                            it
                        )
                }
            } else {
                Log.d("msg", "something went wrong")
            }
        }
    }

//    suspend private fun getSubCategory(category: String, subcategory: String): String {
//        val accessToken = SharedPreferenceManager(requireContext()).getAccessToken()
//        val searchBySubCategoryRequest = SearchBySubCategoryRequest(category, subcategory)
//        val homeService = HomeApiService()
//        homeService.searchbySubCategory(accessToken!!, searchBySubCategoryRequest) {
//        if (it != null) {
//            Log.d("msg", it.toString())
//            if (isAdded) {
//                individualCategorieDetailsBinding.subcategoriesvideoRecyclerview.layoutManager =
//                    LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL, false)
//                individualCategorieDetailsBinding.subcategoriesvideoRecyclerview.adapter =
//                    SubcategoriesVideoRecyclerAdapter(
//                        activity?.applicationContext!!,
//                        it
//                    )
//                }
//            } else {
//              Log.d("msg", "something went wrong")
//            }
//        }
//        return "hi"
//    }



    suspend private fun getAllCouresInCategory(category :String?) {
        val accessToken = SharedPreferenceManager(requireContext()).getAccessToken()
        val allCourseInCategoryRequestData = TopCourseRequest(text = category, choice = "seeAll")
        val homeService = HomeApiService()
        homeService.topCourseInCategory(accessToken!!, allCourseInCategoryRequestData) {
            if (it != null) {
                Log.d("msg", it.toString())
                if (isAdded) {
                    individualCategorieDetailsBinding.allCourseRecycleView.layoutManager = LinearLayoutManager(activity?.applicationContext)
                    individualCategorieDetailsBinding.allCourseRecycleView.adapter =
                        TopCourseInCategoryRecyclerAdapter(
                            activity?.applicationContext!!,
                            it
                        )

                }
            } else {
                Log.d("msg", "something went wrong")
            }
        }
    }

    private suspend fun getSubCategories(category: String) {
        val accessToken = SharedPreferenceManager(requireContext()).getAccessToken()
        val suggestRequest = SubCategorieRequest(category)
        val homeService = HomeApiService()
        homeService.subCategories(accessToken!!, suggestRequest) {
            if (it != null) {
                Log.d("msg", it.toString())

                if (isAdded) {
                    individualCategorieDetailsBinding.subcategoriesRecyclerview.layoutManager =
                        LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL, false)
                    individualCategorieDetailsBinding.subcategoriesRecyclerview.adapter =
                        SubCategoriesRecyclerAdapter(activity?.applicationContext!!, it, category, individualCategorieDetailsBinding.subcategoriesvideoRecyclerview)
                }

            } else {
                Log.d("msg", "something went wrong")
            }
        }

    }

    suspend fun getTopInCategory(category: String?) {
        val accessToken = SharedPreferenceManager(activity?.applicationContext!!)?.getAccessToken()
        val requestData = TopCourseRequest(text = category, choice = "")
        val homeApiService = HomeApiService()
        homeApiService.topCourseInCategory(accessToken!!,requestData){
            if(it!=null){
                Log.d("topCategoryResponse", it.toString())
                if (isAdded) {
                    individualCategorieDetailsBinding.courseStartedRecyclerView.layoutManager =
                        LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL, false)
                    individualCategorieDetailsBinding.courseStartedRecyclerView.adapter =
                        HomeScreenTopCourseOneRecyclerAdapter(
                            activity?.applicationContext!!,
                            it
                        )
                }
            } else{
                Toast.makeText(activity?.applicationContext, "Something went wrong", Toast.LENGTH_SHORT).show()
            }
        }
    }
}