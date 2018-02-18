package jobtrends.job_aymax.viewmodel

import android.annotation.SuppressLint
import android.content.Context
import android.databinding.DataBindingUtil
import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v4.app.FragmentManager
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.BaseAdapter
import jobtrends.job_aymax.R
import jobtrends.job_aymax.databinding.ListviewStartSurveyViewBinding
import jobtrends.job_aymax.model.Survey
import jobtrends.job_aymax.model.Theme
import jobtrends.job_aymax.service.ServiceController.Companion.apiController
import jobtrends.job_aymax.service.ServiceController.Companion.jsonController

class ThemeViewModel(var list : List<Theme>) : BaseAdapter()
{
	var inflater : LayoutInflater? = null
	var myView : View? = null
	var fragmentManager : FragmentManager? = null


	@SuppressLint("ViewHolder")
	override fun getView(position : Int, convertView : View?, parent : ViewGroup?) : View
	{
		if (inflater == null)
		{
			inflater = parent?.context?.getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater
		}

		val binding : ListviewStartSurveyViewBinding = DataBindingUtil.inflate(inflater, R.layout.listview_start_survey_view, parent, false)
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

	fun onClickTheme(theme : Theme)
	{
		val id = theme.survey_id
		apiController.get("https://api.dev.jobtrends.io/jobaymax/survey/$id", ::firstResponse, myView?.context !!)
	}

	fun firstResponse(response : String)
	{
		val survey = jsonController.deserialize<Survey>(response)
		val fragment = SurveyViewModel()
		fragment.survey = survey
		val transaction = fragmentManager?.beginTransaction()
		transaction?.replace(R.id.fragment_app_bar_nav_drawer_0, fragment)
		transaction?.addToBackStack(null)
		transaction?.commit()
	}
}