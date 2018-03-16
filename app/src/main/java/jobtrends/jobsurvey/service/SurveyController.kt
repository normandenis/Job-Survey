package jobtrends.jobsurvey.service

import jobtrends.jobsurvey.model.StartSurveyModel
import jobtrends.jobsurvey.model.SurveyModel
import jobtrends.jobsurvey.service.ServiceController.Companion.apiController
import jobtrends.jobsurvey.service.ServiceController.Companion.jsonController

class SurveyController
{
  fun getSurvey() : SurveyModel
  {
    val strSurvey = apiController.getSurvey()
    return jsonController.deserialize(strSurvey)
  }

  fun getCategory() : StartSurveyModel
  {
    val strCategory = apiController.getCategory()
    return jsonController.deserialize(strCategory)
  }
}