package jobtrends.jobsurvey.viewmodel

import android.databinding.DataBindingUtil
import android.os.Bundle
import android.preference.PreferenceManager
import android.support.v4.app.Fragment
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import jobtrends.jobsurvey.R
import jobtrends.jobsurvey.databinding.ProfileViewBinding
import jobtrends.jobsurvey.model.User
import jobtrends.jobsurvey.model.UserModel
import jobtrends.jobsurvey.service.APIController
import jobtrends.jobsurvey.service.JsonController
import jobtrends.jobsurvey.service.serviceController

class ProfileViewModel : Fragment
{
  var userModel = serviceController!!.getInstance<UserModel>()
  private var _user = serviceController!!.getInstance<User>()
  private val jsonController = serviceController!!.getInstance<JsonController>()
  private val apiController = serviceController!!.getInstance<APIController>()
  private val _tag = "ProfileViewModel"

  constructor() : super()
  {
    userModel.password!!.set("")
  }

  override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                            savedInstanceState: Bundle?): View?
  {
    val binding: ProfileViewBinding = DataBindingUtil
      .inflate(inflater, R.layout.profile_view, container, false)
    val view = binding.root
    binding.vm = this
    return view
  }

  fun onClick()
  {
    val tmpUser = User()
    tmpUser.merge(userModel)
    val json = jsonController.serialize(tmpUser)
    apiController.put("auth/user", json, ::authUserReply, context!!)
  }

  private fun authUserReply(code: Int?, body: String?)
  {
    val msg = "$code: $body"
    Log.i(_tag, msg)
    apiController.get("auth/user/me", ::authUserMeReply, context!!)
  }

  private fun authUserMeReply(code: Int?, body: String?)
  {
    val msg = "$code: $body"
    Log.i(_tag, msg)

    val updatedUser = jsonController.deserialize<User>(body)
    _user.merge(updatedUser)
    userModel.merge(updatedUser)
    val json = jsonController.serialize(_user)
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
