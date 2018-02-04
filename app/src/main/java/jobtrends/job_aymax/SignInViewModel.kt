package jobtrends.job_aymax

import android.content.Intent
import android.databinding.DataBindingUtil
import android.os.Bundle
import android.os.Debug
import android.support.v7.app.AppCompatActivity
import android.view.View
import jobtrends.job_aymax.databinding.SignInViewBinding
import java.io.Console

class SignInViewModel : AppCompatActivity()
{
	override fun onCreate(savedInstanceState : Bundle?)
	{
		super.onCreate(savedInstanceState)
		val binding: SignInViewBinding = DataBindingUtil.setContentView(this, R.layout.sign_in_view)
		binding.vm = this
	}

	fun onClick()
	{
		val intent = Intent(this, SignUpViewModel::class.java)
		startActivity(intent)
	}
}
