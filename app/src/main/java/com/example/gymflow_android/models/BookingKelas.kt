package com.example.gymflow_android.models

data class BookingKelas(
    val nama_kelas: String? = null,
    val nama_instruktur: String? = null,
    val nama_member: String? = null,
    val id_booking_kelas: Int? = null,
    val jenis: String? = null,
    val status: Int? = null,
)

data class BookingKelasListResponse(val data: List<BookingKelas>)