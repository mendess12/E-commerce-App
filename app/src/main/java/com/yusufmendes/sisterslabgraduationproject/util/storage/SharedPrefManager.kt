package com.yusufmendes.sisterslabgraduationproject.util.storage

import android.content.Context
import com.yusufmendes.sisterslabgraduationproject.model.LoginResponse

class SharedPrefManager private constructor(mCtx: Context) {

    val sharedPreferences =
        mCtx.getSharedPreferences(SHARED_PREF_NAME, Context.MODE_PRIVATE)
    val editor = sharedPreferences.edit()

    //daha once giris yapilmissa cagir
    val isLoggedIn: Boolean
        get() {
            return sharedPreferences.getInt("id", -1) != -1
        }

    val data: LoginResponse
        get() {
            return LoginResponse(
                sharedPreferences.getString("message", null) ?: "",
                sharedPreferences.getInt("status", 200),
                sharedPreferences.getString("userId", null) ?: ""
            )
        }

    //login oldugunda cagir
    fun saveUser(loginData: LoginResponse) {
        editor.putString("message", loginData.message)
        editor.putInt("status", loginData.status)
        editor.putString("userId", loginData.userId)
        editor.apply()
    }

    //sign out oldugunda cagir
    fun clear() {
        editor.clear()
        editor.apply()
    }

    companion object {
        private val SHARED_PREF_NAME = "my_shared_preff"
        private var mInstance: SharedPrefManager? = null

        @Synchronized
        fun getInstance(mCtx: Context): SharedPrefManager {
            if (mInstance == null) {
                mInstance = SharedPrefManager(mCtx)
            }
            return mInstance as SharedPrefManager
        }
    }
}