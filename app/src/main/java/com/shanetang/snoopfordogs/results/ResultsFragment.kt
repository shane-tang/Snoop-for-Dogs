package com.shanetang.snoopfordogs.results

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.PagerSnapHelper
import androidx.recyclerview.widget.RecyclerView
import com.shanetang.snoopfordogs.InfiniteLinearLayoutManager
import com.shanetang.snoopfordogs.R
import com.shanetang.snoopfordogs.search.SearchParameters
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.MainScope
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.launch

class ResultsFragment : Fragment() {
    private lateinit var viewModel: ResultsViewModel
    private lateinit var searchParameters: SearchParameters

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        viewModel = viewModels<ResultsViewModel>().value

        searchParameters = arguments?.getParcelable(SEARCH_PARAMETERS)!!
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.search_results_layout, container, false)
        val recyclerView: RecyclerView = view.findViewById(R.id.search_results_recyclerview)

        val adapter = ResultsViewAdapter()
        val snapHelper = PagerSnapHelper()
        val layoutManager = InfiniteLinearLayoutManager(context)

        layoutManager.setInfiniteAction {
            if (adapter.getItemViewType(layoutManager.itemCount - 1) == ResultsViewAdapter.LOADED) {
                // TODO: this 15 limit should be stored somewhere else
                searchParameters =
                    searchParameters.copy(resultStart = searchParameters.resultStart + 15)
                searchAndUpdateAdapter(adapter)
            }
        }

        recyclerView.adapter = adapter
        recyclerView.layoutManager = layoutManager
        recyclerView.onFlingListener = null
        snapHelper.attachToRecyclerView(recyclerView)

        searchAndUpdateAdapter(adapter)

        return view
    }

    private fun searchAndUpdateAdapter(adapter: ResultsViewAdapter) {
        viewModel.performSearch(searchParameters)
            .onEach {
                MainScope().launch {
                    adapter.addStates(it)
                }
            }
            .launchIn(CoroutineScope(Dispatchers.IO))
    }

    companion object {
        private const val SEARCH_PARAMETERS = "RESULTS_SEARCH_PARAMETERS"

        fun newInstance(searchParameters: SearchParameters) = ResultsFragment().apply {
            arguments = Bundle().apply {
                putParcelable(SEARCH_PARAMETERS, searchParameters)
            }
        }
    }
}
