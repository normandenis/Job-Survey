package jobtrends.job_aymax

import android.content.Intent
import android.databinding.DataBindingUtil
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import jobtrends.job_aymax.databinding.StartSurveyViewBinding

class StartSurveyViewModel : AppCompatActivity()
{
	override fun onCreate(savedInstanceState : Bundle?)
	{
		super.onCreate(savedInstanceState)
		val binding : StartSurveyViewBinding = DataBindingUtil.setContentView(this, R.layout.start_survey_view)
		binding.vm = this
	}

	fun onClick()
	{
		val intent = Intent(this, SurveyViewModel::class.java)
		startActivity(intent)
	}
}
