package jobtrends.job_aymax.service

import jobtrends.job_aymax.R
import jobtrends.job_aymax.service.ServiceController.Companion.resources
import org.apache.commons.io.IOUtils
import java.nio.charset.StandardCharsets

class APIController
{
	fun getSurvey() : String
	{
		val inputStream = resources?.openRawResource(R.raw.survey_example)
		val strSurvey = IOUtils.toString(inputStream, StandardCharsets.UTF_8)
		return strSurvey
	}
}