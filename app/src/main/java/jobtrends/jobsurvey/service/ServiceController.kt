package jobtrends.jobsurvey.service

import android.content.Context
import android.content.SharedPreferences
import android.preference.PreferenceManager
import jobtrends.jobsurvey.model.ErrorModel
import jobtrends.jobsurvey.model.ResultModel
import jobtrends.jobsurvey.model.UserModel

var serviceController: ServiceController? = null

class ServiceController {
    var instances: MutableMap<Any?, Any?> = mutableMapOf()
    private var mContext: Context?

    constructor() {
        mContext = null
        register<JsonController>()
        register<UserModel>()
        register<UserModel>()
        register<ResultModel>()
        register<ErrorModel>()
        register<APIController>()
        register<SurveyController>()
    }

    fun saveUser(context: Context?) {
        mContext = context ?: return
        val apiController: APIController? = getInstance()
        apiController?.get("users/me", ::onUsersMeAnswer, mContext)
    }

    fun deleteUser(context: Context?) {
        mContext = context ?: return

        val userModel: UserModel? = getInstance()
        val apiController: APIController? = getInstance()
        apiController?.resetToken()
        userModel?.reset()

        val preferences: SharedPreferences? = PreferenceManager.getDefaultSharedPreferences(mContext)
        val editor: SharedPreferences.Editor? = preferences?.edit()
        editor?.putString(UserModel::class.java.simpleName, "")
        editor?.apply()
    }

    private fun onUsersMeAnswer(code: Int?, body: String?) {
        if (code != 200 && code != 201 && body == null && body == "") {
            return
        }
        val jsonController: JsonController? = getInstance()
        val oldUserModel: UserModel? = serviceController?.getInstance()
        val userModel: UserModel? = jsonController?.deserialize(body) ?: return
        userModel?.password?.set(oldUserModel?.password?.get())
        userModel?.passwordProtection?.set(oldUserModel?.password?.get())
        register(userModel, true)

        val preferences: SharedPreferences? = PreferenceManager.getDefaultSharedPreferences(mContext)
        val editor: SharedPreferences.Editor? = preferences?.edit()
        val json: String? = jsonController.serialize(userModel)
        editor?.putString(UserModel::class.java.simpleName, json)
        editor?.apply()
    }

    inline fun <reified T> register(obj: T? = null, new: Boolean? = false) {
        if (obj == null && new == null) {
            return
        }
        if (!instances.containsKey(T::class) || new == true) {
            instances[T::class] = (obj ?: T::class.java.newInstance()) as Any?
        }
    }

    inline fun <reified T> getInstance(): T {
        return instances[T::class] as T
    }
}
