package com.example.gymflow_android.models

data class Kelas(
    val nama_instruktur: String? = null,
    val nama_kelas: String? = null,
    val id_jadwal_harian: Int = 0,
)

data class KelasListResponse(val data: List<Kelas>)