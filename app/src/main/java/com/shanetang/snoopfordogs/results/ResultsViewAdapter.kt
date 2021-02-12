package com.shanetang.snoopfordogs.results

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.PagerSnapHelper
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.RecyclerView.ViewHolder
import com.shanetang.snoopfordogs.R

class ResultsViewAdapter : RecyclerView.Adapter<ViewHolder>() {

    class ViewHolderResult(view: View) : ViewHolder(view)

    class ViewHolderLoading(view: View) : ViewHolder(view)

    class ViewHolderEmptyError(view: View) : ViewHolder(view)

    private val states = ArrayList<SearchResultUIState>()

    fun addStates(newStates: SearchResultUIState) {
        if (states.size > 0 && states.last() !is SearchResultUIState.Loaded) {
            states.removeLast()
            notifyItemRemoved(states.lastIndex)
        }
        states.add(newStates)
        notifyItemInserted(states.lastIndex)
    }

    override fun getItemViewType(position: Int): Int {
        return when (states[position]) {
            is SearchResultUIState.Loaded -> LOADED
            is SearchResultUIState.Loading -> LOADING
            is SearchResultUIState.EmptyOrError -> EMPTY_OR_ERROR

            else -> EMPTY_OR_ERROR // should never happen
        }
    }

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): ViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        return when (viewType) {
            LOADED -> ViewHolderResult(
                inflater.inflate(
                    R.layout.search_result_item_layout,
                    parent,
                    false
                )
            )
            LOADING -> ViewHolderLoading(
                inflater.inflate(
                    R.layout.search_loading_layout,
                    parent,
                    false
                )
            )
            else -> ViewHolderEmptyError(
                inflater.inflate(
                    R.layout.message_with_cta_layout,
                    parent,
                    false
                )
            )
        }
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        when (states[position]) {
            is SearchResultUIState.Loaded -> {
                val animal = (states[position] as SearchResultUIState.Loaded).animal
                val subtext =
                    animal.distance.toString() + " miles" + if (animal.age != null) " • ${animal.age}" else ""
                holder.itemView.findViewById<TextView>(R.id.result_name).text = animal.name
                holder.itemView.findViewById<TextView>(R.id.result_location).text = subtext

                val imageURIs = animal.pictures?.map {
                    it.urlFullsize
                } ?: listOf(null)

                val imagesView: RecyclerView = holder.itemView.findViewById(R.id.result_images)
                imagesView.adapter = ImagesViewAdapter(imageURIs)
                imagesView.layoutManager = LinearLayoutManager(
                    holder.itemView.context,
                    RecyclerView.HORIZONTAL,
                    false
                )
                imagesView.onFlingListener = null
                PagerSnapHelper().attachToRecyclerView(imagesView)
            }
            is SearchResultUIState.EmptyOrError -> {
                val state = states[position] as SearchResultUIState.EmptyOrError
                with(holder.itemView) {
                    findViewById<TextView>(R.id.emptyerror_text).text = state.message
                    findViewById<Button>(R.id.emptyerror_action).text = state.ctaMessage
                    findViewById<Button>(R.id.emptyerror_action).setOnClickListener(state.ctaAction)
                }
            }
        }
    }

    override fun getItemCount() = states.size

    // TODO: can this be an enum? getItemViewType() returns an Int, kotlin is being stubborn
    companion object {
        const val LOADED = 0
        const val LOADING = 1
        const val EMPTY_OR_ERROR = 2
    }
}
