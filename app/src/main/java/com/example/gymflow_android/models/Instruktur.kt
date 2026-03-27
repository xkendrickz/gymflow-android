package com.example.gymflow_android.models

data class Instruktur(
    val nama_kelas: String? = null,
    val hari: String? = null,
    val izin: String? = null,
    val mulai_kelas: String? = null,
    val selesai_kelas: String? = null,
)

data class InstrukturListResponse(val data: List<Instruktur>)

data class InstrukturProfile(
    val id_instruktur: Int? = null,
    val nama_instruktur: String? = null,
    val tanggal_lahir: String? = null,
    val waktu_terlambat: String? = null,
)

data class InstrukturProfileResponse(val message: String, val data: InstrukturProfile)