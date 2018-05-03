package jobtrends.jobsurvey.service

import android.preference.PreferenceManager
import android.util.Log
import com.google.firebase.iid.FirebaseInstanceId
import com.google.firebase.iid.FirebaseInstanceIdService

class MyFirebaseInstanceIDService : FirebaseInstanceIdService()
{
  private val _tag = "MyFirebaseIIDService"
  var jsonController = serviceController!!.getInstance<JsonController>()
  var apiController = serviceController!!.getInstance<APIController>()

  override fun onTokenRefresh()
  {
    val refreshedToken = FirebaseInstanceId.getInstance().token
    val msg = "Refreshed token: $refreshedToken"
    Log.d(_tag, msg)
    sendRegistrationToServer(refreshedToken!!)
  }

  private fun sendRegistrationToServer(token: String?)
  {
    // TODO: Implement this method to send token to your app server.
    val preferences = PreferenceManager.getDefaultSharedPreferences(this)
    val editor = preferences.edit()
    editor.putString("DeviceId", token)
    editor.apply()
    val msg = "Token: $token"
    Log.d(_tag, msg)
  }

  private fun firstResponse(response: String?)
  {
    Log.d(_tag, "-------------------------------------")
    Log.d(_tag, response)
    Log.d(_tag, "-------------------------------------")
  }
}