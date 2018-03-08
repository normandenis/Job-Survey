package jobtrends.jobaymax.service

import jobtrends.jobaymax.model.StartSurveyModel
import jobtrends.jobaymax.model.SurveyModel
import jobtrends.jobaymax.service.ServiceController.Companion.apiController
import jobtrends.jobaymax.service.ServiceController.Companion.jsonController

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