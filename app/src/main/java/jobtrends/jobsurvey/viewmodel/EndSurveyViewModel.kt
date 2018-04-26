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
  var binding : EndSurveyViewBinding? = null
  val appBarBtn = serviceController !!.getInstance<Button>()
  val jsonController = serviceController !!.getInstance<JsonController>()
  val apiController = serviceController !!.getInstance<APIController>()
  val endSurvey = serviceController !!.getInstance<EndSurvey>()
  private val TAG = "EndSurveyViewModel"

  override fun onCreateView(inflater : LayoutInflater, container : ViewGroup?,
                            savedInstanceState : Bundle?) : View
  {
    binding = DataBindingUtil.inflate(inflater, R.layout.end_survey_view, container, false)
    binding !!.vm = this
    appBarBtn.setBackgroundResource(R.drawable.ic_close_orange_32dp)
    appBarBtn.setOnClickListener { onValidateSurvey() }
    return binding !!.root
  }

  fun onValidateSurvey()
  {
    val endSurveySerialiazed = jsonController.serialize(endSurvey)
    apiController.post("jobaymax/result", endSurveySerialiazed, ::firstResponse, context !!)
  }

  fun firstResponse(code: Int, response : String)
  {
    println(response)
    apiController.get("jobaymax/me", ::secondResponse, context !!)
  }

  fun secondResponse(response: String)
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

}
