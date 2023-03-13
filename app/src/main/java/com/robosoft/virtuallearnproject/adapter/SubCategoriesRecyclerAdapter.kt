package com.robosoft.virtuallearnproject.adapter

import android.content.Context
import android.content.Intent
import android.net.Uri
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.core.content.ContentProviderCompat.requireContext
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.robosoft.virtuallearnproject.R
import com.robosoft.virtuallearnproject.SharedPreferenceManager
import com.robosoft.virtuallearnproject.dataclass.home.choicecourse.ChoiceCourseResponse
import com.robosoft.virtuallearnproject.dataclass.home.searchbysubcategory.SearchBySubCategoryRequest
import com.robosoft.virtuallearnproject.dataclass.home.subcategories.SubCtegoriesResponseData
import com.robosoft.virtuallearnproject.network.home.HomeApiService
import com.robosoft.virtuallearnproject.ui.activities.IndividualCourseActivity
import com.squareup.picasso.Picasso
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch

class SubCategoriesRecyclerAdapter(
    val context: Context,
    private val data: SubCtegoriesResponseData?,
    private val category: String,
    private val subcategoriesvideoRecyclerview: RecyclerView
) : RecyclerView.Adapter<SubCategoriesRecyclerAdapter.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.subcategories_grid_item_list, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        //  val course = get(position)
        holder.subcategorieTitle.text = data?.get(position).toString()

        holder.itemView.setOnClickListener{
            GlobalScope.launch { getSubCategory(category,data?.get(position).toString()) }

        }
    }

    override fun getItemCount(): Int {
        return data?.size!!


    }

    class ViewHolder(ItemView: View) : RecyclerView.ViewHolder(ItemView) {
        val subcategorieTitle: TextView = itemView.findViewById(R.id.subcategorie_tv)
    }
    suspend private fun getSubCategory(category: String, subcategory: String): String {
        val accessToken = SharedPreferenceManager(context).getAccessToken()
        val searchBySubCategoryRequest = SearchBySubCategoryRequest(category, subcategory)
        val homeService = HomeApiService()
        homeService.searchbySubCategory(accessToken!!, searchBySubCategoryRequest) {
            if (it != null) {
                Log.d("msg", it.toString())

                subcategoriesvideoRecyclerview.layoutManager =
                    LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL, false)
                subcategoriesvideoRecyclerview.adapter =
                    SubcategoriesVideoRecyclerAdapter(
                        context,
                        it
                    )

            } else {
                Log.d("msg", "something went wrong")
            }
        }
        return "hi"
    }

}