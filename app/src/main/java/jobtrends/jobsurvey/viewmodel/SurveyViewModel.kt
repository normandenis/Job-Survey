package jobtrends.jobsurvey.viewmodel

import android.app.Dialog
import android.databinding.DataBindingUtil
import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v4.view.ViewPager
import android.support.v4.view.ViewPager.OnPageChangeListener
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import jobtrends.jobsurvey.R
import jobtrends.jobsurvey.databinding.CancelPopupViewBinding
import jobtrends.jobsurvey.databinding.SurveyViewBinding
import jobtrends.jobsurvey.databinding.ValidatePopupViewBinding
import jobtrends.jobsurvey.model.EndSurvey
import jobtrends.jobsurvey.model.Question
import jobtrends.jobsurvey.model.Reply
import jobtrends.jobsurvey.model.StartSurveyModel
import jobtrends.jobsurvey.model.Survey
import jobtrends.jobsurvey.service.APIController
import jobtrends.jobsurvey.service.JsonController
import jobtrends.jobsurvey.service.PagerAdapterController
import jobtrends.jobsurvey.service.serviceController

class SurveyViewModel : Fragment()
{
  var myView: View? = null
  private var _pagerAdapterController: PagerAdapterController? = null
  var survey: Survey? = null
  var dialog: Dialog? = null
  val apiController = serviceController!!.getInstance<APIController>()
  val jsonController = serviceController!!.getInstance<JsonController>()
  val endSurvey = serviceController!!.getInstance<EndSurvey>()
  val appBarBtn = serviceController!!.getInstance<Button>()
  private val TAG = "SurveyViewModel"

  override fun onViewCreated(view: View, savedInstanceState: Bundle?)
  {
    super.onViewCreated(view, savedInstanceState)
    val list: MutableList<Fragment> = mutableListOf()
    endSurvey.surveyId = survey!!.id
    endSurvey.answers = mutableListOf()
    for (question in survey!!.questions!!)
    {
      list.add(newInstance(question))
    }

    _pagerAdapterController = PagerAdapterController(fragmentManager, list)
    val pager: ViewPager = getView()!!.findViewById(R.id.pager)
    pager.adapter = _pagerAdapterController
    pager.addOnPageChangeListener(object : OnPageChangeListener
                                  {
                                    override fun onPageScrollStateChanged(state: Int)
                                    {
                                    }

                                    override fun onPageScrolled(position: Int,
                                                                positionOffset: Float,
                                                                positionOffsetPixels: Int)
                                    {
                                    }

                                    override fun onPageSelected(position: Int)
                                    {
                                      if (position == list.size - 1)
                                      {
                                        appBarBtn.setBackgroundResource(R.drawable.ic_check_orange_32dp)
                                        appBarBtn.setOnClickListener { onValidateSurvey() }
                                      }
                                      else
                                      {
                                        appBarBtn.setBackgroundResource(R.drawable.ic_close_orange_32dp)
                                        appBarBtn.setOnClickListener { onCloseSurvey() }
                                      }
                                    }
                                  })
  }

  override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                            savedInstanceState: Bundle?): View?
  {
    val binding: SurveyViewBinding = DataBindingUtil.inflate(inflater, R.layout.survey_view, container, false)
    binding.vm = this
    dialog = Dialog(context!!)
    myView = binding.root
    serviceController!!.register(myView!!, true)
    return myView
  }

  private fun newInstance(question: Question): Fragment
  {
    val reply = Reply()
    reply.questionId = question.id
    val fragment = QuestionViewModel()
    fragment.question = question
    fragment.reply = reply
    endSurvey.answers!!.add(reply)
    return fragment
  }

  fun onValidateSurvey()
  {
    val bind: ValidatePopupViewBinding = DataBindingUtil
      .inflate(LayoutInflater.from(context), R.layout.validate_popup_view,
               myView as ViewGroup, false)
    bind.vm = this
    dialog!!.setContentView(bind.root)
    dialog!!.show()
  }

  fun onCloseSurvey()
  {
    val bind: CancelPopupViewBinding = DataBindingUtil
      .inflate(LayoutInflater.from(context), R.layout.cancel_popup_view,
               myView as ViewGroup, false)
    bind.vm = this
    dialog!!.setContentView(bind.root)
    dialog!!.show()
  }

  fun onValideYes()
  {
    dialog!!.hide()
    val fragment = EndSurveyViewModel()
    val transaction = fragmentManager!!.beginTransaction()
    transaction.replace(R.id.fragment_app_bar_nav_drawer_0, fragment)
    transaction.addToBackStack(null)
    transaction.commit()
  }

  fun onValideNo()
  {
    dialog!!.hide()
  }

  fun onCloseYes()
  {
    dialog!!.hide()
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

  fun onCloseNo()
  {
    dialog!!.hide()
  }
}