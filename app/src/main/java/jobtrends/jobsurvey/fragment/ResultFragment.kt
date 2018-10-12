package jobtrends.jobsurvey.fragment

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
import jobtrends.jobsurvey.model.HomeModel
import jobtrends.jobsurvey.model.ResultModel
import jobtrends.jobsurvey.service.ApiController
import jobtrends.jobsurvey.service.JsonController
import jobtrends.jobsurvey.service.ServiceController
import jobtrends.jobsurvey.adapter.displayError
import jobtrends.jobsurvey.dagger.App
import jobtrends.jobsurvey.databinding.FragmentResultBinding
import javax.inject.Inject

class ResultFragment : Fragment() {
    @Inject
    lateinit var jsonController: JsonController
    @Inject
    lateinit var apiController: ApiController
    @Inject
    lateinit var serviceController: ServiceController

    private val appBarBtn: Button
    private val resultModel: ResultModel

    init {
        App.component.inject(this)
        appBarBtn = serviceController.getInstance()
        resultModel = serviceController.getInstance()
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View {
        val binding: FragmentResultBinding = DataBindingUtil.inflate(inflater, R.layout.fragment_result, container, false)
        binding.resultFragment = this
        appBarBtn.setBackgroundResource(R.drawable.close_accent_36)
        appBarBtn.setOnClickListener { onValidateSurvey() }
        return binding.root
    }

    fun onValidateSurvey() {
        val endSurveySerialiazed = jsonController.serialize(resultModel)
        apiController.post("results/${resultModel.id?.get()}", endSurveySerialiazed, ::jobaymaxResultReply)
    }

    private fun jobaymaxResultReply(code: Int, body: String) {
        if (code != 200 && code != 201) {
            displayError(tag!!, body)
        }
        apiController.get("me", ::jobaymaxMeReply)
    }

    private fun jobaymaxMeReply(code: Int, body: String) {
        if (code != 200 && code != 201) {
            displayError(tag!!, body)
            return
        }
        val startSurveyModel: HomeModel = jsonController.deserialize(body)
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
}
