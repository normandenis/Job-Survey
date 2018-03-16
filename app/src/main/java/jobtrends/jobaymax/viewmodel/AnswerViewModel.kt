package jobtrends.jobaymax.viewmodel

import android.annotation.SuppressLint
import android.content.Context
import android.databinding.DataBindingUtil
import android.graphics.Color
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.BaseAdapter
import android.widget.Button
import jobtrends.jobaymax.R
import jobtrends.jobaymax.databinding.ListviewQuestionViewBinding
import jobtrends.jobaymax.model.Answer
import jobtrends.jobaymax.model.Reply
import jobtrends.jobaymax.service.ServiceController.Companion.endSurvey

class AnswerViewModel(var list : List<Answer>) : BaseAdapter()
{
  var inflater : LayoutInflater? = null
  var reply : Reply? = null
  var myView : View? = null
  var lastButton : Button? = null
  @SuppressLint("ViewHolder")
  override fun getView(position : Int, convertView : View?, parent : ViewGroup?) : View
  {
    if (inflater == null)
    {
      inflater = parent?.context?.getSystemService(
          Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater
    }
    val binding : ListviewQuestionViewBinding = DataBindingUtil
        .inflate(inflater, R.layout.listview_question_view, parent, false)
    binding.vm = this
    binding.m = list[position]

    myView = binding.root

    return myView !!
  }

  override fun getItem(position : Int) : Any
  {
    return list[position]
  }

  override fun getItemId(position : Int) : Long
  {
    return position.toLong()
  }

  override fun getCount() : Int
  {
    return list.size
  }

  fun onClick(answer : Answer, view : View)
  {
    println("---------------------------------------------------------------------------------")
    println(endSurvey.answers?.size)
    val button = view.findViewById<Button>(R.id.answer_button)
    reply?.value = answer.value
    lastButton?.setBackgroundResource(R.drawable.outline_layout)
    lastButton?.setTextColor(Color.BLACK)
    lastButton = button
    lastButton?.setBackgroundResource(R.drawable.rounded_layout)
    lastButton?.setTextColor(Color.WHITE)
  }
}