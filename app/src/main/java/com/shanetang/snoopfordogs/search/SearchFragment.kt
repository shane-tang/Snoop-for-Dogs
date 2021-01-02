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
import com.shanetang.domain.interactor.SearchInteractor
import com.shanetang.snoopfordogs.BuildConfig
import com.shanetang.snoopfordogs.R
import com.shanetang.snoopfordogs.databinding.SearchLayoutBinding
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers.IO
import kotlinx.coroutines.launch

class SearchFragment : Fragment() {
    private lateinit var viewModel: SearchViewModel
    private val interactor = SearchInteractor()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        viewModel = viewModels<SearchViewModel>().value
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        val binding: SearchLayoutBinding =
            DataBindingUtil.inflate(inflater, R.layout.search_layout, container, false)
        binding.zipcode.addTextChangedListener { viewModel.setZipcode(it.toString()) }
        binding.searchButton.setOnClickListener { performSearch() }
        return binding.root
    }

    private fun performSearch() {
        // TODO: copy zipcode and other filters from livedata here so we dont reference livedata later

        if (viewModel.zipcode.value.isNullOrEmpty()) {
            Toast.makeText(context, "Zipcode is required!", Toast.LENGTH_SHORT).show()
        } else {
            CoroutineScope(IO).launch {
                interactor.searchAnimals(
                    apikey = BuildConfig.RESCUE_KEY,
                    zipcode = viewModel.zipcode.value ?: ""
                )
            }
        }
    }

    companion object {
        fun newInstance() = SearchFragment()
    }
}
