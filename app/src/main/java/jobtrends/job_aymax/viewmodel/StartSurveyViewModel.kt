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
import jobtrends.job_aymax.service.ServiceController
import jobtrends.job_aymax.service.ServiceController.Companion.apiController


class StartSurveyViewModel : Fragment()
{
	var themeViewModel : ThemeViewModel? = null
	var startSurveyModel : StartSurveyModel? = null
	var myview : View? = null

	override fun onCreateView(inflater : LayoutInflater, container : ViewGroup?, savedInstanceState : Bundle?) : View?
	{
		val binding : StartSurveyViewBinding = DataBindingUtil.inflate(inflater, R.layout.start_survey_view, container, false)
		myview = binding.root
		binding.vm = this

		val strThemes = arguments?.get("themes")
		startSurveyModel = ServiceController.jsonController.deserialize<StartSurveyModel>(strThemes.toString())
		themeViewModel = ThemeViewModel(startSurveyModel?.themes !!)

		return myview
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