package jobtrends.job_aymax.viewmodel

import android.databinding.DataBindingUtil
import android.os.Bundle
import android.support.v4.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import jobtrends.job_aymax.R
import jobtrends.job_aymax.databinding.FaqViewBinding
import jobtrends.job_aymax.model.ListFAQModel
import jobtrends.job_aymax.service.ServiceController

class FAQViewModel : Fragment()
{
	var viewmodel : ListViewFAQViewModel? = null
	var model : ListFAQModel? = null

	override fun onCreateView(inflater : LayoutInflater, container : ViewGroup?, savedInstanceState : Bundle?) : View?
	{
		val binding : FaqViewBinding = DataBindingUtil.inflate(inflater, R.layout.faq_view, container, false)
		val view = binding.root
		binding.vm = this

		val strQuestions = ServiceController.apiController.getFAQQuestion()
		model = ServiceController.jsonController.deserialize<ListFAQModel>(strQuestions)
		viewmodel = ListViewFAQViewModel(model?.questions !!)

		return view
	}
}
