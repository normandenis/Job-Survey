package jobtrends.jobsurvey.viewmodel

import android.annotation.SuppressLint
import android.content.SharedPreferences
import android.databinding.DataBindingUtil
import android.os.Bundle
import android.preference.PreferenceManager
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.Toolbar
import android.util.Log
import android.view.View
import android.widget.Button
import android.widget.FrameLayout
import jobtrends.jobsurvey.R
import jobtrends.jobsurvey.databinding.HomeViewBinding
import jobtrends.jobsurvey.model.StartSurveyModel
import jobtrends.jobsurvey.model.User
import jobtrends.jobsurvey.service.APIController
import jobtrends.jobsurvey.service.JsonController
import jobtrends.jobsurvey.service.serviceController

class HomeViewModel : AppCompatActivity()
{
  private val TAG = "HomeViewModel"
  private val jsonController = serviceController!!.getInstance<JsonController>()
  private val apiController = serviceController!!.getInstance<APIController>()

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


    apiController.get("auth/user/me", ::syncUser, this)
    apiController.get("jobaymax/me", ::firstResponse, this)
  }

  fun syncUser(response: String)
  {
    val updatedUser = jsonController.deserialize<User>(response)
    val user = serviceController!!.getInstance<User>()
    user.merge(updatedUser)
    val json = jsonController.serialize(user)
    Log.d(TAG, json)
    val preferences = PreferenceManager.getDefaultSharedPreferences(this)
    val editor = preferences.edit()
    editor.putString(User::class.java.simpleName, json)
    editor.apply()
  }

  fun firstResponse(response: String)
  {
    Log.i(TAG, response)
    val startSurveyModel = jsonController.deserialize<StartSurveyModel>(response)
    serviceController!!.register(startSurveyModel, true)
    val fragment = StartSurveyViewModel()
    fragment.startSurveyModel = startSurveyModel
    val transaction = supportFragmentManager!!.beginTransaction()
    transaction.replace(R.id.fragment_app_bar_nav_drawer_0, fragment)
    transaction.addToBackStack(null)
    transaction.commit()
  }
}