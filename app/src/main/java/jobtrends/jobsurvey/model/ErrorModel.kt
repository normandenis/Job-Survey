package jobtrends.jobsurvey.model

import android.databinding.ObservableField

class ErrorModel
{
  var lastnameMsg: ObservableField<String?>?
  var firstnameMsg: ObservableField<String?>?
  var birthdayMsg: ObservableField<String?>?
  var emailMsg: ObservableField<String?>?
  var jobMsg: ObservableField<String?>?
  var passwordMsg: ObservableField<String?>?
  var passwordBisMsg: ObservableField<String?>?
  var mainMsg: ObservableField<String?>?

  constructor()
  {
    lastnameMsg = ObservableField()
    firstnameMsg = ObservableField()
    birthdayMsg = ObservableField()
    emailMsg = ObservableField()
    jobMsg = ObservableField()
    passwordMsg = ObservableField()
    passwordBisMsg = ObservableField()
    mainMsg = ObservableField()
  }
}