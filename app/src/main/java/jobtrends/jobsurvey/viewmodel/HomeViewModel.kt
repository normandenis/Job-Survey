package jobtrends.jobsurvey.viewmodel

import android.annotation.SuppressLint
import android.databinding.DataBindingUtil
import android.os.Bundle
import android.preference.PreferenceManager
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.Toolbar
import android.util.Log
import android.view.View
import android.widget.Button
import jobtrends.jobsurvey.R
import jobtrends.jobsurvey.databinding.HomeViewBinding
import jobtrends.jobsurvey.model.StartSurveyModel
import jobtrends.jobsurvey.model.User
import jobtrends.jobsurvey.model.UserModel
import jobtrends.jobsurvey.service.APIController
import jobtrends.jobsurvey.service.JsonController
import jobtrends.jobsurvey.service.serviceController

class HomeViewModel : AppCompatActivity()
{
  private val _tag = "HomeViewModel"
  private val _jsonController = serviceController!!.getInstance<JsonController>()
  private val _apiController = serviceController!!.getInstance<APIController>()

  @SuppressLint("CommitPrefEdits")
  override fun onCreate(savedInstanceState: Bundle?)
  {
    super.onCreate(savedInstanceState)
    val binding: HomeViewBinding = DataBindingUtil.setContentView(this, R.layout.home_view)
    binding.vm = this
    val toolbar = findViewById<View>(R.id.toolbar) as Toolbar
    setSupportActionBar(toolbar)

    serviceController!!.register(findViewById<Button>(R.id.btn), true)
    serviceController!!.register(binding.root, true)

    _apiController.get("auth/user/me", ::authUserMeReply, this)
    _apiController.get("jobaymax/me", ::jobaymaxReply, this)

    initNotification()
  }

  private fun initNotification()
  {
    val preferences = PreferenceManager.getDefaultSharedPreferences(this)
    val token = preferences.getString("DeviceId", null)
    val msg = token ?: ""
    Log.d(_tag, msg)
    if (token != null && token != "")
    {
      _apiController.post("jobaymax/me/device/$token?type=ANDROID", null, ::jobaymaxMeDeviceTokenReply, this)
    }
  }

  private fun jobaymaxMeDeviceTokenReply(code: Int?, body: String?)
  {
    val msg = "$code: $body"
    Log.d(_tag, msg)
  }

  private fun authUserMeReply(code: Int?, body: String?)
  {
    val msg = "$code: $body"
    Log.i(_tag, msg)
    if (code != 200 && code != 201)
    {
      return
    }
    val updatedUser = _jsonController.deserialize<User>(body)
    val user = serviceController!!.getInstance<User>()
    val userModel = serviceController!!.getInstance<UserModel>()
    user.merge(userModel)
    user.merge(updatedUser)
    userModel.merge(user)
    userModel.merge(updatedUser)
    val json = _jsonController.serialize(user)
    Log.d(_tag, json)
    val preferences = PreferenceManager.getDefaultSharedPreferences(this)
    val editor = preferences.edit()
    editor.putString(User::class.java.simpleName, json)
    editor.apply()
  }

  private fun jobaymaxReply(code: Int?, body: String?)
  {
    val msg = "$code: $body"
    Log.i(_tag, msg)
    if (code != 200 && code != 201)
    {
      return
    }
    val startSurveyModel = _jsonController.deserialize<StartSurveyModel>(body)
    serviceController!!.register(startSurveyModel, true)
    val fragment = StartSurveyViewModel(startSurveyModel)
    val transaction = supportFragmentManager!!.beginTransaction()
    transaction.replace(R.id.fragment_app_bar_nav_drawer_0, fragment)
    transaction.addToBackStack(null)
    transaction.commit()
  }
}