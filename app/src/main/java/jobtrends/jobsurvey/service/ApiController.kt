package jobtrends.jobsurvey.service

import android.content.Context
import android.content.res.Resources
import com.android.volley.NetworkResponse
import com.android.volley.Request
import com.android.volley.RequestQueue
import com.android.volley.Response
import com.android.volley.toolbox.HttpHeaderParser
import com.android.volley.toolbox.StringRequest
import com.android.volley.toolbox.Volley
import jobtrends.jobsurvey.BuildConfig
import jobtrends.jobsurvey.R
import jobtrends.jobsurvey.dagger.App
import jobtrends.jobsurvey.model.BodyModel
import jobtrends.jobsurvey.model.UserModel
import org.apache.commons.io.IOUtils
import java.io.InputStream
import java.nio.charset.StandardCharsets
import javax.inject.Inject

class ApiController {

    @Inject
    lateinit var jsonController: JsonController
    @Inject
    lateinit var resources: Resources
    @Inject
    lateinit var context: Context
    @Inject
    lateinit var serviceController: ServiceController

    private var token: String
    private var statusCode: Int
    private val urlBase: String
    private val errorMessage: String

    init {
        App.component.inject(this)
        inject()
        statusCode = 0
        token = ""
        errorMessage = "Nous rencontrons des soucis avec nos serveurs, veuillez nous excuser de la gêne occasionnée."
        if (BuildConfig.FLAVOR == "dev") {
            urlBase = "https://api.dev.jobtrends.io/v2/"
        } else {
            urlBase = "https://api.jobtrends.io/v2/"
        }
    }

    fun inject() {
        serviceController.apiController = this
    }

    fun resetToken() {
        token = ""
    }

    fun isToken(): Boolean {
        if (token == "") {
            return false
        }
        return true
    }

    fun post(url: String, json: String, callback: (Int, String) -> Unit) {
        val queue: RequestQueue = Volley.newRequestQueue(context)
        val stringRequest: StringRequest = object : StringRequest(Request.Method.POST, urlBase + url, Response.Listener<String> { s ->
            callback(statusCode, s)
        }, Response.ErrorListener { s ->
            val data: String = try {
                String(s.networkResponse.data)
            } catch (e: Exception) {
                ""
            }
            val bodyModel: BodyModel = jsonController.deserialize(data)
            callback(s.networkResponse.statusCode, bodyModel.message?.get()
                    ?: errorMessage)
        }) {

            override fun parseNetworkResponse(response: NetworkResponse): Response<String> {
                if (token == "") {
                    token = response.headers["x-access-token"] ?: token
                }
                statusCode = response.statusCode
                val data: String = try {
                    String(response.data)
                } catch (e: Exception) {
                    ""
                }
                return Response.success(data, HttpHeaderParser.parseCacheHeaders(response))
            }

            override fun getBody(): ByteArray = json.toByteArray()

            override fun getHeaders(): MutableMap<String, String> {
                val tmp = mutableMapOf<String, String>()
                if (token != "") {
                    tmp["x-access-token"] = token
                }
                tmp["Content-Type"] = "application/json"
                return tmp
            }
        }
        queue.add(stringRequest)
    }

    fun patch(url: String, json: String, callback: (Int, String) -> Unit) {
        val queue: RequestQueue = Volley.newRequestQueue(context)
        val stringRequest: StringRequest = object :
                StringRequest(Request.Method.PATCH, urlBase + url, Response.Listener<String> { s ->
                    callback(statusCode, s)
                }, Response.ErrorListener { s ->
                    val data: String = try {
                        String(s.networkResponse.data)
                    } catch (e: Exception) {
                        ""
                    }
                    val bodyModel: BodyModel = jsonController.deserialize(data)
                    callback(s.networkResponse.statusCode, bodyModel.message?.get()
                            ?: errorMessage)
                }) {
            override fun parseNetworkResponse(response: NetworkResponse): Response<String> {
                if (token == "") {
                    token = response.headers["x-access-token"] ?: token
                }
                val data: String = try {
                    String(response.data)
                } catch (e: Exception) {
                    ""
                }
                return Response.success(data, HttpHeaderParser.parseCacheHeaders(response))
            }

            override fun getBody(): ByteArray = json.toByteArray()

            override fun getHeaders(): MutableMap<String, String> {
                val tmp = mutableMapOf<String, String>()
                if (token != "") {
                    tmp["x-access-token"] = token
                }
                tmp["Content-Type"] = "application/json"
                return tmp
            }
        }
        queue.add(stringRequest)
    }

    fun get(url: String, callback: (Int, String) -> Unit) {
        val queue = Volley.newRequestQueue(context)
        val stringRequest = object :
                StringRequest(Request.Method.GET, urlBase + url, Response.Listener<String> { s ->
                    callback(statusCode, s)
                }, Response.ErrorListener { s ->
                    val data: String = try {
                        String(s.networkResponse.data)
                    } catch (e: Exception) {
                        ""
                    }
                    val bodyModel: BodyModel = jsonController.deserialize(data)
                    callback(s.networkResponse.statusCode, bodyModel.message?.get()
                            ?: errorMessage)
                }) {
            override fun parseNetworkResponse(response: NetworkResponse): Response<String> {
                val data: String = try {
                    String(response.data)
                } catch (e: Exception) {
                    ""
                }
                return Response.success(data, HttpHeaderParser.parseCacheHeaders(response))
            }

            override fun getHeaders(): MutableMap<String, String> {
                val tmp = mutableMapOf<String, String>()
                if (token != "") {
                    tmp["x-access-token"] = token
                }
                tmp["Content-Type"] = "application/json"
                return tmp
            }
        }
        queue.add(stringRequest)
    }
}