package jobtrends.job_aymax.viewmodel

import android.databinding.DataBindingUtil
import android.os.Bundle
import android.support.v4.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import jobtrends.job_aymax.R
import jobtrends.job_aymax.databinding.QuestionViewBinding
import jobtrends.job_aymax.model.Question
import jobtrends.job_aymax.model.Reply

class QuestionViewModel : Fragment()
{
	var question : Question? = null
	var reply : Reply? = null
	var answerViewModel : AnswerViewModel? = null

	override fun onCreateView(inflater : LayoutInflater, container : ViewGroup?, savedInstanceState : Bundle?) : View?
	{
		val binding : QuestionViewBinding = DataBindingUtil.inflate(inflater, R.layout.question_view, container, false)
		val view = binding.root
		binding.vm = this
		answerViewModel = AnswerViewModel(question?.answers !!)
		answerViewModel?.reply = reply
		return view
	}
}
