package jobtrends.job_aymax.viewmodel

import android.databinding.DataBindingUtil
import android.os.Bundle
import android.support.v4.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import jobtrends.job_aymax.R
import jobtrends.job_aymax.databinding.SurveyContentViewBinding
import jobtrends.job_aymax.model.SurveyQuestionModel
import jobtrends.job_aymax.service.ServiceController.Companion.jsonController

class SurveyContentViewModel : Fragment()
{
	var surveyQuestionModel : SurveyQuestionModel? = null
	var surveyAnswerViewModel : SurveyAnswerViewModel? = null

	override fun onCreateView(inflater : LayoutInflater, container : ViewGroup?, savedInstanceState : Bundle?) : View?
	{
		val binding : SurveyContentViewBinding = DataBindingUtil.inflate(inflater, R.layout.survey_content_view, container, false)
		val view = binding.root
		binding.vm = this
		val strModel = arguments?.getString("survey")
		surveyQuestionModel = jsonController.deserialize<SurveyQuestionModel>(strModel.toString())
		surveyAnswerViewModel = SurveyAnswerViewModel(surveyQuestionModel?.answers !!)
		return view
	}
}
