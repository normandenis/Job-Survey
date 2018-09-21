package jobtrends.jobsurvey.viewmodel

import android.content.Intent
import android.content.SharedPreferences
import android.databinding.DataBindingUtil
import android.os.Bundle
import android.preference.PreferenceManager
import android.support.v7.app.AppCompatActivity
import android.util.Log
import jobtrends.jobsurvey.R
import jobtrends.jobsurvey.databinding.SignInViewBinding
import jobtrends.jobsurvey.model.ErrorModel
import jobtrends.jobsurvey.model.UserModel
import jobtrends.jobsurvey.service.APIController
import jobtrends.jobsurvey.service.JsonController
import jobtrends.jobsurvey.service.serviceController

class SignInViewModel : AppCompatActivity {
    private val _jsonController: JsonController?
    private val _apiController: APIController?
    private val _userModel: UserModel?
    private val _errorModel: ErrorModel?
    private val _tag: String?

    constructor() : super() {
        _tag = "SignInViewModel"
        _apiController = serviceController?.getInstance()
        _jsonController = serviceController?.getInstance()
        _errorModel = serviceController?.getInstance()
        _userModel = serviceController?.getInstance()
    }


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val binding: SignInViewBinding = DataBindingUtil.setContentView(this, R.layout.sign_in_view)
        binding.vm = this
        binding.userModel = _userModel
        binding.errorModel = _errorModel
        serviceController?.register(resources)
    }

    fun onClickSignIn() {
        val body: MutableMap<String?, String?>? = mutableMapOf()
        body?.set("email", _userModel?.email?.get())
        body?.set("password", _userModel?.password?.get())
        val bodySerialized = _jsonController?.serialize(body)
        _apiController?.post("login", bodySerialized, ::authLoginReply, this)
    }

    private fun authLoginReply(code: Int?, body: String?) {
        if (code == null || body == null) {
            return
        }
        val msg = "$code: $body"
        Log.d(_tag, msg)
        if (code != 200 && code != 201) {
            _errorModel?.mainMsg?.set(body)
            return
        }
        _errorModel?.reset()

        serviceController?.saveUser(this)
//        val preferences: SharedPreferences? = PreferenceManager.getDefaultSharedPreferences(this)
//        val json = preferences?.getString(_userModel?.email?.get(), null)
//        val user: UserModel? = _jsonController?.deserialize(json)
//        serviceController?.register(user, true)
        val intent = Intent(this, HomeViewModel::class.java)
        startActivity(intent)
    }

    fun onClickSignUp() {
        _errorModel?.reset()
        _userModel?.reset()
        val intent = Intent(this, SignUpViewModel::class.java)
        startActivity(intent)
    }
}
