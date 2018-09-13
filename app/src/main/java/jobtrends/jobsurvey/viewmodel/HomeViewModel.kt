package jobtrends.jobsurvey.viewmodel

import android.annotation.SuppressLint
import android.databinding.DataBindingUtil
import android.os.Bundle
import android.preference.PreferenceManager
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.Toolbar
import android.util.Log
import android.view.View
import android.widget.Button
import jobtrends.jobsurvey.R
import jobtrends.jobsurvey.databinding.HomeViewBinding
import jobtrends.jobsurvey.model.HomeModel
import jobtrends.jobsurvey.service.APIController
import jobtrends.jobsurvey.service.JsonController
import jobtrends.jobsurvey.service.serviceController

class HomeViewModel : AppCompatActivity
{
    private val _tag = "HomeViewModel"
    private val _jsonController: JsonController?
    private val _apiController: APIController?

    constructor()
    {
        _jsonController = serviceController?.getInstance()
        _apiController = serviceController?.getInstance()
    }

    @SuppressLint("CommitPrefEdits")
    override fun onCreate(savedInstanceState: Bundle?)
    {
        super.onCreate(savedInstanceState)
        val binding: HomeViewBinding = DataBindingUtil.setContentView(this, R.layout.home_view)
        binding.vm = this
        val toolbar = findViewById<View>(R.id.toolbar) as Toolbar
        setSupportActionBar(toolbar)

        serviceController?.register(findViewById<Button>(R.id.btn), true)
        serviceController?.register(binding.root, true)

        _apiController?.get("me", ::jobaymaxMeRepley, this)

        initNotification()
    }

    private fun initNotification()
    {
        val preferences = PreferenceManager.getDefaultSharedPreferences(this)
        val token = preferences.getString("DeviceId", null)
        val msg = token ?: ""
        Log.d(_tag, msg)
        if (token != null && token != "")
        {
            _apiController?.post("jobaymax/me/device/$token?type=ANDROID", null, ::jobaymaxMeDeviceTokenReply, this)
        }
    }

    private fun jobaymaxMeDeviceTokenReply(code: Int?, body: String?)
    {
        val msg = "$code: $body"
        Log.d(_tag, msg)
    }

    private fun jobaymaxMeRepley(code: Int?, body: String?)
    {
        val msg = "$code: $body"
        Log.i(_tag, msg)
        if (code != 200 && code != 201)
        {
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