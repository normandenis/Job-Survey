package jobtrends.jobsurvey.viewmodel

import android.databinding.DataBindingUtil
import android.os.Bundle
import android.support.v4.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import jobtrends.jobsurvey.R
import jobtrends.jobsurvey.databinding.ProfileViewBinding

class ProfileViewModel : Fragment()
{
  override fun onCreateView(inflater : LayoutInflater, container : ViewGroup?,
                            savedInstanceState : Bundle?) : View?
  {
    val binding : ProfileViewBinding = DataBindingUtil
        .inflate(inflater, R.layout.profile_view, container, false)
    val view = binding.root
    binding.vm = this
    return view
  }

  fun onClick()
  {
    val fragment = SettingViewModel()
    val transaction = fragmentManager?.beginTransaction()
    transaction?.replace(R.id.fragment_app_bar_nav_drawer_0, fragment)
    transaction?.addToBackStack(null)
    transaction?.commit()
  }
}
