package com.shanetang.snoopfordogs.results

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.PagerSnapHelper
import androidx.recyclerview.widget.RecyclerView
import com.shanetang.domain.models.Animal
import com.shanetang.snoopfordogs.R

class ResultsViewAdapter(private val animals: List<Animal>) :
    RecyclerView.Adapter<ResultsViewAdapter.ViewHolder>() {

    class ViewHolder(view: View) : RecyclerView.ViewHolder(view)

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): ViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val view = inflater.inflate(R.layout.search_result_item_layout, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val animal = animals[position]
        val subtext = animal.distance.toString() + " miles" + if (animal.age != null) " • ${animal.age}" else ""
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

    override fun getItemCount() = animals.size
}
