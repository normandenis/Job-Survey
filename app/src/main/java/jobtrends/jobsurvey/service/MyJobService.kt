package jobtrends.jobsurvey.service

import android.util.Log
import com.firebase.jobdispatcher.JobParameters
import com.firebase.jobdispatcher.JobService

class MyJobService : JobService()
{
  private val _tag = "MyJobService"

  override fun onStartJob(jobParameters: JobParameters): Boolean
  {
    Log.d(_tag, "Performing long running task in scheduled job")
    // TODO(developer): add long running task here.
    return false
  }

  override fun onStopJob(jobParameters: JobParameters): Boolean
  {
    return false
  }
}