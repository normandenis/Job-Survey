package jobtrends.jobsurvey.model

import android.databinding.ObservableField

class QuestionModel
{
  var id = ObservableField<String>()
  var content = ObservableField<String>()
  var answers = ObservableField<List<AnswerModel>>()
  var type = ObservableField<Int>()
  var description = ObservableField<String>()
}

class Question
{
  var id : String? = null
  var content : String? = null
  var answers : List<Answer>? = null
  var type : Int? = null
  var description : String? = null
}