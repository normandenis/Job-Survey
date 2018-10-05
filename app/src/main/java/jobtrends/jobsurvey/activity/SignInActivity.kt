package jobtrends.jobsurvey.activity

import android.content.Intent
import android.databinding.DataBindingUtil
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import jobtrends.jobsurvey.R
import jobtrends.jobsurvey.dagger.App
import jobtrends.jobsurvey.databinding.ActivitySignInBinding
import jobtrends.jobsurvey.model.ErrorModel
import jobtrends.jobsurvey.model.UserModel
import jobtrends.jobsurvey.service.ApiController
import jobtrends.jobsurvey.service.JsonController
import jobtrends.jobsurvey.service.ServiceController
import jobtrends.jobsurvey.adapter.displayError
import javax.inject.Inject

class SignInActivity : AppCompatActivity() {
    @Inject
    lateinit var jsonController: JsonController
    @Inject
    lateinit var apiController: ApiController
    @Inject
    lateinit var serviceController: ServiceController

    private val userModel: UserModel
    private val errorModel: ErrorModel
    private val tag: String

    init {
        App.component.inject(this)
        tag = "SignInActivity"
        errorModel = serviceController.getInstance()
        userModel = serviceController.getInstance()
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val binding: ActivitySignInBinding = DataBindingUtil.setContentView(this, R.layout.activity_sign_in)
        binding.signInActivity = this
        binding.userModel = userModel
        binding.errorModel = errorModel
        serviceController.register(resources)
    }

    fun onClickSignIn() {
        val body: MutableMap<String, String> = mutableMapOf()
        body["email"] = userModel.email?.get() ?: ""
        body["password"] = userModel.password?.get() ?: ""
        val bodySerialized = jsonController.serialize(body)
        apiController.post("login", bodySerialized, ::authLoginReply)
    }

    private fun authLoginReply(code: Int, body: String) {
        if (code != 200 && code != 201) {
            displayError(tag, body)
            errorModel.mainMsg?.set(body)
            return
        }
        errorModel.reset()
        serviceController.saveUser()
        val intent = Intent(this, HomeActivity::class.java)
        startActivity(intent)
    }

    fun onClickSignUp() {
        errorModel.reset()
        userModel.reset()
        val intent = Intent(this, SignUpActivity::class.java)
        startActivity(intent)
    }
}
