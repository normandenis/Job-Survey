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
import jobtrends.jobsurvey.adapter.ThemeAdapter
import jobtrends.jobsurvey.dagger.App
import jobtrends.jobsurvey.databinding.FragmentThemeBinding
import jobtrends.jobsurvey.model.HomeModel
import jobtrends.jobsurvey.service.ServiceController
import javax.inject.Inject

class ThemeFragment : Fragment() {
    @Inject
    lateinit var serviceController: ServiceController

    private lateinit var themeAdapter: ThemeAdapter
    private var homeModel: HomeModel
    private val appBarBtn: Button

    init {
        App.component.inject(this)
        homeModel = serviceController.getInstance()
        appBarBtn = serviceController.getInstance()
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val binding: FragmentThemeBinding =
            DataBindingUtil.inflate(inflater, R.layout.fragment_theme, container, false)
        themeAdapter = ThemeAdapter(homeModel.themes, fragmentManager)
        appBarBtn.setBackgroundResource(R.drawable.person_accent)
        appBarBtn.setOnClickListener { onNavSetting() }
        binding.themeFragment = this
        binding.homeModel = homeModel
        binding.themeAdapter = themeAdapter
        return binding.root
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

    private fun onNavSetting() {
        val fragment = SettingFragment()
        navTo(fragment)
    }
}
