package com.shanetang.snoopfordogs.search

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.widget.addTextChangedListener
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import com.shanetang.domain.Filter
import com.shanetang.domain.SearchRepository
import com.shanetang.snoopfordogs.BuildConfig
import com.shanetang.snoopfordogs.R
import com.shanetang.snoopfordogs.databinding.SearchLayoutBinding
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers.IO
import kotlinx.coroutines.launch

class SearchFragment : Fragment() {
    private lateinit var viewModel: SearchViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        viewModel = viewModels<SearchViewModel>().value
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val binding: SearchLayoutBinding = DataBindingUtil.inflate(inflater, R.layout.search_layout, container, false)
        binding.zipcode.addTextChangedListener { viewModel.setZipcode(it.toString()) }
        binding.searchButton.setOnClickListener { performSearch() }
        return binding.root
    }

    private fun isValid(): Boolean {
        return viewModel.zipcode.value != ""
    }

    private fun constructFilters() = listOf(
        Filter("animalStatus", "notequal", "adopted"),
        Filter("animalSpecies", "equals", "dog"),
        Filter("animalUpdatedDate", "greaterthan", "9/1/2020"),
        Filter("animalLocation", "equals", viewModel.zipcode.value ?: ""),
        Filter("animalLocationDistance", "radius", "1000"),
    )

    private fun performSearch() {
        if (isValid()) {
            getDogs()
        } else {
            Toast.makeText(context, "Zipcode is required!", Toast.LENGTH_SHORT).show()
        }
    }

    private fun getDogs() {
        CoroutineScope(IO).launch {
            val filters = constructFilters()
            val result = SearchRepository().getDogs(BuildConfig.RESCUE_KEY, filters)
            print(result)
        }
    }

    companion object {
        fun newInstance() = SearchFragment()
    }
}
