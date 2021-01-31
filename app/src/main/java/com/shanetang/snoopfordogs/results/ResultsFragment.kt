package com.shanetang.snoopfordogs.results

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.PagerSnapHelper
import androidx.recyclerview.widget.RecyclerView
import com.shanetang.domain.models.SearchResults
import com.shanetang.snoopfordogs.R

class ResultsFragment(state: SearchResults) : Fragment() {

    private val adapter = if (state is SearchResults.Successful) ResultsViewAdapter(state.animals) else null

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.search_results_layout, container, false)
        val snapHelper = PagerSnapHelper()

        val recyclerView: RecyclerView = view.findViewById(R.id.search_results_recyclerview)
        recyclerView.adapter = adapter
        recyclerView.layoutManager = LinearLayoutManager(context)
        recyclerView.onFlingListener = null
        snapHelper.attachToRecyclerView(recyclerView)

        return view
    }

    companion object {
        fun newInstance(state: SearchResults) = ResultsFragment(state)
    }
}
