package com.example.binarchplatinum.data.localpref

import android.content.Context
import android.content.SharedPreferences
import androidx.core.content.edit

class UserPreference(context: Context) {
    private val preference: SharedPreferences =
        context.getSharedPreferences(NAME, Context.MODE_PRIVATE)

    companion object {
        private const val NAME = "UserPreference"
    }

    fun isSkipIntro(): Boolean {
        return preference.getBoolean(
            PreferenceKey.PREF_IS_SKIP_INTRO.first,
            PreferenceKey.PREF_IS_SKIP_INTRO.second
        )
    }

    fun setSkipIntro(isSkipIntro: Boolean) {
        preference.edit {
            this.putBoolean(PreferenceKey.PREF_IS_SKIP_INTRO.first, isSkipIntro)
        }
    }

    fun getUserToken(): String? {
        return preference.getString(
            PreferenceKey.PREF_USER_TOKEN.first,
            PreferenceKey.PREF_USER_TOKEN.second
        )
    }

    fun setUserToken(username: String) {
        preference.edit {
            this.putString(PreferenceKey.PREF_USER_TOKEN.first, username)
        }
    }

    fun clearUserToken() {
        preference.edit {
            this.putString(PreferenceKey.PREF_USER_TOKEN.first, "")
        }
    }

}

object PreferenceKey {
    val PREF_IS_SKIP_INTRO = Pair("PREF_IS_SKIP_INTRO", false)
    val PREF_USER_TOKEN = Pair("PREF_USER_TOKEN", null)
}