package com.example.krendikova.presentation.films

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import coil.load
import com.example.krendikova.databinding.FilmsItemBinding
import com.example.krendikova.presentation.FilmUiModel

class FilmsAdapter() : ListAdapter<FilmUiModel, FilmsAdapter.FilmsViewHolder>(DiffCallback) {

    inner class FilmsViewHolder(
        private val binding: FilmsItemBinding
    ) : RecyclerView.ViewHolder(binding.root) {

        fun bind(item: FilmUiModel) {
            with(binding) {
                filmName.text = item.name
                filmGenre.text = item.genres[0] + " (" + item.year.toString() + ")"
                bannerImg.load(item.posterUrlPreview)
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): FilmsViewHolder =
        FilmsViewHolder(
            FilmsItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        )

    override fun onBindViewHolder(holder: FilmsViewHolder, position: Int) =
        holder.bind(getItem(position))

    companion object {
        private val DiffCallback = object : DiffUtil.ItemCallback<FilmUiModel>() {
            override fun areItemsTheSame(oldItem: FilmUiModel, newItem: FilmUiModel) = oldItem.id == newItem.id
            override fun areContentsTheSame(oldItem: FilmUiModel, newItem: FilmUiModel) = oldItem == newItem
        }
    }
}