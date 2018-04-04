package jobtrends.jobsurvey.service

import android.util.Log
import com.google.firebase.iid.FirebaseInstanceId
import com.google.firebase.iid.FirebaseInstanceIdService

class MyFirebaseInstanceIDService : FirebaseInstanceIdService()
{
  override fun onTokenRefresh()
  {
    val refreshedToken = FirebaseInstanceId.getInstance().token
    Log.d(TAG, "Refreshed token: $refreshedToken")

    sendRegistrationToServer(refreshedToken !!)
  }

  private fun sendRegistrationToServer(token : String)
  {
    // TODO: Implement this method to send token to your app server.

    Log.e(TAG, token)
  }

  companion object
  {
    private const val TAG = "MyFirebaseIIDService"
  }
}