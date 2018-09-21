package jobtrends.jobsurvey.viewmodel

import android.databinding.DataBindingUtil
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.Toolbar
import android.util.Log
import android.view.View
import android.widget.Button
import jobtrends.jobsurvey.R
import jobtrends.jobsurvey.databinding.HomeViewBinding
import jobtrends.jobsurvey.model.HomeModel
import jobtrends.jobsurvey.model.UserModel
import jobtrends.jobsurvey.service.APIController
import jobtrends.jobsurvey.service.JsonController
import jobtrends.jobsurvey.service.serviceController

class HomeViewModel : AppCompatActivity {
    private val _tag = "HomeViewModel"
    private val _jsonController: JsonController?
    private val _apiController: APIController?
    private val _userModel: UserModel?

    constructor() {
        _jsonController = serviceController?.getInstance()
        _apiController = serviceController?.getInstance()
        _userModel = serviceController?.getInstance()
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val binding: HomeViewBinding = DataBindingUtil.setContentView(this, R.layout.home_view)
        binding.vm = this
        val toolbar = findViewById<View>(R.id.toolbar) as Toolbar
        setSupportActionBar(toolbar)

        serviceController?.register(findViewById<Button>(R.id.btn), true)
        serviceController?.register(binding.root, true)

        _apiController?.get("me", ::jobaymaxMeReply, this)

//        initNotification()
//        _apiController?.get("users/me", ::saveUser, this)
        serviceController?.saveUser(this)
    }

//    private fun saveUser(code: Int?, body: String?) {
//        if (code != 200 || code != 201) {
//            return
//        }
//        val json: String? = _jsonController?.serialize(_userModel)
//        val preferences: SharedPreferences? = PreferenceManager.getDefaultSharedPreferences(this)
//        val editor: SharedPreferences.Editor? = preferences?.edit()
//        editor?.putString("email", _userModel?.email?.get())
//        editor?.putString(_userModel?.email?.get(), json)
//        editor?.apply()
//    }

//    private fun initNotification() {
//        val preferences: SharedPreferences? = PreferenceManager.getDefaultSharedPreferences(this)
//        val token = preferences?.getString("DeviceId", null)
//        val msg = token ?: ""
//        Log.d(_tag, msg)
//        if (token != null && token != "") {
//            _apiController?.post("users/device/$token?os=ANDROID", null, ::jobaymaxMeDeviceTokenReply, this)
//        }
//    }

//    private fun jobaymaxMeDeviceTokenReply(code: Int?, body: String?) {
//        val msg = "$code: $body"
//        Log.d(_tag, msg)
//    }

    private fun jobaymaxMeReply(code: Int?, body: String?) {
        if (code == null || body == null) {
            return
        }
        val msg = "$code: $body"
        Log.i(_tag, msg)
        if (code != 200 && code != 201) {
            return
        }
        val homeModel: HomeModel? = _jsonController?.deserialize(body)
        serviceController?.register(homeModel, true)
        val fragment = ThemeViewModel(homeModel)
        val transaction = supportFragmentManager?.beginTransaction()
        transaction?.replace(R.id.fragment_app_bar_nav_drawer_0, fragment)
        transaction?.addToBackStack(null)
        transaction?.commit()
    }
}