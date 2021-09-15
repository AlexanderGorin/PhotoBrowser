package com.alexandergorin.photobrowser.photos

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.alexandergorin.domain.PhotoDomainModel
import com.alexandergorin.photobrowser.databinding.PhotosItemBinding
import com.alexandergorin.photobrowser.utils.GlideApp

class PhotosRecyclerViewAdapter(
    private val items: List<PhotoDomainModel>
) : RecyclerView.Adapter<PhotosRecyclerViewAdapter.ItemHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ItemHolder {
        val itemBinding = PhotosItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ItemHolder(itemBinding)
    }

    override fun onBindViewHolder(holder: ItemHolder, position: Int) {
        holder.setData(items[position])
    }

    override fun getItemCount(): Int {
        return items.size
    }

    class ItemHolder(
        private val binding: PhotosItemBinding
    ) : RecyclerView.ViewHolder(binding.root) {

        fun setData(model: PhotoDomainModel) {
            GlideApp.with(binding.imageView)
                .load(model.url)
                .into(binding.imageView)
        }
    }
}