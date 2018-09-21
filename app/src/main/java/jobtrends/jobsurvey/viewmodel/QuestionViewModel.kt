package jobtrends.jobsurvey.viewmodel

import android.annotation.SuppressLint
import android.databinding.DataBindingUtil
import android.os.Bundle
import android.support.v4.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import jobtrends.jobsurvey.R
import jobtrends.jobsurvey.adapter.AnswerAdapter
import jobtrends.jobsurvey.databinding.QuestionViewBinding
import jobtrends.jobsurvey.model.QuestionModel
import jobtrends.jobsurvey.model.UserAnswerModel

@SuppressLint("ValidFragment")
class QuestionViewModel : Fragment {
    private val _questionModel: QuestionModel?
    private val _userAnswerModel: UserAnswerModel?
    private val _answerAdapter: AnswerAdapter?
    private var _view: View?

    @SuppressLint("ValidFragment")
    constructor(userAnswerModel: UserAnswerModel?, questionModel: QuestionModel?) : super() {
        _questionModel = questionModel
        _userAnswerModel = userAnswerModel

        _answerAdapter = AnswerAdapter(_questionModel?.answers, _userAnswerModel)

        _view = null
    }


    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        val binding: QuestionViewBinding? = DataBindingUtil.inflate(inflater, R.layout.question_view, container, false)
        _view = binding?.root
        binding?.vm = this
        binding?.question = _questionModel
        binding?.answerAdapter = _answerAdapter
        return _view
    }
}
