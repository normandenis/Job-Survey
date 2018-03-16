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
import jobtrends.jobsurvey.service.ServiceController
import jobtrends.jobsurvey.service.ServiceController.Companion.apiController
import jobtrends.jobsurvey.service.ServiceController.Companion.jsonController
import jobtrends.jobsurvey.service.serviceController


class Toto
{
  var name : String? = null
}

class SignInViewModel : AppCompatActivity()
{
  var username = ObservableField<String>()
  var password = ObservableField<String>()
  private val TAG = "MainActivity"

  override fun onCreate(savedInstanceState : Bundle?)
  {
    super.onCreate(savedInstanceState)
    val binding : SignInViewBinding = DataBindingUtil.setContentView(this, R.layout.sign_in_view)
    binding.vm = this
    ServiceController(this.resources)
    val toto = Toto()
    toto.name = "Toto"
    serviceController.register(toto)
    val totobis = serviceController.getInstance<Toto>()
    println(totobis.name)

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
      notificationManager !!.createNotificationChannel(
        NotificationChannel(channelId, channelName, NotificationManager.IMPORTANCE_LOW))
    }

    // If a notification message is tapped, any data accompanying the notification
    // message is available in the intent extras. In this sample the launcher
    // intent is fired when the notification is tapped, so any accompanying data would
    // be handled here. If you want a different intent fired, set the click_action
    // field of the notification message to the desired intent. The launcher intent
    // is used when no click_action is specified.
    //
    // Handle possible data accompanying notification message.
    // [START handle_data_extras]
    if (intent.extras != null)
    {
      for (key in intent.extras !!.keySet())
      {
        val value = intent.extras !!.get(key)
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
    val tmp = mutableMapOf<String, String?>()
    //		"benjamin@jobtrends.io"
    //		"totoToto"
    tmp["username"] = this.username.get()
    tmp["password"] = this.password.get()
    val tmpSerialized = jsonController.serialize(tmp)

    apiController
      .post("https://api.dev.jobtrends.io/auth/login", tmpSerialized, this::firstResponse, this)
  }

  private fun firstResponse(response : String)
  {
    val intent = Intent(this, HomeViewModel::class.java)
    this.startActivity(intent)
  }

  fun onClickSignUp()
  {
    val intent = Intent(this, SignUpViewModel::class.java)
    this.startActivity(intent)
  }
}
