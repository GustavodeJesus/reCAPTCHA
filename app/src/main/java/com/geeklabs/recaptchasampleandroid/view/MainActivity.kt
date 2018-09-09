package com.geeklabs.recaptchasampleandroid.view

import android.app.AlertDialog
import android.arch.lifecycle.Observer
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import com.geeklabs.recaptchasampleandroid.R

import com.google.android.gms.safetynet.SafetyNet
import com.google.android.gms.safetynet.SafetyNetApi
import com.google.android.gms.tasks.OnSuccessListener
import com.geeklabs.recaptchasampleandroid.viewmodel.RecaptchaResponseViewModel
import android.arch.lifecycle.ViewModelProviders
import com.geeklabs.recaptchasampleandroid.model.RecaptchaVerifyResponse
import com.google.android.gms.tasks.OnFailureListener
import kotlinx.android.synthetic.main.activity_main.*


class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        button.setOnClickListener {
            SafetyNet.getClient(this@MainActivity).verifyWithRecaptcha(resources.getString(R.string.pubK))
                    .addOnSuccessListener(SuccessListener())
                    .addOnFailureListener(FailureListener())
        }
    }

    private fun showAlertWithButton(title: String, message: String, buttonMessage: String) {
        AlertDialog.Builder(this).setTitle(title).setMessage(message).setCancelable(false).setPositiveButton(buttonMessage, null).create().show()
    }

    private inner class SuccessListener : OnSuccessListener<SafetyNetApi.RecaptchaTokenResponse> {

        override fun onSuccess(recaptchaTokenResponse: SafetyNetApi.RecaptchaTokenResponse) {

            val userResponseToken = recaptchaTokenResponse.tokenResult
            if (!userResponseToken.isEmpty()) {
                val mViewModel = ViewModelProviders.of(this@MainActivity).get(RecaptchaResponseViewModel::class.java)
                mViewModel.getmRecaptchaObservable("https://www.google.com", userResponseToken, applicationContext.getString(R.string.priK))
                        .observe(this@MainActivity, Observer<RecaptchaVerifyResponse> { recaptchaVerifyResponse ->
                            if (recaptchaVerifyResponse != null && recaptchaVerifyResponse.isSuccess) {
                                showAlertWithButton("Você é um humano", "Sim Siree, ele é um humano, eu te digo", "Bora Continuar!")
                            } else {
                                showAlertWithButton("Você não é um humano", "", "Doggone it!")
                            }
                        })
            }
        }
    }

    private inner class FailureListener : OnFailureListener {

        override fun onFailure(e: Exception) {
            showAlertWithButton("Obie is Unknown", e.localizedMessage, "Doggone it!")
        }
    }
}