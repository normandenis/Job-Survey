package jobtrends.jobsurvey.model

import android.databinding.ObservableField

class UserModel
{
  var id = ObservableField<String>()
  var email = ObservableField<String>()
  var firstName = ObservableField<String>()
  var lastName = ObservableField<String>()
  var password = ObservableField<String>()
  var birthday = ObservableField<String>()
  var encryptedPassword = ObservableField<String>()
  var metier = ObservableField<String>()
  var telephonePortable = ObservableField<Any>()
  var isAdmin = ObservableField<Boolean>()
  var newsletter = ObservableField<Boolean>()
  var jobaymax = ObservableField<Boolean>()
  var signInCount = ObservableField<Int>()
  var resetToken = ObservableField<String>()
  var resetTimestamp = ObservableField<Int>()

  fun merge(user: User)
  {
    id.set(if (user.id == null) id.get() else user.id)
    email.set(if (user.email == null) email.get() else user.email)
    firstName.set(if (user.firstName == null) firstName.get() else user.firstName)
    lastName.set(if (user.lastName == null) lastName.get() else user.lastName)
    password.set(if (user.password == null) password.get() else user.password)
    birthday.set(if (user.birthday == null) birthday.get() else user.birthday.toString())
    encryptedPassword.set(if (user.encryptedPassword == null) encryptedPassword.get() else user.encryptedPassword)
    metier.set(if (user.metier == null) metier.get() else user.metier)
    telephonePortable.set(if (user.telephonePortable == null) telephonePortable.get() else user.telephonePortable)
    isAdmin.set(if (user.isAdmin == null) isAdmin.get() else user.isAdmin)
    newsletter.set(if (user.newsletter == null) newsletter.get() else user.newsletter)
    jobaymax.set(if (user.jobaymax == null) jobaymax.get() else user.jobaymax)
    signInCount.set(if (user.signInCount == null) signInCount.get() else user.signInCount)
    resetToken.set(if (user.resetToken == null) resetToken.get() else user.resetToken)
    resetTimestamp.set(if (user.resetTimestamp == null) resetTimestamp.get() else user.resetTimestamp)
  }
}

class User
{
  var id: String? = null
  var email: String? = null
  var firstName: String? = null
  var lastName: String? = null
  var password: String? = null
  var birthday: String? = null
  var encryptedPassword: String? = null
  var metier: String? = null
  var telephonePortable: Any? = null
  var isAdmin: Boolean? = null
  var newsletter: Boolean? = null
  var jobaymax: Boolean? = null
  var signInCount: Int? = null
  var resetToken: String? = null
  var resetTimestamp: Int? = null

  fun merge(user: User)
  {
    id = if (user.id != null) user.id else id
    email = if (user.email != null) user.email else email
    firstName = if (user.firstName != null) user.firstName else firstName
    lastName = if (user.lastName != null) user.lastName else lastName
    password = if (user.password != null) user.password else password
    birthday = if (user.birthday != null) user.birthday else birthday
    encryptedPassword = if (user.encryptedPassword != null) user.encryptedPassword else encryptedPassword
    metier = if (user.metier != null) user.metier else metier
    telephonePortable = if (user.telephonePortable != null) user.telephonePortable else telephonePortable
    isAdmin = if (user.isAdmin != null) user.isAdmin else isAdmin
    newsletter = if (user.newsletter != null) user.newsletter else newsletter
    jobaymax = if (user.jobaymax != null) user.jobaymax else jobaymax
    signInCount = if (user.signInCount != null) user.signInCount else signInCount
    resetToken = if (user.resetToken != null) user.resetToken else resetToken
    resetTimestamp = if (user.resetTimestamp != null) user.resetTimestamp else resetTimestamp
  }

  fun merge(user: UserModel)
  {
    id = if (user.id.get() != null) user.id.get() else id
    email = if (user.email.get() != null) user.email.get() else email
    firstName = if (user.firstName.get() != null) user.firstName.get() else firstName
    lastName = if (user.lastName.get() != null) user.lastName.get() else lastName
    password = if (user.password.get() != null) user.password.get() else password
    birthday = if (user.birthday.get() != null) user.birthday.get() else birthday
    encryptedPassword = if (user.encryptedPassword.get() != null) user.encryptedPassword.get() else encryptedPassword
    metier = if (user.metier.get() != null) user.metier.get() else metier
    telephonePortable = if (user.telephonePortable.get() != null) user.telephonePortable else telephonePortable
    isAdmin = if (user.isAdmin.get() != null) user.isAdmin.get() else isAdmin
    newsletter = if (user.newsletter.get() != null) user.newsletter.get() else newsletter
    jobaymax = if (user.jobaymax.get() != null) user.jobaymax.get() else jobaymax
    signInCount = if (user.signInCount.get() != null) user.signInCount.get() else signInCount
    resetToken = if (user.resetToken.get() != null) user.resetToken.get() else resetToken
    resetTimestamp = if (user.resetTimestamp.get() != null) user.resetTimestamp.get() else resetTimestamp
  }
}