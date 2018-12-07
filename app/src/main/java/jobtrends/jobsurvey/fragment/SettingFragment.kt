package jobtrends.jobsurvey.fragment

import android.content.Intent
import android.databinding.DataBindingUtil
import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v4.app.FragmentTransaction
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import jobtrends.jobsurvey.R
import jobtrends.jobsurvey.activity.SignInActivity
import jobtrends.jobsurvey.adapter.displayError
import jobtrends.jobsurvey.dagger.App
import jobtrends.jobsurvey.databinding.FragmentSettingBinding
import jobtrends.jobsurvey.model.HomeModel
import jobtrends.jobsurvey.model.UserModel
import jobtrends.jobsurvey.service.ApiController
import jobtrends.jobsurvey.service.JsonController
import jobtrends.jobsurvey.service.ServiceController
import javax.inject.Inject

class SettingFragment : Fragment() {
    @Inject
    lateinit var jsonController: JsonController
    @Inject
    lateinit var apiController: ApiController
    @Inject
    lateinit var serviceController: ServiceController

    private val appBarBtn: Button
    private val homeModel: HomeModel
    private val userModel: UserModel

    init {
        App.component.inject(this)
        appBarBtn = serviceController.getInstance()
        homeModel = serviceController.getInstance()
        userModel = serviceController.getInstance()
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val binding: FragmentSettingBinding = DataBindingUtil
            .inflate(inflater, R.layout.fragment_setting, container, false)
        binding.settingFragment = this
        binding.homeModel = homeModel
        binding.userModel = userModel
        appBarBtn.setBackgroundResource(R.drawable.close_accent_36)
        appBarBtn.setOnClickListener { onNavStartSurvey() }
        return binding.root
    }

    private fun onNavStartSurvey() {
        apiController.get("me", ::jobaymaxMeReply)
    }

    private fun jobaymaxMeReply(code: Int, body: String) {
        if (code != 200 && code != 201) {
            displayError(tag!!, body)
            return
        }
        val startSurveyModel: HomeModel? = jsonController.deserialize(body)
        serviceController.register(startSurveyModel, true)
        val fragment = ThemeFragment()
        navTo(fragment)
    }

    private fun navTo(fragment: Fragment) {
        try {
            val transaction: FragmentTransaction = fragmentManager?.beginTransaction()!!
            transaction.replace(R.id.my_framelayout_0, fragment)
            transaction.commit()
        } catch (exception: Exception) {
            Log.e(tag, exception.message)
        }
    }

    fun onNavToProfile() {
        val fragment = ProfileFragment()
        navTo(fragment)
    }

    fun onNavSignIn() {
        serviceController.deleteUser()
        val intent = Intent(activity, SignInActivity::class.java)
        startActivity(intent)
    }
}
