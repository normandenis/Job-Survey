package jobtrends.jobsurvey.fragment

import android.annotation.SuppressLint
import android.databinding.DataBindingUtil
import android.os.Bundle
import android.support.v4.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import jobtrends.jobsurvey.R
import jobtrends.jobsurvey.adapter.AnswerAdapter
import jobtrends.jobsurvey.dagger.App
import jobtrends.jobsurvey.databinding.FragmentQuestionBinding
import jobtrends.jobsurvey.model.QuestionModel
import jobtrends.jobsurvey.model.UserAnswerModel

@SuppressLint("ValidFragment")
class QuestionFragment : Fragment {
    private val questionModel: QuestionModel
    private val userAnswerModel: UserAnswerModel
    private val answerAdapter: AnswerAdapter

    @SuppressLint("ValidFragment")
    constructor(userAnswerModel: UserAnswerModel, questionModel: QuestionModel) : super() {
        App.component.inject(this)
        this.questionModel = questionModel
        this.userAnswerModel = userAnswerModel
        answerAdapter = AnswerAdapter(this.questionModel.answers, this.userAnswerModel)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val binding: FragmentQuestionBinding =
            DataBindingUtil.inflate(inflater, R.layout.fragment_question, container, false)
        binding.questionFragment = this
        binding.questionModel = questionModel
        binding.answerAdapter = answerAdapter
        return binding.root
    }
}
