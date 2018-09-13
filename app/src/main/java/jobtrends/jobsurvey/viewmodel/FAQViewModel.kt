package jobtrends.jobsurvey.viewmodel

import android.databinding.DataBindingUtil
import android.os.Bundle
import android.support.v4.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import jobtrends.jobsurvey.R
import jobtrends.jobsurvey.adapter.FAQAdapter
import jobtrends.jobsurvey.databinding.FaqViewBinding
import jobtrends.jobsurvey.model.ListFAQModel
import jobtrends.jobsurvey.service.APIController
import jobtrends.jobsurvey.service.JsonController
import jobtrends.jobsurvey.service.serviceController

class FAQViewModel : Fragment
{
    val faqAdapter: FAQAdapter?
    val model: ListFAQModel?
    private val _apiController: APIController?
    private val _jsonController: JsonController?

    constructor() : super()
    {
        _apiController = serviceController?.getInstance()
        _jsonController = serviceController?.getInstance()
        val strQuestions = _apiController?.getFAQQuestion()
        model = _jsonController?.deserialize(strQuestions)
        faqAdapter = FAQAdapter(model?.questions)
    }


    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View?
    {
        val binding: FaqViewBinding? = DataBindingUtil.inflate(inflater, R.layout.faq_view, container, false)
        binding?.vm = this
        return binding?.root
    }

    fun onTextChanged(s: CharSequence, start: Int?, before: Int?, count: Int?)
    {
        start as Any
        before as Any
        count as Any
        // Call back the Adapter with current character to Filter
        faqAdapter?.filter?.filter("$s")
    }
}
