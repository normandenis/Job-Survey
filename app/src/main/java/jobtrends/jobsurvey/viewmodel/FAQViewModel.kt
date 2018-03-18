package jobtrends.jobsurvey.viewmodel

import android.databinding.DataBindingUtil
import android.os.Bundle
import android.support.v4.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import jobtrends.jobsurvey.R
import jobtrends.jobsurvey.databinding.FaqViewBinding
import jobtrends.jobsurvey.model.ListFAQModel
import jobtrends.jobsurvey.service.APIController
import jobtrends.jobsurvey.service.JsonController
import jobtrends.jobsurvey.service.serviceController

class FAQViewModel : Fragment()
{
  var viewmodel: ListViewFAQViewModel? = null
  var model: ListFAQModel? = null
  val apiController = serviceController!!.getInstance<APIController>()
  val jsonController = serviceController!!.getInstance<JsonController>()

  override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                            savedInstanceState: Bundle?): View?
  {
    val binding: FaqViewBinding = DataBindingUtil
      .inflate(inflater, R.layout.faq_view, container, false)
    val view = binding.root
    binding.vm = this
    val strQuestions = apiController.getFAQQuestion()
    model = jsonController.deserialize<ListFAQModel>(strQuestions)
    viewmodel = ListViewFAQViewModel(model!!.questions!!)

    return view
  }

  fun onTextChanged(s: CharSequence, start: Int, before: Int, count: Int)
  {
    // Call back the Adapter with current character to Filter
    viewmodel!!.filter.filter(s.toString())
  }
}
