package jobtrends.job_aymax

import android.content.Intent
import android.databinding.DataBindingUtil
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import jobtrends.job_aymax.databinding.SignInViewBinding

class SignInViewModel : AppCompatActivity()
{
	override fun onCreate(savedInstanceState : Bundle?)
	{
		super.onCreate(savedInstanceState)
//		setContentView(R.layout.sign_in_view)
		val binding : SignInViewBinding = DataBindingUtil.setContentView(this, R.layout.sign_in_view)
		binding.vm = this
	}

	fun onClick()
	{
		val intent = Intent(this, SignUpViewModel::class.java)
		startActivity(intent)
	}
}
