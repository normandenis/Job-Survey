package jobtrends.jobsurvey.adapter

import android.annotation.SuppressLint
import android.content.Context
import android.databinding.DataBindingUtil
import android.graphics.Color
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.BaseAdapter
import android.widget.Button
import jobtrends.jobsurvey.R
import jobtrends.jobsurvey.dagger.App
import jobtrends.jobsurvey.databinding.AdapterQuestionBinding
import jobtrends.jobsurvey.model.QuestionAnswerModel
import jobtrends.jobsurvey.model.ResultModel
import jobtrends.jobsurvey.model.UserAnswerModel
import jobtrends.jobsurvey.service.ServiceController
import javax.inject.Inject

class AnswerAdapter(
    private val list: List<QuestionAnswerModel?>?,
    private var userAnswerModel: UserAnswerModel
) : BaseAdapter() {

    @Inject
    lateinit var serviceController: ServiceController

    private var layoutInflater: LayoutInflater?
    private var button: Button?
    private val resultModel: ResultModel
    private val tag: String

    init {
        App.component.inject(this)
        resultModel = serviceController.getInstance()
        tag = "AnswerAdapter"
        layoutInflater = null
        button = null
    }

    @SuppressLint("ViewHolder")
    override fun getView(position: Int, convertView: View?, parent: ViewGroup?): View? {
        if (layoutInflater == null) {
            layoutInflater =
                    parent?.context?.getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater
        }
        val binding: AdapterQuestionBinding =
            DataBindingUtil.inflate(layoutInflater!!, R.layout.adapter_question, parent, false)
        binding.answerAdapter = this
        binding.questionAnswerModel = list?.get(position)
        return binding.root
    }

    override fun getItem(position: Int): Any? {
        return list?.get(position)
    }

    override fun getItemId(position: Int): Long {
        return position.toLong()
    }

    override fun getCount(): Int {
        return list?.size!!
    }

    fun onClick(answer: QuestionAnswerModel, view: View) {
        val button = view.findViewById<Button>(R.id.answer_button)
        userAnswerModel.answer?.set(answer.value?.get())
        if (this.button != null) {
            this.button?.setBackgroundResource(R.drawable.outline_layout)
            this.button?.setTextColor(Color.BLACK)
        }
        this.button = button
        this.button?.setBackgroundResource(R.drawable.rounded_layout)
        this.button?.setTextColor(Color.WHITE)
    }
}