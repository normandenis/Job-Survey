package jobtrends.jobsurvey.viewmodel

import android.app.NotificationChannel
import android.app.NotificationManager
import android.content.Intent
import android.databinding.DataBindingUtil
import android.databinding.ObservableField
import android.os.Build
import android.os.Build.VERSION
import android.os.Build.VERSION_CODES
import android.os.Bundle
import android.preference.PreferenceManager
import android.support.v7.app.AppCompatActivity
import android.util.Log
import jobtrends.jobsurvey.R
import jobtrends.jobsurvey.databinding.SignInViewBinding
import jobtrends.jobsurvey.model.User
import jobtrends.jobsurvey.service.APIController
import jobtrends.jobsurvey.service.JsonController
import jobtrends.jobsurvey.service.ServiceController
import jobtrends.jobsurvey.service.serviceController

class SignInViewModel : AppCompatActivity()
{
  var jsonController = serviceController!!.getInstance<JsonController>()
  var apiController = serviceController!!.getInstance<APIController>()
  var username = ObservableField<String>()
  var password = ObservableField<String>()
  var error = ObservableField<String>()
  private val TAG = "SignInViewModel"

  override fun onCreate(savedInstanceState: Bundle?)
  {
    super.onCreate(savedInstanceState)
    val binding: SignInViewBinding = DataBindingUtil.setContentView(this, R.layout.sign_in_view)
    binding.vm = this

    serviceController!!.register(resources)

  }

  fun onClickSignIn()
  {
    val tmp = mutableMapOf<String, String?>()
    tmp["username"] = username.get()
    tmp["password"] = password.get()
    val tmpSerialized = jsonController.serialize(tmp)
    apiController.post("auth/login", tmpSerialized, ::firstResponse, this)
  }

  private fun firstResponse(code: Int, response: String)
  {
    Log.d(TAG, "$code: $response")
    if (code != 200)
    {
      error.set("L'email et/ou le mot de passe entré(s) est/sont incorrect(s).")
      return
    }
    val intent = Intent(this, HomeViewModel::class.java)
    startActivity(intent)
  }

  fun onClickSignUp()
  {
    val intent = Intent(this, SignUpViewModel::class.java)
    startActivity(intent)
  }
}
