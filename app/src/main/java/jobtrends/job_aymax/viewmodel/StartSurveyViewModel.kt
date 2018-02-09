package jobtrends.job_aymax.viewmodel

import android.databinding.DataBindingUtil
import android.os.Bundle
import android.support.v4.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import jobtrends.job_aymax.databinding.StartSurveyViewBinding
import jobtrends.job_aymax.R
import jobtrends.job_aymax.model.StartSurveyModel
import jobtrends.job_aymax.model.SurveyCategoryModel
import jobtrends.job_aymax.service.ServiceController


class StartSurveyViewModel : Fragment()
{
	var surveyCategoryViewModel : SurveyCategoryViewModel? = null
	var startSurveyModel : StartSurveyModel? = null

	override fun onCreateView(inflater : LayoutInflater, container : ViewGroup?, savedInstanceState : Bundle?) : View?
	{
		val binding : StartSurveyViewBinding = DataBindingUtil.inflate(inflater, R.layout.start_survey_view, container, false)
		val view = binding.root
		binding.vm = this

		val strCategories = arguments?.getString("categories")
		startSurveyModel = ServiceController.jsonController.deserialize<StartSurveyModel>(strCategories.toString())
		surveyCategoryViewModel = SurveyCategoryViewModel(startSurveyModel?.categories !!)

		return view
	}

//	fun onClick()
//	{
//		val fragment = SurveyViewModel()
//		val transaction = fragmentManager?.beginTransaction()
//		transaction?.replace(R.id.fragment_app_bar_nav_drawer_0, fragment)
//		transaction?.addToBackStack(null)
//		transaction?.commit()
//	}
}
