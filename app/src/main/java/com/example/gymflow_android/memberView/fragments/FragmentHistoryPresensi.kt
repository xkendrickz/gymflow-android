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
import com.example.gymflow_android.adapters.PresensiAdapter
import com.example.gymflow_android.api.RetrofitClient
import kotlinx.coroutines.launch

class FragmentHistoryPresensi : Fragment() {

    private var adapter: PresensiAdapter? = null
    private val myPreference = "myPref"
    private var userId: Int = -1

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_history_presensi, container, false)

        userId = requireContext()
            .getSharedPreferences(myPreference, Context.MODE_PRIVATE)
            .getInt("userId", -1)

        adapter = PresensiAdapter(emptyList(), requireContext())
        view.findViewById<androidx.recyclerview.widget.RecyclerView>(R.id.rv_presensi).apply {
            layoutManager = LinearLayoutManager(requireContext())
            adapter = this@FragmentHistoryPresensi.adapter
        }

        view.findViewById<SwipeRefreshLayout>(R.id.sr_presensi)
            .setOnRefreshListener { loadPresensi(view) }

        loadPresensi(view)
        return view
    }

    private fun loadPresensi(view: View) {
        view.findViewById<SwipeRefreshLayout>(R.id.sr_presensi).isRefreshing = true
        viewLifecycleOwner.lifecycleScope.launch {
            try {
                val response = RetrofitClient.api.getHistoryPresensi(userId)
                if (response.isSuccessful && response.body() != null) {
                    adapter?.setPresensiList(response.body()!!.data.toTypedArray())
                } else {
                    Toast.makeText(requireContext(), "Gagal memuat data.", Toast.LENGTH_SHORT).show()
                }
            } catch (e: Exception) {
                Toast.makeText(requireContext(), "Error: ${e.message}", Toast.LENGTH_SHORT).show()
            } finally {
                view.findViewById<SwipeRefreshLayout>(R.id.sr_presensi).isRefreshing = false
            }
        }
    }
}