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
		return IOUtils.toString(inputStream, StandardCharsets.UTF_8)
	}

	fun getCategory() : String
	{
		val inputStream = resources?.openRawResource(R.raw.survey_category_example)
		return IOUtils.toString(inputStream, StandardCharsets.UTF_8)
	}
}