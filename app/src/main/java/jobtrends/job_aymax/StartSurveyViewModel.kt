package jobtrends.job_aymax

import android.databinding.DataBindingUtil
import android.os.Bundle
import android.support.v4.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import jobtrends.job_aymax.databinding.StartSurveyViewBinding
import android.support.v4.app.FragmentManager
import android.support.v4.app.FragmentTransaction


class StartSurveyViewModel : Fragment()
{
	override fun onCreateView(inflater : LayoutInflater, container : ViewGroup?, savedInstanceState : Bundle?) : View?
	{
		val binding : StartSurveyViewBinding = DataBindingUtil.inflate(inflater, R.layout.start_survey_view, container, false)
		val view = binding.root
		binding.vm = this
		return view
	}

	fun onClick()
	{
		val fragment = SurveyViewModel()
		val transaction = fragmentManager?.beginTransaction()
		transaction?.replace(R.id.fragment_app_bar_nav_drawer_0, fragment)
		transaction?.addToBackStack(null)
		transaction?.commit()
	}
}
