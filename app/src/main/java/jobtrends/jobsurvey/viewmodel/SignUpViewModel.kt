package jobtrends.jobsurvey.viewmodel

import android.content.Intent
import android.databinding.DataBindingUtil
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.util.Log
import jobtrends.jobsurvey.R
import jobtrends.jobsurvey.databinding.SignUpViewBinding
import jobtrends.jobsurvey.model.User
import jobtrends.jobsurvey.model.UserModel
import jobtrends.jobsurvey.service.APIController
import jobtrends.jobsurvey.service.JsonController
import jobtrends.jobsurvey.service.serviceController

class SignUpViewModel : AppCompatActivity()
{
  var userModel = serviceController !!.getInstance<UserModel>()
  var user = serviceController !!.getInstance<User>()
  var jsonController = serviceController !!.getInstance<JsonController>()
  var apiController = serviceController !!.getInstance<APIController>()
  private val tag = "SignUpViewModel"

  override fun onCreate(savedInstanceState : Bundle?)
  {
    super.onCreate(savedInstanceState)
    val binding : SignUpViewBinding = DataBindingUtil.setContentView(this, R.layout.sign_up_view)
    binding.vm = this
  }

  fun onClick()
  {
    user.email = userModel.email.get()
    user.firstName = userModel.firstName.get()
    user.lastName = userModel.lastName.get()
    user.password = userModel.password.get()
    user.metier = userModel.metier.get()
    user.birthday = userModel.birthday.get()
    val json = jsonController.serialize(user)
    apiController.post("auth/signup", json, ::firstResponse, this)
  }

  fun firstResponse(code: Int, response : String)
  {
    Log.d(tag, response)
    val tmp = mutableMapOf<String, String?>()
    tmp["username"] = user.email
    tmp["password"] = user.password
    val tmpSerialized = jsonController.serialize(tmp)
    apiController
      .post("auth/login", tmpSerialized, ::secondResponse, this)
  }

  fun secondResponse(code: Int, response : String)
  {
    println(response)
    val intent = Intent(this, HomeViewModel::class.java)
    startActivity(intent)
  }
}
