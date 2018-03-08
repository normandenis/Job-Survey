package jobtrends.jobaymax.service

import com.google.gson.Gson

class JsonController
{
  val gson = Gson()
  inline fun <reified T> deserialize(str : String) : T
  {
    return gson.fromJson(str, T::class.java)
  }

  fun <T> serialize(obj : T) : String
  {
    return gson.toJson(obj)
  }
}

