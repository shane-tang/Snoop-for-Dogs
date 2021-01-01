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
import com.shanetang.snoopfordogs.R
import com.shanetang.snoopfordogs.databinding.SearchLayoutBinding

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

    private fun performSearch() {
        if (isValid()) {
            Toast.makeText(context, "Search coming soon!", Toast.LENGTH_SHORT).show()
        } else {
            Toast.makeText(context, "Zipcode is required!", Toast.LENGTH_SHORT).show()
        }
    }

    companion object {
        fun newInstance() = SearchFragment()
    }
}
