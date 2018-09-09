package com.geeklabs.recaptchasampleandroid.viewmodel

import android.arch.lifecycle.AndroidViewModel
import com.geeklabs.recaptchasampleandroid.model.RecaptchaRepository
import com.geeklabs.recaptchasampleandroid.model.RecaptchaVerifyResponse
import android.arch.lifecycle.LiveData
import android.app.Application


class RecaptchaResponseViewModel(application: Application) : AndroidViewModel(application) {

    fun getmRecaptchaObservable(baseUrl: String, response: String, key: String): LiveData<RecaptchaVerifyResponse> {
        return RecaptchaRepository().doRecaptchaValidation(baseUrl, response, key)
    }
}