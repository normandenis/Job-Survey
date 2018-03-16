package jobtrends.jobsurvey.service

import android.content.res.Resources
import android.widget.Button
import jobtrends.jobsurvey.model.EndSurvey
import jobtrends.jobsurvey.model.StartSurveyModel
import jobtrends.jobsurvey.model.User
import jobtrends.jobsurvey.model.UserModel

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