package com.example.gymflow_android.instrukturView

import android.content.Context
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout
import com.example.gymflow_android.R
import com.example.gymflow_android.adapters.InstrukturAdapter
import com.example.gymflow_android.api.RetrofitClient
import kotlinx.coroutines.launch

class HistoryInstrukturActivity : AppCompatActivity() {

    private var adapter: InstrukturAdapter? = null
    private val myPreference = "myPref"
    private var userId: Int = -1

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_history_instruktur)
        supportActionBar?.hide()

        userId = getSharedPreferences(myPreference, Context.MODE_PRIVATE).getInt("userId", -1)

        adapter = InstrukturAdapter(emptyList(), this)
        findViewById<androidx.recyclerview.widget.RecyclerView>(R.id.rv_instruktur).apply {
            layoutManager = LinearLayoutManager(this@HistoryInstrukturActivity)
            adapter = this@HistoryInstrukturActivity.adapter
        }

        findViewById<SwipeRefreshLayout>(R.id.sr_instruktur).setOnRefreshListener { loadHistory() }
        loadHistory()
    }

    private fun loadHistory() {
        findViewById<SwipeRefreshLayout>(R.id.sr_instruktur).isRefreshing = true
        lifecycleScope.launch {
            try {
                val response = RetrofitClient.api.getHistoryInstruktur(userId)
                if (response.isSuccessful && response.body() != null) {
                    adapter?.setInstrukturList(response.body()!!.data.toTypedArray())
                } else {
                    Toast.makeText(this@HistoryInstrukturActivity, "Gagal memuat data.", Toast.LENGTH_SHORT).show()
                }
            } catch (e: Exception) {
                Toast.makeText(this@HistoryInstrukturActivity, "Error: ${e.message}", Toast.LENGTH_SHORT).show()
            } finally {
                findViewById<SwipeRefreshLayout>(R.id.sr_instruktur).isRefreshing = false
            }
        }
    }
}