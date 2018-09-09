package com.geeklabs.recaptchasampleandroid.model

import com.google.gson.annotations.SerializedName


class RecaptchaVerifyResponse {

    val isSuccess: Boolean = false
    val challenge_ts: String? = null
    val apk_package_name: String? = null
    @SerializedName("error-codes")
    val errorCodes: List<String>? = null
}
