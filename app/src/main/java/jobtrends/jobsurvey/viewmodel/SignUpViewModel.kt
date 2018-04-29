package jobtrends.jobsurvey.viewmodel

import android.content.Intent
import android.databinding.DataBindingUtil
import android.databinding.ObservableField
import android.icu.text.SimpleDateFormat
import android.net.ParseException
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.util.Log
import android.widget.EditText
import jobtrends.jobsurvey.R
import jobtrends.jobsurvey.databinding.SignUpViewBinding
import jobtrends.jobsurvey.model.User
import jobtrends.jobsurvey.model.UserModel
import jobtrends.jobsurvey.service.APIController
import jobtrends.jobsurvey.service.JsonController
import jobtrends.jobsurvey.service.serviceController
import java.util.*
import javax.mail.internet.AddressException
import javax.mail.internet.InternetAddress

class SignUpViewModel : AppCompatActivity
{
  var userModel = serviceController !!.getInstance<UserModel>()
  var user = serviceController !!.getInstance<User>()
  var jsonController = serviceController !!.getInstance<JsonController>()
  var apiController = serviceController !!.getInstance<APIController>()
  private val tag = "SignUpViewModel"
  var lastnameErrorText = ObservableField<String?>()
  var firstnameErrorText = ObservableField<String?>()
  var birthdayErrorText = ObservableField<String?>()
  var emailErrorText = ObservableField<String?>()
  var jobErrorText = ObservableField<String?>()
  var passwordErrorText = ObservableField<String?>()
  var passwordBisErrorText = ObservableField<String?>()
  private val funcs = mutableMapOf<Int, (String?) -> Boolean>()

  constructor() : super()
  {
    funcs[0] = { s : String? -> isValidEmailAddress(s) }
    funcs[1] = { s : String? -> isValidFormat(s) }
  }

  override fun onCreate(savedInstanceState : Bundle?)
  {
    super.onCreate(savedInstanceState)
    val binding : SignUpViewBinding = DataBindingUtil.setContentView(this, R.layout.sign_up_view)
    binding.vm = this
  }


  fun checkInput()
  {
    if (!funcs[0]!!(userModel.email.get()))
    {
      emailErrorText.set("Invalid email")
    }
  }

  fun isValidEmailAddress(email: String?): Boolean
  {
    var result = true
    try
    {
      val emailAddr = InternetAddress(email)
      emailAddr.validate()
    }
    catch (ex: AddressException)
    {
      result = false
    }

    return result
  }

  fun isValidFormat(value: String?): Boolean
  {
    val format : String = "dd/MM/yyyy"
    var date: Date? = null
    try
    {
      val sdf = SimpleDateFormat(format)
      date = sdf.parse(value)
      if (value != sdf.format(date))
      {
        date = null
      }
    }
    catch (ex: ParseException)
    {
      ex.printStackTrace()
    }

    return date != null
  }

  private fun isInvalidInput(input : String?): Boolean
  {
    if (input == null || input == "")
    {
      return true
    }
    val regex : Regex = Regex("/^[0-9]{8}[A-Za-z]$/")
    val result = input.matches(regex)
    return !result
  }

  fun onClick()
  {
    checkInput()
//    user.email = userModel.email.get()
//    user.firstName = userModel.firstName.get()
//    user.lastName = userModel.lastName.get()
//    user.password = userModel.password.get()
//    user.metier = userModel.metier.get()
//    user.birthday = userModel.birthday.get()
//    val json = jsonController.serialize(user)
//    apiController.post("auth/signup", json, ::firstResponse, this)
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
