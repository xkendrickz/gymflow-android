package com.example.gymflow_android.memberView

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import com.example.gymflow_android.R
import com.example.gymflow_android.memberView.fragments.FragmentHistoryPresensi
import com.example.gymflow_android.memberView.fragments.FragmentHistoryTransaksi
import com.google.android.material.bottomnavigation.BottomNavigationView

class HistoryMemberActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_history_member)
        supportActionBar?.hide()

        changeFragment(FragmentHistoryTransaksi())

        val bottomNav = findViewById<BottomNavigationView>(R.id.bottom_navigation)
        bottomNav.setOnItemSelectedListener {
            when (it.itemId) {
                R.id.menu_transaksi -> { changeFragment(FragmentHistoryTransaksi()); true }
                R.id.menu_presensi  -> { changeFragment(FragmentHistoryPresensi()); true }
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