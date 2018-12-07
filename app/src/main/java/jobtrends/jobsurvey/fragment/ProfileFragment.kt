package jobtrends.jobsurvey.fragment

import android.databinding.DataBindingUtil
import android.databinding.ObservableField
import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v4.app.FragmentTransaction
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import jobtrends.jobsurvey.R
import jobtrends.jobsurvey.adapter.displayError
import jobtrends.jobsurvey.dagger.App
import jobtrends.jobsurvey.databinding.FragmentProfileBinding
import jobtrends.jobsurvey.model.ErrorModel
import jobtrends.jobsurvey.model.UserModel
import jobtrends.jobsurvey.service.ApiController
import jobtrends.jobsurvey.service.JsonController
import jobtrends.jobsurvey.service.ServiceController
import javax.inject.Inject

class ProfileFragment : Fragment() {
    @Inject
    lateinit var errorModel: ErrorModel
    @Inject
    lateinit var jsonController: JsonController
    @Inject
    lateinit var apiController: ApiController
    @Inject
    lateinit var serviceController: ServiceController

    private val userModel: UserModel
    private val tmpUserModel: UserModel
    private val appBarBtn: Button

    private val _oldPassword: String
    private var stop: Boolean
    val oldPassword: ObservableField<String?>?

    init {
        App.component.inject(this)
        stop = false
        userModel = serviceController.getInstance()
        appBarBtn = serviceController.getInstance()

        oldPassword = ObservableField()
        tmpUserModel = userModel.copy()
        tmpUserModel.password?.set("")
        tmpUserModel.passwordProtection?.set("")
        _oldPassword = userModel.password?.get() ?: ""

        appBarBtn.setBackgroundResource(R.drawable.arrow_back_accent_512)
        appBarBtn.setOnClickListener { onNavBack() }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        val binding: FragmentProfileBinding = DataBindingUtil
            .inflate(inflater, R.layout.fragment_profile, container, false)
        binding.profileFragment = this
        binding.userModel = tmpUserModel
        binding.errorModel = errorModel
        return binding.root
    }

    private fun onNavBack() {
        fragmentManager?.popBackStack()
    }

    private fun checkInput() {
        stop = false
        errorModel.reset()
        checkInput(tmpUserModel.firstName?.get(), "^[a-zA-Z ,.'-]+$", errorModel.firstnameMsg)
        checkInput(tmpUserModel.lastName?.get(), "^[a-zA-Z ,.'-]+$", errorModel.lastnameMsg)
        checkInput(
            tmpUserModel.email?.get(), "^[a-zA-Z0-9_.+-]+@[a-zA-Z0-9-]+\\.[a-zA-Z0-9-.]+$",
            errorModel.emailMsg
        )
        checkInput(tmpUserModel.password?.get(), "^[a-zA-Z0-9]+$", errorModel.passwordMsg)
        checkInput(tmpUserModel.job?.get(), "^[a-zA-Z ,.'-]+$", errorModel.jobMsg)
        checkInput(
            tmpUserModel.birthday?.get(),
            "[0-9]{2}-[0-9]{2}-[0-9]{4}",
            errorModel.birthdayMsg
        )
        checkPassword()
    }

    private fun isNull(str: String?, error: ObservableField<String?>?): Boolean {
        if (str == null || str == "") {
            stop = true
            error?.set("Ce champ ne peut pas être vide")
            return true
        }
        str.length
        return false
    }

    private fun checkPassword() {
        if (!isNull(oldPassword?.get(), errorModel.oldPassword)) {
            if (oldPassword?.get() != _oldPassword) {
                stop = true
                errorModel.oldPassword?.set("Ce mot de passe ne correspond pas à l'ancien mot de passe")
            }
        }
        if (!isNull(tmpUserModel.password?.get(), errorModel.passwordMsg)
            && !isNull(tmpUserModel.passwordProtection?.get(), errorModel.passwordBisMsg)
        ) {
            if (tmpUserModel.password?.get()?.length ?: 0 < 8) {
                stop = true
                errorModel.passwordMsg?.set("Votre mot de passe est inférieur à 8 charactères")
            }
            if (tmpUserModel.password?.get() != tmpUserModel.passwordProtection?.get()) {
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
        tmpUserModel.password?.set(_oldPassword)
        val json = jsonController.serialize(tmpUserModel)
        apiController.patch("users", json, ::authUserReply)
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

    private fun authUserReply(code: Int, body: String) {
        if (code != 200 && code != 201) {
            displayError(tag!!, body)
            errorModel.mainMsg?.set(body)
            return
        }
        serviceController.saveUser()
        val fragment = SettingFragment()
        navTo(fragment)
    }
}
