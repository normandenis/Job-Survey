package jobtrends.jobaymax.viewmodel

import android.content.Intent
import android.databinding.DataBindingUtil
import android.os.Bundle
import android.support.v4.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import jobtrends.jobaymax.R
import jobtrends.jobaymax.databinding.SettingViewBinding
import jobtrends.jobaymax.model.StartSurveyModel
import jobtrends.jobaymax.service.ServiceController

class SettingViewModel : Fragment()
{
  var startSurveyModel : StartSurveyModel? = ServiceController.startSurveyModel
  override fun onCreateView(inflater : LayoutInflater, container : ViewGroup?,
                            savedInstanceState : Bundle?) : View?
  {
    val binding : SettingViewBinding = DataBindingUtil
        .inflate(inflater, R.layout.setting_view, container, false)
    val view = binding.root
    binding.vm = this

    ServiceController.appBarBtn?.setBackgroundResource(R.drawable.ic_close_orange_32dp)
    ServiceController.appBarBtn?.setOnClickListener { onNavStartSurvey() }

    return view
  }

  fun onNavStartSurvey()
  {
    ServiceController.apiController
        .get("https://api.dev.jobtrends.io/jobaymax/me", ::firstResponse, context !!)
  }

  fun firstResponse(response : String)
  {
    val fragment = StartSurveyViewModel()
    fragment.startSurveyModel = ServiceController.jsonController
        .deserialize<StartSurveyModel>(response)
    val transaction = fragmentManager?.beginTransaction()
    transaction?.replace(R.id.fragment_app_bar_nav_drawer_0, fragment)
    transaction?.addToBackStack(null)
    transaction?.commit()
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
    val transaction = fragmentManager?.beginTransaction()
    transaction?.replace(R.id.fragment_app_bar_nav_drawer_0, fragment)
    transaction?.addToBackStack(null)
    transaction?.commit()
  }

  fun onNavNotif()
  {
    val dialog = NotificationPopupViewModel()
    dialog.show(fragmentManager, "Salut")
  }

  fun onNavSignIn()
  {
    val intent = Intent(activity, SignInViewModel::class.java)
    startActivity(intent)
  }
}
