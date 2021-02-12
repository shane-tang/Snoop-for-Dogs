package com.shanetang.snoopfordogs.results

import android.os.Parcelable
import android.view.View
import com.shanetang.domain.models.Animal
import kotlinx.parcelize.Parcelize

@Parcelize
open class SearchResultUIState : Parcelable {

    data class Loaded(val animal: Animal) : SearchResultUIState()

    data class EmptyOrError(
        val message: String,
        val ctaMessage: String,
        val ctaAction: View.OnClickListener,
    ) : SearchResultUIState()

    object Loading : SearchResultUIState()
}
