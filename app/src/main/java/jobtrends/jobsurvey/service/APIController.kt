package jobtrends.jobsurvey.service

import android.content.Context
import android.content.res.Resources
import com.android.volley.NetworkResponse
import com.android.volley.Request
import com.android.volley.Response
import com.android.volley.toolbox.HttpHeaderParser
import com.android.volley.toolbox.StringRequest
import com.android.volley.toolbox.Volley
import jobtrends.jobsurvey.BuildConfig
import jobtrends.jobsurvey.R
import jobtrends.jobsurvey.model.BodyModel
import jobtrends.jobsurvey.model.UserModel
import org.apache.commons.io.IOUtils
import java.io.InputStream
import java.nio.charset.StandardCharsets

class APIController {
    var token: String? = null
    var statusCode: Int? = null
    val urlBase: String?

    constructor() {
        if (BuildConfig.FLAVOR == "dev") {
            urlBase = "https://api.dev.jobtrends.io/v2/"
        } else {
            urlBase = "https://api.jobtrends.io/v2/"
        }
    }

    fun resetToken() {
        token = null
    }

    fun isToken(): Boolean {
        if (token == null || token == "") {
            return false
        }
        return true
    }

    fun post(url: String?, json: String?, callback: (Int?, String?) -> Unit?, context: Context?) {
        val queue = Volley.newRequestQueue(context)
        val stringRequest = object : StringRequest(Request.Method.POST, urlBase + url, Response.Listener<String?> { s ->
            callback(statusCode, s)
        }, Response.ErrorListener { s ->
            val jsonController: JsonController? = serviceController?.getInstance()
            val data: String? = try {
                String(s?.networkResponse?.data!!)
            } catch (e: Exception) {
                null
            }
            val bodyModel: BodyModel? = jsonController?.deserialize(data)
            callback(s?.networkResponse?.statusCode, bodyModel?.message?.get()
                    ?: "Nous rencontrons des soucis avec nos serveurs, veuillez nous excuser de la gêne occasionnée.")
        }) {

            override fun parseNetworkResponse(response: NetworkResponse?): Response<String?> {
                if (token == null || token == "") {
                    token = response?.headers?.get("x-access-token")
                    val user: UserModel? = serviceController?.getInstance()
                    user?.token?.set(token)
                }
                statusCode = response?.statusCode
                val data: String? = try {
                    String(response?.data!!)
                } catch (e: Exception) {
                    null
                }
                return Response.success(data, HttpHeaderParser.parseCacheHeaders(response))
            }

            override fun getBody(): ByteArray? =
                    if (json != null && json != "") json.toByteArray() else null

            override fun getHeaders(): MutableMap<String?, String?> {
                val tmp = mutableMapOf<String?, String?>()
                if (token != null) {
                    tmp["x-access-token"] = token
                }
                tmp["Content-Type"] = "application/json"
                return tmp
            }
        }
        queue.add(stringRequest)
    }

    fun patch(url: String?, json: String?, callback: (Int?, String?) -> Unit?, context: Context?) {
        val queue = Volley.newRequestQueue(context)
        val stringRequest = object :
                StringRequest(Request.Method.PATCH, urlBase + url, Response.Listener<String?> { s ->
                    callback(statusCode, s)
                }, Response.ErrorListener { s ->
                    val jsonController: JsonController? = serviceController?.getInstance()
                    val data: String? = try {
                        String(s.networkResponse.data)
                    } catch (e: Exception) {
                        null
                    }
                    val bodyModel: BodyModel? = jsonController?.deserialize(data)
                    callback(s?.networkResponse?.statusCode, bodyModel?.message?.get()
                            ?: "Nous rencontrons des soucis avec nos serveurs, veuillez nous excuser de la gêne occasionnée.")
                }) {
            override fun parseNetworkResponse(response: NetworkResponse?): Response<String?> {
                if (token == null || token == "") {
                    token = response?.headers?.get("x-access-token")
                    val user: UserModel? = serviceController?.getInstance()
                    user?.token?.set(token)
                }
                val data: String? = try {
                    String(response?.data!!)
                } catch (e: Exception) {
                    null
                }
                return Response.success(data, HttpHeaderParser.parseCacheHeaders(response))
            }

            override fun getBody(): ByteArray? = json?.toByteArray()

            override fun getHeaders(): MutableMap<String?, String?> {
                val tmp = mutableMapOf<String?, String?>()
                if (token != null) {
                    tmp["x-access-token"] = token
                }
                tmp["Content-Type"] = "application/json"
                return tmp
            }
        }
        queue.add(stringRequest)
    }

    fun get(url: String?, callback: (Int?, String?) -> Unit?, context: Context?) {
        val queue = Volley.newRequestQueue(context)
        val stringRequest = object :
                StringRequest(Request.Method.GET, urlBase + url, Response.Listener<String?> { s ->
                    callback(statusCode, s)
                }, Response.ErrorListener { s ->
                    val jsonController: JsonController? = serviceController?.getInstance()
                    val data: String? = try {
                        String(s.networkResponse.data)
                    } catch (e: Exception) {
                        null
                    }
                    val bodyModel: BodyModel? = jsonController?.deserialize(data)
                    callback(s.networkResponse.statusCode, bodyModel?.message?.get()
                            ?: "Nous rencontrons des soucis avec nos serveurs, veuillez nous excuser de la gêne occasionnée.")
                }) {
            override fun parseNetworkResponse(response: NetworkResponse?): Response<String?> {
                val data: String? = try {
                    String(response?.data!!)
                } catch (e: Exception) {
                    null
                }
                return Response.success(data, HttpHeaderParser.parseCacheHeaders(response))
            }

            override fun getHeaders(): MutableMap<String?, String?> {
                val tmp = mutableMapOf<String?, String?>()
                if (token != null) {
                    tmp["x-access-token"] = token
                }
                tmp["Content-Type"] = "application/json"
                return tmp
            }
        }
        queue.add(stringRequest)
    }

    fun getFAQQuestion(): String? {
        val resources: Resources? = serviceController?.getInstance()
        val inputStream: InputStream? = resources?.openRawResource(R.raw.question_faq_example)
        return IOUtils.toString(inputStream, StandardCharsets.UTF_8)
    }
}