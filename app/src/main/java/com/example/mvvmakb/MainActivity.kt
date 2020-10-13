package com.example.mvvmakb

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.fragment.app.Fragment
import com.example.mvvmakb.fragment.EventsFragment
import com.example.mvvmakb.fragment.MainFragment
import com.example.mvvmakb.fragment.MembersFragment
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {
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
        navigation.setOnNavigationItemSelectedListener {
            when(it.itemId){
                R.id.nav_home -> {
                    supportFragmentManager.beginTransaction().hide(currentFragment).show(mainFragment).commit()
                    currentFragment = mainFragment
                    true
                }
                R.id.nav_events -> {
                    supportFragmentManager.beginTransaction().hide(currentFragment).show(eventsFragment).commit()
                    currentFragment = eventsFragment
                    true
                }
                R.id.nav_members -> {
                    supportFragmentManager.beginTransaction().hide(currentFragment).show(membersFragment).commit()
                    currentFragment = membersFragment
                    true
                }
                else -> true
            }
        }
    }


}
