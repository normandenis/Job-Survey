package jobtrends.jobsurvey.service

import android.os.Debug
import android.util.Log
import jobtrends.jobsurvey.model.EndSurvey
import jobtrends.jobsurvey.model.ErrorModel
import jobtrends.jobsurvey.model.User
import jobtrends.jobsurvey.model.UserModel

var serviceController: ServiceController? = null

class ServiceController
{
  var instances: MutableMap<Any?, Any?> = mutableMapOf()

  constructor()
  {
    register<JsonController>()
    register<UserModel>()
    register<User>()
    register<EndSurvey>()
    register<ErrorModel>()
    register<APIController>()
    register<SurveyController>()
  }

  inline fun <reified T> register(obj: T? = null, new: Boolean? = false)
  {
    if (!instances.containsKey(T::class) || new == true)
    {
      instances[T::class] = (obj ?: T::class.java.newInstance()) as Any?
    }
  }

  inline fun <reified T> getInstance(): T
  {
    return instances[T::class] as T
  }
}