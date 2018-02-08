package jobtrends.job_aymax.service

import android.content.res.Resources

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

		var apiController = APIController()

		var surveyController = SurveyController()
	}
}