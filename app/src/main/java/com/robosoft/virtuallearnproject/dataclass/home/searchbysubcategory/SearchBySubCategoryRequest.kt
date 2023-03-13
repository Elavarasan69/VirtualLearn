package com.robosoft.virtuallearnproject.dataclass.home.searchbysubcategory

import com.robosoft.virtuallearnproject.dataclass.home.subcategories.SubCtegoriesResponseData

data class SearchBySubCategoryRequest(
    val category: String,
    val subCategory: String
)