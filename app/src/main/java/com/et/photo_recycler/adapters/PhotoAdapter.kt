package com.et.photo_recycler.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.et.photo_recycler.R
import com.et.photo_recycler.model.PhotoModel

class PhotoAdapter (items: List<PhotoModel>) : RecyclerView.Adapter<PhotoAdapter.PhotoHolder>() {

    var items: ArrayList<PhotoModel> = ArrayList()

    init {
        this.items.addAll(items)
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PhotoHolder {
        val inflater = LayoutInflater.from(parent.context)
        return PhotoHolder(inflater.inflate(R.layout.item_photo, parent, false))
    }

    override fun onBindViewHolder(holder: PhotoHolder, position: Int) {
        val photo = items[position % items.size]

        Glide.with(holder.itemView.context)
            .load(photo.url)
            .centerCrop()
            .into(holder.img!!)
    }

    override fun getItemCount(): Int {
        //return items.size
        return Int.MAX_VALUE
    }

    class PhotoHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        var img: ImageView? = null

        init {
            img = itemView.findViewById(R.id.img)
        }
    }
}