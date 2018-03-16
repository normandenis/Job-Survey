package jobtrends.jobsurvey.viewmodel

import android.databinding.DataBindingUtil
import android.os.Bundle
import android.support.v4.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import jobtrends.jobsurvey.R
import jobtrends.jobsurvey.databinding.EndSurveyViewBinding
import jobtrends.jobsurvey.model.StartSurveyModel
import jobtrends.jobsurvey.service.ServiceController
import jobtrends.jobsurvey.service.ServiceController.Companion.endSurvey
import jobtrends.jobsurvey.service.ServiceController.Companion.jsonController
import jobtrends.jobsurvey.service.ServiceController.Companion.startSurveyModel

class EndSurveyViewModel : Fragment()
{
  var binding : EndSurveyViewBinding? = null
  override fun onCreateView(inflater : LayoutInflater, container : ViewGroup?,
                            savedInstanceState : Bundle?) : View
  {
    binding = DataBindingUtil.inflate(inflater, R.layout.end_survey_view, container, false)
    binding?.vm = this
    ServiceController.appBarBtn?.setBackgroundResource(R.drawable.ic_close_orange_32dp)
    ServiceController.appBarBtn?.setOnClickListener { onValidateSurvey() }
    return binding?.root !!
  }

  fun onValidateSurvey()
  {
    val endSurveySerialiazed = jsonController.serialize(endSurvey)
    ServiceController.apiController
        .post("https://api.dev.jobtrends.io/jobsurvey/result", endSurveySerialiazed, ::firstResponse,
              context !!)
  }

  fun firstResponse(response : String)
  {
    println(response)
    ServiceController.apiController
        .get("https://api.dev.jobtrends.io/jobsurvey/me", ::secondResponse, context !!)
  }

  fun secondResponse(response : String)
  {
    val fragment = StartSurveyViewModel()
    fragment.startSurveyModel = ServiceController.jsonController
        .deserialize<StartSurveyModel>(response)
    startSurveyModel = fragment.startSurveyModel
    val transaction = fragmentManager?.beginTransaction()
    transaction?.replace(R.id.fragment_app_bar_nav_drawer_0, fragment)
    transaction?.addToBackStack(null)
    transaction?.commit()
  }
}
