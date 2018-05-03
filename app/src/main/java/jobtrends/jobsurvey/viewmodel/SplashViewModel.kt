package jobtrends.jobsurvey.viewmodel

import android.app.NotificationChannel
import android.app.NotificationManager
import android.content.Intent
import android.os.Build
import android.os.Build.VERSION
import android.os.Build.VERSION_CODES
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

  private fun initNotification()
  {
    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O)
    {
      val channelId = getString(R.string.default_notification_channel_id)
      val channelName = getString(R.string.default_notification_channel_name)
      val notificationManager = if (VERSION.SDK_INT >= VERSION_CODES.M)
      {
        getSystemService(NotificationManager::class.java)
      }
      else
      {
        TODO("VERSION.SDK_INT < M")
      }
      notificationManager!!.createNotificationChannel(
        NotificationChannel(channelId, channelName, NotificationManager.IMPORTANCE_HIGH))
    }
    if (intent.extras != null)
    {
      for (key in intent.extras!!.keySet())
      {
        val value = intent.extras!!.get(key)
        Log.d(TAG, "Key: $key Value: $value")
      }
    }
  }

  override fun onCreate(savedInstanceState: Bundle?)
  {
    super.onCreate(savedInstanceState)
    setContentView(R.layout.splash_view)

    Handler().postDelayed({
                            initNotification()
                            val preferences = PreferenceManager.getDefaultSharedPreferences(this)
                            val json = preferences.getString(User::class.java.simpleName, null)
                            Log.d(TAG, json ?: "")
                            if (json != null && json != "")
                            {
                              val user = jsonController!!.deserialize<User>(json)
                              serviceController!!.register(user, true)
                              val tmp = mutableMapOf<String?, String?>()
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

  private fun firstResponse(code: Int?, response: String?)
  {
    val intent = Intent(this, HomeViewModel::class.java)
    startActivity(intent)
  }


}
