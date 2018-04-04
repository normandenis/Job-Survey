package jobtrends.jobsurvey.viewmodel

import android.databinding.DataBindingUtil
import android.databinding.ObservableField
import android.os.Bundle
import android.support.v4.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import jobtrends.jobsurvey.R
import jobtrends.jobsurvey.databinding.StartSurveyViewBinding
import jobtrends.jobsurvey.model.StartSurveyModel
import jobtrends.jobsurvey.service.serviceController

class StartSurveyViewModel : Fragment()
{
  var themeViewModel: ThemeViewModel? = null
  var startSurveyModel: StartSurveyModel? = null
  var myview: View? = null
  val appBarBtn = serviceController!!.getInstance<Button>()
  private val TAG = "StartSurveyViewModel"

  override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                            savedInstanceState: Bundle?): View?
  {
    val binding: StartSurveyViewBinding = DataBindingUtil
      .inflate(inflater, R.layout.start_survey_view, container, false)
    myview = binding.root
    binding.vm = this

    appBarBtn.setBackgroundResource(R.drawable.ic_person_orange_32dp)
    appBarBtn.setOnClickListener { onNavSetting() }

    themeViewModel = ThemeViewModel(startSurveyModel!!.themes !!)
    themeViewModel!!.fragmentManager = fragmentManager

    return myview
  }

  fun onNavSetting()
  {
    val fragment = SettingViewModel()
    val transaction = fragmentManager!!.beginTransaction()
    transaction.replace(R.id.fragment_app_bar_nav_drawer_0, fragment)
    transaction.addToBackStack(null)
    transaction.commit()
  }
}