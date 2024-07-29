package com.zee.blueapp.ui.home.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import coil.load
import com.zee.blueapp.R
import com.zee.blueapp.databinding.CaraouselImageBinding

import com.zee.blueapp.domain.models.Topic

class CarouselAdapter : ListAdapter<Topic, CarouselAdapter.CarouselViewHolder>(diffUtil) {
    companion object {
        val diffUtil = object : DiffUtil.ItemCallback<Topic>() {
            override fun areItemsTheSame(oldItem: Topic, newItem: Topic): Boolean =
                oldItem.id == newItem.id

            override fun areContentsTheSame(oldItem: Topic, newItem: Topic): Boolean =
                oldItem.id == newItem.id
                        && oldItem.coverPhoto == newItem.coverPhoto
                        && oldItem.description == newItem.description
                        && oldItem.title == newItem.title


        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CarouselViewHolder {
        val binding =
            CaraouselImageBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return CarouselViewHolder(binding)
    }

    override fun onBindViewHolder(holder: CarouselViewHolder, position: Int) {
        holder.bind(getItem(position))
    }

    inner class CarouselViewHolder(private val binding: CaraouselImageBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(data: Topic) = binding.run {
            root.load(data.coverPhoto.url) {
                placeholder(R.drawable.image_place_holder)
            }
        }

    }
}