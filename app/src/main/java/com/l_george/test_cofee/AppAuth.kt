package com.l_george.test_cofee

import android.content.Context

class AppAuth(context: Context) {
    companion object {
        const val PREF_AUTH_NAME_KEY = "PREF_AUTH_NAME_KEY"
        const val PREF_AUTH_VALUE_KEY = "PREF_AUTH_VALUE_KEY"
    }

    private val prefAuth = context.getSharedPreferences(PREF_AUTH_NAME_KEY, Context.MODE_PRIVATE)

    val token: String?
        get() = prefAuth.getString(PREF_AUTH_VALUE_KEY, null)

    fun saveAuth(token: String) {
        prefAuth.edit()
            .putString(PREF_AUTH_VALUE_KEY, token)
            .apply()
    }

    fun clearAuth(){
        prefAuth.edit()
            .putString(PREF_AUTH_VALUE_KEY, null)
            .apply()
    }


}