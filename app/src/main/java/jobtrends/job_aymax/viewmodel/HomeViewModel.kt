package jobtrends.job_aymax.viewmodel

import android.app.Fragment
import android.databinding.DataBindingUtil
import android.os.Bundle
import android.support.design.widget.NavigationView
import android.support.v4.view.GravityCompat
import android.support.v4.widget.DrawerLayout
import android.support.v7.app.ActionBarDrawerToggle
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.Toolbar
import android.view.MenuItem
import android.view.View
import android.view.ViewGroup
import android.widget.FrameLayout
import jobtrends.job_aymax.R
import jobtrends.job_aymax.databinding.HomeViewBinding
import jobtrends.job_aymax.model.StartSurveyModel
import jobtrends.job_aymax.service.ServiceController
import jobtrends.job_aymax.service.ServiceController.Companion.apiController
import jobtrends.job_aymax.service.ServiceController.Companion.jsonController
import jobtrends.job_aymax.service.ServiceController.Companion.surveyController

class HomeViewModel : AppCompatActivity()
{

	override fun onCreate(savedInstanceState : Bundle?)
	{
		super.onCreate(savedInstanceState)
		val binding : HomeViewBinding = DataBindingUtil.setContentView(this, R.layout.home_view)
		binding.vm = this

		val toolbar = findViewById<View>(R.id.toolbar) as Toolbar
		setSupportActionBar(toolbar)

		if (findViewById<FrameLayout>(R.id.fragment_app_bar_nav_drawer_0) != null)
		{
			if (savedInstanceState != null)
			{
				return
			}
			apiController.get("https://api.dev.jobtrends.io/jobaymax/me", ::firstResponse, this)
		}
	}

	fun firstResponse(response : String)
	{
		val bundle = Bundle()
		bundle.putString("themes", response)
		val fragment = StartSurveyViewModel()
		fragment.arguments = bundle
		val transaction = supportFragmentManager.beginTransaction()
		transaction.add(R.id.fragment_app_bar_nav_drawer_0, fragment)
		transaction.addToBackStack(null)
		transaction.commit()
	}



	fun onClick()
	{
		val fragment = SettingViewModel()
		val transaction = supportFragmentManager.beginTransaction()
		transaction?.replace(R.id.fragment_app_bar_nav_drawer_0, fragment)
		transaction?.addToBackStack(null)
		transaction?.commit()
	}
}
