package com.example.gymflow_android.models

data class PresensiInstruktur(
    val id_jadwal_harian: Int = 0,
    val mulai_kelas: String = "",
    val selesai_kelas: String = "",
    val id_presensi_instruktur: Int? = null,
)