package jobtrends.jobaymax.viewmodel

import android.databinding.DataBindingUtil
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.Toolbar
import android.view.View
import android.widget.FrameLayout
import jobtrends.jobaymax.R
import jobtrends.jobaymax.databinding.HomeViewBinding
import jobtrends.jobaymax.model.StartSurveyModel
import jobtrends.jobaymax.service.ServiceController
import jobtrends.jobaymax.service.ServiceController.Companion.apiController
import jobtrends.jobaymax.service.ServiceController.Companion.appBarBtn
import jobtrends.jobaymax.service.ServiceController.Companion.startSurveyModel

class HomeViewModel : AppCompatActivity()
{
  override fun onCreate(savedInstanceState : Bundle?)
  {
    super.onCreate(savedInstanceState)
    val binding : HomeViewBinding = DataBindingUtil.setContentView(this, R.layout.home_view)
    binding.vm = this
    val toolbar = findViewById<View>(R.id.toolbar) as Toolbar
    setSupportActionBar(toolbar)

    if (findViewById<FrameLayout>(R.id.fragment_app_bar_nav_drawer_0) != null)
    {
      if (savedInstanceState != null)
      {
        return
      }
      apiController.get("https://api.dev.jobtrends.io/jobaymax/me", ::firstResponse, this)
    }

    appBarBtn = findViewById(R.id.btn)
  }

  fun firstResponse(response : String)
  {
    val fragment = StartSurveyViewModel()
    fragment.startSurveyModel = ServiceController.jsonController
        .deserialize<StartSurveyModel>(response)
    startSurveyModel = fragment.startSurveyModel
    val transaction = supportFragmentManager?.beginTransaction()
    transaction?.replace(R.id.fragment_app_bar_nav_drawer_0, fragment)
    transaction?.addToBackStack(null)
    transaction?.commit()
  }
}