package jobtrends.job_aymax.viewmodel

import android.content.Intent
import android.databinding.DataBindingUtil
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import jobtrends.job_aymax.R
import jobtrends.job_aymax.databinding.SignInViewBinding
import jobtrends.job_aymax.service.ServiceController
import jobtrends.job_aymax.service.ServiceController.Companion.apiController
import jobtrends.job_aymax.service.ServiceController.Companion.jsonController
import jobtrends.job_aymax.service.SurveyController

class SignInViewModel : AppCompatActivity()
{
//	val serviceController = ServiceController(resources)

	override fun onCreate(savedInstanceState : Bundle?)
	{
		super.onCreate(savedInstanceState)
		val binding: SignInViewBinding = DataBindingUtil.setContentView(this, R.layout.sign_in_view)
		binding.vm = this
		ServiceController(resources)
	}

	fun onClickSignIn()
	{
		val tmp = mutableMapOf<String, String?>()

		tmp["username"] = "benjamin@jobtrends.io"
		tmp["password"] = "totoToto"

		val tmpSerialized = jsonController.serialize(tmp)

		apiController.post("https://api.dev.jobtrends.io/auth/login", tmpSerialized, ::firstResponse, this)
	}

	fun firstResponse(response : String)
	{
		val intent = Intent(this, HomeViewModel::class.java)
		startActivity(intent)
	}

	fun onClickSignUp()
	{
		val intent = Intent(this, SignUpViewModel::class.java)
		startActivity(intent)
	}
}
