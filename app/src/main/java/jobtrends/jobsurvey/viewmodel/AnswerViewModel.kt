package jobtrends.jobsurvey.viewmodel

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
import jobtrends.jobsurvey.databinding.ListviewQuestionViewBinding
import jobtrends.jobsurvey.model.Answer
import jobtrends.jobsurvey.model.EndSurvey
import jobtrends.jobsurvey.model.Reply
import jobtrends.jobsurvey.service.serviceController

class AnswerViewModel : BaseAdapter
{
  private var _inflater: LayoutInflater?
  private var _reply: Reply?
  private var _view: View?
  private var _button: Button?
  private val _endSurvey: EndSurvey?
  private val _list: List<Answer>?
  private val _tag: String?


  constructor(list: List<Answer>?, reply: Reply?) : super()
  {
    _list = list
    _reply = reply

    _tag = "AnswerViewModel"
    _endSurvey = serviceController!!.getInstance()

    _inflater = null
    _button = null
    _view = null
  }

  @SuppressLint("ViewHolder")
  override fun getView(position: Int, convertView: View?, parent: ViewGroup?): View?
  {
    if (_inflater == null)
    {
      _inflater = parent!!.context!!.getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater
    }
    val binding: ListviewQuestionViewBinding = DataBindingUtil
      .inflate(_inflater!!, R.layout.listview_question_view, parent, false)
    binding.vm = this
    binding.m = _list!![position]

    _view = binding.root

    return _view!!
  }

  override fun getItem(position: Int): Any?
  {
    return _list!![position]
  }

  override fun getItemId(position: Int): Long
  {
    return position.toLong()
  }

  override fun getCount(): Int
  {
    return _list!!.size
  }

  fun onClick(answer: Answer, view: View)
  {
    val msg = "Answers size: ${_endSurvey!!.answers!!.size}"
    Log.d(_tag, msg)
    val button = view.findViewById<Button>(R.id.answer_button)
    _reply!!.value = answer.value
    if (_button != null)
    {
      _button!!.setBackgroundResource(R.drawable.outline_layout)
      _button!!.setTextColor(Color.BLACK)
    }
    _button = button
    _button!!.setBackgroundResource(R.drawable.rounded_layout)
    _button!!.setTextColor(Color.WHITE)
  }
}