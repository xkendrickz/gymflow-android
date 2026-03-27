package com.example.gymflow_android.memberView.fragments

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout
import com.example.gymflow_android.R
import com.example.gymflow_android.adapters.TransaksiAdapter
import com.example.gymflow_android.api.RetrofitClient
import kotlinx.coroutines.launch

class FragmentHistoryTransaksi : Fragment() {

    private var adapter: TransaksiAdapter? = null
    private val myPreference = "myPref"
    private var userId: Int = -1

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_history_transaksi, container, false)

        userId = requireContext()
            .getSharedPreferences(myPreference, Context.MODE_PRIVATE)
            .getInt("userId", -1)

        adapter = TransaksiAdapter(emptyList(), requireContext())
        view.findViewById<androidx.recyclerview.widget.RecyclerView>(R.id.rv_transaksi).apply {
            layoutManager = LinearLayoutManager(requireContext())
            adapter = this@FragmentHistoryTransaksi.adapter
        }

        view.findViewById<SwipeRefreshLayout>(R.id.sr_transaksi)
            .setOnRefreshListener { loadTransaksi(view) }

        loadTransaksi(view)
        return view
    }

    private fun loadTransaksi(view: View) {
        view.findViewById<SwipeRefreshLayout>(R.id.sr_transaksi).isRefreshing = true
        viewLifecycleOwner.lifecycleScope.launch {
            try {
                val response = RetrofitClient.api.getHistoryTransaksi(userId)
                if (response.isSuccessful && response.body() != null) {
                    adapter?.setTransaksiList(response.body()!!.data.toTypedArray())
                } else {
                    Toast.makeText(requireContext(), "Gagal memuat data.", Toast.LENGTH_SHORT).show()
                }
            } catch (e: Exception) {
                Toast.makeText(requireContext(), "Error: ${e.message}", Toast.LENGTH_SHORT).show()
            } finally {
                view.findViewById<SwipeRefreshLayout>(R.id.sr_transaksi).isRefreshing = false
            }
        }
    }
}