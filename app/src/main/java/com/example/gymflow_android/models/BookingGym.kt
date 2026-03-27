package com.example.gymflow_android.models

data class BookingGym(
    val id_member: Int = 0,
    val tanggal: String = "",
    val slot_waktu: String = "",
    val id_booking_gym: Int? = null,
    val status: Int? = null,
)

data class BookingGymListResponse(val data: List<BookingGym>)