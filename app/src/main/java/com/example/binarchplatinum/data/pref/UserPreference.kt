package com.example.binarchplatinum.data.pref

import android.content.Context
import android.content.SharedPreferences
import androidx.core.content.edit

class UserPreference(context: Context) {

    //1 : Declare the preference
    private val preference: SharedPreferences =
        context.getSharedPreferences(NAME, Context.MODE_PRIVATE)

    companion object {
        private const val NAME = "Yorda Wira Aswin"
    }

    //3 : define the setter and getter
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

    fun getToken(): String? {
        return preference.getString(
            PreferenceKey.PREF_TOKEN.first,
            PreferenceKey.PREF_TOKEN.second)
    }

}

object PreferenceKey {
    //2 : Define the key
    val PREF_IS_SKIP_INTRO = Pair("PREF_IS_SKIP_INTRO", false)
    val PREF_TOKEN = Pair("PREF_TOKEN", null)

}