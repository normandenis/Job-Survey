package jobtrends.job_aymax.viewmodel

import android.content.Intent
import android.databinding.DataBindingUtil
import android.os.Bundle
import android.provider.Contacts.Intents.UI
import android.support.v7.app.AppCompatActivity
import jobtrends.job_aymax.R
import jobtrends.job_aymax.databinding.SignUpViewBinding
import jobtrends.job_aymax.service.ServiceController.Companion.apiController
import jobtrends.job_aymax.service.ServiceController.Companion.jsonController
import jobtrends.job_aymax.service.ServiceController.Companion.user
import jobtrends.job_aymax.service.ServiceController.Companion.userModel
import kotlinx.coroutines.experimental.async
import java.net.CacheResponse

class SignUpViewModel : AppCompatActivity()
{
	var m_userModel = userModel
	var m_user = user

	override fun onCreate(savedInstanceState : Bundle?)
	{
		super.onCreate(savedInstanceState)
		val binding : SignUpViewBinding = DataBindingUtil.setContentView(this, R.layout.sign_up_view)
		binding.vm = this
	}

	fun onClick()
	{
		m_user.email = m_userModel.email.get()
		m_user.firstName = m_userModel.firstName.get()
		m_user.lastName = m_userModel.lastName.get()
		m_user.password = m_userModel.password.get()
		m_user.metier = m_userModel.metier.get()
		m_user.birthday = m_userModel.birthday.get().toInt()

		val json = jsonController.serialize(m_user)

		apiController.post("https://api.dev.jobtrends.io/auth/signup", json, ::firstResponse, this)
	}

	fun firstResponse(response : String)
	{
		m_user = jsonController.deserialize(response)

		val tmp = mutableMapOf<String, String?>()

		tmp["username"] = m_user.email
		tmp["password"] = m_user.password

		val tmpSerialized = jsonController.serialize(tmp)

		apiController.post("https://api.dev.jobtrends.io/auth/login", tmpSerialized, ::secondResponse, this)
	}

	fun secondResponse(response : String)
	{
		val intent = Intent(this, HomeViewModel::class.java)
		startActivity(intent)
	}
}
