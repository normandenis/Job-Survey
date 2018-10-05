package jobtrends.jobsurvey.adapter

import android.support.v4.app.Fragment
import android.support.v4.app.FragmentManager
import android.support.v4.app.FragmentStatePagerAdapter

class PagerAdapter(fm: FragmentManager, private var fragments: List<Fragment>) :
        FragmentStatePagerAdapter(fm) {

    override fun getItem(position: Int): Fragment? {
        return fragments[position]
    }

    override fun getCount(): Int {
        return fragments.size
    }

}