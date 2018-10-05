package jobtrends.jobsurvey.activity

import android.content.Intent
import android.databinding.DataBindingUtil
import android.databinding.ObservableField
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import jobtrends.jobsurvey.R
import jobtrends.jobsurvey.dagger.App
import jobtrends.jobsurvey.model.ErrorModel
import jobtrends.jobsurvey.model.UserModel
import jobtrends.jobsurvey.service.ApiController
import jobtrends.jobsurvey.service.JsonController
import jobtrends.jobsurvey.service.ServiceController
import jobtrends.jobsurvey.adapter.displayError
import jobtrends.jobsurvey.databinding.ActivitySignUpBinding
import javax.inject.Inject

class SignUpActivity : AppCompatActivity() {
    @Inject
    lateinit var jsonController: JsonController
    @Inject
    lateinit var apiController: ApiController
    @Inject
    lateinit var errorModel: ErrorModel
    @Inject
    lateinit var serviceController: ServiceController

    private val userModel: UserModel
    private val tag: String
    private var stop: Boolean

    init {
        App.component.inject(this)
        tag = "SignUpActivity"
        stop = false
        userModel = serviceController.getInstance()
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val binding: ActivitySignUpBinding = DataBindingUtil.setContentView(this, R.layout.activity_sign_up)
        binding.signUpActivity = this
        binding.errorModel = errorModel
        binding.userModel = userModel
    }

    private fun checkInput() {
        stop = false
        errorModel.reset()
        checkInput(userModel.firstName?.get(), "^[a-zA-Z ,.'-]+$", errorModel.firstnameMsg)
        checkInput(userModel.lastName?.get(), "^[a-zA-Z ,.'-]+$", errorModel.lastnameMsg)
        checkInput(userModel.email?.get(), "^[a-zA-Z0-9_.+-]+@[a-zA-Z0-9-]+\\.[a-zA-Z0-9-.]+$",
                errorModel.emailMsg)
        checkInput(userModel.password?.get(), "^[a-zA-Z0-9]+$", errorModel.passwordMsg)
        checkInput(userModel.job?.get(), "^[a-zA-Z ,.'-]+$", errorModel.jobMsg)
        checkInput(userModel.birthday?.get(), "[0-9]{2}-[0-9]{2}-[0-9]{4}", errorModel.birthdayMsg)
        checkPassword()
    }

    private fun isNull(str: String?, error: ObservableField<String?>?): Boolean {
        if (str == null && str == "") {
            stop = true
            error?.set("Ce champ ne peut pas être vide")
            return true
        }
        return false
    }

    private fun checkPassword() {
        if (!isNull(userModel.password?.get(), errorModel.passwordMsg)
                && !isNull(userModel.passwordProtection?.get(), errorModel.passwordBisMsg)) {
            if (userModel.password?.get()!!.length < 8) {
                stop = true
                errorModel.passwordMsg?.set("Votre mot de passe est inférieur à 8 charactères")
            }
            if (userModel.password?.get() != userModel.passwordProtection?.get()) {
                stop = true
                errorModel.passwordBisMsg?.set("Ce mot de passe est différent du premier")
            }
        }

    }

    private fun checkInput(input: String?, pattern: String?, error: ObservableField<String?>?) {
        if (input == null || input == "") {
            error?.set("Ce champ ne peut pas être vide")
            stop = true
            return
        }
        val regex = Regex(pattern!!)
        val result = input.matches(regex)
        if (!result) {
            error?.set("Ce champ est invalide")
            stop = true
        }
    }

    fun onClick() {
        checkInput()
        if (stop) {
            return
        }
        val body = jsonController.serialize(userModel)
        apiController.post("signup", body, ::authSignupReply)
    }

    fun onNavBack() {
        val intent = Intent(this, SignInActivity::class.java)
        startActivity(intent)
    }

    private fun authSignupReply(code: Int, body: String) {
        if (code != 200 && code != 201) {
            displayError(tag, body)
            errorModel.mainMsg?.set(body)
            return
        }
        singIn()
    }

    private fun singIn() {
        val bodySend: MutableMap<String, String> = mutableMapOf()
        bodySend["email"] = userModel.email?.get() ?: ""
        bodySend["password"] = userModel.password?.get() ?: ""
        val bodySerialized = jsonController.serialize(bodySend)
        apiController.post("login", bodySerialized, ::authLoginReply)
    }

    private fun authLoginReply(code: Int, body: String) {
        if (code != 200 && code != 201) {
            displayError(tag, body)
            errorModel.mainMsg?.set(body)
            return
        }
        if (!apiController.isToken()) {
            singIn()
        }
        val intent = Intent(this, HomeActivity::class.java)
        startActivity(intent)
    }
}
