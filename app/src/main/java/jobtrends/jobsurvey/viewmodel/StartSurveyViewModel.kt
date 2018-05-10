package jobtrends.jobsurvey.viewmodel

import android.annotation.SuppressLint
import android.databinding.DataBindingUtil
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

@SuppressLint("ValidFragment")
class StartSurveyViewModel : Fragment
{
  private var _themeViewModel: ThemeViewModel?
  private var _startSurveyModel: StartSurveyModel?
  private var _view: View?
  private val _appBarBtn: Button?
  private val _tag: String?

  @SuppressLint("ValidFragment")
  constructor(startSurveyModel: StartSurveyModel?) : super()
  {
    _tag = "StartSurveyViewModel"
    _themeViewModel = null
    _view = null
    _startSurveyModel = startSurveyModel
    _appBarBtn = serviceController!!.getInstance()
  }

  override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                            savedInstanceState: Bundle?): View?
  {

    val binding: StartSurveyViewBinding = DataBindingUtil
      .inflate(inflater, R.layout.start_survey_view, container, false)
    _view = binding.root

    _themeViewModel = ThemeViewModel(_startSurveyModel!!.themes!!, fragmentManager)

    _appBarBtn!!.setBackgroundResource(R.drawable.ic_person_orange_32dp)
    _appBarBtn.setOnClickListener { onNavSetting() }

    binding.vm = this
    binding.startSurveyModel = _startSurveyModel
    binding.themeViewModel = _themeViewModel

    return _view
  }

  private fun onNavSetting()
  {
    val fragment = SettingViewModel()
    val transaction = fragmentManager!!.beginTransaction()
    transaction.replace(R.id.fragment_app_bar_nav_drawer_0, fragment)
    transaction.addToBackStack(null)
    transaction.commit()
  }
}
