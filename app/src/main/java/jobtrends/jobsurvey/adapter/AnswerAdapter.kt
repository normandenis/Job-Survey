package jobtrends.jobsurvey.adapter

import android.annotation.SuppressLint
import android.content.Context
import android.databinding.DataBindingUtil
import android.graphics.Color
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.BaseAdapter
import android.widget.Button
import jobtrends.jobsurvey.R
import jobtrends.jobsurvey.databinding.QuestionAdapterViewBinding
import jobtrends.jobsurvey.model.QuestionAnswerModel
import jobtrends.jobsurvey.model.ResultModel
import jobtrends.jobsurvey.model.UserAnswerModel
import jobtrends.jobsurvey.service.serviceController

class AnswerAdapter : BaseAdapter
{
    private var _inflater: LayoutInflater?
    private var _userAnswerModel: UserAnswerModel?
    private var _button: Button?
    private val _resultModel: ResultModel?
    private val _list: List<QuestionAnswerModel?>?
    private val _tag: String?


    constructor(list: List<QuestionAnswerModel?>?, reply: UserAnswerModel?) : super()
    {
        _list = list
        _userAnswerModel = reply

        _tag = "AnswerAdapter"
        _resultModel = serviceController?.getInstance()

        _inflater = null
        _button = null
    }

    @SuppressLint("ViewHolder")
    override fun getView(position: Int, convertView: View?, parent: ViewGroup?): View?
    {
        if (_inflater == null)
        {
            _inflater = parent?.context?.getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater
        }
        val binding: QuestionAdapterViewBinding? =
                DataBindingUtil.inflate(_inflater!!, R.layout.question_adapter_view, parent, false)
        binding?.vm = this
        binding?.m = _list?.get(position)

        return binding?.root
    }

    override fun getItem(position: Int): Any?
    {
        return _list?.get(position)
    }

    override fun getItemId(position: Int): Long
    {
        return position.toLong()
    }

    override fun getCount(): Int
    {
        return _list?.size!!
    }

    fun onClick(answer: QuestionAnswerModel?, view: View?)
    {
        val msg = "Answers size: ${_resultModel?.answers?.size}"
        Log.d(_tag, msg)
        val button = view?.findViewById<Button>(R.id.answer_button)
        _userAnswerModel?.answer?.set(answer?.value?.get())
        if (_button != null)
        {
            _button?.setBackgroundResource(R.drawable.outline_layout)
            _button?.setTextColor(Color.BLACK)
        }
        _button = button
        _button?.setBackgroundResource(R.drawable.rounded_layout)
        _button?.setTextColor(Color.WHITE)
    }
}