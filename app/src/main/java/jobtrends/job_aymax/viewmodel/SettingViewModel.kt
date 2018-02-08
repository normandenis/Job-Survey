package jobtrends.job_aymax.viewmodel

import android.content.Intent
import android.databinding.DataBindingUtil
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import jobtrends.job_aymax.R
import jobtrends.job_aymax.databinding.SettingViewBinding

class SettingViewModel : AppCompatActivity()
{
	override fun onCreate(savedInstanceState : Bundle?)
	{
		super.onCreate(savedInstanceState)
		val binding : SettingViewBinding = DataBindingUtil.setContentView(this, R.layout.setting_view)
		binding.vm = this
	}

	fun onClick(view : View)
	{
		val intent = Intent(this, ProfileViewModel::class.java)
		startActivity(intent)
	}
}
