package jobtrends.jobsurvey.viewmodel

import android.app.Dialog
import android.content.Intent
import android.databinding.DataBindingUtil
import android.os.Bundle
import android.preference.PreferenceManager
import android.support.v4.app.Fragment
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import jobtrends.jobsurvey.R
import jobtrends.jobsurvey.databinding.NotificationPopupViewBinding
import jobtrends.jobsurvey.databinding.SettingViewBinding
import jobtrends.jobsurvey.model.StartSurveyModel
import jobtrends.jobsurvey.model.User
import jobtrends.jobsurvey.model.UserModel
import jobtrends.jobsurvey.service.APIController
import jobtrends.jobsurvey.service.JsonController
import jobtrends.jobsurvey.service.serviceController

class SettingViewModel : Fragment
{
  private val _appBarBtn: Button?
  private val _startSurveyModel: StartSurveyModel?
  private val _jsonController: JsonController?
  private val _apiController: APIController?
  private val _userModel: UserModel?
  private val _tag: String?
  private var _dialog: Dialog?
  private var _view: View?
  private var _news: Boolean?
  private var _jobaymax: Boolean?

  constructor() : super()
  {
    _tag = "SettingViewModel"
    _news = false
    _jobaymax = false

    _apiController = serviceController!!.getInstance()
    _appBarBtn = serviceController!!.getInstance()
    _startSurveyModel = serviceController!!.getInstance()
    _jsonController = serviceController!!.getInstance()
    _userModel = serviceController!!.getInstance()

    _dialog = null
    _view = null
  }


  override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                            savedInstanceState: Bundle?): View?
  {
    val binding: SettingViewBinding = DataBindingUtil
      .inflate(inflater, R.layout.setting_view, container, false)
    binding.vm = this
    binding.startSurveyModel = _startSurveyModel
    binding.userModel = _userModel

    _dialog = Dialog(context)

    _appBarBtn!!.setBackgroundResource(R.drawable.ic_close_orange_32dp)
    _appBarBtn.setOnClickListener { onNavStartSurvey() }

    _view = binding.root
    serviceController!!.register(_view)
    return _view
  }

  private fun onNavStartSurvey()
  {
    _apiController!!.get("jobaymax/me", ::jobaymaxMeReply, context)
  }

  private fun jobaymaxMeReply(code: Int?, body: String?)
  {
    val msg = "$code: $body"
    Log.d(_tag, msg)
    val startSurveyModel = _jsonController!!.deserialize<StartSurveyModel>(body)
    serviceController!!.register(startSurveyModel, true)
    val fragment = StartSurveyViewModel(startSurveyModel)
    val transaction = fragmentManager!!.beginTransaction()
    transaction.replace(R.id.fragment_app_bar_nav_drawer_0, fragment)
    transaction.addToBackStack(null)
    transaction.commit()
  }

  fun onClick()
  {
    val fragment = ProfileViewModel()
    val transaction = fragmentManager!!.beginTransaction()
    transaction.replace(R.id.fragment_app_bar_nav_drawer_0, fragment)
    transaction.addToBackStack(null)
    transaction.commit()
  }

  fun onNavFAQ()
  {
    val fragment = FAQViewModel()
    val transaction = fragmentManager!!.beginTransaction()
    transaction.replace(R.id.fragment_app_bar_nav_drawer_0, fragment)
    transaction.addToBackStack(null)
    transaction.commit()
  }

  fun onNavNotif()
  {
    _jobaymax = _userModel!!.jobaymax!!.get()
    _news = _userModel.newsletter!!.get()
    val bind: NotificationPopupViewBinding = DataBindingUtil
      .inflate(LayoutInflater.from(context), R.layout.notification_popup_view,
               _view as ViewGroup, false)
    bind.vm = this
    bind.userModel = _userModel
    _dialog!!.setContentView(bind.root)
    _dialog!!.show()
  }

  fun onNotifYes()
  {
    val tmpUser = User()
    tmpUser.merge(_userModel)
    val json = _jsonController!!.serialize(tmpUser)
    _apiController!!.put("auth/user/me", json, ::authUserReply, context)
  }


  private fun authUserReply(code: Int?, body: String?)
  {
    val msg = "$code: $body"
    Log.d(_tag, msg)
    _apiController!!.get("auth/user/me", ::authUserMeReply, context)
  }

  private fun authUserMeReply(code: Int?, body: String?)
  {
    val msg = "$code: $body"
    Log.d(_tag, msg)
    val updatedUser = _jsonController!!.deserialize<User>(body)
    val user = serviceController!!.getInstance<User>()
    val userModel = serviceController!!.getInstance<UserModel>()
    user.merge(updatedUser)
    userModel.merge(updatedUser)
    val json = _jsonController.serialize(user)
    Log.d(_tag, json)
    val preferences = PreferenceManager.getDefaultSharedPreferences(activity)
    val editor = preferences.edit()
    editor.putString(User::class.java.simpleName, json)
    editor.apply()
    _dialog!!.hide()
  }

  fun onNotifNo()
  {
    _userModel!!.jobaymax!!.set(_jobaymax)
    _userModel.newsletter!!.set(_news)
    _dialog!!.hide()
  }

  fun onNavSignIn()
  {
    val preferences = PreferenceManager.getDefaultSharedPreferences(activity)
    val editor = preferences.edit()
    editor.putString(User::class.java.simpleName, "")
    editor.apply()
    val intent = Intent(activity, SignInViewModel::class.java)
    startActivity(intent)
  }
}
