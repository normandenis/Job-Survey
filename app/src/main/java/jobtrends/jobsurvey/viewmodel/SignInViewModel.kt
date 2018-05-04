package jobtrends.jobsurvey.viewmodel

import android.content.Intent
import android.databinding.DataBindingUtil
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.util.Log
import jobtrends.jobsurvey.R
import jobtrends.jobsurvey.databinding.SignInViewBinding
import jobtrends.jobsurvey.model.ErrorModel
import jobtrends.jobsurvey.model.UserModel
import jobtrends.jobsurvey.service.APIController
import jobtrends.jobsurvey.service.JsonController
import jobtrends.jobsurvey.service.serviceController

class SignInViewModel : AppCompatActivity
{
  private val _jsonController: JsonController?
  private val _apiController: APIController?
  private val _userModel: UserModel?
  private val _errorModel: ErrorModel?
  private val _tag: String?

  constructor() : super()
  {
    _tag = "SignInViewModel"
    _apiController = serviceController!!.getInstance()
    _jsonController = serviceController!!.getInstance()
    _errorModel = serviceController!!.getInstance()
    _userModel = serviceController!!.getInstance()
  }


  override fun onCreate(savedInstanceState: Bundle?)
  {
    super.onCreate(savedInstanceState)
    val binding: SignInViewBinding = DataBindingUtil.setContentView(this, R.layout.sign_in_view)
    binding.vm = this
    binding.userModel = _userModel
    binding.errorModel = _errorModel
    serviceController!!.register(resources)
  }

  fun onClickSignIn()
  {
    val body: MutableMap<String?, String?>? = mutableMapOf()
    body!!["username"] = _userModel!!.email!!.get()
    body["password"] = _userModel.password!!.get()
    val bodySerialized = _jsonController!!.serialize(body)
    _apiController!!.post("auth/login", bodySerialized, ::authLoginReply, this)
  }

  private fun authLoginReply(code: Int?, body: String?)
  {
    val msg = "$code: $body"
    Log.d(_tag, msg)
    if (code != 200)
    {
      _errorModel!!.mainMsg!!.set(body)
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
