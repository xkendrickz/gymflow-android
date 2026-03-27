package com.example.gymflow_android.instrukturView

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.lifecycleScope
import com.example.gymflow_android.MainActivity
import com.example.gymflow_android.R
import com.example.gymflow_android.api.RetrofitClient
import kotlinx.coroutines.launch

class FragmentProfileInstruktur : Fragment() {

    private val myPreference = "myPref"
    private var userId: Int = -1
    private var loadingView: View? = null

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_profile_instruktur, container, false)

        loadingView = view.findViewById(R.id.layout_loading)

        view.findViewById<Button>(R.id.btnLogout).setOnClickListener {
            startActivity(Intent(requireActivity(), MainActivity::class.java))
            requireActivity().finish()
        }

        view.findViewById<Button>(R.id.button4).setOnClickListener {
            startActivity(Intent(requireActivity(), HistoryInstrukturActivity::class.java))
        }

        userId = requireContext()
            .getSharedPreferences(myPreference, Context.MODE_PRIVATE)
            .getInt("userId", -1)

        loadProfile(view)
        return view
    }

    private fun loadProfile(view: View) {
        if (userId == -1) return
        loadingView?.visibility = View.VISIBLE

        viewLifecycleOwner.lifecycleScope.launch {
            try {
                val response = RetrofitClient.api.getInstrukturProfile(userId)
                if (response.isSuccessful && response.body() != null) {
                    val data = response.body()!!.data
                    view.findViewById<TextView>(R.id.tvNamaProfilInstruktur).text = data.nama_instruktur ?: "—"
                    view.findViewById<TextView>(R.id.tvTglLahirProfilInstruktur).text = data.tanggal_lahir ?: "—"
                    view.findViewById<TextView>(R.id.tvWaktuTerlambat).text = data.waktu_terlambat ?: "—"
                } else {
                    Toast.makeText(requireContext(), "Gagal memuat profil.", Toast.LENGTH_SHORT).show()
                }
            } catch (e: Exception) {
                Toast.makeText(requireContext(), "Error: ${e.message}", Toast.LENGTH_SHORT).show()
            } finally {
                loadingView?.visibility = View.GONE
            }
        }
    }
}