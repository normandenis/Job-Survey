package jobtrends.jobaymax.model

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
  var longitude = ObservableField<Int>()
  var latitude = ObservableField<Int>()
  var signInCount = ObservableField<Int>()
  var resetToken = ObservableField<Any>()
  var resetTimestamp = ObservableField<Int>()
}

class User
{
  var id : String? = null
  var email : String? = null
  var firstName : String? = null
  var lastName : String? = null
  var password : String? = null
  var birthday : Int? = null
  var encryptedPassword : String? = null
  var metier : String? = null
  var telephonePortable : Any? = null
  var isAdmin : Boolean? = null
  var longitude : Int? = null
  var latitude : Int? = null
  var signInCount : Int? = null
  var resetToken : Any? = null
  var resetTimestamp : Int? = null
}