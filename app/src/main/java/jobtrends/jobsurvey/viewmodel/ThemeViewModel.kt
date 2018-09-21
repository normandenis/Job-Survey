package jobtrends.jobsurvey.viewmodel

import android.annotation.SuppressLint
import android.databinding.DataBindingUtil
import android.os.Bundle
import android.support.v4.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import jobtrends.jobsurvey.R
import jobtrends.jobsurvey.adapter.ThemeAdapter
import jobtrends.jobsurvey.databinding.ThemeViewBinding
import jobtrends.jobsurvey.model.HomeModel
import jobtrends.jobsurvey.service.serviceController

@SuppressLint("ValidFragment")
class ThemeViewModel : Fragment {
    private var _themeAdapter: ThemeAdapter?
    private var _homeModel: HomeModel?
    private var _view: View?
    private val _appBarBtn: Button?
    private val _tag: String?

    @SuppressLint("ValidFragment")
    constructor(homeModel: HomeModel?) : super() {
        _tag = "ThemeViewModel"
        _themeAdapter = null
        _view = null
        _homeModel = homeModel
        _appBarBtn = serviceController?.getInstance()
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {

        val binding: ThemeViewBinding? = DataBindingUtil.inflate(inflater, R.layout.theme_view, container, false)
        _view = binding?.root

        _themeAdapter = ThemeAdapter(_homeModel?.themes, fragmentManager)

        _appBarBtn?.setBackgroundResource(R.drawable.ic_person_orange_32dp)
        _appBarBtn?.setOnClickListener { onNavSetting() }

        binding?.vm = this
        binding?.homeModel = _homeModel
        binding?.themeAdapter = _themeAdapter

        return _view
    }

    private fun onNavSetting() {
        val fragment = SettingViewModel()
        val transaction = fragmentManager?.beginTransaction()
        transaction?.replace(R.id.fragment_app_bar_nav_drawer_0, fragment)
        transaction?.addToBackStack(null)
        transaction?.commit()
    }
}
