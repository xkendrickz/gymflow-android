package com.example.gymflow_android.models

data class Member(
    val id_member: Int? = null,
    val member_id: String? = null,
    val nama_member: String? = null,
    val alamat: String? = null,
    val tanggal_lahir: String? = null,
    val telepon: String? = null,
    val email: String? = null,
    val sisa_deposit_reguler: Int? = null,
    val sisa_deposit_paket: Int? = null,
    val masa_aktif: String? = null,
    val nama_kelas: String? = null,
)

data class MemberProfileResponse(val message: String, val data: Member)