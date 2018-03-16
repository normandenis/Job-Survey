package jobtrends.jobaymax.model

class EndSurveyModel
{
  var surveyId : String? = null
  var answers : List<Answer>? = null
}

class EndSurvey
{
  var surveyId : String? = null
  var answers : MutableList<Reply>? = null
}
