package jobtrends.jobsurvey.viewmodel

import android.content.Intent
import android.databinding.DataBindingUtil
import android.databinding.ObservableField
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
import java.util.*
import javax.mail.internet.AddressException
import javax.mail.internet.InternetAddress
import android.app.DatePickerDialog
import android.app.DatePickerDialog.OnDateSetListener
import java.text.SimpleDateFormat

class SignUpViewModel : AppCompatActivity
{
  val userModel: UserModel
  val user: User
  val jsonController: JsonController
  val apiController: APIController
  val tag: String
  val lastnameErrorText: ObservableField<String>
  val firstnameErrorText: ObservableField<String>
  val birthdayErrorText: ObservableField<String>
  val emailErrorText: ObservableField<String>
  val jobErrorText: ObservableField<String>
  val passwordErrorText: ObservableField<String>
  val passwordBisErrorText: ObservableField<String>
  private val myCalendar: Calendar
  val date: OnDateSetListener

  constructor() : super()
  {
    tag = "SignUpViewModel"

    userModel = serviceController!!.getInstance()
    user = serviceController!!.getInstance()
    jsonController = serviceController!!.getInstance()
    apiController = serviceController!!.getInstance()

    lastnameErrorText = ObservableField()
    firstnameErrorText = ObservableField()
    birthdayErrorText = ObservableField()
    emailErrorText = ObservableField()
    jobErrorText = ObservableField()
    passwordErrorText = ObservableField()
    passwordBisErrorText = ObservableField()

    myCalendar = Calendar.getInstance()
    date = DatePickerDialog.OnDateSetListener { view, year, monthOfYear, dayOfMonth ->
      myCalendar.set(Calendar.YEAR, year)
      myCalendar.set(Calendar.MONTH, monthOfYear)
      myCalendar.set(Calendar.DAY_OF_MONTH, dayOfMonth)
      updateLabel()
    }
  }

  override fun onCreate(savedInstanceState: Bundle?)
  {
    super.onCreate(savedInstanceState)
    val binding: SignUpViewBinding = DataBindingUtil.setContentView(this, R.layout.sign_up_view)
    binding.vm = this
  }

  private var stop: Boolean = false

  private fun checkInput(): Boolean
  {
    stop = false
    checkInput(userModel.lastName.get(), "^[a-zA-Z ,.'-]+$", lastnameErrorText)
    checkInput(userModel.firstName.get(), "^[a-zA-Z ,.'-]+$", firstnameErrorText)
    checkInput(userModel.birthday.get(), "[0-9]{2}/[0-9]{2}/[0-9]{4}", birthdayErrorText)
    checkInput(userModel.email.get(), "^[a-zA-Z0-9_.+-]+@[a-zA-Z0-9-]+\\.[a-zA-Z0-9-.]+$", emailErrorText)
    checkInput(userModel.metier.get(), "^[a-zA-Z ,.'-]+$", jobErrorText)
    checkInput(userModel.password.get(), "^[a-zA-Z0-9]+$", passwordErrorText)
    return stop
  }

  private fun checkInput(input: String?, pattern: String, error: ObservableField<String>)
  {
    if (input == null || input == "")
    {
      error.set("")
      error.set("Ce champ ne peut pas être vide")
      stop = true
      return
    }
    val regex = Regex(pattern)
    val result = input.matches(regex)
    if (!result)
    {
      error.set("")
      error.set("Ce champ est invalide")
      stop = true
    }
  }

  fun onDatePickerClick()
  {
    DatePickerDialog(this, date, myCalendar
      .get(Calendar.YEAR), myCalendar.get(Calendar.MONTH),
                     myCalendar.get(Calendar.DAY_OF_MONTH)).show()
  }

  private fun updateLabel()
  {
    val df = SimpleDateFormat("dd/mm/yyyy", Locale.FRANCE)
    val format = df.format(myCalendar.time)
    userModel.birthday.set(format)
  }

  fun onClick()
  {
    checkInput()
    if (stop)
    {
      return
    }
//    user.email = userModel.email.get()
//    user.firstName = userModel.firstName.get()
//    user.lastName = userModel.lastName.get()
//    user.password = userModel.password.get()
//    user.metier = userModel.metier.get()
//    user.birthday = userModel.birthday.get()
//    val json = jsonController.serialize(user)
//    apiController.post("auth/signup", json, ::firstResponse, this)
  }

  fun firstResponse(code: Int, response: String?)
  {
    Log.d(tag, response)
    val tmp = mutableMapOf<String?, String?>()
    tmp["username"] = user.email!!
    tmp["password"] = user.password!!
    val tmpSerialized = jsonController.serialize(tmp)
    apiController.post("auth/login", tmpSerialized, ::secondResponse, this)
  }

  private fun secondResponse(code: Int, response: String?)
  {
    println(response)
    val intent = Intent(this, HomeViewModel::class.java)
    startActivity(intent)
  }
}
