package com.shanetang.snoopfordogs.results

import android.view.View
import androidx.lifecycle.ViewModel
import com.shanetang.domain.interactor.SearchInteractor
import com.shanetang.domain.models.SearchResults
import com.shanetang.snoopfordogs.search.SearchParameters
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow

class ResultsViewModel : ViewModel() {

    fun performSearch(searchParameters: SearchParameters): Flow<SearchResultUIState> = flow {
        emit(SearchResultUIState.Loading)

        when (val results = SearchInteractor().searchAnimals(
            searchParameters.apikey,
            searchParameters.resultStart,
            searchParameters.zipcode
        )) {
            is SearchResults.Successful -> {
                results.animals.forEach { emit(SearchResultUIState.Loaded(it)) }
            }
            is SearchResults.Empty -> emit(
                SearchResultUIState.EmptyOrError(
                    "empty",
                    "go back",
                    View.OnClickListener { }
                )
            )
            is SearchResults.Error -> emit(
                SearchResultUIState.EmptyOrError(
                    "error",
                    "retry",
                    View.OnClickListener { }
                )
            )
        }
    }
}