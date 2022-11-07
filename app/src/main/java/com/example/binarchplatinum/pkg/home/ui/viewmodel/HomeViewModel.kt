package com.example.binarchplatinum.pkg.home.ui.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

class HomeViewModel : ViewModel() {

    val someValue: MutableLiveData<String> = MutableLiveData()

    fun doSomeWork() {
        viewModelScope.launch {
            someValue.postValue("First")
            delay(1000)
            someValue.postValue("Second")
            delay(2000)
            someValue.postValue("Third")
        }
    }

}