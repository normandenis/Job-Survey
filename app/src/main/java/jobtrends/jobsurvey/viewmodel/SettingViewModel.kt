package jobtrends.jobsurvey.viewmodel

import android.app.Dialog
import android.content.Intent
import android.databinding.DataBindingUtil
import android.os.Bundle
import android.preference.PreferenceManager
import android.support.v4.app.Fragment
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import jobtrends.jobsurvey.R
import jobtrends.jobsurvey.databinding.NotificationPopupViewBinding
import jobtrends.jobsurvey.databinding.SettingViewBinding
import jobtrends.jobsurvey.model.HomeModel
import jobtrends.jobsurvey.model.UserModel
import jobtrends.jobsurvey.service.APIController
import jobtrends.jobsurvey.service.JsonController
import jobtrends.jobsurvey.service.serviceController

class SettingViewModel : Fragment
{
    private val _appBarBtn: Button?
    private val _homeModel: HomeModel?
    private val _jsonController: JsonController?
    private val _apiController: APIController?
    private val _userModel: UserModel?
    private val _tag: String?
    private var _dialog: Dialog?
    private var _view: View?
    private var _news: Boolean?
    private var _jobaymax: Boolean?

    constructor() : super()
    {
        _tag = "SettingViewModel"
        _news = false
        _jobaymax = false

        _apiController = serviceController?.getInstance()
        _appBarBtn = serviceController?.getInstance()
        _homeModel = serviceController?.getInstance()
        _jsonController = serviceController?.getInstance()
        _userModel = serviceController?.getInstance()

        _dialog = null
        _view = null
    }


    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View?
    {
        val binding: SettingViewBinding = DataBindingUtil
                .inflate(inflater, R.layout.setting_view, container, false)
        binding.vm = this
        binding.homeModel = _homeModel
        binding.userModel = _userModel

        _dialog = Dialog(context)

        _appBarBtn?.setBackgroundResource(R.drawable.ic_close_orange_32dp)
        _appBarBtn?.setOnClickListener { onNavStartSurvey() }

        _view = binding.root
        serviceController?.register(_view)
        return _view
    }

    private fun onNavStartSurvey()
    {
        _apiController?.get("me", ::jobaymaxMeReply, context)
    }

    private fun jobaymaxMeReply(code: Int?, body: String?)
    {
        val msg = "$code: $body"
        Log.d(_tag, msg)
        val startSurveyModel: HomeModel? = _jsonController?.deserialize(body)
        serviceController?.register(startSurveyModel, true)
        val fragment = ThemeViewModel(startSurveyModel)
        val transaction = fragmentManager?.beginTransaction()
        transaction?.replace(R.id.fragment_app_bar_nav_drawer_0, fragment)
        transaction?.addToBackStack(null)
        transaction?.commit()
    }

    fun onClick()
    {
        val fragment = ProfileViewModel()
        val transaction = fragmentManager?.beginTransaction()
        transaction?.replace(R.id.fragment_app_bar_nav_drawer_0, fragment)
        transaction?.addToBackStack(null)
        transaction?.commit()
    }

    fun onNavFAQ()
    {
        val fragment = FAQViewModel()
        val transaction = fragmentManager?.beginTransaction()
        transaction?.replace(R.id.fragment_app_bar_nav_drawer_0, fragment)
        transaction?.addToBackStack(null)
        transaction?.commit()
    }

    fun onNavNotif()
    {
        _jobaymax = false
        _news = false
        val bind: NotificationPopupViewBinding = DataBindingUtil
                .inflate(LayoutInflater.from(context), R.layout.notification_popup_view,
                         _view as ViewGroup, false)
        bind.vm = this
        bind.userModel = _userModel
        _dialog?.setContentView(bind.root)
        _dialog?.show()
    }

    fun onNotifYes()
    {
        val json = _jsonController?.serialize(_userModel)
        _apiController?.put("users", json, ::authUserReply, context)
    }


    private fun authUserReply(code: Int?, body: String?)
    {
        val msg = "$code: $body"
        Log.d(_tag, msg)
        val json = _jsonController?.serialize(_userModel)
        val preferences = PreferenceManager.getDefaultSharedPreferences(activity)
        val editor = preferences.edit()
        editor.putString(UserModel::class.java.simpleName, json)
        editor.apply()
        _dialog?.hide()
    }

    fun onNotifNo()
    {
        _dialog?.hide()
    }

    fun onNavSignIn()
    {
        _userModel?.reset()
        _apiController?.resetToken()
        val preferences = PreferenceManager.getDefaultSharedPreferences(activity)
        val editor = preferences.edit()
        editor.putString(UserModel::class.java.simpleName, "")
        editor.apply()
        val intent = Intent(activity, SignInViewModel::class.java)
        startActivity(intent)
    }
}
