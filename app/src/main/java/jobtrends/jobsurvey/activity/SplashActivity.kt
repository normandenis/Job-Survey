package jobtrends.jobsurvey.activity

import android.content.Intent
import android.content.SharedPreferences
import android.os.Bundle
import android.os.Handler
import android.preference.PreferenceManager
import android.support.v7.app.AppCompatActivity
import jobtrends.jobsurvey.R
import jobtrends.jobsurvey.adapter.displayError
import jobtrends.jobsurvey.dagger.App
import jobtrends.jobsurvey.model.UserModel
import jobtrends.jobsurvey.service.ApiController
import jobtrends.jobsurvey.service.JsonController
import jobtrends.jobsurvey.service.ServiceController
import javax.inject.Inject

class SplashActivity : AppCompatActivity() {
    @Inject
    lateinit var jsonController: JsonController
    @Inject
    lateinit var apiController: ApiController
    @Inject
    lateinit var serviceController: ServiceController

    private val delayMillis: Long
    private val tag: String

    init {
        App.component.inject(this)
        tag = "SplashActivity"
        delayMillis = 1000
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash)
        Handler().postDelayed(::initUser, delayMillis)
    }

    private fun initUser() {
        val preferences: SharedPreferences = PreferenceManager.getDefaultSharedPreferences(this)
        val json: String = preferences.getString(UserModel::class.java.simpleName, null) ?: ""
        if (json != "") {
            signInUser(json)
        } else {
            navToSignInView()
        }
    }

    private fun signInUser(json: String) {
        val user: UserModel = jsonController.deserialize(json)
        if (user.password?.get() == null || user.password?.get() == "") {
            navToSignInView()
        }
        serviceController.register(user, true)
        val tmp: MutableMap<String, String> = mutableMapOf()
        tmp["email"] = user.email?.get() ?: ""
        tmp["password"] = user.password?.get() ?: ""
        val tmpSerialized = jsonController.serialize(tmp)
        apiController.post("login", tmpSerialized, ::authLoginReply)
    }

    private fun authLoginReply(code: Int, body: String) {
        if (code != 200 && code != 201) {
            displayError(tag, body)
            navToSignInView()
            return
        }
        navToHomeView()
    }

    private fun navToSignInView() {
        val intent = Intent(this, SignInActivity::class.java)
        startActivity(intent)
    }

    private fun navToHomeView() {
        val intent = Intent(this, HomeActivity::class.java)
        startActivity(intent)
    }
}
