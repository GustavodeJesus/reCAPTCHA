package com.geeklabs.recaptchasampleandroid.model

import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.Retrofit
import android.arch.lifecycle.MutableLiveData
import android.arch.lifecycle.LiveData
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.util.HashMap


class RecaptchaRepository {

    private val okHttpClient: OkHttpClient
        get() = OkHttpClient.Builder().addInterceptor(loggingInterceptor).build()

    private val loggingInterceptor: HttpLoggingInterceptor
        get() {
            val interceptor = HttpLoggingInterceptor()
            interceptor.setLevel(HttpLoggingInterceptor.Level.BODY)
            return interceptor
        }

    fun doRecaptchaValidation(baseUrl: String, response: String, key: String): LiveData<RecaptchaVerifyResponse> {
        val data = MutableLiveData<RecaptchaVerifyResponse>()
        val params = HashMap<String, String>()
        params["response"] = response
        params["secret"] = key
        getRecaptchaValidationService(baseUrl).verifyResponse(params).enqueue(object : Callback<RecaptchaVerifyResponse> {
            override fun onResponse(call: Call<RecaptchaVerifyResponse>, response: Response<RecaptchaVerifyResponse>) {
                data.setValue(response.body())
            }

            override fun onFailure(call: Call<RecaptchaVerifyResponse>, t: Throwable) {
                data.setValue(null)
            }
        })
        return data
    }

    private fun getRecaptchaValidationService(baseUrl: String): RecaptchaVerificationService {
        return getRetrofit(baseUrl).create(RecaptchaVerificationService::class.java)
    }

    private fun getRetrofit(baseUrl: String): Retrofit {
        return Retrofit.Builder()
                .baseUrl(baseUrl)
                .client(okHttpClient)
                .addConverterFactory(GsonConverterFactory.create())
                .build()
    }
}
