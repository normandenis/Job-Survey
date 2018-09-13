package jobtrends.jobsurvey.service

import com.google.gson.Gson
import com.google.gson.GsonBuilder

class JsonController
{
    val gson: Gson

    constructor()
    {
        gson = GsonBuilder().excludeFieldsWithoutExposeAnnotation().create()
    }


    inline fun <reified T> deserialize(str: String?): T
    {
        return gson.fromJson(str, T::class.java)
    }

    fun <T> serialize(obj: T): String?
    {
        return gson.toJson(obj)
    }
}
