package jobtrends.job_aymax

import android.annotation.SuppressLint
import android.databinding.DataBindingUtil
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.support.v4.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import jobtrends.job_aymax.databinding.SurveyContentViewBinding

class SurveyContentViewModel : Fragment()
{
	var Title : String? = null

	override fun onCreateView(inflater : LayoutInflater, container : ViewGroup?, savedInstanceState : Bundle?) : View?
	{
		val binding : SurveyContentViewBinding = DataBindingUtil.inflate(inflater, R.layout.survey_content_view, container, false)
		val view = binding.root
		binding.vm = this
		Title = arguments?.getString("title")
		return view
	}
}
