package jobtrends.jobsurvey.viewmodel

import android.app.NotificationChannel
import android.app.NotificationManager
import android.content.Intent
import android.databinding.DataBindingUtil
import android.databinding.ObservableField
import android.os.Build
import android.os.Build.VERSION
import android.os.Build.VERSION_CODES
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.util.Log
import jobtrends.jobsurvey.R
import jobtrends.jobsurvey.databinding.SignInViewBinding
import jobtrends.jobsurvey.service.APIController
import jobtrends.jobsurvey.service.JsonController
import jobtrends.jobsurvey.service.ServiceController
import jobtrends.jobsurvey.service.serviceController

class SignInViewModel : AppCompatActivity()
{
  var username = ObservableField<String>()
  var password = ObservableField<String>()
  private val TAG = "SignInViewModel"

  override fun onCreate(savedInstanceState: Bundle?)
  {
    super.onCreate(savedInstanceState)
    val binding: SignInViewBinding = DataBindingUtil.setContentView(this, R.layout.sign_in_view)
    binding.vm = this
    serviceController = ServiceController()
    serviceController!!.register(resources)

    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O)
    {
      // Create channel to show notifications.
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

    // Handle possible data accompanying notification message.
    // [START handle_data_extras]
    if (intent.extras != null)
    {
      for (key in intent.extras!!.keySet())
      {
        val value = intent.extras!!.get(key)
        Log.d(TAG, "Key: $key Value: $value")
      }
    }
    // [END handle_data_extras]

//    val subscribeButton = findViewById(R.id.subscribeButton)
//    subscribeButton.setOnClickListener(View.OnClickListener {
//      // [START subscribe_topics]
//      FirebaseMessaging.getInstance().subscribeToTopic("news")
//      // [END subscribe_topics]
//
//      // Log and toast
//      val msg = getString(R.string.msg_subscribed)
//      Log.d(TAG, msg)
//      Toast.makeText(this@SignInViewModel, msg, Toast.LENGTH_SHORT).show()
//    })
//
//    val logTokenButton = findViewById(R.id.logTokenButton)
//    logTokenButton.setOnClickListener(View.OnClickListener {
//      // Get token
//      val token = FirebaseInstanceId.getInstance().token
//
//      // Log and toast
//      val msg = getString(R.string.msg_token_fmt, token)
//      Log.d(TAG, msg)
//      Toast.makeText(this@SignInViewModel, msg, Toast.LENGTH_SHORT).show()
//    })
  }

  fun onClickSignIn()
  {
    val jsonController = serviceController!!.getInstance<JsonController>()
    val apiController = serviceController!!.getInstance<APIController>()
    val tmp = mutableMapOf<String, String?>()
    //		"benjamin@jobtrends.io"
    //		"totoToto"*
    tmp["username"] = username.get()
    tmp["password"] = password.get()
    val tmpSerialized = jsonController.serialize(tmp)

    apiController
      .post("auth/login", tmpSerialized, ::firstResponse, this)
  }

  private fun firstResponse(response: String)
  {
    Log.d(TAG, response)
    val intent = Intent(this, HomeViewModel::class.java)
    this.startActivity(intent)
  }

  fun onClickSignUp()
  {
    val intent = Intent(this, SignUpViewModel::class.java)
    this.startActivity(intent)
  }
}
