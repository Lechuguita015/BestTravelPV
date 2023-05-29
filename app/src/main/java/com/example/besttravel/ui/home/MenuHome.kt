package com.example.besttravel.ui.home

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.MenuItem
import androidx.appcompat.app.ActionBarDrawerToggle
import androidx.fragment.app.Fragment
import androidx.navigation.NavController
import androidx.navigation.findNavController
import com.example.besttravel.R
import com.example.besttravel.databinding.ActivityMenuHomeBinding
import com.example.besttravel.ui.EditUserFragment
import com.example.besttravel.ui.SupportFragment
import com.example.besttravel.utils.AppPrefs
import com.example.besttravel.utils.Constants
import com.google.android.material.bottomnavigation.BottomNavigationView


class MenuHome : AppCompatActivity(),BottomNavigationView.OnNavigationItemSelectedListener {

    private lateinit var binding: ActivityMenuHomeBinding
    private lateinit var toggle: ActionBarDrawerToggle


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMenuHomeBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.bottomNavigation.setOnNavigationItemSelectedListener(this)
        loadFavoriteData()
        loadFragment(HomeFragment())
        supportActionBar?.setDisplayShowTitleEnabled(false)



        //Todavia no funciona
        toggle = ActionBarDrawerToggle(this, binding.drawerLayout, R.string.open,R.string.close)
        binding.drawerLayout.addDrawerListener(toggle)
        toggle.syncState()
        supportActionBar?.setDisplayHomeAsUpEnabled(true)


        binding.navView.setNavigationItemSelectedListener {item ->
            when(item.itemId){
                R.id.nav_edit -> loadFragment(EditUserFragment())
                R.id.nav_info -> loadFragment(SupportFragment())
            }
            true
        }
        // Hasta aqui
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

    override fun onOptionsItemSelected(item2: MenuItem): Boolean {
        if (toggle.onOptionsItemSelected(item2)){
            return true
        }
        return super.onOptionsItemSelected(item2)
    }
    private fun getNavController():NavController{
        return binding.fragmentContainer.findNavController()
    }
}
