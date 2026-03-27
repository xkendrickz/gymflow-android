package com.example.gymflow_android.memberView

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import com.example.gymflow_android.FragmentHome
import com.example.gymflow_android.R
import com.example.gymflow_android.memberView.fragments.FragmentClass
import com.example.gymflow_android.memberView.fragments.FragmentGym
import com.example.gymflow_android.memberView.fragments.FragmentProfileMember
import com.google.android.material.bottomnavigation.BottomNavigationView

class HomeMemberActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_home_member)
        supportActionBar?.hide()

        changeFragment(FragmentHome())

        val bottomNav = findViewById<BottomNavigationView>(R.id.bottom_navigation)
        bottomNav.setOnItemSelectedListener {
            when (it.itemId) {
                R.id.menu_home    -> { changeFragment(FragmentHome()); true }
                R.id.menu_gym     -> { changeFragment(FragmentGym()); true }
                R.id.menu_class   -> { changeFragment(FragmentClass()); true }
                R.id.menu_profile -> { changeFragment(FragmentProfileMember()); true }
                else -> false
            }
        }
    }

    private fun changeFragment(fragment: Fragment) {
        supportFragmentManager.beginTransaction()
            .replace(R.id.layoutFragment, fragment)
            .commit()
    }
}