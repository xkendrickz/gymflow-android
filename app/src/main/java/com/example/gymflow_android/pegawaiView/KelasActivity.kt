package com.example.gymflow_android.pegawaiView

import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout
import com.example.gymflow_android.R
import com.example.gymflow_android.adapters.KelasAdapter
import com.example.gymflow_android.api.RetrofitClient
import kotlinx.coroutines.launch

class KelasActivity : AppCompatActivity() {

    private var adapter: KelasAdapter? = null

    companion object {
        const val LAUNCH_ADD_ACTIVITY = 123
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_kelas)
        supportActionBar?.hide()

        adapter = KelasAdapter(emptyList(), this)
        findViewById<androidx.recyclerview.widget.RecyclerView>(R.id.rv_kelas).apply {
            layoutManager = LinearLayoutManager(this@KelasActivity)
            adapter = this@KelasActivity.adapter
        }

        val swipeRefresh = findViewById<SwipeRefreshLayout>(R.id.sr_kelas)
        swipeRefresh.setOnRefreshListener { loadKelas() }

        loadKelas()
    }

    private fun loadKelas() {
        findViewById<SwipeRefreshLayout>(R.id.sr_kelas).isRefreshing = true
        lifecycleScope.launch {
            try {
                val response = RetrofitClient.api.getPresensiInstruktur()
                if (response.isSuccessful && response.body() != null) {
                    val list = response.body()!!.data
                    adapter?.setKelasList(list.toTypedArray())
                    if (list.isEmpty())
                        Toast.makeText(this@KelasActivity, "Data kosong.", Toast.LENGTH_SHORT).show()
                } else {
                    Toast.makeText(this@KelasActivity, "Gagal memuat data.", Toast.LENGTH_SHORT).show()
                }
            } catch (e: Exception) {
                Toast.makeText(this@KelasActivity, "Error: ${e.message}", Toast.LENGTH_SHORT).show()
            } finally {
                findViewById<SwipeRefreshLayout>(R.id.sr_kelas).isRefreshing = false
            }
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == LAUNCH_ADD_ACTIVITY && resultCode == RESULT_OK) loadKelas()
    }
}