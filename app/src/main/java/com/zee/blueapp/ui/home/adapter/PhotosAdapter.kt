package com.zee.blueapp.ui.home.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import coil.load
import com.zee.blueapp.R
import com.zee.blueapp.databinding.ListRcvItemBinding
import com.zee.blueapp.domain.models.Photo

class PhotosAdapter : ListAdapter<Photo, PhotosAdapter.CarouselViewHolder>(diffUtil) {
    companion object {
        val diffUtil = object : DiffUtil.ItemCallback<Photo>() {
            override fun areItemsTheSame(oldItem: Photo, newItem: Photo): Boolean =
                oldItem.id == newItem.id

            override fun areContentsTheSame(oldItem: Photo, newItem: Photo): Boolean =
                oldItem == newItem

        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CarouselViewHolder {
        val binding = ListRcvItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return CarouselViewHolder(binding)
    }

    override fun onBindViewHolder(holder: CarouselViewHolder, position: Int) {
        holder.bind(getItem(position))
    }

    inner class CarouselViewHolder(private val binding: ListRcvItemBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(data: Photo) = binding.run {
            tvTitle.text = data.userName
            tvDescription.text = data.description
            imageview.load(data.url) {
                placeholder(R.drawable.image_place_holder)
            }
        }

    }
}