package jobtrends.jobsurvey.service

import android.annotation.SuppressLint
import android.content.res.Resources
import android.widget.Button
import jobtrends.jobsurvey.model.EndSurvey
import jobtrends.jobsurvey.model.StartSurveyModel
import jobtrends.jobsurvey.model.User
import jobtrends.jobsurvey.model.UserModel

var serviceController = ServiceController()

class ServiceController
{
  var instances : MutableMap<Any, Any> = mutableMapOf()

  constructor(rsrc : Resources)
  {
    resources = rsrc
  }

  constructor()

  inline fun <reified T> register(obj : T)
  {
    instances[T::class] = obj as Any
  }

  inline fun <reified T> getInstance() : T
  {
    return instances[T::class] as T
  }

  companion object
  {
    var resources : Resources? = null
    var jsonController = JsonController()
    var userModel = UserModel()
    var user = User()
    var endSurvey = EndSurvey()
    var startSurveyModel : StartSurveyModel? = null
    var apiController = APIController()
    var surveyController = SurveyController()
    var appBarBtn : Button? = null
  }
}