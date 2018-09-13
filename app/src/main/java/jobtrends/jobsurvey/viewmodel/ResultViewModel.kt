package jobtrends.jobsurvey.viewmodel

import android.databinding.DataBindingUtil
import android.os.Bundle
import android.support.v4.app.Fragment
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import jobtrends.jobsurvey.R
import jobtrends.jobsurvey.databinding.ResultViewBinding
import jobtrends.jobsurvey.model.HomeModel
import jobtrends.jobsurvey.model.ResultModel
import jobtrends.jobsurvey.service.APIController
import jobtrends.jobsurvey.service.JsonController
import jobtrends.jobsurvey.service.serviceController

class ResultViewModel : Fragment
{
    private val _appBarBtn: Button?
    private val _jsonController: JsonController?
    private val _apiController: APIController?
    private val _resultModel: ResultModel?
    private val _tag: String?

    constructor()
    {
        _tag = "ResultViewModel"
        _appBarBtn = serviceController?.getInstance()
        _jsonController = serviceController?.getInstance()
        _apiController = serviceController?.getInstance()
        _resultModel = serviceController?.getInstance()
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View
    {
        val binding: ResultViewBinding? = DataBindingUtil.inflate(inflater, R.layout.result_view, container, false)
        binding?.vm = this
        _appBarBtn?.setBackgroundResource(R.drawable.ic_close_orange_32dp)
        _appBarBtn?.setOnClickListener { onValidateSurvey() }
        return binding?.root!!
    }

    fun onValidateSurvey()
    {
        val endSurveySerialiazed = _jsonController?.serialize(_resultModel)
        _apiController?.post("results/${_resultModel?.id?.get()}", endSurveySerialiazed, ::jobaymaxResultReply, context)
    }

    private fun jobaymaxResultReply(code: Int?, body: String?)
    {
        val msg = "$code: $body"
        Log.i(_tag, msg)
        _apiController?.get("me", ::jobaymaxMeReply, context)
    }

    private fun jobaymaxMeReply(code: Int?, body: String?)
    {
        val msg = "$code: $body"
        Log.i(_tag, msg)
        val startSurveyModel = _jsonController?.deserialize<HomeModel>(body)
        serviceController?.register(startSurveyModel, true)
        val fragment = ThemeViewModel(startSurveyModel)
        val transaction = fragmentManager?.beginTransaction()
        transaction?.replace(R.id.fragment_app_bar_nav_drawer_0, fragment)
        transaction?.addToBackStack(null)
        transaction?.commit()
    }
}
