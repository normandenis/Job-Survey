package jobtrends.job_aymax.service

import jobtrends.job_aymax.model.SurveyModel
import jobtrends.job_aymax.service.ServiceController.Companion.apiController
import jobtrends.job_aymax.service.ServiceController.Companion.jsonController

class SurveyController
{
	fun getSurvey() : SurveyModel
	{
		val strSurvey = apiController.getSurvey()
		return jsonController.deserialize(strSurvey)
	}
}