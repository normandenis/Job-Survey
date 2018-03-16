package jobtrends.jobsurvey.viewmodel

import android.content.Intent
import android.databinding.DataBindingUtil
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import jobtrends.jobsurvey.R
import jobtrends.jobsurvey.databinding.SignUpViewBinding
import jobtrends.jobsurvey.service.ServiceController.Companion.apiController
import jobtrends.jobsurvey.service.ServiceController.Companion.jsonController
import jobtrends.jobsurvey.service.ServiceController.Companion.user
import jobtrends.jobsurvey.service.ServiceController.Companion.userModel

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
    m_user.birthday = 0
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

    apiController
        .post("https://api.dev.jobtrends.io/auth/login", tmpSerialized, ::secondResponse, this)
  }

  fun secondResponse(response : String)
  {
    val intent = Intent(this, HomeViewModel::class.java)
    startActivity(intent)
  }
}
