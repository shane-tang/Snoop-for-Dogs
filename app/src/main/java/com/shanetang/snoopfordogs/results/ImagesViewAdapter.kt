package com.shanetang.snoopfordogs.results

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.recyclerview.widget.RecyclerView
import com.shanetang.snoopfordogs.R
import com.squareup.picasso.Picasso
import jp.wasabeef.picasso.transformations.BlurTransformation

class ImagesViewAdapter(private val imageURIs: List<String?>) :
    RecyclerView.Adapter<ImagesViewAdapter.ViewHolder>() {

    class ViewHolder(view: View) : RecyclerView.ViewHolder(view)

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): ViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val view = inflater.inflate(R.layout.image_fullscreen_layout, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        // TODO have a "no image available" drawable locally
        val uri = imageURIs[position] ?: "https://upload.wikimedia.org/wikipedia/commons/thumb/a/ac/No_image_available.svg/600px-No_image_available.svg.png"

        val img = Picasso.get().load(uri)

        val bgView = holder.itemView.findViewById<ImageView>(R.id.image_background)
        val imageView = holder.itemView.findViewById<ImageView>(R.id.image)
        img.into(imageView)
        img.transform(BlurTransformation(holder.itemView.context, 20)).into(bgView)
    }

    override fun getItemCount() = imageURIs.size
}
