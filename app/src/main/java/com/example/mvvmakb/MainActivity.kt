package com.example.mvvmakb

import android.content.Intent
import android.os.Bundle
import android.view.MenuItem
import androidx.appcompat.app.ActionBarDrawerToggle
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.GravityCompat
import androidx.fragment.app.Fragment
import com.example.mvvmakb.activity.LiveRecordActivity
import com.example.mvvmakb.fragment.EventsFragment
import com.example.mvvmakb.fragment.MainFragment
import com.example.mvvmakb.fragment.MembersFragment
import com.google.android.material.navigation.NavigationView
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() , NavigationView.OnNavigationItemSelectedListener{
    private lateinit var currentFragment : Fragment
    private var mainFragment : Fragment = MainFragment.instance
    private var eventsFragment : Fragment = EventsFragment.instance
    private var membersFragment : Fragment = MembersFragment.instance

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        setupUI()
    }

    private fun setupUI(){
        currentFragment = mainFragment
        supportFragmentManager.beginTransaction().apply {
            add(R.id.container_main,membersFragment).hide(membersFragment)
            add(R.id.container_main,eventsFragment).hide(eventsFragment)
            add(R.id.container_main,mainFragment)
        }.commit()

        val toggle = ActionBarDrawerToggle(this,drawer_layout,toolbar,R.string.navigation_drawer_open,R.string.navigation_drawer_close)
        drawer_layout.addDrawerListener(toggle)
        toggle.syncState()
        nav_view.setNavigationItemSelectedListener(this)

        toolbar.title = "首頁"

        navigation.setOnNavigationItemSelectedListener {
            when(it.itemId){
                R.id.nav_home -> {
                    supportFragmentManager.beginTransaction().hide(currentFragment).show(mainFragment).commit()
                    currentFragment = mainFragment
                    toolbar.title = "首頁"
                    true
                }
                R.id.nav_events -> {
                    supportFragmentManager.beginTransaction().hide(currentFragment).show(eventsFragment).commit()
                    currentFragment = eventsFragment
                    toolbar.title = "活動訊息"
                    true
                }
                R.id.nav_members -> {
                    supportFragmentManager.beginTransaction().hide(currentFragment).show(membersFragment).commit()
                    currentFragment = membersFragment
                    toolbar.title = "成員介紹"
                    true
                }
                else -> true
            }
        }
    }

    override fun onBackPressed() {
        if(drawer_layout.isDrawerOpen(GravityCompat.START))
            drawer_layout.closeDrawer(GravityCompat.START)
        else
            super.onBackPressed()
    }

    override fun onResume() {
        super.onResume()
        for ( i in 0 until nav_view.menu.size()){
            nav_view.menu.getItem(i).isChecked = false
        }
    }

    override fun onNavigationItemSelected(item: MenuItem): Boolean {
        drawer_layout.closeDrawer(GravityCompat.START)
        return when(item.itemId){
            R.id.nav_langlive -> {
                startActivity(Intent(this,LiveRecordActivity::class.java))
                true
            }
            else -> true
        }
        return true
    }
}
