package com.example.gymflow_android.api

import com.example.gymflow_android.models.Auth
import com.example.gymflow_android.models.BookingGym
import com.example.gymflow_android.models.BookingGymListResponse
import com.example.gymflow_android.models.BookingKelasListResponse
import com.example.gymflow_android.models.InstrukturListResponse
import com.example.gymflow_android.models.InstrukturProfileResponse
import com.example.gymflow_android.models.KelasListResponse
import com.example.gymflow_android.models.LoginResponse
import com.example.gymflow_android.models.MemberProfileResponse
import com.example.gymflow_android.models.PresensiInstruktur
import com.example.gymflow_android.models.PresensiListResponse
import com.example.gymflow_android.models.TransaksiListResponse
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.DELETE
import retrofit2.http.POST
import retrofit2.http.GET
import retrofit2.http.PUT
import retrofit2.http.Path

interface ApiService {
    // Auth
    @POST("loginAndroid")
    suspend fun login(@Body request: Auth): Response<LoginResponse>

    // Member
    @GET("profileMember/{id}")
    suspend fun getMemberProfile(@Path("id") id: Int): Response<MemberProfileResponse>

    // Instruktur
    @GET("profileInstruktur/{id}")
    suspend fun getInstrukturProfile(@Path("id") id: Int): Response<InstrukturProfileResponse>

    // Booking Gym
    @GET("bookingGym/{id}")
    suspend fun getBookingGymByMember(@Path("id") id: Int): Response<BookingGymListResponse>

    @POST("bookingGym")
    suspend fun createBookingGym(@Body body: BookingGym): Response<Any>

    @DELETE("bookingGym/{id_member}/{tanggal}")
    suspend fun deleteBookingGym(
        @Path("id_member") idMember: Int,
        @Path("tanggal") tanggal: String
    ): Response<Any>

    // Presensi
    @GET("presensiInstruktur")
    suspend fun getPresensiInstruktur(): Response<KelasListResponse>

    @POST("presensiInstruktur")
    suspend fun createPresensiInstruktur(@Body body: PresensiInstruktur): Response<Any>

    @GET("presensiKelas/{id}")
    suspend fun getPresensiKelas(@Path("id") id: Int): Response<BookingKelasListResponse>

    @PUT("presensiKelas/{id}")
    suspend fun updatePresensiKelas(@Path("id") id: Int): Response<Any>

    // History
    @GET("historyMemberTransaksi/{id}")
    suspend fun getHistoryTransaksi(@Path("id") id: Int): Response<TransaksiListResponse>

    @GET("historyMemberPresensi/{id}")
    suspend fun getHistoryPresensi(@Path("id") id: Int): Response<PresensiListResponse>

    @GET("historyInstruktur/{id}")
    suspend fun getHistoryInstruktur(@Path("id") id: Int): Response<InstrukturListResponse>
}