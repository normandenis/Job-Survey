package jobtrends.jobsurvey.viewmodel

import android.content.Intent
import android.databinding.DataBindingUtil
import android.databinding.ObservableField
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.util.Log
import jobtrends.jobsurvey.R
import jobtrends.jobsurvey.databinding.SignInViewBinding
import jobtrends.jobsurvey.service.APIController
import jobtrends.jobsurvey.service.JsonController
import jobtrends.jobsurvey.service.serviceController

class SignInViewModel : AppCompatActivity()
{
  var jsonController = serviceController!!.getInstance<JsonController>()
  var apiController = serviceController!!.getInstance<APIController>()
  var username = ObservableField<String?>()
  var password = ObservableField<String?>()
  var error = ObservableField<String?>()
  private val tag = "SignInViewModel"

  override fun onCreate(savedInstanceState: Bundle?)
  {
    super.onCreate(savedInstanceState)
    val binding: SignInViewBinding = DataBindingUtil.setContentView(this, R.layout.sign_in_view)
    binding.vm = this
    serviceController!!.register(resources)
  }

  fun onClickSignIn()
  {
    val body = mutableMapOf<String?, String?>()
    body["username"] = username.get()
    body["password"] = password.get()
    val bodySerialized = jsonController.serialize(body)
    apiController.post("auth/login", bodySerialized, ::authLoginReply, this)
  }

  private fun authLoginReply(code: Int?, body: String?)
  {
    val msg = "$code: $body"
    Log.d(tag, msg)
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
