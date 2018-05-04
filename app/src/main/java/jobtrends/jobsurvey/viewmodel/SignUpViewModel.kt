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
import android.app.DatePickerDialog
import android.app.DatePickerDialog.OnDateSetListener
import jobtrends.jobsurvey.model.ErrorModel
import java.text.SimpleDateFormat
import java.util.*

class SignUpViewModel : AppCompatActivity
{
  private val _userModel: UserModel?
  private val _user: User?
  private val _jsonController: JsonController?
  private val _apiController: APIController?
  private val _errorModel: ErrorModel?
  private val _tag: String?
  private val _calendar: Calendar?
  private val _date: OnDateSetListener?

  constructor() : super()
  {
    _tag = "SignUpViewModel"

    _userModel = serviceController!!.getInstance()
    _user = serviceController!!.getInstance()
    _errorModel = serviceController!!.getInstance()
    _jsonController = serviceController!!.getInstance()
    _apiController = serviceController!!.getInstance()

    _calendar = Calendar.getInstance()
    _date = DatePickerDialog.OnDateSetListener { _, year, monthOfYear, dayOfMonth ->
      _calendar.set(Calendar.YEAR, year)
      _calendar.set(Calendar.MONTH, monthOfYear)
      _calendar.set(Calendar.DAY_OF_MONTH, dayOfMonth)
      updateLabel()
    }
  }

  override fun onCreate(savedInstanceState: Bundle?)
  {
    super.onCreate(savedInstanceState)
    val binding: SignUpViewBinding = DataBindingUtil.setContentView(this, R.layout.sign_up_view)
    binding.vm = this
    binding.errorModel = _errorModel
    binding.userModel = _userModel
  }

  private var stop: Boolean? = false

  private fun checkInput()
  {
    stop = false
    checkInput(_userModel!!.lastName!!.get(), "^[a-zA-Z ,.'-]+$", _errorModel!!.lastnameMsg)
    checkInput(_userModel.firstName!!.get(), "^[a-zA-Z ,.'-]+$", _errorModel.firstnameMsg)
    checkInput(_userModel.birthday!!.get(), "[0-9]{2}/[0-9]{2}/[0-9]{4}", _errorModel.birthdayMsg)
    checkInput(_userModel.email!!.get(), "^[a-zA-Z0-9_.+-]+@[a-zA-Z0-9-]+\\.[a-zA-Z0-9-.]+$",
               _errorModel.emailMsg)
    checkInput(_userModel.metier!!.get(), "^[a-zA-Z ,.'-]+$", _errorModel.jobMsg)
    checkInput(_userModel.password!!.get(), "^[a-zA-Z0-9]+$", _errorModel.passwordMsg)
    checkPassword()
  }

  private fun checkPassword()
  {
    if (_userModel!!.password!!.get() != _userModel.encryptedPassword!!.get())
    {
      stop = true
      _errorModel!!.passwordBisMsg!!.set("")
      _errorModel.passwordBisMsg!!.set("Ce mot de passe est différent du premier")
    }
  }

  private fun checkInput(input: String?, pattern: String?, error: ObservableField<String?>?)
  {
    if (input == null || input == "")
    {
      error!!.set("")
      error.set("Ce champ ne peut pas être vide")
      stop = true
      return
    }
    val regex = Regex(pattern!!)
    val result = input.matches(regex)
    if (!result)
    {
      error!!.set("")
      error.set("Ce champ est invalide")
      stop = true
    }
  }

  fun onDatePickerClick()
  {
    DatePickerDialog(this, _date, _calendar!!.get(Calendar.YEAR), _calendar.get(Calendar.MONTH),
                     _calendar.get(Calendar.DAY_OF_MONTH)).show()
  }

  private fun updateLabel()
  {
    val df = SimpleDateFormat("dd/mm/yyyy", Locale.FRANCE)
    val format = df.format(_calendar!!.time)
    _userModel!!.birthday!!.set(format)
  }

  fun onClick()
  {
    checkInput()
    if (stop == true)
    {
      return
    }
    _user!!.lastName = _userModel!!.lastName!!.get()
    _user.firstName = _userModel.firstName!!.get()
    _user.birthday = _userModel.birthday!!.get()
    _user.email = _userModel.email!!.get()
    _user.metier = _userModel.metier!!.get()
    _user.password = _userModel.password!!.get()
    _user.encryptedPassword = _userModel.encryptedPassword!!.get()
    val body = _jsonController!!.serialize(_user)
    _apiController!!.post("auth/signup", body, ::authSignupReply, this)
  }

  fun onNavBack()
  {
    val intent = Intent(this, SignInViewModel::class.java)
    startActivity(intent)
  }

  private fun authSignupReply(code: Int?, bodyRecv: String?)
  {
    val msg = "$code: $bodyRecv"
    Log.d(_tag, msg)
    if (code != 201)
    {
      _errorModel!!.mainMsg!!.set("")
      _errorModel.mainMsg!!.set(bodyRecv)
      return
    }
    val bodySend: MutableMap<String?, String?>? = mutableMapOf()
    bodySend!!["username"] = _user!!.email
    bodySend["password"] = _user.password
    val bodySerialized = _jsonController!!.serialize(bodySend)
    _apiController!!.post("auth/login", bodySerialized, ::authLoginReply, this)
  }

  private fun authLoginReply(code: Int?, body: String?)
  {
    val msg = "$code: $body"
    Log.d(_tag, msg)
    val intent = Intent(this, HomeViewModel::class.java)
    startActivity(intent)
  }
}
