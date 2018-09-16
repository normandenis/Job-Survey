package jobtrends.jobsurvey.service

import com.google.gson.Gson
import com.google.gson.GsonBuilder
import com.google.gson.JsonSyntaxException

class JsonController
{
    val gson: Gson

    constructor()
    {
        gson = GsonBuilder().excludeFieldsWithoutExposeAnnotation().create()
    }


    inline fun <reified T> deserialize(str: String?): T?
    {
        try
        {
            return gson.fromJson(str, T::class.java)
        }
        catch (exception: JsonSyntaxException)
        {
            return null
        }
    }

    fun <T> serialize(obj: T): String?
    {
        try
        {
            return gson.toJson(obj)
        }
        catch (exception: JsonSyntaxException)
        {
            return null
        }
    }
}
