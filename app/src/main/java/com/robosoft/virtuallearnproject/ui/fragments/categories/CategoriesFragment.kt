package com.robosoft.virtuallearnproject.ui.fragments.categories

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.robosoft.virtuallearnproject.R
import com.robosoft.virtuallearnproject.databinding.FragmentCategoriesBinding
import com.robosoft.virtuallearnproject.ui.fragments.Search.HomeToSearchScreenFragment
import com.robosoft.virtuallearnproject.ui.fragments.homelayoutfragments.HomeScreenMainFragment

class CategoriesFragment : Fragment() {
    private lateinit var CategoriesBinding: FragmentCategoriesBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        CategoriesBinding = FragmentCategoriesBinding.inflate(inflater, container, false)

        CategoriesBinding.backbtnIv?.setOnClickListener {
            activity?.supportFragmentManager?.popBackStack()
//            activity?.supportFragmentManager?.beginTransaction()
//                ?.replace(R.id.homescreenfragment_container, HomeScreenMainFragment())
//                ?.addToBackStack(null)
//                ?.commit()
        }

        CategoriesBinding.searchbtnIv?.setOnClickListener {
            activity?.supportFragmentManager?.beginTransaction()

                ?.replace(
                    com.robosoft.virtuallearnproject.R.id.homescreenfragment_container,
                    HomeToSearchScreenFragment()
                )
                ?.addToBackStack(null)
                ?.commit()
        }

        CategoriesBinding.ITSoftware?.setOnClickListener {
//            val bundle = Bundle()
//            bundle.putString("category", "IT & Software")
//
//            val individualCategory = IndividualCategorieOnClickDetailScreenFragment()
//            individualCategory.arguments = bundle
//            activity?.supportFragmentManager?.beginTransaction()?.replace(
//                com.robosoft.virtuallearnproject.R.id.homescreenfragment_container,
//                individualCategory
//            )
//                ?.addToBackStack(null)
//                ?.commit()
             startIndividualCategory("IT & Software")
        }

        CategoriesBinding.ITBusiness?.setOnClickListener{
            startIndividualCategory("Business")
        }
        CategoriesBinding.ITDesign?.setOnClickListener {
            startIndividualCategory("Design")
        }

        CategoriesBinding.ITHealthAndFitness?.setOnClickListener{
            startIndividualCategory("Health & Fitness")
        }
        
        CategoriesBinding.ITMarketing?.setOnClickListener {
            startIndividualCategory("Marketing")
        }

        CategoriesBinding.ITMusic?.setOnClickListener{
            startIndividualCategory("Music")
        }

        CategoriesBinding.ITTeaching?.setOnClickListener{
            startIndividualCategory("Teaching")
        }

        CategoriesBinding.ITWebDevelopment?.setOnClickListener{
            startIndividualCategory("Web Development")
        }

        return CategoriesBinding.root
    }

    private fun startIndividualCategory( category : String) {
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

}