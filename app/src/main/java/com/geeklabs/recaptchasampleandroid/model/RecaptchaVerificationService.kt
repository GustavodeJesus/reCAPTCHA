package com.geeklabs.recaptchasampleandroid.model

import retrofit2.http.Headers
import retrofit2.http.POST
import retrofit2.http.QueryMap
import com.geeklabs.recaptchasampleandroid.model.RecaptchaVerifyResponse
import retrofit2.Call


interface RecaptchaVerificationService {
    @Headers("Content-Type: application/x-www-form-urlencoded; charset=utf-8")
    @POST("/recaptcha/api/siteverify")
    fun verifyResponse(@QueryMap params: Map<String, String>): Call<RecaptchaVerifyResponse>
}