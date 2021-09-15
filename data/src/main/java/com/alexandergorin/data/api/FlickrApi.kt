package com.alexandergorin.data.api

import com.alexandergorin.data.BuildConfig
import com.alexandergorin.data.models.PhotosResponse
import io.reactivex.Single
import retrofit2.http.GET
import retrofit2.http.Query

interface FlickrApi {

    @GET("services/rest/")
    fun getRecentPhotos(
        @Query(API_KEY_PARAM) apiKey: String = BuildConfig.API_KEY,
        @Query(METHOD_PARAM) method: String = GET_RECENT_METHOD,
        @Query(NO_JSON_CALLBACK_PARAM) noJsonCallback: Int = NO_JSON_CALLBACK,
        @Query(FORMAT_PARAM) format: String = FORMAT,
        @Query(EXTRAS_PARAM) extras: String = URLS
    ): Single<PhotosResponse>

    private companion object {
        private const val EXTRAS_PARAM = "extras"
        private const val METHOD_PARAM = "method"
        private const val FORMAT_PARAM = "format"
        private const val NO_JSON_CALLBACK_PARAM = "nojsoncallback"
        private const val API_KEY_PARAM = "api_key"

        private const val GET_RECENT_METHOD = "flickr.photos.getRecent"
        private const val NO_JSON_CALLBACK = 1
        private const val FORMAT = "json"
        private const val URLS = "url_sq, url_t, url_s, url_q, url_m, url_n, url_z, url_c, url_l, url_o"
    }
}