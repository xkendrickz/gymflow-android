package com.example.gymflow_android.models

data class Auth(
    val username: String = "",
    val password: String = "",
    val userType: String? = null,
    val id_member: Int? = null,
    val id_pegawai: Int? = null,
    val id_instruktur: Int? = null,
)

data class LoginResponse(val message: String, val token: String, val userType: String, val data: Auth)