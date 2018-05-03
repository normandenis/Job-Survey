package jobtrends.jobsurvey.model

import android.databinding.ObservableField

class ThemeModel
{
  var name = ObservableField<String?>()
  var surveyId = ObservableField<String?>()
  var themeId = ObservableField<Int?>()
  var lvl = ObservableField<Int?>()
  var open = ObservableField<Boolean?>()
}

class Theme
{
  var name: String? = null
  var survey_id: String? = null
  var theme_id: Int? = null
  var lvl: Int? = null
  var open: Boolean? = null
}