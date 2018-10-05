package jobtrends.jobsurvey.activity

import android.databinding.DataBindingUtil
import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v4.app.FragmentTransaction
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.Toolbar
import android.util.Log
import android.view.View
import android.widget.Button
import jobtrends.jobsurvey.R
import jobtrends.jobsurvey.dagger.App
import jobtrends.jobsurvey.databinding.ActivityHomeBinding
import jobtrends.jobsurvey.fragment.ThemeFragment
import jobtrends.jobsurvey.model.HomeModel
import jobtrends.jobsurvey.model.UserModel
import jobtrends.jobsurvey.service.ApiController
import jobtrends.jobsurvey.service.JsonController
import jobtrends.jobsurvey.service.ServiceController
import jobtrends.jobsurvey.adapter.displayError
import javax.inject.Inject

class HomeActivity : AppCompatActivity() {

    @Inject
    lateinit var jsonController: JsonController
    @Inject
    lateinit var apiController: ApiController
    @Inject
    lateinit var serviceController: ServiceController

    private val userModel: UserModel
    private val tag: String

    init {
        App.component.inject(this)
        userModel = serviceController.getInstance()
        tag = "HomeActivity"
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val binding: ActivityHomeBinding = DataBindingUtil.setContentView(this, R.layout.activity_home)
        binding.homeActivity = this
        val toolbar = findViewById<View>(R.id.toolbar) as Toolbar
        setSupportActionBar(toolbar)

        serviceController.register(findViewById<Button>(R.id.btn), true)
        serviceController.register(binding.root, true)

        apiController.get("me", ::jobaymaxMeReply)

        serviceController.saveUser()
    }

    private fun navTo(fragment: Fragment) {
        try {
            val transaction: FragmentTransaction = supportFragmentManager?.beginTransaction()!!
            transaction.replace(R.id.fragment_app_bar_nav_drawer_0, fragment)
            transaction.commit()
        } catch (exception: Exception) {
            Log.e(tag, exception.message)
        }
    }

    private fun jobaymaxMeReply(code: Int, body: String) {
        if (code != 200 && code != 201) {
            displayError(tag, body)
            return
        }
        val homeModel: HomeModel? = jsonController.deserialize(body)
        serviceController.register(homeModel, true)
        val fragment = ThemeFragment()
        navTo(fragment)
    }
}