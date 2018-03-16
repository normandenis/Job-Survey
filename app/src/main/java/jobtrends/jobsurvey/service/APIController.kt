package jobtrends.jobsurvey.service

import android.content.Context
import com.android.volley.NetworkResponse
import com.android.volley.Request
import com.android.volley.Response
import com.android.volley.toolbox.HttpHeaderParser
import com.android.volley.toolbox.StringRequest
import com.android.volley.toolbox.Volley
import jobtrends.jobsurvey.R
import jobtrends.jobsurvey.service.ServiceController.Companion.resources
import org.apache.commons.io.IOUtils
import java.nio.charset.StandardCharsets

class APIController
{
  var token : String? = null

  fun post(url : String, json : String, callback : (res : String) -> Unit, context : Context)
  {
    val queue = Volley.newRequestQueue(context)
    val stringRequest = object :
        StringRequest(Request.Method.POST, url, Response.Listener<String> { s ->
          callback(s)
        }, Response.ErrorListener(::println))
    {
      override fun parseNetworkResponse(response : NetworkResponse?) : Response<String>
      {
        token = "Bearer " + response?.headers?.get("X-AUTH-TOKEN")
        val data = String(response?.data !!)
        return Response.success(data, HttpHeaderParser.parseCacheHeaders(response))
      }

      override fun getBody() : ByteArray = json.toByteArray()

      override fun getHeaders() : MutableMap<String, String>
      {
        val tmp = mutableMapOf<String, String>()
        if (token != null)
        {
          tmp["Authorization"] = token !!
        }
        tmp["Content-Type"] = "application/json"
        return tmp
      }
    }
    queue.add(stringRequest)
  }

  fun get(url : String, callback : (res : String) -> Unit, context : Context)
  {
    val queue = Volley.newRequestQueue(context)
    val stringRequest = object :
        StringRequest(Request.Method.GET, url, Response.Listener<String> { s ->
          callback(s)
        }, Response.ErrorListener(::println))
    {
      override fun parseNetworkResponse(response : NetworkResponse?) : Response<String>
      {
        val data = String(response?.data !!)
        return Response.success(data, HttpHeaderParser.parseCacheHeaders(response))
      }

      override fun getHeaders() : MutableMap<String, String>
      {
        val tmp = mutableMapOf<String, String>()
        if (token != null)
        {
          tmp["Authorization"] = token !!
        }
        tmp["Content-Type"] = "application/json"
        return tmp
      }
    }
    queue.add(stringRequest)
  }

  fun getSurvey() : String
  {
    val inputStream = resources?.openRawResource(R.raw.survey_example)
    return IOUtils.toString(inputStream, StandardCharsets.UTF_8)
  }

  fun getCategory() : String
  {
    val inputStream = resources?.openRawResource(R.raw.survey_category_example)
    return IOUtils.toString(inputStream, StandardCharsets.UTF_8)
  }

  fun getFAQQuestion() : String
  {
    val inputStream = resources?.openRawResource(R.raw.question_faq_example)
    return IOUtils.toString(inputStream, StandardCharsets.UTF_8)
  }
}