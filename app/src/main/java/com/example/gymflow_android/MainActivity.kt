package com.example.gymflow_android

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.View
import android.view.WindowManager
import android.widget.Button
import android.widget.LinearLayout
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import com.example.gymflow_android.api.RetrofitClient
import com.example.gymflow_android.databinding.ActivityMainBinding
import com.example.gymflow_android.instrukturView.HomeInstrukturActivity
import com.example.gymflow_android.memberView.HomeMemberActivity
import com.example.gymflow_android.pegawaiView.HomePegawaiActivity
import com.example.gymflow_android.models.Auth
import com.google.android.material.textfield.TextInputEditText
import com.google.android.material.textfield.TextInputLayout
import kotlinx.coroutines.launch

class MainActivity : AppCompatActivity() {
    private lateinit var inputUsername: TextInputLayout
    private lateinit var inputPassword: TextInputLayout
    private lateinit var etUsername: TextInputEditText
    private lateinit var etPassword: TextInputEditText
    private val myPreference = "myPref"
    lateinit var mbundle: Bundle
    lateinit var vUsername: String
    lateinit var vPassword: String

    private var layoutLoading: LinearLayout? = null
    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        supportActionBar?.hide()
        title = "User Login"

        layoutLoading = findViewById(R.id.layout_loading)
        inputUsername = findViewById(R.id.inputLayoutUsername)
        inputPassword = findViewById(R.id.inputLayoutPassword)
        val textBtnGuest: TextView = findViewById(R.id.textBtnGuest)
        val btnLogin: Button = findViewById(R.id.btnLogin)
        getBundle()
        setText()

        textBtnGuest.setOnClickListener {
            startActivity(Intent(this, GuestActivity::class.java))
        }

        btnLogin.setOnClickListener {
            var isValid = true

            if (inputUsername.editText?.text.toString().isEmpty()) {
                inputUsername.error = "Username harus diisi"
                isValid = false
            } else inputUsername.error = null

            if (inputPassword.editText?.text.toString().isEmpty()) {
                inputPassword.error = "Password harus diisi"
                isValid = false
            } else inputPassword.error = null

            if (isValid) login()
        }
    }

    private fun login() {
        setLoading(true)

        val request = Auth(
            username = inputUsername.editText?.text.toString(),
            password = inputPassword.editText?.text.toString()
        )

        lifecycleScope.launch {
            try {
                val response = RetrofitClient.api.login(request)

                if (response.isSuccessful && response.body() != null) {
                    val body = response.body()!!
                    val userType = body.userType
                    val data = body.data

                    val userId = when (userType) {
                        "pegawai"    -> data.id_pegawai
                        "member"     -> data.id_member
                        "instruktur" -> data.id_instruktur
                        else         -> null
                    } ?: 0

                    // Save to SharedPreferences
                    val prefs = getSharedPreferences(myPreference, Context.MODE_PRIVATE)
                    prefs.edit()
                        .putInt("userId", userId)
                        .putString("token", body.token)
                        .putString("userType", userType)
                        .apply()

                    Log.d("Login", "userType=$userType userId=$userId")
                    Toast.makeText(this@MainActivity, "Berhasil Login!", Toast.LENGTH_SHORT).show()

                    val intent = when (userType) {
                        "pegawai"    -> Intent(this@MainActivity, HomePegawaiActivity::class.java)
                        "member"     -> Intent(this@MainActivity, HomeMemberActivity::class.java)
                        "instruktur" -> Intent(this@MainActivity, HomeInstrukturActivity::class.java)
                        else -> null
                    }

                    intent?.let {
                        startActivity(it)
                        finish()
                    }

                } else {
                    val errorMsg = response.errorBody()?.string() ?: "Login gagal."
                    Log.d("Login Error", errorMsg)
                    Toast.makeText(this@MainActivity, "Username atau password salah.", Toast.LENGTH_LONG).show()
                }

            } catch (e: Exception) {
                Log.d("Login Exception", e.message.toString())
                Toast.makeText(this@MainActivity, "Terjadi kesalahan: ${e.message}", Toast.LENGTH_LONG).show()
            } finally {
                setLoading(false)
            }
        }
    }

    fun getBundle() {
        try {
            mbundle = intent?.getBundleExtra("register")!!
            vUsername = mbundle.getString("username") ?: ""
            vPassword = mbundle.getString("password") ?: ""
        } catch (e: NullPointerException) {
            vUsername = ""
            vPassword = ""
        }
    }

    fun setText() {
        etUsername = findViewById(R.id.etUsername)
        etPassword = findViewById(R.id.etPassword)
        etUsername.setText(vUsername)
        etPassword.setText(vPassword)
    }

    private fun setLoading(isLoading: Boolean) {
        if (isLoading) {
            window.setFlags(
                WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE,
                WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE
            )
            layoutLoading?.visibility = View.VISIBLE
        } else {
            window.clearFlags(WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE)
            layoutLoading?.visibility = View.GONE
        }
    }
}