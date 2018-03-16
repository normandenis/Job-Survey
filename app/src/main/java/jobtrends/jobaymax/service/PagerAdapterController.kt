package jobtrends.jobaymax.service

import android.support.v4.app.Fragment
import android.support.v4.app.FragmentManager
import android.support.v4.app.FragmentStatePagerAdapter

class PagerAdapterController(fm : FragmentManager?, private var _fragments : List<Fragment>?) :
    FragmentStatePagerAdapter(fm)
{
  override fun getItem(position : Int) : Fragment?
  {
    return _fragments?.get(position)
  }

  override fun getCount() : Int
  {
    return _fragments !!.size
  }
}