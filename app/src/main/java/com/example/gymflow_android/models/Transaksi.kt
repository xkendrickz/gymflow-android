package com.example.gymflow_android.models

data class Transaksi(
    val nama_aktivitas: String? = null,
    val tanggal: String? = null,
    val harga: String? = null,
    val kelas: String? = null,
)

data class TransaksiListResponse(val data: List<Transaksi>)