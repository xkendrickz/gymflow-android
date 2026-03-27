package com.example.gymflow_android.instrukturView

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import com.example.gymflow_android.FragmentHome
import com.example.gymflow_android.R
import com.google.android.material.bottomnavigation.BottomNavigationView

class HomeInstrukturActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_home_instruktur)
        supportActionBar?.hide()

        changeFragment(FragmentHome())

        findViewById<BottomNavigationView>(R.id.bottom_navigation).setOnItemSelectedListener {
            when (it.itemId) {
                R.id.menu_home     -> { changeFragment(FragmentHome()); true }
                R.id.menu_izin     -> { changeFragment(FragmentIzin()); true }
                R.id.menu_presence -> { changeFragment(FragmentPresensiMember()); true }
                R.id.menu_profile  -> { changeFragment(FragmentProfileInstruktur()); true }
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