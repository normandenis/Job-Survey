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
  private val _jsonController: JsonController?
  private val _apiController: APIController?
  private val _delayMillis: Long?
  private val _tag = "SplashViewModel"

  constructor() : super()
  {
    _delayMillis = 1000
    serviceController = ServiceController()
    _jsonController = serviceController!!.getInstance()
    _apiController = serviceController!!.getInstance()
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
      } else
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
        Log.d(_tag, "Key: $key Value: $value")
      }
    }
  }

  override fun onCreate(savedInstanceState: Bundle?)
  {
    super.onCreate(savedInstanceState)
    setContentView(R.layout.splash_view)
    serviceController!!.register(resources)

    Handler().postDelayed({
                            initNotification()
                            val preferences = PreferenceManager.getDefaultSharedPreferences(this)
                            val json = preferences.getString(User::class.java.simpleName, null)
                            Log.d(_tag, json ?: "")
                            if (json != null && json != "")
                            {
                              val user = _jsonController!!.deserialize<User>(json)
                              serviceController!!.register(user, true)
                              val tmp = mutableMapOf<String?, String?>()
                              tmp["username"] = user.email
                              tmp["password"] = user.password
                              val tmpSerialized = _jsonController.serialize(tmp)
                              _apiController!!.post("auth/login", tmpSerialized, ::authLoginReply, this)
                            } else
                            {
                              val intent = Intent(this, SignInViewModel::class.java)
                              startActivity(intent)
                            }
                          }, _delayMillis!!)
  }

  private fun authLoginReply(code: Int?, body: String?)
  {
    val msg = "$code: $body"
    Log.d(_tag, msg)
    val intent = Intent(this, HomeViewModel::class.java)
    startActivity(intent)
  }


}
