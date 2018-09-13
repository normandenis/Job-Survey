package jobtrends.jobsurvey.viewmodel

import android.app.DatePickerDialog
import android.app.DatePickerDialog.OnDateSetListener
import android.content.Intent
import android.databinding.DataBindingUtil
import android.databinding.ObservableField
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.util.Log
import jobtrends.jobsurvey.R
import jobtrends.jobsurvey.databinding.SignUpViewBinding
import jobtrends.jobsurvey.model.ErrorModel
import jobtrends.jobsurvey.model.UserModel
import jobtrends.jobsurvey.service.APIController
import jobtrends.jobsurvey.service.JsonController
import jobtrends.jobsurvey.service.serviceController
import java.text.SimpleDateFormat
import java.util.*

class SignUpViewModel : AppCompatActivity
{
    private val _userModel: UserModel?
    private val _jsonController: JsonController?
    private val _apiController: APIController?
    private val _errorModel: ErrorModel?
    private val _tag: String?
    private val _calendar: Calendar?
    private val _date: OnDateSetListener?
    private var _stop: Boolean?

    constructor() : super()
    {
        _tag = "SignUpViewModel"
        _stop = false

        _userModel = serviceController?.getInstance()
        _errorModel = serviceController?.getInstance()
        _jsonController = serviceController?.getInstance()
        _apiController = serviceController?.getInstance()

        _calendar = Calendar.getInstance()
        _date = DatePickerDialog.OnDateSetListener { _, year, monthOfYear, dayOfMonth ->
            _calendar.set(Calendar.YEAR, year)
            _calendar.set(Calendar.MONTH, monthOfYear)
            _calendar.set(Calendar.DAY_OF_MONTH, dayOfMonth)
            updateLabel()
        }
    }

    override fun onCreate(savedInstanceState: Bundle?)
    {
        super.onCreate(savedInstanceState)
        val binding: SignUpViewBinding = DataBindingUtil.setContentView(this, R.layout.sign_up_view)
        binding.vm = this
        binding.errorModel = _errorModel
        binding.userModel = _userModel
    }

    private fun checkInput()
    {
        _stop = false
        _errorModel?.reset()
        checkInput(_userModel?.firstName?.get(), "^[a-zA-Z ,.'-]+$", _errorModel?.firstnameMsg)
        checkInput(_userModel?.lastName?.get(), "^[a-zA-Z ,.'-]+$", _errorModel?.lastnameMsg)
        checkInput(_userModel?.email?.get(), "^[a-zA-Z0-9_.+-]+@[a-zA-Z0-9-]+\\.[a-zA-Z0-9-.]+$",
                   _errorModel?.emailMsg)
        checkInput(_userModel?.password?.get(), "^[a-zA-Z0-9]+$", _errorModel?.passwordMsg)
        checkInput(_userModel?.job?.get(), "^[a-zA-Z ,.'-]+$", _errorModel?.jobMsg)
        checkInput(_userModel?.birthday?.get(), "[0-9]{2}-[0-9]{2}-[0-9]{4}", _errorModel?.birthdayMsg)
        checkPassword()
    }

    private fun isNull(str: String?, error: ObservableField<String?>?): Boolean?
    {
        if (str == null || str == "")
        {
            _stop = true
            error?.set("Ce champ ne peut pas être vide")
            return true
        }
        return false
    }

    private fun checkPassword()
    {
        if (isNull(_userModel?.password?.get(), _errorModel?.passwordMsg) == false
            && isNull(_userModel?.passwordProtection?.get(), _errorModel?.passwordBisMsg) == false)
        {
            if (_userModel?.password?.get()!!.length < 8)
            {
                _stop = true
                _errorModel?.passwordMsg?.set("Votre mot de passe est inférieur à 8 charactères")
            }
            if (_userModel.password?.get() != _userModel.passwordProtection?.get())
            {
                _stop = true
                _errorModel?.passwordBisMsg?.set("Ce mot de passe est différent du premier")
            }
        }

    }

    private fun checkInput(input: String?, pattern: String?, error: ObservableField<String?>?)
    {
        if (input == null || input == "")
        {
            error?.set("Ce champ ne peut pas être vide")
            _stop = true
            return
        }
        val regex = Regex(pattern!!)
        val result = input.matches(regex)
        if (!result)
        {
            error?.set("Ce champ est invalide")
            _stop = true
        }
    }

    fun onDatePickerClick()
    {
        DatePickerDialog(this, _date, _calendar?.get(Calendar.YEAR)!!, _calendar.get(Calendar.MONTH),
                         _calendar.get(Calendar.DAY_OF_MONTH)).show()
    }

    private fun updateLabel()
    {
        val df = SimpleDateFormat("dd/mm/yyyy", Locale.FRANCE)
        val format = df.format(_calendar?.time)
        _userModel?.birthday?.set(format)
    }

    fun onClick()
    {
        checkInput()
        if (_stop == true)
        {
            return
        }
        val body = _jsonController?.serialize(_userModel)
        _apiController?.post("signup", body, ::authSignupReply, this)
    }

    fun onNavBack()
    {
        val intent = Intent(this, SignInViewModel::class.java)
        startActivity(intent)
    }

    private fun authSignupReply(code: Int?, bodyRecv: String?)
    {
        val msg = "$code: $bodyRecv"
        Log.d(_tag, msg)
        if (code != 200 && code != 201)
        {
            _errorModel?.mainMsg?.set(bodyRecv)
            return
        }
        singIn()
    }

    private fun singIn()
    {
        val bodySend: MutableMap<String?, String?>? = mutableMapOf()
        bodySend?.set("username", _userModel?.email?.get())
        bodySend?.set("password", _userModel?.password?.get())
        val bodySerialized = _jsonController?.serialize(bodySend)
        _apiController?.post("auth/login", bodySerialized, ::authLoginReply, this)
    }

    private fun authLoginReply(code: Int?, body: String?)
    {
        val msg = "$code: $body"
        Log.d(_tag, msg)
        if (code != 200 && code != 201)
        {
            _errorModel?.mainMsg?.set(body)
            return
        }
        if (_apiController?.isToken() == false)
        {
            singIn()
        }
        val intent = Intent(this, HomeViewModel::class.java)
        startActivity(intent)
    }
}
