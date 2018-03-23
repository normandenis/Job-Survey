package jobtrends.jobsurvey.viewmodel

import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.preference.PreferenceManager
import android.support.v7.app.AppCompatActivity
import android.util.Log
import jobtrends.jobsurvey.R
import jobtrends.jobsurvey.model.User
import jobtrends.jobsurvey.service.APIController
import jobtrends.jobsurvey.service.JsonController
import jobtrends.jobsurvey.service.ServiceController
import jobtrends.jobsurvey.service.serviceController

class SplashViewModel : AppCompatActivity
{
  var jsonController: JsonController? = null
  var apiController: APIController? = null
  private val delayMillis: Long = 1000
  private val TAG = "SplashViewModel"

  constructor() : super()
  {
    serviceController = ServiceController()
    jsonController = serviceController!!.getInstance<JsonController>()
    apiController = serviceController!!.getInstance<APIController>()
  }

  override fun onCreate(savedInstanceState: Bundle?)
  {
    super.onCreate(savedInstanceState)
    setContentView(R.layout.splash_view)

    Handler().postDelayed({
                            val preferences = PreferenceManager.getDefaultSharedPreferences(this)
                            val json = preferences.getString(User::class.java.simpleName, null)
                            Log.d(TAG, json ?: "")
                            if (json != null && json != "")
                            {
                              val user = jsonController!!.deserialize<User>(json)
                              serviceController!!.register(user, true)
                              val tmp = mutableMapOf<String, String?>()
                              tmp["username"] = user.email
                              tmp["password"] = user.password
                              val tmpSerialized = jsonController!!.serialize(tmp)
                              apiController!!.post("auth/login", tmpSerialized, ::firstResponse, this)
                            }
                            else
                            {
                              val intent = Intent(this, SignInViewModel::class.java)
                              startActivity(intent)
                            }
                          }, delayMillis)
  }

  private fun firstResponse(response: String)
  {
    val intent = Intent(this, HomeViewModel::class.java)
    startActivity(intent)
  }


}
