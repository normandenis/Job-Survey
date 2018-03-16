package jobtrends.jobsurvey.viewmodel

import android.databinding.DataBindingUtil
import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v4.view.ViewPager
import android.support.v4.view.ViewPager.OnPageChangeListener
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import jobtrends.jobsurvey.R
import jobtrends.jobsurvey.databinding.CancelPopupViewBinding
import jobtrends.jobsurvey.databinding.SurveyViewBinding
import jobtrends.jobsurvey.databinding.ValidatePopupViewBinding
import jobtrends.jobsurvey.model.Question
import jobtrends.jobsurvey.model.Reply
import jobtrends.jobsurvey.model.StartSurveyModel
import jobtrends.jobsurvey.model.Survey
import jobtrends.jobsurvey.service.PagerAdapterController
import jobtrends.jobsurvey.service.ServiceController
import jobtrends.jobsurvey.service.ServiceController.Companion.endSurvey
import jobtrends.jobsurvey.service.ServiceController.Companion.startSurveyModel

class SurveyViewModel : Fragment()
{
  var binding : SurveyViewBinding? = null
  private var _pagerAdapterController : PagerAdapterController? = null
  var survey : Survey? = null
  var valDialog : ValidatePopupViewModel? = null
  var canDialog : CancelPopupViewModel? = null
  override fun onViewCreated(view : View, savedInstanceState : Bundle?)
  {
    super.onViewCreated(view, savedInstanceState)
    val list : MutableList<Fragment> = mutableListOf()
    endSurvey.surveyId = survey?.id
    endSurvey.answers = mutableListOf()

    for (question in survey?.questions !!)
    {
      list.add(newInstance(question))
    }

    _pagerAdapterController = PagerAdapterController(fragmentManager, list)
    val pager : ViewPager? = getView()?.findViewById(R.id.pager)
    pager?.adapter = _pagerAdapterController
    pager?.addOnPageChangeListener(object : OnPageChangeListener
                                   {
                                     override fun onPageScrollStateChanged(state : Int)
                                     {
                                     }

                                     override fun onPageScrolled(position : Int,
                                                                 positionOffset : Float,
                                                                 positionOffsetPixels : Int)
                                     {
                                     }

                                     override fun onPageSelected(position : Int)
                                     {
                                       if (position == list.size - 1)
                                       {
                                         ServiceController.appBarBtn?.setBackgroundResource(
                                             R.drawable.ic_check_orange_32dp)
                                         ServiceController.appBarBtn
                                             ?.setOnClickListener { onValidateSurvey() }
                                       }
                                       else
                                       {
                                         ServiceController.appBarBtn?.setBackgroundResource(
                                             R.drawable.ic_close_orange_32dp)
                                         ServiceController.appBarBtn
                                             ?.setOnClickListener { onCloseSurvey() }
                                       }
                                     }
                                   })
  }

  override fun onCreateView(inflater : LayoutInflater, container : ViewGroup?,
                            savedInstanceState : Bundle?) : View?
  {
    binding = DataBindingUtil.inflate(inflater, R.layout.survey_view, container, false)
    binding?.vm = this
    return binding?.root
  }

  private fun newInstance(question : Question) : Fragment
  {
    val reply = Reply()
    reply.questionId = question.id
    val fragment = QuestionViewModel()
    fragment.question = question
    fragment.reply = reply
    endSurvey.answers?.add(reply)
    return fragment
  }

  fun onValidateSurvey()
  {
    valDialog = ValidatePopupViewModel(context)
    val bind : ValidatePopupViewBinding = DataBindingUtil
        .inflate(LayoutInflater.from(context), R.layout.validate_popup_view,
                 binding?.root as ViewGroup?, false)
    bind.vm = this
    valDialog?.setContentView(bind.root)
    valDialog?.show()
  }

  fun onCloseSurvey()
  {
    canDialog = CancelPopupViewModel(context)
    val bind : CancelPopupViewBinding = DataBindingUtil
        .inflate(LayoutInflater.from(context), R.layout.cancel_popup_view,
                 binding?.root as ViewGroup?, false)
    bind.vm = this
    canDialog?.setContentView(bind.root)
    canDialog?.show()
  }

  fun onValideYes()
  {
    valDialog?.hide()
    val fragment = EndSurveyViewModel()
    val transaction = fragmentManager?.beginTransaction()
    transaction?.replace(R.id.fragment_app_bar_nav_drawer_0, fragment)
    transaction?.addToBackStack(null)
    transaction?.commit()
  }

  fun onValideNo()
  {
    valDialog?.hide()
  }

  fun onCloseYes()
  {
    canDialog?.hide()
    ServiceController.apiController
        .get("https://api.dev.jobtrends.io/jobsurvey/me", ::firstResponse, context !!)
  }

  fun onCloseNo()
  {
    canDialog?.hide()
  }

  fun firstResponse(response : String)
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