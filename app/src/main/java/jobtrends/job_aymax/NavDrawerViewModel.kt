package jobtrends.job_aymax

import android.os.Bundle
import android.support.design.widget.FloatingActionButton
import android.support.design.widget.Snackbar
import android.view.View
import android.support.design.widget.NavigationView
import android.support.v4.view.GravityCompat
import android.support.v4.widget.DrawerLayout
import android.support.v7.app.ActionBarDrawerToggle
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.Toolbar
import android.view.Menu
import android.view.MenuItem

class NavDrawerViewModel : AppCompatActivity(), NavigationView.OnNavigationItemSelectedListener
{

	override fun onCreate(savedInstanceState : Bundle?)
	{
		super.onCreate(savedInstanceState)
		setContentView(R.layout.nav_drawer_view)
		val toolbar = findViewById<View>(R.id.toolbar) as Toolbar
		setSupportActionBar(toolbar)

//		val fab = findViewById<View>(R.id.fab) as FloatingActionButton
//		fab.setOnClickListener { view ->
//			Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
//					.setAction("Action", null).show()
//		}

		val drawer = findViewById<View>(R.id.drawer_layout) as DrawerLayout
		val toggle = ActionBarDrawerToggle(
				this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close)
		drawer.setDrawerListener(toggle)
		toggle.syncState()

		val navigationView = findViewById<View>(R.id.nav_view) as NavigationView
		navigationView.setNavigationItemSelectedListener(this)
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

		if (id == R.id.nav_camera)
		{
			// Handle the camera action
		}
		else if (id == R.id.nav_gallery)
		{

		}
		else if (id == R.id.nav_slideshow)
		{

		}
//		else if (id == R.id.nav_manage)
//		{
//
//		}
//		else if (id == R.id.nav_share)
//		{
//
//		}
//		else if (id == R.id.nav_send)
//		{
//
//		}

		val drawer = findViewById<View>(R.id.drawer_layout) as DrawerLayout
		drawer.closeDrawer(GravityCompat.START)
		return true
	}
}
