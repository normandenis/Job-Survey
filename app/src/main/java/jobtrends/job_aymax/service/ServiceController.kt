package jobtrends.job_aymax.service

import android.content.res.Resources
import jobtrends.job_aymax.model.EndSurvey
import jobtrends.job_aymax.model.User
import jobtrends.job_aymax.model.UserModel

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

		var apiController = APIController()

		var surveyController = SurveyController()
	}
}