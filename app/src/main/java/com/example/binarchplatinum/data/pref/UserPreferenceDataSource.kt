package com.example.binarchplatinum.data.pref

interface UserPreferenceDataSource {
    fun isSkipIntro(): Boolean
    fun setSkipIntro(isSkipIntro: Boolean)
}

class UserPreferenceDataSourceImpl(private val preference: UserPreference) :
    UserPreferenceDataSource {

    override fun isSkipIntro(): Boolean {
        return preference.isSkipIntro()
    }

    override fun setSkipIntro(isSkipIntro: Boolean) {
        preference.setSkipIntro(isSkipIntro)
    }

}