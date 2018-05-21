package jobtrends.jobsurvey.viewmodel

import android.databinding.DataBindingUtil
import android.databinding.ObservableField
import android.os.Bundle
import android.preference.PreferenceManager
import android.support.v4.app.Fragment
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import jobtrends.jobsurvey.R
import jobtrends.jobsurvey.databinding.ProfileViewBinding
import jobtrends.jobsurvey.model.ErrorModel
import jobtrends.jobsurvey.model.User
import jobtrends.jobsurvey.model.UserModel
import jobtrends.jobsurvey.service.APIController
import jobtrends.jobsurvey.service.JsonController
import jobtrends.jobsurvey.service.serviceController

class ProfileViewModel : Fragment
{
  private val _userModel: UserModel?
  private val _user: User?
  private val _errorModel: ErrorModel?
  private val _jsonController: JsonController?
  private val _apiController: APIController?
  private val _appBarBtn: Button?
  private val _tag: String?
  private var _stop: Boolean?
  private val _tmpUserModel: UserModel?
  val oldPassword: ObservableField<String?>?

  constructor() : super()
  {
    _tag = "ProfileViewModel"
    _stop = false
    _userModel = serviceController!!.getInstance()
    _user = serviceController!!.getInstance()
    _errorModel = serviceController!!.getInstance()
    _jsonController = serviceController!!.getInstance()
    _apiController = serviceController!!.getInstance()
    _appBarBtn = serviceController!!.getInstance()

    oldPassword = ObservableField()
    _tmpUserModel = UserModel()
    _tmpUserModel.merge(_userModel)
    _tmpUserModel.password!!.set("")
    _tmpUserModel.encryptedPassword!!.set("")

    _appBarBtn!!.setBackgroundResource(R.drawable.ic_arrow_back_orange_512dp)
    _appBarBtn.setOnClickListener { onNavBack() }
  }

  override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                            savedInstanceState: Bundle?): View?
  {
    val binding: ProfileViewBinding = DataBindingUtil
      .inflate(inflater, R.layout.profile_view, container, false)
    binding.vm = this
    binding.userModel = _tmpUserModel
    binding.errorModel = _errorModel
    return binding.root
  }

  private fun onNavBack()
  {
    fragmentManager!!.popBackStack()
  }

  private fun checkInput()
  {
    _stop = false
    _errorModel!!.reset()
    checkInput(_tmpUserModel!!.lastName!!.get(), "^[a-zA-Z ,.'-]+$", _errorModel.lastnameMsg)
    checkInput(_tmpUserModel.firstName!!.get(), "^[a-zA-Z ,.'-]+$", _errorModel.firstnameMsg)
    checkInput(_tmpUserModel.birthday!!.get(), "[0-9]{2}/[0-9]{2}/[0-9]{4}", _errorModel.birthdayMsg)
    checkInput(_tmpUserModel.email!!.get(), "^[a-zA-Z0-9_.+-]+@[a-zA-Z0-9-]+\\.[a-zA-Z0-9-.]+$",
               _errorModel.emailMsg)
    checkInput(_tmpUserModel.metier!!.get(), "^[a-zA-Z ,.'-]+$", _errorModel.jobMsg)
    checkInput(_tmpUserModel.password!!.get(), "^[a-zA-Z0-9]+$", _errorModel.passwordMsg)
    checkPassword()
  }

  private fun isNull(str: String?, error: ObservableField<String?>?): Boolean?
  {
    if (str == null || str == "")
    {
      _stop = true
      error!!.set("Ce champ ne peut pas être vide")
      return true
    }
    return false
  }

  private fun checkPassword()
  {
    if (isNull(oldPassword!!.get(), _errorModel!!.oldPassword) == false)
    {
      if (oldPassword.get() != _userModel!!.encryptedPassword!!.get())
      {
        _stop = true
        _errorModel.oldPassword!!.set("Ce mot de passe ne correspond pas à l'ancien mot de passe")
      }
    }
    if (isNull(_tmpUserModel!!.password!!.get(), _errorModel.passwordMsg) == false
        && isNull(_tmpUserModel.encryptedPassword!!.get(), _errorModel.passwordBisMsg) == false)
    {
      if (_tmpUserModel.password!!.get()!!.length < 8)
      {
        _stop = true
        _errorModel.passwordMsg!!.set("Votre mot de passe est inférieur à 8 charactères")
      }
      if (_tmpUserModel.password!!.get() != _tmpUserModel.encryptedPassword!!.get())
      {
        _stop = true
        _errorModel.passwordBisMsg!!.set("Ce mot de passe est différent du premier")
      }
    }
  }

  private fun checkInput(input: String?, pattern: String?, error: ObservableField<String?>?)
  {
    if (input == null || input == "")
    {
      error!!.set("Ce champ ne peut pas être vide")
      _stop = true
      return
    }
    val regex = Regex(pattern!!)
    val result = input.matches(regex)
    if (!result)
    {
      error!!.set("Ce champ est invalide")
      _stop = true
    }
  }


  fun onClick()
  {
    checkInput()
    if (_stop == true)
    {
      return
    }
    val tmpUser = User()
    tmpUser.merge(_tmpUserModel)
    val json = _jsonController!!.serialize(tmpUser)
    _apiController!!.put("auth/user", json, ::authUserReply, context!!)
  }

  private fun authUserReply(code: Int?, body: String?)
  {
    val msg = "$code: $body"
    Log.i(_tag, msg)
    if (code != 200 && code != 201)
    {
      _errorModel!!.mainMsg!!.set(body)
      return
    }
    _apiController!!.get("auth/user/me", ::authUserMeReply, context!!)
  }

  private fun authUserMeReply(code: Int?, body: String?)
  {
    val msg = "$code: $body"
    Log.i(_tag, msg)
    val updatedUser = _jsonController!!.deserialize<User>(body)
    _user!!.merge(updatedUser)
    _userModel!!.merge(updatedUser)
    val json = _jsonController.serialize(_user)
    Log.d(_tag, json)

    val preferences = PreferenceManager.getDefaultSharedPreferences(activity)
    val editor = preferences.edit()
    editor.putString(User::class.java.simpleName, json)
    editor.apply()

    val fragment = SettingViewModel()
    val transaction = fragmentManager!!.beginTransaction()
    transaction.replace(R.id.fragment_app_bar_nav_drawer_0, fragment)
    transaction.addToBackStack(null)
    transaction.commit()
  }
}
