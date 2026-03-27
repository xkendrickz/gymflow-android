package com.example.gymflow_android.models

data class Presensi(
    val nama_aktivitas: String? = null,
    val tanggal: String? = null,
    val jenis: String? = null,
    val status: String? = null,
    val kelas: String? = null,
)

data class PresensiListResponse(val data: List<Presensi>)