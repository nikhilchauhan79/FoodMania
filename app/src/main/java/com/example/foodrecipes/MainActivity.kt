package com.example.foodrecipes

import android.os.Bundle
import android.os.PersistableBundle
import android.view.Gravity
import android.view.MenuItem
import android.widget.Toast
import androidx.appcompat.app.ActionBarDrawerToggle
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.GravityCompat
import androidx.navigation.ui.setupWithNavController
import com.google.android.material.appbar.MaterialToolbar
import com.google.android.material.bottomnavigation.BottomNavigationView
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.snippet_view_home_toolbar.*


class MainActivity : AppCompatActivity() {

    lateinit var toggle: ActionBarDrawerToggle
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContentView(R.layout.activity_main)


        setSupportActionBar(findViewById(R.id.toolbar))

        toggle=ActionBarDrawerToggle(this,drawer_layout,R.string.open,R.string.close)

        drawer_layout.addDrawerListener(toggle)
        toggle.isDrawerIndicatorEnabled=true
        toggle.syncState()
        supportActionBar?.setDisplayHomeAsUpEnabled(true)


        if (savedInstanceState == null) {

            supportFragmentManager.beginTransaction().apply {
                replace(R.id.nav_host_fragment, FragmentHome())
                addToBackStack(null)
                commit()
            }
        }


        nav_view.setNavigationItemSelectedListener {
            when(it.itemId){
                R.id.nav_cuisine ->
                    supportFragmentManager.beginTransaction().apply {
                        replace(R.id.nav_host_fragment,FragmentHome())
                        addToBackStack(null)
                        commit()
                    }

                R.id.nav_diet -> supportFragmentManager.beginTransaction().apply {
                    replace(R.id.nav_host_fragment,GoogleLoginFragment())
                    addToBackStack(null)
                    commit()
                }
                R.id.nav_equipment ->supportFragmentManager.beginTransaction().apply {
                    replace(R.id.nav_host_fragment,RegisterFragment())
                    addToBackStack(null)
                    commit()
                }
                R.id.nav_type -> Toast.makeText(applicationContext,"Type clicked",Toast.LENGTH_SHORT).show()
            }
            drawer_layout.closeDrawer(GravityCompat.START)
            true
        }

        bottom_navigation.setOnNavigationItemSelectedListener {
            when (it.itemId) {
                R.id.nav_search -> {
                    // Respond to navigation item 1 click
                    supportFragmentManager.beginTransaction().apply {
                        replace(R.id.nav_host_fragment, SearchFragment())
                        addToBackStack(null)
                        commit()
                    }
                    true
                }
                R.id.nav_favorites -> {
                    // Respond to navigation item 2 click
                    true
                }

                R.id.nav_home -> {
                    // Respond to navigation item 2 click
                    true
                }

                R.id.nav_favorites -> {
                    // Respond to navigation item 2 click
                    true
                }

                R.id.nav_favorites -> {
                    // Respond to navigation item 2 click
                    true
                }
                else -> false
            }
        }


    }
    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if(toggle.onOptionsItemSelected(item)){
            drawer_layout.openDrawer(Gravity.LEFT)
            return true
        }
        return super.onOptionsItemSelected(item)
    }

    override fun onBackPressed() {
        if (drawer_layout.isDrawerOpen(GravityCompat.START)) {
            drawer_layout.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }


}