package com.example.candles_guardian.representation.ui.main_activity

import android.content.Intent
import android.content.SharedPreferences
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Button
import android.widget.CheckBox
import android.widget.ProgressBar
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.widget.doOnTextChanged
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import com.example.candles_guardian.R
import com.example.candles_guardian.pojo.User
import com.example.candles_guardian.representation.ui.HomeActivity
import com.example.retrofitandcoroutine.data.remote.RetrofitClient
import com.example.weatherforecast.data.remote.ApiHelperImpl
import com.example.weatherforecast.utils.Status
import com.google.android.material.textfield.TextInputEditText
import com.google.android.material.textfield.TextInputLayout
import com.google.gson.Gson
import kotlinx.coroutines.flow.collect


class MainActivity : AppCompatActivity() {
    private lateinit var userNameTF: TextInputLayout
    private lateinit var passwordTF: TextInputLayout
    private lateinit var userNameET: TextInputEditText
    private lateinit var passwordET: TextInputEditText
    private lateinit var loginBtn: Button
    private lateinit var progressBar: ProgressBar
    private lateinit var mainActivityViewModel: MainActivityViewModel
    private lateinit var saveLoginCheckBox: CheckBox
    private lateinit var mPreferences: SharedPreferences
    private lateinit var mPrefsEditor: SharedPreferences.Editor
    private var saveLogin: Boolean? = null
    private lateinit var gson: Gson
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        init()

        loginBtn.setOnClickListener {

            validate(userNameTF.editText?.text.toString(), passwordTF.editText?.text.toString())
        }
        saveLoginCheckBox.setOnClickListener {
            checkRememberMe(
                userNameTF.editText?.text.toString(),
                passwordTF.editText?.text.toString()
            )
        }
        passwordET.doOnTextChanged { text, start, before, count ->
            Log.v("start", start.toString() + text.toString())
            passwordTF.error = null
            passwordTF.isErrorEnabled = false
        }
        userNameET.doOnTextChanged { text, start, before, count ->
            userNameTF.error = null
            userNameTF.isErrorEnabled = false
        }
    }

    private fun init() {
        userNameTF = findViewById(R.id.userNameLayout)
        passwordTF = findViewById(R.id.passwordLayout)
        userNameET = findViewById(R.id.userNameEditText)
        passwordET = findViewById(R.id.passwordEditText)
        loginBtn = findViewById(R.id.loginBtn)
        progressBar = findViewById(R.id.progressBar)
        mainActivityViewModel = ViewModelProvider(
            this
        ).get(MainActivityViewModel::class.java)
        mainActivityViewModel.setAtrribute(ApiHelperImpl(RetrofitClient.getApiService()))
        saveLoginCheckBox = findViewById(R.id.checkBox)
        mPreferences = getSharedPreferences("prefs", MODE_PRIVATE)
        mPrefsEditor = mPreferences.edit()
        saveLogin = mPreferences.getBoolean("saveLogin", false)
        if (saveLogin == true) {
            userNameET.setText(mPreferences.getString("username", ""))
            passwordET.setText(mPreferences.getString("password", ""))
            saveLoginCheckBox.setChecked(true)
        }
        gson = Gson()
    }

    private fun validate(userName: String, password: String) {
        userNameTF.error = null
        passwordTF.error = null
        userNameTF.isErrorEnabled = false
        passwordTF.isErrorEnabled = false
        if (userName.isEmpty() || password.isEmpty()) {
            Toast.makeText(
                applicationContext,
                getString(R.string.errorEmptyFeilds),
                Toast.LENGTH_LONG
            ).show()
        } else {
            lifecycleScope.launchWhenStarted {
                checkLogin(userName, password)
            }
        }

    }

    fun checkRememberMe(userName: String, password: String) {
        if (saveLoginCheckBox.isChecked()) {
            mPrefsEditor.putBoolean("saveLogin", true)
            mPrefsEditor.putString("username", userName)
            mPrefsEditor.putString("password", password)
            mPrefsEditor.commit()
        } else {
            mPrefsEditor.clear()
            mPrefsEditor.commit()
        }

    }

    private fun saveUser(user: User) {
        val json = gson.toJson(user)
        mPrefsEditor.putString("user", json)
        mPrefsEditor.commit()
    }

    private suspend fun checkLogin(userName: String, password: String) {
        mainActivityViewModel.loginUser(userName, password)
        mainActivityViewModel.loginUserResponse.collect {
            it?.let {
                progressBar.visibility = View.VISIBLE
                when (it.status) {

                    Status.SUCCESS -> {
                        Log.v("status", "success")
                        progressBar.visibility = View.GONE
                        it.data?.let {
                            Log.v("data", it.toString())
                            saveUser(it)
                            startActivity(Intent(this, HomeActivity::class.java))
                            finish()
                        }

                    }
                    Status.LOADING -> {
                        progressBar.visibility = View.VISIBLE
                        Log.v("status", "loading")

                    }
                    Status.ERROR -> {
                        progressBar.visibility = View.GONE
                        Log.v("status", "error " + it.toString())
                        Toast.makeText(
                            applicationContext,
                            getString(R.string.errorConnection),
                            Toast.LENGTH_LONG
                        )
                            .show()
                    }
                }
            }
        }
    }
}
