package jobtrends.jobsurvey.model

import android.databinding.ObservableField

class ErrorModel {
    var lastnameMsg: ObservableField<String?>?
    var firstnameMsg: ObservableField<String?>?
    var birthdayMsg: ObservableField<String?>?
    var emailMsg: ObservableField<String?>?
    var jobMsg: ObservableField<String?>?
    var oldPassword: ObservableField<String?>?
    var passwordMsg: ObservableField<String?>?
    var passwordBisMsg: ObservableField<String?>?
    var mainMsg: ObservableField<String?>?

    constructor() {
        lastnameMsg = ObservableField()
        firstnameMsg = ObservableField()
        birthdayMsg = ObservableField()
        emailMsg = ObservableField()
        jobMsg = ObservableField()
        oldPassword = ObservableField()
        passwordMsg = ObservableField()
        passwordBisMsg = ObservableField()
        mainMsg = ObservableField()
    }

    fun reset() {
        lastnameMsg?.set(null)
        firstnameMsg?.set(null)
        birthdayMsg?.set(null)
        emailMsg?.set(null)
        jobMsg?.set(null)
        oldPassword?.set(null)
        passwordMsg?.set(null)
        passwordBisMsg?.set(null)
        mainMsg?.set(null)
    }
}