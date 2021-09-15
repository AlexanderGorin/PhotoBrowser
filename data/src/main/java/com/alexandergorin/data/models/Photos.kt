package com.alexandergorin.data.models

import com.google.gson.annotations.SerializedName

data class Photos(
    @SerializedName("page")
    val page: Int,
    @SerializedName("pages")
    val pages: Int,
    @SerializedName("perpage")
    val perPage: Int,
    @SerializedName("photo")
    val photos: List<Photo>,
    @SerializedName("total")
    val total: Int
)