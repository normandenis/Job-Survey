package jobtrends.job_aymax.service

import android.annotation.SuppressLint
import android.content.Context
import android.view.View
import android.view.ViewGroup
import android.widget.BaseAdapter
import jobtrends.job_aymax.model.AnswerSurveyModel
import android.databinding.DataBindingUtil
import android.content.Context.LAYOUT_INFLATER_SERVICE
import android.view.LayoutInflater
import jobtrends.job_aymax.R
import jobtrends.job_aymax.databinding.ListviewSurveyContentViewBinding


class ListAdapter(list : List<AnswerSurveyModel>) : BaseAdapter()
{
	var list : List<AnswerSurveyModel> = list
	var inflater : LayoutInflater? = null

	@SuppressLint("ViewHolder") override fun getView(position : Int, convertView : View?,
	                                                 parent : ViewGroup?) : View
	{
		if (inflater == null)
		{
			inflater = parent?.context?.getSystemService(
					Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater
		}

		val binding : ListviewSurveyContentViewBinding = DataBindingUtil
				.inflate(inflater, R.layout.listview_survey_content_view, parent, false)
		binding.vm = list[position]

		return binding.root
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

}