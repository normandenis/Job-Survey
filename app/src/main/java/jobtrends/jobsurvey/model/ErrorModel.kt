package jobtrends.jobsurvey.model

import android.databinding.ObservableField

data class ErrorModel(
    var lastnameMsg: ObservableField<String?>? = ObservableField(),
    var firstnameMsg: ObservableField<String?>? = ObservableField(),
    var birthdayMsg: ObservableField<String?>? = ObservableField(),
    var emailMsg: ObservableField<String?>? = ObservableField(),
    var jobMsg: ObservableField<String?>? = ObservableField(),
    var oldPassword: ObservableField<String?>? = ObservableField(),
    var passwordMsg: ObservableField<String?>? = ObservableField(),
    var passwordBisMsg: ObservableField<String?>? = ObservableField(),
    var mainMsg: ObservableField<String?>? = ObservableField()
) {

    fun reset() {
        lastnameMsg?.set("")
        firstnameMsg?.set("")
        birthdayMsg?.set("")
        emailMsg?.set("")
        jobMsg?.set("")
        oldPassword?.set("")
        passwordMsg?.set("")
        passwordBisMsg?.set("")
        mainMsg?.set("")
    }
}