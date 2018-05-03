package jobtrends.jobsurvey.viewmodel

import android.databinding.DataBindingUtil
import android.os.Bundle
import android.support.v4.app.Fragment
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import jobtrends.jobsurvey.R
import jobtrends.jobsurvey.databinding.EndSurveyViewBinding
import jobtrends.jobsurvey.model.EndSurvey
import jobtrends.jobsurvey.model.StartSurveyModel
import jobtrends.jobsurvey.service.APIController
import jobtrends.jobsurvey.service.JsonController
import jobtrends.jobsurvey.service.serviceController

class EndSurveyViewModel : Fragment()
{
  var binding: EndSurveyViewBinding? = null
  private val _appBarBtn = serviceController!!.getInstance<Button>()
  val jsonController = serviceController!!.getInstance<JsonController>()
  val apiController = serviceController!!.getInstance<APIController>()
  private val _endSurvey = serviceController!!.getInstance<EndSurvey>()
  private val _tag = "EndSurveyViewModel"

  override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                            savedInstanceState: Bundle?): View
  {
    binding = DataBindingUtil.inflate(inflater, R.layout.end_survey_view, container, false)
    binding!!.vm = this
    _appBarBtn.setBackgroundResource(R.drawable.ic_close_orange_32dp)
    _appBarBtn.setOnClickListener { onValidateSurvey() }
    return binding!!.root
  }

  fun onValidateSurvey()
  {
    val endSurveySerialiazed = jsonController.serialize(_endSurvey)
    apiController.post("jobaymax/result", endSurveySerialiazed, ::jobaymaxResultReply, context!!)
  }

  private fun jobaymaxResultReply(code: Int?, body: String?)
  {
    val msg = "$code: $body"
    Log.i(_tag, msg)
    apiController.get("jobaymax/me", ::jobaymaxMeReply, context!!)
  }

  private fun jobaymaxMeReply(code: Int?, body: String?)
  {
    val msg = "$code: $body"
    Log.i(_tag, msg)
    val startSurveyModel = jsonController.deserialize<StartSurveyModel>(body)
    serviceController!!.register(startSurveyModel, true)
    val fragment = StartSurveyViewModel(startSurveyModel)
    val transaction = fragmentManager!!.beginTransaction()
    transaction.replace(R.id.fragment_app_bar_nav_drawer_0, fragment)
    transaction.addToBackStack(null)
    transaction.commit()
  }
}
