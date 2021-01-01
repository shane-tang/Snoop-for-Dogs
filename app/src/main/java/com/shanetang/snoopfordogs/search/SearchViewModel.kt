package com.shanetang.snoopfordogs.search

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class SearchViewModel : ViewModel() {
    private val _zipcode = MutableLiveData<String>("")
    val zipcode: LiveData<String> = _zipcode

    fun setZipcode(value: String) {
        _zipcode.value = value
    }
}
