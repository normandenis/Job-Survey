package jobtrends.jobaymax.viewmodel

import android.databinding.DataBindingUtil
import android.os.Bundle
import android.support.v4.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import jobtrends.jobaymax.R
import jobtrends.jobaymax.databinding.FaqViewBinding
import jobtrends.jobaymax.model.ListFAQModel
import jobtrends.jobaymax.service.ServiceController

class FAQViewModel : Fragment()
{
  var viewmodel : ListViewFAQViewModel? = null
  var model : ListFAQModel? = null
  override fun onCreateView(inflater : LayoutInflater, container : ViewGroup?,
                            savedInstanceState : Bundle?) : View?
  {
    val binding : FaqViewBinding = DataBindingUtil
        .inflate(inflater, R.layout.faq_view, container, false)
    val view = binding.root
    binding.vm = this
    val strQuestions = ServiceController.apiController.getFAQQuestion()
    model = ServiceController.jsonController.deserialize<ListFAQModel>(strQuestions)
    viewmodel = ListViewFAQViewModel(model?.questions !!)

    return view
  }

  fun onTextChanged(s : CharSequence, start : Int, before : Int, count : Int)
  {
    // Call back the Adapter with current character to Filter
    viewmodel?.filter?.filter(s.toString())
  }
}
