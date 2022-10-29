package com.example.binarchplatinum.models

import androidx.databinding.BaseObservable
import androidx.databinding.Bindable
import com.example.binarchplatinum.BR

class User : BaseObservable() {
    @get:Bindable
    var username: String = ""
        set(value) {
            field = value
            notifyPropertyChanged(BR.username)
        }

    @get:Bindable
    var password: String = ""
        set(value) {
            field = value
            notifyPropertyChanged(BR.password)
        }
}