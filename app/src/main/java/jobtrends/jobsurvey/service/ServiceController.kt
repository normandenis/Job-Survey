package jobtrends.jobsurvey.service

import android.content.Context
import android.content.SharedPreferences
import android.preference.PreferenceManager
import jobtrends.jobsurvey.dagger.App
import jobtrends.jobsurvey.model.UserModel
import javax.inject.Inject


class ServiceController {

    @Inject
    lateinit var context: Context
    @Inject
    lateinit var jsonController: JsonController

    lateinit var apiController: ApiController
    val instances: MutableMap<Any?, Any?>

    init {
        App.component.inject(this)
        instances = mutableMapOf()
    }

    fun saveUser() {
        apiController.get("users/me", ::onUsersMeAnswer)
    }

    fun deleteUser() {
        val userModel: UserModel = getInstance()
        apiController.resetToken()
        userModel.reset()

        val preferences: SharedPreferences = PreferenceManager.getDefaultSharedPreferences(context)
        val editor: SharedPreferences.Editor = preferences.edit()
        editor.putString(UserModel::class.java.simpleName, "")
        editor.apply()
    }

    private fun onUsersMeAnswer(code: Int, body: String) {
        if (code != 200 && code != 201 && body == "") {
            return
        }
        val oldUserModel: UserModel = getInstance()
        val userModel: UserModel = jsonController.deserialize(body)
        userModel.password?.set(oldUserModel.password?.get())
        userModel.passwordProtection?.set(oldUserModel.password?.get())
        register(userModel, true)

        val preferences: SharedPreferences = PreferenceManager.getDefaultSharedPreferences(context)
        val editor: SharedPreferences.Editor = preferences.edit()
        val json: String = jsonController.serialize(userModel)
        editor.putString(UserModel::class.java.simpleName, json)
        editor.apply()
    }

    inline fun <reified T> register(obj: T? = null, new: Boolean? = false) {
        if (!instances.containsKey(T::class) || new == true) {
            instances[T::class] = (obj ?: T::class.java.newInstance()) as Any?
        }
    }

    inline fun <reified T> getInstance(): T {
        return try {
            instances[T::class] as T
        } catch (exception: Exception) {
            val obj = T::class.java.newInstance()
            register(obj)
            obj
        }
    }
}
