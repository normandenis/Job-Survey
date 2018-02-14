package jobtrends.job_aymax.viewmodel

import android.annotation.SuppressLint
import android.content.Context
import android.databinding.DataBindingUtil
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.BaseAdapter
import jobtrends.job_aymax.R
import jobtrends.job_aymax.databinding.ListviewFaqViewBinding
import jobtrends.job_aymax.model.FAQModel

class ListViewFAQViewModel(var list : List<FAQModel>) : BaseAdapter()
{
	var inflater : LayoutInflater? = null

	@SuppressLint("ViewHolder")
	override fun getView(position : Int, convertView : View?, parent : ViewGroup?) : View
	{
		if (inflater == null)
		{
			inflater = parent?.context?.getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater
		}

		val binding : ListviewFaqViewBinding = DataBindingUtil.inflate(inflater, R.layout.listview_faq_view, parent, false)
		binding.m = list[position]

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
