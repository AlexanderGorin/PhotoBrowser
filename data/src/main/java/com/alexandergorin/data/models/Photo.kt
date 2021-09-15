package com.alexandergorin.data.models

import com.google.gson.annotations.SerializedName

data class Photo(
    @SerializedName("farm")
    val farm: Int,
    @SerializedName("id")
    val id: String,
    @SerializedName("isfamily")
    val isFamily: Int,
    @SerializedName("isfriend")
    val isFriend: Int,
    @SerializedName("ispublic")
    val isPublic: Int,
    @SerializedName("owner")
    val owner: String,
    @SerializedName("secret")
    val secret: String,
    @SerializedName("server")
    val server: String,
    @SerializedName("title")
    val title: String,
    @SerializedName("url_sq")
    val urlSq: String?,
    @SerializedName("url_t")
    val urlT: String?,
    @SerializedName("url_s")
    val urlS: String?,
    @SerializedName("url_q")
    val urlQ: String?,
    @SerializedName("url_m")
    val urlM: String?,
    @SerializedName("url_n")
    val urlN: String?,
    @SerializedName("url_z")
    val urlZ: String?,
    @SerializedName("url_c")
    val urlC: String?,
    @SerializedName("url_l")
    val urlL: String?,
    @SerializedName("url_o")
    val urlO: String?,
)