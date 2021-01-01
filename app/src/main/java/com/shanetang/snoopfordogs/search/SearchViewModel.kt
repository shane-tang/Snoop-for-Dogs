package com.shanetang.snoopfordogs.search

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class SearchViewModel : ViewModel() {
    private val _zipcode = MutableLiveData<Int>()
    val zipcode: LiveData<Int> = _zipcode
}
