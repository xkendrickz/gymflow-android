package com.example.gymflow_android.memberView.fragments

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
import com.example.gymflow_android.memberView.HistoryMemberActivity
import kotlinx.coroutines.launch

class FragmentProfileMember : Fragment() {

    private val myPreference = "myPref"
    private var userId: Int = -1

    private var tvNama: TextView? = null
    private var tvAlamat: TextView? = null
    private var tvTglLahir: TextView? = null
    private var tvTelepon: TextView? = null
    private var tvEmail: TextView? = null
    private var tvMasaBerlaku: TextView? = null
    private var tvNamaKelas: TextView? = null
    private var tvDepositPaket: TextView? = null
    private var tvDepositReguler: TextView? = null
    private var loadingView: View? = null

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_profile_member, container, false)

        tvNama           = view.findViewById(R.id.tvNamaProfilMember)
        tvAlamat         = view.findViewById(R.id.tvAlamatProfilMember)
        tvTglLahir       = view.findViewById(R.id.tvTglLahirProfilMember)
        tvTelepon        = view.findViewById(R.id.tvTeleponProfilMember)
        tvEmail          = view.findViewById(R.id.tvEmailProfilMember)
        tvMasaBerlaku    = view.findViewById(R.id.tvMasaBerlakuProfilMember)
        tvNamaKelas      = view.findViewById(R.id.tvJenisPaketBerlakuProfilMember)
        tvDepositPaket   = view.findViewById(R.id.tvDepositPaketBerlakuProfilMember)
        tvDepositReguler = view.findViewById(R.id.tvDepositRegulerBerlakuProfilMember)
        loadingView      = view.findViewById(R.id.layout_loading)

        val btnLogout: Button = view.findViewById(R.id.btnLogout)
        val btnHistory: Button = view.findViewById(R.id.button4)

        btnLogout.setOnClickListener {
            startActivity(Intent(requireActivity(), MainActivity::class.java))
            requireActivity().finish()
        }

        btnHistory.setOnClickListener {
            startActivity(Intent(requireActivity(), HistoryMemberActivity::class.java))
        }

        userId = requireContext()
            .getSharedPreferences(myPreference, Context.MODE_PRIVATE)
            .getInt("userId", -1)

        loadProfile()
        return view
    }

    private fun loadProfile() {
        if (userId == -1) {
            Toast.makeText(requireContext(), "User tidak ditemukan.", Toast.LENGTH_SHORT).show()
            return
        }

        setLoading(true)
        viewLifecycleOwner.lifecycleScope.launch {
            try {
                val response = RetrofitClient.api.getMemberProfile(userId)
                if (response.isSuccessful && response.body() != null) {
                    val member = response.body()!!.data
                    tvNama?.text           = member.nama_member ?: "—"
                    tvAlamat?.text         = member.alamat ?: "—"
                    tvTglLahir?.text       = member.tanggal_lahir ?: "—"
                    tvTelepon?.text        = member.telepon ?: "—"
                    tvEmail?.text          = member.email ?: "—"
                    tvMasaBerlaku?.text    = member.masa_aktif ?: "—"
                    tvNamaKelas?.text      = member.nama_kelas ?: "—"
                    tvDepositPaket?.text   = member.sisa_deposit_paket?.toString() ?: "0"
                    tvDepositReguler?.text = member.sisa_deposit_reguler?.toString() ?: "0"
                } else {
                    Toast.makeText(requireContext(), "Gagal memuat profil.", Toast.LENGTH_SHORT).show()
                }
            } catch (e: Exception) {
                Toast.makeText(requireContext(), "Error: ${e.message}", Toast.LENGTH_SHORT).show()
            } finally {
                setLoading(false)
            }
        }
    }

    private fun setLoading(isLoading: Boolean) {
        loadingView?.visibility = if (isLoading) View.VISIBLE else View.GONE
    }
}