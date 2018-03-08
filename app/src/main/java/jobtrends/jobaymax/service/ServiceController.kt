package jobtrends.jobaymax.service

import android.content.res.Resources
import android.widget.Button
import jobtrends.jobaymax.model.EndSurvey
import jobtrends.jobaymax.model.StartSurveyModel
import jobtrends.jobaymax.model.User
import jobtrends.jobaymax.model.UserModel

class ServiceController
{
  constructor(rsrc : Resources)
  {
    resources = rsrc
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