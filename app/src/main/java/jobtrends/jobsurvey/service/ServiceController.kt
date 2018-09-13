package jobtrends.jobsurvey.service

import jobtrends.jobsurvey.model.ErrorModel
import jobtrends.jobsurvey.model.ResultModel
import jobtrends.jobsurvey.model.UserModel

var serviceController: ServiceController? = null

class ServiceController
{
    var instances: MutableMap<Any?, Any?> = mutableMapOf()

    constructor()
    {
        register<JsonController>()
        register<UserModel>()
        register<UserModel>()
        register<ResultModel>()
        register<ErrorModel>()
        register<APIController>()
        register<SurveyController>()
    }

    inline fun <reified T> register(obj: T? = null, new: Boolean? = false)
    {
        if (!instances.containsKey(T::class) || new == true)
        {
            instances[T::class] = (obj ?: T::class.java.newInstance()) as Any?
        }
    }

    inline fun <reified T> getInstance(): T
    {
        return instances[T::class] as T
    }
}