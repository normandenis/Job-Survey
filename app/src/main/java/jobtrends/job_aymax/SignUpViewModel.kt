package jobtrends.job_aymax

import android.databinding.DataBindingUtil
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import jobtrends.job_aymax.databinding.SignUpViewBinding

class SignUpViewModel : AppCompatActivity()
{

	override fun onCreate(savedInstanceState : Bundle?)
	{
		super.onCreate(savedInstanceState)
		setContentView(R.layout.sign_up_view)
		val binding : SignUpViewBinding = DataBindingUtil.setContentView(this, R.layout.sign_up_view)
		binding.vm = this
	}

	fun onClick()
	{
	}
}
