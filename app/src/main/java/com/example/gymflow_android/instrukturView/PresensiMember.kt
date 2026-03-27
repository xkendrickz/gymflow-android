package com.example.gymflow_android.instrukturView

import android.content.Context
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout
import com.example.gymflow_android.R
import com.example.gymflow_android.adapters.MemberAdapter
import com.example.gymflow_android.api.RetrofitClient
import kotlinx.coroutines.launch

class PresensiMember : AppCompatActivity() {

    private var adapter: MemberAdapter? = null
    private val myPreference = "myPref"
    private var userId: Int = -1

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_presensi_member)
        supportActionBar?.hide()

        userId = getSharedPreferences(myPreference, Context.MODE_PRIVATE).getInt("userId", -1)

        adapter = MemberAdapter(emptyList(), this)
        findViewById<androidx.recyclerview.widget.RecyclerView>(R.id.rv_member).apply {
            layoutManager = LinearLayoutManager(this@PresensiMember)
            adapter = this@PresensiMember.adapter
        }

        findViewById<SwipeRefreshLayout>(R.id.sr_member).setOnRefreshListener { loadMembers() }
        loadMembers()
    }

    private fun loadMembers() {
        findViewById<SwipeRefreshLayout>(R.id.sr_member).isRefreshing = true
        lifecycleScope.launch {
            try {
                val response = RetrofitClient.api.getPresensiKelas(userId)
                if (response.isSuccessful && response.body() != null) {
                    adapter?.setMemberList(response.body()!!.data.toTypedArray())
                } else {
                    Toast.makeText(this@PresensiMember, "Gagal memuat data.", Toast.LENGTH_SHORT).show()
                }
            } catch (e: Exception) {
                Toast.makeText(this@PresensiMember, "Error: ${e.message}", Toast.LENGTH_SHORT).show()
            } finally {
                findViewById<SwipeRefreshLayout>(R.id.sr_member).isRefreshing = false
            }
        }
    }

    fun updateStatus(id: Int) {
        lifecycleScope.launch {
            try {
                val response = RetrofitClient.api.updatePresensiKelas(id)
                if (response.isSuccessful) {
                    Toast.makeText(this@PresensiMember, "Status berhasil diupdate.", Toast.LENGTH_SHORT).show()
                    loadMembers()
                } else {
                    Toast.makeText(this@PresensiMember, "Gagal update status.", Toast.LENGTH_SHORT).show()
                }
            } catch (e: Exception) {
                Toast.makeText(this@PresensiMember, "Error: ${e.message}", Toast.LENGTH_SHORT).show()
            }
        }
    }
}