package jobtrends.jobsurvey.model

import android.databinding.ObservableField

class AnswerModel
{
  var title = ObservableField<String>()
  var value = ObservableField<String>()
}

class Answer
{
  var title : String? = null
  var value : String? = null
}