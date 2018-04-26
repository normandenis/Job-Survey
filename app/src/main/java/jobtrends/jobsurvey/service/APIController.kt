package jobtrends.jobsurvey.service

import android.app.Application
import android.content.Context
import android.content.res.Resources
import android.preference.PreferenceManager
import android.util.Log
import android.view.View
import com.android.volley.NetworkResponse
import com.android.volley.Request
import com.android.volley.Response
import com.android.volley.toolbox.HttpHeaderParser
import com.android.volley.toolbox.StringRequest
import com.android.volley.toolbox.Volley
import jobtrends.jobsurvey.R
import jobtrends.jobsurvey.model.User
import org.apache.commons.io.IOUtils
import java.nio.charset.StandardCharsets

class APIController
{
  var token: String? = null
  var statusCode: Int? = null
  val urlBase: String = "https://api.dev.jobtrends.io/"

  fun post(url: String, json: String?, callback: (code: Int, res: String) -> Unit, context: Context)
  {
    val queue = Volley.newRequestQueue(context)
    val stringRequest = object :
      StringRequest(Request.Method.POST, urlBase + url, Response.Listener<String> { s ->
        callback(statusCode!!, s)
      }, Response.ErrorListener { s ->
        callback(s.networkResponse.statusCode, "Failure")
      })
    {

      override fun parseNetworkResponse(response: NetworkResponse?): Response<String>
      {
        if (token == null)
        {
          token = "Bearer " + response?.headers?.get("X-AUTH-TOKEN")
          val user = serviceController!!.getInstance<User>()
          user.resetToken = token
        }
        statusCode = response!!.statusCode
        val data = String(response.data!!)
        return Response.success(data, HttpHeaderParser.parseCacheHeaders(response))
      }

      override fun getBody(): ByteArray? = if (json != null && json != "") json.toByteArray() else null

      override fun getHeaders(): MutableMap<String, String>
      {
        val tmp = mutableMapOf<String, String>()
        if (token != null)
        {
          tmp["Authorization"] = token!!
        }
        tmp["Content-Type"] = "application/json"
        return tmp
      }
    }
    queue.add(stringRequest)
  }

  fun put(url: String, json: String, callback: (res: String) -> Unit, context: Context)
  {
    val queue = Volley.newRequestQueue(context)
    val stringRequest = object :
      StringRequest(Request.Method.PUT, urlBase + url, Response.Listener<String> { s ->
        callback(s)
      }, Response.ErrorListener(::println))
    {
      override fun parseNetworkResponse(response: NetworkResponse?): Response<String>
      {
        if (token == null)
        {
          token = "Bearer " + response?.headers?.get("X-AUTH-TOKEN")
          val user = serviceController!!.getInstance<User>()
          user.resetToken = token
        }
        val data = String(response?.data!!)
        return Response.success(data, HttpHeaderParser.parseCacheHeaders(response))
      }

      override fun getBody(): ByteArray = json.toByteArray()

      override fun getHeaders(): MutableMap<String, String>
      {
        val tmp = mutableMapOf<String, String>()
        if (token != null)
        {
          tmp["Authorization"] = token!!
        }
        tmp["Content-Type"] = "application/json"
        return tmp
      }
    }
    queue.add(stringRequest)
  }

  fun get(url: String, callback: (res: String) -> Unit, context: Context)
  {
    val queue = Volley.newRequestQueue(context)
    val stringRequest = object :
      StringRequest(Request.Method.GET, urlBase + url, Response.Listener<String> { s ->
        callback(s)
      }, Response.ErrorListener(::println))
    {
      override fun parseNetworkResponse(response: NetworkResponse?): Response<String>
      {
        val data = String(response?.data!!)
        return Response.success(data, HttpHeaderParser.parseCacheHeaders(response))
      }

      override fun getHeaders(): MutableMap<String, String>
      {
        val tmp = mutableMapOf<String, String>()
        if (token != null)
        {
          tmp["Authorization"] = token!!
        }
        tmp["Content-Type"] = "application/json"
        return tmp
      }
    }
    queue.add(stringRequest)
  }

  fun getFAQQuestion(): String
  {
    val resources = serviceController!!.getInstance<Resources>()
    val inputStream = resources.openRawResource(R.raw.question_faq_example)
    return IOUtils.toString(inputStream, StandardCharsets.UTF_8)
  }
}