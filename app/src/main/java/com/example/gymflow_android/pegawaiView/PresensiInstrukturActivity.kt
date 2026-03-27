package com.example.gymflow_android.pegawaiView

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.TextView
import android.widget.TimePicker
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import com.example.gymflow_android.R
import com.example.gymflow_android.api.RetrofitClient
import com.example.gymflow_android.models.PresensiInstruktur
import kotlinx.coroutines.launch

class PresensiInstrukturActivity : AppCompatActivity() {

    private var timePickerMulai: TimePicker? = null
    private var timePickerSelesai: TimePicker? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_presensi_instruktur)
        supportActionBar?.hide()

        timePickerMulai  = findViewById(R.id.timePicker_mulai_kelas)
        timePickerSelesai = findViewById(R.id.timePicker_selesai_kelas)

        val bundle = intent.getBundleExtra("id") ?: return
        findViewById<TextView>(R.id.tv_presensi_kelas).text = bundle.getString("nama_kelas")
        findViewById<TextView>(R.id.tv_presensi_instruktur).text = bundle.getString("nama_instruktur")
        val idJadwalHarian = bundle.getInt("id_jadwal_harian")

        findViewById<Button>(R.id.btn_cancel).setOnClickListener { finish() }
        findViewById<Button>(R.id.btn_save).setOnClickListener { createPresensi(idJadwalHarian) }
    }

    private fun createPresensi(idJadwalHarian: Int) {
        val mulai   = "${timePickerMulai?.hour}:${String.format("%02d", timePickerMulai?.minute)}"
        val selesai = "${timePickerSelesai?.hour}:${String.format("%02d", timePickerSelesai?.minute)}"

        val body = PresensiInstruktur(
            id_jadwal_harian = idJadwalHarian,
            mulai_kelas = mulai,
            selesai_kelas = selesai
        )

        lifecycleScope.launch {
            try {
                val response = RetrofitClient.api.createPresensiInstruktur(body)
                if (response.isSuccessful) {
                    Toast.makeText(this@PresensiInstrukturActivity, "Data berhasil ditambahkan.", Toast.LENGTH_SHORT).show()
                    setResult(RESULT_OK, Intent())
                    finish()
                } else {
                    Toast.makeText(this@PresensiInstrukturActivity, "Gagal menyimpan presensi.", Toast.LENGTH_SHORT).show()
                }
            } catch (e: Exception) {
                Toast.makeText(this@PresensiInstrukturActivity, "Error: ${e.message}", Toast.LENGTH_SHORT).show()
            }
        }
    }
}