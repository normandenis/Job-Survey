package jobtrends.job_aymax

import android.os.Bundle
import android.support.design.widget.NavigationView
import android.support.v4.view.GravityCompat
import android.support.v4.widget.DrawerLayout
import android.support.v7.app.ActionBarDrawerToggle
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.Toolbar
import android.view.MenuItem
import android.view.View
import android.widget.FrameLayout

class NavDrawerViewModel : AppCompatActivity(), NavigationView.OnNavigationItemSelectedListener
{
	override fun onCreate(savedInstanceState : Bundle?)
	{
		super.onCreate(savedInstanceState)
		setContentView(R.layout.nav_drawer_view)
		val toolbar = findViewById<View>(R.id.toolbar) as Toolbar
		setSupportActionBar(toolbar)


		val drawer = findViewById<View>(R.id.drawer_layout) as DrawerLayout
		val toggle = ActionBarDrawerToggle(this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close)
		drawer.setDrawerListener(toggle)
		toggle.syncState()

		val navigationView = findViewById<View>(R.id.nav_view) as NavigationView
		navigationView.setNavigationItemSelectedListener(this)

		if (findViewById<FrameLayout>(R.id.fragment_app_bar_nav_drawer_0) != null)
		{
			if (savedInstanceState != null)
			{
				return
			}
			val firstFragment = StartSurveyViewModel()
			val transaction = supportFragmentManager.beginTransaction()
			transaction.add(R.id.fragment_app_bar_nav_drawer_0, firstFragment)
			transaction.commit()
		}
	}

	override fun onBackPressed()
	{
		val drawer = findViewById<View>(R.id.drawer_layout) as DrawerLayout
		if (drawer.isDrawerOpen(GravityCompat.START))
		{
			drawer.closeDrawer(GravityCompat.START)
		}
		else
		{
			super.onBackPressed()
		}
	}

	override fun onNavigationItemSelected(item : MenuItem) : Boolean
	{
		// Handle navigation view item clicks here.
		val id = item.itemId

		if (id == R.id.nav_gallery)
		{

		}
		else if (id == R.id.nav_slideshow)
		{

		}

		val drawer = findViewById<View>(R.id.drawer_layout) as DrawerLayout
		drawer.closeDrawer(GravityCompat.START)
		return true
	}
}
