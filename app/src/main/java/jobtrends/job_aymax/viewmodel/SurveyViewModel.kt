package jobtrends.job_aymax.viewmodel

import android.databinding.DataBindingUtil
import android.support.v4.app.Fragment
import android.os.Bundle
import android.support.v4.view.ViewPager
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import jobtrends.job_aymax.R
import jobtrends.job_aymax.databinding.SurveyViewBinding
import jobtrends.job_aymax.model.Question
import jobtrends.job_aymax.model.Reply
import jobtrends.job_aymax.model.Survey
import jobtrends.job_aymax.service.PagerAdapterController
import jobtrends.job_aymax.service.ServiceController.Companion.endSurvey
import jobtrends.job_aymax.service.ServiceController.Companion.jsonController

class SurveyViewModel : Fragment()
{
	private var _pagerAdapterController : PagerAdapterController? = null
	var survey : Survey? = null

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
	}

	override fun onCreateView(inflater : LayoutInflater, container : ViewGroup?,
	                          savedInstanceState : Bundle?) : View?
	{
		val binding : SurveyViewBinding =
				DataBindingUtil.inflate(inflater, R.layout.survey_view, container, false)
		val view = binding.root
		binding.vm = this
		return view
	}
}
