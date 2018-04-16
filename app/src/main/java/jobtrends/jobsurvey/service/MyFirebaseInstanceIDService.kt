package jobtrends.jobsurvey.service

import android.preference.PreferenceManager
import android.util.Log
import com.google.firebase.iid.FirebaseInstanceId
import com.google.firebase.iid.FirebaseInstanceIdService
import jobtrends.jobsurvey.model.User

class MyFirebaseInstanceIDService : FirebaseInstanceIdService()
{
  var jsonController = serviceController!!.getInstance<JsonController>()
  var apiController = serviceController!!.getInstance<APIController>()

  override fun onTokenRefresh()
  {
    val refreshedToken = FirebaseInstanceId.getInstance().token
    Log.d(TAG, "Refreshed token: $refreshedToken")

    sendRegistrationToServer(refreshedToken !!)
  }

  private fun sendRegistrationToServer(token : String)
  {
    // TODO: Implement this method to send token to your app server.
    val preferences = PreferenceManager.getDefaultSharedPreferences(this)
    val editor = preferences.edit()
    editor.putString("DeviceId", token)
    editor.apply()
    Log.e(TAG, token)
  }

  private fun firstResponse(response: String)
  {
    Log.e(TAG, "-------------------------------------")
    Log.e(TAG, response)
    Log.e(TAG, "-------------------------------------")
  }

  companion object
  {
    private const val TAG = "MyFirebaseIIDService"
  }
}