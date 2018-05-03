package jobtrends.jobsurvey.model

import android.databinding.ObservableField

class SurveyModel
{
  var id = ObservableField<String?>()
  var title = ObservableField<String?>()
  var questions = ObservableField<List<QuestionModel>>()
  var require = ObservableField<List<Any?>>()
  var themeId = ObservableField<Int?>()
}

class Survey
{
  var id: String? = null
  var title: String? = null
  var questions: List<Question>? = null
  var require: List<Any?>? = null
  var themeId: Int? = null
}
