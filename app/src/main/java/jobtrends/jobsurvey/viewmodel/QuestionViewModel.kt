package jobtrends.jobsurvey.viewmodel

import android.annotation.SuppressLint
import android.databinding.DataBindingUtil
import android.os.Bundle
import android.support.v4.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import jobtrends.jobsurvey.R
import jobtrends.jobsurvey.databinding.QuestionViewBinding
import jobtrends.jobsurvey.model.Question
import jobtrends.jobsurvey.model.Reply

@SuppressLint("ValidFragment")
class QuestionViewModel : Fragment
{
  private val _question: Question?
  private val _reply: Reply?
  private val _answerViewModel: AnswerViewModel?
  private var _view: View?

  @SuppressLint("ValidFragment")
  constructor(reply: Reply?, question: Question?) : super()
  {
    _question = question
    _reply = reply

    _answerViewModel = AnswerViewModel(_question!!.answers, _reply)

    _view = null
  }


  override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                            savedInstanceState: Bundle?): View?
  {
    val binding: QuestionViewBinding? = DataBindingUtil
      .inflate(inflater, R.layout.question_view, container, false)
    _view = binding!!.root
    binding.vm = this
    binding.question = _question
    binding.answerViewModel = _answerViewModel
    return _view
  }
}
