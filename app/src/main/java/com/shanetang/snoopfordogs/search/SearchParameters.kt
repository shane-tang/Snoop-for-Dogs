package com.shanetang.snoopfordogs.search

import android.os.Parcelable
import com.shanetang.snoopfordogs.BuildConfig
import kotlinx.parcelize.Parcelize

@Parcelize
data class SearchParameters(
    val apikey: String = BuildConfig.RESCUE_KEY,
    val resultStart: Int = 0,
    val zipcode: String,
) : Parcelable