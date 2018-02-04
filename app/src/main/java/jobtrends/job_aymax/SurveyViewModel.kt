package jobtrends.job_aymax

import android.databinding.DataBindingUtil
import android.support.v4.app.Fragment
import android.support.v4.app.FragmentManager
import android.support.v4.app.FragmentPagerAdapter
import android.os.Bundle
import android.support.v4.app.FragmentStatePagerAdapter
import android.support.v4.view.ViewPager
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import jobtrends.job_aymax.databinding.SurveyViewBinding
import java.util.*

class SurveyViewModel : Fragment()
{
	private var _pagerAdapterController : PagerAdapterController? = null

	private fun newInstance(title : String) : Fragment
	{
		val fragment = SurveyContentViewModel()

		val bundle = Bundle()
		bundle.putString("title", title)
		fragment.arguments = bundle
		return fragment
	}

	override fun onViewCreated(view : View, savedInstanceState : Bundle?)
	{
		super.onViewCreated(view, savedInstanceState)

		val list : MutableList<Fragment> = mutableListOf()
		var i = 0
		list.add(newInstance("Sondage : $i"))
		i++
		list.add(newInstance("Sondage : $i"))
		i++
		list.add(newInstance("Sondage : $i"))

		_pagerAdapterController = PagerAdapterController(fragmentManager, list)
		val pager : ViewPager? = getView()?.findViewById(R.id.pager)
		pager?.adapter = _pagerAdapterController
	}

	override fun onCreateView(inflater : LayoutInflater, container : ViewGroup?, savedInstanceState : Bundle?) : View?
	{
		val binding : SurveyViewBinding = DataBindingUtil.inflate(inflater, R.layout.survey_view, container, false)
		val view = binding.root
		binding.vm = this
		return view
	}
}
