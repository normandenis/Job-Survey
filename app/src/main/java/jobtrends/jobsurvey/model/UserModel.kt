package jobtrends.jobsurvey.model

import android.databinding.ObservableField

class UserModel
{
  var id: ObservableField<String?>?
  var email: ObservableField<String?>?
  var firstName: ObservableField<String?>?
  var lastName: ObservableField<String?>?
  var password: ObservableField<String?>?
  var birthday: ObservableField<String?>?
  var encryptedPassword: ObservableField<String?>?
  var metier: ObservableField<String?>?
  var telephonePortable: ObservableField<Any?>?
  var isAdmin: ObservableField<Boolean?>?
  var newsletter: ObservableField<Boolean?>?
  var jobaymax: ObservableField<Boolean?>?
  var signInCount: ObservableField<Int?>?
  var resetToken: ObservableField<String?>?
  var resetTimestamp: ObservableField<Int?>?

  constructor()
  {
    id = ObservableField()
    email = ObservableField()
    firstName = ObservableField()
    lastName = ObservableField()
    password = ObservableField()
    birthday = ObservableField()
    encryptedPassword = ObservableField()
    metier = ObservableField()
    telephonePortable = ObservableField()
    isAdmin = ObservableField()
    newsletter = ObservableField()
    jobaymax = ObservableField()
    signInCount = ObservableField()
    resetToken = ObservableField()
    resetTimestamp = ObservableField()
    newsletter?.set(false)
    jobaymax?.set(false)
  }

  fun reset()
  {
    lastName?.set("")
    firstName?.set("")
    birthday?.set("")
    email?.set("")
    metier?.set("")
    password?.set("")
    encryptedPassword?.set("")
  }

  fun merge(userModel: UserModel?)
  {
    id?.set(if (userModel?.id?.get() == null) id?.get() else userModel.id?.get())
    email?.set(if (userModel?.email?.get() == null) email?.get() else userModel.email?.get())
    firstName?.set(if (userModel?.firstName?.get() == null) firstName?.get() else userModel.firstName?.get())
    lastName?.set(if (userModel?.lastName?.get() == null) lastName?.get() else userModel.lastName?.get())
    password?.set(if (userModel?.password?.get() == null) password?.get() else userModel.password?.get())
    birthday?.set(if (userModel?.birthday?.get() == null) birthday?.get() else userModel.birthday?.get())
    encryptedPassword?.set(
      if (userModel?.encryptedPassword?.get() == null) encryptedPassword?.get() else userModel.encryptedPassword?.get())
    metier?.set(if (userModel?.metier?.get() == null) metier?.get() else userModel.metier?.get())
    telephonePortable?.set(
      if (userModel?.telephonePortable?.get() == null) telephonePortable?.get() else userModel.telephonePortable?.get())
    isAdmin?.set(if (userModel?.isAdmin?.get() == null) isAdmin?.get() else userModel.isAdmin?.get())
    newsletter?.set(if (userModel?.newsletter?.get() == null) newsletter?.get() else userModel.newsletter?.get())
    jobaymax?.set(if (userModel?.jobaymax?.get() == null) jobaymax?.get() else userModel.jobaymax?.get())
    signInCount?.set(if (userModel?.signInCount?.get() == null) signInCount?.get() else userModel.signInCount?.get())
    resetToken?.set(if (userModel?.resetToken?.get() == null) resetToken?.get() else userModel.resetToken?.get())
    resetTimestamp
      ?.set(if (userModel?.resetTimestamp?.get() == null) resetTimestamp?.get() else userModel.resetTimestamp?.get())
  }

  fun merge(user: User?)
  {
    id?.set(if (user?.id == null) id?.get() else user.id)
    email?.set(if (user?.email == null) email?.get() else user.email)
    firstName?.set(if (user?.firstName == null) firstName?.get() else user.firstName)
    lastName?.set(if (user?.lastName == null) lastName?.get() else user.lastName)
    password?.set(if (user?.password == null) password?.get() else user.password)
    birthday?.set(if (user?.birthday == null) birthday?.get() else user.birthday)
    encryptedPassword?.set(
      if (user?.encryptedPassword == null) encryptedPassword?.get() else user.encryptedPassword)
    metier?.set(if (user?.metier == null) metier?.get() else user.metier)
    telephonePortable?.set(
      if (user?.telephonePortable == null) telephonePortable?.get() else user.telephonePortable)
    isAdmin?.set(if (user?.isAdmin == null) isAdmin?.get() else user.isAdmin)
    newsletter?.set(if (user?.newsletter == null) newsletter?.get() else user.newsletter)
    jobaymax?.set(if (user?.jobaymax == null) jobaymax?.get() else user.jobaymax)
    signInCount?.set(if (user?.signInCount == null) signInCount?.get() else user.signInCount)
    resetToken?.set(if (user?.resetToken == null) resetToken?.get() else user.resetToken)
    resetTimestamp?.set(if (user?.resetTimestamp == null) resetTimestamp?.get() else user.resetTimestamp)
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

  fun merge(user: User?)
  {
    id = if (user?.id != null) user.id else id
    email = if (user?.email != null) user.email else email
    firstName = if (user?.firstName != null) user.firstName else firstName
    lastName = if (user?.lastName != null) user.lastName else lastName
    password = if (user?.password != null) user.password else password
    birthday = if (user?.birthday != null) user.birthday else birthday
    encryptedPassword = if (user?.encryptedPassword != null) user.encryptedPassword else encryptedPassword
    metier = if (user?.metier != null) user.metier else metier
    telephonePortable = if (user?.telephonePortable != null) user.telephonePortable else telephonePortable
    isAdmin = if (user?.isAdmin != null) user.isAdmin else isAdmin
    newsletter = if (user?.newsletter != null) user.newsletter else newsletter
    jobaymax = if (user?.jobaymax != null) user.jobaymax else jobaymax
    signInCount = if (user?.signInCount != null) user.signInCount else signInCount
    resetToken = if (user?.resetToken != null) user.resetToken else resetToken
    resetTimestamp = if (user?.resetTimestamp != null) user.resetTimestamp else resetTimestamp
  }

  fun merge(userModel: UserModel?)
  {
    id = if (userModel?.id?.get() != null) userModel.id?.get() else id
    email = if (userModel?.email?.get() != null) userModel.email?.get() else email
    firstName = if (userModel?.firstName?.get() != null) userModel.firstName?.get() else firstName
    lastName = if (userModel?.lastName?.get() != null) userModel.lastName?.get() else lastName
    password = if (userModel?.password?.get() != null) userModel.password?.get() else password
    birthday = if (userModel?.birthday?.get() != null) userModel.birthday?.get() else birthday
    encryptedPassword = if (userModel?.encryptedPassword?.get() != null) userModel.encryptedPassword?.get() else encryptedPassword
    metier = if (userModel?.metier?.get() != null) userModel.metier?.get() else metier
    telephonePortable = if (userModel?.telephonePortable?.get() != null) userModel.telephonePortable else telephonePortable
    isAdmin = if (userModel?.isAdmin?.get() != null) userModel.isAdmin?.get() else isAdmin
    newsletter = if (userModel?.newsletter?.get() != null) userModel.newsletter?.get() else newsletter
    jobaymax = if (userModel?.jobaymax?.get() != null) userModel.jobaymax?.get() else jobaymax
    signInCount = if (userModel?.signInCount?.get() != null) userModel.signInCount?.get() else signInCount
    resetToken = if (userModel?.resetToken?.get() != null) userModel.resetToken?.get() else resetToken
    resetTimestamp = if (userModel?.resetTimestamp?.get() != null) userModel.resetTimestamp?.get() else resetTimestamp
  }
}