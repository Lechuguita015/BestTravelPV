package com.example.besttravel.ui.home

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.MenuItem
import androidx.fragment.app.Fragment
import com.example.besttravel.R
import com.example.besttravel.utils.AppPrefs
import com.example.besttravel.utils.Constants
import com.google.android.material.bottomnavigation.BottomNavigationView


class MenuHome : AppCompatActivity(),BottomNavigationView.OnNavigationItemSelectedListener {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_menu_home)

        val bottomNavigationView = findViewById<BottomNavigationView>(R.id.bottom_navigation)
        bottomNavigationView.setOnNavigationItemSelectedListener(this)

        loadFavoriteData()
        loadFragment(HomeFragment())
    }

    private fun loadFavoriteData() {
        val getList = AppPrefs.getList(this)
        Constants.mFavoriteList.addAll(getList)

        for (i in 0 until getList.size)
        {
            Constants.mStringList.add(getList[i].address)
        }
    }

    override fun onNavigationItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.action_home -> loadFragment(HomeFragment())
            R.id.action_search -> loadFragment(FavoriteFragment())
            R.id.action_profile -> loadFragment(EventsFragment())
        }
        return true
    }

    private fun loadFragment(fragment: Fragment) {
        supportFragmentManager.beginTransaction()
            .replace(R.id.fragment_container, fragment)
            .commit()
    }
}
