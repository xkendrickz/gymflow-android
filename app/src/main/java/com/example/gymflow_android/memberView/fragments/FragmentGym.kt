package com.example.gymflow_android.memberView.fragments

import android.app.DatePickerDialog
import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import androidx.fragment.app.Fragment
import androidx.lifecycle.lifecycleScope
import com.example.gymflow_android.R
import com.example.gymflow_android.api.RetrofitClient
import com.example.gymflow_android.models.BookingGym
import kotlinx.coroutines.launch
import java.text.SimpleDateFormat
import java.util.*

class FragmentGym : Fragment() {

    companion object {
        private val SLOT_WAKTU = arrayOf(
            "07:00:00", "09:00:00", "11:00:00",
            "13:00:00", "15:00:00", "17:00:00", "19:00:00"
        )
    }

    private var etTanggal: EditText? = null
    private var edTanggal2: AutoCompleteTextView? = null
    private var edSlotWaktu: AutoCompleteTextView? = null
    private val myPreference = "myPref"
    private var userId: Int = -1

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_gym, container, false)

        etTanggal   = view.findViewById(R.id.etTanggal)
        edTanggal2  = view.findViewById(R.id.edTanggal2)
        edSlotWaktu = view.findViewById(R.id.edSlotWaktu)

        userId = requireContext()
            .getSharedPreferences(myPreference, Context.MODE_PRIVATE)
            .getInt("userId", -1)

        edSlotWaktu?.setAdapter(ArrayAdapter(requireContext(), R.layout.item_list, SLOT_WAKTU))
        edSlotWaktu?.isFocusable = false
        etTanggal?.isFocusable = false
        etTanggal?.setOnClickListener { showDatePicker() }

        loadBookingDates()

        view.findViewById<Button>(R.id.btn_save).setOnClickListener { createBooking() }
        view.findViewById<Button>(R.id.btn_cancel).setOnClickListener {
            val tanggal = edTanggal2?.text.toString()
            if (tanggal.isNotEmpty()) deleteBooking(tanggal)
            else Toast.makeText(requireContext(), "Pilih tanggal terlebih dahulu.", Toast.LENGTH_SHORT).show()
        }

        return view
    }

    private fun loadBookingDates() {
        viewLifecycleOwner.lifecycleScope.launch {
            try {
                val response = RetrofitClient.api.getBookingGymByMember(userId)
                if (response.isSuccessful && response.body() != null) {
                    val dates = response.body()!!.data.map { it.tanggal }
                    edTanggal2?.setAdapter(ArrayAdapter(requireContext(), R.layout.item_list, dates))
                }
            } catch (e: Exception) {
                Toast.makeText(requireContext(), "Error: ${e.message}", Toast.LENGTH_SHORT).show()
            }
        }
    }

    private fun createBooking() {
        val tanggal   = etTanggal?.text.toString()
        val slotWaktu = edSlotWaktu?.text.toString()

        if (tanggal.isEmpty() || slotWaktu.isEmpty()) {
            Toast.makeText(requireContext(), "Tanggal dan slot waktu harus diisi.", Toast.LENGTH_SHORT).show()
            return
        }

        viewLifecycleOwner.lifecycleScope.launch {
            try {
                val response = RetrofitClient.api.createBookingGym(
                    BookingGym(id_member = userId, tanggal = tanggal, slot_waktu = slotWaktu)
                )
                if (response.isSuccessful) {
                    Toast.makeText(requireContext(), "Booking berhasil ditambahkan.", Toast.LENGTH_SHORT).show()
                    etTanggal?.setText("")
                    edSlotWaktu?.setText("")
                    loadBookingDates()
                } else {
                    Toast.makeText(requireContext(), "Gagal membuat booking.", Toast.LENGTH_SHORT).show()
                }
            } catch (e: Exception) {
                Toast.makeText(requireContext(), "Error: ${e.message}", Toast.LENGTH_SHORT).show()
            }
        }
    }

    private fun deleteBooking(tanggal: String) {
        viewLifecycleOwner.lifecycleScope.launch {
            try {
                val response = RetrofitClient.api.deleteBookingGym(userId, tanggal)
                if (response.isSuccessful) {
                    Toast.makeText(requireContext(), "Booking berhasil dihapus.", Toast.LENGTH_SHORT).show()
                    loadBookingDates()
                } else {
                    Toast.makeText(requireContext(), "Gagal menghapus booking.", Toast.LENGTH_SHORT).show()
                }
            } catch (e: Exception) {
                Toast.makeText(requireContext(), "Error: ${e.message}", Toast.LENGTH_SHORT).show()
            }
        }
    }

    private fun showDatePicker() {
        val cal = Calendar.getInstance()
        DatePickerDialog(requireContext(), { _, y, m, d ->
            cal.set(y, m, d)
            etTanggal?.setText(SimpleDateFormat("yyyy-MM-dd", Locale.getDefault()).format(cal.time))
        }, cal.get(Calendar.YEAR), cal.get(Calendar.MONTH), cal.get(Calendar.DAY_OF_MONTH)).show()
    }
}