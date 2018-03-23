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

class SettingViewModel : Fragment()
{
  val appBarBtn = serviceController!!.getInstance<Button>()
  val startSurveyModel = serviceController!!.getInstance<StartSurveyModel>()
  private val jsonController = serviceController!!.getInstance<JsonController>()
  private val apiController = serviceController!!.getInstance<APIController>()
  var userModel = serviceController!!.getInstance<UserModel>()
  private val TAG = "SettingViewModel"
  private var myView: View? = null
  private var dialog: Dialog? = null
  private var news : Boolean? = null
  private var jobaymax : Boolean? = null

  override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                            savedInstanceState: Bundle?): View?
  {
    val binding: SettingViewBinding = DataBindingUtil
      .inflate(inflater, R.layout.setting_view, container, false)
    binding.vm = this

    appBarBtn.setBackgroundResource(R.drawable.ic_close_orange_32dp)
    appBarBtn.setOnClickListener { onNavStartSurvey() }

    dialog = Dialog(context)

    myView = binding.root
    serviceController!!.register(myView)
    return myView
  }

  fun onNavStartSurvey()
  {
    apiController.get("jobaymax/me", ::firstResponse, context!!)
  }

  fun firstResponse(response: String)
  {
    Log.i(TAG, response)
    val startSurveyModel = jsonController.deserialize<StartSurveyModel>(response)
    serviceController!!.register(startSurveyModel, true)
    val fragment = StartSurveyViewModel()
    fragment.startSurveyModel = startSurveyModel
    val transaction = fragmentManager!!.beginTransaction()
    transaction.replace(R.id.fragment_app_bar_nav_drawer_0, fragment)
    transaction.addToBackStack(null)
    transaction.commit()
  }

  fun onClick()
  {
    val fragment = ProfileViewModel()
    val transaction = fragmentManager?.beginTransaction()
    transaction?.replace(R.id.fragment_app_bar_nav_drawer_0, fragment)
    transaction?.addToBackStack(null)
    transaction?.commit()
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
    jobaymax = userModel.jobaymax.get()
    news = userModel.newsletter.get()
    val bind: NotificationPopupViewBinding = DataBindingUtil
      .inflate(LayoutInflater.from(context), R.layout.notification_popup_view,
               myView as ViewGroup, false)
    bind.vm = this
    dialog!!.setContentView(bind.root)
    dialog!!.show()
  }

  fun onNotifYes()
  {
    val tmpUser = User()
    tmpUser.merge(userModel)
    val json = jsonController.serialize(tmpUser)
    apiController.put("auth/user", json, ::myResponse, context!!)
  }


  fun myResponse(response: String)
  {
    apiController.get("auth/user/me", ::secondResponse, context!!)
  }

  private fun secondResponse(response: String)
  {
    val updatedUser = jsonController.deserialize<User>(response)
    val user = serviceController!!.getInstance<User>()
    val userModel = serviceController!!.getInstance<UserModel>()
    user.merge(updatedUser)
    userModel.merge(updatedUser)
    val json = jsonController.serialize(user)
    Log.d(TAG, json)
    val preferences = PreferenceManager.getDefaultSharedPreferences(activity)
    val editor = preferences.edit()
    editor.putString(User::class.java.simpleName, json)
    editor.apply()
    dialog!!.hide()
  }

  fun onNotifNo()
  {
    userModel.jobaymax.set(jobaymax)
    userModel.newsletter.set(news)
    dialog!!.hide()
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
