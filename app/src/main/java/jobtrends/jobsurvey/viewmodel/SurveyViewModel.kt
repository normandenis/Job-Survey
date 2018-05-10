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
import android.widget.LinearLayout
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

class SurveyViewModel : Fragment
{
  private var _view: View?
  private var _pagerAdapterController: PagerAdapterController?
  var survey: Survey?
  private var _dialog: Dialog?
  private val _apiController: APIController?
  private val _jsonController: JsonController?
  private val _endSurvey: EndSurvey?
  private val _appBarBtn: Button?
  private val _tag: String?

  constructor() : super()
  {
    _view = null
    _pagerAdapterController = null
    survey = null
    _dialog = null
    _apiController = serviceController!!.getInstance()
    _jsonController = serviceController!!.getInstance()
    _endSurvey = serviceController!!.getInstance()
    _appBarBtn = serviceController!!.getInstance()
    _tag = "SurveyViewModel"
  }

  override fun onViewCreated(view: View, savedInstanceState: Bundle?)
  {
    super.onViewCreated(view, savedInstanceState)

    _appBarBtn!!
      .setBackgroundResource(R.drawable.ic_close_orange_32dp)
    _appBarBtn.setOnClickListener { onCloseSurvey() }

    val list: MutableList<Fragment> = mutableListOf()
    _endSurvey!!.surveyId = survey!!.id
    _endSurvey.answers = mutableListOf()
    survey!!.questions!!.mapTo(list, ::newInstance)
    _pagerAdapterController = PagerAdapterController(fragmentManager, list)
    val pager: ViewPager? = getView()!!.findViewById(R.id.pager)
    pager!!.offscreenPageLimit = survey!!.questions!!.size
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
                                      if (position == list.size-1)
                                      {
                                        _appBarBtn
                                          .setBackgroundResource(R.drawable.ic_check_orange_32dp)
                                        _appBarBtn.setOnClickListener { onValidateSurvey() }
                                      } else
                                      {
                                        _appBarBtn
                                          .setBackgroundResource(R.drawable.ic_close_orange_32dp)
                                        _appBarBtn.setOnClickListener { onCloseSurvey() }
                                      }
                                    }
                                  })
  }

  override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                            savedInstanceState: Bundle?): View?
  {
    val binding: SurveyViewBinding = DataBindingUtil
      .inflate(inflater, R.layout.survey_view, container, false)
    binding.vm = this
    _dialog = Dialog(context)
    _view = binding.root
    serviceController!!.register(_view, true)
    return _view
  }

  private fun newInstance(question: Question): Fragment
  {
    val reply = Reply()
    reply.questionId = question.id
    val fragment = QuestionViewModel(reply, question)
    _endSurvey!!.answers!!.add(reply)
    return fragment
  }

  fun onValidateSurvey()
  {
    val bind: ValidatePopupViewBinding = DataBindingUtil
      .inflate(LayoutInflater.from(context), R.layout.validate_popup_view,
               _view as ViewGroup, false)
    bind.vm = this
    _dialog!!.setContentView(bind.root)
    _dialog!!.show()
  }

  fun onCloseSurvey()
  {
    val bind: CancelPopupViewBinding = DataBindingUtil
      .inflate(LayoutInflater.from(context), R.layout.cancel_popup_view,
               _view as ViewGroup, false)
    bind.vm = this
    _dialog!!.setContentView(bind.root)
    _dialog!!.show()
  }

  fun onValideYes()
  {
    _dialog!!.hide()
    val fragment = EndSurveyViewModel()
    val transaction = fragmentManager!!.beginTransaction()
    transaction.replace(R.id.fragment_app_bar_nav_drawer_0, fragment)
    transaction.addToBackStack(null)
    transaction.commit()
  }

  fun onValideNo()
  {
    _dialog!!.hide()
  }

  fun onCloseYes()
  {
    _dialog!!.hide()
    _apiController!!.get("jobaymax/me", ::jobaymaxMeReply, context!!)
  }

  private fun jobaymaxMeReply(code: Int?, body: String?)
  {
    val msg = "$code: $body"
    Log.i(_tag, msg)
    val startSurveyModel = _jsonController!!.deserialize<StartSurveyModel>(body)
    serviceController!!.register(startSurveyModel, true)
    val fragment = StartSurveyViewModel(startSurveyModel)
    val transaction = fragmentManager!!.beginTransaction()
    transaction.replace(R.id.fragment_app_bar_nav_drawer_0, fragment)
    transaction.addToBackStack(null)
    transaction.commit()
  }

  fun onCloseNo()
  {
    _dialog!!.hide()
  }
}